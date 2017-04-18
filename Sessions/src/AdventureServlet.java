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
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/**
 * Demonstrates how to use session tracking and enum types to create a simple
 * adventure game.
 *
 * @see AdventureServer
 * @see AdventureServlet
 * @see AdventureRoom
 * @see Direction
 */
@SuppressWarnings("serial")
public class AdventureServlet extends HttpServlet {

	private static Logger log = Log.getRootLogger();

	// consider using StringTemplate instead
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

		// get rid of favicon requests
		if (req.getContextPath().endsWith("favicon.ico")) {
			log.warn("Discarding " + req.getContextPath() + " request.");
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		PrintWriter writer = res.getWriter();
		HttpSession session = req.getSession(true);

		// tracks current game state
		AdventureRoom room = null;
		Direction direction = null;

		// enables/disables direction buttons
		String west = null;
		String east = null;
		String north = null;
		String south = null;

		// try to get current game state
		try {
			room = (AdventureRoom) session.getAttribute("room");
			direction = Direction.valueOf(req.getParameter("direction"));
		}
		catch (Exception ignored) {
			// ignored
		}

		// make sure values are valid
		if (room == null || direction == null) {
			log.info("Starting new game for session " + session.getId());
			room = AdventureRoom.START_ROOM;
			direction = Direction.EAST;
		}
		else {
			int old = room.ordinal();
			room = room.moveRoom(direction);
			log.info("Session " + session.getId() + " moved from " + old + " to " + room.ordinal());
		}

		// save the updated room value in the session
		session.setAttribute("room", room);

		if (room.done()) {
			// game won or lost
			log.info("Game finished for session " + session.getId());
			session.setAttribute("room", AdventureRoom.START_ROOM);
			session.invalidate();
			west = "disabled";
			east = "disabled";
			north = "disabled";
			south = "disabled";
		}
		else {
			// calculate which buttons to disable
			west = room.canMove(Direction.WEST) ? "" : "disabled";
			east = room.canMove(Direction.EAST) ? "" : "disabled";
			north = room.canMove(Direction.NORTH) ? "" : "disabled";
			south = room.canMove(Direction.SOUTH) ? "" : "disabled";
		}

		// setup template
		// inefficient, look into StringTemplate instead!
		String template = getTemplate();
		String html = String.format(template, room.toString(), // 1: message
				res.encodeURL("/"), // 2: action
				// 3, 4, 5, 6: move buttons
				north, west, east, south,
				// 7, 8, 9: debug info
				session.getId(), String.valueOf(room.ordinal()), direction.name());

		// generate html from template
		writer.write(html);

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
