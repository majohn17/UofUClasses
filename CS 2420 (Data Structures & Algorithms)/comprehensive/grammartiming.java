package comprehensive;

import java.io.IOException;
import java.util.Scanner;

public class GrammarTiming {

//	public static void generate(int phrases, int nonTerminals, int rules, int length) {
//		for (int i = 0; i < phrases; i++) {
//			try {
//				RandomPhraseGenerator.generatePhrase();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

	public static void main(String[] args) throws IOException {
		long start;
		long mid;
		long stop;
		long timesToLoop = 1000;
		int nSteps = 100;
		int nMax = 2000	;
		String[] results = new String[nMax / nSteps];

		for (int n = nSteps, p = 0; n <= nMax; n += nSteps, p++) {

			// pre-timing set up goes here
			RandomPhraseGenerator.setGrammar(RandomPhraseGenerator.createGrammar(createGscan(n, 500, 2)));

			// run clock for better timing
			start = System.nanoTime();
			while (System.nanoTime() - start < 1000000000) {
				// nothing
			}

			start = System.nanoTime();
			for (long i = 0; i < timesToLoop; i++) {
				RandomPhraseGenerator.generatePhrase();
			}

			mid = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) {

			}

			stop = System.nanoTime();

			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;

			results[p] = (n + "\t" + averageTime);
			for (int i = 0; i < 2; i++) {
				System.out.println("" + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p
						+ p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p
						+ p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p
						+ p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p
						+ p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p
						+ p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p
						+ p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p);
			}
		}
		System.out.println("NonTerminals Test");
		System.out.println("N\ttime");
		for (int i = 0; i < results.length; i++)
			System.out.println(results[i]);

	}

	private static Scanner createGscan(int nonTerminals, int productionRules, int length) {
		StringBuilder sb = new StringBuilder();
		sb.append(createNonTerminal("<start>", productionRules, length));
		for (int i = 0; i < nonTerminals; i++) {
			sb.append(createNonTerminal("<" + i + ">", productionRules, length));
		}
		return new Scanner(sb.toString());
	}

	private static String createNonTerminal(String name, int rules, int length) {
		String nonTerminal;
		nonTerminal = "\n{\n" + name + "\n";
		for (int i = 0; i < rules / 2 + 1; i++) {
			nonTerminal += name.substring(1, name.length() - 1) + "\n";
		}
		for (int i = 0; i < rules / 2 + 1; i++) {

			for (int j = 0; j < length; j++) {
				nonTerminal += "<" + j + "> ";
			}
			nonTerminal += "\n";
		}
		nonTerminal += "}\n";
		return nonTerminal;
	}
}
