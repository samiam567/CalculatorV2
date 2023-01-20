package calculatorv2_statistical_operations;

import calculatorv2_basic_operations.BasicOpsList;
import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.Two_subNode_node;

public class Combination extends Two_subNode_node {
	private Factorial f = new Factorial();
	
	@Override
	public String getOperationKeyword() {
		return "C";
	}

	@Override
	public void test() {
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(StatisticalOperationsList.getOps());
		
		Calculator.testEquation(testEq,"10C3",120);
		Calculator.testEquation(testEq,"5C2",10);

	}

	@Override
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Combination();
	}

	@Override
	protected double operation(double n, double r) {
		if (n < r || r < 0) {
			Calculator.error("combination input n must be >= r >= 0");
		}
		return f.operation(n) / (f.operation(n-r) * f.operation(r));
	}

}
