package calculatorv2_core;

/**
 * @author apun1
 *
 */
public abstract class EquationNode {
	private int parenthesisLevel;
	protected int orderOfOpsLevel;
	private EquationNode parent;
	protected ValueNode valueData;
	
	private boolean calculated = false;
	
	public EquationNode() {
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
		
		if ((getParent() != null) ) parent.notCalculated(); //if we have a parent, they need to redo their calculation as well

	}
	
	public void setParent(EquationNode parent) {
	    notCalculated();
		this.parent = parent;
	}
	
	public EquationNode getParent() {
		return parent;
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


	public int getParenthesisLevel() {
		return parenthesisLevel;
	}

	protected void setParenthesisLevel(int parenthesisLevel) {
		this.parenthesisLevel = parenthesisLevel;
	}

	public int getOrderOfOpsLevel() {
		return orderOfOpsLevel;
	}
	
	public long getLevel() {
		return parenthesisLevel * Equation.operations.length + orderOfOpsLevel;
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
	
	
	public static boolean hasChildInTree(EquationNode nodeToCheck, EquationNode nodeToLookFor) {
		
		if (nodeToCheck.equals(nodeToLookFor)) {
			return true;
		}else {
			if (nodeToCheck instanceof One_subNode_node) {
				return hasChildInTree( ((One_subNode_node) nodeToCheck).getSubNode(), nodeToLookFor );
			}else if (nodeToCheck instanceof Two_subNode_node) {
				return hasChildInTree( ((Two_subNode_node) nodeToCheck).getLeftSubNode(), nodeToLookFor ) || hasChildInTree( ((Two_subNode_node) nodeToCheck).getRightSubNode(), nodeToLookFor );
			}
		
		}
		
		return false;
	}


}
