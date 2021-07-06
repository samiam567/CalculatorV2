package calculatorv2_visualization;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;


public class Grapher extends Canvas {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4670676449322662698L;

	public static ArrayList<Graph> graphs = new ArrayList<Graph>();
	static JFrame frame = new JFrame();
	
	public Grapher() {
		addGraph(new Axis());
		setBackground(Color.BLACK);
	}
	
	public void paint(Graphics page) {
		for (Graph current_graph : graphs) {
			current_graph.paint(page);
		}
	}
	
	public void updateGraph() {
		frame.dispose();
		frame = new JFrame();
		frame.setVisible(true);
		frame.setSize(Settings.width,Settings.height);
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public void openGraph() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setSize(Settings.width,Settings.height);
		frame.getContentPane().add(this);
	}
	
	public void addGraph(Graph newGraph) {
		graphs.add(newGraph);
	}
	

}
