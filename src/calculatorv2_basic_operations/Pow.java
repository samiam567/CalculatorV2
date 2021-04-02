package calculatorv2_basic_operations;

import calculatorv2_core.Equation;
import calculatorv2_core.Two_subNode_node;

public class Pow extends Two_subNode_node {
	
	public Pow() {
		orderOfOpsLevel = 3;
	}
	
	protected double operation(double a, double b) {
		if (Equation.printInProgress) System.out.println(a + "^" + b);
		return Math.pow(a,b);
	}
	
	public String toString() {
		return "^";
	}
}

