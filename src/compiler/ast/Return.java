package compiler.ast;

public class Return extends Statement {
    public Expr value;
    public Return(Expr value) {
        this.value = value;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        if (value != null) {
            value.accept(visitor);
        }
    }
}