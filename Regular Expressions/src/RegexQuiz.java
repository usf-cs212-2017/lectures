import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tests how well you combine different regex components.
 *
 * @see RegexHelper
 */
public class RegexQuiz {

	public static void main(String[] args) {

		String text = null;
		String regex = null;

		text = "hubbub";
		RegexHelper.printMatches(text, "h.*b"); 		// [hubbub]
		RegexHelper.printMatches(text, "h.*?b"); 	// [hub]
		RegexHelper.printMatches(text, "h.*bb*"); 	// [hubbub]
		RegexHelper.printMatches(text, "h.*bb+"); 	// [hubb]
		RegexHelper.printMatches(text, "h.*bb?"); 	// [hubbub]
		RegexHelper.printMatches(text, "h.*?bb?"); 	// [hubb]

		System.out.println();

		text = "ant ape bat bee bug cat cow dog";
		RegexHelper.printMatches(text, "\\w*a\\w*"); 	// [ant, ape, bat, cat]
		RegexHelper.printMatches(text, "\\w+a\\w+"); 	// [bat, cat]
		RegexHelper.printMatches(text, "\\w+t\\b"); 		// [ant, bat, cat]
		RegexHelper.printMatches(text, "\\w*[^e]e\\b"); 	// [ape]

		System.out.println();

		text = "dragonfly";
		regex = "(drag(on))(fly)";

		System.out.println(regex);

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);

		if (m.matches()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				System.out.println(i + ": " + m.group(i));
			}
		}

		// 0: dragonfly
		// 1: dragon
		// 2: on
		// 3: fly
	}

}
