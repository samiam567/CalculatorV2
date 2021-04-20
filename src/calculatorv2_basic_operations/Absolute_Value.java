package calculatorv2_basic_operations;

import calculatorv2_core.AdvancedValueNode;
import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;
import calculatorv2_core.ValueNode;
import calculatorv2_scientific_operations.ComplexValueNode;

public class Absolute_Value extends One_subNode_node {
	
	public Absolute_Value() {
		orderOfOpsLevel = 4;
	}
	
	protected double operation(double a) {
		if (Equation.printInProgress) System.out.println("|" + a + "|");
		return Math.abs(a);
	}
	
	public String toString() {
		return "abs";
	}
	
	// class-specific advanced operations
	
	@Override
	protected ValueNode operation(ValueNode n1, ValueNode outputNode) {
		
		if (n1 instanceof AdvancedValueNode) {
			if (n1 instanceof ComplexValueNode) { 
				// complex number
				if (! (outputNode instanceof ComplexValueNode) ) outputNode = new ComplexValueNode();
				((ComplexValueNode) outputNode).setValues(operation(((ComplexValueNode) n1).getReal()), operation(((ComplexValueNode) n1).getComplex()));
			}else {
				Calculator.warn("WARNING: class " + getClass() + " has no implementation for AdvancedValueNodes of class " + n1.getClass());
				outputNode.setValue(operation(n1.getValue()));
			}
		}else {
			outputNode.setValue(operation(n1.getValue()));
		}
		
		return outputNode;
		
	}
	
	
	public String getOperationKeyword() {
		return "abs";
	}
	
	public void test() { 
		Calculator.warn(getClass() + " is not tested and should not be used");
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Absolute_Value();
	}
}