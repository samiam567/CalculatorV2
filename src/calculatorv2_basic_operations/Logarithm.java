package calculatorv2_basic_operations;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.FunctionNode;
import calculatorv2_core.ValueNode;

public class Logarithm extends FunctionNode {
	
	private static Natural_logarithm logCalc = new Natural_logarithm();
	private static Division divCalc = new Division();
	
	public String getOperationKeyword() {
		return "log";
	}
	
	public void test() { 
		Calculator.warn(getClass() + " is not tested and should not be used");
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Logarithm();
	}
	
	@Override
	public ValueNode function(EquationNode[] values, ValueNode outputNode) {
		Calculator.Assert(values.length == 2, "log takes two parameters, base and value ");
		
		outputNode = divCalc.operation(logCalc.operation(values[1].getValueData(),new ValueNode(1)) , logCalc.operation(values[0].getValueData(),new ValueNode(1)), outputNode);
		return outputNode;
	}
		
	public String toString() {
		return "log";
	}
	
	
}
