package assign03;

import java.util.Random;

public class SimplePriorityQueueTIming {

	public static void main(String[] args) {
		long start;
		long mid;
		long stop;
		long timesToLoop = 10000;
		Random rnd = new Random();

		System.out.println("FindMinTest");
		for (int n = 100000; n <= 2000000; n += 100000) {
			SimplePriorityQueue<Integer> test = new SimplePriorityQueue<Integer>();

			for (int i = 0; i < n; i++) {
				test.insert(n - i);
			}

			start = System.nanoTime();
			while (System.nanoTime() - start < 1000000000) {
			}

			start = System.nanoTime();
			for (long i = 0; i < timesToLoop; i++) {
				test.findMin();
			}

			mid = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) {

			}

			stop = System.nanoTime();

			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
			System.out.println(n + "\t" + averageTime);
		}

		System.out.println("InsertTest");
		for (int n = 100000; n <= 2000000; n += 100000) {
			long testTimeStart, testTimeEnd;
			testTimeStart = System.currentTimeMillis();
			SimplePriorityQueue<Integer> test = new SimplePriorityQueue<Integer>();

			start = System.nanoTime();
			while (System.nanoTime() - start < 1000000000) {
			}

			start = System.nanoTime();
			for (long i = 0; i < n; i++) {
				test.insert(rnd.nextInt());
			}

			mid = System.nanoTime();

			for (long i = 0; i < n; i++) {
				rnd.nextInt();
			}

			stop = System.nanoTime();
			testTimeEnd = System.currentTimeMillis();

			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
			double printTime = ((testTimeEnd - testTimeStart) / 1000.0);
			System.out.println(n + "\t" + averageTime + "\t" + "Test time was about " + printTime + " seconds.");
		}
	}
}
