package calculatorv2_scientific_operations;


import calculatorv2_basic_operations.Addition;
import calculatorv2_basic_operations.Multiplication;
import calculatorv2_basic_operations.Round;
import calculatorv2_core.AdvancedValueNode;
import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.Equation.DegOrRadValue;
import calculatorv2_core.EquationNode;
import calculatorv2_core.FunctionNode;
import calculatorv2_core.StringValueNode;
import calculatorv2_core.ValueNode;
import calculatorv2_matrix_core.Bra;

// integrate( function , "vartointover" , lowerBound, upperBound     , precision)
public class DefiniteIntegral extends FunctionNode {
	
	public ValueNode function(EquationNode[] params, ValueNode outputNode) {
		if (! Calculator.Assert(params.length > 3, getClass() + " must have at least two parameters")) return outputNode;
		
		double precision = 0.0001;
		
		// get parameters
		
		Equation equation;
		String eqString;
		
		if (params[0] instanceof StringValueNode) {
			eqString = ((StringValueNode) params[0]).getString();
		
		}else {
			
			eqString = params[0].convertEquationToString();
			Calculator.warn(getClass() + " should be used exclusively with strings for equation input");
			
			
			
		}
		
		equation = new Equation(eqString);
		
		
		String variableName = ((StringValueNode) params[1]).getString();
		
		ValueNode lowerBound = params[2].getValueData();
		ValueNode upperBound = params[3].getValueData();
		
		if (params.length > 4) {
			precision = params[4].getValue();
		}
		
		
		if (false) {
			double step = precision*(upperBound.getValue()-lowerBound.getValue())/1000;
			double integral = 0;
			for (double i = lowerBound.getValue(); i < upperBound.getValue(); i+= step) {
				equation.setVariableValue(variableName,i);
				integral += equation.solve()*step;
			}
			
			outputNode.setValue(integral);
		}else { //this algorithm is not operational
			ValueNode step = new ValueNode(precision*(upperBound.getValue()-lowerBound.getValue())/1000);
			
			Addition add = new Addition();
			Multiplication multi = new Multiplication();
			outputNode = new ValueNode(0);
			ValueNode answer;
			
			for (; lowerBound.getValue() < upperBound.getValue(); lowerBound = add.operation(lowerBound,step,new ValueNode(0)) ) {
				equation.setAdvancedVariableValue(variableName,lowerBound);
				answer = equation.evaluate().getValueData();
				answer = multi.operation(answer,step,new ValueNode(0));
				outputNode = add.operation(outputNode,answer, new ValueNode(0));
				//System.out.println("i: " + lowerBound + "  eqAns: " + equation.evaluate().getValueData() + " eqAnswStep: " + answer + "  integral " + outputNode);
			}
			
			
		}
		
		
		if (!(outputNode instanceof AdvancedValueNode)) {
			Round rounder = new Round();
			rounder.setSubNode(new Bra(new ValueNode[] {outputNode,new ValueNode(-Math.log10(precision))}));
		
			outputNode = rounder.getValueData();
		}
		
		calculated();
		
		return outputNode;
	}
//integrate( function , "vartointover" , lowerBound, upperBound     , precision)
	
	@Override
	public String getParameterInputs() {
		return "integrate( \"function\" , \"vartointover\" , lowerBound, upperBound , precision=0.000001)";
	}
	

	
	public String getOperationKeyword() {
		return "integrate";
	}
	
	public void test() { 
		Equation eq = new Equation();
		eq.importAll();
		
		Calculator.testEquation(eq, "integrate(\"x + 1\",\"x\",0,10,1)",60);
	
		eq.setDegRadMode(DegOrRadValue.radians);
		Calculator.testEquation(eq,"integrate(\"sin(x)\",\"x\",0,10,0.01)",1.84);
		
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new DefiniteIntegral();
	}

}
