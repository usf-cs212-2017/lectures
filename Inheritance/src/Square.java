/**
 * Demonstrates several inheritance concepts, including basic inheritance,
 * upcasting and downcasting, nested classes, and anonymous classes.
 *
 * @see {@link ShapeDemo}
 * @see {@link Shape}
 * @see {@link Rectangle}
 * @see {@link Square}
 */
public class Square extends Rectangle {

	/*
	 * What will be the shapeName in this case?
	 */

	public Square(double width) {
		super(width, width);
	}

	public Square() {
		this(0.0);
	}
}
