package calculatorv2_basic_operations;

import calculatorv2_core.Equation;
import calculatorv2_core.One_subNode_node;

public class Sine extends One_subNode_node {
	Equation equation;
	
	public Sine(Equation equation) {
		this.equation = equation;
	}
	
	protected double operation(double a) {
		if (Equation.printInProgress) System.out.println("sin" + a);

		if (equation.useRadiansNotDegrees) {
			return Math.sin(a);
		}else {
			return Math.sin(a*Math.PI / 180);
		}
	}
	
	public String toString() {
		return "sin";
	}
}