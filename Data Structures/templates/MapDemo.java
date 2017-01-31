import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapDemo {

	public static final String buffalo = "bison that other bison bully also bully bison";

	private static void demoMap(Map<String, Integer> map, String text) {
		// TODO

		// System.out.printf("Word: %s \tCount: %d%n", word, map.get(word));
		// System.out.println();
	}

	public static void main(String[] args) {
		HashMap<String, Integer> wordHashMap = new HashMap<>();
		TreeMap<String, Integer> wordTreeMap = new TreeMap<>();

		System.out.println("HashMap:");
		demoMap(wordHashMap, buffalo);

		System.out.println("TreeMap:");
		demoMap(wordTreeMap, buffalo);
	}

}
