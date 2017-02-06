/**
 * Demonstrates basic word matching in regular expressions. Requires
 * the {@link RegexHelper} class.
 *
 * @see RegexHelper
 */
public class RegexWordParsing {

	public static void main(String[] args) {

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 0_____1___2_____3__4___5_______6____7___8_________
		System.out.println("Start of Word \\b\\w");
		RegexHelper.showMatches(RegexHelper.sample, "\\b\\w");

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// ____0___1_____2__3___4______5___6_____7_________8_
		System.out.println("End of Word \\w\\b");
		RegexHelper.showMatches(RegexHelper.sample, "\\w\\b");

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 00000_111_22222_33_444_555555__66___777_888888888_
		System.out.println("Words (Sans Hyphen) \\b\\w+\\b");
		RegexHelper.showMatches(RegexHelper.sample, "\\b\\w+\\b");

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 00000_111_22222_33_444_555555__66___777_888888888_
		System.out.println("Words (Sans Hyphen) \\w+");
		RegexHelper.showMatches(RegexHelper.sample, "\\w+");

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 00000_111_22222_33_4444444444__55___666_777777777_
		System.out.println("Words (With Hyphen) \\S+\\b");
		RegexHelper.showMatches(RegexHelper.sample, "\\S+\\b");

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 00000_111_22222_33_44444444444_55___666_7777777777
		System.out.println("Words (With Symbol) \\S+");
		RegexHelper.showMatches(RegexHelper.sample, "\\S+");

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 00000_111_22222____3333333333___________444444444_
		System.out.println("Words that start with S (?i)s\\S+\\b");
		RegexHelper.showMatches(RegexHelper.sample, "(?i)s\\S+\\b");

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// ______000_11111____2222222222_______333_444444444_
		System.out.println("Words that contain an e \\b\\S*e\\S*\\b");
		RegexHelper.showMatches(RegexHelper.sample, "\\b\\S*e\\S*\\b");
	}
}
