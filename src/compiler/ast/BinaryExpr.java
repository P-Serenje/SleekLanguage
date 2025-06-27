package compiler.ast;

public class BinaryExpr extends Expr {
    public String op;
    public Expr left;
    public Expr right;
    public BinaryExpr(String op, Expr left, Expr right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        left.accept(visitor);
        right.accept(visitor);
    }
}
