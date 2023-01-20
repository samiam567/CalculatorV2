package calculatorv2_core;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;

import calculatorV2_programming.ProgrammingOpsList;
import calculatorv2_basic_operations.BasicOpsList;
import calculatorv2_circuit_math.CircuitMathOperationsList;
import calculatorv2_matrix_core.MatrixOperationsList;
import calculatorv2_scientific_operations.Comparation;
import calculatorv2_scientific_operations.ComplexValueNode;
import calculatorv2_scientific_operations.ScientificOperationsList;
import calculatorv2_statistical_operations.StatisticalOperationsList;
import calculatorv2_visualization.VisualizationOpsList;
import calculatorv2_scientific_operations.Comparation.ComparationValues;



/**
 * {@summary this equation class will store the tree of operations and values that make up an equation and it will be capable of solving itself with different values}
 * @author apun1
 *
 */
public class Equation extends One_subNode_node {
	
	public ArrayList<EquationNode> operations = new ArrayList<EquationNode>();
	
	public ArrayList<String> operationKeywords = new ArrayList<String>();
	
	LinkedList<String[]> aliases = new LinkedList<String[]>(); //parser will replace all of the instances of the first strings with the last string
	
	private static final String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	public static final int[] numbers = {1,2,3,4,5,6,7,8,9,0};
	public static final String[] numberChars = {"1","2","3","4","5","6","7","8","9","0",".",","};
	
	public ArrayList<VariableNode> variables = new ArrayList<VariableNode>();;	// just a list of the variables for quick access
	
	public PrintStream out = System.out;
	
	//calculator settings
	public static boolean printInProgress = false;
	
	public enum DegOrRadValue {degrees, radians};
	private DegOrRadValue degRadMode = DegOrRadValue.radians;
	
	
	EquationNode prevAns = new ValueNode(0);
	

	public String cMode = "unknown"; // for Negative operation

	
	public boolean usingRadians() {
		return degRadMode.equals(DegOrRadValue.radians);
	}
	
	public DegOrRadValue getDegRadMode() {
		return degRadMode;
	}
	
	public void importStandardConstants() {
		// put some constants into the variables as a default
		boolean prevEnJFO = Calculator.enableJFrameOutput;
		boolean prevVout = Calculator.verboseOutput;
		Calculator.enableJFrameOutput = false; //be quiet about it
		Calculator.verboseOutput = false;
	
		Commands.addVariable("/pi=3.14159265358979323846264",this); // pi
		Commands.addVariable("/c=2.99792458*10^8",this); // speed of light 
		Commands.addVariable("/e=2.7182818284590452353602874713527",this); // e
		Commands.addVariable("/ℏ=1.054571817Ã—10^_34",this);
		Commands.addVariable("/h= 6.62607015Ã—10^_34",this); // plank's constant
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
							
	
		Calculator.enableJFrameOutput = prevEnJFO;	
		Calculator.verboseOutput = prevVout;
	
	}
	
	
	
	
	/**
	 * {@summary will create an Equation with the passed String}
	 * @param equation
	 */
	public Equation(String equation) {
		importAll();
		importStandardConstants();
		createTree(equation);
		Commands.applyVariables(this);
	}
	
	public Equation(boolean placeholder) {
		importAll();
		importStandardConstants();
	}
	
	public Equation() {
		//nothing needs to be done here
	}
	
	
	
	public Equation(EquationNode equationNode) {
		importAll();
		importStandardConstants();
		createTree(equationNode);
		Commands.applyVariables(this);
	}

	public void importAll() {
		
	
		importOperations(BasicOpsList.getOps());
		importAliases(BasicOpsList.getAliases());
		
		importOperations(ScientificOperationsList.getOps());
		importAliases(ScientificOperationsList.getAliases());
		
		importOperations(MatrixOperationsList.getOps());
		importAliases(MatrixOperationsList.getAliases());
		
		importOperations(CircuitMathOperationsList.getOps());
		importAliases(CircuitMathOperationsList.getAliases());
		
		importOperations(VisualizationOpsList.getOps());
		importAliases(VisualizationOpsList.getAliases());
		
		importOperations(ProgrammingOpsList.getOps());
		importAliases(ProgrammingOpsList.getAliases());
		
		importOperations(StatisticalOperationsList.getOps());
		importAliases(StatisticalOperationsList.getAliases());
		
		setDegRadMode(Commands.mostRecentDegRadMode);
		
	}
	public void importOperations(EquationNode[] ops) {
		for (EquationNode opType : ops) {
			importOperation(opType);
		}
	}
	public void importAliases(String[][] aliases) {
		for (String[] cAlias : aliases) {
			this.aliases.add(cAlias);
		}
		
		
	}
	
	public void importOperation(EquationNode opType) {
		if (opType.getOperationKeyword() == null || opType.getOperationKeyword().length() == 0) {
			Calculator.error("Operation " + opType.getClass() + " does not have a valid operation keyword. Check importation methods and fields");
		}else if (indexOf(opType.getOperationKeyword(),operationKeywords) == -1) {
			operations.add(opType);
			operationKeywords.add(opType.getOperationKeyword());
		}else {
			Calculator.warn("Tried to import operation with keyword " + opType.getOperationKeyword() + " More than once!");
		}
	}
	
	/**
	 * {@code runs input assuming the user called for the parse and a already setup equation}
	 * @param input
	 * @return
	 */
	public String queryUserCalculator(String input) {
		
		boolean jfo = Calculator.enableJFrameOutput;
		Calculator.enableJFrameOutput = false;
		Commands.addVariable("ans", prevAns.getValueData(), null);
		Calculator.enableJFrameOutput = true;
	
		
		
				
		if (input == null || input.contains("exit") || input.contains("quit")) {
			out.println("terminating");
			Calculator.calculatorAnchor.dispose();
			out.println("exited");
			System.exit(1);
		}else if (input.length() == 0)  { 
			return "~~~~";
		} else if (input.contains("/last"))  { 
			
			if (! Calculator.lastEquations.isEmpty()) {
				Calculator.userInputEqSuggestion = Calculator.lastEquations.pop(); // replace with peek if you dont want to remove
			}
			input = "";
			return "~~~~";
		} else if (input.substring(0,1).equals("/")) {
			String output = Commands.parseCommand(input,this);
			input = "";
			Calculator.userInputEqSuggestion = "";
			return output;
		}else {
			Calculator.lastEquations.push(input);
		}
		
	
		
		
		Calculator.userInputEqSuggestion = ""; // reset the equation suggestion
	
		out.println("Input: " + input);
		
		
		
		try {
			createTree(input);
			Commands.applyVariables(this);
			
			
			
			Calculator.enableJFrameOutput = jfo;
			return toString();
		}catch(Exception e) {
			e.printStackTrace();
			
			Calculator.warn("Exception occured whilst parsing:\n" + e);
			System.out.println("StackTrace of source exception: \n" + e.getStackTrace().toString());
			
			Calculator.enableJFrameOutput = jfo;
			return "Exception occured whilst parsing:\n" + e;
		}
		
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
	
	/**
	 * Used by createTree(EquationNode)
	 * Searches tree for variableNodes and adds them to the variables list
	 * @param equationNode
	 */
	private void recursiveAddVariables(EquationNode equationNode) {
		System.out.println(equationNode.toString());
		if (equationNode instanceof One_subNode_node) {
			recursiveAddVariables(((One_subNode_node) equationNode).getSubNode());
		}else if (equationNode instanceof Two_subNode_node) { 
			recursiveAddVariables(((Two_subNode_node) equationNode).getLeftSubNode());
			recursiveAddVariables(((Two_subNode_node) equationNode).getRightSubNode());
		}else if (equationNode instanceof VariableNode) {
			variables.add((VariableNode) equationNode);
		}
	}
	/**
	 * creates this equation from a pre-defined tree
	 * @param equationNode
	 */
	public void createTree(EquationNode equationNode) {
		setSubNode(equationNode);
		recursiveAddVariables(equationNode);
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
			
		System.out.println("Equation with aliases: " + equation);
		
		//create nodes
		
		
		EquationNode[] nodes = new EquationNode[0];


		String mode = "unknown";
		String prevMode = "unknown";
		String inputBuffer = "";
		String strAcqBuffer = "";
		int parenthesisLevel = 0;
		
		boolean space = false;
		boolean doubleQuote = false;
	
		String cChar = "";
		for (int i = 0; i < equation.length()+1; i++) {	
	
			if (i < equation.length()) {
				cChar = equation.substring(i, i+1);
					
				if (printInProgress) out.println("Parsing character: " + cChar + "   Index: " + i + "  in equation: " + equation);
				
				
				if (mode.equals("stringAcquisition")) {
					if ( (doubleQuote && cChar.equals("\"")) || (! doubleQuote && cChar.equals("'")) ) {
						VariableNode newVariable = new VariableNode(this, new StringValueNode(strAcqBuffer),parenthesisLevel);
						variables.add(newVariable);
						nodes = addToNodesArray(newVariable,nodes); //add a new VariableNode with the variable name
						inputBuffer = ""; //clear the inputBuffer
						strAcqBuffer = "";//clear the stringbuffer
						prevMode="unknown";
						mode = "unknown";
						continue;
					}else {
						strAcqBuffer += cChar;
						continue;
					}
					
				}
				
					
					
				if (cChar.equals(" ")) {
					space = true;
					continue;
				} else if (cChar.equals("\""))  { 
					mode = "stringAcquisition";
					doubleQuote = true;
				}else if (cChar.equals("'")) {
					mode = "stringAcquisition";
					doubleQuote = false;
				}else if ( cChar.equals("(") ) { //it is an open-parenthesis, and the parenthesis level goes up
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
			
			
			if (indexOf(inputBuffer,operationKeywords) != -1) { //catch if we have two operations in a row ex: 1 + sin(25) or 1 + _3
				nodes = addToNodesArray(createOperation(inputBuffer,parenthesisLevel,mode),nodes);
				inputBuffer = "";
				prevMode = "unknown";
			}
			
			
			
			if ( (! prevMode.equals("unknown")) && ( (! prevMode.equals(mode)) || space )  ) {
				if (printInProgress) out.println("modeChange:" + inputBuffer);
				
				if (prevMode.equals("letterInput") && (! mode.equals("multi-char-operation"))) { //create a variable 
					VariableNode newVariable = new VariableNode(this, inputBuffer,parenthesisLevel);
					variables.add(newVariable);
					nodes = addToNodesArray(newVariable,nodes); //add a new VariableNode with the variable name
					inputBuffer = ""; //clear the inputBuffer
				}else if (prevMode.equals("numberInput")) { //create a value
					nodes = addToNodesArray(new VariableNode(this, Double.parseDouble(inputBuffer),parenthesisLevel),nodes); //add a new VariableNode with the variable name
					inputBuffer = ""; //clear the inputBuffer
					
				}else if (prevMode.equals("operation") || prevMode.equals("multi-char-operation") || (prevMode.equals("letterInput") && space) ) {
					if (indexOf(inputBuffer,operationKeywords) != -1) {
						nodes = addToNodesArray(createOperation(inputBuffer,parenthesisLevel,mode),nodes);
					}else { //treat operations that the calculator doesn't recognize as a variable name
						
						
						//this new code treats multi-char strings that aren't operations as variables
						VariableNode newVariable = new VariableNode(this, inputBuffer,parenthesisLevel);
						variables.add(newVariable);
						nodes = addToNodesArray(newVariable,nodes); //add a new VariableNode with the variable name
						
						
						
						
						
						/* this old code treated multi-char strings that aren't operations as an error
						Exception e = new Exception("operation not found in operations array: " + inputBuffer);
						e.printStackTrace(out);
						if (JOptionPane_error_messages) calculatorAnchor.showMessageDialog(calculatorAnchor, e.toString() + "\n" + e.getStackTrace().toString());
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
			
			prevMode = mode;
			
			 
			
		}
		
		if (printInProgress) printNodeArray(nodes);
		
		if (mode.equals("stringAcquisition")) {
			Exception e = new Exception("StringError: missing closing \"");
			e.printStackTrace(out);
			if (Calculator.enableJFrameOutput) Calculator.calculatorAnchor.showMessageDialog( e.toString() + "\n" + e.getStackTrace().toString());	
		}
		if (parenthesisLevel > 0) {
			Exception e = new Exception("ParenthesisError: missing close-parenthesis");
			e.printStackTrace(out);
			if (Calculator.enableJFrameOutput) Calculator.calculatorAnchor.showMessageDialog( e.toString() + "\n" + e.getStackTrace().toString());
		}else if (parenthesisLevel < 0) {
			Exception e = new Exception("ParenthesisError: missing open-parenthesis");
			e.printStackTrace(out);
			if (Calculator.enableJFrameOutput) Calculator.calculatorAnchor.showMessageDialog( e.toString() + "\n" + e.getStackTrace().toString());
		}
		
		
		//create tree linkups
		
		
		
		return getTree(nodes);
		
	}
	
	public void setDegRadMode(DegOrRadValue degorrad ) {
		degRadMode = degorrad;
		Commands.mostRecentDegRadMode = degRadMode;
	}
	
	private int indexOf(String strToFind, ArrayList<String> arrLst) {
		int indx = 0;
		for (String s : arrLst) {
			if (s.equals(strToFind)) {
				return indx;
			}
			indx++;
		}
		return -1;
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
			
			double nLevel = n.getParenthesisLevel() * operations.size() + n.getOrderOfOpsLevel();
			double lowestLevel = lowestNode.getParenthesisLevel() * operations.size() + lowestNode.getOrderOfOpsLevel();
			if ( nLevel < lowestLevel) {
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
					if (Calculator.enableJFrameOutput) Calculator.calculatorAnchor.showMessageDialog( e.toString() + "\n" + "Node: " + node.toString() + "\nOpsLvl: " + node.orderOfOpsLevel);
				}
				
				node.setSubNode(getTree(resizeNodesArray(arr,lowestIndx+1,arr.length-1)));
			}catch(ClassCastException e) {
				if (printInProgress) out.println("valueNode");
			}
		}
		
		
		return lowestNode;
	}
	
	private EquationNode createOperation(String op, int parenthesisLevel, String mode) {
		
		cMode = mode;
		EquationNode node = null;
		
		EquationNode opType = operations.get(indexOf(op,operationKeywords));
		
		node = opType.createNewInstanceOfOperation(this);
		
		if (node == null) {
			Exception e = new Exception("operation not found in createOperation: " + op);
			e.printStackTrace(out);
			if (Calculator.enableJFrameOutput) Calculator.calculatorAnchor.showMessageDialog( e.toString() + "\n" + e.getStackTrace().toString());
		}
		/*
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
			if (JOptionPane_error_messages) calculatorAnchor.showMessageDialog(calculatorAnchor, e.toString() + "\n" + e.getStackTrace().toString());
			break;
			
		}
		*/
		
		node.setParenthesisLevel(parenthesisLevel);
		
		return node;
	}
	
	
	@Override
	public double getValue() {
		
		if (! isCalculated()) {
			super.getValue();
			prevAns = valueData;
			return prevAns.getValue();
		}else {
			return valueData.value;
		}
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
		getValue();
		return getSubNode();
	}
	
	/**
	 * {@summary calculates the inputted equation and returns string output}
	 * @param input
	 * @return
	 */
	public String calculate(String input) {
		Calculator.enableJFrameOutput = false;
		Commands.addVariable("ans", prevAns.getValueData(), null);
		Calculator.enableJFrameOutput = true;
		if (input.substring(0,1).equals("/")) {
			String commandOutput = Commands.parseCommand(input, this);
			return Calculator.verboseOutput ? commandOutput : "";	
		}else {
			createTree(input);
			Commands.applyVariables(this);
			return evaluate().getValueData().toString();
		}
		
		
	}
	
	@Override
	public double operation(double a) {
		return a;
	}
	
	@Override
	public ValueNode operation(ValueNode a, ValueNode outputNode) {
		return outputNode = a;
	}
	
	/**
	 *  {@summary replaces all variables with the passed name with the passed AdvancedValueNode}
	 * @param varName the name of the variables to replace
	 * @param value the AdvancedValueNode to replace the variables with 
	 */
	public boolean setAdvancedVariableValue(String varName, ValueNode value) {
		boolean varFound = false;
		
		for (VariableNode n : variables) {
			if (n.getName().equals(varName)) {
				n.setValueData(value);
				n.setValue(value.getValue());
				n.setName(varName);
				n.notCalculated();
				
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
			if (Calculator.enableJFrameOutput) Calculator.calculatorAnchor.showMessageDialog( e.toString() + "\n" + e.getStackTrace().toString());
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
			if (Calculator.enableJFrameOutput) Calculator.calculatorAnchor.showMessageDialog( e.toString() + "\n" + e.getStackTrace().toString());
			return null;
		}
	}
	
	
	
	
	
	

	public void setPrintStream(PrintStream outputStream) {
		out = outputStream;
	}
	
	@Override
	public String toString() {
		return evaluate().getDataStr();
	}

	@Override
	public String getOperationKeyword() {
		return null;
	}
	@Override
	public void test() { 
		Calculator.warn(getClass() + " is not tested and should not be used");
	}
	@Override
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		Calculator.error("createNewInstanceOfOperation MUST be overriden by every operation Offender: " + getClass());
		return null;
	}
	
	
	
}
