package calculatorv2_core;

import java.util.ArrayList;


@Deprecated
public abstract class Sandwich_operatorNode extends EquationNode {

	
	protected EquationNode[] subNodes;
	
	
	public Sandwich_operatorNode(Equation equation, String data, int parenthesisLevel ) {
		setParenthesisLevel(parenthesisLevel);
		
		if (Equation.printInProgress) System.out.println("getting nodes from data: " + data);
		
		ArrayList<EquationNode> nodesBuffer = new ArrayList<EquationNode>();
		
		int bracketLevel = 0;
		String cChar = "", prevChar = "";
		String inputBuffer = "";
		for(int i = 0; i < data.length(); i++) {
			cChar = data.substring(i,i+1);
			
			if (cChar.equals(" ")) continue; //skip spaces
			
			if ( cChar.equals("{") || cChar.equals("[") || cChar.equals("<") || (cChar.equals("|") && (! prevChar.equals(",")) ) ) {
				bracketLevel++;
			}else if ( (cChar.equals("}") || cChar.equals("]") || cChar.equals(">") || cChar.equals("|") ) ) {
				bracketLevel--;
			}
			
			if (Equation.printInProgress) {
				System.out.println("Char: " + cChar);
				System.out.println("BracketLevel: " + bracketLevel);
			}
			
			
			if (cChar.equals(",") && (bracketLevel == 0 || bracketLevel == data.length()-i)) {
				if (Equation.printInProgress) System.out.println("adding node: " + inputBuffer);
				nodesBuffer.add(equation.recursiveCreateTree(inputBuffer));
				
				inputBuffer = "";
				continue;
			}
			
			inputBuffer += cChar;
			
			prevChar = cChar;
			
			
			
		}
		
		if (inputBuffer.length() > 0 ) {
			if (Equation.printInProgress) System.out.println("Recursively creating tree for subNodeString of sandwich: " + inputBuffer);
			nodesBuffer.add(equation.recursiveCreateTree(inputBuffer));
		}
		
		subNodes = new EquationNode[nodesBuffer.size()];
		
		for (int i = 0; i < nodesBuffer.size(); i++) {
			subNodes[i] = nodesBuffer.get(i);
			subNodes[i].setParent(this);
		}
		
	}
	
	
	/**
	 * {@summary will return the portion of the input string that is between the sandwich operators}
	 * @param input
	 * @return
	 */
	public static int getSandwichSubString(String input,String beginChar, String endChar) {
		if (Equation.printInProgress) System.out.println("getting sandwich from: " + input);
		
		
		int level = 1, i = 1;
		String cChar;
		for (i = 1; i < input.length(); i++) {
			cChar = input.substring(i,i+1);
			
			if (cChar.equals(" ")) {
				continue; //skip spaces
			}else if (cChar.equals(beginChar)) {
				level++;
			}else if (cChar.equals(endChar)) {
				level--;
			}
			
			
			if (level == 0) break; //we have reached this sandwich's end character
		}
		
		if (level != 0) {
			Equation.warn("Bad sandwich!! Missing a close character - input: " + input);
		}
		
		if (Equation.printInProgress) System.out.println("sandwich got: " + input.substring(0,i));
		return i;
	}
	@Override
	public double getValue() {
		Equation.warn("getValue() not overriden for class " + getClass());
		return 1;
	}



	public EquationNode[] getSubNodes() {
		return subNodes;
	}
	
}