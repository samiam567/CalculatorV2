package calculatorv2_basic_operations;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;
import calculatorv2_scientific_operations.ScientificOperationsList;

public class Sine extends One_subNode_node {
	Equation equation;
	
	public Sine(Equation equation) {
		this.equation = equation;
	}
	
	protected double operation(double a) {
		if (Equation.printInProgress) System.out.println("sin" + a);

		if (equation.usingRadians()) {
			return Math.sin(a);
		}else {
			return Math.sin(a*Math.PI / 180);
		}
	}
	
	public String toString() {
		return "sin";
	}
	
	public String getOperationKeyword() {
		return "sin";
	}
	
	public void test() { 
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(ScientificOperationsList.getOps());
		testEq.importAliases(ScientificOperationsList.getAliases());
		
		Calculator.testEquation(testEq, "sin(pi)", 1.2246467991473532E-16);
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Sine(eq);
	}
}