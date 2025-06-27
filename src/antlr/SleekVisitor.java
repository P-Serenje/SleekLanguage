// Generated from Sleek.g4 by ANTLR 4.13.2
package antlr;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SleekParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SleekVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SleekParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(SleekParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link SleekParser#functionDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDecl(SleekParser.FunctionDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link SleekParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(SleekParser.ParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SleekParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(SleekParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SleekParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(SleekParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link SleekParser#printStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStmt(SleekParser.PrintStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link SleekParser#returnStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(SleekParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link SleekParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(SleekParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link SleekParser#whileStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(SleekParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link SleekParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(SleekParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link SleekParser#exprStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStmt(SleekParser.ExprStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link SleekParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(SleekParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SleekParser#equalityExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpr(SleekParser.EqualityExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SleekParser#relationalExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpr(SleekParser.RelationalExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SleekParser#additiveExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpr(SleekParser.AdditiveExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SleekParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpr(SleekParser.MultiplicativeExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SleekParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(SleekParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SleekParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(SleekParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InputIntCall}
	 * labeled alternative in {@link SleekParser#inputCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInputIntCall(SleekParser.InputIntCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InputFloatCall}
	 * labeled alternative in {@link SleekParser#inputCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInputFloatCall(SleekParser.InputFloatCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InputStringCall}
	 * labeled alternative in {@link SleekParser#inputCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInputStringCall(SleekParser.InputStringCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link SleekParser#argList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgList(SleekParser.ArgListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SleekParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(SleekParser.LiteralContext ctx);
}