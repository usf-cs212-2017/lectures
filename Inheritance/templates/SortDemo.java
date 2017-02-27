import java.util.ArrayList;
import java.util.Collection;

public class SortDemo {

	private static class Widget {
		public int id;
		public String name;

		public Widget(int id, String name) {
			this.id = id;
			this.name = name;
		}

		// TODO
	}

	private static class WidgetComparator {

		// TODO

	}

	public static void printCollection(Collection<Widget> widgets) {
		for (Widget widget : widgets) {
			System.out.printf("id: %2d name: %s\n", widget.id, widget.name);
		}
	}

	public static void main(String[] args) {
		ArrayList<Widget> list = new ArrayList<Widget>();
		list.add(new Widget(10, "ant"));
		list.add(new Widget(7, "hippopotamus"));
		list.add(new Widget(14, "dragonfly"));
		list.add(new Widget(3, "camel"));

		// TODO
	}
}
