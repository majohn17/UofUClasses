package assign06;

public class StackTiming {

	public static void main(String[] args) {
		long start;
		long mid;
		long stop;
		long timesToLoop = 10000;
		int nSteps = 100000;
		int nMax = 2000000;
		LinkedListStack<Integer> lStack = new LinkedListStack<>();
		ArrayStack<Integer> aStack = new ArrayStack<>();

		System.out.println("Linked list Push Test");
		for (int n = nSteps; n <= nMax; n += nSteps) {

			// run clock for better timing
			start = System.nanoTime();
			while (System.nanoTime() - start < 1000000000) {
				// nothing
			}

			start = System.nanoTime();
			for (long i = 0; i < timesToLoop; i++) {
				lStack.push((int) i);
			}

			mid = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) {

			}

			stop = System.nanoTime();

			lStack.clear();
			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
			System.out.println(n + "\t" + averageTime);
		}

		System.out.println("Array Push Test");
		for (int n = nSteps; n <= nMax; n += nSteps) {

			// run clock for better timing
			start = System.nanoTime();
			while (System.nanoTime() - start < 1000000000) {
				// nothing
			}

			start = System.nanoTime();
			for (long i = 0; i < timesToLoop; i++) {

				aStack.push((int) i);
			}

			mid = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) {

			}

			stop = System.nanoTime();

			aStack.clear();
			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
			System.out.println(n + "\t" + averageTime);
		}

		System.out.println("Linked list Pop Test");
		for (int n = nSteps; n <= nMax; n += nSteps) {

			for (int i = 0; i < n; i++) {
				lStack.push(i);
			}

			// run clock for better timing
			start = System.nanoTime();
			while (System.nanoTime() - start < 1000000000) {
				// nothing
			}

			start = System.nanoTime();
			for (long i = 0; i < timesToLoop; i++) {
				lStack.pop();
			}

			mid = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) {

			}

			stop = System.nanoTime();

			lStack.clear();
			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
			System.out.println(n + "\t" + averageTime);
		}

		System.out.println("Array Pop Test");
		for (int n = nSteps; n <= nMax; n += nSteps) {

			for (int i = 0; i < n; i++) {
				aStack.push(i);
			}
			// run clock for better timing
			start = System.nanoTime();
			while (System.nanoTime() - start < 1000000000) {
				// nothing
			}

			start = System.nanoTime();
			for (long i = 0; i < timesToLoop; i++) {
				aStack.pop();
			}

			mid = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) {

			}

			stop = System.nanoTime();

			aStack.clear();
			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
			System.out.println(n + "\t" + averageTime);
		}

		System.out.println("Linked list Peek Test");
		for (int n = nSteps; n <= nMax; n += nSteps) {

			for (int i = 0; i < n; i++) {
				lStack.push(i);
			}

			// run clock for better timing
			start = System.nanoTime();
			while (System.nanoTime() - start < 1000000000) {
				// nothing
			}

			start = System.nanoTime();
			for (long i = 0; i < timesToLoop; i++) {
				lStack.peek();
			}

			mid = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) {

			}

			stop = System.nanoTime();

			lStack.clear();
			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
			System.out.println(n + "\t" + averageTime);
		}

		System.out.println("Array Peek Test");
		for (int n = nSteps; n <= nMax; n += nSteps) {

			for (int i = 0; i < n; i++) {
				aStack.push(i);
			}
			// run clock for better timing
			start = System.nanoTime();
			while (System.nanoTime() - start < 1000000000) {
				// nothing
			}

			start = System.nanoTime();
			for (long i = 0; i < timesToLoop; i++) {

				aStack.peek();
			}

			mid = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) {

			}

			stop = System.nanoTime();

			aStack.clear();
			double averageTime = ((mid - start) - (stop - mid)) / (double) timesToLoop;
			System.out.println(n + "\t" + averageTime);
		}
		System.out.println("----- END OF TESTS -----");
	}
}
