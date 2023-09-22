package calculatorv2_statistical_operations;

import calculatorv2_basic_operations.BasicOpsList;
import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.Two_subNode_node;

public class Permutation extends Two_subNode_node {

	private Factorial f = new Factorial();
	
	public Permutation() {
		orderOfOpsLevel = 5;
	}
	
	public Permutation(Equation eq) {
		orderOfOpsLevel = 5;
	}
	
	@Override
	public String getOperationKeyword() {
		return "P";
	}

	@Override
	public void test() {
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(StatisticalOperationsList.getOps());
		
		Calculator.testEquation(testEq,"10P3",720);
		Calculator.testEquation(testEq,"5P2",20);
		Calculator.testEquation(testEq,"5P2*2",40);
		Calculator.testEquation(testEq,"2*5P2",40);

	}

	@Override
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Permutation();
	}

	@Override
	protected double operation(double n, double r) {
		if (n < r || r < 0) {
			Calculator.error("permutation input n must be >= r >= 0");
		}
		return f.operation(n) / f.operation(n-r);
	}

}
