package calculatorv2_core;

public class ValueNode extends EquationNode {
	protected double value;
	
	public ValueNode(double value) {
		this.value = value;
		
		valueData = this;
	}
	public ValueNode(char key) {
		assert key == 'k'; // ensure that you can only use this constuctor if you know what you are doing
		valueData = this;
	}
	
	@Override
	public double getValue() {
		return value;
	}
	
	@Override
	public long getLevel() {
		return Long.MAX_VALUE;
	}
	
	@Override
	public String toString() {
		return "" + getValue();
	}
	public void setValue(double value) {
		notCalculated();
		calculated();
		this.value = value;	
	}

}
