package calculatorv2_basic_operations;

import calculatorv2_core.AdvancedValueNode;
import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;
import calculatorv2_core.EquationNode;
import calculatorv2_core.Two_subNode_node;
import calculatorv2_core.ValueNode;
import calculatorv2_matrix_core.Bra;
import calculatorv2_matrix_core.Ket;
import calculatorv2_matrix_core.MatrixNode;
import calculatorv2_scientific_operations.ComplexValueNode;

public class Multiplication extends Two_subNode_node {
	
	public Multiplication() {
		orderOfOpsLevel = 2;
	}
	
	protected double operation(double a, double b) {
		if (Equation.printInProgress) System.out.println(a + "*" + b);
		return a*b;
	}
	
	public String toString() {
		return "*";
	}
	
	@Override
	public ValueNode operation(ValueNode n1, ValueNode n2, ValueNode outputNode) {
		if (outputNode == null) outputNode = new ValueNode(0);
		if ( (n1 instanceof AdvancedValueNode && ( (AdvancedValueNode) n1).needsSpecialOperationConditions) || (n2 instanceof AdvancedValueNode && ( (AdvancedValueNode) n2).needsSpecialOperationConditions) ) {
			if (Equation.printInProgress) System.out.println(n1.getDataStr() + toString() + n2.getDataStr());
			
			
			if (false) {//n1 instanceof MatrixNode && n2 instanceof MatrixNode) {
				// both matrices
			}else if (n1 instanceof MatrixNode) {
				
				// only n1 matrixNode
				MatrixNode N1 = (MatrixNode) n1;
				
				
				if (! (outputNode instanceof MatrixNode)) outputNode = new MatrixNode(N1.getRows().length, N1.getColumns().length);
				

				for (int i = 0; i < N1.getRows().length; i++) {
					((MatrixNode) outputNode).getRows()[i].setValues(((Bra) operation(N1.getRows()[i],n2,((MatrixNode) outputNode).getRows()[i])));
				}
				
			}else if (n2 instanceof MatrixNode) {
				// only n2 matrixNode
				
				MatrixNode N2 = (MatrixNode) n2;
				
				
				if (! (outputNode instanceof MatrixNode)) outputNode = new MatrixNode(N2.getRows().length, N2.getColumns().length);
				

				for (int i = 0; i < N2.getRows().length; i++) {
					((MatrixNode) outputNode).getRows()[i] = ((Bra) operation(N2.getRows()[i],n1,((MatrixNode) outputNode).getRows()[i]));
				}
				
				
			}else if (n1 instanceof Bra && n2 instanceof Ket) {
				// inner product
				Bra N1 = (Bra) n1;
				Ket N2 = (Ket) n2;
				
				int length = N1.size() > N2.size() ? N2.size() : N1.size();
				
				double innerProduct = 0;
				for (int i = 0; i < length; i++) {
					innerProduct += operation(N1.getValue(i),N2.getValue(i),null).getValue();
				}
			
				outputNode = new ValueNode(innerProduct);
			}else if (false) {// (n1 instanceof Ket && n2 instanceof Bra) {
				// outer product
				
			}else if (n1 instanceof Bra && ! (n2 instanceof Bra) ) {
				Bra N1 = (Bra) n1;
				if (! (outputNode instanceof Bra) ) outputNode = new Bra( N1.size() );
				//Bra * number
				for (int i = 0; i < N1.size(); i++) {
					((Bra)outputNode).setValue(i,operation(N1.getValue(i),n2,((Bra)outputNode).getValue(i)));
				}
				
			}else if (n1 instanceof Ket && ! (n2 instanceof Ket)) {
				Ket N1 = (Ket) n1;
				if (! (outputNode instanceof Ket) ) outputNode = new Ket( N1.size() );
				//Ket * number
				for (int i = 0; i < N1.size(); i++) {
					((Ket)outputNode).setValue(i,operation(N1.getValue(i),n2,((Ket)outputNode).getValue(i)));
				}
			}else if (n2 instanceof Bra && ! (n1 instanceof Bra) ) {
				Bra N2 = (Bra) n2;
				if (! (outputNode instanceof Bra) ) outputNode = new Bra( N2.size() );
				//Bra * number
				for (int i = 0; i < N2.size(); i++) {
					((Bra)outputNode).setValue(i,operation(N2.getValue(i),n1,((Bra)outputNode).getValue(i)));
				}
				
			}else if (n2 instanceof Ket && ! (n1 instanceof Ket)) {
				Ket N2 = (Ket) n2;
				if (! (outputNode instanceof Ket) ) outputNode = new Ket( N2.size() );
				//Ket * number
				for (int i = 0; i < N2.size(); i++) {
					((Ket)outputNode).setValue(i,operation(N2.getValue(i),n1,((Ket)outputNode).getValue(i)));
				}
				
			}else if (n1 instanceof ComplexValueNode || n2 instanceof ComplexValueNode) {
				// (a + bi) * (c + di)
				double a,b,c,d;
				
				if (n1 instanceof ComplexValueNode) {
					ComplexValueNode N1 = (ComplexValueNode) n1;
					a = N1.getReal();
					b = N1.getComplex();
				}else {
					a = n1.getValue();
					b = 0;
				}
				
				if (n2 instanceof ComplexValueNode) {
					ComplexValueNode N2 = (ComplexValueNode) n2;
					c = N2.getReal();
					d = N2.getComplex();
				}else {
					c = n2.getValue();
					d = 0;
				}
				
				if (! (outputNode instanceof ComplexValueNode)) outputNode = new ComplexValueNode();
				
				((ComplexValueNode) outputNode).setValues( (a*c - b*d), (a*d + b*c));
				
			}
			
				
			/*else if (n1 instanceof ComplexValueNode && n2 instanceof ComplexValueNode) { 
				// both complex numbers
				if (! (outputNode instanceof ComplexValueNode) ) outputNode = new ComplexValueNode();
				((ComplexValueNode) outputNode).setValues(((ComplexValueNode) n1).getReal()*((ComplexValueNode) n2).getReal()-((ComplexValueNode) n1).getComplex()*((ComplexValueNode) n2).getComplex(), ((ComplexValueNode) n1).getReal()*((ComplexValueNode) n2).getComplex()+((ComplexValueNode) n1).getComplex()*((ComplexValueNode) n2).getReal());
			}else if (n1 instanceof ComplexValueNode) { 
				// only n1 Complex Number
				if (! (outputNode instanceof ComplexValueNode) ) outputNode = new ComplexValueNode();
				((ComplexValueNode) outputNode).setValues(((ComplexValueNode) n1).getReal()*(n2).getValue(), ((ComplexValueNode) n1).getComplex()*(n2).getValue());
			}else if (n2 instanceof ComplexValueNode) { 
				// only n2 complex number
				if (! (outputNode instanceof ComplexValueNode) ) outputNode = new ComplexValueNode();
				((ComplexValueNode) outputNode).setValues(((ComplexValueNode) n2).getReal()*(n1).getValue(), ((ComplexValueNode) n2).getComplex()*(n1).getValue());
			}*/
			
			else {
				Calculator.warn("class " + getClass() + " has no implementation for AdvancedValueNodes of class " + n1.getClass() + " and " + n2.getClass());
				outputNode.setValue(operation(n1.getValue(),n2.getValue()));
			}
			
		
		}else { //they are just normal values
			outputNode.setValue(operation(n1.getValue(),n2.getValue()));
		}
		
		return outputNode;
	}
	
	public String getOperationKeyword() {
		return "*";
	}
	
	public void test() { 
		Calculator.warn(getClass() + " is not tested and should not be used");
	}
	
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		return new Multiplication();
	}
	
}
