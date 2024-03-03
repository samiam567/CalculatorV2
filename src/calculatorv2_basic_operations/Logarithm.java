package calculatorv2_basic_operations;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.FunctionNode;
import calculatorv2_core.ValueNode;
import calculatorv2_matrix_core.MatrixOperationsList;
import calculatorv2_scientific_operations.ScientificOperationsList;

public class Logarithm extends FunctionNode {
	
	private static Natural_logarithm logCalc = new Natural_logarithm();
	private static Division divCalc = new Division();
	
	public String getOperationKeyword() {
		return "log";
	}
	
	public void test() { 
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(ScientificOperationsList.getOps());
		testEq.importAliases(ScientificOperationsList.getAliases());
		testEq.importOperations(MatrixOperationsList.getOps());
		testEq.importAliases(MatrixOperationsList.getAliases());
		
		Calculator.testEquation(testEq, "log(2 , 1 )", 0);
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
