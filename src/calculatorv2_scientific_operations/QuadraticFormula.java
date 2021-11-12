package calculatorv2_scientific_operations;


import calculatorv2_core.Calculator;
import calculatorv2_core.Commands;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.FunctionNode;
import calculatorv2_core.StringValueNode;
import calculatorv2_core.ValueNode;
import calculatorv2_core.Equation.DegOrRadValue;
import calculatorv2_matrix_core.Bra;


public class QuadraticFormula extends FunctionNode {
	public ValueNode function(EquationNode[] params, ValueNode outputNode) {
		
		if (params.length != 3) {
			Calculator.warn("incorrect number of params for quadForm\n" + getParameterInputs());
			return new StringValueNode("incorrect number of params for quadForm");
		}
		
		double a = params[0].getValue();
		double b = params[1].getValue();
		double c = params[2].getValue();
		
		double radValue = b*b-4*a*c;
		
		ValueNode ans1, ans2;
		if (radValue < 0) {
			ans1 = new ComplexValueNode(-b/2/a,Math.sqrt(-radValue)/2/a);
			ans2 = new ComplexValueNode(-b/2/a,-Math.sqrt(-radValue)/2/a);
		}else {
			ans1 = new ValueNode( ( -b + Math.sqrt(radValue) ) /2/a );
			ans2 = new ValueNode( ( -b - Math.sqrt(radValue) ) /2/a );
		}
		
		return new Bra(new ValueNode[] {ans1, ans2} );
	}
	
	@Override
	public String getParameterInputs() {
		return "quadForm(a,b,c)";
	}
	
	@Override
	public String getEquation() {
		return "solves ax^2 + bx + c for roots";
	}
	
	@Override
	public String getOperationKeyword() {
		return "quadForm";
	}
	
	@Override
	public void test() { 
		Equation testEq = new Equation();
		
		testEq.importAll();
		
		testEq.setDegRadMode(DegOrRadValue.degrees);
		
		boolean prevOutputEnable = Calculator.enableJFrameOutput;
		Calculator.enableJFrameOutput = false;
		
		

		
		
		Calculator.testEquation(testEq,"quadForm(1,2,5)","<-1.0 + 2.0i,-1.0 + -2.0i|",-1.0);
		Calculator.testEquation(testEq,"quadForm(3,5,12)","<-0.833333333 + 1.818118686i,-0.833333333 + -1.818118686i|",-0.8333333333333334);
		Calculator.testEquation(testEq,"quadForm(1,5,6)","<-2.0,-3.0|",-2.0);
		
		Commands.parseCommand("/V = exp(i×53.1)",testEq);
		Commands.parseCommand("/I = 1",testEq);
		Calculator.testEquation(testEq,"Round(getValue(V-0.8×i×I),1)",0.6);
		
		Calculator.enableJFrameOutput = prevOutputEnable;
	}
	
	@Override
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new QuadraticFormula();
	}

}