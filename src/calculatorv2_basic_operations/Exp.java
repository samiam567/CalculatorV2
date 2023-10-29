package calculatorv2_basic_operations;

import calculatorv2_core.AdvancedValueNode;
import calculatorv2_core.Calculator;
import calculatorv2_core.Commands;
import calculatorv2_core.Equation;
import calculatorv2_core.Equation.DegOrRadValue;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;
import calculatorv2_core.ValueNode;
import calculatorv2_scientific_operations.ComplexValueNode;

public class Exp extends One_subNode_node {
	
	Equation eq; 
	
	public Exp(Equation eq) {
		this.eq = eq;
	}
	
	protected double operation(double a) {
		return Math.exp(a);
	}
	
	public String toString() {
		return "e^";
	}
	
	
	// class-specific advanced operations
	
	@Override
	protected ValueNode operation(ValueNode n1, ValueNode outputNode) {
		
		if ( (n1 instanceof AdvancedValueNode && ( (AdvancedValueNode) n1).needsSpecialOperationConditions)) {
			if (n1 instanceof ComplexValueNode) { 
				
				ComplexValueNode N1 = (ComplexValueNode) n1;
				
				// both complex numbers
				if (! (outputNode instanceof ComplexValueNode) ) outputNode = new ComplexValueNode();
				
				double degRadMult = eq.usingRadians() ? 1 : Math.PI/180;
				
				ComplexValueNode eulerExpans = new ComplexValueNode(Math.cos(N1.getComplex() * degRadMult),Math.sin(N1.getComplex() * degRadMult));
				
				
				
				Multiplication multi = new Multiplication();
				
				outputNode = multi.operation(new ValueNode(Math.exp(N1.getReal())),eulerExpans,outputNode);			
			
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
		return "exp";
	}
	
	public void test() { 
		Equation testEq = new Equation();
		
		testEq.importAll();
		
		testEq.setDegRadMode(DegOrRadValue.degrees);
		
		boolean prevOutputEnable = Calculator.enableJFrameOutput;
		Calculator.enableJFrameOutput = false;
		
		

		
		
		Calculator.testEquation(testEq,"3*exp(90*i)","0 + 3i",1.8369701987210297E-16);
		Calculator.testEquation(testEq,"(3×exp(30×i))×(2×exp(_60×i))","5.196152423 + -3i",5.196152422706632);
		
		Commands.parseCommand("/V = exp(i×53.1)",testEq);
		Commands.parseCommand("/I = 1",testEq);
		Calculator.testEquation(testEq,"Round(getValue(V-0.8×i×I),1)",0.6);
		
		Calculator.enableJFrameOutput = prevOutputEnable;
		
	
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Exp(eq);
	}
	
}
