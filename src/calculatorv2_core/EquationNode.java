package calculatorv2_core;

/**
 * @author apun1
 *
 */
public abstract class EquationNode extends EquationObject implements Operation {
	
	
	private int parenthesisLevel;
	protected int orderOfOpsLevel;
	private EquationNode parent;
	public boolean supressWarnings = false;

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
	
	private static class PrintEquationBuilder {
		private String equation = "";
		private int parenthesisLevel = 0;
		
		public PrintEquationBuilder(EquationNode node) {
			build(node);
		}
		
		private void addNodeString(EquationNode node) {
			if (node.getParenthesisLevel() != parenthesisLevel) {
				if (node.getParenthesisLevel() > parenthesisLevel) { 
					while (parenthesisLevel < node.getParenthesisLevel()) {
						equation += "(";
						parenthesisLevel++;
					}
				}else {		
					while (parenthesisLevel > node.getParenthesisLevel()) {
						equation += ")";
						parenthesisLevel--;
					}
				}
			}		
			if (node instanceof VariableNode) {
				equation += ((VariableNode)node).toString();
			
			}else {
				equation += node.getOperationKeyword();
			}		
		}
		
		private void build(EquationNode node) {
			if (node instanceof One_subNode_node) {
				if (!(node instanceof Equation)) addNodeString(node);
				build(((One_subNode_node) node).getSubNode());
			}else if (node instanceof Two_subNode_node) {
				build(((Two_subNode_node) node).getLeftSubNode());
				addNodeString(node);
				build(((Two_subNode_node) node).getRightSubNode());
			}else {
				addNodeString(node);
			}
		}
		public String getEquationString() {
			return equation;
		}
	}

	public String convertEquationToString() {
		return (new PrintEquationBuilder(this)).getEquationString();
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
