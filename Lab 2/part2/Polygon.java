import java.util.List;

public class Polygon {

    private final List<Point> points;

    public Polygon(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        return this.points;
    }

    private static double dist(Point x, Point y) {
        return Math.sqrt((x.getX()-y.getX())*(x.getX()-y.getX()) + (x.getY()-y.getY())*(x.getY()-y.getY()));
    }

    public double perimeter() {
        double distance = 0;
        List<Point> points = this.points;
        for(int i = 1; i < points.size(); i++)
            distance += dist(points.get(i), points.get(i-1));
        distance += dist(points.get(0), points.get(points.size()-1));
        return distance;
    }
}