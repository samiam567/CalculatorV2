package calculatorv2_basic_operations;

import calculatorv2_core.AdvancedValueNode;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;
import calculatorv2_core.ValueNode;
import calculatorv2_scientific_operations.ComplexValueNode;

public class Negative extends One_subNode_node {
	
	
	public Negative(Equation eq) {
		if (eq.cMode.equals("numberInput")) {
			orderOfOpsLevel = 6;
		}
	}
	

	
	protected double operation(double a) {
		if (Equation.printInProgress) System.out.println("_" + a);
		return 0-a;
	}
	
	public String toString() {
		return "_";
	}
	
	
	// class-specific advanced operations
	
	@Override
	protected ValueNode operation(ValueNode n1, ValueNode outputNode) {
		
		if ( (n1 instanceof AdvancedValueNode && ( (AdvancedValueNode) n1).needsSpecialOperationConditions)) {
			if (n1 instanceof ComplexValueNode) { 
				// both complex numbers
				if (! (outputNode instanceof ComplexValueNode) ) outputNode = new ComplexValueNode();
				((ComplexValueNode) outputNode).setValues(operation(((ComplexValueNode) n1).getReal()), operation(((ComplexValueNode) n1).getComplex()));
			}else {
				Equation.warn("WARNING: class " + getClass() + " has no implementation for AdvancedValueNodes of class " + n1.getClass());
				outputNode.setValue(operation(n1.getValue()));
			}
		}else {
			outputNode.setValue(operation(n1.getValue()));
		}
		
		return outputNode;
		
	}	
	
	public String getOperationKeyword() {
		return "_";
	}
	
	public void test() { 
		Equation.warn(getClass() + " is not tested and should not be used");
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Negative(eq);
	}
	
}