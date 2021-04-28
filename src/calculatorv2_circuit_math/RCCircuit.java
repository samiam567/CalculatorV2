package calculatorv2_circuit_math;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.FunctionNode;
import calculatorv2_core.ValueNode;

public class RCCircuit extends FunctionNode {
	public ValueNode function(EquationNode[] params, ValueNode outputNode) {
		
		if (params.length < 5) {
			Calculator.warn(getParameterInputs());
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
	
	@Override
	public String getOperationKeyword() {
		return "RCCircuit";
	}
	
	@Override
	public void test() { 
		Calculator.warn(getClass() + " is not tested and should not be used");
	}
	
	@Override
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new RCCircuit();
	}

}
