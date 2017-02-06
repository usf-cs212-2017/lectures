import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Enclosed.class)
public class EmailParserTest {

	public static class SimpleTest {
		private EmailParser parser;

		@Before
		public void setup() {

			// TODO

		}

		@Test
		public void testLocal() {

			// TODO

		}

		// TODO

	}

	@RunWith(Parameterized.class)
	public static class ValidEmailTest {

//		@Parameters(name = "{index}: {0}")
//		public static Iterable<Object[]> data() {
//			return Arrays
//					.asList(new Object[][] {
//							{ "local@subdomain.example.com", "local", "subdomain.example.com", "com" },
//							{ "first.last@example.org", "first.last", "example.org", "org" },
//							{ "first-last@example.org", "first-last", "example.org", "org" },
//							{ "first+label@example.org", "first+label", "example.org", "org" },
//							{ "admin@mailserver", "admin", "mailserver", null } });
//		}

		// TODO

	}

	public static class InvalidEmailTest {

		// TODO

	}

}
