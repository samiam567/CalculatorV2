package calculatorv2_matrix_core;

import calculatorv2_core.AdvancedValueNode;
import calculatorv2_core.Equation;
import calculatorv2_core.NodeWrapper;
import calculatorv2_core.ValueNode;

public class MatrixNode extends AdvancedValueNode implements Matrixable {
	
	//these two lists' values should be aliases
	private MatrixBra[] rows = new MatrixBra[0]; 
	private MatrixKet[] columns = new MatrixKet[0]; 

	
	
	private boolean matrixSet = false;
	
	public MatrixNode() {
		super('k');
		matrixSet = false;
	}
	
	public MatrixNode(int rowsNum, int colsNum) {
		super('k');
		rows = new MatrixBra[rowsNum];
		columns = new MatrixKet[colsNum];
		
		for (int row_indx = 0; row_indx < rowsNum; row_indx++) {
			rows[row_indx] = new MatrixBra(colsNum);
		}
		
		for (int col_indx = 0; col_indx < colsNum; col_indx++) {
			columns[col_indx] = new MatrixKet(rowsNum);
		}
	}
	
	public MatrixNode(ValueNode[] values) {
		super('k');
		setMatrixFromValues(values);
	}
	
	public void setElements(ValueNode[] values) {
		setMatrixFromValues(values);
		notCalculated();
	}
	
	@Override
	public String toString() {
		return getValueDataStr();
	}
	
	@Override
	public boolean valuesSet() {
		return matrixSet;
	}
	@Override
	public void setValues(ValueNode[] values) {
		setMatrixFromValues(values);
	}
	public void setMatrixFromValues(ValueNode[] values) {	
		matrixSet = true;
		MatrixCreate.combineIntoMatrix(values,this);
		calculated();
	}
	
	public Bra[] getRows() {
		if (! matrixSet) (new Exception("Matrix values not set yet")).printStackTrace();

		return rows;
	}
	
	public ValueNode[] getValues() {
		return getColumns();
	}
	
	public Ket[] getColumns() {
		if (! matrixSet) (new Exception("Matrix values not set yet")).printStackTrace();
		
		return columns;
	}
	
	
	
	/**
	 * {@summary the only way to set a MatrixNode's values is through this method in some way or another}
	 * {@code creates the rows and columns lists from the passed matrix of values}
	 * @param values
	 */
	public void setMatrix(ValueNode[][] values) {
		
		assert values[0].length == values[values.length-1].length; //try and ensure the input matrix is rectangular
		
		rows = new MatrixBra[values.length];
		
		columns = new MatrixKet[values[0].length];
		
		// populate rows
		NodeWrapper[] cRow = new NodeWrapper[values[0].length];
		for (int row_indx = 0; row_indx < values.length; row_indx++) {
			
			for (int col_indx = 0; col_indx < values[0].length; col_indx++) {
				cRow[col_indx] = new NodeWrapper(values[row_indx][col_indx]);
			}
			
			rows[row_indx] = new MatrixBra(cRow);
		}
		
		
		//populate columns with aliased NodeWrappers from rows
		NodeWrapper[] cCol = new NodeWrapper[values.length];
		for (int col_indx = 0; col_indx < values[0].length; col_indx++) {
			for (int row_indx = 0; row_indx < values.length; row_indx++) {
				cCol[row_indx] = (rows[row_indx]).getWrappers()[col_indx];
			}
			
			columns[col_indx] = new MatrixKet(cCol);
		}
		
		matrixSet = true;
		
		
	}
	
	@Override
	public double getValue() {
		//TODO rewrite to return determinant
		
		
		
		if (! isCalculated()) {
			Equation.warn("WARNING: MatrixNode has no implementation for getValue()");
			
			calculated();
		}
		
		return 0;
	}
	
	public String getValueDataStr() {
	
		String out = "";
		
		//add open "bracket" character
		out += "[";
		
		
		// add values
		if (rows != null && rows.length != 0) {
			for (int i = 0; i < rows.length; i++) {

				out += rows[i].getDataStr(); // add the value 
				
				out += i < rows.length-1 ? "," : ""; // add a comma if this isn't the last value
						
			}
		}else { // if there aren't any values just add null
			out += "null";
		}
		
		
		//add close "bracket" character
		out += "]";
		
		return out;
	}
	

	
	

}
