package calculatorv2_basic_operations;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;
import calculatorv2_scientific_operations.ScientificOperationsList;

public class ArcCosine extends One_subNode_node {
	Equation equation;
	
	public ArcCosine(Equation equation) {
		this.equation = equation;
	}
	
	protected double operation(double a) {
		if (Equation.printInProgress) System.out.println("acos" + a);
		
		if (equation.usingRadians()) {
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
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(ScientificOperationsList.getOps());
		testEq.importAliases(ScientificOperationsList.getAliases());
		
		Calculator.testEquation(testEq, "acos(1/2)", 1.0471975511965979);
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new ArcCosine(eq);
	}
}