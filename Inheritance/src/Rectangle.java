/**
 * Demonstrates several inheritance concepts, including basic inheritance,
 * upcasting and downcasting, nested classes, and anonymous classes.
 *
 * @see {@link ShapeDemo}
 * @see {@link Shape}
 * @see {@link Rectangle}
 * @see {@link Square}
 */
public class Rectangle extends Shape {
	/*
	 * Note that we can declare our own private data. These members are not
	 * accessible by our superclasses or subclasses.
	 */

	private final double width;
	private final double height;

	/*
	 * Note the call to super() in the constructor below. This will make sure
	 * the Shape constructor is called to initialize the shape name.
	 */

	public Rectangle(double width, double height) {
		super(); // call direct superclass constructor
		this.width = width;
		this.height = height;
	}

	/*
	 * A default constructor still makes sense here. This constructor will call
	 * the other constructor above, which calls the constructor for Shape. This
	 * is an example of chaining constructor calls.
	 */

	public Rectangle() {
		this(0.0, 0.0);
	}

	/*
	 * If you want to be able to create a Rectangle object (i.e. you do not want
	 * to make this class abstract), you MUST implement all of the abstract
	 * methods inherited at this point.
	 */

	@Override
	public double area() {
		return width * height;
	}

	/*
	 * You can override non-abstract methods too if appropriate.
	 */

	@Override
	public String toString() {
		return String.format("%5.2fw x %5.2fh", width, height);
	}

	/*
	 * You can also provide new methods not found in Shape. These will be
	 * inherited by any subclasses of Rectangle.
	 */

	public double width() {
		return width;
	}

	public double height() {
		return height;
	}
}
