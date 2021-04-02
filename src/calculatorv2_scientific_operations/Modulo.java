package calculatorv2_scientific_operations;

import calculatorv2_core.Two_subNode_node;

public class Modulo extends Two_subNode_node {
	
	public Modulo() {
		orderOfOpsLevel = 2;
	}
	
	@Override
	public double operation(double a, double b) {
		System.out.println("operation");
		return a % b;
	}
	
	public String toString() {
		return "%";
	}
}
