package compiler.ast;

public class InputString extends Input 
{
	 @Override
	    public void accept(Visitor visitor) {
	        visitor.visit(this);  
	    }
}
