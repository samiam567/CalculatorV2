package calculatorv2_basic_operations;

import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;

public abstract class BasicOpsList {
	private static final Equation eq = new Equation();
	private static final EquationNode[] ops = {new Addition(), new Absolute_Value(), new ArcCosine(eq), new ArcSine(eq), new ArcTangent(eq), new Cosine(eq),
			new Division(), new Exp(eq), new Logarithm(), new Multiplication(), new Natural_logarithm(), new Negative(eq), new Pow(), new PowerOfTen(),
			new Root(), new Sine(eq), new SquareRoot(), new Subtraction(), new Tangent(eq), new Round(), new GetValue()};
	
	private static final String[][] aliases =  {{"Round","round"},{"toInt("," round("},{"⁻"," _"}, {"×"," * "}, {"÷"," / "}, {"π"," pi "}, {"e^","exp"},{"isEqualTo","=="},{"solveEquation","solveequation"},{"E-"," timesTenToThe _"},{"E"," timesTenToThe "}};
	
	public static EquationNode[] getOps() {
		// opportunity to test datatypes here
		return ops;
	}
	
	public static String[][] getAliases() {
		return aliases;
	}

}
