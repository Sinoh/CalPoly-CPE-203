import java.awt.*;

public class Rectangle implements Shape{

    private Point top;
    private Color color;
    private double width;
    private double height;

    public Rectangle(double width, double height, Point top, Color color) {
        this.top = top;
        this.color = color;
        this.width = width;
        this.height = height;
    }

    public double getWidth(){
        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;

    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;

    }
    public Point getTopLeft() {

        return this.top;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getArea() {
        return this.height * this.width;
    }

    public double getPerimeter() {
        return 2 * this.height + 2 * this.width;
    }

    public void translate(Point point) {
        this.top = point;
    }
}

