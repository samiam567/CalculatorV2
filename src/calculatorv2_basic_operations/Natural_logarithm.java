package calculatorv2_basic_operations;

import calculatorv2_core.AdvancedValueNode;
import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;
import calculatorv2_core.ValueNode;
import calculatorv2_scientific_operations.ScientificOperationsList;

public class Natural_logarithm extends One_subNode_node {
	
	
	
	protected double operation(double a) {
		if (Equation.printInProgress) System.out.println(toString() + a);

		return Math.log(a);
	}
	

	@Override
	protected ValueNode operation(ValueNode n1, ValueNode outputNode) {
		
		if ( (n1 instanceof AdvancedValueNode && ( (AdvancedValueNode) n1).needsSpecialOperationConditions)) {
			
				Calculator.warn("WARNING: class " + getClass() + " has no implementation for AdvancedValueNodes of class " + n1.getClass());
				outputNode.setValue(operation(n1.getValue()));
			
		}else {
			outputNode.setValue(operation(n1.getValue()));
		}
		
		return outputNode;
		
	}	
	
	public String toString() {
		return "ln";
	}
	
	public String getOperationKeyword() {
		return "ln";
	}
	
	public void test() { 
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(ScientificOperationsList.getOps());
		testEq.importAliases(ScientificOperationsList.getAliases());
		
		Calculator.testEquation(testEq, "ln(1)", 0);
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Natural_logarithm();
	}
	
}
