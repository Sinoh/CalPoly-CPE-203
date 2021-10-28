import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import java.awt.Color;
import java.awt.Point;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.w3c.dom.css.Rect;

public class TestCases
{
    public static final double DELTA = 0.00001;

    /* some sample tests but you must write more! see lab write up */

    @Test
    public void testCircleGetArea()
    {
        Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);

        assertEquals(101.2839543, c.getArea(), DELTA);
    }

    @Test
    public void testCircleGetPerimeter()
    {
        Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);

        assertEquals(35.6759261, c.getPerimeter(), DELTA);
    }

    @Test
    public void testCircleGetRadius()
    {
        Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);

        assertEquals(5.678, c.getRadius(), DELTA);
    }

    @Test
    public void testCircleSetRadius()
    {
        Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
        c.setRadius(6.0);

        assertEquals(6, c.getRadius(), DELTA);
    }

    @Test
    public void testCircleTranslate()
    {
        Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
        c.translate(new Point(0,0));

        assertEquals(0, c.getCenter().getX(), DELTA);
        assertEquals(0, c.getCenter().getY(), DELTA);
    }

    @Test
    public void testCircleGetCenter()
    {
        Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);

        assertEquals(2, c.getCenter().getX(), DELTA);
        assertEquals(3, c.getCenter().getY(), DELTA);
    }

    @Test
    public void testRectangleGetWidth()
    {
        Rectangle r = new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK);
        assertEquals(1.234, r.getWidth(), DELTA);
    }

    @Test
    public void testRectangleSetWidth()
    {
        Rectangle r = new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK);
        r.setWidth(2.0);
        assertEquals(2.0, r.getWidth(), DELTA);
    }

    @Test
    public void testRectangleGetHeight()
    {
        Rectangle r = new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK);
        assertEquals(5.678, r.getHeight(), DELTA);
    }

    @Test
    public void testRectangleSetHeigth()
    {
        Rectangle r = new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK);
        r.setHeight(2.0);
        assertEquals(2.0, r.getHeight(), DELTA);
    }

    @Test
    public void testRectangleGetTopLeft()
    {
        Rectangle r = new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK);
        assertEquals(2, r.getTopLeft().getX(), DELTA);
        assertEquals(3, r.getTopLeft().getY(), DELTA);
    }

    @Test
    public void testRectangleGetArea()
    {
        Rectangle r = new Rectangle(2.0, 5.0, new Point(2, 3), Color.BLACK);
        assertEquals(10.0, r.getArea(), DELTA);
    }

    @Test
    public void testRectangleGetPerimeter()
    {
        Rectangle r = new Rectangle(2.0, 5.0, new Point(2, 3), Color.BLACK);
        assertEquals(14.0, r.getPerimeter(), DELTA);
    }

    @Test
    public void testRectangleTranslate()
    {
        Rectangle r = new Rectangle(2.0, 5.0, new Point(2, 3), Color.BLACK);
        r.translate(new Point(0,0));

        assertEquals(0, r.getTopLeft().getX(), DELTA);
        assertEquals(0, r.getTopLeft().getY(), DELTA);
    }


    @Test
    public void testTriangleGetPerimeter()
    {
        Triangle t = new Triangle(new Point(0,0), new Point(0,4), new Point(3, 0),
                Color.BLACK);

        assertEquals(12.0, t.getPerimeter(), DELTA);

    }


    @Test
    public void testTriangleTranslate()
    {
        Triangle t = new Triangle(new Point(0,0), new Point(0,4), new Point(3, 0),
                Color.BLACK);
        t.translate(new Point(1,1));
        assertEquals(1, t.getVertexA().getX(), DELTA);
        assertEquals(1, t.getVertexA().getY(), DELTA);
        assertEquals(1, t.getVertexB().getX(), DELTA);
        assertEquals(5, t.getVertexB().getY(), DELTA);
        assertEquals(4, t.getVertexC().getX(), DELTA);
        assertEquals(1, t.getVertexC().getY(), DELTA);
    }

    @Test
    public void testPolygonGetVertex()
    {
        Point[] points = new Point[5];
        points[0] = new Point(0,0);
        points[1] = new Point(1,0);
        points[2] = new Point(1,1);
        points[3] = new Point(0,1);
        ConvexPolygon p = new ConvexPolygon(points, Color.BLACK);

        assertEquals(0, p.getVertex(0).getX(), DELTA);
        assertEquals(0, p.getVertex(0).getY(), DELTA);

    }

    @Test
    public void testPolygonGetArea()
    {
        Point[] points = new Point[4];
        points[0] = new Point(0,0);
        points[1] = new Point(3,0);
        points[2] = new Point(3,3);
        points[3] = new Point(0,3);
        ConvexPolygon p = new ConvexPolygon(points, Color.BLACK);

        assertEquals(9.0, p.getArea(), DELTA);


    }

    @Test
    public void testPolygonGetPerimeter()
    {
        Point[] points = new Point[4];
        points[0] = new Point(0,0);
        points[1] = new Point(1,0);
        points[2] = new Point(1,1);
        points[3] = new Point(0,1);
        ConvexPolygon p = new ConvexPolygon(points, Color.BLACK);

        assertEquals(4.0, p.getPerimeter(), DELTA);


    }

    @Test
    public void testWorkSpaceAreaOfAllShapes()
    {
        WorkSpace ws = new WorkSpace();

        ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
        ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
        ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0), 
                      Color.BLACK));

        assertEquals(114.2906063, ws.getAreaOfAllShapes(), DELTA);
    }

    @Test
    public void testWorkSpaceGetCircles()
    {
        WorkSpace ws = new WorkSpace();
        List<Circle> expected = new LinkedList<>();

        // Have to make sure the same objects go into the WorkSpace as
        // into the expected List since we haven't overriden equals in Circle.
        Circle c1 = new Circle(5.678, new Point(2, 3), Color.BLACK);
        Circle c2 = new Circle(1.11, new Point(-5, -3), Color.RED);

        ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
        ws.add(c1);
        ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
                      Color.BLACK));
        ws.add(c2);

        expected.add(c1);
        expected.add(c2);

        // Doesn't matter if the "type" of lists are different (e.g Linked vs
        // Array).  List equals only looks at the objects in the List.
        assertEquals(expected, ws.getCircles());
    }

    /* HINT - comment out implementation tests for the classes that you have not 
     * yet implemented */
    @Test
    public void testCircleImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getColor", "setColor", "getArea", "getPerimeter", "translate",
            "getRadius", "setRadius", "getCenter");

        final List<Class> expectedMethodReturns = Arrays.asList(
            Color.class, void.class, double.class, double.class, void.class,
            double.class, void.class, Point.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
            new Class[0], new Class[] {double.class}, new Class[0]);

        verifyImplSpecifics(Circle.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }

    @Test
    public void testRectangleImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getColor", "setColor", "getArea", "getPerimeter", "translate",
            "getWidth", "setWidth", "getHeight", "setHeight", "getTopLeft");

        final List<Class> expectedMethodReturns = Arrays.asList(
            Color.class, void.class, double.class, double.class, void.class,
            double.class, void.class, double.class, void.class, Point.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
            new Class[0], new Class[] {double.class}, new Class[0], new Class[] {double.class}, new Class[0]);

        verifyImplSpecifics(Rectangle.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }

    @Test
    public void testTriangleImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getColor", "setColor", "getArea", "getPerimeter", "translate",
            "getVertexA", "getVertexB", "getVertexC");

        final List<Class> expectedMethodReturns = Arrays.asList(
            Color.class, void.class, double.class, double.class, void.class,
            Point.class, Point.class, Point.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
            new Class[0], new Class[0], new Class[0]);

        verifyImplSpecifics(Triangle.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }

    @Test
    public void testConvexPolygonImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getColor", "setColor", "getArea", "getPerimeter", "translate",
            "getVertex");

        final List<Class> expectedMethodReturns = Arrays.asList(
            Color.class, void.class, double.class, double.class, void.class,
            Point.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
            new Class[] {int.class});

        verifyImplSpecifics(ConvexPolygon.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
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
