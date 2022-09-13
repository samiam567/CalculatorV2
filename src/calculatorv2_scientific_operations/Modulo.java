package calculatorv2_scientific_operations;

import calculatorv2_basic_operations.BasicOpsList;
import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.Two_subNode_node;

public class Modulo extends Two_subNode_node {
	
	public Modulo() {
		orderOfOpsLevel = 2;
	}
	
	@Override
	public double operation(double a, double b) {
		System.out.println("running " + a + " modulo " + b);
		return a % b;
	}
	
	public String toString() {
		return "Modulo";
	}
	
	public String getOperationKeyword() {
		return "Modulo";
	}
	
	public void test() { 
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(ScientificOperationsList.getOps());
		testEq.importAliases(ScientificOperationsList.getAliases());
		
		Calculator.testEquation(testEq, "5 Modulo 2", 1);
		Calculator.testEquation(testEq,"5 mod 2",1);
		Calculator.testEquation(testEq,"4 % 2",0); 
		Calculator.testEquation(testEq,"4%2",0); 
		Calculator.testEquation(testEq,"5mod2",1);
		Calculator.testEquation(testEq,"100 mod (3^3)",19);
		Calculator.testEquation(testEq,"100 %3^3",19);
		
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Modulo();
	}
}
