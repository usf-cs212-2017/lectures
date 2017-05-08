import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles login requests.
 *
 * @see LoginServer
 */
@SuppressWarnings("serial")
public class LoginUserServlet extends LoginBaseServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		prepareResponse("Login", response);

		PrintWriter out = response.getWriter();

		// TODO

		printForm(out);
		finishResponse(response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO

		try {
			// TODO
		}
		catch (Exception ex) {
			log.warn("Unable to process login form.", ex);
		}
	}

	private void printForm(PrintWriter out) {
		assert out != null;

		out.println();
		out.println("<form action=\"/login\" method=\"post\" class=\"form-inline\">");

		out.println("\t<div class=\"form-group\">");
		out.println("\t\t<label for=\"user\">Username:</label>");
		out.println("\t\t<input type=\"text\" name=\"user\" class=\"form-control\" id=\"user\" placeholder=\"Username\">");
		out.println("\t</div>\n");

		out.println("\t<div class=\"form-group\">");
		out.println("\t\t<label for=\"pass\">Password:</label>");
		out.println("\t\t<input type=\"password\" name=\"pass\" class=\"form-control\" id=\"pass\" placeholder=\"Password\">");
		out.println("\t</div>\n");

		out.println("\t<button type=\"submit\" class=\"btn btn-primary\">Login</button>\n");
		out.println("</form>");
		out.println("<br/>\n");

		out.println("<p>(<a href=\"/register\">new user? register here.</a>)</p>");
	}
}
