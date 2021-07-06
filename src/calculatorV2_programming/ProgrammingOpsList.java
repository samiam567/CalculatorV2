package calculatorV2_programming;

import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;

public abstract class ProgrammingOpsList {

	private static final Equation eq = new Equation();
	private static final EquationNode[] ops = {
			new RunFile()
	};
	
	private static final String[][] aliases =  {};
	
	public static EquationNode[] getOps() {
		return ops;
	}
	
	public static String[][] getAliases() {
		return aliases;
	}

}
