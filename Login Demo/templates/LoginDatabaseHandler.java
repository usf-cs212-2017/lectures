import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class LoginDatabaseHandler {

	protected static final Logger log = Log.getLog();

	private static final String TABLES_SQL =
			"SHOW TABLES LIKE 'login_users';";

	private static final String CREATE_SQL =
			"CREATE TABLE login_users (" +
			"userid INTEGER AUTO_INCREMENT PRIMARY KEY, " +
			"username VARCHAR(32) NOT NULL UNIQUE, " +
			"password CHAR(64) NOT NULL, " +
			"usersalt CHAR(32) NOT NULL);";

	private static final String REGISTER_SQL =
			"INSERT INTO login_users (username, password, usersalt) " +
			"VALUES (?, ?, ?);";

	private static final String USER_SQL =
			"SELECT username FROM login_users WHERE username = ?";

	private static final String SALT_SQL =
			"SELECT usersalt FROM login_users WHERE username = ?";

	private static final String AUTH_SQL =
			"SELECT username FROM login_users " +
			"WHERE username = ? AND password = ?";

	private static final String DELETE_SQL =
			"DELETE FROM login_users WHERE username = ?";

	// TODO

	private DatabaseConnector db;
	private Random random;

	private LoginDatabaseHandler() {
		Status status = Status.OK;
		random = new Random(System.currentTimeMillis());

		try {
			// TODO
			throw new FileNotFoundException();
		}
		catch (FileNotFoundException e) {
			status = Status.MISSING_CONFIG;
		}
		catch (IOException e) {
			status = Status.MISSING_VALUES;
		}

		if (status != Status.OK) {
			log.warn(status.message());
		}
	}

	public static LoginDatabaseHandler getInstance() {
		// TODO
		return null;
	}

	public static boolean isBlank(String text) {
		return (text == null) || text.trim().isEmpty();
	}

	public static String encodeHex(byte[] bytes, int length) {
		BigInteger bigint = new BigInteger(1, bytes);
		String hex = String.format("%0" + length + "X", bigint);

		assert hex.length() == length;
		return hex;
	}

	public static String getHash(String password, String salt) {
		String salted = salt + password;
		String hashed = salted;

		try {
			// TODO
		}
		catch (Exception ex) {
			log.debug("Unable to properly hash password.", ex);
		}

		return hashed;
	}

	private Status setupTables() {
		Status status = Status.ERROR;

		try (
				Connection connection = db.getConnection();
				Statement statement = connection.createStatement();
		) {
			// TODO
		}
		catch (Exception ex) {
			status = Status.CREATE_FAILED;
			log.debug(status.toString(), ex);
		}

		return status;
	}

	private Status duplicateUser(Connection connection, String user) {

		assert connection != null;
		assert user != null;

		Status status = Status.ERROR;

		// TODO

//		catch (SQLException ex) {
//			status = Status.SQL_EXCEPTION;
//			log.debug(status.toString(), ex);
//		}

		return status;
	}

	public Status duplicateUser(String user) {
		Status status = Status.ERROR;

		// TODO

//		catch (SQLException ex) {
//			status = Status.CONNECTION_FAILED;
//			log.debug(status.toString(), ex);
//		}

		return status;
	}

	private Status registerUser(Connection connection, String newuser, String newpass) {

		Status status = Status.ERROR;

		// TODO

//		catch (SQLException ex) {
//			status = Status.SQL_EXCEPTION;
//			log.debug(status.toString(), ex);
//		}

		return status;
	}

	public Status registerUser(String newuser, String newpass) {
		Status status = Status.ERROR;
		log.debug("Registering " + newuser + ".");

		// TODO

//		catch (SQLException ex) {
//			status = Status.CONNECTION_FAILED;
//			log.debug(status.toString(), ex);
//		}

		return status;
	}

	private String getSalt(Connection connection, String user) throws SQLException {
		assert connection != null;
		assert user != null;

		String salt = null;

		// TODO

		return salt;
	}

	private Status authenticateUser(Connection connection, String username, String password) throws SQLException {

		Status status = Status.ERROR;

		// TODO

//		catch (SQLException ex) {
//			status = Status.SQL_EXCEPTION;
//			log.debug(status.toString(), ex);
//		}

		return status;
	}

	public Status authenticateUser(String username, String password) {
		Status status = Status.ERROR;

		log.debug("Authenticating user " + username + ".");

		try (Connection connection = db.getConnection();) {
			status = authenticateUser(connection, username, password);
		}
		catch (SQLException ex) {
			status = Status.CONNECTION_FAILED;
			log.debug(status.toString(), ex);
		}

		return status;
	}

	private Status removeUser(Connection connection, String username, String password) {
		Status status = Status.ERROR;

		try (PreparedStatement statement = connection.prepareStatement(DELETE_SQL);) {
			statement.setString(1, username);

			int count = statement.executeUpdate();
			status = (count == 1) ? Status.OK : Status.INVALID_USER;
		}
		catch (SQLException ex) {
			status = Status.SQL_EXCEPTION;
			log.debug(status.toString(), ex);
		}

		return status;
	}

	public Status removeUser(String username, String password) {
		Status status = Status.ERROR;

		log.debug("Removing user " + username + ".");

		try (Connection connection = db.getConnection();) {
			status = authenticateUser(connection, username, password);

			if (status == Status.OK) {
				status = removeUser(connection, username, password);
			}
		}
		catch (Exception ex) {
			status = Status.CONNECTION_FAILED;
			log.debug(status.toString(), ex);
		}

		return status;
	}
}
