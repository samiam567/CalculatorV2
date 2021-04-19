package calculatorv2_core;

public interface Operation {
	public String getOperationKeyword();
	
	public void test();
	
	public EquationNode createNewInstanceOfOperation(Equation eq);
}


/*
	@Override
	public String getOperationKeyword() {
		return null;
	}
	
	@Override
	public void test() { 
		Equation.warn(getClass() + " is not tested and should not be used");
	}
	
	@Override
	public EquationNode createNewInstanceOfOperation(Equation eq) {
		Equation.error("createNewInstanceOfOperation MUST be overriden by every operation Offender: " + getClass());
		return null;
	}


*/