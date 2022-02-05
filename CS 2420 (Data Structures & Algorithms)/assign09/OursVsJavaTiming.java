package assign09;

import java.util.HashMap;

public class OursVsJavaTiming {
	public static void main(String[] args) {
		long start;
		long mid;
		long stop;
		long timesToLoop = 1000;
		int nSteps = 1000;
		int nMax = 20000;

		System.out.println("our put Test");
		System.out.println("N\tTime");
		for (int n = nSteps; n <= nMax; n += nSteps) {

			// pre-timing set up goes here

			// run clock for better timing
			start = System.nanoTime();
			while (System.nanoTime() - start < 1000000000) {
				// nothing
			}

			start = System.nanoTime();
			for (long i = 0; i < timesToLoop; i++) {
				HashTable<String, Integer> test = new HashTable<>();
				for (int j = 1; j <= n; j++) {
					test.put("" + j, 2);
				}
				// test
			}

			mid = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) {
				@SuppressWarnings("unused")
				HashTable<String, Integer> test = new HashTable<>();
				for (int j = 1; j <= n; j++) {

				}
				// cut out of test
			}

			stop = System.nanoTime();

			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
			System.out.println(n + "\t" + averageTime);
		}

		System.out.println("Java put Test");
		System.out.println("N\tTime");
		for (int n = nSteps; n <= nMax; n += nSteps) {

			// pre-timing set up goes here

			// run clock for better timing
			start = System.nanoTime();
			while (System.nanoTime() - start < 1000000000) {
				// nothing
			}

			start = System.nanoTime();
			for (long i = 0; i < timesToLoop; i++) {
				HashMap<String, Integer> test = new HashMap<>();
				for (int j = 1; j <= n; j++) {
					test.put("" + j, 2);
				}
				// test
			}

			mid = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) {
				@SuppressWarnings("unused")
				HashMap<String, Integer> test = new HashMap<>();
				for (int j = 1; j <= n; j++) {

				}
				// cut out of test
				// keep loop always-
			}

			stop = System.nanoTime();

			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
			System.out.println(n + "\t" + averageTime);
		}

		System.out.println("our remove Test");
		System.out.println("N\tTime");
		for (int n = nSteps; n <= nMax; n += nSteps) {

			// pre-timing set up goes here

			// run clock for better timing
			start = System.nanoTime();
			while (System.nanoTime() - start < 1000000000) {
				// nothing
			}

			start = System.nanoTime();
			for (long i = 0; i < timesToLoop; i++) {
				HashTable<String, Integer> test = new HashTable<>();
				for (int j = 1; j <= n; j++) {
					test.put("" + j, 2);
				}
				for (int j = 1; j <= n; j++) {
					test.remove("" + j);
				}
				// test
			}

			mid = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) {

				HashTable<String, Integer> test = new HashTable<>();
				for (int j = 1; j <= n; j++) {
					test.put("" + j, 2);
				}
				for (int j = 1; j <= n; j++) {

				}

				// cut out of test
			}

			stop = System.nanoTime();

			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
			System.out.println(n + "\t" + averageTime);
		}

		System.out.println("Java remove Test");
		System.out.println("N\tTime");
		for (int n = nSteps; n <= nMax; n += nSteps) {

			// pre-timing set up goes here

			// run clock for better timing
			start = System.nanoTime();
			while (System.nanoTime() - start < 1000000000) {
				// nothing
			}

			start = System.nanoTime();
			for (long i = 0; i < timesToLoop; i++) {
				HashMap<String, Integer> test = new HashMap<>();
				for (int j = 1; j <= n; j++) {
					test.put("" + j, 2);
				}
				for (int j = 1; j <= n; j++) {
					test.remove("" + j);
				}
				// test
			}

			mid = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) {

				HashMap<String, Integer> test = new HashMap<>();
				for (int j = 1; j <= n; j++) {
					test.put("" + j, 2);
				}
				for (int j = 1; j <= n; j++) {

				}
				// cut out of test
				// keep loop always-
			}

			stop = System.nanoTime();

			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
			System.out.println(n + "\t" + averageTime);
		}
	}
}
