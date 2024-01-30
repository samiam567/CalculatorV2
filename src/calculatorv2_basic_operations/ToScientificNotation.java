package calculatorv2_basic_operations;

import calculatorv2_core.AdvancedValueNode;
import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;
import calculatorv2_core.ScientificValueNode;
import calculatorv2_core.StringValueNode;
import calculatorv2_core.ValueNode;
import calculatorv2_matrix_core.Bra;
import calculatorv2_matrix_core.Bra_ket;
import calculatorv2_matrix_core.Ket;
import calculatorv2_matrix_core.MatrixNode;
import calculatorv2_scientific_operations.Comparation;
import calculatorv2_scientific_operations.ComplexValueNode;

public class ToScientificNotation extends One_subNode_node {
	
	public ToScientificNotation() {
		orderOfOpsLevel = 3;
	}
	
	@Override
	public String getOperationKeyword() {
		return "sci";
	}

	@Override
	public void test() {
	
		Equation testEq = new Equation();
		testEq.importAll();
		
		boolean prevOutputEnable = Calculator.enableJFrameOutput;
		Calculator.enableJFrameOutput = false;
	
		
		Calculator.testEquation(testEq,"sci(1E-3)",0.001);
		Calculator.testEquation(testEq,"sci(1E3)",1000);
		Calculator.testEquation(testEq,"sci(5000*(27.6E6))",1.38E11);
		Calculator.testEquation(testEq,"sci(5000*(27.6E6))","1.38E11",1.38E11);
		Calculator.testEquation(testEq,"sci(pi)","3.141592654E0",3.141592653589793);
		Calculator.enableJFrameOutput = prevOutputEnable;
	}
	
	protected double operation(double a) {
		if (Equation.printInProgress) System.out.println("sqrt" + a);
		return Math.sqrt(a);
	}
	
	
	
	public static ValueNode operationStat(ValueNode n1, ValueNode outputNode) {
		
		if (n1 instanceof AdvancedValueNode) {
			if (n1 instanceof Bra_ket) {
				Bra_ket bkN1 = (Bra_ket) n1;
				
				ValueNode[] values = bkN1.getValues();
				
				if (!(outputNode instanceof Bra_ket)) {
					if (bkN1.isBra()) {
						outputNode = new Bra(values.length);
					}else {
						outputNode = new Ket(values.length);
					}
				}
				Bra_ket out = (Bra_ket) outputNode;
				
				for (int i = 0; i < values.length; i++) {
					out.getValues()[i] = operationStat(values[i], values[i]);
				}
			}else if (n1 instanceof Comparation || n1 instanceof StringValueNode) {
				return n1;
			}else if (n1 instanceof MatrixNode || n1 instanceof ComplexValueNode ) {
				Calculator.warn("Converting MatrixNode or ComplexValueNode to scientific notation has not been implemented yet.");
				return n1;
			}
		} else {
			return new ScientificValueNode(n1.getValue());
		}

		
		return outputNode;
	}
	
	
	public ValueNode operation(ValueNode n1, ValueNode outputNode) {
		return operationStat(n1,outputNode);
	}

	public String toString() {
		return "sci";
	}
	
	@Override
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new ToScientificNotation();
	}

}
