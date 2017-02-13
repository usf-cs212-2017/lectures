import java.util.List;

/**
 * This demo briefly illustrates some basic terminology for object-oriented
 * programming.
 *
 * @see Widget
 * @see WidgetDemo
 */

public class WidgetDemo {

	public static void main(String[] args) {
		/*
		 * STATIC - Since this method is static, we can access it when there are
		 * no instances created. That is why static members are initialized at
		 * declaration!
		 */
		System.out.println(Widget.numWidgets());

		/*
		 * You can access and directly set public static members anytime.
		 */
		Widget.debug = true;

		/*
		 * INSTANCE - The following are actual objects, or instances of the
		 * Widget class. They have their own instance attributes, but share
		 * class attributes.
		 *
		 * DECLARATION - We have declared the identifiers for these elements,
		 * but memory has not yet been reserved. Any access will result in a
		 * null pointer exception.
		 */
		Widget widget1;
		Widget widget2;

		/*
		 * DEFINITION - We are reserving memory with the "new" keyword, creating
		 * actual objects with initial values.
		 */
		widget1 = new Widget("A");
		widget2 = new Widget("B");

		// This uses the toString() method automatically.
		System.out.println(widget1);
		System.out.println(widget2);

		// Note our static member has updated properly.
		System.out.println(Widget.numWidgets());

		// We are unable to modify the name to an invalid value.
		widget1.setName(null);
		System.out.println(widget1);

		// We can see all the widget names created so far.
		System.out.println(Widget.getNames());

		// Notice that we could not access Widget.widgetNames directly.
		// But, we can get the reference!
		List<String> unsafe = Widget.getNamesUnsafe();
		unsafe.clear();
		System.out.println(Widget.getNames());

		try {
			List<String> safe = Widget.getNames();
			safe.clear();
		}
		catch (Exception e) {
			System.out.println("Unable to modify list.");
			System.out.println(Widget.getNames());
		}

	}
}
