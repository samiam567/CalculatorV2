package calculatorv2_scientific_operations;

import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;
import calculatorv2_core.ValueNode;
import calculatorv2_matrix_core.Matrixable;

public class FunctionNode extends One_subNode_node {
	

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
	
	
	public ValueNode function(EquationNode[] params, ValueNode outputNode) {
		Equation.warn("Function method was not overriden for child of FunctionNode " + getClass() );
		return null;
	}
	
	public String getParameterInputs() {
		return "not defined";
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
			Equation.warn(getParameterInputs());
		}
		return function(params,outputNode);
	}
}
