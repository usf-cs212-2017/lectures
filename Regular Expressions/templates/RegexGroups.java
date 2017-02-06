import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexGroups {

	public static void main(String[] args) {
		String email = "username@subdomain.example.com";

		// TODO
		String regex = ".*";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);

		if (matcher.matches()) {

			// TODO

		}

	}

}
