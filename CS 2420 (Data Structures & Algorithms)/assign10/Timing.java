package assign10;

import java.util.ArrayList;
import java.util.Random;
import static assign10.FindKLargest.*;

public class Timing {
	public static void main(String[] args) {
		long start;
		long mid;
		long stop;
		long timesToLoop = 1000;
		int nSteps = 1000;
		int nMax = 20000;

		System.out.println("Add Test");
		System.out.println("N	Time");
//		for (int n = nSteps; n <= nMax; n += nSteps) {
//
//			// pre-timing set up goes here
//			Random rand = new Random();
//			// run clock for better timing
//			start = System.nanoTime();
//			while (System.nanoTime() - start < 1000000000) {
//				// nothing
//			}
//
//			start = System.nanoTime();
//			for (long i = 0; i < timesToLoop; i++) {
//				BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<>();
//				for (int j = 0; j < n; j++) {
//					heap.add(rand.nextInt());
//				}
//			}
//
//			mid = System.nanoTime();
//
//			for (long i = 0; i < timesToLoop; i++) {
//				BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<>();
//				for (int j = 0; j < n; j++) {
//					rand.nextInt();
//				}
//			}
//
//			stop = System.nanoTime();
//
//			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
//			System.out.println(n + "\t" + averageTime);
//		}

//		System.out.println("Peek Test");
//		System.out.println("N	Time");
//		for (int n = nSteps; n <= nMax; n += nSteps) {
//
//			// pre-timing set up goes here
//			BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<>();
//			for (int i = 0; i < n; i++) {
//				heap.add(i);
//			}
//			// run clock for better timing
//			start = System.nanoTime();
//			while (System.nanoTime() - start < 1000000000) {
//				// nothing
//			}
//
//			start = System.nanoTime();
//			for (long i = 0; i < timesToLoop; i++) {
//				heap.peek();
//			}
//
//			mid = System.nanoTime();
//
//			for (long i = 0; i < timesToLoop; i++) {
//				
//			}
//
//			stop = System.nanoTime();
//
//			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
//			System.out.println(n + "\t" + averageTime);
//		}

//		System.out.println("extractMax Test");
//		System.out.println("N	Time");
//		for (int n = nSteps; n <= nMax; n += nSteps) {
//
//			// pre-timing set up goes here
//			// run clock for better timing
//			start = System.nanoTime();
//			while (System.nanoTime() - start < 1000000000) {
//				// nothing
//			}
//
//			start = System.nanoTime();
//			for (long i = 0; i < timesToLoop; i++) {
//				BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<>();
//				for (int j = n; j > 0; j--) {
//					heap.add(j);
//				}
//				for (int k = 0; k < n; k++) {
//					heap.extractMax();
//				}
//			}
//
//			mid = System.nanoTime();
//
//			for (long i = 0; i < timesToLoop; i++) {
//				BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<>();
//				for (int j = n; j > 0; j--) {
//					heap.add(j);
//				}
//				for (int k = 0; k < n; k++) {
//				}
//			}
//
//			stop = System.nanoTime();
//
//			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
//			System.out.println(n + "\t" + averageTime);
//		}

		for (int type = 1; type <= 4; type++) {
			int k;

			System.out.println("findKLargestHeap Test " + type);
			System.out.println("N	Time");
			for (int n = nSteps; n <= nMax; n += nSteps) {
				switch (type) {
				case 1:
					k = 5;
					break;
				case 2:
					k = 500;
					break;
				case 3:
					k = n / 4;
					break;
				case 4:
					k = n;
					break;
				default:
					k = 1;
				}

				Random rand = new Random();
				// pre-timing set up goes here
				ArrayList<Integer> list = new ArrayList<>();
				for (int i = 0; i < n; i++) {
					list.add(rand.nextInt());
				}

				// run clock for better timing
				start = System.nanoTime();
				while (System.nanoTime() - start < 1000000000) {
					// nothing
				}

				start = System.nanoTime();
				for (long i = 0; i < timesToLoop; i++) {

					findKLargestHeap(list, k);
				}

				mid = System.nanoTime();

				for (long i = 0; i < timesToLoop; i++) {

					// cut out of test
				}

				stop = System.nanoTime();

				double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
				System.out.println(n + "\t" + averageTime);
			}

			System.out.println("findKLargestSort Test " + type);
			System.out.println("N	Time");
			for (int n = nSteps; n <= nMax; n += nSteps) {
				switch (type) {
				case 1:
					k = 5;
					break;
				case 2:
					k = 500;
					break;
				case 3:
					k = n / 4;
					break;
				case 4:
					k = n;
					break;
				default:
					k = 1;
				}

				Random rand = new Random();
				// pre-timing set up goes here
				ArrayList<Integer> list = new ArrayList<>();
				for (int i = 0; i < n; i++) {
					list.add(rand.nextInt());
				}

				// run clock for better timing
				start = System.nanoTime();
				while (System.nanoTime() - start < 1000000000) {
					// nothing
				}

				start = System.nanoTime();
				for (long i = 0; i < timesToLoop; i++) {

					findKLargestSort(list, k);
				}

				mid = System.nanoTime();

				for (long i = 0; i < timesToLoop; i++) {

					// cut out of test
				}

				stop = System.nanoTime();

				double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
				System.out.println(n + "\t" + averageTime);
			}
		}

	}
}
