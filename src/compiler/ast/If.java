package compiler.ast;

public class If extends Statement {
    public Expr condition;
    public Block thenBlock;
    public Block elseBlock;
    public If(Expr condition, Block thenBlock, Block elseBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        condition.accept(visitor);
        thenBlock.accept(visitor);
        if (elseBlock != null) {
            elseBlock.accept(visitor);
        }
    }
}
