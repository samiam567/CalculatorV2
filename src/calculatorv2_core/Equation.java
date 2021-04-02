package calculatorv2_core;

import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import calculatorv2_basic_operations.Absolute_Value;
import calculatorv2_basic_operations.Addition;
import calculatorv2_basic_operations.ArcCosine;
import calculatorv2_basic_operations.ArcSine;
import calculatorv2_basic_operations.ArcTangent;
import calculatorv2_basic_operations.Cosine;
import calculatorv2_basic_operations.Division;
import calculatorv2_basic_operations.Exp;
import calculatorv2_basic_operations.Logarithm;
import calculatorv2_basic_operations.Multiplication;
import calculatorv2_basic_operations.Natural_logarithm;
import calculatorv2_basic_operations.Negative;
import calculatorv2_basic_operations.Pow;
import calculatorv2_basic_operations.PowerOfTen;
import calculatorv2_basic_operations.Root;
import calculatorv2_basic_operations.Round;
import calculatorv2_basic_operations.Sine;
import calculatorv2_basic_operations.SquareRoot;
import calculatorv2_basic_operations.Subtraction;
import calculatorv2_basic_operations.Tangent;
import calculatorv2_circuit_math.ParallelImpedanceAdd;
import calculatorv2_circuit_math.RCCircuit;
import calculatorv2_circuit_math.RLCircuit;
import calculatorv2_matrix_core.Bra;
import calculatorv2_matrix_core.Ket;
import calculatorv2_matrix_core.MatrixCombine;
import calculatorv2_matrix_core.MatrixCreate;
import calculatorv2_matrix_core.MatrixNode;
import calculatorv2_matrix_core.Matrixable;
import calculatorv2_scientific_operations.Comparation;
import calculatorv2_scientific_operations.CompareTo;
import calculatorv2_scientific_operations.ComplexValueNode;
import calculatorv2_scientific_operations.EquationSolver;
import calculatorv2_scientific_operations.IsEqualTo;
import calculatorv2_scientific_operations.IsPrime;
import calculatorv2_scientific_operations.Modulo;
import calculatorv2_scientific_operations.PercentError;
import calculatorv2_scientific_operations.Rand;
import calculatorv2_scientific_operations.Comparation.ComparationValues;



/**
 * {@summary this equation class will store the tree of operations and values that make up an equation and it will be capable of solving itself with different values}
 * @author apun1
 *
 */
public class Equation extends One_subNode_node {
	
	public static final String[] operations = {"_","isPrime","percenterror","rand","abs","sin", "cos", "tan", "asin", "acos", "atan", "^", "rt", "sqrt", "*", "/", "Modulo" , "+", "-","matcomb","compareTo","isequalTo","round","timesTenToThe","Solveequation","log","ln","parallImpedanceAdd","exp","RCCircuit","RLCircuit"};
	public static final String[][] aliases = { {"==", "isEqualTo"," isequalTo "}, {"<=>", "compareto", " compareTo "}, {"<+>",","," matcomb "}, {"%Error","%error","%err"," percenterror "}, {"%","mod"," Modulo "}, {"toInt(","toInt( ", " round("}, {"graphEquation","graph", "solveEquation", " Solveequation"}, {"E"," timesTenToThe "} , {"parallel"," parallImpedanceAdd "}, {"�?�"," _"}, {"×"," * "}, {"÷"," / "}, {"π"," pi "}, {"e^","exp"}}; //parser will replace all of the instances of the first strings with the last string
	
	private static String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	public static int[] numbers = {1,2,3,4,5,6,7,8,9,0};
	public static String[] numberChars = {"1","2","3","4","5","6","7","8","9","0",".",","};
	
	public ArrayList<VariableNode> variables;	// just a list of the variables for quick access
	
	public PrintStream out = System.out;
	
	//calculator settings 
	public static boolean JOptionPane_error_messages = true;
	public static final boolean printInProgress = false;
	public boolean useRadiansNotDegrees = true;
	
	//used by the runUserCalculator method
	JFrame calculatorAnchor;
	EquationNode prevAns = new ValueNode(0); 
	
	
	/**
	 * {@summary testing method}
	 * @param equation
	 */
	public static void main(String[] args) { 

		JOptionPane_error_messages = true;
		(new Equation()).runUserCalculator();
		
	}
	
	
	public static void warn(String warning) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		
		
		// Print the warning with its stacktrace
		String stStr = "";
		for (StackTraceElement s : stackTrace) {
			stStr += s.toString() + "\n";
		}
		System.out.println("WARNING: " + warning + " at: \n" + stStr + " \n --" );
		
		//show to JopPane
		if (JOptionPane_error_messages) JOptionPane.showMessageDialog(null, warning,"WARNING", JOptionPane.ERROR_MESSAGE);
	}
	
	public void runUserCalculator() {
		
		// put some constants into the variables as a default
		Commands.enableJFrameOutput = false; //be quiet about it
		JOptionPane_error_messages = false;
		Commands.addVariable("/pi=3.14159265358979323846264",this); // pi
		Commands.addVariable("/c=2.99792458*10^8",this); // speed of light 
		Commands.addVariable("/e=2.7182818284590452353602874713527",this); // e
		Commands.addVariable("/�?=1.054571817×10^_34",this);
		Commands.addVariable("/h= 6.62607015×10^_34",this); // plank's constant
		Commands.addVariable("/µ= 4 * π * 10^_7",this); // mu-naught or magnetic permeability of free space
		Commands.addVariable("/ε = 1/(c^2*µ)",this); // electric permeability of free space 8.854*10^_12
		Commands.addVariable("i", new ComplexValueNode(0,1), this);
		Commands.addVariable("/Kb = 1.38064852*10^_23",this);
		Commands.addVariable("true", new Comparation(ComparationValues.True), this);
		Commands.addVariable("false", new Comparation(ComparationValues.False), this);
		
		//prefixes
		Commands.addVariable("/peta = 1E15",this);
		Commands.addVariable("/tera = 1E12",this);
		Commands.addVariable("/giga = 1E9",this);
		Commands.addVariable("/mega = 1E6",this);
		Commands.addVariable("/kilo = 1E3",this);
		Commands.addVariable("/deci = 1E_1",this);
		Commands.addVariable("/centi = 1E_2",this);
		Commands.addVariable("/milli = 1E_3",this);
		Commands.addVariable("/micro = 1E_6",this);
		Commands.addVariable("/nano = 1E_9",this);
		Commands.addVariable("/pico = 1E_12",this);
		Commands.addVariable("/femto = 1E_15",this);
		
		
		
		Commands.enableJFrameOutput = true;
		
		out.println("Test took " + testCalculator() + " nanos to evaluate equations");
		JOptionPane_error_messages = true;
		
		calculatorAnchor = new JFrame();
	
		calculatorAnchor.setVisible(true);
		calculatorAnchor.setSize(300,10);
		calculatorAnchor.setTitle("Calculator Parser/Solver - Programmed by Alec Pannunzio");
		
		//warn the user about known issues
		knownIssues();
		
		String input = "";
		String lastInput = "";
		String eqSuggestion = "";
		while (true) { //if the user presses cancel the program will automatically terminate
			Commands.enableJFrameOutput = false;
			Commands.addVariable("ans", prevAns.getValueData(), this);
			Commands.enableJFrameOutput = true;
			
			while (input.length() == 0) {
				input = (String) JOptionPane.showInputDialog(calculatorAnchor,"Type in what you want to solve","Calculator V2",1, null,null, eqSuggestion);
				
				if (input == null || input.isBlank() || input.contains("exit") || input.contains("quit")) {
					out.println("terminating");
					calculatorAnchor.dispose();
					System.exit(1);
					out.println("exited");
				}else if (input.contains("/last"))  { 
					eqSuggestion = lastInput;
					input = "";
				} else if (input.substring(0,1).equals("/")) {
					Commands.parseCommand(input,this);
					input = "";
				}
				
			}
			
			
			eqSuggestion = ""; // reset the equation suggestion
		
			out.println("Input: " + input);
			
			
			
			try {
				createTree(input);
				Commands.applyVariables(this);
				prevAns = evaluate();
				
				
				
				// format the answer to look pretty
				if (Commands.outputFormat == 0) {
					JOptionPane.showMessageDialog(calculatorAnchor, toString(),input, 1);
				}else if (Commands.outputFormat == 1) {
					String ansStr = input + " =\n";
					for (int i = 0; i < toString().length(); i++) ansStr += " ";
					
					int numSpaces = ansStr.length() - toString().length();
				
					for (int i = 0; i < numSpaces; i++) ansStr += " ";
					ansStr += toString();
					
					JOptionPane.showMessageDialog(calculatorAnchor, ansStr,"Answer:", 1);
				}else {
					JOptionPane.showMessageDialog(calculatorAnchor,toString());
				}
				
				
				
				out.println("Output: " + toString());
			}catch(Exception e) {
				e.printStackTrace();
				
				Equation.warn("Exception occured whilst parsing:\n" + e);
				System.out.println("StackTrace of source exception: \n" + e.getStackTrace().toString());
				/*
				out.println("terminating because of an exception");
				calculatorAnchor.dispose();
				out.println("exited");
				System.exit(1);
				*/
			}
			
			lastInput = input;
			input = "";
		}
		
	}	
	
	
	/**
	 * {@summary will create an Equation with the passed String}
	 * @param equation
	 */
	public Equation(String equation) {
		variables = new ArrayList<VariableNode>();
		createTree(equation);
	}
	
	public Equation() {
		variables = new ArrayList<VariableNode>();
	}
	
	/**
	 * {@summary resizes the passed array to the given constraints. Can lengthen the array}
	 * @param array
	 * @param start
	 * @param end
	 * @return
	 */
	private EquationNode[] resizeNodesArray(EquationNode[] arr, int start, int end) {
		EquationNode[] prevArray = arr;
		arr = new EquationNode[1+end-start];
		
		for (int i = start; i <= end; i++) {
			try {
				arr[i-start] = prevArray[i];
			}catch(ArrayIndexOutOfBoundsException a) {/*okay yeah this is a lazy way of doing this but it'll work every time*/}
		}
		
		return arr;
	
	}
	
	private void printNodeArray(EquationNode[] arr) {
		int parenthesisLevel = 0;
	
		for (EquationNode n : arr) {
			
			if (n.getParenthesisLevel() != parenthesisLevel) {
				for (int i = 0; i < (n.getParenthesisLevel()-parenthesisLevel); i++) {
					out.print("(");
				}
				
				for (int i = 0; i > (n.getParenthesisLevel()-parenthesisLevel); i--) {
					out.print(")");
				}
				parenthesisLevel = n.getParenthesisLevel();
			}
			
			try {
				out.print(n + " ");
			}catch(NullPointerException npe) {
				out.print("null ");
			}
		}
		out.println();
	}
	
	/**
	 * {@summary searches for a String in an array and returns the index of that string in the array}
	 * {@code returns -1 if the String was not found}
	 * @param str the string to search for
	 * @param arr the array to search in
	 */
	private int indexOf(String str,String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(str)) {
				return i;
			}
		}
		return -1;
	}
	
	private EquationNode[] addToNodesArray(EquationNode n, EquationNode[] nodes) {
		nodes = resizeNodesArray(nodes, 0,nodes.length);
		nodes[nodes.length-1] = n;
		return nodes;
	}

	/**
	 * {@summary recursively adds the child nodes to the parent}
	 * @param equation
	 * @param parent
	 */
	public void createTree(String equation) {
		variables.clear();
		orderOfOpsLevel = 0; //we are the top level
		setParenthesisLevel(0);
		
		setSubNode(recursiveCreateTree(equation));
	}
	
	public EquationNode recursiveCreateTree(String equation) {
		
		
		if (equation.length() == 0) {
			Exception e = new Exception("Cannot create a tree with an equation String of length 0");
			e.printStackTrace();
			return null;
		}
		
		
		
		
		
		// replace aliases
		String searchStr; // String to search for
		String replaceStr; // String to replace searchStr with
		for (String[] alias : aliases) {
			replaceStr = alias[alias.length-1];
			for (int searchStringNum = 0; searchStringNum < alias.length-1; searchStringNum++) {
				searchStr = alias[searchStringNum];
				while(equation.contains(searchStr)) {
					equation = equation.replace(searchStr, replaceStr);
				}
			}
		}
	
		
		//create nodes
		
		
		EquationNode[] nodes = new EquationNode[0];


		String mode = "unknown";
		String prevMode = "unknown";
		String prevPrevMode = "unknown";
		String inputBuffer = "";
		
		int parenthesisLevel = 0;
		
		boolean space = false;
		
		String cChar = "";
		for (int i = 0; i < equation.length()+1; i++) {	
	
			if (i < equation.length()) {
				cChar = equation.substring(i, i+1);
					
				if (printInProgress) out.println("Parsing character: " + cChar + "   Index: " + i + "  in equation: " + equation);
				
				
				
					
					
				if (cChar.equals(" ")) {
					space = true;
					continue;
				} else if ( cChar.equals("(") ) { //it is an open-parenthesis, and the parenthesis level goes up
					mode = "openParent";
					if (printInProgress) out.println("openParent");
				}else if ( cChar.equals(")") ) { //it is an end-parenthesis, and the parenthesis level goes down
					mode = "closeParent";
					if (printInProgress) out.println("closeParent");			
				}else if (indexOf(cChar,numberChars) != -1) { //it is a number, and must be a value
					mode = "numberInput";
					if (printInProgress) out.println("numbInput");
				}else if (indexOf(cChar,letters) != -1) { //it is a letter, and if it is a single letter it is a variable, but if there are multiple letters it is an operation
					if (! mode.equals("multi-char-operation")) {
						mode = "letterInput";
						if (printInProgress) out.println("letter");
						if (prevMode.equals("letterInput")) {
							mode = "multi-char-operation";
							if (printInProgress) out.println("multi-char-operation");
						}
					}
					
						
					
				}else {
					mode = "operation";
					if (printInProgress) out.println("operation");	
				}
				
				

				
				
			}else { //we are at the end of the equation
				mode = "end";
			}
			
			
			if (indexOf(inputBuffer,operations) != -1) { //catch if we have two operations in a row ex: 1 + sin(25) or 1 + _3
				nodes = addToNodesArray(createOperation(inputBuffer,parenthesisLevel,mode),nodes);
				inputBuffer = "";
				prevMode = "unknown";
			}
			
			
			 
			//sandwich operation
			if (cChar.equals("[") && (! mode.equals("end"))) {
				if (printInProgress) System.out.println("Openbracket");
				mode = "matrixAquisition";
				int sand_end_indx = Sandwich_operatorNode.getSandwichSubString(equation.substring(i,equation.length()),"[","]");
				nodes = addToNodesArray(new MatrixCreate(this,equation.substring(i+1,i+sand_end_indx),parenthesisLevel, (Matrixable) new MatrixNode()),nodes);
				inputBuffer = "";
				i += sand_end_indx;
				continue;
			}else if (cChar.equals("{") && (! mode.equals("end"))) {
				if (printInProgress) System.out.println("OpenCurlybracket");
				mode = "matrixAquisition";
				int sand_end_indx = Sandwich_operatorNode.getSandwichSubString(equation.substring(i,equation.length()),"{","}");
				nodes = addToNodesArray(new MatrixCreate(this,equation.substring(i+1,i+sand_end_indx),parenthesisLevel, (Matrixable) new MatrixNode()),nodes);
				inputBuffer = "";
				i += sand_end_indx;
				continue;
			}else if (cChar.equals("<") && (! mode.equals("end"))) {
				if (printInProgress) System.out.println("OpenAnglebracket");
				mode = "braAquisition";
				int sand_end_indx = Sandwich_operatorNode.getSandwichSubString(equation.substring(i,equation.length()),"<","|");
				nodes = addToNodesArray(new MatrixCreate(this,equation.substring(i+1,i+sand_end_indx),parenthesisLevel,(Matrixable) new Bra()),nodes);
				inputBuffer = "";
				i += sand_end_indx;
				continue;
			}else if (cChar.equals("|") && (! mode.equals("end"))) {
				if (printInProgress) System.out.println("Openket");
				
				mode = "ketAquisition";
				int sand_end_indx = Sandwich_operatorNode.getSandwichSubString(equation.substring(i,equation.length()),"|",">");
				nodes = addToNodesArray(new MatrixCreate(this,equation.substring(i+1,i+sand_end_indx),parenthesisLevel,(Matrixable) new Ket()),nodes);
				inputBuffer = "";
				i += sand_end_indx;
				continue;
			}else if ( (! prevMode.equals("unknown")) && ( (! prevMode.equals(mode)) || space )  ) {
				if (printInProgress) out.println("modeChange:" + inputBuffer);
				
				if (prevMode.equals("letterInput") && (! mode.equals("multi-char-operation"))) { //create a variable 
					VariableNode newVariable = new VariableNode(inputBuffer,parenthesisLevel);
					variables.add(newVariable);
					nodes = addToNodesArray(newVariable,nodes); //add a new VariableNode with the variable name
					inputBuffer = ""; //clear the inputBuffer
				}else if (prevMode.equals("numberInput")) { //create a value
					nodes = addToNodesArray(new VariableNode(Double.parseDouble(inputBuffer),parenthesisLevel),nodes); //add a new VariableNode with the variable name
					inputBuffer = ""; //clear the inputBuffer
					
				}else if (prevMode.equals("operation") || prevMode.equals("multi-char-operation") || (prevMode.equals("letterInput") && space) ) {
					if (indexOf(inputBuffer,operations) != -1) {
						nodes = addToNodesArray(createOperation(inputBuffer,parenthesisLevel,mode),nodes);
					}else { //treat operations that the calculator doesn't recognize as a variable name
						
						
						//this new code treats multi-char strings that aren't operations as variables
						VariableNode newVariable = new VariableNode(inputBuffer,parenthesisLevel);
						variables.add(newVariable);
						nodes = addToNodesArray(newVariable,nodes); //add a new VariableNode with the variable name
						
						
						
						
						
						/* this old code treated multi-char strings that aren't operations as an error
						Exception e = new Exception("operation not found in operations array: " + inputBuffer);
						e.printStackTrace(out);
						if (JOptionPane_error_messages) JOptionPane.showMessageDialog(calculatorAnchor, e.toString() + "\n" + e.getStackTrace().toString());
						*/
					}
					inputBuffer = ""; //clear the inputBuffer
				}
				
			}
			
			if (mode.equals("openParent")) {
				parenthesisLevel++;
			}else if (mode.equals("closeParent")) {
				parenthesisLevel--;
			}else {		
				inputBuffer += cChar;
			}
			
			space = false;
			
			prevPrevMode = prevMode;
			prevMode = mode;
			
			 
			
		}
		
		if (printInProgress) printNodeArray(nodes);
		
		if (parenthesisLevel > 0) {
			Exception e = new Exception("ParenthesisError: missing close-parenthesis");
			e.printStackTrace(out);
			if (JOptionPane_error_messages) JOptionPane.showMessageDialog(calculatorAnchor, e.toString() + "\n" + e.getStackTrace().toString());
		}else if (parenthesisLevel < 0) {
			Exception e = new Exception("ParenthesisError: missing open-parenthesis");
			e.printStackTrace(out);
			if (JOptionPane_error_messages) JOptionPane.showMessageDialog(calculatorAnchor, e.toString() + "\n" + e.getStackTrace().toString());
		}
		
		
		//create tree linkups
		
		
		
		return getTree(nodes);
		
	}
	
	/**
	 * {@summary recursively returns the tree of the passed node array}
	 * {@code 1. search for the lowest level node/operation -----
	     2.a if it is a single subnode, getTree(everything right of it) is its subnode -----
	     2.b if it is a double subnode, getTree(everything left of it) is its left subnode, and getTree(everything right of it) is its left subnode
		}
	 * @param arr
	 * @return
	 */
	private EquationNode getTree(EquationNode[] arr) {
		if (printInProgress) printNodeArray(arr);
		
		//find the lowest level node/operation
		EquationNode lowestNode = arr[0];
		EquationNode n;
		int lowestIndx = 0;
		for (int i = arr.length-1; i > 0; i--) {
			n = arr[i];
			if ( n.getLevel() < lowestNode.getLevel() ) {
				lowestNode = n;
				lowestIndx = i;
			}
		}
		
		if (printInProgress) out.println("lowestNode: " + lowestNode + " lowestIndx: " + lowestIndx);
		
		try {
			Two_subNode_node node = (Two_subNode_node) lowestNode;
			if (printInProgress) out.println("two_subnode");
			node.setLeftSubNode(getTree(resizeNodesArray(arr,0,lowestIndx-1)));
			node.setRightSubNode(getTree(resizeNodesArray(arr,lowestIndx+1,arr.length-1)));
		}catch(ClassCastException c) {
			try {
				One_subNode_node node = (One_subNode_node) lowestNode;
				if (printInProgress) out.println("one_subnode");
				if (lowestIndx != 0) {
					Exception e = new Exception("there should be nothing to the left of a lowest-priority single-node operation");
					e.printStackTrace(out);
					if (JOptionPane_error_messages) JOptionPane.showMessageDialog(calculatorAnchor, e.toString() + "\n" + "Node: " + node.toString() + "\nOpsLvl: " + node.orderOfOpsLevel);
				}
				
				node.setSubNode(getTree(resizeNodesArray(arr,lowestIndx+1,arr.length-1)));
			}catch(ClassCastException e) {
				if (printInProgress) out.println("valueNode");
			}
		}
		
		
		return lowestNode;
	}
	
	private EquationNode createOperation(String op, int parenthesisLevel, String mode) {
		EquationNode node = null;
		
		switch (op) {
		case("_"):
			node = new Negative(mode);
			break;
		case("sin"):
			node = new Sine(this);
			break;
		case("cos"):
			node = new Cosine(this);
			break;
		case("tan"):
			node = new Tangent(this);
			break;
		case("asin"):
			node = new ArcSine(this);
			break;
		case("acos"):
			node = new ArcCosine(this);
			break;
		case("atan"):
			node = new ArcTangent(this);
			break;
		case("^"):
			node = new Pow();
			break;
		case("rt"):
			node = new Root();
			break;
		case("sqrt"):
			node = new SquareRoot();
			break;
		case("*"):
			node = new Multiplication();
			break;
		case("/"):
			node = new Division();
			break;
		case("+"):
			node = new Addition();
			break;
		case("-"):
			node = new Subtraction();
			break;
		case("timesTenToThe"):
			node = new PowerOfTen();
			break;
		case("abs"):
			node = new Absolute_Value();
			break;
		case("matcomb"):
			node = new MatrixCombine();
			break;
		case("percenterror"):
			node = new PercentError();
			break;
		case("Modulo"):
			node = new Modulo();
			break;
		case("compareTo"):
			node = new CompareTo();
			break;
		case("isequalTo"):
			node = new IsEqualTo();
			break;
		case("round"):
			node = new Round();
			break;
		case("rand"):
			node = new Rand();
			break;
		case("Solveequation"):
			node = new EquationSolver(this);
			break;
		case("isPrime"):
			node = new IsPrime();
			break;
		case("log"):
			node = new Logarithm();
			break;
		case("ln"):
			node = new Natural_logarithm();
			break;
		case("parallImpedanceAdd"):
			node = new ParallelImpedanceAdd();
			break;
		case("exp"):
			node = new Exp();
			break;
		case("RCCircuit"):
			node = new RCCircuit();
			break;
		case("RLCircuit"):
			node = new RLCircuit();
			break;
		default:
			
			
			Exception e = new Exception("operation not found in createOperation: " + op);
			e.printStackTrace(out);
			if (JOptionPane_error_messages) JOptionPane.showMessageDialog(calculatorAnchor, e.toString() + "\n" + e.getStackTrace().toString());
			break;
			
		}
		
		node.setParenthesisLevel(parenthesisLevel);
		
		return node;
	}
	
	/**
	 * @return the solution to the equation. If the equation has been solved before and no modifications have been made, it will simply return the value of the previous calculation.
	 */
	public double solve() {
	
		if (Double.isNaN(getValue()))  (new Exception("Calculation returned NaN")).printStackTrace();
		if (printInProgress) out.println(getValue());
		
		return getValue();
	}
	
	
	/**
	 * {@summary calculates the entire tree and returns the top level node}
	 * @return
	 */
	public EquationNode evaluate() {
		getSubNode().getValue();
		return getSubNode();
	}
	
	
	@Override
	public double operation(double a) {
		return a;
	}
	
	@Override
	public ValueNode operation(ValueNode a, ValueNode outputNode) {
		return a;
	}
	
	/**
	 *  {@summary replaces all variables with the passed name with the passed AdvancedValueNode}
	 * @param varName the name of the variables to replace
	 * @param value the AdvancedValueNode to replace the variables with 
	 */
	public boolean setAdvancedVariableValue(String varName, AdvancedValueNode value) {
		boolean varFound = false;
		
		for (VariableNode n : variables) {
			if (n.getName().equals(varName)) {
				n.setValueData(value);
				n.setValue(value.getValue());
			
				n.setName(varName);
				
				varFound = true;
			}
		}
		
		return varFound;
	}

	/**
	 * {@summary sets ALL variables in the equation with the passed name to the passed value.}
	 * {@code the equation will automatically know to recalculate itself}
	 * @param varName
	 * @param value
	 * @return
	 */
	public boolean setVariableValue(String varName, double value ) {
		boolean varFound = false;
		for (VariableNode n : variables) {
			if (n.getName().equals(varName)) {
				n.setValue(value);
				varFound = true;
			}
		}	
		return varFound;
	}
	
	/**
	 * {@summary sets the value of the variable with the passed index to the passed value}
	 * @param varIndx
	 * @param value
	 */
	public boolean setVariableValue(int varIndx, double value) {
		try {
			variables.get(varIndx).setValue(value);
			return true;
		}catch(IndexOutOfBoundsException i) {
			Exception e = new Exception("Variable index not found. That Variable dosen't exist. Indx: " + varIndx);
			e.printStackTrace(out);
			if (JOptionPane_error_messages) JOptionPane.showMessageDialog(calculatorAnchor, e.toString() + "\n" + e.getStackTrace().toString());
			return false;
		}
	}
	



	/**
	 * @param varName
	 * @return the index of the first instance of a variable with the passed name, -1 if the variable does not appear in the equation
	 */
	public int getVariableIndex(String varName) {
		VariableNode n;
		for (int i = 0; i < variables.size(); i++) {
			n = variables.get(i);
			if (n.getName().equals(varName)) {
				return i;
			}
		}
		
		return -1;
	}
	
	
	public VariableNode getVariable(int varIndx) {
		try {
			return variables.get(varIndx);
		}catch(IndexOutOfBoundsException i) {
			Exception e = new Exception("Variable index not found. That Varaible dosen't exist. Indx: " + varIndx);
			e.printStackTrace(out);
			if (JOptionPane_error_messages) JOptionPane.showMessageDialog(calculatorAnchor, e.toString() + "\n" + e.getStackTrace().toString());
			return null;
		}
	}
	
	
	private static int testNum = 0;
	private static boolean allTestsPassed = true;
	private static boolean testEquation(String eq, double answer) {
		Equation e = new Equation(eq);
		Commands.applyVariables(e);
		e.solve();
		System.out.print("Equation " + testNum + " ");
		testNum++;
		if (e.solve() == answer) {
			System.out.println("passed");
			return true;
		}else {
			System.out.println("failed!");
			System.out.println("Calculated Answer: " + e.solve() + "   Actual Answer:  " + answer + "    ValueData: " + e.getValueData());
			System.out.println("Equation to solve: " + eq);
			allTestsPassed = false;
			return false;		
		}	
	}
	private static boolean testEquation(String eq, String answerStr, double ans) {
		Equation e = new Equation(eq);
		Commands.applyVariables(e);
		e.solve();
		System.out.print("Equation " + testNum + " ");
		testNum++;
		if ( (e.getValueData().toString()).equals(answerStr) && e.solve() == ans) {
			System.out.println("passed");
			return true;
		}else {
			System.out.println("failed!");
			System.out.println("Calculated ValueData \"" + e.getValueData().toString() + "\" Target ValueData:  \"" + answerStr + "\"\nCalculated Answer: " + e.solve() + "   Actual Answer: " + ans);
			System.out.println("Equation to solve: " + eq);
			allTestsPassed = false;
			return false;	
		}	
	}
	@SuppressWarnings("unused")
	private static long testCalculator() {
		System.out.println("Testing calculator to ensure accuracy...");
		
		System.out.println("Building equations...");		

		long start = System.nanoTime();
		
		Equation e1 = new Equation("1 + 1");  // start simple
		
		if (e1.solve() == (1+1)) { 
			testEquation("1",1); //pass
		}else {
			testEquation("0",1); //fail
		}
		
		
		
		testEquation("1 + 2 * 6^2",1 + 2 * Math.pow(6,2)); //get a little more complicated, test negative exponents
		testEquation("((4^2*3-45)^(1+1*4) / 3) * 2",(Math.pow((Math.pow(4,2)*3-45),(1+1*4)) / 3) * 2 ); //REALLY complicated
		testEquation("45/2 + sin(10-5)/3",45D/2 + Math.sin(10-5) / 3 ); //testing Sine
		testEquation("4rt(tan(atan(0.12))) + 13-sqrt4",Math.pow( (Math.tan(Math.atan(0.12))),1.0/4 ) + 13-Math.sqrt(4) ); //test sin, asin,sqrt,rt
		testEquation("sqrt(3^2 + 4^2) * ( (abs(3)/3 * abs(4)/4) * (_abs(3)/3 + _abs(4)/4) - 1)",Math.sqrt(3*3 + 4*4) * ( (Math.abs(3)/3 * Math.abs(4)/4) * (-Math.abs(3)/3 + -Math.abs(4)/4) - 1)); //test abs and negatives
		testEquation("1/2*3/2",1D/2*3/2); //test left to right execution
		testEquation("atan(_(2rt(3)))",Math.atan(-Math.sqrt(3))); //testing arctan
		testEquation("4*10^_31*sin24",4*Math.pow(10, -31)*Math.sin(24)); //testing negative exponents
		testEquation("sin8+cos9+tan11*asin0.1+acos0.2+atan0.453",Math.sin(8)+Math.cos(9)+Math.tan(11)*Math.asin(0.1) + Math.acos(0.2) + Math.atan(0.453)); //test all trig functions
		testEquation("isPrime(342)",0); //test isPrime
		testEquation("%err(1,2)",-50); //test percent error and aliases
		testEquation("4rt(tan(atan(0.12))) + 13-sqrt4 isEqualTo 4rt(tan(atan(0.12))) + 13-sqrt4",1);
		testEquation("324*tan(34)*26/2 == 1*324*tan(34)*26/2+1-1",1);
		testEquation("1 <=> 2",-1);
		testEquation("2 <=> 1", 1);
		testEquation("34+ 12 *23124 <=> sin23",1);
		testEquation("324*tan(34)*26/2 compareTo 1*324*tan(34)*26/2+1-1",0);
		testEquation("round(1.2123) == 1",1);
		testEquation("round(1.2123,1)",1.2);
		testEquation("5E10",5*Math.pow(10,10));
		testEquation("sin2E_5",Math.sin(2*Math.pow(10,-5)));
		testEquation("0.3^3E_6",Math.pow(0.3,3*Math.pow(10,-6)));		
		testEquation("%err(e,pi)",-13.474402056773494);
		testEquation("solveEquation(x,10)",10);
		testEquation("solveEquation(x+1,10)",9);
		testEquation("solveEquation(18,x+20)",-2);
		testEquation("i+1","1.0 + 1.0i",Math.sqrt(2));
		testEquation("(3 + 2*i)*(1 + 7*i)","-11.0 + 23.0i",Math.sqrt(650));
		testEquation("(7 + 2.1*i)/(1.5 -4*i)","0.11506849315068492 + 1.7068493150684931i",1.7107236312349676);
		
			
	
		
		e1.createTree("((4^2*3-45)^(1+1*4) / 3) * 2"); //test equation reusability
		if (e1.solve() == ((Math.pow((Math.pow(4,2)*3-45),(1+1*4)) / 3) * 2 )) { 
			testEquation("1",1); //pass
		}else {
			testEquation("0",1); //pass
		}
		
		
	
		
		long end = System.nanoTime();
		
		if (allTestsPassed) {
			System.out.println("test complete. All systems functional");
		}else {
			System.out.println("test FAILED. One or more equations gave an incorrect answer.");
			JOptionPane.showMessageDialog(null, "Calculator Test failed. One or more equations did not yield a correct answer");
		}
					
		return end-start;
	}
		

	public void setPrintStream(PrintStream outputStream) {
		out = outputStream;
	}
	
	@Override
	public String toString() {
		return evaluate().getDataStr();
	}

	
	public static boolean Assert(boolean b) {
		return Assert(b,"Assertion failed");
	}
	
	public static boolean Assert(boolean b, String failurePhrase) {
		if (! b) Equation.warn(failurePhrase);
		return b;
	}
	
	
	/*
	 * A list of all the known issues with the calculator
	 */
	public static void knownIssues() {
		String knownIssues = "Known Issues:\n";
		
		
		
		knownIssues += "ans may yield wrong answer when used with some functions ; known: sqrt\n";
		knownIssues += "operations may not be recognized if there is not a space between them and an operation";
		
		
		
		
		warn(knownIssues);
	}
	
}
