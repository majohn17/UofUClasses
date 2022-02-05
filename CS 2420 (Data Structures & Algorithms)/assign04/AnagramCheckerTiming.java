package assign04;

import java.util.Arrays;
import java.util.Random;

import assign04.AnagramChecker.CompareCharacters;

import static assign04.AnagramChecker.*;

public class AnagramCheckerTiming {
	private static final String ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	public static void main(String[] args) {
		long start;
		long mid;
		long stop;
		long timesToLoop = 1000;

		System.out.println("AreAnagrams Test");
		for (int n = 1000; n <= 10000; n += 1000) {
			long testTimeStart, testTimeEnd;
			testTimeStart = System.currentTimeMillis();

			start = System.nanoTime();
			while (System.nanoTime() - start < 1000000000) {
			}

			// Testing
			start = System.nanoTime();
			for (long i = 0; i < timesToLoop; i++) {
				String s1 = randomString(n);
				String s2 = randomString(n);
				areAnagrams(s1, s2);
			}

			mid = System.nanoTime();

			// Extra Stuff
			for (long i = 0; i < timesToLoop; i++) {
				String s1 = randomString(n);
				String s2 = randomString(n);
			}

			stop = System.nanoTime();
			testTimeEnd = System.currentTimeMillis();

			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
			double printTime = ((testTimeEnd - testTimeStart) / 1000.0);
			System.out.println(n + "\t" + averageTime + "\t" + "Test time was about " + printTime + " seconds.");
		}

		System.out.println("GetLargestAnagramGroup Test");
		for (int n = 1000; n <= 10000; n += 1000) {
			long testTimeStart, testTimeEnd;
			testTimeStart = System.currentTimeMillis();

			start = System.nanoTime();
			while (System.nanoTime() - start < 1000000000) {
			}

			// Testing
			start = System.nanoTime();
			for (long i = 0; i < timesToLoop; i++) {
				String[] temp = generateRandomStringArray(n);
				getLargestAnagramGroup(temp);
			}

			mid = System.nanoTime();

			// Extra Stuff
			for (long i = 0; i < timesToLoop; i++) {
				String[] temp = generateRandomStringArray(n);
			}

			stop = System.nanoTime();
			testTimeEnd = System.currentTimeMillis();

			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
			double printTime = ((testTimeEnd - testTimeStart) / 1000.0);
			System.out.println(n + "\t" + averageTime + "\t" + "Test time was about " + printTime + " seconds.");
		}

		System.out.println("Java's Sort Test");
		for (int n = 1000; n <= 10000; n += 1000) {
			long testTimeStart, testTimeEnd;
			testTimeStart = System.currentTimeMillis();

			start = System.nanoTime();
			while (System.nanoTime() - start < 1000000000) {
			}

			// Testing
			start = System.nanoTime();
			for (long i = 0; i < timesToLoop; i++) {
				String[] temp = generateRandomStringArray(n);
				javaAnagramGroup(temp);
			}

			mid = System.nanoTime();

			// Extra Stuff
			for (long i = 0; i < timesToLoop; i++) {
				String[] temp = generateRandomStringArray(n);
			}

			stop = System.nanoTime();
			testTimeEnd = System.currentTimeMillis();

			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
			double printTime = ((testTimeEnd - testTimeStart) / 1000.0);
			System.out.println(n + "\t" + averageTime + "\t" + "Test time was about " + printTime + " seconds.");
		}
	}

	/**
	 * Generates and returns a random string of size n.
	 */
	private static String randomString(int n) {
		Random rand = new Random();
		String temp = "";
		for (int i = 0; i < n; i++) {
			temp = temp + ALPHABETS.charAt(rand.nextInt(ALPHABETS.length()));
		}
		return temp;
	}

	/**
	 * Generates a random string array of size n.
	 */
	private static String[] generateRandomStringArray(int n) {
		String[] temp = new String[n];
		Random rand = new Random();
		for (int i = 0; i < n; i++) {
			temp[i] = randomString(rand.nextInt(5) + 1);
		}
		return temp;
	}

	/**
	 * This is the getLargestAnagramGroup using Java's rather than our sort.
	 */
	public static String[] javaAnagramGroup(String[] input) {
		if (input.length == 0) {
			return input;
		}
		AnagramChecker test = new AnagramChecker();
		Arrays.sort(input, test.new CompareCharacters());
		int bestStart = 0, bestEnd = 0, tempStart = 0, tempEnd;
		for (int i = 0; i < input.length; i++) {
			if ((input.length - 1 == i) || !(areAnagrams(input[i], input[i + 1]))) {
				tempEnd = i;
				if (tempEnd - tempStart > bestEnd - bestStart) {
					bestEnd = tempEnd;
					bestStart = tempStart;
				}
				tempStart = tempEnd + 1;
			}
		}
		if (bestEnd - bestStart == 0) {
			return new String[0];
		}
		String[] temp = new String[bestEnd - bestStart + 1];
		for (int i = bestStart, j = 0; i <= bestEnd; i++, j++) {
			temp[j] = input[i];
		}
		return temp;
	}
}
