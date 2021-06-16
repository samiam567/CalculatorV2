package calculatorv2_visualization;



import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.FunctionNode;
import calculatorv2_core.StringValueNode;
import calculatorv2_core.ValueNode;

public class GraphEquation extends FunctionNode {
	
	private static Grapher grapher = new Grapher();
	
	public ValueNode function(EquationNode[] params, ValueNode outputNode) {
		if (! Calculator.Assert(params.length > 0, getClass() + " must have at least one parameter")) return outputNode;
		
		double precision = 1;
		
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
		
		
		String variableName = "x";
		
		ValueNode lowerBound = new ValueNode(-500);
		ValueNode upperBound = new ValueNode(500);
		
		if (params.length > 1) {
			variableName = ((StringValueNode) params[1]).getString();
		
			
			
			
			if (params.length > 2) {
				lowerBound = params[2].getValueData();
				
				if (params.length > 3) {
					upperBound = params[3].getValueData();
					
					if (params.length > 4) {
						precision = params[4].getValue();
					}
				}
			}
				
		}
		
		
		Graph graph = new Graph();
		
		double step = precision*(upperBound.getValue()-lowerBound.getValue())/100;
	
		for (double i = lowerBound.getValue(); i < upperBound.getValue(); i+= step) {
			equation.setVariableValue(variableName,i);
			graph.addPoint(new GraphPoint(i,equation.solve()));
		}
		
		
		grapher.addGraph(graph);
		
		grapher.updateGraph();
		calculated();
		
		return outputNode;
	}

	@Override
	public String getParameterInputs() {
		return "graph( \"function\" , \"varToPutOnXAxis\"=\"x\" , lowerBound=_500, upperBound=500 , precision=1)";
	}
	
	
	
	public String getOperationKeyword() {
		return "graph";
	}
	
	public void test() { 
		Calculator.warn(getClass() + " is not tested and should not be used");
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new GraphEquation();
	}
}
