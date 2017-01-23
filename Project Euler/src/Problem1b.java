/**
 * Project Euler Problem 1 is stated as follows:
 *
 * <blockquote> If we list all the natural numbers below 10 that are multiples
 * of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23. Find the
 * sum of all the multiples of 3 or 5 below 1000. </blockquote>
 *
 * This example illustrates a more reusable and generalized approach, although
 * there is more work to be done.
 */
public class Problem1b {

	/**
	 * Sums together non-negative multiples of 3 and 5 less than the maximum
	 * value.
	 *
	 * @param max
	 *            maximum value to consider
	 * @return sum of multiples of 3 and 5 less than the maximum value
	 */
	public static int sumMultiples(int max) {
		int sum = 0;

		for (int i = 0; i < max; i++) {
			if (i % 3 == 0 || i % 5 == 0) {
				sum += i;
			}
		}

		return sum;
	}

	/**
	 * Prints the sum of multiples of 3 or 5 less than 1000 to the console.
	 *
	 * @param args
	 *            unused
	 */
	public static void main(String[] args) {
		System.out.println(sumMultiples(1000));
	}

	/*
	 * This code is more reusable now that we have a separate method that can be
	 * called outside of this class and return the value. It is also more
	 * generalized since it now takes a maximum value. However, the main()
	 * method still uses a hard-coded value. It would be nice to make this
	 * method more reusable and generalized as well!
	 */
}
