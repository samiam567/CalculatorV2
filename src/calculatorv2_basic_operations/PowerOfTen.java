package calculatorv2_basic_operations;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.Two_subNode_node;

public class PowerOfTen extends Two_subNode_node {

	public PowerOfTen() {
		orderOfOpsLevel = 5;
	}
	
	@Override
	protected double operation(double a, double b) {
		return a*Math.pow(10,b);
	}
	

	
	public String toString() {
		return "E";
	}
	
	public String getOperationKeyword() {
		return "timesTenToThe";
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new PowerOfTen();
	}

	public void test() { 
		
		Equation testEq = new Equation();
		testEq.importAll();
		
	
		
		boolean prevOutputEnable = Calculator.enableJFrameOutput;
		Calculator.enableJFrameOutput = false;
	
		
		Calculator.testEquation(testEq,"1E-3",0.001);
		Calculator.testEquation(testEq,"1E3",1000);
		
		Calculator.enableJFrameOutput = prevOutputEnable;
	}
}
