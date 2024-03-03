package calculatorv2_basic_operations;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;
import calculatorv2_scientific_operations.ScientificOperationsList;

public class Tangent extends One_subNode_node {
	Equation equation;
	
	public Tangent(Equation equation) {
		this.equation = equation;
	}
	
	protected double operation(double a) {
		if (Equation.printInProgress) System.out.println("tan" + a);

		if (equation.usingRadians()) {
			return Math.tan(a);
		}else {
			return Math.tan(a*Math.PI / 180);
		}
	}
	
	public String toString() {
		return "tan";
	}
	
	public String getOperationKeyword() {
		return "tan";
	}
	
	public void test() { 
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(ScientificOperationsList.getOps());
		testEq.importAliases(ScientificOperationsList.getAliases());
		
		Calculator.testEquation(testEq, "tan(pi)", -1.2246467991473532E-16);
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Tangent(eq);
	}
	
}