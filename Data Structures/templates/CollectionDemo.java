import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;

public class CollectionDemo {

	public static void parseLine(String line) {

		ArrayList<String> wordList;
		HashSet<String> wordHashSet;
		TreeSet<String> wordTreeSet;

		// TODO

//		String format = "%-10s : %02d items : %s\n";
//		System.out.printf("\n");
//		System.out.printf(format, "ArrayList", wordList.size(), wordList);
//		System.out.printf(format, "HashSet", wordHashSet.size(), wordHashSet);
//		System.out.printf(format, "TreeSet", wordTreeSet.size(), wordTreeSet);
//		System.out.println();
	}

	public static void main(String[] args) {

		// https://en.wikipedia.org/wiki/Garden_path_sentence
		String test1 = "the old man the boat";

		// https://en.wikipedia.org/wiki/List_of_linguistic_example_sentences
		String test2 = "rose rose to put rose roes on her rows of roses";

		// https://en.wikipedia.org/wiki/Time_flies_like_an_arrow;_fruit_flies_like_a_banana
		String test3 = "time flies like an arrow fruit flies like an apple";

		parseLine(test1);
		parseLine(test2);
		parseLine(test3);
	}

}
