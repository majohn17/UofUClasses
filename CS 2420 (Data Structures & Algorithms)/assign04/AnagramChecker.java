package assign04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;

/**
 * This class provides methods to determine different things about whether or
 * not different strings are anagrams.
 * 
 * @author Isaac Gibson and Matthew Johnsen
 * @version February 5, 2019
 */
public class AnagramChecker {

	/**
	 * Returns the lexicographically-sorted version of the input string.
	 * 
	 * @param s - the string that will be sorted
	 */
	public static String sort(String s) {
		char[] c = s.toCharArray();
		for (int i = 1; i < c.length; i++) {
			char val = c[i];
			int j;
			for (j = i - 1; j >= 0 && c[j] > val; j--) {
				c[j + 1] = c[j];
			}
			c[j + 1] = val;
		}
		return String.copyValueOf(c);
	}

	/**
	 * Sorts the input array based on a Comparator object.
	 * 
	 * @param a - the input array
	 * @param c - the comparator the sort will be based on
	 */
	public static <T> void insertionSort(T[] a, Comparator<? super T> c) {
		for (int i = 1; i < a.length; i++) {
			T val = a[i];
			int j;
			for (j = i - 1; j >= 0 && c.compare(a[j], val) > 0; j--) {
				a[j + 1] = a[j];
			}
			a[j + 1] = val;
		}
	}

	/**
	 * Returns true if the two input strings are anagrams of each other, otherwise
	 * returns false.
	 * 
	 * @param s1 - the first input string
	 * @param s2 - the second input string
	 */
	public static boolean areAnagrams(String s1, String s2) {
		if (s1.length() == s2.length()) {
			return sort(s1.toUpperCase()).equals(sort(s2.toUpperCase()));
		}
		return false;
	}

	/**
	 * Returns the largest group of anagrams in the input array of words, in no
	 * particular order. Returns an empty array if there are no anagrams in the
	 * input array.
	 * 
	 * @param input - the input array of words
	 */
	public static String[] getLargestAnagramGroup(String[] input) {
		if (input.length == 0) {
			return input;
		}
		AnagramChecker test = new AnagramChecker();
		insertionSort(input, test.new CompareCharacters());
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

	protected class CompareCharacters implements Comparator<String> {
		@Override
		public int compare(String left, String right) {
			String LHS = sort(left.toLowerCase());
			String RHS = sort(right.toLowerCase());
			if (LHS.length() > RHS.length()) {
				return 1;
			} else if (LHS.length() < RHS.length()) {
				return -1;
			} else {
				return LHS.compareTo(RHS);
			}
		}
	}

	/**
	 * Returns the largest group of anagrams in the input file of words, in no
	 * particular order. Returns an empty array if the file doesn't exist or is
	 * empty.
	 * 
	 * @param filename - the name of the input file of words
	 * @throws FileNotFoundException
	 */
	public static String[] getLargestAnagramGroup(String filename) {
		try {
			int length = 0;
			Scanner fileIn = new Scanner(new File(filename));
			while (fileIn.hasNextLine()) {
				fileIn.nextLine();
				length++;
			}
			fileIn.close();
			String[] s = new String[length];
			Scanner fileIn2 = new Scanner(new File(filename));
			for (int i = 0; i < length; i++) {
				s[i] = fileIn2.nextLine();
			}
			fileIn2.close();
			return getLargestAnagramGroup(s);
		} catch (FileNotFoundException e) {
			return new String[0];
		}
	}
}
