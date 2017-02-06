/**
 * Demonstrates basic character classes in regular expressions.
 *
 * @see RegexHelper
 */
public class RegexClasses {

	public static void main(String[] args) {

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		//           0   1    2   3    4           5   6
		System.out.println("Lowercase s");
		RegexHelper.showMatches(RegexHelper.sample, "s");

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 0     1   2   3    4   5    6           7   8
		System.out.println("Lowercase or Uppercase [sS]");
		RegexHelper.showMatches(RegexHelper.sample, "[sS]");

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 0     1   2   3    4   5    6           7   8
		System.out.println("Lowercase or Uppercase (?i)s");
		RegexHelper.showMatches(RegexHelper.sample, "(?i)s");

		System.out.println();

		/*
		 * The above example shows how to use the (?i) flag in a regex.
		 */

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		//  0123  45 6789A    BCD EFGHIJ  KL   MNO PQR STUVW
		System.out.println("Lowercase Letters [a-z]");
		RegexHelper.showMatches(RegexHelper.sample, "[a-z]");

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 0     1
		System.out.println("Uppercase  Letters \\p{Upper}");
		RegexHelper.showMatches(RegexHelper.sample, "\\p{Upper}");

		System.out.println();

		/*
		 * As demonstrated above, there are several ways to specify
		 * equivalent character classes.
		 */

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		//                 01
		System.out.println("Digit Characters \\d");
		RegexHelper.showMatches(RegexHelper.sample, "\\d");

		System.out.println();

		/*
		 * Notice in digit output above that each digit 7 and 6 are
		 * individual matches.
		 */

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 01234 567 89ABC DE FGH IJKLMN  OP   QRS TUVWXYZ01
		System.out.println("Word Characters \\w");
		RegexHelper.showMatches(RegexHelper.sample, "\\w");

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 01234 567 89ABC DE FGHIJKLMNOP QR   STU VWXYZ01234
		System.out.println("Non-Whitespace Characters \\S");
		RegexHelper.showMatches(RegexHelper.sample, "\\S");

		System.out.println();

		/*
		 * Notice difference between word and non-whitespace characters
		 * above is whether the symbols match.
		 */

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		//      0   1     2  3           4  567   8
		System.out.println("Whitespaces \\s");
		RegexHelper.showMatches(RegexHelper.sample, "\\s");

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		//      0   1     2  3   4      56  789   A         B
		System.out.println("Non-Word Characters \\W");
		RegexHelper.showMatches(RegexHelper.sample, "\\W");

		System.out.println();

		/*
		 * Notice difference between whitespace and non-word characters
		 * above is whether the symbols match.
		 */

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789ABCD
		System.out.println("Any Character .");
		RegexHelper.showMatches(RegexHelper.sample, ".");
	}
}
