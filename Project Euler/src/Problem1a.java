/**
 * Project Euler Problem 1 is stated as follows:
 *
 * <blockquote> If we list all the natural numbers below 10 that are multiples
 * of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23. Find the
 * sum of all the multiples of 3 or 5 below 1000. </blockquote>
 *
 * This example illustrates the most straight-forward approach to solve this
 * problem.
 */
public class Problem1a {

	/**
	 * Prints the sum of multiples of 3 or 5 less than 1000 to the console.
	 *
	 * @param args
	 *            unused
	 */
	public static void main(String[] args) {
		int max = 1000;
		int sum = 0;

		for (int i = 0; i < max; i++) {
			if (i % 3 == 0 || i % 5 == 0) {
				sum += i;
			}
		}

		System.out.println(sum);
	}

	/*
	 * What are the possible issues with this code? Realistically, none. This is
	 * too simple of a problem.
	 *
	 * Pretend for a moment this is a complex and useful problem to solve. In
	 * that case, we want generalized and reusable code so our solution has as
	 * much impact as possible.
	 *
	 * Is this code generalized?
	 *
	 * No. It solves for a maximum number of 1000 and for multiples of 3 or 5
	 * only. Any changes required modification to the original source code.
	 *
	 * Is this code reusable?
	 *
	 * No. You can call the main() function from other code since it is public,
	 * but you cannot get the return value or change the parameters of the
	 * problem.
	 */
}
