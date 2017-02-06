
public class RegexQuantifiers {

	public static void main(String[] args) {

		String text = "aardvark";
		String regex = "a";

		System.out.println("\nRegex: " + regex);
		System.out.println();

		RegexHelper.showMatches(text, regex);

	}
}
