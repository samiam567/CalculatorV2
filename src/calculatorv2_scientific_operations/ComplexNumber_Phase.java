package calculatorv2_scientific_operations;

import calculatorv2_core.AdvancedValueNode;
import calculatorv2_core.Calculator;
import calculatorv2_core.Commands;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;
import calculatorv2_core.ValueNode;

public class ComplexNumber_Phase extends One_subNode_node {
	
	Equation eq;
	
	public ComplexNumber_Phase(Equation eq) {
		this.eq = eq;
	}
	
	@Override
	protected double operation(double a) {
		return a >= 0 ? 0 : 180;
	}
	
	protected ValueNode operation(ValueNode nodeA, ValueNode outputNode) {
		if (nodeA instanceof AdvancedValueNode) {
			
			if (nodeA instanceof ComplexValueNode) {
				outputNode.setValue( Math.atan2(((ComplexValueNode) nodeA).getComplex(),((ComplexValueNode) nodeA).getReal())  *  (eq.useRadiansNotDegrees ? 1 : 180/Math.PI)  );
			}else {
				Calculator.warn(getClass() + " has no implementation for generic ValueNode");
				outputNode.setValue(operation(nodeA.getValue())); // do operation normally and assign value to our ValueNode
			}
		}else {
			outputNode.setValue(operation(nodeA.getValue())); // do operation normally and assign value to our ValueNode
		}
		return outputNode;
	}
	
	
	@Override
	public String getOperationKeyword() {
		return "cPhase";
	}

	@Override
	public void test() {		
		Equation testEq = new Equation();
		testEq.importAll();
		

		testEq.useRadiansNotDegrees = false;
		
		boolean prevOutputEnable = Calculator.enableJFrameOutput;
		Calculator.enableJFrameOutput = false;
		
		Calculator.testEquation(testEq,"cPhase(1+i)",45);
		Calculator.testEquation(testEq,"cPhase(2+2*i)",45);
		Calculator.testEquation(testEq,"cPhase(1-i)",-45);
		Calculator.testEquation(testEq,"cPhase(3+3*i)",45);
		

		
		Commands.parseCommand("/n = 3+4×i",testEq);
		Calculator.testEquation(testEq,"cPhase(n×n×n)",159.39030706246794);
		
		testEq.useRadiansNotDegrees = true;
		
		Calculator.testEquation(testEq,"cPhase(1+i)",Math.PI/4);
		Calculator.testEquation(testEq,"cPhase(_1-i)",-3*Math.PI/4);
		
		
		
		
		
		
		
		Calculator.enableJFrameOutput = prevOutputEnable;
	}

	@Override
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new ComplexNumber_Phase(eq);
	}

	
	
}
