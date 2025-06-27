package compiler.ast;

import java.util.List;

public class FunctionCall extends Expr {
    public String name;
    public List<Expr> args;
    public FunctionCall(String name, List<Expr> args) {
        this.name = name;
        this.args = args;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        for (Expr arg : args) {
            arg.accept(visitor);
        }
    }
}
