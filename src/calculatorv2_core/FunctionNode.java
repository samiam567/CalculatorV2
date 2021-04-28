package calculatorv2_core;

import calculatorv2_matrix_core.Matrixable;

public abstract class FunctionNode extends One_subNode_node {
	

	private EquationNode[] getParams() {
		EquationNode[] params;
		if (getSubNode().getValueData() instanceof Matrixable) {
			params = ((Matrixable) getSubNode().getValueData()).getValues();
		}else {
			params = new EquationNode[] {getSubNode().getValueData()};
		}
		return params;
	}
	
	protected double operation(double a) {
		EquationNode[] params = getParams();
		
		return function(params, new ValueNode(0)).getValue();
	}
	

	public String getEquation() {
		return "This function has no registered equation";
	}
	
	
	public abstract ValueNode function(EquationNode[] params, ValueNode outputNode);
	
	public String getParameterInputs() {
		return "This function has no registered parameter codex";
	}
	
	/**
	 * 
	 * @param nodeA the value to calculate with
	 * @param outputNode will output to this node and return it if new node creation is not necessary
	 * @return
	 */
	protected ValueNode operation(ValueNode nodeA, ValueNode outputNode) {
		EquationNode[] params = getParams();
		
		if (params.length == 0) {
			Calculator.warn(getParameterInputs());
		}else if (params[0] instanceof StringValueNode) {
			String str = ((StringValueNode) params[0]).getString();
			if (str.equals("params") || str.equals("equation") || str.equals("help")) {
				return new StringValueNode(getParameterInputs() + "  ,   " + getEquation());
			}
			
		}
		return function(params,outputNode);
	}
}
