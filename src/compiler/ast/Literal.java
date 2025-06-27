package compiler.ast;

public class Literal extends Expr {
    public Object value;
    public Literal(Object value) {
        this.value = value;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
