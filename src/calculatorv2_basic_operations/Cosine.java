package calculatorv2_basic_operations;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;
import calculatorv2_scientific_operations.ScientificOperationsList;

public class Cosine extends One_subNode_node {
	Equation equation;
	
	public Cosine(Equation equation) {
		this.equation = equation;
	}
	
	protected double operation(double a) {
		if (Equation.printInProgress) System.out.println("cos" + a);

		if (equation.usingRadians()) {
			return Math.cos(a);
		}else {
			return Math.cos(a*Math.PI / 180);
		}
	}
	
	public String toString() {
		return "cos";
	}
	
	public String getOperationKeyword() {
		return "cos";
	}
	
	public void test() { 
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(ScientificOperationsList.getOps());
		testEq.importAliases(ScientificOperationsList.getAliases());
		
		Calculator.testEquation(testEq, "cos(pi)", -1.0);
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Cosine(eq);
	}
	
}