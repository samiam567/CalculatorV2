package calculatorv2_core;


import java.util.Stack;

import javax.swing.JOptionPane;

import calculatorv2_core.Equation.DegOrRadValue;
import calculatorv2_socketServerHandler.Socket_handler;


public class Calculator {
	
	public static boolean verboseOutput = true;
	public static boolean enableJFrameOutput = true;
	
	static final String[] run_flags = {"--verbose-output","--user-calculator","--socket-server"};
	
	//used by the runUserCalculator method
	static UserCalculatorInputFrame calculatorAnchor = new UserCalculatorInputFrame();
	static String userInputEqSuggestion = "";
	static Stack<String> lastEquations = new Stack<String>();
	
		
	private static class EquationError extends Exception {
		private static final long serialVersionUID = -5372388435013231712L;
		public EquationError(String err) {
			super(err);
		}
	}
	
	public static ValueNode evaluate(String equation) {
		return (new Equation(equation)).evaluate().getValueData();
	}
	
	public static String calculate(String equation) {
		return (new Equation(equation)).evaluate().getValueData().toString();
	}
	
	public static void error(String errorMessage) {
		EquationError err = new EquationError(errorMessage);
		
		String stStr = "";
		for (StackTraceElement s : err.getStackTrace()) {
			stStr += s.toString() + "\n";
		}
		
		//show to JopPane
		JOptionPane.showMessageDialog(null, err.toString() + "\nAt:\n" + stStr,"ERROR", JOptionPane.ERROR_MESSAGE);
		
		err.printStackTrace();
	}
	
	public static void main(String[] args) { 
		boolean userCalculator = false;
		if (args.length > 0) {
			enableJFrameOutput = false;
			Equation.printInProgress = false;
			verboseOutput = false;
			
			
	
			Equation calc = new Equation();
			calc.out = System.err;
			calc.importAll();
			calc.importStandardConstants();
			
			calc.setDegRadMode(DegOrRadValue.radians);
			
			for (int i = 0; i < args.length; i++) {
				try {
					if (args[i].substring(0,2).equals("--")) { //this is a command
						
						if (args[i].equals(run_flags[0]) ) { //verbose_output
							verboseOutput = true;
						}else if (args[i].equals(run_flags[1])) { //user-calculator
							userCalculator = true;
							enableJFrameOutput = true;
							verboseOutput = true;
						}else if (args[i].equals(run_flags[2])) { //start socket server
							System.out.println("starting socket server");
							if (args.length-i <= 2 ) {
								System.out.println("ERROR: too few arguments after " + run_flags[1] + " flag.");
							}else {
								try { 
									int port = Integer.parseInt(args[i+1]);
									String mode = args[i+2];
									i+=2;
									Socket_handler server = new Socket_handler(mode,port,calc,userCalculator);
									server.start();
							        
								}catch(NumberFormatException n ) {
									System.out.println(n);
								}
							}
							
							
						}else {
							System.out.println("invalid/unrecognized command line argument: " + args[i]);
							System.out.print("possible options: ");
							for (String s : run_flags) {
								System.out.print(s + "   ");
							}
							System.out.println();
						}
					}else {
						String ans;
						if (userCalculator) {
							calc.queryUserCalculator(args[i]);
						}else {
							ans = calc.calculate(args[i]);
							System.out.println(ans);
						}
						 
					
						
					}
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
			
	
			
		}else {
			enableJFrameOutput = true;
			runUserCalculator(new Equation());
		}
	}
	
	public static boolean Assert(boolean b, String failurePhrase) {
		if (! b) Calculator.warn(failurePhrase);
		return b;
	}
	
	public static boolean Assert(boolean b) {
		return Assert(b,"Assertion failed");
	}
	
	public static void println(String s) {
		if (verboseOutput) {
			System.out.println(s);
		}
	}
	
	
	/*
	 * A list of all the known issues with the calculator
	 */
	public static void knownIssues() {
		String knownIssues = "Known Issues:\n";	

		//add knownIssues here
	
		
		
		
		//--------------------
		
		if (knownIssues != "Known Issues:\n") {
			warn(knownIssues);
		}
	}
	public static void warn(String warning) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		
		
		// Print the warning with its stacktrace
		String stStr = "";
		for (StackTraceElement s : stackTrace) {
			stStr += s.toString() + "\n";
		}
		println("WARNING: " + warning + " at: \n" + stStr + " \n --" );
		
		//show to JopPane
		if (enableJFrameOutput) JOptionPane.showMessageDialog(null, warning,"WARNING", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void runUserCalculator(Equation eq) {
		runUserCalculator(eq, false);
	}
	
	public static void runUserCalculator(Equation eq, boolean testEquations) {
		
		
		
		eq.importStandardConstants();
		
		//import operations
		
		eq.importAll();
		
		eq.setDegRadMode(DegOrRadValue.degrees);
		Commands.mostRecentDegRadMode = DegOrRadValue.degrees;
		
		Calculator.enableJFrameOutput = false;
	
		if (testEquations) {
			eq.out.println("Test took " + Calculator.testCalculator() + " nanos to evaluate " + Calculator.testNum + " test equations");
		}
	
	
		
		
		//warn the user about known issues
		Calculator.knownIssues();
		
		String input, output;
		while (true) { //if the user presses cancel the program will automatically terminate
			
			do {
				input = (String) calculatorAnchor.showInputDialog("Type in what you want to solve","Calculator V2",1, null,null, userInputEqSuggestion);
			}while (input.length() == 0);
				
			output = eq.queryUserCalculator(input);
			
			eq.out.println("Output: " + output);
			if (! ( output.startsWith("~~~~") || output.contains("Exception occured whilst parsing") ) ) {
			
				// format the answer to look pretty
				if (Commands.outputFormat == 0) {
					calculatorAnchor.showMessageDialog(eq.toString(),input, 1);
				}else if (Commands.outputFormat == 1) {
					String ansStr = input + " =\n";
					for (int i = 0; i < eq.toString().length(); i++) ansStr += " ";
					
					int numSpaces = ansStr.length() - eq.toString().length();
				
					for (int i = 0; i < numSpaces; i++) ansStr += " ";
					ansStr += eq.toString();
					
					calculatorAnchor.showMessageDialog( ansStr,"Answer:", 1);
				}else {
					calculatorAnchor.showMessageDialog(eq.toString());
				}
				
				
				
				
			}
			
		}
		
		
	}
	
	
	
	
	
	
	private static boolean allTestsPassed = true;
	private static int testNum = 0;
	@SuppressWarnings("unused")
	static long testCalculator() {
		println("Testing calculator to ensure accuracy...");
		
		println("Building equations...");		
	
		long start = System.nanoTime();
		
		Equation testEq = new Equation();
		
		//import the operations
		testEq.importAll();
		
		testEq.setDegRadMode(DegOrRadValue.radians);
		
		testEquation(testEq,"1 + 2 * 6^2",1 + 2 * Math.pow(6,2)); //get a little more complicated, test negative exponents
		testEquation(testEq,"((4^2*3-45)^(1+1*4) / 3) * 2",(Math.pow((Math.pow(4,2)*3-45),(1+1*4)) / 3) * 2 ); //REALLY complicated
		testEquation(testEq,"45/2 + sin(10-5)/3",45D/2 + Math.sin(10-5) / 3 ); //testing Sine
		testEquation(testEq,"4rt(tan(atan(0.12))) + 13-sqrt4",Math.pow( (Math.tan(Math.atan(0.12))),1.0/4 ) + 13-Math.sqrt(4) ); //test sin, asin,sqrt,rt
		testEquation(testEq,"sqrt(3^2 + 4^2) * ( (abs(3)/3 * abs(4)/4) * (_abs(3)/3 + _abs(4)/4) - 1)",Math.sqrt(3*3 + 4*4) * ( (Math.abs(3)/3 * Math.abs(4)/4) * (-Math.abs(3)/3 + -Math.abs(4)/4) - 1)); //test abs and negatives
		testEquation(testEq,"1/2*3/2",1D/2*3/2); //test left to right execution
		testEquation(testEq,"atan(_(2rt(3)))",Math.atan(-Math.sqrt(3))); //testing arctan
		testEquation(testEq,"4*10^_31*sin24",4*Math.pow(10, -31)*Math.sin(24)); //testing negative exponents
		testEquation(testEq,"sin8+cos9+tan11*asin0.1+acos0.2+atan0.453",Math.sin(8)+Math.cos(9)+Math.tan(11)*Math.asin(0.1) + Math.acos(0.2) + Math.atan(0.453)); //test all trig functions
		testEquation(testEq,"isPrime(342)",0); //test isPrime
		testEquation(testEq,"%err(1,2)",-50); //test percent error and aliases
		testEquation(testEq,"4rt(tan(atan(0.12))) + 13-sqrt4 isEqualTo 4rt(tan(atan(0.12))) + 13-sqrt4",1);
		testEquation(testEq,"324*tan(34)*26/2 == 1*324*tan(34)*26/2+1-1",1);
		testEquation(testEq,"1 <=> 2",-1);
		testEquation(testEq,"2 <=> 1", 1);
		testEquation(testEq,"34+ 12 *23124 <=> sin23",1);
		testEquation(testEq,"324*tan(34)*26/2 compareTo 1*324*tan(34)*26/2+1-1",0);
		testEquation(testEq,"round(1.2123) == 1",1);
		testEquation(testEq,"round(1.2123,1)",1.2);
		testEquation(testEq,"5E10",5*Math.pow(10,10));
		testEquation(testEq,"sin2E_5",Math.sin(2*Math.pow(10,-5)));
		testEquation(testEq,"0.3^3E_6",Math.pow(0.3,3*Math.pow(10,-6)));		
		testEquation(testEq,"%err(e,pi)",-13.474402056773494);
		testEquation(testEq,"solveEquation(\"x\",\"10\")",10);
		testEquation(testEq,"solveEquation(\"x+1\",\"10\")",9);
		testEquation(testEq,"solveEquation(\"18\",\"x+20\")",-2);
		testEquation(testEq,"i+1","1.0 + 1.0i",1);
		testEquation(testEq,"(3 + 2*i)*(1 + 7*i)","-11.0 + 23.0i",-11);
		testEquation(testEq,"(7 + 2.1*i)/(1.5 -4*i)","0.115068493 + 1.706849315i",0.11506849315068492);
		testEquation(testEq,"1/(1+i)","0.5 + -0.5i",0.5);
		testEquation(testEq,"\"hello\"", "\"hello\"", 0);
		testEquation(testEq,"\"hello\"+1", 6);
		testEquation(testEq,"\"hello my name is 'alec'\"","\"hello my name is 'alec'\"", 0);
		
		testEq.createTree("((4^2*3-45)^(1+1*4) / 3) * 2"); //test equation re-usability
		if (testEq.solve() == ((Math.pow((Math.pow(4,2)*3-45),(1+1*4)) / 3) * 2 )) { 
			testEquation(testEq,"1",1); //pass
		}else {
			testEquation(testEq,"0",1); //fail
		}
		
		//test all operations
		EquationNode opType;
		for (int i = 0; i < testEq.operations.size(); i++) {
			opType = testEq.operations.get(i);
			opType.test();	
		}
		
	
		
		long end = System.nanoTime();
		
		if (allTestsPassed) {
			println("test complete. All systems functional");
		}else {
			println("test FAILED. One or more equations gave an incorrect answer.");
			JOptionPane.showMessageDialog(null, "Calculator Test failed. One or more equations did not yield a correct answer");
		}
					
		return end-start;
	}
	
	private static class EquationTestFailedException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4671963134340730291L;

		public EquationTestFailedException(String eStr) {
			super(eStr);
		}
	}
	
	
	public static boolean testEquation(Equation testEq, String eq, double answer) {

		testEq.createTree(eq);
		
		Commands.applyVariables(testEq);
	
		
		
		String outStr = "Equation " + testNum + " ";
		testNum++;
		if (testEq.solve() == answer) {
			outStr += "passed";
			println(outStr);
			return true;
		}else {
			outStr += "failed!";
			outStr += "\nCalculated Answer: " + testEq.solve() + "   Actual Answer:  " + answer + "      ValueData: " + testEq.getValueData();
			outStr += "\nEquation to solve: " + eq;
			println(outStr);
			(new EquationTestFailedException(outStr)).printStackTrace();
			calculatorAnchor.showMessageDialog(outStr);
			allTestsPassed = false;
			return false;		
		}	
	}
	public static boolean testEquation(Equation testEq,String eq, String answerStr, double ans) {

		
	
		testEq.createTree(eq);
		Commands.applyVariables(testEq);
	
		
		String outStr = "Equation " + testNum + " ";;
		testNum++;
		if ( (testEq.getValueData().toString()).equals(answerStr) && testEq.solve() == ans) {
			outStr += "passed";
			println(outStr);
			return true;
		}else {
			outStr += "failed!\n";
			outStr += "Calculated ValueData " + testEq.getValueData().toString() + "       Target ValueData: " + answerStr + "\nCalculated Answer: " +testEq.solve() + "   Actual Answer: " + ans;
			outStr += "\nEquation to solve: " + eq;
			println(outStr);
			(new EquationTestFailedException(outStr)).printStackTrace();
			calculatorAnchor.showMessageDialog(outStr);
			allTestsPassed = false;
			return false;	
		}	
	}
	
}
