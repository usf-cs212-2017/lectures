
/**
 * Demonstrates basic boundary matching in regular expressions. Shows how
 * the MULTILINE (?m) and DOTALL (?s) flags changes the results.
 *
 * @see RegexHelper
 */
public class RegexBoundaries {

	public static void main(String[] args) {

		// Example string for testing regular expressions.
		String text = "Knock knock!\nWho's there?";
		System.out.println(text);

		System.out.println();
		System.out.println("Input Boundary \\A and \\z");

		// .* will not match \n
		// since no single-line input, does not match anything
		RegexHelper.printMatches(text, "\\A.*\\z");

		// .* will match \n
		// the (?s) flag enables matching \n when using "."
		RegexHelper.printMatches(text, "(?s)\\A.*\\z");

		System.out.println();
		System.out.println("Line Boundary ^ and $");

		// .* will not match \n
		// []
		RegexHelper.printMatches(text, "^.*$");

		// ^$ will look at individual lines
		// the (?m) flag enables multiline mode
		// [Knock knock!, Who's there?]
		RegexHelper.printMatches(text, "(?m)^.*$");

		// .* will match \n
		// [Knock knock!\nWho's there?]
		RegexHelper.printMatches(text, "(?s)^.*$");

		// greedy, matches everything
		// [Knock knock!\nWho's there?]
		RegexHelper.printMatches(text, "(?ms)^.*$");

		// reluctant, matches each line
		// [Knock knock!, Who's there?]
		RegexHelper.printMatches(text, "(?ms)^.*?$");
	}
}
