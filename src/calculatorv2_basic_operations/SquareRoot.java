package calculatorv2_basic_operations;

import calculatorv2_core.Equation;
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
}
