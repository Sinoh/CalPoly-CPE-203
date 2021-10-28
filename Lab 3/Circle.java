import java.awt.*;

public class Circle implements Shape{

    private Point center;
    private double radius;
    private Color color;

    public Circle(double radius, Point center, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double radius) {this.radius = radius; }

    public Point getCenter() {
        return this.center;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getArea(){
        return Math.PI * this.radius * this.radius;
    }

    public double getPerimeter() {

        return 2 * Math.PI * this.radius;
    }

    public void translate(Point point) {

        this.center = point;
    }
}
