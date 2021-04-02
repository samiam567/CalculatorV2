package calculatorv2_scientific_operations;

import calculatorv2_core.AdvancedValueNode;
import calculatorv2_core.Equation;

public class Comparation extends AdvancedValueNode {
	public enum ComparationValues {greater, less, equal, True, False, unassigned};
	
	ComparationValues compareValue = ComparationValues.unassigned;
	
	public Comparation() {
		super(0,'k');
		needsSpecialOperationConditions = false;
	}
	
	public Comparation(ComparationValues comp) {
		super(0,'k');
		compareValue = comp;
		needsSpecialOperationConditions = false;
	}
	
	public void setValue(ComparationValues newValue) {
		compareValue = newValue;
		notCalculated();
	}
	
	@Override
	public void setValue(double newVal) {
		super.setValue(newVal);
		
		
	
	}
	
	public double getValue() {
		if (! isCalculated()) {
			switch(compareValue) {
				case greater:
				case True:
					value = 1;
					break;
				case less:
					value = -1;
					break;
				case False:
				case equal:
					value = 0;
					break;
				default:
					Equation.warn("Comparation compareValue was never set");
					value = 0;
					break;			
			};
		}
		
		return value;
		
	}
	
	public String toString() {
		switch(compareValue) {
			case greater:
				return "greater";
			case True:
				return "true";
			case less:
				return "less";
			case False:
				return "false";
			case equal:
				return "equal";
			default:
				return "unassigned";	
		}
	}

	
	
}
