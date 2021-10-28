import java.lang.Math;

public class Point {
	private final double x;
	private final double y;

	public Point(double x, double y) {

		this.x = x;
		this.y = y;
	}
	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getRadius() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}

	public double getAngle() {
		return Math.toRadians(Math.atan(this.y/this.x));
	}

	public Point rotate90() {
		double x = this.y * -1;
		double y = this.x;
		return new Point(x, y);

	}

}




