package calculatorv2_core;

public class EquationObject {
	protected ValueNode valueData;
	
	private boolean calculated = false;
	
	public EquationObject() {
		if (! (this instanceof ValueNode)) {
			valueData = new ValueNode(0);
		}
	}
	
	public boolean isCalculated() {
		return calculated;
	}
	
	protected void calculated() {
		calculated = true;
	}
	
	/**
	 * {@summary sets us to not calculated, and makes all parents of this node un-calculated as well}
	 */
	protected void notCalculated() {
		calculated = false;
	}
	
	public ValueNode getValueData() {
		getValue();
		return valueData;
	}
	
	/**
	 * {@summary tells the node to evaluate and evaluates all children and returns the current value}
	 * {@code will be overridden by some children}
	 * @return
	 */
	public double getValue() {
		if (! calculated) {
			Exception e = new Exception("getValue() was not overriden by some children");
			e.printStackTrace();
			calculated = true;
		}
		return valueData.getValue();
	}


	
	
	
	/**
	 * {@summary this should be overridden to display the operation symbol (*,+,-.etc) or the value if the node is not an operation
	 */
	@Override
	public String toString() {
		return "" + getValue();
	}
	
	/**
	 * {@summary this should be overridden to display the data that the node stores} 
	 * @return
	 */
	public String getDataStr() {
		if (getValueData() == null) {
			return "" + getValue();
		}else{
			return getValueData().toString();
		}
	}


	public void setValueData(ValueNode valueNode) {
		this.valueData = valueNode;
	}
	
	
	
	
}
