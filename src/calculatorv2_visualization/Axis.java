package calculatorv2_visualization;

import java.awt.Color;
import java.awt.Graphics;

public class Axis extends Graph {
	public void paint(Graphics page) {
		page.setColor(Color.WHITE);
		page.drawLine(Settings.width/2, 0, Settings.width/2, Settings.height);
		page.drawLine(0, Settings.height/2, Settings.width, Settings.height/2);
	}
}
