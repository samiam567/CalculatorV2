package calculatorv2_scientific_operations;


import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;


public abstract class ScientificOperationsList {
	private static final Equation eq = new Equation();
	private static final EquationNode[] ops = {new CompareTo(), new EquationSolver(eq), new IsEqualTo(), new IsPrime(), new Modulo(), new PercentError(),
											   new Rand(),new ComplexNumber_Phase(eq), new RepeatedCalculation(), new DefiniteIntegral()};
	
	private static final String[][] aliases =  {{"==", "isEqualTo"," isequalTo "}, {"<=>", "compareto", " compareTo "},{"%Error","%error","%err"," percenterror "},{"%","mod"," Modulo "},{"graphEquation","graph", "solveEquation","solveequation", " Solveequation"}};
	public static EquationNode[] getOps() {
		// opportunity to test datatypes here
		return ops;
	}
	
	public static String[][] getAliases() {
		return aliases;
	}
}
