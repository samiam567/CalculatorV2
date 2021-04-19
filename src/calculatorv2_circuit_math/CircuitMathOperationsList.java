package calculatorv2_circuit_math;


import calculatorv2_core.EquationNode;

public abstract class CircuitMathOperationsList {
	private static final EquationNode[] ops = {new ParallelImpedanceAdd(), new RCCircuit(), new RLCircuit()};
	
	private static final String[][] aliases = {{"parallel"," parallImpedanceAdd "}};
	public static EquationNode[] getOps() {
		// opportunity to test datatypes here
		return ops;
	}
	
	public static String[][] getAliases() {
		return aliases;
	}
}
