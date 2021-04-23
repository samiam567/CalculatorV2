package calculatorv2_scientific_operations;

import java.util.Random;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.One_subNode_node;

/**
 * {@summary i'm not convinced this works}
 * @author apun1
 *
 */
@Deprecated
public class Rand extends One_subNode_node {
	
	static Random rand;
	
	public Rand() {

		rand = new Random();
	}
	
	@Override
	protected double operation(double a) {

		return rand.nextDouble();
		
	}
	
	@Override
	public double getValue() {
		if (! isCalculated()) {
			valueData.setValue(operation(getSubNode().getValue())); 
			
			//make all of the parents not calculated so that we have to get a new rand number...
			notCalculated(); 
			//but this node is calculated so that we don't re-set the seed
			calculated();
			
		
			return valueData.getValue();
		}else {
			notCalculated();
			
			return valueData.getValue();
		}
	}
	
	public String toString() {
		return "rand";
	}
	
	public String getOperationKeyword() {
		return "rand";
	}
	
	public void test() { 
		Calculator.warn(getClass() + " is not tested and should not be used");
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Rand();
	}
	
}
