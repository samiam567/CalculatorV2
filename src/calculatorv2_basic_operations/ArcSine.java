package calculatorv2_basic_operations;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;

public class ArcSine extends One_subNode_node {
	Equation equation;
	
	public ArcSine(Equation equation) {
		this.equation = equation;
	}
	
	@Override
	protected double operation(double a) {
		if (Equation.printInProgress) System.out.println("asin" + a);
		
		if (equation.usingRadians()) {
			return Math.asin(a);
		}else {
			return 180 * Math.asin(a)/Math.PI;
		}
	}
	
	public String toString() {
		return "asin";
	}
	
	public String getOperationKeyword() {
		return "asin";
	}
	
	public void test() { 
		Calculator.warn(getClass() + " is not tested and should not be used");
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new ArcSine(eq);
	}
}
