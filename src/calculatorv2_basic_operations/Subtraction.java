package calculatorv2_basic_operations;

import calculatorv2_core.AdvancedValueNode;
import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.Two_subNode_node;
import calculatorv2_core.ValueNode;
import calculatorv2_scientific_operations.ComplexValueNode;
import calculatorv2_scientific_operations.ScientificOperationsList;

public class Subtraction extends Two_subNode_node {
	
	public Subtraction() {
		orderOfOpsLevel = 1;
	}
	
	protected double operation(double a, double b) {
		if (Equation.printInProgress) System.out.println(a + "-" + b);
		return a-b;
	}
	
	public String toString() {
		return "-";
	}
	
	
	// class-specific advanced operations
	
		@Override
		public ValueNode operation(ValueNode n1, ValueNode n2, ValueNode outputNode) {
				
			if ( (n1 instanceof AdvancedValueNode && ( (AdvancedValueNode) n1).needsSpecialOperationConditions) || (n2 instanceof AdvancedValueNode && ( (AdvancedValueNode) n2).needsSpecialOperationConditions) )	{
				
				if (n1 instanceof ComplexValueNode && n2 instanceof ComplexValueNode) { 
					// both complex numbers
					if (! (outputNode instanceof ComplexValueNode) ) outputNode = new ComplexValueNode();
					((ComplexValueNode) outputNode).setValues(((ComplexValueNode) n1).getReal() - ((ComplexValueNode) n2).getReal(), ((ComplexValueNode) n1).getComplex() - ((ComplexValueNode) n2).getComplex());
				}else if (n1 instanceof ComplexValueNode) { 
					// only n1 Complex Number
					if (! (outputNode instanceof ComplexValueNode) ) outputNode = new ComplexValueNode();
					((ComplexValueNode) outputNode).setValues(((ComplexValueNode) n1).getReal() - (n2).getValue(), ((ComplexValueNode) n1).getComplex());
				}else if (n2 instanceof ComplexValueNode) { 
					// only n2 complex number
					if (! (outputNode instanceof ComplexValueNode) ) outputNode = new ComplexValueNode();
					((ComplexValueNode) outputNode).setValues(n1.getValue() - ((ComplexValueNode) n2).getReal(), -((ComplexValueNode) n2).getComplex());
				}else {
					Calculator.warn("WARNING: class " + getClass() + " has no implementation for AdvancedValueNodes of class " + n1.getClass() + " and " + n2.getClass());
					outputNode.setValue(operation(n1.getValue(),n2.getValue()));
				}
				
			
			}else { //they are just normal values
				outputNode.setValue(operation(n1.getValue(),n2.getValue()));
			}
			
			return outputNode;
		}
		
		public String getOperationKeyword() {
			return "-";
		}
		
		public void test() { 
			Equation testEq = new Equation();
			testEq.importOperations(BasicOpsList.getOps());
			testEq.importOperations(ScientificOperationsList.getOps());
			testEq.importAliases(ScientificOperationsList.getAliases());
			
			Calculator.testEquation(testEq, "4-2", 2);
		}
		
		public EquationNode createNewInstanceOfOperation(Equation eq) {
			return new Subtraction();
		}
		
}