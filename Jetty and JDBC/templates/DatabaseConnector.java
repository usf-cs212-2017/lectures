import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Set;

public class DatabaseConnector {

	public final String uri;
	private final Properties login;

	public DatabaseConnector() throws FileNotFoundException, IOException {
		this("database.properties");
	}

	public DatabaseConnector(String configPath) throws FileNotFoundException, IOException {

		// Try to load the configuration from file
		Properties config = loadConfig(configPath);

		// Create database URI in proper format
		uri = null;
		// TODO

		// Create database login properties
		login = new Properties();
		// TODO
	}

	private Properties loadConfig(String configPath) throws FileNotFoundException, IOException {
		Set<String> required = new HashSet<>();
		required.add("username");
		required.add("password");
		required.add("database");
		required.add("hostname");

		Properties config = new Properties();
		// TODO

		if (!config.keySet().containsAll(required)) {
			String error = "Must provide the following in properties file: ";
			throw new InvalidPropertiesFormatException(error + required);
		}

		return config;
	}

	public Connection getConnection() throws SQLException {
		// TODO
		return null;
	}

	public Set<String> getTables(Connection db) throws SQLException {
		Set<String> tables = new HashSet<>();

		// TODO

		return tables;
	}

	public boolean testConnection() {
		boolean okay = false;

		try (Connection db = getConnection();) {
			System.out.println("Executing SHOW TABLES...");
			Set<String> tables = getTables(db);

			if (tables != null) {
				System.out.print("Found " + tables.size() + " tables: ");
				System.out.println(tables);

				okay = true;
			}
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return okay;
	}

	public static void main(String[] args) {
		try {
			DatabaseConnector test = new DatabaseConnector("database.properties");
			System.out.println("Connecting to " + test.uri);

			if (test.testConnection()) {
				System.out.println("Connection to database established.");
			}
			else {
				System.err.println("Unable to connect properly to database.");
			}
		}
		catch (Exception e) {
			System.err.println("Unable to connect properly to database.");
			System.err.println(e.getMessage());
		}
	}
}
