package compiler.ast;

public class InputFloat extends Input 
{
	 @Override
	    public void accept(Visitor visitor) {
	        visitor.visit(this); 
	    }
}
