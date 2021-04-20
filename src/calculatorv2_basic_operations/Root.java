package calculatorv2_basic_operations;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.Two_subNode_node;

public class Root extends Two_subNode_node {
	
	public Root() {
		orderOfOpsLevel = 3;
	}
	
	protected double operation(double a, double b) {
		if (Equation.printInProgress) System.out.println(a + "rt" + b);
		return Math.pow(b, 1/a);
	}
	
	public String toString() {
		return "rt";
	}
	
	public String getOperationKeyword() {
		return "rt";
	}
	
	public void test() { 
		Calculator.warn(getClass() + " is not tested and should not be used");
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Root();
	}
}