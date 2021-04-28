package calculatorv2_scientific_operations;

import java.util.ArrayList;

import calculatorv2_basic_operations.Round;
import calculatorv2_core.Calculator;
import calculatorv2_core.Commands;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.FunctionNode;
import calculatorv2_core.StringValueNode;
import calculatorv2_core.ValueNode;
import calculatorv2_core.VariableNode;
import calculatorv2_matrix_core.Bra;
import calculatorv2_scientific_operations.Comparation.ComparationValues;

//solveEquation(eq1,eq2,precision,maxGuesses,guess1,guess2,...)
public class EquationSolver extends FunctionNode {
	private Equation parentEquation;
	
	
	
	public EquationSolver(Equation equation) {
		parentEquation = equation;
	}
	

	
	private static Bra solveEquation(Equation eq1, Equation eq2,double precision,int maxGuesses,ValueNode[] guesses) {
		
		ArrayList<String> variables = new ArrayList<String>();

	
		
		for (VariableNode v : eq1.variables) {
			if (v.unsetVal() ) {
				if (variables.indexOf(v.getName()) == -1) {
					variables.add(v.getName());
				}
			}
		}
		for (VariableNode v : eq2.variables) {
			if (v.unsetVal() ) {
				if (variables.indexOf(v.getName()) == -1) {
					variables.add(v.getName());
				}
			}
		}
		
		if (variables.size() == 0) {
			return new Bra(new ValueNode[] {});
		}
		
		ValueNode[] oldGuesses = guesses;
		guesses = new ValueNode[variables.size()];
		for (int i = 0; i < oldGuesses.length && i < guesses.length; i++) {
			guesses[i] = oldGuesses[i];
		}
		
		
		double eqDiff = precision * 2;
		double prevDiff = precision * 5;
		long numGuesses = 0;
		int direction = 1;
		int prevDirection = 0;
		int prevPrevDirection = 0;
		
		int directionMulti = 1;
		
		double step = 1;
		do {
			
			
			if (eqDiff > 0) {
				direction = -directionMulti;
			}else {
				direction = directionMulti;
			}
			
			if (direction != prevDirection) {
				step /= 3;
			}else{
				step *= 2;
			}
		
			//adjust the guess by adding the step to the guess
			guesses[0].setValue(guesses[0].getValue() + direction * step);
			
		
			if (direction == prevDirection && prevDirection == prevPrevDirection && Math.abs(eqDiff) > Math.abs(prevDiff)) {
				directionMulti *= -1;
				direction = 1;
				prevDirection = 1;
				prevPrevDirection = 1;
			}
			
			prevPrevDirection = prevDirection;
			prevDirection = direction;
			prevDiff = eqDiff;
			
			numGuesses++;			
			
		
			
			//set the variables to the guesses
			for (int i = 0; i < variables.size(); i++) {
				eq1.setAdvancedVariableValue(variables.get(i),guesses[i]);
				eq2.setAdvancedVariableValue(variables.get(i),guesses[i]);
			}
			
			
			
			//calculate the difference
			eqDiff = eq1.getValue() - eq2.getValue();
			
			
			
			
		
		} while (Math.abs(eqDiff) > precision && numGuesses < maxGuesses);
		
		if (numGuesses >= maxGuesses) {
			Calculator.warn("max guesses used");
		}
		
		return new Bra(guesses);
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
		
	
		System.out.println("eq1String: " + eq1String);
		System.out.println("eq2String: " + eq2String);
		equation1 = new Equation(eq1String);
		
		equation2 = new Equation(eq2String);
		
		ValueNode[] guesses = {new ValueNode(0)};
		
		
		double precision = 0.00001;
		
		int maxGuesses = (int) (10/precision);
		
		
		
		if (params.length > 3) {
			precision = params[2].getValue();
		}
		
		if (params.length > 4) {
			maxGuesses = (int) params[3].getValue();
		}
		
		if (params.length > 4) {
			guesses = new ValueNode[params.length-4];
			for (int i = 0; i < guesses.length; i++) {
				guesses[i] = params[3+i].getValueData();
			}
		}
		
		
		outputNode = solveEquation(equation1,equation2,precision,maxGuesses,guesses);
		
		Round rounder = new Round();
		rounder.setSubNode(new Bra(new ValueNode[] {outputNode,new ValueNode(-Math.log10(precision)-1)}));
		
		outputNode = rounder.getValueData();
		
		calculated();
		return outputNode;
	}
	
	public String getOperationKeyword() {
		return "Solveequation";
	}
	
	public void test() { 
		Calculator.warn(getClass() + " is not tested and should not be used");
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new EquationSolver(eq);
	}


	public static Bra solveEquationOld(Equation parentEquation,EquationNode paramsBra, ValueNode outputNode,EquationNode equation1,EquationNode equation2, double precision, int maxGuesses, ValueNode[] guesses) {
		
		
		ValueNode[] variableValues = new ValueNode[parentEquation.variables.size()];
		
		for (int i = 0; i < variableValues.length; i++) {
			variableValues[i] = i < guesses.length ? guesses[i] : new ValueNode(0);
		}
		
		int numGuesses = 0;
		
		
		double step = 1;
		int direction = 0;
		int prevDirection = 0;
		int prevPrevDirection = 0;
		
		double prevAnswer;
		double answer = precision * 2; //make sure we are not in range of precision before we start solving
		int directionMulti = 1;
	    
	    while (Math.abs(answer) > precision && numGuesses < maxGuesses) {
	
			for (int variable_indx = 0; variable_indx < variableValues.length; variable_indx++) {
				
				if (Equation.printInProgress) parentEquation.out.println("Solving using variable: " + parentEquation.variables.get(variable_indx).getName());
				
				
				
				
				
				if (Equation.hasChildInTree(equation1,parentEquation.variables.get(variable_indx))) { 
					directionMulti = 1;  // this variable is in equation 1
				}else {
					directionMulti = -1; // this variable is in equation 2
				}
		
				variableValues[variable_indx].setValue(variableValues[variable_indx].getValue());
				parentEquation.setVariableValue(variable_indx,variableValues[variable_indx].getValue());
				paramsBra.getValue();
				prevAnswer = equation1.getValue()-equation2.getValue();	
	
				parentEquation.setVariableValue(variable_indx,variableValues[variable_indx].getValue()+directionMulti);
				paramsBra.getValue();
				answer = equation1.getValue()-equation2.getValue();	
				
				if (answer < prevAnswer) {
					directionMulti *= -1;
					if (Equation.printInProgress) parentEquation.out.println("inverse relationship");
				}
						
				
				//reset everything
				prevAnswer = 10+2*precision; // make sure we detect an answer change on first loop
				answer = 10;
				direction = 1;
		
				prevDirection = 0;
				prevPrevDirection = 0;
				step = precision * 10;
				
				while (Math.abs(answer) > precision && (Math.abs(answer-prevAnswer) > 0.1*precision) && numGuesses < maxGuesses) {	
					
					// figure out if we were too high or too low
					if (answer > 0) {
						direction = -1;
					}else {
						direction = 1;
					}
					
					
					
					if (direction != prevDirection) {
						step /= 2;
					}else if (direction == prevPrevDirection){
						step *= 2;
					}
					
					prevPrevDirection = prevDirection;
					prevDirection = direction;
					
					
					//adjust the guess by adding the step to the guess
					variableValues[variable_indx].setValue(variableValues[variable_indx].getValue() + direction * step  * directionMulti);
					parentEquation.setVariableValue(variable_indx,variableValues[variable_indx].getValue());
					
					
					prevAnswer = answer;
					
					
					
					//get the next result of the equations
					paramsBra.getValue();
					answer = equation1.getValue()-equation2.getValue();	
					
					
					
					numGuesses++;
				}
			}
	    }
	    
	    if (numGuesses >= maxGuesses) {
	    	Calculator.warn("max guesses used");
	    }
		
		if (! (outputNode instanceof Bra) ) outputNode = new Bra();
		
		((Bra) outputNode).setValues(variableValues);
		return (Bra) outputNode;
	}
}
