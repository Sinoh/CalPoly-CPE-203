import java.awt.*;

public class Triangle implements Shape{

    private Point A;
    private Point B;
    private Point C;
    private Color color;

    public Triangle(Point first, Point second, Point third, Color color) {
        this.A = first;
        this.B = second;
        this.C = third;
        this.color = color;
    }

    public Point getVertexA() {
        return this.A;
    }

    public Point getVertexB() {
        return this.B;
    }

    public Point getVertexC() {
        return this.C;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getArea() {
        Double side1 = Math.sqrt(Math.pow((this.A.getX() - this.B.getX()), 2) + Math.pow((this.A.getY() - this.B.getY()), 2));
        Double side2 = Math.sqrt(Math.pow((this.B.getX() - this.C.getX()), 2) + Math.pow((this.B.getY() - this.C.getY()), 2));
        Double side3 = Math.sqrt(Math.pow((this.C.getX() - this.A.getX()), 2) + Math.pow((this.C.getY() - this.A.getY()), 2));

        Double S = 0.5 * (side1 + side2 + side3);
        return Math.sqrt(S * (S - side1) * (S - side2) * (S - side3));
    }

    public double getPerimeter() {

        Double side1 = Math.sqrt(Math.pow((this.A.getX() - this.B.getX()), 2) + Math.pow((this.A.getY() - this.B.getY()), 2));
        Double side2 = Math.sqrt(Math.pow((this.B.getX() - this.C.getX()), 2) + Math.pow((this.B.getY() - this.C.getY()), 2));
        Double side3 = Math.sqrt(Math.pow((this.C.getX() - this.A.getX()), 2) + Math.pow((this.C.getY() - this.A.getY()), 2));

        return side1 + side2 + side3;
    }

    public void translate(Point point) {
        int x = point.x - this.A.x;
        int y = point.y - this.A.y;
        this.A = point;
        this.B.x += x;
        this.B.y += y;
        this.C.x += x;
        this.C.y += y;


    }
}