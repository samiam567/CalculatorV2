package calculatorv2_basic_operations;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.Two_subNode_node;
import calculatorv2_scientific_operations.ScientificOperationsList;

public class Root extends Two_subNode_node {
	
	public Root() {
		orderOfOpsLevel = 3;
	}
	
	protected double operation(double a, double b) {
		return Math.pow(b, 1/a);
	}
	
	public String toString() {
		return "rt";
	}
	
	public String getOperationKeyword() {
		return "rt";
	}
	
	public void test() { 
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(ScientificOperationsList.getOps());
		testEq.importAliases(ScientificOperationsList.getAliases());
		
		Calculator.testEquation(testEq, "2rt4", 2);
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Root();
	}
}