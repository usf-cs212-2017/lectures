import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * An example showing how to use the DatabaseConnector and JDBC to connect to a
 * database and retrieve information safely without directly using user input to
 * avoid an XSS or SQL injection attack.
 *
 * @see DatabaseConenctor
 * @see ContactServer
 * @see ContactSimpleServlet
 * @see ContactComplexServlet
 */
@SuppressWarnings("serial")
public class ContactComplexServlet extends ContactSimpleServlet {

	/**
	 * We use this map to validate information that may originate from the user.
	 * This maps user-friendly column names to the actual column names in our
	 * SQL database.
	 */
	private final Map<String, String> tableInfo;

	/**
	 * Sets the database connector to use, and populates the table of valid
	 * column names.
	 *
	 * @param connector
	 */
	public ContactComplexServlet(DatabaseConnector connector) {
		super(connector);

		// use a linked hashmap to preserve insertion order
		tableInfo = new LinkedHashMap<String, String>();
		tableInfo.put("Name", "last");
		tableInfo.put("Email", "email");
		tableInfo.put("Twitter", "twitter");
		tableInfo.put("Courses", "courses");
	}

	/**
	 * A helper method to get the column name to sort by. If the column name is
	 * missing or invalid (i.e. a script), we will default to a safe value.
	 *
	 * @param request
	 * @return safe column name
	 */
	private String getSortName(String name) {
		return tableInfo.getOrDefault(name, "last");
	}

	/**
	 * A helper method to get the sort order (ascending or descending). If the
	 * sort order is missing or invalid (i.e. a script), we will default to a
	 * safe value.
	 *
	 * @param request
	 * @return safe sort order
	 */
	private static boolean getSortOrder(String order) {
		if (order != null) {
			return Boolean.parseBoolean(order);
		}
		else {
			return true;
		}
	}

	/**
	 * Overrides
	 * {@link ContactSimpleServlet#outputHeaders(HttpServletRequest, HttpServletResponse)}
	 * to output column names that can be clicked to change the sort order. Must
	 * be careful that the GET parameters are validated, and not used directly
	 * on the web page (XSS Attack) or in the SELECT statement (SQL Injection).
	 */
	@Override
	protected void outputHeaders(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// table format, includes link to change sort column, sort order, and
		// column name
		String cellFormat = "\t<td><a href=\"%s?column=%s&asc=%b\"><b>%s</b></a></td>%n";
		PrintWriter out = response.getWriter();

		// get parameters safely
		String name = request.getParameter("column");
		String order = request.getParameter("asc");
		boolean asc = getSortOrder(order);

		// set column to "Name" if null
		if (name == null) {
			name = "Name";
		}

		out.printf("<tr style=\"background-color: #EEEEEE;\">%n");

		for (String column : tableInfo.keySet()) {
			// if we sorted by this column, reverse the sort order option
			if (column.equalsIgnoreCase(name)) {
				out.printf(cellFormat, request.getRequestURI(), column, !asc, column);
			}
			else {
				out.printf(cellFormat, request.getRequestURI(), column, true, column);
			}
		}

		out.printf("</tr>%n");
	}

	/**
	 * Overrides
	 * {@link ContactSimpleServlet#outputContacts(HttpServletRequest, HttpServletResponse)}
	 * to get contacts in the sort order specified. Must be careful that the GET
	 * parameters are validated, and not used directly on the web page (XSS
	 * Attack) or in the SELECT statement (SQL Injection).
	 */
	@Override
	protected void outputContacts(HttpServletRequest request, HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();

		// need different format strings to handle websites and emails
		String nameFormat = "\t<td>%s</td>%n";
		String blankFormat = "\t<td>&nbsp;</td>%n";

		String mailFormat = "\t<td><a href=\"mailto:%1$s\">%1$s</a></td>%n";
		String twitterFormat = "\t<td><a href=\"https://twitter.com/%1$s\">@%1$s</a></td>%n";

		String courseFormat = "<a href=\"https://www.usfca.edu/search/catalog/%1$s\">%1$s</a>";

		// get parameters safely
		String name = request.getParameter("column");
		String sort = getSortName(name);

		String order = request.getParameter("asc");
		boolean asc = getSortOrder(order);

		// figure out ORDER BY clause to add to SELECT statement
		String orderby = "ORDER BY " + sort;
		orderby += (asc) ? " ASC;" : " DESC;";

		log.info(request.getRequestURI() + ": " + orderby);

		try (
				Connection db = connector.getConnection();
				Statement statement = db.createStatement();
				ResultSet results = statement.executeQuery(SELECT + orderby);
		) {
			while (results.next()) {
				out.printf("<tr>%n");
				out.printf(nameFormat, results.getString("Name"));
				out.printf(mailFormat, results.getString("Email"));

				String twitter = results.getString("twitter");

				if (twitter != null && !twitter.trim().isEmpty()) {
					out.printf(twitterFormat, twitter);
				}
				else {
					out.printf(blankFormat);
				}

				String[] courses = results.getString("courses").split(", ");

				if (courses.length > 0) {
					out.printf("\t<td>");
					out.printf(courseFormat, courses[0]);

					for (int i = 1; i < courses.length; i++) {
						out.printf(", ");
						out.printf(courseFormat, courses[i]);
					}

					out.printf("</td>%n");
				}
				else {
					out.printf(blankFormat);
				}

				out.printf("</tr>%n");
			}
		}
		catch (SQLException e) {
			out.printf("\t<td colspan=\"5\">%s</td>%n", e.getMessage());
		}
	}
}
