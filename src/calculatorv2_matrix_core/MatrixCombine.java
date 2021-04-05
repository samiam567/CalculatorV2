package calculatorv2_matrix_core;

import calculatorv2_basic_operations.Addition;
import calculatorv2_core.AdvancedValueNode;
import calculatorv2_core.Equation;
import calculatorv2_core.Two_subNode_node;
import calculatorv2_core.ValueNode;

/**
 * {@summary combines two matrixes together}
 * @author apun1
 *
 */
public class MatrixCombine extends Two_subNode_node {
	
	private Addition addition = new Addition();
	
	
	public MatrixCombine() {
		orderOfOpsLevel = 0;
	}
	
	@Override
	protected double operation(double a, double b) {
		if (Equation.printInProgress) System.out.println(a + "<+>" + b);
		return a+b;
	}
	
	public String toString() {
		return "<+>";
	}
	
	
	
	// class-specific advanced operations
	
	@Override
	public ValueNode operation(ValueNode n1, ValueNode n2, ValueNode outputNode) {
		assert outputNode instanceof MatrixNode; // we can't combine into a non-matrix

		
		if ( (n1 instanceof AdvancedValueNode && ( (AdvancedValueNode) n1).needsSpecialOperationConditions) || (n2 instanceof AdvancedValueNode && ( (AdvancedValueNode) n2).needsSpecialOperationConditions) ) {
			//for convenience, convert all standalone values to kets
			if (! (n2 instanceof AdvancedValueNode)) n2 = new Ket(new ValueNode[] {n2}); 
			
			ValueNode[][] outputMatrix;
			
			if (Equation.printInProgress) System.out.println(n1.toString() + toString() + n2.toString());
			
			if (n1 instanceof MatrixNode && n2 instanceof Bra) {
				// matrix <+> Bra
				MatrixNode N1 = (MatrixNode) n1;
				Bra N2 = (Bra) n2;
				
				outputMatrix = new ValueNode[N1.getRows().length+1][N1.getColumns().length > N2.size() ? N1.getColumns().length : N2.size()];
				
				// fill in the rows of outputMatrix
				for (int row_indx = 0; row_indx < N1.getRows().length; row_indx++ ) {
					outputMatrix[row_indx] = N1.getRows()[row_indx].getValues();
				}
				
				outputMatrix[outputMatrix.length-1] = N2.getValues();
			}else if (n1 instanceof MatrixNode && n2 instanceof Ket) {
				// matrix <+> Ket
				MatrixNode N1 = (MatrixNode) n1;
				Ket N2 = (Ket) n2;
				
				outputMatrix = new ValueNode[N1.getRows().length > N2.size() ? N1.getRows().length : N2.size()][N1.getColumns().length+1];
				
				for (int row_indx = 0; row_indx < outputMatrix.length; row_indx++) {
					for (int col_indx = 0; col_indx < N1.getColumns().length; col_indx++) {
						outputMatrix[row_indx][col_indx] = col_indx < N1.getColumns().length ? N1.getRows()[row_indx].getValue(col_indx) : new ValueNode(0);
					}
					
					outputMatrix[row_indx][outputMatrix[0].length-1] = row_indx < N2.size() ? N2.getValue(row_indx) : new ValueNode(0);
				}
				
			}else if (false) {//n1 instanceof MatrixNode && n2 instanceof MatrixNode) {
				// matrix <+> matrix
			}else if (n1 instanceof Bra && n2 instanceof Bra) {
				// Bra <+> Bra
				
				outputMatrix = new ValueNode[][] {((Bra) n1).getValues(),((Bra) n2).getValues()};
				
			}else if (n1 instanceof Ket && n2 instanceof Ket) {
				// Ket <+> Ket
				
				Ket N1 = (Ket) n1;
				Ket N2 = (Ket) n2; 
				
				outputMatrix = new ValueNode[N1.size() > N2.size() ? N1.size() : N2.size()][2];
				
				for (int i = 0; i < outputMatrix.length; i++ ) {
					outputMatrix[i] = new ValueNode[] {i < N1.size() ? N1.getValue(i) : new ValueNode(0),i < N2.size() ? N2.getValue(i) : new ValueNode(0)};
				}
				
				
			}else if (n1 instanceof Bra && n2 instanceof Ket) {
				Bra N1 = (Bra) n1;
				Ket N2 = (Ket) n2;
				ValueNode[] outputBra = new ValueNode[N1.size()+1];
				
				for (int i = 0; i < N1.size(); i++) {
					outputBra[i] = N1.getValue(i);
				}
				
				if (N2.size() > 1) {
					outputBra[N1.size()] = N2;
				}else {
					outputBra[N1.size()] = N2.getValue(0);
				}
				
				if (! (outputNode instanceof Bra) ) outputNode = new Bra(); 
				
				((Bra) outputNode).setValues(outputBra);
				
				return outputNode;
				
			} else {
				Equation.warn("class " + getClass() + " has no implementation for AdvancedValueNodes of class " + n1.getClass() + " and " + n2.getClass());

				if (outputNode instanceof MatrixNode) {
					((MatrixNode) outputNode).setMatrix(new ValueNode[][] {{n1}, {n2}});
				}else {
					if (! (outputNode instanceof Matrixable) ) outputNode = new Bra();
					
					((Matrixable) outputNode).setValues(new ValueNode[] {n1,n2});
				}
				
				return outputNode;
			}
			
			if (! (outputNode instanceof MatrixNode)) outputNode = new MatrixNode();
			((MatrixNode) outputNode).setMatrix(outputMatrix);
			
			
			
			
		}else { //they are just normal values
		
			if (outputNode instanceof MatrixNode) {
				((MatrixNode) outputNode).setMatrix(new ValueNode[][] {{n1}, {n2}});
			}else {
				if (! (outputNode instanceof Matrixable) ) outputNode = new Bra();
				
				((Matrixable) outputNode).setValues(new ValueNode[] {n1,n2});
			}
		}
			
		return outputNode;
	}
		
		

}
