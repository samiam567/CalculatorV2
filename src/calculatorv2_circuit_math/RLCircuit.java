package calculatorv2_circuit_math;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.FunctionNode;
import calculatorv2_core.ValueNode;

public class RLCircuit extends FunctionNode {
	public ValueNode function(EquationNode[] params, ValueNode outputNode) {
		
		if (params.length < 5) {
			Calculator.warn(getParameterInputs());
		}
		double R = params[0].getValue();
		double L = params[1].getValue();
		double Ii = params[2].getValue();
		double If = params[3].getValue();
		double t = params[4].getValue();
		return new ValueNode(Ii*Math.exp(-R*t/L) + If*(1-Math.exp(-R*t/L)));
	}
	
	@Override
	public String getParameterInputs() {
		return "IL(t) = RLCircuit(R,L, Ii, If, t)";
	}
	
	public String getEquation() {
		return "Ii*e^(-Rt/L) + If*(1-e^(-Rt/L))";
	}
	
	@Override
	public String getOperationKeyword() {
		return "RLCircuit";
	}
	
	@Override
	public void test() { 
//		Calculator.warn(getClass() + " is not tested and should not be used");
	}
	
	@Override
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new RLCircuit();
	}

}
