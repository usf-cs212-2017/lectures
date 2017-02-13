import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This demo briefly illustrates some basic terminology for object-oriented
 * programming.
 *
 * @see Widget
 * @see WidgetDemo
 */

/*
 * CLASS - Defines the implementation of an OBJECT.
 */
public class Widget {

	/*
	 * INSTANCE MEMBER - Each instance will have its own value. Initiali- zation
	 * should occur in the constructor. (If you only use this value in a single
	 * method, it should be a local variable instead of an instance member.)
	 *
	 * PRIVATE - Only this class can access this value. In general, you should
	 * make members private unless you have a reason. This helps the
	 * ENCAPSULATION of your code.
	 *
	 * IDENTIFIER - The name of something, in this case the name of the instance
	 * member is "widgetName".
	 *
	 * DECLARATION - Establishes an identifier and associated attributes (such
	 * as the type).
	 */
	private String widgetName;

	/*
	 * FINAL MEMBER - Means the member can only be DEFINED once. For a primitive
	 * type, this means it can only be assigned a value once and is effectively
	 * constant.
	 */
	private final int widgetID;

	/*
	 * STATIC MEMBER/CLASS MEMBER - All instances of this class share this
	 * value. Should initialize at declaration.
	 *
	 * DEFINITION - A declaration that also reserves storage (for data) or
	 * provides an implementation (for methods).
	 */
	private static int numWidgets = 0;

	/*
	 * STATIC FINAL MEMBER - All instances of this class share this value. Must
	 * initialize at declaration. Since it is FINAL, this is the only time a
	 * reference may be assigned to this member. If the member is a mutable
	 * object, this means the reference cannot be updated (but the mutable
	 * object remains mutable). More later.
	 *
	 * GENERIC TYPES - We have one ArrayList class, but we can create ArrayList
	 * objects that store different types of elements. (We do not have one
	 * ArrayList class specific to String objects, and another specific to
	 * Integer objects, and so on.) We specify the type with the <> generics
	 * notation. Generics are a form of polymorphism ("many forms").
	 */
	private static final ArrayList<String> widgetNames = new ArrayList<>();

	/*
	 * PUBLIC - Any other class may directly modify this value. It is very rare
	 * that making a member public is appropriate. This is one of the few cases
	 * where you could justify it, since this only changes the output and not
	 * the functionality of this class. Another case is for constants.
	 */
	public static boolean debug = false;

	/*
	 * CONSTRUCTOR - The method that is called when an object/instance is
	 * created. Should initialize all of the instance members.
	 */
	public Widget(String name) {
		/*
		 * THIS - The "this" keyword refers to the current instance. Here, we
		 * set this object's widget name to whatever was passed in to the
		 * constructor.
		 */
		this.widgetName = name;

		/*
		 * FINAL - After this initialization, the value of widgetID may not
		 * change. (Try it!)
		 */
		this.widgetID = ++numWidgets;

		// Attempt redefine modify widgetID or widgetNames at this point!

		/*
		 * STATIC (ACCESS) - We access static members through the class name, to
		 * make it clear this changes the value for all instances of this class.
		 * It is not necessary WITHIN the class, however.
		 */
		Widget.widgetNames.add(widgetName);

		if (debug) {
			System.out.println("Created widget #" + widgetID + " named " + widgetName + ".");
		}
	}

	/*
	 * OVERLOADING - You can provide methods with the same name, but different
	 * parameters. They should do the same thing! A good way to ensure this is
	 * to call the other overloaded method from this one. Method overloading is
	 * a form of polymorphism ("many forms").
	 *
	 * DEFAULT CONSTRUCTOR - A constructor that takes no parameters. It is
	 * usually a good idea to create a default constructor.
	 */
	public Widget() {
		/*
		 * THIS - You can use the "this" keyword as a method too, which will
		 * call the appropriate constructor. This lets you reuse the
		 * initialization code you have written in a different constructor.
		 */
		this("Widget");
	}

	/*
	 * STATIC METHOD/CLASS METHOD - A static method may not access any instance
	 * members. The method itself does not require an instance to be created,
	 * and can be accessed through the class name.
	 *
	 * If your method does not access any instance members, make sure you make
	 * it static for a bit of speedup.
	 *
	 * GETTER/GET METHOD - Since this member is private, other classes cannot
	 * access this value (an example of ENCAPSULATION). We do not want other
	 * classes to be able to change this value, but it is okay for them to see
	 * the current value.
	 */
	public static int numWidgets() {
		return Widget.numWidgets;
	}

	/*
	 * UNSAFE GET METHOD - Since widgetNames is mutable, returning the reference
	 * is not safe. While the list reference cannot be modified, the contents of
	 * the list may be modified!
	 */
	public static List<String> getNamesUnsafe() {
		return widgetNames;
	}

	/*
	 * SAFE GET METHOD - You can make a copy and return the copy, but that takes
	 * extra time and memory. Instead, return an unmodifiable version of the
	 * list.
	 */
	public static List<String> getNames() {
		return Collections.unmodifiableList(widgetNames);
	}

	/*
	 * NON-STATIC METHOD/INSTANCE METHOD - A non-static method can only be
	 * accessed through an actual instance.
	 */
	public String getName() {
		return this.widgetName;
	}

	/*
	 * HELPER METHOD/PRIVATE METHOD - These types of methods are not visible
	 * outside of the class, and are meant to "help" other public methods in the
	 * class.
	 */
	private static boolean validName(String name) {
		return (name != null) && !name.trim().isEmpty();
	}

	/*
	 * SETTER/SET METHOD - These types of methods allow you to validate unsafe
	 * input before changing the value. In this case, we make sure the name is
	 * not null or empty.
	 *
	 * Often times, a set method returns a boolean to indicate whether the
	 * change was made.
	 */
	public boolean setName(String newName) {
		/*
		 * LOCAL VARIABLE - This variable only exists locally, inside this
		 * method. It isn't an "attribute" of this object so it does not make
		 * sense to make it a member.
		 *
		 * We use our helper method here, so that validation is always done
		 * consistently.
		 */
		boolean valid = validName(newName);

		if (valid) {
			this.widgetName = newName;
		}
		else if (debug) {
			/*
			 * THIS - Again, "this" refers to this instance. By default,
			 * whenever an Object is treated as a String, Java calls the
			 * toString() method automatically. So, the code below will call
			 * this.toString() automatically.
			 */
			System.out.println("Failed to change name for widget " + this);
		}

		return valid;
	}

	/*
	 * OVERRIDING - This replaces or overrides the implementation of toString()
	 * provided by the Object class. More on this when we discuss inheritance.
	 */
	@Override
	public String toString() {
		return widgetName + " (" + widgetID + ")";
	}
}
