package calculatorv2_visualization;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class Graph {
	public static int colorCounter = -1;
	public static Color[] colors = {Color.blue,Color.CYAN,Color.ORANGE,Color.GREEN,Color.MAGENTA,Color.ORANGE,Color.PINK,Color.RED,Color.YELLOW}; 
	
	
	
	
	public String name = "unNamed";
	public ArrayList<GraphPoint> points = new ArrayList<GraphPoint>();
	protected Color color;
	
	public Graph() {
		
		if (colorCounter >= colors.length-1) {
			colorCounter = 0;
		}else {
			colorCounter++;
		}
		
		color = colors[colorCounter];
		
		
	}

	public void paint(Graphics page) {
		page.setColor(color);
		GraphPoint current_point;
		GraphPoint next_point;
		for (int i = 0 ; i <  points.size()-1 ; i++) {		
			current_point = points.get(i);	
			next_point = points.get(i+1);
		
			page.drawLine((int) Math.round(Settings.width/2+current_point.getCoordinates()[0]), (int) Math.round(Settings.height/2-current_point.getCoordinates()[1]), (int) Math.round(Settings.width/2+next_point.getCoordinates()[0]), (int) Math.round(Settings.height/2-next_point.getCoordinates()[1]));
		}
	}
	
	public void addPoint(GraphPoint newPoint) {
		points.add(newPoint);
	}
}
