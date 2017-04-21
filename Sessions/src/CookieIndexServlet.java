import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
public class CookieIndexServlet extends CookieBaseServlet {

	public static final String VISIT_DATE = "Visited";
	public static final String VISIT_COUNT = "Count";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("GET " + request.getRequestURL().toString());

		if (request.getRequestURI().endsWith("favicon.ico")) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		prepareResponse("Cookies!", response);

		Map<String, Cookie> cookies = getCookieMap(request);

		Cookie visitDate = cookies.get(VISIT_DATE);
		Cookie visitCount = cookies.get(VISIT_COUNT);

		PrintWriter out = response.getWriter();
		out.printf("<p>");

		// Update visit count as necessary and output information.
		if ((visitDate == null) || (visitCount == null)) {
			visitCount = new Cookie(VISIT_COUNT, "0");
			visitDate = new Cookie(VISIT_DATE, "");

			out.printf("You have never been to this webpage before! ");
			out.printf("Thank you for visiting.");
		}
		else {
			int count = Integer.parseInt(visitCount.getValue());
			visitCount.setValue(Integer.toString(count + 1));

			String decoded = URLDecoder.decode(visitDate.getValue(), StandardCharsets.UTF_8.name());
			log.info("Encoded: " + visitDate.getValue() + ", Decoded: " + decoded);

			out.printf("You have visited this website %s times. ", visitCount.getValue());
			out.printf("Your last visit was on %s.", decoded);
		}

		out.printf("</p>%n");

		// Checks if the browser indicates visits should not be tracked.
		// This is not a standard header!
		// Try this in Safari private browsing mode.
		if (request.getIntHeader("DNT") != 1) {
			String encoded = URLEncoder.encode(getLongDate(), StandardCharsets.UTF_8.name());
			visitDate.setValue(encoded);
			response.addCookie(visitDate);
			response.addCookie(visitCount);
		}
		else {
			clearCookies(request, response);
			out.printf("<p>Your visits will not be tracked.</p>");
		}

		finishResponse(request, response);
	}
}
