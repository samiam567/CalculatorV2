package calculatorv2_basic_operations;

import calculatorv2_core.Calculator;
import calculatorv2_core.Commands;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;
import calculatorv2_core.ValueNode;

//Returns what getValue() returns when called on the operated valuenode
public class GetValue extends One_subNode_node {
	
	@Override
	protected double operation(double a) {
		return a;
	}
	
	@Override
	protected ValueNode operation(ValueNode n1, ValueNode outputNode) {
		outputNode = new ValueNode(n1.getValue());
		return outputNode;
	}
	
	
	@Override
	public String getOperationKeyword() {
		return "getValue";
	}
	
	@Override
	public void test() { 
	
		
		Equation testEq = new Equation();
		testEq.importAll();
		
		boolean prevOutputEnable = Calculator.enableJFrameOutput;
		Calculator.enableJFrameOutput = false;
		
		Calculator.testEquation(testEq,"getValue(5*i)",0);
		Calculator.testEquation(testEq,"getValue(7)",7);
		Commands.parseCommand("/n = 3+4×i",testEq);
		Calculator.testEquation(testEq,"getValue(n×n×n)",-117);

		
		
		
		Calculator.enableJFrameOutput = prevOutputEnable;
		
	}
	
	@Override
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new GetValue();
	}

	
}
