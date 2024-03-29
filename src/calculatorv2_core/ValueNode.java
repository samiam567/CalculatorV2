package calculatorv2_core;

public class ValueNode extends EquationNode {
	protected double value;
	
	public ValueNode(double value) {
		this.value = value;
		
		valueData = this;
	}
	public ValueNode(char key) {
		assert key == 'k'; // ensure that you can only use this constructor if you know what you are doing
		valueData = this;
	}
	

	@Override
	public double getValue() {
		return value;
	}
	
	
	public long getLevel() {
		return Long.MAX_VALUE;
	}
	
	@Override
	public String toString() {
		return Calculator.decimalFormat.format(getValue());
	}
	public void setValue(double value) {
		notCalculated();
		calculated();
		this.value = value;	
	}
	
	@Override
	public String getOperationKeyword() {
		return null;
	}
	
	@Override
	public void test() { 
		Calculator.error("generic EquationNode.test should always be overriden. Offender: " + getClass());
	}
	
	@Override
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		Calculator.error("createNewInstanceOfOperation MUST be overriden by every operation Offender: " + getClass());
		return null;
	}

}
