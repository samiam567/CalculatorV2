package calculatorv2_core;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class UserCalculatorInputFrame extends JFrame implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8091811344185541027L;
	
	
	
	
	public UserCalculatorInputFrame(Equation eq) {
		setVisible(true);
		setSize(300,10);
		setTitle("Calculator Parser/Solver - Programmed by Alec Pannunzio");
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch( e.getKeyCode() ) { 
        case KeyEvent.VK_UP:
            // handle up 
            break;
        case KeyEvent.VK_DOWN:
            // handle down 
            break;
        case KeyEvent.VK_LEFT:
            // handle left
            break;
        case KeyEvent.VK_RIGHT :
            // handle right
            break;
     }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public String showInputDialog( Object string, String string2, int i,
			Icon icon, Object[] object2, String eqSuggestion) {
	
		return (String) JOptionPane.showInputDialog(this,string,string2,i,icon,object2,eqSuggestion);
	}
	
	public void showMessageDialog(String message) {
		JOptionPane.showMessageDialog(this,message);
	}

	public void showMessageDialog(UserCalculatorInputFrame calculatorAnchor, String message) {
		showMessageDialog(message);
	}

	public void showMessageDialog(String string1, String string2, int i) {
		JOptionPane.showMessageDialog(this,string1, string2,i);
		
	}

	public String showInputDialog(String string) {
		return (String) JOptionPane.showInputDialog(this,string);
	}

}
