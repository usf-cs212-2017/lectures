import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/**
 * An {@link HttpServlet} with several additional utility methods. Used by
 * several servlet examples.
 */
@SuppressWarnings("serial")
public class CookieBaseServlet extends HttpServlet {

	/** Default port to be used. */
	public static final int PORT = 8080;

	/** Log used by Jetty (not log4j2). */
	public static final Logger log = Log.getRootLogger();

	/**
	 * Returns the current date and time in a long format.
	 *
	 * @return current date and time
	 * @see #getShortDate()
	 */
	public static String getLongDate() {
		String format = "hh:mm a 'on' EEEE, MMMM dd yyyy";
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(new Date());
	}

	/**
	 * Returns the current date and time in a short format.
	 *
	 * @return current date and time
	 * @see #getLongDate()
	 */
	public static String getShortDate() {
		String format = "yyyy-MM-dd hh:mm a";
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(Calendar.getInstance().getTime());
	}

	/**
	 * Prepares the HTTP response by setting the content type and adding header
	 * HTML code.
	 *
	 * @param title
	 *            - web page title
	 * @param response
	 *            - HTTP response
	 * @throws IOException
	 * @see #finishResponse(HttpServletRequest, HttpServletResponse)
	 */
	public static void prepareResponse(String title, HttpServletResponse response) throws IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.printf("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"");
		out.printf("\"http://www.w3.org/TR/html4/strict.dtd\">%n%n");
		out.printf("<html>%n%n");
		out.printf("<head>%n");
		out.printf("\t<title>%s</title>%n", title);
		out.printf("\t<meta http-equiv=\"Content-Type\" ");
		out.printf("content=\"text/html;charset=utf-8\">%n");
		out.printf("</head>%n%n");
		out.printf("<body>%n%n");
	}

	/**
	 * Finishes the HTTP response by adding footer HTML code and setting the
	 * response code.
	 *
	 * @param request
	 *            - HTTP request
	 * @param response
	 *            - HTTP response
	 * @throws IOException
	 * @see #prepareResponse(String, HttpServletResponse)
	 */
	public static void finishResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();

		out.printf("%n");
		out.printf("<p style=\"font-size: 10pt; font-style: italic; text-align: center;");
		out.printf("border-top: 1px solid #eeeeee; margin-bottom: 1ex;\">");

		out.printf("Page <a href=\"%s\">%s</a> generated on %s by thread %s. ", request.getRequestURL(),
				request.getRequestURL(), getShortDate(), Thread.currentThread().getName());

		out.printf("</p>%n%n");
		out.printf("</body>%n");
		out.printf("</html>%n");

		out.flush();

		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
	}

	/**
	 * Gets the cookies form the HTTP request, and maps the cookie name to the
	 * cookie object.
	 *
	 * @param request
	 *            - HTTP request from web server
	 * @return map from cookie key to cookie value
	 */
	public Map<String, Cookie> getCookieMap(HttpServletRequest request) {
		HashMap<String, Cookie> map = new HashMap<>();
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				map.put(cookie.getName(), cookie);
			}
		}

		return map;
	}

	/**
	 * Clears all of the cookies included in the HTTP request.
	 *
	 * @param request
	 *            - HTTP request
	 * @param response
	 *            - HTTP response
	 */
	public void clearCookies(HttpServletRequest request, HttpServletResponse response) {

		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookie.setValue(null);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
	}
}
