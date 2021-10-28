import java.awt.*;

public class ConvexPolygon implements Shape{

    private Point[] points;
    private Color color;

    public ConvexPolygon(Point[] points, Color color) {
        this.points = points;
        this.color = color;
    }

    public Point getVertex(int index) {
        return this.points[index];
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getArea(){
        Double[] sides = new Double[this.points.length];
        Point one = this.points[0];
        double S = Math.sqrt(Math.pow((one.x - this.points[this.points.length-1].x), 2) + Math.pow((one.y - this.points[this.points.length-1].y),2));
        for (int n = 1; n < this.points.length; n++ ) {
            Point two = this.points[n];
            double side = Math.sqrt(Math.pow((two.x - one.x), 2) + Math.pow((two.y - one.y),2));
            sides[n-1] = side;
            S += side;
            one = two;

        }
        S = S / 2;
        Double num = S - sides[0];

        for (int n = 0; n < sides.length-1; n++ ) {
            num = num * (S - sides[n]);
        }

        return Math.sqrt(num);
    }

    public double getPerimeter() {

        Double[] sides = new Double[this.points.length];
        Point one = this.points[0];
        for (int n = 1; n < this.points.length; n++ ) {
            Point two = this.points[n];
            double side = Math.sqrt(Math.pow((two.x - one.x), 2) + Math.pow((two.y - one.y),2));
            sides[n-1] = side;
            one = two;
        }
        sides[this.points.length-1] = Math.sqrt(Math.pow((one.x - this.points[0].x), 2) + Math.pow((one.y - this.points[0].y),2));
        Double sum = 0.0;
        for (int n = 0; n < sides.length; n++ ) {
            sum += sides[n];
        }

        return sum;
    }

    public void translate(Point point) {
        int x = point.x - this.points[0].x;
        int y = point.y - this.points[0].y;
        for (int n = 0; n < this.points.length; n++ ) {
            this.points[n].x += x;
            this.points[n].y += y;

        }

    }
}
