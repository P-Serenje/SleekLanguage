package compiler.ast;

public class Var extends Expr {
    public String name;
    public Var(String name) {
        this.name = name;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
