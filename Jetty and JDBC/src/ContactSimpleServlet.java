import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

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
public class ContactSimpleServlet extends HttpServlet {

	protected static final Logger log = Log.getLog();

	/** SQL SELECT statement without the ORDER BY clause. */
	protected static final String SELECT = "SELECT "
			+ "CONCAT(first, ' ', last) AS 'name', "
			+ "CONCAT(usfid, '@usfca.edu') AS 'email', "
			+ "IFNULL(twitterid, '') AS 'twitter', "
			+ "GROUP_CONCAT(course ORDER BY course SEPARATOR ', ') AS 'courses' "
			+ "FROM contact_names "
			+ "NATURAL LEFT OUTER JOIN contact_twitter "
			+ "NATURAL LEFT OUTER JOIN contact_courses "
			+ "GROUP BY contact_names.usfid ";

	/** Pre-existing database connector. */
	protected final DatabaseConnector connector;

	/** Initializes database connector to use for all servlet requests. */
	public ContactSimpleServlet(DatabaseConnector connector) {
		this.connector = connector;
	}

	/**
	 * Responds to HTTP GET requests with a simple web page containing a static
	 * table of contacts.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		prepareResponse(response);
		outputHeaders(request, response);
		outputContacts(request, response);
		finishResponse(response);
	}

	/**
	 * Prepares the servlet response by writing header HTML to the web page.
	 *
	 * @param response
	 * @throws IOException
	 */
	protected static void prepareResponse(HttpServletResponse response) throws IOException {

		// uses bootstrap: http://getbootstrap.com/
		PrintWriter out = response.getWriter();
		out.printf("<!DOCTYPE html>%n");
		out.printf("<html lang=\"en\">%n");
		out.printf("<head>%n");
		out.printf("\t<meta charset=\"utf-8\">%n");
		out.printf("\t<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">%n");
		out.printf("\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">%n");
		out.printf("\t<title>Tenure-Track CS Professors</title>%n");
		out.printf("\t<link rel=\"stylesheet\" ");
		out.printf("href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" ");
		out.printf("integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" ");
		out.printf("crossorigin=\"anonymous\">%n");
		out.printf("</head>%n%n");
		out.printf("<body class=\"container\">%n");
		out.printf("<h2>CS Professors</h2>%n");
		out.printf("<table class=\"table table-hover\">%n");
	}

	/**
	 * Finishes the servlet response by writing footer HTML to the web page and
	 * setting the status code to 200 OK.
	 *
	 * @param response
	 * @throws IOException
	 */
	protected static void finishResponse(HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		out.printf("</table>%n");

		// uses bootstrap: http://getbootstrap.com/
		out.printf("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js\"></script>%n");
		out.printf("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\" ");
		out.printf(" integrity=\"sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa\" ");
		out.printf("crossorigin=\"anonymous\"></script>%n");

		out.printf("%n</body>%n</html>%n");
		out.flush();

		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
	}

	/**
	 * Outputs the table headers, containing the column names.
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	protected void outputHeaders(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String cellFormat = "\t<td><b>%s</b></td>%n";
		PrintWriter out = response.getWriter();

		out.printf("<tr style=\"background-color: #EEEEEE;\">%n");
		out.printf(cellFormat, "Name");
		out.printf(cellFormat, "Email");
		out.printf(cellFormat, "Twitter");
		out.printf(cellFormat, "Courses");
		out.printf("</tr>%n");
	}

	/**
	 * Outputs the contact information, without any links.
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	protected void outputContacts(HttpServletRequest request, HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		String cellFormat = "\t<td>%s</td>%n";

		try (
				Connection db = connector.getConnection();
				Statement statement = db.createStatement();
				ResultSet results = statement.executeQuery(SELECT + ";");
		) {
			// The text used in results.getString(String) must match the
			// column names in the SELECT statement.
			while (results.next()) {
				out.printf("<tr>%n");
				out.printf(cellFormat, results.getString("name"));
				out.printf(cellFormat, results.getString("email"));
				out.printf(cellFormat, results.getString("twitter"));
				out.printf(cellFormat, results.getString("courses"));
				out.printf("</tr>%n");
			}
		}
		catch (SQLException e) {
			out.printf("\t<td colspan=\"5\">%s</td>%n", e.getMessage());
		}
	}
}
