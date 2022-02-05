package comprehensive;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * This class can be run to generate random phrases from a correctly formatter
 * grammar file.
 * 
 * @author Isaac Gibson and Matthew Johnsen
 * @version April 15, 2019
 */
public class RandomPhraseGenerator {
	private static HashMap<String, ArrayList<String>> grammar;

	public static void main(String[] args) {
		grammar = createGrammar(args[0]);
		for (int i = 0; i < Integer.parseInt(args[1]); i++) {
			try {
				generatePhrase();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Generates a phrase from a grammar file
	 */
	public static void generatePhrase() throws IOException {
		BufferedWriter write = new BufferedWriter(new OutputStreamWriter(System.out));
		String terminal = randomTerminal("<start>");
		if (terminal.contains("<")) {
			write.write(nonTerminal(terminal));
			write.newLine();
			write.flush();
		} else {
			write.write(terminal);
			write.newLine();
			write.flush();
		}
	}

	/**
	 * Deals with a phrase being generated that contains non-terminals within a
	 * terminal.
	 */
	private static String nonTerminal(String terminal) {
		char[] c = terminal.toCharArray();
		StringBuilder phrase = new StringBuilder();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '<') {
				StringBuilder key = new StringBuilder();
				while (c[i] != '>') {
					key.append(c[i]);
					i++;
				}
				key.append(c[i]);
				String temp = randomTerminal(key.toString());
				if (temp.contains("<")) {
					phrase.append(nonTerminal(temp));
				} else {
					phrase.append(temp);
				}
			} else {
				phrase.append(c[i]);
			}
		}
		return phrase.toString();
	}

	/**
	 * Sets the grammar object, only used for testing.
	 */
	public static void setGrammar(HashMap<String, ArrayList<String>> change) {
		grammar = change;
	}

	/**
	 * Selects a random terminal from the specified non-terminal
	 */
	private static String randomTerminal(String key) {
		Random rand = new Random();
		return grammar.get(key).get(rand.nextInt(grammar.get(key).size()));
	}

	/**
	 * Generates a HashMap containing non-terminals for keys and terminals in
	 * ArrayLists for values from a file
	 */
	public static HashMap<String, ArrayList<String>> createGrammar(String filename) {
		Scanner scn = null;
		try {
			scn = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		HashMap<String, ArrayList<String>> items = new HashMap<>();
		while (scn.hasNext()) {
			String temp = scn.nextLine();
			if (temp.equals("{")) {
				String key = scn.nextLine();
				ArrayList<String> values = new ArrayList<>();
				String value = scn.nextLine();
				while (!value.equals("}")) {
					values.add(value);
					value = scn.nextLine();
				}
				items.put(key, values);
			}
		}
		return items;
	}

	/**
	 * Generates a HashMap containing non-terminals for keys and terminals in
	 * ArrayLists for values from a Scanner
	 */
	public static HashMap<String, ArrayList<String>> createGrammar(Scanner scn) {

		HashMap<String, ArrayList<String>> items = new HashMap<>();
		while (scn.hasNext()) {
			String temp = scn.nextLine();
			if (temp.equals("{")) {
				String key = scn.nextLine();
				ArrayList<String> values = new ArrayList<>();
				String value = scn.nextLine();
				while (!value.equals("}")) {
					values.add(value);
					value = scn.nextLine();
				}
				items.put(key, values);
			}
		}
		return items;
	}
}