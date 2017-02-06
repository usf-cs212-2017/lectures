
public class RegexWordParsing {

	public static void main(String[] args) {

		// Sally Sue sells 76 sea-shells, by   the sea_shore.

		String regex = ".*";

		System.out.println("Regex: " + regex);
		System.out.println();

		RegexHelper.showMatches(RegexHelper.sample, "\\b\\w");

	}
}
