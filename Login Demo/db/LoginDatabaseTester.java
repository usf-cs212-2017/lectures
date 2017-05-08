/**
 * Tests database configuration and handler.
 *
 * @see LoginServer
 */
public class LoginDatabaseTester {

	// Should really convert this to unit tests...
	public static void main(String[] args) throws Exception {
		Status status = null;
		LoginDatabaseHandler logindb = LoginDatabaseHandler.getInstance();

		status = logindb.registerUser("test01", "test01");
		System.out.println("Register test01: " + status);

		status = logindb.registerUser("test02", "test02");
		System.out.println("Register test02: " + status);

		status = logindb.registerUser("test01", "test01");
		System.out.println("Register test01: " + status);

		status = logindb.authenticateUser("test01", "test01");
		System.out.println("Auth test01/test01: " + status);

		status = logindb.authenticateUser("test01", "mypass");
		System.out.println("Auth test01/mypass: " + status);

		status = logindb.removeUser("test01", "test01");
		System.out.println("Remove test01: " + status);

		status = logindb.removeUser("test02", "test02");
		System.out.println("Remove test02: " + status);
	}
}
