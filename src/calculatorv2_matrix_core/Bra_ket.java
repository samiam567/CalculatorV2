package calculatorv2_matrix_core;

import java.util.Arrays;

import calculatorv2_basic_operations.Multiplication;
import calculatorv2_core.AdvancedValueNode;
import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.Two_subNode_node;
import calculatorv2_core.ValueNode;

public abstract class Bra_ket extends AdvancedValueNode implements Matrixable {
	private boolean bra = false;
	
	protected ValueNode[] values;
	
	public Bra_ket(boolean bra, int numVals) {
		super('k');
		this.bra = bra;
		values = new ValueNode[numVals];
		for (int i = 0; i < values.length; i++) {
			values[i] = new ValueNode(0);
		}
	}
	public Bra_ket(boolean bra) {
		super('k');
		this.bra = bra;
		values = new ValueNode[0];
	}
	
	public Bra_ket(ValueNode[] values, boolean bra) {
		super('k');
		this.bra = bra;
		this.values = values;
	}
	
	public int size() {
		return values.length;
	}
	
	public String toString() {
		return getValueDataStr();
	}
	
	public void setValue(int indx, ValueNode value) {
		getValues()[indx] = value;
		notCalculated();
	}
	
	public void setValues(ValueNode[] val) {
		values = val;
		notCalculated();
	}
	
	public void setValues(ValueNode val) {
		if (val instanceof Bra_ket) {
			if ( ! ((Bra_ket)val).bra == bra) Calculator.warn("WARNING: tried to set values to a Bra_ket with mismatched bool bra. ");
			
			for (int i = 0; i < ((Bra_ket) val).getValues().length; i++) {
				getValues()[i] = (((Bra_ket) val).getValues())[i];
			}
		}else {
			values = new ValueNode[] {val};
		}
		notCalculated();
	}
	
	@Override
	public boolean valuesSet() {
		return values.length > 0;
	}
	
	public ValueNode[] getValues() {
		return values;
	}
	
	public ValueNode getValue(int indx) {
		return getValues()[indx];
	}
	
	@Override
	public double getValue() {
		if (! isCalculated()) {
			
			if (Equation.printInProgress) System.out.println("calculating magnitude of " + toString());
			
			//if (values.length == 1) {
			if(true) {
				value = values[0].getValue();
			}else {
				double magnitude = 0;
				ValueNode outputNode = new ValueNode(0);
				Multiplication multi = new Multiplication();
				for (ValueNode v : getValues()) {
					magnitude += ((Two_subNode_node)multi).operation(v,v,outputNode).getValue();
				}
			
				value = Math.sqrt(magnitude);
			}
			calculated();
			
		}
		return value;
	}
	
	public String getValueDataStr() {
		String out = "";
		
		//add open "bracket" character
		out += bra ? "<" : "|";
		
		
		// add values
		if (values != null && values.length != 0) {
			for (int i = 0; i < values.length; i++) {
				out += values[i]; // add the value 
				out += i < values.length-1 ? "," : ""; // add a comma if this isn't the last value
						
			}
		}else { // if there aren't any values just add null
			out += "null";
		}
		
		
		//add close "bracket" character
		out += bra ? "|" : ">";
		
		return out;
	}
}
