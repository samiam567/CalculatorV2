package calculatorv2_statistical_operations;

import calculatorv2_core.EquationNode;

public abstract class StatisticalOperationsList {
	private static final EquationNode[] ops = {new Factorial(), new Permutation(), new Combination()};
	
	private static final String[][] aliases = {};
	public static EquationNode[] getOps() {
		// opportunity to test datatypes here
		return ops;
	}
	
	public static String[][] getAliases() {
		return aliases;
	}
}
