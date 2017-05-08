import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
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
		String error = request.getParameter("error");
		int code = 0;

		if (error != null) {
			try {
				code = Integer.parseInt(error);
			}
			catch (Exception ex) {
				code = -1;
			}

			String errorMessage = getStatusMessage(code);
			out.println("<p class=\"alert alert-danger\">" + errorMessage + "</p>");
		}

		if (request.getParameter("newuser") != null) {
			out.println("<p>Registration was successful!");
			out.println("Login with your new username and password below.</p>");
		}

		if (request.getParameter("logout") != null) {
			clearCookies(request, response);
			out.println("<p class=\"alert alert-success\">Successfully logged out.</p>");
		}

		printForm(out);
		finishResponse(response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");

		Status status = dbhandler.authenticateUser(user, pass);

		try {
			if (status == Status.OK) {
				// should eventually change this to something more secure
				response.addCookie(new Cookie("login", "true"));
				response.addCookie(new Cookie("name", user));
				response.sendRedirect(response.encodeRedirectURL("/welcome"));
			}
			else {
				response.addCookie(new Cookie("login", "false"));
				response.addCookie(new Cookie("name", ""));
				response.sendRedirect(response.encodeRedirectURL("/login?error=" + status.ordinal()));
			}
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
