package calculatorv2_basic_operations;

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
}
