import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class PartOneTestCases
{
    public static final double DELTA = 0.00001;

    @Test
    public void testCircleImplSpecifics()
            throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
                "getCenter", "getRadius");

        final List<Class> expectedMethodReturns = Arrays.asList(
                Point.class, double.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
                new Class[0], new Class[0]);

        verifyImplSpecifics(Circle.class, expectedMethodNames,
                expectedMethodReturns, expectedMethodParameters);
    }

    @Test
    public void testRectangleImplSpecifics()
            throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
                "getTopLeft", "getBottomRight");

        final List<Class> expectedMethodReturns = Arrays.asList(
                Point.class, Point.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
                new Class[0], new Class[0]);

        verifyImplSpecifics(Rectangle.class, expectedMethodNames,
                expectedMethodReturns, expectedMethodParameters);
    }

    @Test
    public void testPolygonImplSpecifics()
            throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
                "getPoints");

        final List<Class> expectedMethodReturns = Arrays.asList(
                List.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
                new Class[][] {new Class[0]});

        verifyImplSpecifics(Polygon.class, expectedMethodNames,
                expectedMethodReturns, expectedMethodParameters);
    }

    @Test
    public void testUtilImplSpecifics()
            throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
                "perimeter", "perimeter", "perimeter");

        final List<Class> expectedMethodReturns = Arrays.asList(
                double.class, double.class, double.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
                new Class[] {Circle.class},
                new Class[] {Polygon.class},
                new Class[] {Rectangle.class});

        verifyImplSpecifics(Util.class, expectedMethodNames,
                expectedMethodReturns, expectedMethodParameters);
    }
    // Testing
    @Test
    public void testGetCenter() {
        Circle circle = new Circle(new Point(1.0, 2.0), 3.0);
        assertEquals(1.0, circle.getCenter().getX(), DELTA);
        assertEquals(2.0, circle.getCenter().getY(), DELTA);
    }

    @Test
    public void testGetRadius() {
        Circle circle = new Circle(new Point(1.0, 2.0), 3.0);
        assertEquals(3.0, circle.getRadius(), DELTA);
    }

    @Test
    public void testGetTop() {
        Rectangle rectangle = new Rectangle(new Point(0.0, 10.0), new Point(10.0, 0.0));
        assertEquals(0.0, rectangle.getTopLeft().getX(), DELTA);
        assertEquals(10.0, rectangle.getTopLeft().getY(), DELTA);
    }

    @Test
    public void testGetBottom() {
        Rectangle rectangle = new Rectangle(new Point(0.0, 10.0), new Point(10.0, 0.0));
        assertEquals(10.0, rectangle.getBottomRight().getX(), DELTA);
        assertEquals(0.0, rectangle.getBottomRight().getY(), DELTA);
    }

    @Test
    public void testGetPoints() {
        List<Point>points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(3,0));
        points.add(new Point(0,4));
        Polygon polygon = new Polygon(points);

        assertEquals(0, polygon.getPoints().get(0).getX(), DELTA);
        assertEquals(0, polygon.getPoints().get(0).getY(), DELTA);
        assertEquals(3, polygon.getPoints().get(1).getX(), DELTA);
        assertEquals(0, polygon.getPoints().get(1).getY(), DELTA);
        assertEquals(0, polygon.getPoints().get(2).getX(), DELTA);
        assertEquals(4, polygon.getPoints().get(2).getY(), DELTA);
    }

    @Test
    public void testPerimPoly() {
        List<Point>points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(3,0));
        points.add(new Point(0,4));
        double d = Util.perimeter(new Polygon(points));
        assertEquals(12.0, d, DELTA);
    }

    @Test
    public void testWhichIsBigger() {
        Circle circle = new Circle(new Point(1.0, 1.0), 2.0);
        Rectangle rectangle = new Rectangle(new Point(-1.0, 2.0), new Point(1.0, -1.6));
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(3, 1));
        points.add(new Point(1, 4));
        Polygon polygon = new Polygon(points);
        assertEquals(12.566370614359172, Bigger.whichIsBigger(circle, rectangle, polygon), DELTA);
    }

    private static void verifyImplSpecifics(
        final Class<?> clazz,
        final List<String> expectedMethodNames,
        final List<Class> expectedMethodReturns,
        final List<Class[]> expectedMethodParameters)
        throws NoSuchMethodException
    {
        assertEquals("Unexpected number of public fields",
            0, clazz.getFields().length);

        final List<Method> publicMethods = Arrays.stream(
            clazz.getDeclaredMethods())
                .filter(m -> Modifier.isPublic(m.getModifiers()))
                .collect(Collectors.toList());

        assertEquals("Unexpected number of public methods",
            expectedMethodNames.size(), publicMethods.size());

        assertTrue("Invalid test configuration",
            expectedMethodNames.size() == expectedMethodReturns.size());
        assertTrue("Invalid test configuration",
            expectedMethodNames.size() == expectedMethodParameters.size());

        for (int i = 0; i < expectedMethodNames.size(); i++)
        {
            Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
                expectedMethodParameters.get(i));
            assertEquals(expectedMethodReturns.get(i), method.getReturnType());
        }
    }
}
