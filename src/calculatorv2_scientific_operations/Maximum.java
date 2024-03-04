package calculatorv2_scientific_operations;

import calculatorv2_basic_operations.BasicOpsList;
import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.FunctionNode;
import calculatorv2_core.ValueNode;
import calculatorv2_matrix_core.MatrixOperationsList;

public class Maximum extends FunctionNode {
	
	CompareTo comp = new CompareTo();
	
	public ValueNode function(EquationNode[] params, ValueNode outputNode) {
		
		ValueNode maximum = new ValueNode(Double.MIN_VALUE);
		Comparation compResult = new Comparation();
		
		for (EquationNode param : params) {
			if (( (Comparation) comp.operation(param.getValueData(), maximum, compResult)).compareValue.equals(Comparation.ComparationValues.greater)) {
				maximum = param.getValueData();
			}
		}
		
		return maximum;
	}
	
	@Override
	public String getParameterInputs() {
		return "max(val1, val2, ..., valn)";
	}
	
	@Override
	public String getEquation() {
		return "returns the maximum of the passed arguments";
	}
	
	@Override
	public String getOperationKeyword() {
		return "max";
	}
	
	@Override
	public void test() { 
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(ScientificOperationsList.getOps());
		testEq.importAliases(ScientificOperationsList.getAliases());
		testEq.importOperations(MatrixOperationsList.getOps());
		testEq.importAliases(MatrixOperationsList.getAliases());
		
		Calculator.testEquation(testEq, "max(4,3,67,2,54,_1)", 67);
		Calculator.testEquation(testEq, "max(4,3,67,34534,2,54)", 34534);
	}
	
	@Override
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Maximum();
	}

}

