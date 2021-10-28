class Bigger {

    public static double whichIsBigger(Circle circle, Rectangle rectangle, Polygon polygon) {
        double big = Util.perimeter(circle);
        if (big < Util.perimeter(rectangle)) {
            big = Util.perimeter(rectangle);
        }
        if (big < Util.perimeter(polygon)) {
            big = Util.perimeter(polygon);
        }
        return big;
    }
}

