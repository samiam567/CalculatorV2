package calculatorv2_matrix_core;

import calculatorv2_core.EquationNode;

public abstract class MatrixOperationsList {
	private static final EquationNode[] ops = {new MatrixCombine()};
	
	private static final String[][] aliases = {{"<+>",","," matcomb "}};
	
	public static EquationNode[] getOps() {
		// opportunity to test datatypes here
		return ops;
	}
	
	public static String[][] getAliases() {
		return aliases;
	}
}
