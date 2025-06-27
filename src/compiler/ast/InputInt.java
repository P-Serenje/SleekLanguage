package compiler.ast;

public class InputInt extends Input 
{
	 @Override
	    public void accept(Visitor visitor) {
	        visitor.visit(this); 
	    }
}
