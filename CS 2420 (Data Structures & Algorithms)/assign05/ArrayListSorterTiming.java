package assign05;

import java.util.ArrayList;
import java.util.Random;

import static assign05.ArrayListSorter.*;

/**
 * Times different methods of the ArrayListSorter class.
 * 
 * @author Jacob Montes and Matthew Johnsen
 * @version February 13, 2019
 */
public class ArrayListSorterTiming {

	public static void main(String[] args) {
		long start;
		long mid;
		long stop;
		long timesToLoop = 1000;
		Random rand;
		ArrayList<Integer> testList = new ArrayList<Integer>(200000);

		System.out.println("Mergesort");
		for (int n = 10000; n <= 200000; n += 10000) {
			start = System.nanoTime();
			while (System.nanoTime() - start < 1000000000) {
			}

			// Testing
			start = System.nanoTime();
			for (long i = 0; i < timesToLoop; i++) {
				testList = generatePermuted(n);
				quicksort(testList);
			}

			mid = System.nanoTime();

			// Extra Stuff
			for (long i = 0; i < timesToLoop; i++) {
				testList = generatePermuted(n);
			}

			stop = System.nanoTime();

			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
			System.out.println(n + "\t" + averageTime);
		}
	}
}
