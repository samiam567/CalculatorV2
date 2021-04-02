package calculatorv2_core;

/**
 * {@summary signifies that this ValueNode stores more information than a regular ValueNode}
 * {@code essentially it takes more than one double to represent this value ex. complex numbers, matrices, etc}
 * @author samiam567
 */
public class AdvancedValueNode extends ValueNode {
	
	public boolean needsSpecialOperationConditions = true;
	/**
	 * @param key ensures that this constructor can only be used by child classes
	 */
	public AdvancedValueNode(char key) {
		super(key);
		assert key == 'k';
		super.setValueData(this);
	}
	
	/**
	 * @param val the value to set this node to
	 * @param key ensures that this constructor can only be used by child classes
	 */
	public AdvancedValueNode(double val, char key) {
		super(val);
		assert key == 'k';	
		super.setValueData(this);
	}
	
	
	public double getValue() {
		Exception e = new Exception("AdvancedValueNode of type " + getClass() + " did not override getValue()");
		e.printStackTrace();
		return value;
	}
	
	/**
	 * @return a non-aliased (brand new) copy of this AdvancedValueNode with all identical data
	 *//*
	public AdvancedValueNode copy() {
		Exception e = new Exception("copy() was not overridden (and must be) in child of AdvancedValueNode");
		e.printStackTrace();
		return null;
	}*/
	
	//this method should be overridden since by definition this value can not be represented solely by it's single double representation
	@Override
	public String toString() {
		Exception e = new Exception("toString() was not overridden (and must be) in child of AdvancedValueNode");
		e.printStackTrace();
		return super.toString();
	}
	

	@Override
	public void setValueData(ValueNode valueData) {
		Exception e = new Exception("AdvancedValueNode is its own valueData so setValueData() should never be called.");
		e.printStackTrace();
	}
	
	
}
