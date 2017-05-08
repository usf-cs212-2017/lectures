
public enum Status {

	OK("No errors occured."),
	ERROR("Unknown error occurred."),
	MISSING_CONFIG("Unable to find configuration file."),
	MISSING_VALUES("Missing values in configuration file."),
	CONNECTION_FAILED("Failed to establish a database connection."),
	CREATE_FAILED("Failed to create necessary tables."),
	INVALID_LOGIN("Invalid username and/or password."),
	INVALID_USER("User does not exist."),
	DUPLICATE_USER("User with that username already exists."),
	SQL_EXCEPTION("Unable to execute SQL statement.");

	private final String message;

	private Status(String message) {
		this.message = message;
	}

	public String message() {
		return message;
	}

	@Override
	public String toString() {
		return this.message;
	}
}
