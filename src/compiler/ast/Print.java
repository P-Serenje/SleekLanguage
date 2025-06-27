package compiler.ast;

public class Print extends Statement {
    public Expr expression;
    public Print(Expr expression) {
        this.expression = expression;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        expression.accept(visitor);
    }
}