import java.util.Arrays;

/**
 * Illustrates basic mutability using an array and primitive types. Most data
 * structures are mutable, meaning you can modify the content stored by the data
 * structure by passing around the reference.
 */
public class MutabilityDemo {

	public static void main(String[] args) {

		/*
		 * We start by creating a simple integer array. The "new" keyword
		 * indicates we are allocating new memory for this array.
		 */

		System.out.println("int[] a = new int[] { 0, 1, 2 };");
		int[] a = new int[] { 0, 1, 2 };
		System.out.println("a: " + Arrays.toString(a));
		System.out.println();

		/*
		 * Arrays are mutable, so when we change an individual value in the
		 * array, it is modifying the memory already allocated to that array.
		 */

		System.out.println("a[0] = 3;");
		a[0] = 3;
		System.out.println("a: " + Arrays.toString(a));
		System.out.println();

		/*
		 * Here notice we are not allocating new memory, as there is no new
		 * keyword. Instead, we are simply creating another way to refer to the
		 * memory already allocated to "a". Think of it as a nickname.
		 *
		 * Since arrays are also mutable, whenever we change the value in
		 * memory, all references to that memory will see this change.
		 */

		System.out.println("int[] b = a; b[1] = 4;");
		int[] b = a;
		b[1] = 4;
		System.out.println("a: " + Arrays.toString(a));
		System.out.println("b: " + Arrays.toString(b));
		System.out.println();

		/*
		 * References to this memory could be in another scope. For example, the
		 * incrementFirst() method here has a reference to the array's memory,
		 * so changes within that method will be seen outside of the method as
		 * well.
		 */

		System.out.println("incrementFirst(a);");
		incrementFirst(a);
		System.out.println("a: " + Arrays.toString(a));
		System.out.println("b: " + Arrays.toString(b));
		System.out.println();

		/*
		 * If we use the "new" keyword, that means new memory is being created.
		 * In this case, "b" will no longer refer to the same memory as "a".
		 */
		System.out.println("b = new int[] { 5, 6, 7 };");
		b = new int[] { 5, 6, 7 };
		System.out.println("a: " + Arrays.toString(a));
		System.out.println("b: " + Arrays.toString(b));
		System.out.println();

		/*
		 * Primitive types are different. Mutability is something we really only
		 * discuss in to the context of objects, but you can think of a
		 * primitive type as being immutable. If you modify the value, you break
		 * the original memory reference.
		 */

		System.out.println("int c = a[2]; c = 8;");
		int c = a[2];
		c = 8;
		System.out.println("a: " + Arrays.toString(a));
		System.out.println("b: " + Arrays.toString(b));
		System.out.println("c: " + c);
		System.out.println();

		/*
		 * This is why primitives are often considered to behave like pass-by-
		 * value. When you pass the value to a method, any modifications to that
		 * value are lost when the method scope is exited unless you return
		 * those changes.
		 */

		System.out.println("incrementPrimitive(c);");
		incrementPrimitive(c);
		System.out.println("c: " + c);
		System.out.println();
	}

	public static void incrementFirst(int[] z) {
		z[0]++;
		System.out.println("z: " + Arrays.toString(z));
	}

	public static void incrementPrimitive(int z) {
		z++;
		System.out.println("z: " + z);
	}

}
