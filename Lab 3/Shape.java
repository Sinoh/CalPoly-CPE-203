import java.awt.*;

interface Shape {

	Color getColor();

	void setColor(Color color);

	double getArea();

	double getPerimeter();

	void translate(Point point);

}
/*
	Color getColor() - Returns the java.awt.Color of the Shape.
		void setColor() - Sets the java.awt.Color of the Shape.
		double getArea() - Returns the area of the Shape
		double getPerimeter() - Returns the perimeter of the Shape
		void translate() - Translates the entire shape by the (x,y) coordinates of a given java.awt.Point

*/