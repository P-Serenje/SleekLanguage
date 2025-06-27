package compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import antlr.SleekBaseVisitor;
import antlr.SleekParser;
import antlr.SleekParser.*;
import antlr.SleekParser.AdditiveExprContext;
import antlr.SleekParser.AssignmentContext;
import antlr.SleekParser.BlockContext;
import antlr.SleekParser.EqualityExprContext;
import antlr.SleekParser.ExprContext;
import antlr.SleekParser.ExprStmtContext;
import antlr.SleekParser.FunctionCallContext;
import antlr.SleekParser.FunctionDeclContext;
import antlr.SleekParser.IfStmtContext;
import antlr.SleekParser.LiteralContext;
import antlr.SleekParser.MultiplicativeExprContext;
import antlr.SleekParser.PrimaryContext;
import antlr.SleekParser.PrintStmtContext;
import antlr.SleekParser.ProgramContext;
import antlr.SleekParser.RelationalExprContext;
import antlr.SleekParser.ReturnStmtContext;
import antlr.SleekParser.WhileStmtContext;
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


public class ASTConverter extends SleekBaseVisitor<ASTNode> {

	@Override
	public ASTNode visitProgram(ProgramContext ctx) {
	    List<ASTNode> nodes = new ArrayList<>();

	    for (var funcDecl : ctx.functionDecl()) {
	        nodes.add(visit(funcDecl));
	    }

	    for (var stmt : ctx.statement()) {
	        nodes.add(visit(stmt));
	    }

	    return new Program(nodes);
	}


    @Override
    public ASTNode visitFunctionDecl(FunctionDeclContext ctx) {
        String name = ctx.ID().getText();
        List<String> params = ctx.paramList() != null ? 
            ctx.paramList().ID().stream()
                .map(id -> id.getText())
                .collect(Collectors.toList()) 
            : new ArrayList<>();
        Block body = (Block) visit(ctx.block());
        return new FunctionDecl(name, params, body);
    }

    @Override
    public ASTNode visitBlock(BlockContext ctx) {
        List<Statement> stmts = ctx.statement().stream()
            .map(stmt -> (Statement) visit(stmt))
            .collect(Collectors.toList());
        return new Block(stmts);
    }

    @Override
    public ASTNode visitAssignment(AssignmentContext ctx) {
        String varName = ctx.ID().getText();
        Expr value = (Expr) visit(ctx.expr());
        return new Assignment(varName, value);
    }

    @Override
    public ASTNode visitPrintStmt(PrintStmtContext ctx) {
        Expr expr = (Expr) visit(ctx.expr());
        return new Print(expr);
    }

    @Override
    public ASTNode visitReturnStmt(ReturnStmtContext ctx) {
        Expr value = ctx.expr() != null ? (Expr) visit(ctx.expr()) : null;
        return new Return(value);
    }

    @Override
    public ASTNode visitIfStmt(IfStmtContext ctx) {
        Expr cond = (Expr) visit(ctx.expr());
        Block thenBlock = (Block) visit(ctx.block(0));
        Block elseBlock = ctx.ELSE() != null ? (Block) visit(ctx.block(1)) : null;
        return new If(cond, thenBlock, elseBlock);
    }

    @Override
    public ASTNode visitWhileStmt(WhileStmtContext ctx) {
        Expr cond = (Expr) visit(ctx.expr());
        Block body = (Block) visit(ctx.block());
        return new While(cond, body);
    }

    @Override
    public ASTNode visitExprStmt(ExprStmtContext ctx) {
        return new ExprStmt((Expr) visit(ctx.expr()));
    }

    @Override
    public Expr visitExpr(ExprContext ctx) {
        return (Expr) visit(ctx.equalityExpr());
    }

    @Override
    public Expr visitEqualityExpr(EqualityExprContext ctx) {
        if (ctx.children.size() == 1) {
            return (Expr) visit(ctx.relationalExpr(0));
        } else {
            String op = ctx.getChild(1).getText(); // EQ or NEQ
            Expr left = (Expr) visit(ctx.relationalExpr(0));
            Expr right = (Expr) visit(ctx.relationalExpr(1));
            return new BinaryExpr(op, left, right);
        }
    }

    // Similar for relationalExpr, additiveExpr, multiplicativeExpr
    @Override
    public Expr visitRelationalExpr(RelationalExprContext ctx) {
        if (ctx.children.size() == 1) {
            return (Expr) visit(ctx.additiveExpr(0));
        } else {
            String op = ctx.getChild(1).getText(); // LT, GT, LE, GE
            Expr left = (Expr) visit(ctx.additiveExpr(0));
            Expr right = (Expr) visit(ctx.additiveExpr(1));
            return new BinaryExpr(op, left, right);
        }
    }

    @Override
    public Expr visitAdditiveExpr(AdditiveExprContext ctx) {
        Expr expr = (Expr) visit(ctx.multiplicativeExpr(0));
        for (int i = 1; i < ctx.multiplicativeExpr().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            Expr right = (Expr) visit(ctx.multiplicativeExpr(i));
            expr = new BinaryExpr(op, expr, right);
        }
        return expr;
    }
    
    @Override
    public Expr visitMultiplicativeExpr(MultiplicativeExprContext ctx) {
        if (ctx.children.size() == 1) {
            // Access the first (and only) primary element in the list
            return (Expr) visit(ctx.primary(0)); // <-- Use index 0
        } else {
            String op = ctx.getChild(1).getText(); // MUL or DIV
            Expr left = (Expr) visit(ctx.primary(0));
            Expr right = (Expr) visit(ctx.primary(1));
            return new BinaryExpr(op, left, right);
        }
    }

    @Override
    public Expr visitPrimary(PrimaryContext ctx) {
        if (ctx.ID() != null) {
            return new Var(ctx.ID().getText());
        } else if (ctx.functionCall() != null) {
            return (Expr) visit(ctx.functionCall());
        } else if (ctx.inputCall() != null) {
            return (Expr) visit(ctx.inputCall());
        } else if (ctx.literal() != null) {
            return (Expr) visit(ctx.literal());
        } else {
            return (Expr) visit(ctx.expr());
        }
    }

    @Override
    public Expr visitFunctionCall(FunctionCallContext ctx) {
        String name = ctx.ID().getText();
        List<Expr> args = ctx.argList() != null ? 
            ctx.argList().expr().stream()
                .map(arg -> (Expr) visit(arg))
                .collect(Collectors.toList())
            : new ArrayList<>();
        return new FunctionCall(name, args);
    }

    @Override
    public Expr visitInputIntCall(SleekParser.InputIntCallContext ctx) {
        return new InputInt(); // Create AST node for integer input
    }

    @Override
    public Expr visitInputFloatCall(SleekParser.InputFloatCallContext ctx) {
        return new InputFloat(); // Create AST node for float input
    }

    @Override
    public Expr visitInputStringCall(SleekParser.InputStringCallContext ctx) {
        return new InputString(); // Create AST node for string input
    }
    
    @Override
    public Expr visitLiteral(LiteralContext ctx) {
        if (ctx.INT() != null) {
            return new Literal(Integer.parseInt(ctx.INT().getText()));
        } else if (ctx.FLOAT() != null) {
            return new Literal(Double.parseDouble(ctx.FLOAT().getText()));
        } else {
            String str = ctx.STRING().getText();
            return new Literal(str.substring(1, str.length() - 1)); // Remove quotes
        }
    }
}