package compiler.ast;

public class Assignment extends Statement {
    public String varName;
    public Expr value;
    public Assignment(String varName, Expr value) {
        this.varName = varName;
        this.value = value;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        value.accept(visitor);
    }
}
