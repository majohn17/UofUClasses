package assign07;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static assign07.GraphUtility.*;

public class GraphUtilityTiming {

	public static List<Integer> generateAscendingList(int size) {
		List<Integer> list = new ArrayList<>();
		for (int i = 2; i <= (size * 2) + 1; i++) {
			list.add(i);
		}
		return list;
	}

	public static List<Integer> generateDuplicateList(int size) {
		List<Integer> list = new ArrayList<>();
		for (int i = 2; i <= (size * 2) + 1; i++) {
			list.add(i / 2);
		}
		return list;
	}

	public static Graph<Integer> generateIsCyclicGraph(int size) {
		List<Integer> source = new ArrayList<>();
		List<Integer> dest = new ArrayList<>();
		Random rnd = new Random();
		for (int i = 2; i <= (size * 2) + 1; i++) {
			source.add(i / 2);
			dest.add(rnd.nextInt(size));
		}
		return generateGraph(source, dest);
	}

	public static void main(String[] args) {
		long start;
		long mid;
		long stop;
		long timesToLoop = 1000;
		int nSteps = 10000;
		int nMax = 200000;

		List<Integer> source;
		List<Integer> dest;

//		System.out.println("isCyclic Test");
//		for (int n = nSteps; n <= nMax; n += nSteps) {
//			source = generateAscendingList(n);
//			Graph<Integer> graph = generateIsCyclicGraph(n);
//
//			// run clock for better timing
//			start = System.nanoTime();
//			while (System.nanoTime() - start < 1000000000) {
//				// nothing
//			}
//
//			start = System.nanoTime();
//			for (long i = 0; i < timesToLoop; i++) {
//				graph.isCyclic();
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

		System.out.println("sort Test");
		for (int n = nSteps; n <= nMax; n += nSteps) {

			source = generateDuplicateList(n);
			dest = generateAscendingList(n);
			Graph<Integer> graph = generateGraph(source, dest);

			// run clock for better timing
			start = System.nanoTime();
			while (System.nanoTime() - start < 1000000000) {
				// nothing
			}

			start = System.nanoTime();
			for (long i = 0; i < timesToLoop; i++) {
				graph.sort();
			}

			mid = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) {

				// cut out of test
				// keep loop always-
			}

			stop = System.nanoTime();

			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
			System.out.println(n + "\t" + averageTime);
		}

//		System.out.println("areConnected");
//		for (int n = nSteps; n <= nMax; n += nSteps) {
//
//			source = generateDuplicateList(n);
//			dest = generateAscendingList(n);
//			Random rnd = new Random();
//			Graph<Integer> graph = generateGraph(source, dest);
//
//			// run clock for better timing
//			start = System.nanoTime();
//			while (System.nanoTime() - start < 1000000000) {
//				// nothing
//			}
//
//			start = System.nanoTime();
//			for (long i = 0; i < timesToLoop; i++) {
//				graph.areConnected(rnd.nextInt(n), rnd.nextInt(n));
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
	}
}
