package calculatorv2_core;

/**
 * {@summary allows aliasing even when changing ValueNode types by alaiasing with this object}
 * used in MatrixNode (MatrixBra and MatrixKet)
 * @author apun1
 *
 */
public class NodeWrapper extends ValueNode {
	
	public NodeWrapper(ValueNode value) {
		super('k');
		super.setValueData(value);
	}
	
	
	public ValueNode getValueNodeChildOfWrapper() {
		return super.getValueData();
	}
	@Override
	public void setValue(double value) {
		valueData.setValue(value);
	}
	
	public void setValue(ValueNode value) {
		super.setValueData(value);
	}
	
	@Override
	public ValueNode getValueData() {
		return valueData.getValueData();
	}
	
	@Override
	public double getValue() {
		return valueData.getValue();
	}
		
	@Override
	public long getLevel() {
		return valueData.getLevel();
	}
	
	public boolean isCalculated() {
		return valueData.isCalculated();
	}
	
	protected void calculated() {
		valueData.calculated();
	}
	
	/**
	 * {@summary sets us to not calculated, and makes all parents of this node un-calculated as well}
	 */
	protected void notCalculated() {
		valueData.notCalculated();
	}
	
	public void setParent(EquationNode parent) {
		valueData.setParent(parent);
	}
	
	public EquationNode getParent() {
		return valueData.getParent();
	}

	public int getParenthesisLevel() {
		return valueData.getParenthesisLevel();
	}

	protected void setParenthesisLevel(int parenthesisLevel) {
		valueData.setParenthesisLevel(parenthesisLevel);
	}

	public int getOrderOfOpsLevel() {
		return valueData.getOrderOfOpsLevel();
	}


	public void setValueData(ValueNode valueNode) {
		valueData.setValueData(valueNode);
	}

}
