package calculatorv2_core;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class UserCalculatorInputFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8091811344185541027L;

	public UserCalculatorInputFrame() {
		setVisible(false);
		setSize(300,10);
		setTitle("Calculator Parser/Solver - Programmed by Alec Pannunzio");

	}
	

	


	public String showInputDialog( Object string, String string2, int i,Icon icon, Object[] object2, String eqSuggestion) {
			setVisible(true);
			String input = (String) JOptionPane.showInputDialog(this,string,string2,i,icon,object2,eqSuggestion);
			setVisible(false);
			return input;
	}

	
	public void showMessageDialog(String message) {
		setVisible(true);
		JOptionPane.showMessageDialog(this,message);
		setVisible(false);
	}

	public void showMessageDialog(UserCalculatorInputFrame calculatorAnchor, String message) {
		setVisible(true);
		showMessageDialog(message);
		setVisible(false);
	}

	public void showMessageDialog(String string1, String string2, int i) {
		setVisible(true);
		JOptionPane.showMessageDialog(this,string1, string2,i);
		setVisible(false);
	}

	public String showInputDialog(String string) {
		setVisible(true);
		String input = (String) JOptionPane.showInputDialog(this,string);
		setVisible(false);
		return input;
	}
	
	
	@Override
	public void finalize() {
		Exception e = new Exception("Terminating program because frame was closed.");
		e.printStackTrace();
		System.exit(ERROR);
	}

}
