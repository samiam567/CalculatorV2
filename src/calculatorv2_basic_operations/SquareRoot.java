package calculatorv2_basic_operations;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;

public class SquareRoot extends One_subNode_node {
	
	public SquareRoot() {
		orderOfOpsLevel = 3;
	}
	
	protected double operation(double a) {
		if (Equation.printInProgress) System.out.println("sqrt" + a);
		return Math.sqrt(a);
	}
	
	public String toString() {
		return "sqrt";
	}
	
	public String getOperationKeyword() {
		return "sqrt";
	}
	
	public void test() { 
		Calculator.warn(getClass() + " is not tested and should not be used");
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new SquareRoot();
	}
}
