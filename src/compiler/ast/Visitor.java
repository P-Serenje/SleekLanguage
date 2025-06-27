package compiler.ast;

public interface Visitor {
 void visit(Program program);
 void visit(FunctionDecl functionDecl);
 void visit(Block block);
 void visit(Assignment assignment);
 void visit(BinaryExpr binaryExpr);
 void visit(ExprStmt exprStmt);
 void visit(FunctionCall functionCall);
 void visit(If iff);
 void visit(InputFloat inputFloat);
 void visit(InputInt inputInt);
 void visit(InputString inputString);
 void visit(Print print);
 void visit(Return returnStmt);
 void visit(Var var);
 void visit(While whileStmt);
 void visit(Literal literal);
 

}