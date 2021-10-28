import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class WorkSpace{

    private List<Shape> shapes;

    public WorkSpace () {
        this.shapes = new ArrayList<>();
    }

    public void add(Shape shape) {
        this.shapes.add(shape);
    }

    public Shape get(int index) {
        return this.shapes.get(index);
    }

    public int size() {
        return this.shapes.size();
    }

    public List<Circle> getCircles() {
        ArrayList<Circle> CircleList = new ArrayList<>();

        for (int n = 0; n < this.shapes.size(); n++) {
            if (this.shapes.get(n) instanceof Circle) {
                CircleList.add((Circle)this.shapes.get(n));
            }
        }
        return CircleList;
    }

    public List<Rectangle> getRectangle() {
        ArrayList<Rectangle> RectangleList = new ArrayList<>();

        for (int n = 0; n < this.shapes.size(); n++) {
            if (this.shapes.get(n) instanceof Rectangle) {
                RectangleList.add((Rectangle)this.shapes.get(n));
            }
        }
        return RectangleList;
    }

    public List<Triangle> getTriangle() {
        ArrayList<Triangle> TriangleList = new ArrayList<>();

        for (int n = 0; n < this.shapes.size(); n++) {
            if (this.shapes.get(n) instanceof Triangle) {
                TriangleList.add((Triangle)this.shapes.get(n));
            }
        }
        return TriangleList;
    }

    public List<ConvexPolygon> getConvexPolygon() {
        ArrayList<ConvexPolygon> ConvexPolygonList = new ArrayList<>();

        for (int n = 0; n < this.shapes.size(); n++) {
            if (this.shapes.get(n) instanceof ConvexPolygon) {
                ConvexPolygonList.add((ConvexPolygon)this.shapes.get(n));
            }
        }
        return ConvexPolygonList;
    }

    public List<Shape> getShapesByColor(Color color) {
        ArrayList<Shape> ShapeList = new ArrayList<>();

        for (int n = 0; n < this.shapes.size(); n++) {
            if (this.shapes.get(n).getColor() == color ) {
                ShapeList.add(this.shapes.get(n));
            }
        }
        return ShapeList;
    }

    public double getAreaOfAllShapes() {
        double sum = 0.0;

        for (int n = 0; n < this.shapes.size(); n++) {
                sum += this.shapes.get(n).getArea();
            }

        return sum;
    }

    public double getPerimeterOfAllShapes() {
        double sum = 0.0;

        for (int n = 0; n < this.shapes.size(); n++) {
            sum += this.shapes.get(n).getPerimeter();
        }

        return sum;
    }
}









/*
WorkSpace() - A default constructor to initialize its instance variable to an empty List. The constructor should not take any parameters. (If you do nothing in this constructor, you may omit it from your code altogether.)
void add(Shape shape) - Adds an object which implements the Shape interface to the end of the List in the WorkSpace.
Shape get(int index) - Returns the specified Shape from the WorkSpace.
int size() - Returns the number of Shapes contained by the WorkSpace.
List<Circle> getCircles() - Returns a List of all the Circle objects contained by the WorkSpace.
List<Rectangle> getRectangles() - Returns a List of all the Rectangle objects contained by the WorkSpace.
List<Triangle> getTriangles() - Returns a List of all the Triangle objects contained by the WorkSpace.
List<ConvexPolygon> getConvexPolygons() - Returns a List of all the ConvexPolygon objects contained by the WorkSpace.
List<Shape> getShapesByColor(Color color) - Returns a List of all the Shape objects contained by the WorkSpace that match the specified Color.
double getAreaOfAllShapes() - Returns the sum of the areas of all the Shapes contained by the WorkSpace.
double getPerimeterOfAllShapes() - Returns the sum of the perimeters of all the Shapes contained by the WorkSpace.
 */


