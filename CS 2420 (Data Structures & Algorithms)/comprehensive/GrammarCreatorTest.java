package comprehensive;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class GrammarCreatorTest {

	@Test
	void test() {
		HashMap<String, ArrayList<String>> items = RandomPhraseGenerator.createGrammar("src/comprehensive/super_simple.g");
		for(String key :items.keySet())
		{
			System.out.println(key);
			System.out.println("__________");
			ArrayList<String> values = items.get(key);
			for(String s : values)
			{
				System.out.println(s);
			}
			System.out.println("");
		}
		assertTrue(items.get("<start>")!= null);
	}

}
