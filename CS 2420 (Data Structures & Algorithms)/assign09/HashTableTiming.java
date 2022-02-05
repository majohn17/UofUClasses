package assign09;

public class HashTableTiming {

	public static void main(String[] args) {
		long start;
		long mid;
		long stop;
		long timesToLoop = 1000;
		int nSteps = 1000;
		int nMax = 20000;

		System.out.println("Bad Test");
		for (int n = nSteps; n <= nMax; n += nSteps) {

			// pre-timing set up goes here
			double averageCollisions = 0.0;

			// run clock for better timing
			start = System.nanoTime();
			while (System.nanoTime() - start < 1000000000) {
				// nothing
			}

			start = System.nanoTime();
			for (long i = 0; i < timesToLoop; i++) {
				HashTable<StudentBadHash, Integer> test = new HashTable<>();
				for (int j = 1; j <= n; j++) {
					test.put(new StudentBadHash(j, "" + j + j, "" + j + j + j), j);
				}
				averageCollisions += test.getCollisions();
			}

			mid = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) {
				HashTable<StudentBadHash, Integer> test = new HashTable<>();
				for (int j = 1; j <= n; j++) {
					new StudentBadHash(j, "" + j + j, "" + j + j + j);
				}
				test.getCollisions();
				// cut out of test
			}

			stop = System.nanoTime();

			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
			averageCollisions = averageCollisions / (double) timesToLoop;
			System.out.println(n + "\t" + averageTime + "\t" + averageCollisions);
		}

//		System.out.println("Medium Test");
//		for (int n = nSteps; n <= nMax; n += nSteps) {
//
//			// pre-timing set up goes here
//			double averageCollisions = 0.0;
//
//			// run clock for better timing
//			start = System.nanoTime();
//			while (System.nanoTime() - start < 1000000000) {
//				// nothing
//			}
//
//			start = System.nanoTime();
//			for (long i = 0; i < timesToLoop; i++) {
//				HashTable<StudentMediumHash, Integer> test = new HashTable<>();
//				for (int j = 1; j <= n; j++) {
//					test.put(new StudentMediumHash(j, "" + j + j, "" + j + j + j), j);
//				}
//				averageCollisions += test.getCollisions();
//			}
//
//			mid = System.nanoTime();
//
//			for (long i = 0; i < timesToLoop; i++) {
//				HashTable<StudentMediumHash, Integer> test = new HashTable<>();
//				for (int j = 1; j <= n; j++) {
//					new StudentMediumHash(j, "" + j + j, "" + j + j + j);
//				}
//				test.getCollisions();
//				// cut out of test
//			}
//
//			stop = System.nanoTime();
//
//			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
//			averageCollisions = averageCollisions / (double) timesToLoop;
//			System.out.println(n + "\t" + averageTime + "\t" + averageCollisions);
//		}

//		System.out.println("Good Test");
//		for (int n = nSteps; n <= nMax; n += nSteps) {
//
//			// pre-timing set up goes here
//			double averageCollisions = 0.0;
//
//			// run clock for better timing
//			start = System.nanoTime();
//			while (System.nanoTime() - start < 1000000000) {
//				// nothing
//			}
//
//			start = System.nanoTime();
//			for (long i = 0; i < timesToLoop; i++) {
//				HashTable<StudentGoodHash, Integer> test = new HashTable<>();
//				for (int j = 1; j <= n; j++) {
//					test.put(new StudentGoodHash(j, "" + j + j, "" + j + j + j), j);
//				}
//				averageCollisions += test.getCollisions();
//			}
//
//			mid = System.nanoTime();
//
//			for (long i = 0; i < timesToLoop; i++) {
//				HashTable<StudentGoodHash, Integer> test = new HashTable<>();
//				for (int j = 1; j <= n; j++) {
//					new StudentGoodHash(j, "" + j + j, "" + j + j + j);
//				}
//				test.getCollisions();
//				// cut out of test
//			}
//
//			stop = System.nanoTime();
//
//			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
//			averageCollisions = averageCollisions / (double) timesToLoop;
//			System.out.println(n + "\t" + averageTime + "\t" + averageCollisions);
//		}
	}
}
