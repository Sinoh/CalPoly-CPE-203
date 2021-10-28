import java.util.List;

class Util {


    public static double perimeter(Rectangle rectangle) {
        Point x = rectangle.getTopLeft();
        Point y = rectangle.getBottomRight();

        double vertical = (x.getY() - y.getY()) * 2;
        double horizontal = (x.getX() - y.getX()) * 2;

        return vertical + horizontal;

    }

    private static double dist(Point x, Point y) {
        return Math.sqrt((x.getX()-y.getX())*(x.getX()-y.getX()) + (x.getY()-y.getY())*(x.getY()-y.getY()));
    }

    public static double perimeter(Polygon polygon) {
        double distance = 0;
        List<Point> points = polygon.getPoints();
        for(int i = 1; i < points.size(); i++)
            distance += dist(points.get(i), points.get(i-1));
        distance += dist(points.get(0), points.get(points.size()-1));
        return distance;
    }

}