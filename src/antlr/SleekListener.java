// Generated from Sleek.g4 by ANTLR 4.13.2
package antlr;


import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SleekParser}.
 */
public interface SleekListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SleekParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(SleekParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link SleekParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(SleekParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link SleekParser#functionDecl}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDecl(SleekParser.FunctionDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link SleekParser#functionDecl}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDecl(SleekParser.FunctionDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link SleekParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(SleekParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SleekParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(SleekParser.ParamListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SleekParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(SleekParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SleekParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(SleekParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SleekParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(SleekParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SleekParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(SleekParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SleekParser#printStmt}.
	 * @param ctx the parse tree
	 */
	void enterPrintStmt(SleekParser.PrintStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SleekParser#printStmt}.
	 * @param ctx the parse tree
	 */
	void exitPrintStmt(SleekParser.PrintStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SleekParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(SleekParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SleekParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(SleekParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SleekParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(SleekParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SleekParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(SleekParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SleekParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(SleekParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SleekParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(SleekParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SleekParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(SleekParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SleekParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(SleekParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link SleekParser#exprStmt}.
	 * @param ctx the parse tree
	 */
	void enterExprStmt(SleekParser.ExprStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SleekParser#exprStmt}.
	 * @param ctx the parse tree
	 */
	void exitExprStmt(SleekParser.ExprStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SleekParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(SleekParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link SleekParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(SleekParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link SleekParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(SleekParser.EqualityExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link SleekParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(SleekParser.EqualityExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link SleekParser#relationalExpr}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpr(SleekParser.RelationalExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link SleekParser#relationalExpr}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpr(SleekParser.RelationalExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link SleekParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpr(SleekParser.AdditiveExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link SleekParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpr(SleekParser.AdditiveExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link SleekParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpr(SleekParser.MultiplicativeExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link SleekParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpr(SleekParser.MultiplicativeExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link SleekParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(SleekParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link SleekParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(SleekParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link SleekParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(SleekParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link SleekParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(SleekParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InputIntCall}
	 * labeled alternative in {@link SleekParser#inputCall}.
	 * @param ctx the parse tree
	 */
	void enterInputIntCall(SleekParser.InputIntCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InputIntCall}
	 * labeled alternative in {@link SleekParser#inputCall}.
	 * @param ctx the parse tree
	 */
	void exitInputIntCall(SleekParser.InputIntCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InputFloatCall}
	 * labeled alternative in {@link SleekParser#inputCall}.
	 * @param ctx the parse tree
	 */
	void enterInputFloatCall(SleekParser.InputFloatCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InputFloatCall}
	 * labeled alternative in {@link SleekParser#inputCall}.
	 * @param ctx the parse tree
	 */
	void exitInputFloatCall(SleekParser.InputFloatCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InputStringCall}
	 * labeled alternative in {@link SleekParser#inputCall}.
	 * @param ctx the parse tree
	 */
	void enterInputStringCall(SleekParser.InputStringCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InputStringCall}
	 * labeled alternative in {@link SleekParser#inputCall}.
	 * @param ctx the parse tree
	 */
	void exitInputStringCall(SleekParser.InputStringCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link SleekParser#argList}.
	 * @param ctx the parse tree
	 */
	void enterArgList(SleekParser.ArgListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SleekParser#argList}.
	 * @param ctx the parse tree
	 */
	void exitArgList(SleekParser.ArgListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SleekParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(SleekParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link SleekParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(SleekParser.LiteralContext ctx);
}