package assign08;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class BinarySearchTreeTiming {

	public static void main(String[] args) {
		long start;
		long mid;
		long stop;
		long timesToLoop = 1000;
		int nSteps = 1000;
		int nMax = 20000;

//		System.out.println("BST Sorted Test");
//		for (int n = nSteps; n <= nMax; n += nSteps) {
//
//			// run clock for better timing
//			start = System.nanoTime();
//			while (System.nanoTime() - start < 1000000000) {
//				// nothing
//			}
//
//			start = System.nanoTime();
//			for (long i = 0; i < timesToLoop; i++) {
//				BinarySearchTree<Integer> testTree = new BinarySearchTree<Integer>();
//				for (int j = 0; j < n; j++) {
//					testTree.add(j);
//				}
//			}
//
//			mid = System.nanoTime();
//
//			for (long i = 0; i < timesToLoop; i++) {
//				BinarySearchTree<Integer> testTree = new BinarySearchTree<Integer>();
//				for (int j = 0; j < n; j++) {
//				}
//			}
//
//			stop = System.nanoTime();
//
//			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
//			System.out.println(n + "\t" + averageTime);
//		}

		System.out.println("BST Random Add Test");
		for (int n = nSteps; n <= nMax; n += nSteps) {
			ArrayList<Integer> shuffled = generatePermuted(n);

			// run clock for better timing
			start = System.nanoTime();
			while (System.nanoTime() - start < 1000000000) {
				// nothing
			}

			start = System.nanoTime();
			for (long i = 0; i < timesToLoop; i++) {
				BinarySearchTree<Integer> testTree = new BinarySearchTree<Integer>();
				for (int j = 0; j < n; j++) {
					testTree.add(shuffled.get(j));
				}
			}

			mid = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) {
				BinarySearchTree<Integer> testTree = new BinarySearchTree<Integer>();
				for (int j = 0; j < n; j++) {
					shuffled.get(j);
				}
			}

			stop = System.nanoTime();

			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
			System.out.println(n + "\t" + averageTime);
		}

//		System.out.println("BST Random Contains Test");
//		for (int n = nSteps; n <= nMax; n += nSteps) {
//			ArrayList<Integer> shuffled = generatePermuted(n);
//			BinarySearchTree<Integer> testTree = new BinarySearchTree<Integer>();
//			for (int j = 0; j < n; j++) {
//				testTree.add(shuffled.get(j));
//			}
//
//			// run clock for better timing
//			start = System.nanoTime();
//			while (System.nanoTime() - start < 1000000000) {
//				// nothing
//			}
//
//			start = System.nanoTime();
//			for (long i = 0; i < timesToLoop; i++) {
//				for (int j = 0; j < n; j++) {
//					testTree.contains(j);
//				}
//			}
//
//			mid = System.nanoTime();
//
//			for (long i = 0; i < timesToLoop; i++) {
//				for (int j = 0; j < n; j++) {
//				}
//			}
//
//			stop = System.nanoTime();
//
//			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
//			System.out.println(n + "\t" + averageTime);
//		}
//
//		System.out.println("Java Random Add Test");
//		for (int n = nSteps; n <= nMax; n += nSteps) {
//			ArrayList<Integer> shuffled = generatePermuted(n);
//
//			// run clock for better timing
//			start = System.nanoTime();
//			while (System.nanoTime() - start < 1000000000) {
//				// nothing
//			}
//
//			start = System.nanoTime();
//			for (long i = 0; i < timesToLoop; i++) {
//				TreeSet<Integer> testTree = new TreeSet<Integer>();
//				for (int j = 0; j < n; j++) {
//					testTree.add(shuffled.get(j));
//				}
//			}
//
//			mid = System.nanoTime();
//
//			for (long i = 0; i < timesToLoop; i++) {
//				TreeSet<Integer> testTree = new TreeSet<Integer>();
//				for (int j = 0; j < n; j++) {
//					shuffled.get(j);
//				}
//			}
//
//			stop = System.nanoTime();
//
//			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
//			System.out.println(n + "\t" + averageTime);
//		}
//
//		System.out.println("Java Random Contains Test");
//		for (int n = nSteps; n <= nMax; n += nSteps) {
//			ArrayList<Integer> shuffled = generatePermuted(n);
//			TreeSet<Integer> testTree = new TreeSet<Integer>();
//			for (int j = 0; j < n; j++) {
//				testTree.add(shuffled.get(j));
//			}
//
//			// run clock for better timing
//			start = System.nanoTime();
//			while (System.nanoTime() - start < 1000000000) {
//				// nothing
//			}
//
//			start = System.nanoTime();
//			for (long i = 0; i < timesToLoop; i++) {
//				for (int j = 0; j < n; j++) {
//					testTree.contains(j);
//				}
//			}
//
//			mid = System.nanoTime();
//
//			for (long i = 0; i < timesToLoop; i++) {
//				for (int j = 0; j < n; j++) {
//				}
//			}
//
//			stop = System.nanoTime();
//
//			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
//			System.out.println(n + "\t" + averageTime);
//		}
	}

	public static ArrayList<Integer> generatePermuted(int size) {
		ArrayList<Integer> temp = new ArrayList<>();
		for (int i = 1; i <= size; i++) {
			temp.add(i);
		}
		Collections.shuffle(temp);
		return temp;
	}
}