package calculatorv2_scientific_operations;

import calculatorv2_core.AdvancedValueNode;
import calculatorv2_core.ValueNode;

public class ComplexValueNode extends AdvancedValueNode {
	private double realComponent;
	private double imaginaryComponent;

	
	public ComplexValueNode() {
		super('k');
		notCalculated();
	}
	
	public ComplexValueNode(double real, double imaginary) { 
		super(0,'k');
		setRealComponent(real);
		imaginaryComponent = imaginary;
	
	}
	
	public void congjugate() {
		imaginaryComponent = -imaginaryComponent;
	}
	
	public ComplexValueNode getConjugate() {
		return new ComplexValueNode(realComponent,-imaginaryComponent);
	}
	
	public double getRealComponent() {
		return realComponent;
	}
	
	public double getImaginaryComponent() {
		return imaginaryComponent;
	}
	
	public double getComplex() {
		return imaginaryComponent;
	}
	
	public void setValue(ValueNode v) {
		if (v instanceof ComplexValueNode) {
			setRealComponent(((ComplexValueNode) v ).getRealComponent());
			setImaginaryComponent( ((ComplexValueNode) v ).getImaginaryComponent());
		}else {
			setRealComponent(v.getValue());
			setImaginaryComponent(0);
		}
		
	
	}

	
	// returns the magnitude of this number
	@Override
	public double getValue() {
		if (! isCalculated()) {
			value = realComponent; 
		}
		
		return value;
	}
	
	
	public void setImaginaryComponent(double imaginary) {
		this.imaginaryComponent = imaginary;
	}

	public void setValues(double real, double imaginary) {
		setRealComponent(real);
		setImaginaryComponent(imaginary);
	}
	
	
	
	
	public void setRealComponent(double real) {
		realComponent = real;
	}

	@Override
	public String toString() {
		return "" + Math.round(realComponent*1000000000)/1000000000D + " + " + Math.round(imaginaryComponent*1000000000)/1000000000D + "i";
	}

	public double getReal() {
		return realComponent;
	}
	

}
