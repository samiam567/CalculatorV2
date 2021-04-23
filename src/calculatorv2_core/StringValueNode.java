package calculatorv2_core;


public class StringValueNode extends AdvancedValueNode {
	private String str;

	
	public StringValueNode(String str) {
		super('k');
		this.str = str;
	}

	public void setValue(ValueNode v) {
		if (v instanceof StringValueNode) {
			str = ((StringValueNode) v).getString();
		}else {
			Calculator.warn("Do you really want to set the value of a String to something that's not a string?");
		}
		
	
	}
	
	public String getString() {
		return str;
	}

	
	// returns the magnitude of this number
	@Override
	public double getValue() {
		return str.length();
	}
	

	@Override
	public String toString() {
		return "\"" + getString() + "\"";
	}


	
}
