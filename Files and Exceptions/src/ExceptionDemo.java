import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Demonstrates different strategies for dealing with exceptions.
 */
public class ExceptionDemo {

	/**
	 * A simple method to calculate the percentage given the total points earned
	 * versus the total points possible. Note that this method does not
	 * explicitly throw any exceptions, but an ArithmeticException will be
	 * thrown if the value of possible is 0.
	 *
	 * @param total
	 *            points earned
	 * @param possible
	 *            points
	 * @return percentage
	 */
	private static int calcPercentage(int total, int possible) {
		return 100 * total / possible;
	}

	/**
	 * A simple helper method for outputting the results in a nice format. Not
	 * the focus of this example.
	 *
	 * @param total
	 *            points earned
	 * @param possible
	 *            points
	 * @param percentage
	 */
	private static void printResult(int total, int possible, int percentage) {
		System.out.printf("%d/%d = %d%% %n", total, possible, percentage);
	}

	/**
	 * This shows what the code looks like if we assume no exceptions or invalid
	 * data will occur. It is pretty easy to understand what this code is trying
	 * to do, and hence easier to debug.
	 */
	public static void uncaughtDemo() {
		int earned = 0;
		int possible = 0;
		int percentage = 0;

		Scanner scanner = new Scanner(System.in);

		// Can throw InputMismatchException
		System.out.print("Enter total points earned: ");
		earned = scanner.nextInt();

		// Can be a negative/invalid number
		System.out.print("Enter total points possible: ");
		possible = scanner.nextInt();

		// Can throw ArithmeticException
		percentage = calcPercentage(earned, possible);
		printResult(earned, possible, percentage);

		// Can throw IllegalStateException
		scanner.close();
		System.out.println("[done]");
	}

	/**
	 * Here, we try to handle anything that can go wrong without catching any
	 * exceptions. Instead, we try to constantly validate the input data. This
	 * clutters the code quite a bit, making it harder to follow. Also, notice
	 * we have a problem if we return too soon without closing the Scanner
	 * object.
	 */
	@SuppressWarnings("resource")
	public static void validationDemo() {
		int earned = 0;
		int possible = 0;
		int percentage = 0;

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter total points earned: ");

		if (scanner.hasNextInt()) {
			earned = scanner.nextInt();
		}
		else {
			// Use return instead of System.exit(), which gives the calling
			// method a chance to react if necessary.
			System.err.println("Please enter integer values.");
			return;
		}

		System.out.print("Enter total points possible: ");

		if (scanner.hasNextInt()) {
			possible = scanner.nextInt();
		}
		else {
			System.err.println("Please enter integer values.");
			return;
		}

		if (earned < 0 || possible <= 0) {
			System.err.println("Please enter non-negative values.");
			return;
		}

		percentage = calcPercentage(earned, possible);
		printResult(earned, possible, percentage);

		// Uh oh, what happens if we returned because one of the issues
		// earlier? Will the Scanner be closed properly?
		scanner.close();
		System.out.println("[done]");
	}

	/**
	 * This is much easier to read. The try block is as uncluttered as the
	 * uncaught demo, and always makes sure the Scanner is closed. However, we
	 * are not catching cases with negative values and the output is not
	 * user-friendly!
	 */
	public static void catchAllDemo() {
		int earned = 0;
		int possible = 0;
		int percentage = 0;

		Scanner scanner = null;

		try {
			scanner = new Scanner(System.in);

			// Can throw InputMismatchException
			System.out.print("Enter total points earned: ");
			earned = scanner.nextInt();

			// Can be a negative/invalid number
			System.out.print("Enter total points possible: ");
			possible = scanner.nextInt();

			// Can throw ArithmeticException
			percentage = calcPercentage(earned, possible);
			printResult(earned, possible, percentage);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			scanner.close();
			System.out.println("[done]");
		}
	}

	/**
	 * Here, we make several improvements. A try-with-resources block for the
	 * Scanner object removes the necessity of the finally block. We added back
	 * in a little bit of validation where necessary, and made the catch blocks
	 * more specific to give better output to the user.
	 */
	public static void resourcefulDemo() {
		int earned = 0;
		int possible = 0;
		int percentage = 0;

		try (Scanner scanner = new Scanner(System.in);) {
			// Can throw InputMismatchException
			System.out.print("Enter total points earned: ");
			earned = scanner.nextInt();

			// Can be a negative/invalid number
			System.out.print("Enter total points possible: ");
			possible = scanner.nextInt();

			// We can also throw exceptions, so that we can still place the
			// code for dealing with when things go wrong in a catch block.
			if (earned < 0 | possible <= 0) {
				throw new IllegalArgumentException("Values must be non-negative.");
			}

			// Can throw ArithmeticException
			percentage = calcPercentage(earned, possible);
			printResult(earned, possible, percentage);
		}
		catch (InputMismatchException e) {
			// Don't return or exit inside a catch unless you really need to.
			// There is only one statement outside of the try/catch, so we
			// can just fall through to the end of the method.
			System.err.println("Please enter integer values.");
		}
		catch (ArithmeticException | IllegalArgumentException e) {
			// Notice we can handle two types of exceptions at once, allowing
			// for more code reuse.
			System.err.println("Please enter non-negative values.");
		}
		catch (Exception e) {
			// This shouldn't ever happen, but then again, bugs happen.
			System.err.println(e.toString());
		}

		System.out.println("[done]");
	}

	/**
	 * Never write a main method that throws an exception to the user!
	 *
	 * @param args
	 *            (unused)
	 */
	public static void main(String[] args) {
		// Earned: 2, Possible: 4, Percentage: 30%
		// Earned: a, Error
		// Earned: 2, Possible: a, Error
		// Earned: 2, Possible: 0, Error
		// Earned: 2, Possible: -1, Error?

		// uncaughtDemo();
		// validationDemo();
		// catchAllDemo();
		resourcefulDemo();
	}
}
