import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		// TODO

		finishResponse(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("POST " + request.getRequestURL().toString());

		prepareResponse("Configure", response);

		// TODO

		finishResponse(request, response);
	}
}
