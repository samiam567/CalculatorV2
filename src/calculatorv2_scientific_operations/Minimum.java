package calculatorv2_scientific_operations;

import calculatorv2_basic_operations.BasicOpsList;
import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.FunctionNode;
import calculatorv2_core.ValueNode;
import calculatorv2_matrix_core.MatrixOperationsList;

public class Minimum extends FunctionNode {
	
	CompareTo comp = new CompareTo();
	
	public ValueNode function(EquationNode[] params, ValueNode outputNode) {
		
		ValueNode minumum = new ValueNode(Double.MAX_VALUE);
		Comparation compResult = new Comparation();
		
		for (EquationNode param : params) {
			if (( (Comparation) comp.operation(param.getValueData(), minumum, compResult)).compareValue.equals(Comparation.ComparationValues.less)) {
				minumum = param.getValueData();
			}
		}
		
		return minumum;
	}
	
	@Override
	public String getParameterInputs() {
		return "min(val1, val2, ..., valn)";
	}
	
	@Override
	public String getEquation() {
		return "returns the minimum of the passed arguments";
	}
	
	@Override
	public String getOperationKeyword() {
		return "min";
	}
	
	@Override
	public void test() { 
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(ScientificOperationsList.getOps());
		testEq.importAliases(ScientificOperationsList.getAliases());
		testEq.importOperations(MatrixOperationsList.getOps());
		testEq.importAliases(MatrixOperationsList.getAliases());
		
		Calculator.testEquation(testEq, "min(4,3,67,2,54,_1)", -1);
		Calculator.testEquation(testEq, "min(4,3,67,2,54)", 2);
	}
	
	@Override
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Minimum();
	}

}

