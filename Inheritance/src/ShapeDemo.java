/**
 * Demonstrates several inheritance concepts, including basic inheritance,
 * upcasting and downcasting, nested classes, and anonymous classes.
 *
 * @see {@link ShapeDemo}
 * @see {@link Shape}
 * @see {@link Rectangle}
 * @see {@link Square}
 */
public class ShapeDemo {
	private static final String format = "Variable: %-2s | Class: %-11s | ID: %#010x | Type: %-9s | String: %-15s | Area: %6.2f\n";

	/**
	 * Displays basic information about the shape, including the class name,
	 * hash code, type, String representation, and area. Uses casting so that
	 * one method can be provided for any type of Shape object.
	 *
	 * @param variable
	 *            - name of the variable for display purposes
	 * @param shape
	 *            - shape object to display
	 */
	private static void printInfo(String variable, Shape shape) {
		System.out.printf(format, variable, shape.getClass().getName(), shape.hashCode(), shape.getName(),
				shape.toString(), shape.area());
	}

	public static void main(String[] args) {
		/*
		 * What will be the class name? (Rectangle) What will be the type?
		 * (Rectangle) What will be the string? (0.00w x 0.00h)
		 */
		Rectangle r1 = new Rectangle();
		System.out.printf(format, "r1", r1.getClass().getName(), r1.hashCode(), r1.getName(), r1.toString(), r1.area());

		/*
		 * What will be the string? (10.50w x 3.20h)
		 */
		Rectangle r2 = new Rectangle(10.5, 3.2);
		System.out.printf(format, "r2", r2.getClass().getName(), r2.hashCode(), r2.getName(), r2.toString(), r2.area());

		/*
		 * Can a square be treated as a rectangle? (Yes, is-a versus has-a) Does
		 * the type of variable or type of referenced object matter? (Referenced
		 * object) What will be the class/type? (Square)
		 */
		Rectangle r3 = new Square(4.6);
		System.out.printf(format, "r3", r3.getClass().getName(), r3.hashCode(), r3.getName(), r3.toString(), r3.area());

		/*
		 * Can we go in the other direction? (No, superclass isn't-a subclass)
		 * Square q0 = new Rectangle();
		 */

		/*
		 * What is downcasting? (Cast from superclass to subclass) Is r3 and q1
		 * the same object? (Yes, see ID)
		 */
		Square q1 = (Square) r3;
		System.out.printf(format, "q1", q1.getClass().getName(), q1.hashCode(), q1.getName(), q1.toString(), q1.area());

		/*
		 * Why would I use the superclass as the variable type? (Show method)
		 * Can I use printInfo on Square? (Yes, is-a relationship)
		 */
		Square q2 = new Square(9.12);
		printInfo("q2", q2);

		/*
		 * Can I create a Shape instance? (No, abstract) Can I create a Shape
		 * variable? (Yes, superclass/subclass relationship) Can I call
		 * printInfo on Shape? (Yes)
		 */
		Shape s1 = q2;
		printInfo("s1", s1);

		/*
		 * How do we create an inner class? (See A) What will be the class name?
		 * (ShapeDemo$A) What will be the type? (Nested)
		 */
		A s2 = new A();
		printInfo("s2", s2);

		/*
		 * How do we create an anonymous inner class? (See below) What will be
		 * the class name? (ShapeDemo$1) What will be the shape name? (Anon)
		 */
		Shape s3 = new Shape("Anon") {
			@Override
			public double area() {
				return 0.0;
			}
		};

		printInfo("s3", s3);

		System.out.println("Finished!");
	}

	/**
	 * Demonstrates how to create a static nested class.
	 */
	private static class A extends Shape {
		public A() {
			super("Nested");
		}

		@Override
		public double area() {
			return -1.0;
		}
	}
}
