package calculatorv2_scientific_operations;

import calculatorv2_core.AdvancedValueNode;

public class ComplexValueNode extends AdvancedValueNode {
	private double imaginaryComponent;
	
	public ComplexValueNode() {
		super('k');
		notCalculated();
	}
	
	public ComplexValueNode(double real, double imaginary) { 
		super(real,'k');
		imaginaryComponent = imaginary;
	}
	
	public void congjugate() {
		imaginaryComponent = -imaginaryComponent;
	}
	
	public ComplexValueNode getConjugate() {
		return new ComplexValueNode(value,-imaginaryComponent);
	}
	
	public double getImaginaryComponent() {
		return imaginaryComponent;
	}
	
	public double getComplex() {
		return imaginaryComponent;
	}

	/*
	@Override
	public ComplexValueNode copy() {
		ComplexValueNode cV = new ComplexValueNode(value,imaginaryComponent);
		cV.setUnsetVal(unsetVal(),'k');
		cV.setName(getName());
		System.out.println(cV.unsetVal());
		return cV;
	}*/
	
	
	public void setImaginaryComponent(double imaginary) {
		this.imaginaryComponent = imaginary;
	}

	public void setValues(double real, double imaginary) {
		setValue(real);
		setImaginaryComponent(imaginary);
	}
	
	/*
	@Override
	public ComplexValueNode copy() {
		ComplexValueNode cV = new ComplexValueNode(value,imaginaryComponent);
		cV.setUnsetVal(unsetVal(),'k');
		cV.setName(getName());
		System.out.println(cV.unsetVal());
		return cV;
	}*/
	
	
	@Override
	public String toString() {
		return "" + value + " + " + imaginaryComponent + "i";
	}

	public double getReal() {
		return value;
	}
	

}
