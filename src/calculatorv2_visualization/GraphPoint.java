package calculatorv2_visualization;

public class GraphPoint {
	private double[] coordinates;
	
	public GraphPoint(double[] coordinates) {
		this.coordinates = coordinates;
	}
	
	public GraphPoint(double x, double y) {
		coordinates = new double[] {x,y};
	}
	
	public double[] getCoordinates() {
		return coordinates;
	}
}