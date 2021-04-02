package calculatorv2_scientific_operations;

import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.ValueNode;

// percentError(experimental, theoretical)
public class PercentError extends FunctionNode {
	
	public ValueNode function(EquationNode[] params, ValueNode outputNode) {
		if (! Equation.Assert(params.length == 2, "Must have 2 and only 2 parameters for percent error.")) return new ValueNode(-1);	
		double experimental = params[0].getValue();
		double actual = params[1].getValue();
		
		outputNode.setValue((experimental-actual)/actual * 100);
		
		return outputNode;
	}
	
	public String toString() {
		return "percentError";
	}
}
