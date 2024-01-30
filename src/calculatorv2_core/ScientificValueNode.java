package calculatorv2_core;

import java.text.DecimalFormat;

public class ScientificValueNode extends ValueNode {
	
	public static DecimalFormat scientificFormatter = new DecimalFormat("#.#########E0");
	
	public ScientificValueNode(char key) {
		super(key);
	}
	
	public ScientificValueNode(double value) {
		super(value);
	}
	
	
	@Override
	public String toString() {
		return scientificFormatter.format(getValue());
	}
}
