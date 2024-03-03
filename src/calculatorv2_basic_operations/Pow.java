package calculatorv2_basic_operations;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.Two_subNode_node;
import calculatorv2_scientific_operations.ScientificOperationsList;

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
	
	public String getOperationKeyword() {
		return "^";
	}
	
	public void test() { 
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(ScientificOperationsList.getOps());
		testEq.importAliases(ScientificOperationsList.getAliases());
		
		Calculator.testEquation(testEq, "4^5", 1024.0);
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Pow();
	}
}

