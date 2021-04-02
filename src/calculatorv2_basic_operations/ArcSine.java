package calculatorv2_basic_operations;

import calculatorv2_core.Equation;
import calculatorv2_core.One_subNode_node;

public class ArcSine extends One_subNode_node {
	Equation equation;
	
	public ArcSine(Equation equation) {
		this.equation = equation;
	}
	
	@Override
	protected double operation(double a) {
		if (Equation.printInProgress) System.out.println("asin" + a);
		
		if (equation.useRadiansNotDegrees) {
			return Math.asin(a);
		}else {
			return 180 * Math.asin(a)/Math.PI;
		}
	}
	
	public String toString() {
		return "asin";
	}
}
