package calculatorv2_basic_operations;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;
import calculatorv2_core.ValueNode;
import calculatorv2_scientific_operations.ComplexValueNode;

public class SquareRoot extends One_subNode_node {
	
	public SquareRoot() {
		orderOfOpsLevel = 3;
	}
	
	protected double operation(double a) {
		if (Equation.printInProgress) System.out.println("sqrt" + a);
		return Math.sqrt(a);
	}
	
	
	
	public static ValueNode operationStat(ValueNode n1, ValueNode outputNode) {
		
		if (n1.getValue() < 0) {
			outputNode = new ComplexValueNode(0,Math.sqrt(n1.getValue()));
		}else {
			outputNode = new ValueNode(Math.sqrt(n1.getValue()));
		}
		
		return outputNode;
	}
	
	
	public ValueNode operation(ValueNode n1, ValueNode outputNode) {
		
		return operationStat(n1,outputNode);
	}

	public String toString() {
		return "sqrt";
	}
	
	public String getOperationKeyword() {
		return "sqrt";
	}
	
	public void test() { 
		Calculator.warn(getClass() + " is not tested and should not be used");
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new SquareRoot();
	}
}
