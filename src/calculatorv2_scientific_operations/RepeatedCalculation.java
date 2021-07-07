package calculatorv2_scientific_operations;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.FunctionNode;
import calculatorv2_core.StringValueNode;
import calculatorv2_core.ValueNode;


public class RepeatedCalculation extends FunctionNode {
	
	public ValueNode function(EquationNode[] params, ValueNode outputNode) {
		System.out.println("-----------------------------");
		System.out.println("repetedcalc");
		if (! Calculator.Assert(params.length >= 2, "Must have 2 parameters for " + getOperationKeyword())) return new ValueNode(-1);	
		String equation  = ((StringValueNode) params[0].getValueData() ).getString();
		double times = params[1].getValue();
		
		boolean reParse = false, printEachTime = false;
		if (params.length > 2 && params[2].getValue() != 0) {
			reParse=true;
		}
		if (params.length > 3 && params[3].getValue() != 0) {
			printEachTime = true;
		}
		
		if (printEachTime) {
			if (reParse) {
				Equation eq = new Equation();
				for (int i = 0; i < times; i++) {
					System.out.println(eq.calculate(equation));
				}
				outputNode = eq.getValueData();
			}else {
				Equation eq = new Equation(equation);
				for (int i = 0; i < times; i++) {
					System.out.println(eq.evaluate().getValueData().toString());
				}
				outputNode = eq.getValueData();
			}
		}else {
			if (reParse) {
				Equation eq = new Equation();
				for (int i = 0; i < times; i++) {
					eq.calculate(equation);
				}
				outputNode = eq.getValueData();
			}else {
				Equation eq = new Equation(equation);
				for (int i = 0; i < times; i++) {
					eq.solve();
				}
				outputNode = eq.getValueData();
			}
		}
	
		return outputNode;
	}
	
	public String toString() {
		return "repeat";
	}
	
	public String getOperationKeyword() {
		return "repeat";
	}
	
	public void test() { 
		Equation eq = new Equation();
		eq.importAll();
		Calculator.testEquation(eq,"repeat(\"1+1\",100)",2);
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new RepeatedCalculation();
	}
}
