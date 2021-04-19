package calculatorv2_basic_operations;

import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;

public class ArcTangent extends One_subNode_node {
	Equation equation;
	
	public ArcTangent(Equation equation) {
		this.equation = equation;
	}
	
	protected double operation(double a) {
		if (Equation.printInProgress) System.out.println("atan" + a);

		if (equation.useRadiansNotDegrees) {
			return Math.atan(a);
		}else {
			return 180 * Math.atan(a)/Math.PI;
		}
	}
	
	public String toString() {
		return "atan";
	}
	
	public String getOperationKeyword() {
		return "atan";
	}
	
	public void test() { 
		Equation.warn(getClass() + " is not tested and should not be used");
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new ArcTangent(eq);
	}
}