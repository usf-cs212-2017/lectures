
public class RegexBoundaries {

	public static void main(String[] args) {

		String text = "Knock knock!\nWho's there?";
		System.out.println(text);
		System.out.println();
		
		RegexHelper.printMatches(text, ".*");
	}
}
