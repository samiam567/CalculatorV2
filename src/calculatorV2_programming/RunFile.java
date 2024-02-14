package calculatorV2_programming;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.FunctionNode;
import calculatorv2_core.StringValueNode;
import calculatorv2_core.ValueNode;

public class RunFile extends FunctionNode {

	@Override
	public String getOperationKeyword() {
		return "run";
	}

	@Override
	public void test() {
		
		
	}

	@Override
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new RunFile();
	}

	@Override
	public ValueNode function(EquationNode[] params, ValueNode outputNode) {
		Calculator.Assert(params.length == 1, "Run should only take the name of the file to run");
		Calculator.Assert(params[0] instanceof StringValueNode, "Run argument must be a string");
		
		
		
		String filename = ((StringValueNode) params[0]).getString();
		
		File program = new File(filename);
		Scanner fileScanner;
		try {
			fileScanner = new Scanner(program);
			
			
			String output = "\n";
			Equation eq = new Equation(true);
			
			String nextLine;
			while (fileScanner.hasNext()) {
				nextLine = fileScanner.nextLine();
				if (nextLine.length() > 0) {
					boolean isComment = nextLine.startsWith("#");
					boolean isSilenced = nextLine.substring(nextLine.length()-1, nextLine.length()).equals(";"); // commands with a semicolon are not outputted
					
					if (isSilenced) {
						nextLine = nextLine.substring(0, nextLine.length()-1); // remove ending semicolon
					}
					
					if (isComment && ! isSilenced) {
						output += nextLine + "\n";
					}else {
						
						// check if the user put a comment at the end of the line
						int commentStartLocation = nextLine.indexOf('#'); 
						String commentPortion = "";
						if (commentStartLocation != -1) {
							commentPortion = nextLine.substring(commentStartLocation);
							nextLine = nextLine.substring(0,commentStartLocation);
						}
						
						// calculate the line and add to output
						String lineOut = eq.calculate(nextLine);
						
						if (! isSilenced) output += lineOut + " " + commentPortion + "\n"; // output if there is no semicolon
					}

				}else {
					output += "\n";
				}
			}
			
			fileScanner.close();
			outputNode = new StringValueNode(output);
			return outputNode;
		
		} catch (FileNotFoundException e) {
		
			Calculator.warn(e.toString());
			return outputNode;
		}
	}
	
	@Override
	public String getParameterInputs() {
		return "run(\"<filename>\")";
	}

}
