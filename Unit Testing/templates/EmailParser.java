public class EmailParser {
	private String email;
	private String local;
	private String domain;
	private String tld;

	public EmailParser(String email) {
		// TODO
	}

	public String getLocal() {
		return local;
	}

	public String getDomain() {
		return domain;
	}

	public String getTLD() {
		return tld;
	}

	public String getEmail() {
		return email;
	}

	public String debug() {
		return String.format("Email: %s, Local: %s, Domain: %s, TLD: %s", getEmail(), getLocal(), getDomain(),
				getTLD());
	}

	@Override
	public String toString() {
		return getEmail();
	}
}
