import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses an email into its local, domain, and top-level domain components. Does
 * not do full email validation. The primary purpose of this class is to demo
 * unit testing.
 *
 * @see EmailParserTest
 */
public class EmailParser {
	/** Reconstructed email. */
	private String email;

	/** Local component of email; appears before the "@" at sign. */
	private String local;

	/** Domain component of the email; appears after the "@" at sign. */
	private String domain;

	/**
	 * Top-level domain component of the domain; similar to the extension of a
	 * file.
	 */
	private String tld;

	/**
	 * Initializes each component of the specified email.
	 *
	 * @param email
	 *            email to parse into components
	 * @throws IllegalArgumentException
	 *             when the email cannot be parsed
	 */
	public EmailParser(String email) {
		// There are many possible regular expressions.
		// Note: Validating RFC-compliant emails are pretty hard :(
		String regex = "([^@]+)@([^@]+?(?:\\.([^@.]+))?)";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);

		if (!matcher.matches()) {
			throw new IllegalArgumentException("Unable to parse the email " + email + ".");
		}

		assert matcher.groupCount() == 3;

		this.email = matcher.group(0);
		this.local = matcher.group(1);
		this.domain = matcher.group(2);
		this.tld = matcher.group(3);
	}

	/**
	 * Returns the local component of the email.
	 *
	 * @return local component of email
	 */
	public String getLocal() {
		return local;
	}

	/**
	 * Returns the domain component of the email.
	 *
	 * @return domain component of email
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * Returns the top-level domain of the email.
	 *
	 * @return top-level domain of email
	 */
	public String getTLD() {
		return tld;
	}

	/**
	 * Returns the reconstructed email from the local and domain components.
	 *
	 * @return reconstructed email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns a string with all of the email components. Useful for debugging.
	 *
	 * @return debug output for email
	 */
	public String debug() {
		return String.format("Email: %s, Local: %s, Domain: %s, TLD: %s", getEmail(), getLocal(), getDomain(),
				getTLD());
	}

	@Override
	public String toString() {
		return getEmail();
	}
}
