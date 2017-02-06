import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexHelper {

	public static final String sample = "Sally Sue sells 76 sea-shells, by   the sea_shore.";

	public static void printMatches(String text, String regex) {
		ArrayList<String> matches = getMatches(text, regex);

		for (int i = 0; i < matches.size(); i++) {
			matches.set(i, matches.get(i).replaceAll("\n", "\\\\n"));
		}

		System.out.printf("%s: %s%n", regex, matches);
	}

	public static ArrayList<String> getMatches(String text, String regex) {

		ArrayList<String> matches = new ArrayList<String>();

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);

		int index = 0;

		while ((index < text.length()) && m.find(index)) {

			matches.add(text.substring(m.start(), m.end()));

			if (m.start() == m.end()) {
				index = m.end() + 1;
			}
			else {
				index = m.end();
			}
		}

		return matches;
	}

	public static void showMatches(String text, String regex) {

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);

		char fill = '_';
		char[] region = new char[text.length()];
		Arrays.fill(region, fill);

		int index = 0;
		int count = 0;

		while ((index < text.length()) && m.find(index)) {

			fill = Character.forDigit(count % 36, 36);
			fill = Character.toUpperCase(fill);
			Arrays.fill(region, m.start(), m.end(), fill);

			if (m.start() == m.end()) {
				region[m.start()] = '*';
				index++;
			}
			else {
				index = m.end();
			}

			count++;
		}

		System.out.println(text);
		System.out.println(region);
	}
}
