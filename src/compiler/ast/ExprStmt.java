package compiler.ast;

//Expression Statement (e.g., "x + 1;")
public class ExprStmt extends Statement {
 public Expr expression;
 public ExprStmt(Expr expression) {
     this.expression = expression;
 }
 
 @Override
 public void accept(Visitor visitor) {
     visitor.visit(this);
     expression.accept(visitor);
 }
}