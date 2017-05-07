import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ContactComplexServlet extends ContactSimpleServlet {

	private final Map<String, String> tableInfo;

	public ContactComplexServlet(DatabaseConnector connector) {
		super(connector);

		tableInfo = new LinkedHashMap<String, String>();
		tableInfo.put("Name", "last");
		tableInfo.put("Email", "email");
		tableInfo.put("Twitter", "twitter");
		tableInfo.put("Courses", "courses");
	}

	private String getSortName(String name) {
		return tableInfo.getOrDefault(name, "last");
	}

	private static boolean getSortOrder(String order) {
		if (order != null) {
			return Boolean.parseBoolean(order);
		}
		else {
			return true;
		}
	}

	@Override
	protected void outputHeaders(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String cellFormat = "\t<td><a href=\"%s?column=%s&asc=%b\"><b>%s</b></a></td>%n";
		PrintWriter out = response.getWriter();

		// TODO

		out.printf("<tr style=\"background-color: #EEEEEE;\">%n");

		// TODO

		out.printf("</tr>%n");
	}

	@Override
	protected void outputContacts(HttpServletRequest request, HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();

		String nameFormat = "\t<td>%s</td>%n";
		String blankFormat = "\t<td>&nbsp;</td>%n";

		String mailFormat = "\t<td><a href=\"mailto:%1$s\">%1$s</a></td>%n";
		String twitterFormat = "\t<td><a href=\"https://twitter.com/%1$s\">@%1$s</a></td>%n";

		String courseFormat = "<a href=\"https://www.usfca.edu/search/catalog/%1$s\">%1$s</a>";

		String name = request.getParameter("column");
		String sort = getSortName(name);

		String order = request.getParameter("asc");
		boolean asc = getSortOrder(order);

		// TODO
		// log.info(request.getRequestURI() + ": " + orderby);

		// TODO
		// out.printf("\t<td colspan=\"5\">%s</td>%n", e.getMessage());
	}
}
