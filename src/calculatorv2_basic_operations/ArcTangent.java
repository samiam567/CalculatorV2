package calculatorv2_basic_operations;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;
import calculatorv2_scientific_operations.ScientificOperationsList;

public class ArcTangent extends One_subNode_node {
	Equation equation;
	
	public ArcTangent(Equation equation) {
		this.equation = equation;
	}
	
	protected double operation(double a) {
		if (Equation.printInProgress) System.out.println("atan" + a);

		if (equation.usingRadians()) {
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
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(ScientificOperationsList.getOps());
		testEq.importAliases(ScientificOperationsList.getAliases());
		
		Calculator.testEquation(testEq, "atan(1/2)", 0.4636476090008061);
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new ArcTangent(eq);
	}
}