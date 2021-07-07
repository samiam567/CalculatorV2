package calculatorv2_visualization;

import calculatorv2_core.EquationNode;

public abstract class VisualizationOpsList {

//	private static final Equation eq = new Equation();
	private static final EquationNode[] ops = {new GraphEquation()};
	
	private static final String[][] aliases =  {{"graphEquation","graph"}};
	
	public static EquationNode[] getOps() {
		// opportunity to test datatypes here
		return ops;
	}
	
	public static String[][] getAliases() {
		return aliases;
	}

}



