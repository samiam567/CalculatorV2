package calculatorv2_core;

/**
 * @author apun1
 *
 */
public abstract class EquationNode extends EquationObject implements Operation {
	
	
	private int parenthesisLevel;
	protected int orderOfOpsLevel;
	private EquationNode parent;
	

	@Override
	protected void notCalculated() {
		super.notCalculated();
		if ((getParent() != null) ) parent.notCalculated(); //if we have a parent, they need to redo their calculation as well
	}
	
	public void setParent(EquationNode parent) {
	    notCalculated();
		this.parent = parent;
	}
	
	public EquationNode getParent() {
		return parent;
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
