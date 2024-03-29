package calculatorv2_scientific_operations;

import calculatorv2_basic_operations.BasicOpsList;
import calculatorv2_core.AdvancedValueNode;
import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;
import calculatorv2_core.ValueNode;
import calculatorv2_matrix_core.MatrixOperationsList;

/**
 * {@summary checks if the number is prime - returns 1 if true, 0 if false}
 * @author apun1
 *
 */

public class IsPrime extends One_subNode_node {	
	
	public int isPrime(double a) {
		int aInt = (int) a;
		assert aInt == a;
		
		for (int i = 2; i <= aInt/2; i++) {
			if (aInt % i == 0) {
				return 0;
			}
		}
		return 1;
	}
	
	@Override
	protected double operation(double a) {
		return isPrime(a);
	}

	
	
	@Override
	protected ValueNode operation(ValueNode n1, ValueNode outputNode) {
		if (! (outputNode instanceof Comparation)) outputNode = new Comparation();
		
		if ( (n1 instanceof AdvancedValueNode && ( (AdvancedValueNode) n1).needsSpecialOperationConditions)) {
			Calculator.warn("class " + IsPrime.class + " has no implementation for AdvancedValueNodes of class " + n1.getClass());

			if (operation(n1.getValue()) == 1) {
				((Comparation) outputNode).setValue(Comparation.ComparationValues.True);
			}else {
				((Comparation) outputNode).setValue(Comparation.ComparationValues.False);
			}
		}else {
			if (operation(n1.getValue()) == 1) {
				((Comparation) outputNode).setValue(Comparation.ComparationValues.True);
			}else {
				((Comparation) outputNode).setValue(Comparation.ComparationValues.False);
			}
		}
		
		return outputNode;
	}
	
	public String toString() {
		return "isPrime";
	}
	
	public String getOperationKeyword() {
		return "isPrime";
	}
	
	public void test() { 
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(ScientificOperationsList.getOps());
		testEq.importAliases(ScientificOperationsList.getAliases());
		testEq.importOperations(MatrixOperationsList.getOps());
		testEq.importAliases(MatrixOperationsList.getAliases());
		
		Calculator.testEquation(testEq, "isPrime(2)", "true", 1);
		Calculator.testEquation(testEq, "isPrime(7)", "true", 1);
		Calculator.testEquation(testEq, "isPrime(54)", "false", 0);
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new IsPrime();
	}
}
