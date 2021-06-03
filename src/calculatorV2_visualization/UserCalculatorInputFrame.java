package calculatorV2_visualization;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;

public class UserCalculatorInputFrame extends JFrame implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8091811344185541027L;

	public UserCalculatorInputFrame(Equation eq) {
		setVisible(true);
		setSize(300,10);
		setTitle("Calculator Parser/Solver - Programmed by Alec Pannunzio");
		addKeyListener(this); // add this when we phase out JOptionPane inputs
		setup();
	}
	
	public void setup() {
		//Creating the Frame
        JFrame frame = new JFrame("Chat Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Open");
        JMenuItem m22 = new JMenuItem("Save as");
        m1.add(m11);
        m1.add(m22);

        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        JLabel label = new JLabel("Enter Text");
        JTextField tf = new JTextField(100); // accepts upto 10 characters
        
        JButton send = new JButton("Send");
        JButton reset = new JButton("Reset");
        panel.add(label); // Components Added using Flow Layout
        panel.add(tf);
        panel.add(send);
        panel.add(reset);

        // Text Area at the Center
        JTextArea ta = new JTextArea();

        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, ta);
        frame.setVisible(true);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("recieved keyevent");
		switch( e.getKeyCode() ) { 
        case KeyEvent.VK_UP:
            Calculator.userInputEqSuggestion = Calculator.lastEquations.pop();
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
		try {
			Thread.sleep(1000000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return (String) JOptionPane.showInputDialog(this,string);
		return "1+1";
	//	return (String) JOptionPane.showInputDialog(this,string,string2,i,icon,object2,eqSuggestion);
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
		try {
			Thread.sleep(1000000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return (String) JOptionPane.showInputDialog(this,string);
		return "1+1";
	}

}
