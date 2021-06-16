package calculatorv2_scientific_operations;


import calculatorv2_basic_operations.Addition;
import calculatorv2_basic_operations.Division;
import calculatorv2_basic_operations.Multiplication;
import calculatorv2_basic_operations.Round;
import calculatorv2_basic_operations.Subtraction;
import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.FunctionNode;
import calculatorv2_core.StringValueNode;
import calculatorv2_core.ValueNode;
import calculatorv2_matrix_core.Bra;

// integrate( function , "vartointover" , lowerBound, upperBound     , precision)
public class DefiniteIntegral extends FunctionNode {
	public ValueNode function(EquationNode[] params, ValueNode outputNode) {
		if (! Calculator.Assert(params.length > 3, getClass() + " must have at least two parameters")) return outputNode;
		
		double precision = 0.000001;
		
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
		
		
		if (true) {
			double step = precision*(upperBound.getValue()-lowerBound.getValue())/100;
			double integral = 0;
			for (double i = lowerBound.getValue(); i < upperBound.getValue(); i+= step) {
				equation.setVariableValue(variableName,i);
				integral += equation.solve()*step;
			}
			
			outputNode.setValue(integral);
		}else { //this algorithm is not operational
			ValueNode step = new ValueNode(0.0001);//(new Division()).operation((new Subtraction()).operation(upperBound,lowerBound,new ValueNode(0)), new ValueNode(precision),new ValueNode(0));
			
			Addition add = new Addition();
			Multiplication multi = new Multiplication();
			ValueNode integral = new ValueNode(0);
			for (; lowerBound.getValue() < upperBound.getValue(); lowerBound = add.operation(lowerBound,step,new ValueNode(0)) ) {
				equation.setAdvancedVariableValue(variableName,lowerBound);
				integral = multi.operation(add.operation(equation.evaluate().getValueData(),integral,new ValueNode(0)),step,new ValueNode(0));
				
			}
			
			
		}
		
		
		
		Round rounder = new Round();
		rounder.setSubNode(new Bra(new ValueNode[] {outputNode,new ValueNode(-Math.log10(precision)-1)}));
		
		outputNode = rounder.getValueData();
		
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
		Calculator.warn(getClass() + " is not tested and should not be used");
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new DefiniteIntegral();
	}

}
