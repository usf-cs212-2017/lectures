import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

@SuppressWarnings("serial")
public class AdventureServlet extends HttpServlet {

	private static Logger log = Log.getRootLogger();

	private static String getTemplate() {
		Path template = Paths.get(".", "templates", "main.html");
		byte[] bytes;

		try {
			bytes = Files.readAllBytes(template);
		}
		catch (IOException ex) {
			bytes = new byte[0];
		}

		return new String(bytes, Charset.forName("UTF8"));
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		if (req.getContextPath().endsWith("favicon.ico")) {
			log.warn("Discarding " + req.getContextPath() + " request.");
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		AdventureRoom room = null;
		Direction direction = null;

		String west = null;
		String east = null;
		String north = null;
		String south = null;

		PrintWriter writer = res.getWriter();

		// TODO

		if (room == null || direction == null) {
//			log.info("Starting new game for session " + session.getId());

			// TODO
		}
		else {
			// TODO

//			log.info("Session " + session.getId() + " moved from " + old + " to " + room.ordinal());
		}

		// TODO

		String template = getTemplate();
//		String html = String.format(template, room.toString(),
//				res.encodeURL("/"),
//				north, west, east, south,
//				session.getId(), String.valueOf(room.ordinal()), direction.name());

//		writer.write(html);

		// finish up response
		res.setContentType("text/html");
		res.setStatus(HttpServletResponse.SC_OK);
		res.flushBuffer();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
