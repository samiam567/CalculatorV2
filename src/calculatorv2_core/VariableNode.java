package calculatorv2_core;



/**
 * {@summary a value node can be a number or a variable in an equation}
 * @author apun1
 *
 */
public class VariableNode extends ValueNode {
	private String name = "number";

	protected boolean unsetVal = false;
	
	public VariableNode(Equation eq, String name) {
		super('k');
		unsetVal = true;
		this.name = name;
		orderOfOpsLevel = eq.operations.size();
		
	}
	
	public VariableNode(Equation eq,String name, int parenthesisLevel) {
		super('k');
		unsetVal = true;
		this.name = name;
		setParenthesisLevel(parenthesisLevel);
		orderOfOpsLevel = eq.operations.size();
	}
	
	public VariableNode(Equation eq,double v) {
		super('k');
		setValue(v);
		orderOfOpsLevel = eq.operations.size();
	}
	
	public VariableNode(Equation eq,ValueNode v, int parenthesisLevel) {
		super('k');
		setValueData(v);
		setParenthesisLevel(parenthesisLevel);
		orderOfOpsLevel = eq.operations.size();
	}
	
	public VariableNode(Equation eq,double v, int parenthesisLevel) {
		super('k');
		setValue(v);
		setParenthesisLevel(parenthesisLevel);
		orderOfOpsLevel = eq.operations.size();
	}

	
	@Override 
	public double getValue() {
		if (! isCalculated()) {
			if (unsetVal) { //make sure this node's value was set
				Calculator.warn("Variable-node " + name + " never had its value set");
			}
			calculated();
		}
		return value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * {@summary sets unsetVal. This method should ONLY be used in SPECIFIC circumstances where using other methods is not an option}
	 * @param unsetVal
	 * @param key
	 */
	public void setUnsetVal(boolean unsetVal, char key) {
		assert key == 'k'; //makes sure you know what you're doing when you use this method
		this.unsetVal = unsetVal;
	}
	
	@Override
	public long getLevel() {
		return Long.MAX_VALUE;
	}
	
	public boolean unsetVal() {
		return unsetVal;
	}
	
	public void setValue(double value) {
		if (getParent() != null) getParent().notCalculated();
		this.value = value;
		calculated();
		unsetVal = false;
	}
	
	@Override
	public String toString() {
		if (name.equals("number")) {
			return "" + value;
		}else {
			return " " + name + ":" + value + " ";
		}
	}
}
