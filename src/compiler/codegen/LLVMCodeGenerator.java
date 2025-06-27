package compiler.codegen;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import compiler.ast.ASTNode;
import compiler.ast.Assignment;
import compiler.ast.BinaryExpr;
import compiler.ast.Block;
import compiler.ast.Expr;
import compiler.ast.ExprStmt;
import compiler.ast.FunctionCall;
import compiler.ast.FunctionDecl;
import compiler.ast.If;
import compiler.ast.InputFloat;
import compiler.ast.InputInt;
import compiler.ast.InputString;
import compiler.ast.Literal;
import compiler.ast.Print;
import compiler.ast.Program;
import compiler.ast.Return;
import compiler.ast.Statement;
import compiler.ast.Var;
import compiler.ast.While;
import compiler.semantics.SemanticAnalyzer;

public class LLVMCodeGenerator {
    private final StringBuilder ir = new StringBuilder();
    private int tempCounter = 0;
    private int labelCounter = 0;
    private int stringCounter = 0;
    private final Deque<Map<String, String>> scopes = new ArrayDeque<>();
    private final Map<String, String> stringConstants = new LinkedHashMap<>();
    private final Map<String, FunctionSignature> functions = new HashMap<>();
    private final SemanticAnalyzer semanticAnalyzer;

    private static class FunctionSignature {
        String returnType;
        List<String> paramTypes;

        FunctionSignature(String returnType, List<String> paramTypes) {
            this.returnType = returnType;
            this.paramTypes = paramTypes;
        }
    }

    private static class Value {
        final String type;
        final String reg;

        Value(String type, String reg) {
            this.type = type;
            this.reg = reg;
        }
    }

    public LLVMCodeGenerator(SemanticAnalyzer semanticAnalyzer) {
        this.semanticAnalyzer = semanticAnalyzer;
        this.scopes.push(new HashMap<>());
    }

    public void generate(Program program, String filename) throws IOException {
        initializeModule();
        declareBuiltins();
        collectStringConstants(program);
        emitStringConstants();
        processFunctions(program);
        emitMain(program);
        writeToFile(filename);
    }

    private void initializeModule() {
        ir.append("; ModuleID = 'sleek'\n")
          .append("target datalayout = \"e-m:e-p270:32:32-p271:32:32-p272:64:64-i64:64-f80:128-n8:16:32:64-S128\"\n")
          .append("target triple = \"x86_64-pc-linux-gnu\"\n\n");
    }

    private void declareBuiltins() {
        ir.append("declare i32 @input()\n")
          .append("declare double @inputfloat()\n")
          .append("declare i8* @inputstring()\n")
          .append("declare void @print_int(i32)\n")
          .append("declare void @print_float(double)\n")
          .append("declare void @print_string(i8*)\n\n");
    }

    private void collectStringConstants(Program program) {
        program.accept(new Visitor() {
            @Override
            public void visit(Literal lit) {
                if (lit.value instanceof String) {
                    String str = (String) lit.value;
                    if (!stringConstants.containsValue(str)) {
                        String label = "@.str" + stringCounter++;
                        stringConstants.put(label, str);
                    }
                }
            }

            // Other visit methods with empty implementations
            @Override public void visit(Program p) {}
            @Override public void visit(FunctionDecl fn) {}
            @Override public void visit(Block b) {}
            @Override public void visit(Assignment a) {}
            @Override public void visit(BinaryExpr be) {}
            @Override public void visit(ExprStmt es) {}
            @Override public void visit(FunctionCall fc) {}
            @Override public void visit(If iff) {}
            @Override public void visit(InputFloat input) {}
            @Override public void visit(InputInt input) {}
            @Override public void visit(InputString input) {}
            @Override public void visit(Print p) {}
            @Override public void visit(Return r) {}
            @Override public void visit(Var v) {}
            @Override public void visit(While w) {}
        });
    }

    private void emitStringConstants() {
        for (Map.Entry<String, String> entry : stringConstants.entrySet()) {
            String escaped = escapeString(entry.getValue());
            ir.append(entry.getKey()).append(" = private unnamed_addr constant [")
              .append(entry.getValue().length() + 1).append(" x i8] c\"")
              .append(escaped).append("\\00\", align 1\n");
        }
        if (!stringConstants.isEmpty()) ir.append("\n");
    }

    private String escapeString(String s) {
        return s.replace("\\", "\\5C")
                .replace("\"", "\\22")
                .replace("\n", "\\0A")
                .replace("\t", "\\09");
    }

    private void processFunctions(Program program) {
        for (ASTNode node : program.declarations) {
            if (node instanceof FunctionDecl fn) {
                processFunction(fn);
            }
        }
    }

    private void processFunction(FunctionDecl fn) {
        FunctionSignature sig = getFunctionSignature(fn);
        functions.put(fn.name, sig);
        
        ir.append("define ").append(sig.returnType).append(" @").append(fn.name).append("(");
        List<String> params = new ArrayList<>();
        for (int i = 0; i < fn.params.size(); i++) {
            params.add(sig.paramTypes.get(i) + " %" + fn.params.get(i));
        }
        ir.append(String.join(", ", params)).append(") {\n");
        ir.append("entry:\n");
        
        scopes.push(new HashMap<>());
        for (int i = 0; i < fn.params.size(); i++) {
            String param = fn.params.get(i);
            String type = sig.paramTypes.get(i);
            String alloca = "%" + param + ".addr";
            ir.append("  ").append(alloca).append(" = alloca ").append(type).append("\n");
            ir.append("  store ").append(type).append(" %").append(param)
              .append(", ").append(type).append("* ").append(alloca).append("\n");
            scopes.peek().put(param, alloca);
        }
        
        emitBlock(fn.body);
        ir.append("  ret ").append(sig.returnType).append(" ").append(getDefaultValue(sig.returnType)).append("\n}\n\n");
        scopes.pop();
    }

    private void emitMain(Program program) {
        ir.append("define i32 @main() {\nentry:\n");
        scopes.push(new HashMap<>());
        for (ASTNode node : program.declarations) {
            if (!(node instanceof FunctionDecl)) {
                emitStatement((Statement) node);
            }
        }
        ir.append("  ret i32 0\n}\n");
        scopes.pop();
    }

    private void emitBlock(Block block) {
        for (Statement stmt : block.statements) {
            emitStatement(stmt);
        }
    }

    private void emitStatement(Statement stmt) {
        if (stmt instanceof Assignment) {
            emitAssignment((Assignment) stmt);
        } else if (stmt instanceof Print) {
            emitPrint((Print) stmt);
        } else if (stmt instanceof Return) {
            emitReturn((Return) stmt);
        } else if (stmt instanceof If) {
            emitIf((If) stmt);
        } else if (stmt instanceof While) {
            emitWhile((While) stmt);
        } else if (stmt instanceof Block) {
            emitBlock((Block) stmt);
        } else if (stmt instanceof ExprStmt) {
            emitExpression(((ExprStmt) stmt).expression);
        }
    }

    private void emitAssignment(Assignment asn) {
        Value val = emitExpression(asn.value);
        String alloca = scopes.peek().get(asn.varName);
        if (alloca == null) {
            alloca = "%" + asn.varName + ".addr";
            ir.append("  ").append(alloca).append(" = alloca ").append(val.type).append("\n");
            scopes.peek().put(asn.varName, alloca);
        }
        ir.append("  store ").append(val.type).append(" ").append(val.reg)
          .append(", ").append(val.type).append("* ").append(alloca).append("\n");
    }

    private void emitPrint(Print p) {
        Value val = emitExpression(p.expression);
        switch (val.type) {
            case "i32":
                ir.append("  call void @print_int(i32 ").append(val.reg).append(")\n");
                break;
            case "double":
                ir.append("  call void @print_float(double ").append(val.reg).append(")\n");
                break;
            case "i8*":
                ir.append("  call void @print_string(i8* ").append(val.reg).append(")\n");
                break;
        }
    }

    private Value emitExpression(Expr expr) {
        if (expr instanceof Literal) {
            return emitLiteral((Literal) expr);
        } else if (expr instanceof Var) {
            return emitVar((Var) expr);
        } else if (expr instanceof BinaryExpr) {
            return emitBinaryExpr((BinaryExpr) expr);
        } else if (expr instanceof FunctionCall) {
            return emitFunctionCall((FunctionCall) expr);
        } else if (expr instanceof InputInt) {
            return emitInputCall("i32", "input");
        } else if (expr instanceof InputFloat) {
            return emitInputCall("double", "inputfloat");
        } else if (expr instanceof InputString) {
            return emitInputCall("i8*", "inputstring");
        }
        throw new IllegalArgumentException("Unsupported expression type");
    }

    private Value emitLiteral(Literal lit) {
        if (lit.value instanceof Integer) {
            return new Value("i32", lit.value.toString());
        } else if (lit.value instanceof Double) {
            return new Value("double", String.format("%f", lit.value));
        } else if (lit.value instanceof String) {
            String label = findStringConstant((String) lit.value);
            String tmp = newTemp();
            ir.append("  ").append(tmp).append(" = getelementptr inbounds [")
              .append(((String) lit.value).length() + 1).append(" x i8], [")
              .append(((String) lit.value).length() + 1).append(" x i8]* ")
              .append(label).append(", i64 0, i64 0\n");
            return new Value("i8*", tmp);
        }
        throw new IllegalArgumentException("Unsupported literal type");
    }

    private Value emitVar(Var var) {
        String alloca = scopes.peek().get(var.name);
        if (alloca == null) {
            throw new IllegalStateException("Undefined variable: " + var.name);
        }
        String tmp = newTemp();
        String type = getVarType(var.name);
        ir.append("  ").append(tmp).append(" = load ").append(type)
          .append(", ").append(type).append("* ").append(alloca).append("\n");
        return new Value(type, tmp);
    }

    private Value emitBinaryExpr(BinaryExpr be) {
        Value left = emitExpression(be.left);
        Value right = emitExpression(be.right);
        Value promoted = promoteTypes(left, right);
        
        String tmp = newTemp();
        String instruction = getInstruction(be.op, promoted.type);
        
        if (be.op.matches("<=?|>=?|==|!=")) {
            ir.append("  ").append(tmp).append(" = ").append(instruction)
              .append(" ").append(promoted.type).append(" ")
              .append(left.reg).append(", ").append(right.reg).append("\n");
            return new Value("i1", tmp);
        }
        
        ir.append("  ").append(tmp).append(" = ").append(instruction)
          .append(" ").append(promoted.type).append(" ")
          .append(left.reg).append(", ").append(right.reg).append("\n");
        return new Value(promoted.type, tmp);
    }

    private Value emitFunctionCall(FunctionCall fc) {
        FunctionSignature sig = functions.get(fc.name);
        List<String> args = new ArrayList<>();
        for (int i = 0; i < fc.args.size(); i++) {
            Value arg = emitExpression(fc.args.get(i));
            Value promoted = promoteType(arg, sig.paramTypes.get(i));
            args.add(promoted.type + " " + promoted.reg);
        }
        
        String tmp = newTemp();
        ir.append("  ").append(tmp).append(" = call ").append(sig.returnType)
          .append(" @").append(fc.name).append("(").append(String.join(", ", args)).append(")\n");
        return new Value(sig.returnType, tmp);
    }

    private Value emitInputCall(String type, String func) {
        String tmp = newTemp();
        ir.append("  ").append(tmp).append(" = call ").append(type).append(" @").append(func).append("()\n");
        return new Value(type, tmp);
    }

    private Value promoteTypes(Value a, Value b) {
        if (a.type.equals(b.type)) return a;
        if (a.type.equals("double") || b.type.equals("double")) {
            Value promotedA = promoteType(a, "double");
            Value promotedB = promoteType(b, "double");
            return new Value("double", promotedA.reg);
        }
        throw new IllegalStateException("Type mismatch between " + a.type + " and " + b.type);
    }

    private Value promoteType(Value val, String targetType) {
        if (val.type.equals(targetType)) return val;
        
        String tmp = newTemp();
        if (targetType.equals("double")) {
            ir.append("  ").append(tmp)
              .append(" = sitofp i32 ").append(val.reg)
              .append(" to double\n");
        } else {
            ir.append("  ").append(tmp)
              .append(" = fptosi double ").append(val.reg)
              .append(" to i32\n");
        }
        return new Value(targetType, tmp);
    }

    private String getInstruction(String op, String type) {
        Map<String, String> floatOps = Map.of(
            "+", "fadd",
            "-", "fsub",
            "*", "fmul",
            "/", "fdiv",
            "<", "fcmp olt",
            ">", "fcmp ogt",
            "<=", "fcmp ole",
            ">=", "fcmp oge",
            "==", "fcmp oeq",
            "!=", "fcmp one"
        );
        
        Map<String, String> intOps = Map.of(
            "+", "add",
            "-", "sub",
            "*", "mul",
            "/", "sdiv",
            "<", "icmp slt",
            ">", "icmp sgt",
            "<=", "icmp sle",
            ">=", "icmp sge",
            "==", "icmp eq",
            "!=", "icmp ne"
        );

        return type.equals("double") ? floatOps.get(op) : intOps.get(op);
    }

    private String newTemp() {
        return "%t" + tempCounter++;
    }

    private String newLabel() {
        return "L" + labelCounter++;
    }

    private String findStringConstant(String value) {
        for (Map.Entry<String, String> entry : stringConstants.entrySet()) {
            if (entry.getValue().equals(value)) return entry.getKey();
        }
        return null;
    }

    private FunctionSignature getFunctionSignature(FunctionDecl fn) {
        // In real implementation, get from semantic analyzer
        return new FunctionSignature("i32", Collections.nCopies(fn.params.size(), "i32"));
    }

    private String getVarType(String name) {
        // In real implementation, get from semantic analyzer
        return "i32";
    }

    private String getDefaultValue(String type) {
        return type.equals("double") ? "0.0" : "0";
    }

    private void writeToFile(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(ir.toString());
        }
    }

    private interface Visitor extends compiler.ast.Visitor {
        // Unified visitor interface
    }
    
    private void emitReturn(Return ret) {
        if (ret.value != null) {
            Value retVal = emitExpression(ret.value);
            ir.append("  ret ").append(retVal.type).append(" ").append(retVal.reg).append("\n");
        } else {
            ir.append("  ret void\n");
        }
    }

    private void emitIf(If iff) {
        Value cond = emitExpression(iff.condition);
        String thenLabel = newLabel();
        String elseLabel = newLabel();
        String endLabel = newLabel();

        // Convert condition to i1 if needed
        Value boolCond = convertToI1(cond);
        
        ir.append("  br i1 ").append(boolCond.reg)
          .append(", label %").append(thenLabel)
          .append(", label %").append(elseLabel).append("\n");

        // Then block
        ir.append(thenLabel).append(":\n");
        emitBlock(iff.thenBlock);
        ir.append("  br label %").append(endLabel).append("\n");

        // Else block (if exists)
        ir.append(elseLabel).append(":\n");
        if (iff.elseBlock != null) {
            emitBlock(iff.elseBlock);
        }
        ir.append("  br label %").append(endLabel).append("\n");

        // End label
        ir.append(endLabel).append(":\n");
    }

    private void emitWhile(While w) {
        String loopLabel = newLabel();
        String bodyLabel = newLabel();
        String endLabel = newLabel();

        ir.append("  br label %").append(loopLabel).append("\n");
        
        // Loop condition
        ir.append(loopLabel).append(":\n");
        Value cond = emitExpression(w.condition);
        Value boolCond = convertToI1(cond);
        ir.append("  br i1 ").append(boolCond.reg)
          .append(", label %").append(bodyLabel)
          .append(", label %").append(endLabel).append("\n");

        // Loop body
        ir.append(bodyLabel).append(":\n");
        emitBlock(w.body);
        ir.append("  br label %").append(loopLabel).append("\n");

        // End label
        ir.append(endLabel).append(":\n");
    }

    private Value convertToI1(Value val) {
        if (val.type.equals("i1")) return val;
        
        String tmp = newTemp();
        if (val.type.equals("double")) {
            // Compare against 0.0
            String zero = newTemp();
            ir.append("  ").append(zero).append(" = fcmp one double ")
              .append(val.reg).append(", 0.0\n");
            return new Value("i1", zero);
        } else {
            // Compare against 0
            String zero = newTemp();
            ir.append("  ").append(zero).append(" = icmp ne i32 ")
              .append(val.reg).append(", 0\n");
            return new Value("i1", zero);
        }
    }

}