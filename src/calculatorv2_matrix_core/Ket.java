package calculatorv2_matrix_core;

import calculatorv2_core.ValueNode;

public class Ket extends Bra_ket {

	public Ket() {
		super(false);
	}
	
	public Ket(int numVals) {
		super(false,numVals);
	}

	
	public Ket(ValueNode[] values) {
		super(values, false);
	}
}
