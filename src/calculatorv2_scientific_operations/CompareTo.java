package calculatorv2_scientific_operations;

import calculatorv2_basic_operations.BasicOpsList;
import calculatorv2_core.AdvancedValueNode;
import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.Two_subNode_node;
import calculatorv2_core.ValueNode;

public class CompareTo extends Two_subNode_node {
	public CompareTo() {
		orderOfOpsLevel = 0;
	}
	
	@Override
	protected double operation(double a, double b) {
		if (a == b) {
			return 0;
		}else if (a < b) {
			return -1;
		}else{
			return 1;
		}
	}
	
	@Override
	public ValueNode operation(ValueNode n1, ValueNode n2, ValueNode outputNode) {
		if (! (outputNode instanceof Comparation)) outputNode = new Comparation();
		
		if ( (n1 instanceof AdvancedValueNode && ( (AdvancedValueNode) n1).needsSpecialOperationConditions) || (n2 instanceof AdvancedValueNode && ( (AdvancedValueNode) n2).needsSpecialOperationConditions) ) {
			Calculator.warn("class " + CompareTo.class + " has no implementation for AdvancedValueNodes of class " + n1.getClass() + " and " + n2.getClass());

			if (operation(n1.getValue(),n2.getValue()) == 1) {
				((Comparation) outputNode).setValue(Comparation.ComparationValues.True);
			}else {
				((Comparation) outputNode).setValue(Comparation.ComparationValues.False);
			}
		}
		
		if (n1.getValue() == n2.getValue()) {
			((Comparation) outputNode).setValue(Comparation.ComparationValues.equal);
		}else if (n1.getValue() < n2.getValue()){
			((Comparation) outputNode).setValue(Comparation.ComparationValues.less);
		}else {
			((Comparation) outputNode).setValue(Comparation.ComparationValues.greater);
		}
		
		
		return outputNode;
	}
	
	
	public String getOperationKeyword() {
		return "compareTo";
	}
	
	public void test() { 
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(ScientificOperationsList.getOps());
		testEq.importAliases(ScientificOperationsList.getAliases());
		
		Calculator.testEquation(testEq, "1 compareTo 2", -1);
		Calculator.testEquation(testEq, "2 compareTo 1", 1);
		Calculator.testEquation(testEq, "1 compareTo 1", 0);
		Calculator.testEquation(testEq, "true compareTo true", 0);
		Calculator.testEquation(testEq, "true compareTo false", 1);
		
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new CompareTo();
	}
}
