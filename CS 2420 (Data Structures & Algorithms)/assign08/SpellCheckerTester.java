package assign08;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.List;

class SpellCheckerTester {

	private SpellChecker sc = new SpellChecker(new File("src/assign08/dictionary.txt"));
	private List<String> msWords;

	@Test
	void testFile1() {
		File doc = new File("src/assign08/test1.txt");
		msWords = sc.spellCheck(doc);
		spellCheckInfo(msWords, "Test1");
		assertTrue(msWords.size() == 0);
	}

	@Test
	void testFile2() {
		File doc = new File("src/assign08/test2.txt");
		msWords = sc.spellCheck(doc);
		spellCheckInfo(msWords, "Test2");
		assertTrue(msWords.size() == 2);
	}

	@Test
	void testFile3() {
		File doc = new File("src/assign08/test3.txt");
		msWords = sc.spellCheck(doc);
		spellCheckInfo(msWords, "Test3");
		assertTrue(msWords.size() == 3);
	}

	/**
	 * Runs the given spell checker (with dictionary already added) on the specified
	 * file.
	 * 
	 * @param sc               - the given spell checker
	 * @param documentFilename - name of the file to be spell checked
	 */
	private static void spellCheckInfo(List<String> misspelledWords, String title) {
		if (misspelledWords.size() == 0)
			System.out.println(title + ": There are no misspelled words.");
		else {
			System.out.print(title + ": ");
			for (String w : misspelledWords)
				System.out.print(w + " ");
			System.out.print("\n");
		}
	}
}
