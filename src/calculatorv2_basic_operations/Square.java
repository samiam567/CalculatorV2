package calculatorv2_basic_operations;

import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;
import calculatorv2_core.ValueNode;

public class Square extends One_subNode_node {

	@Override
	public String getOperationKeyword() {
		return "square";
	}

	@Override
	public void test() {}

	@Override
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Square();
	}

	@Override
	protected double operation(double a) {
		return Multiplication.operationStat(a,a);
	}
	
	@Override
	protected ValueNode operation(ValueNode a, ValueNode outputNode) {
		return Multiplication.operationStat(a,a,outputNode);
	}

}
