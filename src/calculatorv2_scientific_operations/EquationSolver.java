package calculatorv2_scientific_operations;

import calculatorv2_basic_operations.Round;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.ValueNode;
import calculatorv2_matrix_core.Bra;
import calculatorv2_scientific_operations.Comparation.ComparationValues;

//solveEquation(eq1,eq2,precision,maxGuesses,guess1,guess2,...)
public class EquationSolver extends FunctionNode {
	private Equation parentEquation;
	
	
	
	public EquationSolver(Equation equation) {
		parentEquation = equation;
	}
	

	public static Bra solveEquation(Equation parentEquation,EquationNode paramsBra, ValueNode outputNode,EquationNode equation1,EquationNode equation2, double precision, int maxGuesses, ValueNode[] guesses) {
		
		
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
	    	Equation.warn("max guesses used");
	    }
		
		if (! (outputNode instanceof Bra) ) outputNode = new Bra();
		
		((Bra) outputNode).setValues(variableValues);
		return (Bra) outputNode;
	}
	
	public ValueNode function(EquationNode[] params, ValueNode outputNode) {
		if (! Equation.Assert(params.length != 0, "EquationSolve must have at least one parameter")) return outputNode;
		
		
		// get parameters
		
		EquationNode equation1 = params[0];
		
		EquationNode equation2;
		
		ValueNode[] guesses = {new ValueNode(0)};
		
		
		double precision = 0.0000001;
		
		int maxGuesses = (int) (10/precision);
		
		if (params.length > 1) {
			equation2 = params[1];
		}else {
			equation2 = new Comparation(ComparationValues.equal);
		}
		
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
		
		
		outputNode = solveEquation(parentEquation,getSubNode(), outputNode,equation1,equation2,precision,maxGuesses,guesses);
		
		Round rounder = new Round();
		rounder.setSubNode(new Bra(new ValueNode[] {outputNode,new ValueNode(-Math.log(precision-1))}));
		
		outputNode = rounder.getValueData();
		
		calculated();
		return outputNode;
	}
}
