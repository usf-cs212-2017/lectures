import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Demonstrates how to create unit tests.
 *
 * @see EmailParser
 * @see Enclosed
 */
@RunWith(Enclosed.class)
public class EmailParserTest {

	/**
	 * Demonstrates a simple test class.
	 */
	public static class SimpleTest {
		/** Parser to use in each of these tests. */
		private EmailParser parser;

		/**
		 * Runs setup operations before each test is run. It is called for every
		 * test so that each test has a clean setup.
		 *
		 * @see Before
		 */
		@Before
		public void setup() {
			parser = new EmailParser("simple@example.com");
		}

		/**
		 * Demonstrates a simple unit test.
		 *
		 * @see Assert#assertEquals(String, Object, Object)
		 */
		@Test
		public void testLocal() {
			String expect = "simple";
			String actual = parser.getLocal();
			String output = parser.debug();

			Assert.assertEquals(output, expect, actual);
		}

		@Test
		public void testDomain() {
			String expect = "example.com";
			String actual = parser.getDomain();
			String output = parser.debug();

			Assert.assertEquals(output, expect, actual);
		}

		@Test
		public void testTLD() {
			String expect = "com";
			String actual = parser.getTLD();
			String output = parser.debug();

			Assert.assertEquals(output, expect, actual);
		}

		// Note the repetition here. There must be a better way...
	}

	/**
	 * Demonstrates how to setup a parameterized test class for tests that are
	 * the same except for a set of parameters (e.g. test cases).
	 *
	 * @see Parameterized
	 * @see Parameters
	 * @see <a href=
	 *      "https://github.com/junit-team/junit/wiki/Parameterized-tests">JUnit:
	 *      Parameterized Tests</a>
	 * @see <a href=
	 *      "https://en.wikipedia.org/wiki/Email_address#Valid_email_addresses">Valid
	 *      Email Addresses</a>
	 */
	@RunWith(Parameterized.class)
	public static class ValidEmailTest {
		private String email;
		private String local;
		private String domain;
		private String tld;

		/**
		 * Provides the parameters for each test. This includes everything we
		 * need to setup the test case and expected values.
		 *
		 * @return test parameters
		 */
		@Parameters(name = "{index}: {0}")
		public static Iterable<Object[]> data() {
			return Arrays
					.asList(new Object[][] { { "local@subdomain.example.com", "local", "subdomain.example.com", "com" },
							{ "first.last@example.org", "first.last", "example.org", "org" },
							{ "first-last@example.org", "first-last", "example.org", "org" },
							{ "first+label@example.org", "first+label", "example.org", "org" },
							{ "admin@mailserver", "admin", "mailserver", null } });
		}

		/**
		 * This is called for each set of parameters. Order must match that of
		 * {@link #data()}.
		 *
		 * @param email
		 *            test case
		 * @param local
		 *            expected local output
		 * @param domain
		 *            expected domain output
		 * @param tld
		 *            explected tld output
		 */
		public ValidEmailTest(String email, String local, String domain, String tld) {
			this.email = email;
			this.local = local;
			this.domain = domain;
			this.tld = tld;
		}

		@Test
		public void testLocal() {
			EmailParser parser = new EmailParser(this.email);

			String expect = this.local;
			String actual = parser.getLocal();
			String output = parser.debug();

			Assert.assertEquals(output, expect, actual);
		}

		@Test
		public void testDomain() {
			EmailParser parser = new EmailParser(this.email);

			String expect = this.domain;
			String actual = parser.getDomain();
			String output = parser.debug();

			Assert.assertEquals(output, expect, actual);
		}

		@Test
		public void testTLD() {
			EmailParser parser = new EmailParser(this.email);

			String expect = this.tld;
			String actual = parser.getTLD();
			String output = parser.debug();

			Assert.assertEquals(output, expect, actual);
		}

		// Now we do not have to copy/paste the above for every test case!
	}

	/**
	 * Demonstrates how to handle testing for expected exceptions.
	 *
	 * @see <a href=
	 *      "https://github.com/junit-team/junit/wiki/Exception-testing">JUnit:
	 *      Exception Testing</a>
	 */
	public static class InvalidEmailTest {
		@Test(expected = IllegalArgumentException.class)
		public void testNoLocal() {
			new EmailParser("@example.com");
		}

		@Test(expected = IllegalArgumentException.class)
		public void testNoDomain() {
			new EmailParser("local@");
		}

		@Test(expected = IllegalArgumentException.class)
		public void testMissingAt() {
			new EmailParser("local+example.com");
		}

		@Test(expected = IllegalArgumentException.class)
		public void testTooManyAts() {
			new EmailParser("first@middle@last");
		}

		@Test(expected = NullPointerException.class)
		public void testNullEmail() {
			new EmailParser(null);
		}
	}

}
