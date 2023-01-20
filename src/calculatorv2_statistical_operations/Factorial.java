package calculatorv2_statistical_operations;

import calculatorv2_basic_operations.BasicOpsList;
import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;

public class Factorial extends One_subNode_node {
	@Override
	public String getOperationKeyword() {
		// TODO Auto-generated method stub
		return "fact";
	}

	@Override
	public void test() {
		
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(StatisticalOperationsList.getOps());
		
		Calculator.testEquation(testEq,"fact(3)",6);
		
		Calculator.testEquation(testEq,"fact(6)",720);
		Calculator.testEquation(testEq,"fact(1)",1);
		Calculator.testEquation(testEq,"fact(0)",1);

	}

	@Override
	public EquationNode createNewInstanceOfOperation(Equation eq) {
	
		return new Factorial();
	}

	@Override
	protected double operation(double a) {
		if (a < 0) {
			Calculator.error("factorial input must be >= 0");
		}
		double ans = 1;
		for (; a > 1; a--) {
			ans = ans * a;
		}
		return ans;
	}

}
