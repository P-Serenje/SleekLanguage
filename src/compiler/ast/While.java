package compiler.ast;


public class While extends Statement {
    public Expr condition;
    public Block body;
    public While(Expr condition, Block body) {
        this.condition = condition;
        this.body = body;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        condition.accept(visitor);
        body.accept(visitor);
    }
}
