import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Demonstrates how to create, use, and clear cookies. Vulnerable to attack
 * since cookie values are not sanitized prior to use!
 *
 * @see CookieBaseServlet
 * @see CookieIndexServlet
 * @see CookieConfigServlet
 */
@SuppressWarnings("serial")
public class CookieConfigServlet extends CookieBaseServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("GET " + request.getRequestURL().toString());

		prepareResponse("Configure", response);

		PrintWriter out = response.getWriter();
		out.printf("<p>To clear saved cookies, please press \"Clear\".</p>%n");
		out.printf("%n");

		out.printf("<form method=\"post\" action=\"%s\">%n", request.getRequestURI());
		out.printf("\t<input type=\"submit\" value=\"Clear\">%n");
		out.printf("</form>%n");

		finishResponse(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("POST " + request.getRequestURL().toString());

		clearCookies(request, response);

		prepareResponse("Configure", response);

		PrintWriter out = response.getWriter();
		out.printf("<p>Your cookies for this site have been cleared.</p>%n%n");

		finishResponse(request, response);
	}
}
