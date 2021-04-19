package calculatorv2_basic_operations;

import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;

public class ArcCosine extends One_subNode_node {
	Equation equation;
	
	public ArcCosine(Equation equation) {
		this.equation = equation;
	}
	
	protected double operation(double a) {
		if (Equation.printInProgress) System.out.println("acos" + a);
		
		if (equation.useRadiansNotDegrees) {
			return Math.acos(a);
		}else {
			return 180 * Math.acos(a)/Math.PI;
		}
	}
	
	public String toString() {
		return "acos";
	}
	
	public String getOperationKeyword() {
		return "acos";
	}
	
	public void test() { 
		Equation.warn(getClass() + " is not tested and should not be used");
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new ArcCosine(eq);
	}
}