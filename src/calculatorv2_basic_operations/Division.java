package calculatorv2_basic_operations;

import calculatorv2_core.AdvancedValueNode;
import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.Two_subNode_node;
import calculatorv2_core.ValueNode;
import calculatorv2_scientific_operations.ComplexValueNode;
import calculatorv2_scientific_operations.ScientificOperationsList;

public class Division extends Two_subNode_node {
	
	public Division() {
		orderOfOpsLevel = 2;
	}
	
	
	protected double operation(double a, double b) {
		if (Equation.printInProgress) System.out.println(a + "/" + b);
		return a/b;
	}
	
	public String toString() {
		return "/";
	}
	
	
	
	public ValueNode operation(ValueNode n1, ValueNode n2, ValueNode outputNode) {
	
		if ( (n1 instanceof AdvancedValueNode && ( (AdvancedValueNode) n1).needsSpecialOperationConditions) || (n2 instanceof AdvancedValueNode && ( (AdvancedValueNode) n2).needsSpecialOperationConditions) )	{		
			
			/*if (n1 instanceof ComplexValueNode && n2 instanceof ComplexValueNode) { 
				// both complex numbers
				if (! (outputNode instanceof ComplexValueNode) ) outputNode = new ComplexValueNode();
				((ComplexValueNode) outputNode).setValues((((ComplexValueNode) n1).getReal()*((ComplexValueNode) n2).getReal()+((ComplexValueNode) n1).getImaginaryComponent()*((ComplexValueNode) n2).getImaginaryComponent())/(((ComplexValueNode) n2).getReal()*((ComplexValueNode) n2).getReal()+((ComplexValueNode) n2).getImaginaryComponent()*((ComplexValueNode) n2).getImaginaryComponent()), (((ComplexValueNode) n1).getImaginaryComponent()*((ComplexValueNode) n2).getReal()-((ComplexValueNode) n1).getReal()*((ComplexValueNode) n2).getImaginaryComponent())/(((ComplexValueNode) n2).getReal()*((ComplexValueNode) n2).getReal()+((ComplexValueNode) n2).getImaginaryComponent()*((ComplexValueNode) n2).getImaginaryComponent()));
			}else if (n1 instanceof ComplexValueNode) { 
				// only n1 Complex Number
				if (! (outputNode instanceof ComplexValueNode) ) outputNode = new ComplexValueNode();
				((ComplexValueNode) outputNode).setValues((((ComplexValueNode) n1).getReal()*(n2).getValue()+((ComplexValueNode) n1).getImaginaryComponent())/(( n2).getValue()*(n2).getValue()), (((ComplexValueNode) n1).getImaginaryComponent()*(n2).getValue()-((ComplexValueNode) n1).getReal())/( (n2).getValue()*(n2).getValue()));
				
			}else if (n2 instanceof ComplexValueNode) { 
				// only n2 complex number
				if (! (outputNode instanceof ComplexValueNode) ) outputNode = new ComplexValueNode(); //cast outputNode to the correct type 
				((ComplexValueNode) outputNode).setValues(((n1).getValue()*((ComplexValueNode) n2).getReal()+((ComplexValueNode) n2).getImaginaryComponent())/(((ComplexValueNode) n2).getReal()*((ComplexValueNode) n2).getReal()+((ComplexValueNode) n2).getImaginaryComponent()*((ComplexValueNode) n2).getImaginaryComponent()), (((ComplexValueNode) n2).getReal()-( n1).getValue()*((ComplexValueNode) n2).getImaginaryComponent())/(((ComplexValueNode) n2).getReal()*((ComplexValueNode) n2).getReal()+((ComplexValueNode) n2).getImaginaryComponent()*((ComplexValueNode) n2).getImaginaryComponent()));
			}*/
			
			if (n1 instanceof ComplexValueNode || n2 instanceof ComplexValueNode) {
				// (a + bi) * (c + di)
				double a,b,c,d;
				
				if (n1 instanceof ComplexValueNode) {
					ComplexValueNode N1 = (ComplexValueNode) n1;
					a = N1.getReal();
					b = N1.getComplex();
				}else {
					a = n1.getValue();
					b = 0;
				}
				
				if (n2 instanceof ComplexValueNode) {
					ComplexValueNode N2 = (ComplexValueNode) n2;
					c = N2.getReal();
					d = N2.getComplex();
				}else {
					c = n2.getValue();
					d = 0;
				}
				
				if (! (outputNode instanceof ComplexValueNode)) outputNode = new ComplexValueNode();
				
				((ComplexValueNode) outputNode).setValues( (a*c + b*d) / (c*c + d*d), (b*c-a*d) / (c*c+d*d));
				
			} else {
				Calculator.warn("class " + getClass() + " has no implementation for AdvancedValueNodes of class " + n1.getClass() + " and " + n2.getClass());
				outputNode.setValue(operation(n1.getValue(),n2.getValue()));
			}
			
		
		}else { //they are just normal values
			outputNode.setValue(operation(n1.getValue(),n2.getValue()));
		}
		
		return outputNode;
	}
	
	public String getOperationKeyword() {
		return "/";
	}
	
	public void test() { 
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(ScientificOperationsList.getOps());
		testEq.importAliases(ScientificOperationsList.getAliases());
		
		Calculator.testEquation(testEq, "1/2", 0.5);
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Division();
	}
	
}