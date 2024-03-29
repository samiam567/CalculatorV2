package calculatorv2_scientific_operations;

import calculatorv2_basic_operations.BasicOpsList;
import calculatorv2_core.AdvancedValueNode;
import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.Two_subNode_node;
import calculatorv2_core.ValueNode;
import calculatorv2_matrix_core.MatrixOperationsList;

public class IsEqualTo extends Two_subNode_node {
	public IsEqualTo() {
		orderOfOpsLevel = 0;
	}
	
	@Override
	protected double operation(double a, double b) {
		return a == b ? 1 : 0;
	}
	
	@Override
	public ValueNode operation(ValueNode n1, ValueNode n2, ValueNode outputNode) {
		if (! (outputNode instanceof Comparation)) outputNode = new Comparation();
		
		if ( (n1 instanceof AdvancedValueNode && ( (AdvancedValueNode) n1).needsSpecialOperationConditions) || (n2 instanceof AdvancedValueNode && ( (AdvancedValueNode) n2).needsSpecialOperationConditions) ) {
			Calculator.warn("class " + IsEqualTo.class + " has no implementation for AdvancedValueNodes of class " + n1.getClass() + " and " + n2.getClass());

			if (operation(n1.getValue(),n2.getValue()) == 1) {
				((Comparation) outputNode).setValue(Comparation.ComparationValues.True);
			}else {
				((Comparation) outputNode).setValue(Comparation.ComparationValues.False);
			}
		}else {
			if (operation(n1.getValue(),n2.getValue()) == 1) {
				((Comparation) outputNode).setValue(Comparation.ComparationValues.True);
			}else {
				((Comparation) outputNode).setValue(Comparation.ComparationValues.False);
			}
		}
		
		return outputNode;
	}
	
	@Override
	public String toString() {
		return "==";
	}
	
	public String getOperationKeyword() {
		return "isequalTo";
	}
	
	public void test() { 
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(ScientificOperationsList.getOps());
		testEq.importAliases(ScientificOperationsList.getAliases());
		testEq.importOperations(MatrixOperationsList.getOps());
		testEq.importAliases(MatrixOperationsList.getAliases());
		
		Calculator.testEquation(testEq, "1 == 1", "true", 1);
		Calculator.testEquation(testEq, "1 == 0", "false", 0);
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new IsEqualTo();
	}
	
}