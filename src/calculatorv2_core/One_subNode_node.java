package calculatorv2_core;

public abstract class One_subNode_node extends EquationNode {

	private EquationNode subNode;

	public One_subNode_node() {
		orderOfOpsLevel = 4;
	}

	public EquationNode getSubNode() {
		subNode.getValue();
		return subNode;
	}

	public void setSubNode(EquationNode sub) {
		subNode = sub;
		subNode.setParent(this);
		notCalculated();
	}

	@Override
	public double getValue() {
		if (!isCalculated()) {
			calculated();
			setValueData(operation(getSubNode().getValueData(),valueData));
		}

		return valueData.getValue();
	}

	protected abstract double operation(double a);
	
	/**
	 * 
	 * @param nodeA the value to calculate with
	 * @param outputNode will output to this node and return it if new node creation is not necessary
	 * @return
	 */
	protected ValueNode operation(ValueNode nodeA, ValueNode outputNode) {
		if (nodeA instanceof AdvancedValueNode) Equation.warn(getClass() + " has no implementation for generic ValueNode");
		outputNode.setValue(operation(nodeA.getValue())); // do operation normally and assign value to our ValueNode
		return outputNode;
	}

}
