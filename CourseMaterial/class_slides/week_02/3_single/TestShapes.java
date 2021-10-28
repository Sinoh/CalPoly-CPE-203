
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//
// "import static" can be used to avoid typing long names for
// static functions in other classes.  The following imports
// let us just say, for exmaple, "PI" instead of
// "java.lang.Math.PI" or "Math.PI".
//
import static java.lang.Math.PI;
import static java.awt.Color.RED;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Test;


/**
 * JUnit tests for the non-GUI parts of Square and Circle.  Run with:  <pre>
 *
 * java org.junit.runner.JUnitCore TestShapes
 * </pre>
 * JUnit is described at https://junit.org/junit4/
 */

public class TestShapes {

    private final static float TOLERANCE = 0.000001f;

    //
    // Things like "@Test" are called "annotations."  Annotations are
    // part of Java's mechanisms for "reflection."  Reflection really has
    // nothing to do with OO programming per se; it's an orthogonal concept.
    // It's an important part of Java, but out of scope of this class.  For
    // now, just know that org.junit.runner.JUnitCore uses this marking to
    // search through the methods of TestShapes and find the tests.
    //
    @Test
    public void testPoint() {
	Point pt = new Point(2.1f, 3.7f);
	assertEquals("check Point.getX()", pt.getX(), 2.1, TOLERANCE);
	assertEquals("check Point.getY()", pt.getY(), 3.7, TOLERANCE);
    }

    @Test
    public void testSquare() {
	Square sq = new Square(new Point(1, 1), new Point(4, 5), RED);
	if (sq.getArea() != 12.0) {
	    fail();	// Is this OK?  How is it different from assertEquals()?
	}
    }

    @Test
    public void testCircle1() {
	Circle circle1 = new Circle(new Point(1.3f, 2.7f), 5.6f, RED);
	Circle circle2 = new Circle(new Point(-0.7f, 2.9f), 5.6f, RED);
	assertTrue("Circle area 1", circle1.getArea() == circle2.getArea());
    }

    @Test
    public void testCircle2() {
	float[] radii = { 0.1f, 27.6f, 7.1f };
	float[] answers = new float[radii.length];
	float[] results = new float[radii.length];
	for (int i = 0; i < radii.length; i++) {
	    answers[i] = (float) (PI * radii[i] * radii[i]);
	    Circle c = new Circle(new Point(1, 2), radii[i], RED);
	    results[i] = c.getArea();
	}
	assertArrayEquals("Circle area 2", answers, results, TOLERANCE);
    }

    @Test
    public void testCircle3() {
	Circle c = new Circle(new Point(0.1f, 2.7f), 4.5f, RED);
	assertEquals("Circle area 3", (float) (PI * 4.5 * 4.5), c.getArea(), TOLERANCE);
    }
}


