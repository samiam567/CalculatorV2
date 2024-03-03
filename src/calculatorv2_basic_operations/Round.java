package calculatorv2_basic_operations;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.FunctionNode;
import calculatorv2_core.ValueNode;
import calculatorv2_matrix_core.Bra;
import calculatorv2_matrix_core.Matrixable;
import calculatorv2_scientific_operations.ScientificOperationsList;

//round(value,numDecimals) or round(value) 
public class Round extends FunctionNode {
	
	private ValueNode round(ValueNode value, int precision, ValueNode outputNode) {
		if (value instanceof Matrixable) {
			if (! (outputNode instanceof Matrixable)) outputNode = new Bra();
			((Matrixable) outputNode).setValues(((Matrixable) value).getValues());
			
			ValueNode indValue;
			for (int i = 0; i < ((Matrixable) value).getValues().length; i++) {
				indValue = ((Matrixable) value).getValues()[i];
				((Matrixable) outputNode).getValues()[i] = round(indValue,precision,((Matrixable) outputNode).getValues()[i]);
			}
		}else {
			outputNode.setValue(Math.round( Math.pow(10,precision) * value.getValue() )  / Math.pow(10,precision));
		}
		
		return outputNode;
	}
	
	@Override
	public ValueNode function(EquationNode[] params, ValueNode outputNode) {
		Calculator.Assert(params.length <= 2, "Round can take a max of two parameters. Throwing away extra params");
		if (! Calculator.Assert(params.length != 0, "Round must have at least one parameter")) return outputNode;
		
		
		if (params.length == 2) {
			outputNode = round(params[0].getValueData(),(int) Math.round(params[1].getValue()),outputNode);
		}else {
			outputNode = round(params[0].getValueData(),0,outputNode);
		}
				return outputNode;
	}
	
	public String getOperationKeyword() {
		return "round";
	}
	
	public void test() { 
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(ScientificOperationsList.getOps());
		testEq.importAliases(ScientificOperationsList.getAliases());
		
		Calculator.testEquation(testEq, "round(pi)", 3);
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Round();
	}
	
}
