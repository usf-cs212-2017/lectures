import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexQuiz {

	public static void main(String[] args) {

		String text = null;
		String regex = null;

		text = "hubbub";
		RegexHelper.printMatches(text, "h.*b");
//		RegexHelper.printMatches(text, "h.*?b");
//		RegexHelper.printMatches(text, "h.*bb*");
//		RegexHelper.printMatches(text, "h.*bb+");
//		RegexHelper.printMatches(text, "h.*bb?");
//		RegexHelper.printMatches(text, "h.*?bb?");

		System.out.println();

		text = "ant ape bat bee bug cat cow dog";
//		RegexHelper.printMatches(text, "\\w*a\\w*");
//		RegexHelper.printMatches(text, "\\w+a\\w+");
//		RegexHelper.printMatches(text, "\\w+t\\b");
//		RegexHelper.printMatches(text, "\\w*[^e]e\\b");

		System.out.println();

		text = "dragonfly";
		regex = "(drag(on))(fly)";

//		System.out.println(regex);

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);

//		if (m.matches()) {
//			for (int i = 0; i <= m.groupCount(); i++) {
//				System.out.println(i + ": " + m.group(i));
//			}
//		}

	}

}
