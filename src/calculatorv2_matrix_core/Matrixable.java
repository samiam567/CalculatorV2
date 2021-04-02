package calculatorv2_matrix_core;

import calculatorv2_core.ValueNode;

public interface Matrixable {
	public void setValues(ValueNode[] vals);

	public boolean valuesSet();
	
	public ValueNode[] getValues();
}
