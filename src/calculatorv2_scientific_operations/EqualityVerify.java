package calculatorv2_scientific_operations;

import java.util.ArrayList;

import calculatorv2_basic_operations.Absolute_Value;
import calculatorv2_basic_operations.BasicOpsList;
import calculatorv2_basic_operations.Division;
import calculatorv2_basic_operations.Round;
import calculatorv2_basic_operations.Subtraction;
import calculatorv2_core.Calculator;
import calculatorv2_core.Commands;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.FunctionNode;
import calculatorv2_core.StringValueNode;
import calculatorv2_core.ValueNode;
import calculatorv2_core.VariableNode;
import calculatorv2_matrix_core.Bra;
import calculatorv2_matrix_core.MatrixOperationsList;

//solveEquation(eq1,eq2,precision,maxGuesses,guess1,guess2,...)
public class EqualityVerify extends FunctionNode {
	private Equation parentEquation;
	
	private CompareTo compNode = new CompareTo();
	private Subtraction equationSubtraction = new Subtraction();
	private Absolute_Value absVal = new Absolute_Value();
	private Absolute_Value absValTwo = new Absolute_Value();
	private Division normalizationDivide = new Division();
	private ValueNode precisionValueNode = new ValueNode(0.01);
	
	private static boolean debug = true;
	
	public EqualityVerify(Equation equation) {
		parentEquation = equation;
		
		absVal.setSubNode(equationSubtraction);
		normalizationDivide.setLeftSubNode(absVal); //rightSubnode will be eq1
		absValTwo.setSubNode(normalizationDivide);
		compNode.setLeftSubNode(absValTwo);
		compNode.setRightSubNode(precisionValueNode);
	}
	

	
	private String verifyEquality(Equation eq1, Equation eq2, String[] variables, int idx, double lowerBound, double upperBound, double step) {
		
		
		if(debug) {
//			System.out.println("loop: " + idx);
		}
		
		if (idx >= variables.length) {

			if (false) {
				for (int i = 0; i < variables.length; i++) {
					System.out.print(variables[i] + " : ");
					int e1idx = eq1.getVariableIndex(variables[i]);
					if (e1idx != -1) {
						System.out.print(eq1.getVariable(e1idx).getValueData());
					}
					System.out.print(",");
					
					int e2idx = eq2.getVariableIndex(variables[i]);
					if (e2idx != -1) {
						System.out.print(eq1.getVariable(e2idx).getValueData());
					}
					System.out.println();
				}

			}
			

			Comparation.ComparationValues compValue = ((Comparation) compNode.getValueData()).getCompareValue();

			if ((compValue.equals(Comparation.ComparationValues.less) || compValue.equals(Comparation.ComparationValues.equal)) ) {
//	
//				System.out.println(eq1.getAliasedEquation() + "==" + eq2.getAliasedEquation());
//				System.out.println(eq1.getDataStr() + "==" + eq2.getDataStr());
//				System.out.println("Subtraction: " + equationSubtraction.getValueData());
//				System.out.println("absVal: " + absVal.getValueData());
//				System.out.println("normalizationDivide: " + normalizationDivide.getValueData());
//				System.out.println("absValTwo: " + absValTwo.getValueData());
//				System.out.println("precisionValueNode: " + precisionValueNode.getValueData());
//				System.out.println("compNode: " + compNode.getValueData());

				return null;
			}else {
				String output = "";
				for (int i = 0; i < variables.length; i++) {
					try {
					output += (variables[i] + " : ");
					int e1idx = eq1.getVariableIndex(variables[i]);
					if (e1idx != -1) {
						output += (eq1.getVariable(e1idx).getValueData());
					}
					}catch(Exception e) {}
					output += (",");
					
					try {
					int e2idx = eq2.getVariableIndex(variables[i]);
					if (e2idx != -1) {
						output += (eq1.getVariable(e2idx).getValueData());
					}
					output += "\n";
					}catch(Exception e) {}
				}
				
				output += ("verifySame failed!\n");
				output += (eq1.getAliasedEquation() + "!=" + eq2.getAliasedEquation() + "\n");
				output += (eq1.getDataStr() + "!=" + eq2.getDataStr()+ "\n");
				output += ("Subtraction: " + equationSubtraction.getValueData()+ "\n");
				output += ("absVal: " + absVal.getValueData()+ "\n");
				output += ("normalizationDivide: " + normalizationDivide.getValueData()+ "\n");
				output += ("absValTwo: " + absValTwo.getValueData()+ "\n");
				output += ("precisionValueNode: " + precisionValueNode.getValueData()+ "\n");
				output += ("compNode: " + compNode.getValueData()+ "\n");
				
				return output;
				
			}
		}
	
		
		ValueNode checkValueNode = new ValueNode(0.0); // the number we will set the next variable to check the equation equality
			
		for (double checkValue = lowerBound+step/2; checkValue < upperBound; checkValue+= step ) {
			//adjust the guess by adding the step to the guess
			checkValueNode.setValue(checkValue);
//			if(debug) System.out.println("setting " + variables[idx] + " to " + checkValue);
			eq1.setAdvancedVariableValue(variables[idx],checkValueNode);
			eq2.setAdvancedVariableValue(variables[idx],checkValueNode);
			
			String output = verifyEquality(eq1, eq2, variables, idx+1, lowerBound, upperBound, step);
			if (output != null) {
				return output;
			}
				
		}
		return null;
		
	}
	

	public ValueNode function(EquationNode[] params, ValueNode outputNode) {
		if (! Calculator.Assert(params.length > 1, "EquationSolve must have at least two parameters")) return outputNode;
		
		
		// get parameters
		
		Equation equation1, equation2;
		String eq1String, eq2String;
		
		if (params[0] instanceof StringValueNode) {
			eq1String = ((StringValueNode) params[0]).getString();
		}else {
			eq1String = params[0].convertEquationToString();
			Calculator.warn(getClass() + " should be used exclusively with strings for equation input");
		}
		
		if (params[1] instanceof StringValueNode) {
			eq2String = ((StringValueNode) params[1]).getString();
		}else {
			eq2String = params[1].convertEquationToString();
			Calculator.warn(getClass() + " should be used exclusively with strings for equation input");
		}
		
	
//		System.out.println("eq1String: " + eq1String);
//		System.out.println("eq2String: " + eq2String);
		equation1 = new Equation(eq1String);
		equation2 = new Equation(eq2String);
		
		
		equationSubtraction.setLeftSubNode(equation1);
		equationSubtraction.setRightSubNode(equation2);
		normalizationDivide.setRightSubNode(equation1);

		double lowerRange = -100;
		if (params.length > 2) {
			lowerRange = params[2].getValue();
		}
		double upperRange = 100;
		if (params.length > 3) {
			upperRange = params[3].getValue();
		}
		
		int numChecks = 1000;
		if (params.length > 4) {
			numChecks = (int) params[4].getValue();
			if (numChecks <= 0) {
				Calculator.warn("numchecks (" + numChecks + ") must be greater than zero");
			}
		}
		
		double precision = 0.0001;
		if (params.length > 5) {
			precision = params[5].getValue();
		}
		
		precisionValueNode.setValue(precision);
		
		
	
		
		
		if (upperRange <= lowerRange) {
			throw new IllegalArgumentException("upperRange must be larger than lowerRange in EqualityVerify");
		}
		
		double checkStep = (upperRange-lowerRange) / numChecks;
		
		ArrayList<String> variablesArrList = new ArrayList<String>();

		for (VariableNode v : equation1.variables) {
			if (v.unsetVal() ) {
				if (variablesArrList.indexOf(v.getName()) == -1) {
					variablesArrList.add(v.getName());
				}
			}
		}
		for (VariableNode v : equation2.variables) {
			if (v.unsetVal() ) {
				if (variablesArrList.indexOf(v.getName()) == -1) {
					variablesArrList.add(v.getName());
				}
			}
		}
		
		String[] variables = new String[variablesArrList.size()];
		for (int i = 0; i < variables.length; i++) {
			variables[i] = variablesArrList.get(i);
		}
		
		for (int i = 0; i < variables.length; i++) {
			VariableNode v;
			if (equation1.getVariableIndex(variables[i]) == -1) {
				equation1.variables.add(new VariableNode(equation1, variables[i]));
			}
			
			if (equation2.getVariableIndex(variables[i]) == -1) {
				equation2.variables.add(new VariableNode(equation2, variables[i]));
			}
		}
		
		
		String output = null;
		try {
			output = verifyEquality(equation1,equation2, variables, 0, lowerRange, upperRange, checkStep);
		}catch(StackOverflowError e) {
			Calculator.warn(e.toString());
			output = e.getMessage();
		}
	
		
		calculated();
		
		if (output == null) {
			return new Comparation(Comparation.ComparationValues.True);
		}else {
			return new StringValueNode(output);
		}
	}
	
	@Override
	public String getParameterInputs() {
		return "verifySame(\"Equation1\",\"Equation2\",lowerRange=_1E9,upperRange=1E9,numChecks=1E3,precision=0.0001) ";
	}
	
	@Override
	public String getEquation() {
		return "verifyEquality will make sure that the two passed equations are mathematically equivalent by testing each with a range of values for each variable";
	}
	
	public String getOperationKeyword() {
		return "verifySame";
	}
	
	public void test() {
		Equation testEq = new Equation();
		testEq.importOperations(BasicOpsList.getOps());
		testEq.importOperations(ScientificOperationsList.getOps());
		testEq.importAliases(ScientificOperationsList.getAliases());
		testEq.importOperations(MatrixOperationsList.getOps());
		testEq.importAliases(MatrixOperationsList.getAliases());
		
		Calculator.testEquation(testEq, "verifySame(\"x^2\",\"x*x\")", "true", 1);
		Calculator.testEquation(testEq, "verifySame(\"x^2\",\"x*x*x\")", "false", 0);
		Calculator.testEquation(testEq, "verifySame(\"pi*x^2\",\"x*x*x\")", "false", 0);
		Calculator.testEquation(testEq, "verifySame(\"pi*x^2\",\"x*pi*x+1\")", "false", 0);
		Calculator.testEquation(testEq, "verifySame(\"c*x^2\",\"x*c*x\")", "true", 1);
		Calculator.testEquation(testEq, "verifySame(\"c*x^2\",\"x*c*x+1\")", "false", 0);
		Calculator.testEquation(testEq, "verifySame(\"integrate('x^2','x',0,X)\",\"(1/3)*X^3\",0,10,10,0.001)", "true", 1);
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new EqualityVerify(eq);
	}


}
