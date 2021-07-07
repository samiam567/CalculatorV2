package calculatorv2_visualization;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;


public abstract class Grapher {
	

	public static ArrayList<Graph> graphs = new ArrayList<Graph>();

	public static JFrame frame = new JFrame();
	private static GrapherCanvas canvas = new GrapherCanvas();
	
	private static class GrapherCanvas extends Canvas {
		private static final long serialVersionUID = 1679647209129546743L;
		
		public GrapherCanvas() {
			setBackground(Color.BLACK);
			addGraph(new Axis());
		}
		public void paint(Graphics page) {
			for (Graph current_graph : graphs) {
				current_graph.paint(page);
			}
		}
	}

	
	public static void clearGraphs() {
		graphs.clear();
		addGraph(new Axis());
	}
	public static void updateGraph() {
		frame.dispose();
		frame = new JFrame();
		frame.setVisible(true);
		frame.setSize(Settings.width,Settings.height);
		frame.getContentPane().add(canvas);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public static void openGraph() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setSize(Settings.width,Settings.height);
		frame.getContentPane().add(canvas);
	}
	
	public static void addGraph(Graph newGraph) {
		graphs.add(newGraph);
	}
	

}
