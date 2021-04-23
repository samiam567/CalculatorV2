package calculatorv2_circuit_math;

import calculatorv2_basic_operations.Addition;
import calculatorv2_basic_operations.Division;
import calculatorv2_core.Calculator;
import calculatorv2_core.Commands;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.ValueNode;
import calculatorv2_scientific_operations.ComplexValueNode;
import calculatorv2_scientific_operations.FunctionNode;

public class ParallelImpedanceAdd extends FunctionNode {

	@Override
	public ValueNode function(EquationNode[] params, ValueNode outputNode) {
		
		if (params.length < 2) {
			Calculator.warn(getParameterInputs());
		}
		// add up 1 / each impedance
		
		ValueNode divisionResult = new ValueNode(0);
		ValueNode additionResult = new ValueNode(0);
		ValueNode one = new ValueNode(1);
		for (int i = 0; i < params.length; i++) {
			one.setValue(1); //just in case this gets altered at any point
			divisionResult = new Division().operation(one,params[i].getValueData(),new ValueNode(0));	
			additionResult = new Addition().operation(additionResult,divisionResult,new ValueNode(0));	
		}
		
		
		//do 1 / that sum
		one.setValue(1);
		outputNode = new Division().operation(one, additionResult,outputNode);
		
		return outputNode;
		
	}
	
	public String getParameterInputs() {
		return "parallel(impd1,impd2,...)";
	}
	
	@Override
	public String getOperationKeyword() {
		return "parallImpedanceAdd";
	}
	
	@Override
	public void test() { 
		
		Equation testEq = new Equation();
		testEq.importAll();
		
		testEq.useRadiansNotDegrees = false;
		
		boolean prevOutputEnable = Calculator.enableJFrameOutput;
		Calculator.enableJFrameOutput = false;	
		Calculator.testEquation(testEq,"parallel(1,2,3,4,5",0.43795620437956206);
		Calculator.testEquation(testEq,"parallel(_8.8*i,8)","4.380090498 + -3.981900452i",5.91952058716755);
		
		
		Commands.parseCommand("/V = exp(i×53.1)",testEq);
		Commands.parseCommand("/I = 1",testEq);
		Calculator.testEquation(testEq,"Round(getValue(V-0.8×i×I),1)",0.6);
		
		Calculator.enableJFrameOutput = prevOutputEnable;
	}
	
	@Override
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new ParallelImpedanceAdd();
	}
}
