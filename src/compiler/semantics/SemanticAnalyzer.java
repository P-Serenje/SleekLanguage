package compiler.semantics;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import compiler.ast.ASTNode;
import compiler.ast.Assignment;
import compiler.ast.BinaryExpr;
import compiler.ast.Block;
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

//===== SEMANTIC ANALYZER =====
public class SemanticAnalyzer {
    private final SymbolTable symbols = new SymbolTable();
    private final Map<String, FunctionDecl> functionDecls = new HashMap<>();
    private final Deque<String> functionStack = new ArrayDeque<>();
    private final Set<String> functionsAnalyzed = new HashSet<>();

    public void analyze(Program prog) {
        symbols.enterScope();

        // 1) Predefine built-ins
        symbols.define(new FunctionSymbol("input",      new IntType(),    List.of()));
        symbols.define(new FunctionSymbol("inputfloat", new FloatType(),  List.of()));
        symbols.define(new FunctionSymbol("inputstring",new StringType(), List.of()));
        symbols.define(new FunctionSymbol("print",      new VoidType(),   List.of(new VariableSymbol("value", null))));

        // 2) Collect only signatures of user functions
        for (ASTNode decl : prog.declarations) {
            if (decl instanceof FunctionDecl fn) {
                functionDecls.put(fn.name, fn);
                defineFunction(fn);
            }
        }

        // 3) Analyze only top-level statements now (functions deferred)
        for (ASTNode decl : prog.declarations) {
            if (!(decl instanceof FunctionDecl)) {
                visit(decl);
            }
        }

        symbols.exitScope();
    }

    private void defineFunction(FunctionDecl fn) {
        List<VariableSymbol> params = new ArrayList<>();
        for (String pname : fn.params) {
            params.add(new VariableSymbol(pname, null));
        }
        symbols.define(new FunctionSymbol(fn.name, null, params));
    }

    private Type visit(ASTNode node) {
        if (node instanceof Assignment asn)    return visitAssignment(asn);
        if (node instanceof Print p)           { visit(p.expression); return null; }
        if (node instanceof Return r)          return visitReturn(r);
        if (node instanceof If iff)            return visitIf(iff);
        if (node instanceof While w)           return visitWhile(w);
        if (node instanceof ExprStmt es)       return visit(es.expression);
        if (node instanceof FunctionCall fc)   return visitFunctionCall(fc);
        if (node instanceof BinaryExpr be)     return visitBinary(be);
        if (node instanceof Literal lit) {
            Object v = lit.value;
            if (v instanceof Integer) return new IntType();
            if (v instanceof Double)  return new FloatType();
            return new StringType();
        }
        if (node instanceof Var v) {
            Symbol sym = symbols.resolve(v.name);
            if (sym == null) throw new SemanticException("Undefined variable " + v.name);
            if (sym.getType() == null) throw new SemanticException("Uninitialized variable " + v.name);
            return sym.getType();
        }
        if (node instanceof InputInt)    return new IntType();
        if (node instanceof InputFloat)  return new FloatType();
        if (node instanceof InputString) return new StringType();
        throw new SemanticException("Unhandled AST node: " + node.getClass().getSimpleName());
    }

    private Type visitAssignment(Assignment asn) {
        Type rhs = visit(asn.value);
        Symbol sym = symbols.resolve(asn.varName);
        if (sym == null) {
            symbols.define(new VariableSymbol(asn.varName, rhs));
        } else {
            if (!sym.getType().getName().equals(rhs.getName()))
                throw new SemanticException("Type mismatch assigning to " + asn.varName);
        }
        return rhs;
    }

    private Type visitReturn(Return r) {
        Type rt = visit(r.value);
        String fname = functionStack.peek();
        FunctionSymbol fs = (FunctionSymbol) symbols.resolve(fname);
        if (fs.getReturnType() == null) fs.setReturnType(rt);
        else if (!fs.getReturnType().getName().equals(rt.getName()))
            throw new SemanticException("Inconsistent return type in function " + fname);
        return rt;
    }

    private void visitFunctionDecl(FunctionDecl fn) {
        if (functionsAnalyzed.contains(fn.name)) return;
        functionsAnalyzed.add(fn.name);

        functionStack.push(fn.name);
        symbols.enterScope();

        // Bind parameters now that we know argument types
        FunctionSymbol fs = (FunctionSymbol) symbols.resolve(fn.name);
        for (VariableSymbol param : fs.getParameters()) {
            symbols.define(param);
        }

        // Analyze the body with parameters bound
        for (Statement s : fn.body.statements) {
            visit(s);
        }

        // If no return seen, default to void
        if (fs.getReturnType() == null) {
            fs.setReturnType(new VoidType());
        }

        symbols.exitScope();
        functionStack.pop();
    }

    private Type visitIf(If iff) {
        Type cond = visit(iff.condition);
        if (!cond.isNumeric())
            throw new SemanticException("Condition must be numeric (0/1) in if");
        visitBlock(iff.thenBlock);
        if (iff.elseBlock != null) visitBlock(iff.elseBlock);
        return null;
    }

    private Type visitWhile(While w) {
        Type cond = visit(w.condition);
        if (!cond.isNumeric())
            throw new SemanticException("Condition must be numeric (0/1) in while");
        visitBlock(w.body);
        return null;
    }

    private Type visitBinary(BinaryExpr be) {
        Type left  = visit(be.left);
        Type right = visit(be.right);

        switch (be.op) {
            case "+": case "-": case "*": case "/":
                if (!left.isNumeric() || !right.isNumeric())
                    throw new SemanticException("Arithmetic requires numeric types");
                return (left instanceof FloatType || right instanceof FloatType)
                        ? new FloatType() : new IntType();

            case "<": case ">": case "<=": case ">=":
            case "==": case "!=":
                if (!left.isNumeric() || !right.isNumeric())
                    throw new SemanticException("Comparison requires numeric types");
                return new IntType();

            default:
                throw new SemanticException("Unknown operator " + be.op);
        }
    }

    private Type visitFunctionCall(FunctionCall fc) {
        Symbol sym = symbols.resolve(fc.name);
        if (!(sym instanceof FunctionSymbol fs))
            throw new SemanticException("Unknown function " + fc.name);

        // Arity check
        if (fc.args.size() != fs.getParameters().size())
            throw new SemanticException("Argument count mismatch in call to " + fc.name);

        // Infer or check each parameter type
        for (int i = 0; i < fc.args.size(); i++) {
            Type argType = visit(fc.args.get(i));
            VariableSymbol param = fs.getParameters().get(i);
            if (param.getType() == null) {
                // first‐time binding
                param.setType(argType);
            } else if (!param.getType().getName().equals(argType.getName())) {
                throw new SemanticException(
                    String.format("Param type mismatch in call to %s: expected %s, got %s",
                                  fc.name,
                                  param.getType().getName(),
                                  argType.getName()));
            }
        }

        // Now that parameters are bound, analyze the function body once
        if (!functionsAnalyzed.contains(fc.name)) {
            visitFunctionDecl(functionDecls.get(fc.name));
        }

        // Return the function's return type
        Type rt = fs.getReturnType();
        if (rt == null) {
            throw new SemanticException("Function " + fc.name + " has no return type");
        }
        return rt;
    }

    private void visitBlock(Block blk) {
        symbols.enterScope();
        for (Statement s : blk.statements) {
            visit(s);
        }
        symbols.exitScope();
    }
}
