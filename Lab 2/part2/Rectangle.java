public class Rectangle {

    private final Point top;
    private final Point bottom;

    public Rectangle(Point top, Point bottom) {
        this.top = new Point(top.getX(), top.getY());
        this.bottom = new Point(bottom.getX(), bottom.getY());
    }

    public Point getTopLeft() {
        return this.top;
    }

    public Point getBottomRight() {
        return this.bottom;
    }

    public double perimeter() {
        Point x = this.top;
        Point y = this.bottom;

        double vertical = (x.getY() - y.getY()) * 2;
        double horizontal = (x.getX() - y.getX()) * 2;

        return vertical + horizontal;
    }

}
