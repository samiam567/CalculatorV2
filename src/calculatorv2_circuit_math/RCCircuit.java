package calculatorv2_circuit_math;

import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.ValueNode;
import calculatorv2_scientific_operations.FunctionNode;

public class RCCircuit extends FunctionNode {
	public ValueNode function(EquationNode[] params, ValueNode outputNode) {
		
		if (params.length < 5) {
			Equation.warn(getParameterInputs());
		}
		
		double R = params[0].getValue();
		double C = params[1].getValue();
		double Vi = params[2].getValue();
		double Vf = params[3].getValue();
		double t = params[4].getValue();
		return new ValueNode(Vf + (Vi-Vf) * Math.exp(-t/(R*C)));
	}
	
	@Override
	public String getParameterInputs() {
		return "Vc(t) = RCCircuit(R,C, Vi, Vf, t)";
	}
	
	public String getEquation() {
		return "Vc(t) = Vf + [Vi-Vf]*exp[-t/RC]";
	}
}
