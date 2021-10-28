class Bigger {

    public static double whichIsBigger(Circle circle, Rectangle rectangle, Polygon polygon) {
        double big = circle.perimeter();
        if (big < rectangle.perimeter()) {
            big = rectangle.perimeter();
        }
        if (big < polygon.perimeter()) {
            big = polygon.perimeter();
        }
        return big;
    }
}

