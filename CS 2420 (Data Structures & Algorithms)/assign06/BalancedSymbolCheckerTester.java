package assign06;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;
import static assign06.BalancedSymbolChecker.*;

class BalancedSymbolCheckerTester {
	private String file(int fileNum) {
		return "C:\\Users\\matth\\Desktop\\Programming Stuff\\CS 2420 Workspace\\Assignments\\src\\assign06\\Class"
				+ fileNum + ".java";
	}

	@Test
	void test1() throws FileNotFoundException {
		assertEquals("ERROR: Unmatched symbol at line 6 and column 1. Expected ), but read } instead.",
				checkFile(file(1)));
	}

	@Test
	void test2() throws FileNotFoundException {
		assertEquals("ERROR: Unmatched symbol at line 7 and column 1. Expected  , but read } instead.",
				checkFile(file(2)));
	}

	@Test
	void test3() throws FileNotFoundException {
		assertEquals("No errors found. All symbols match.", checkFile(file(3)));
	}

	@Test
	void test4() throws FileNotFoundException {
		assertEquals("ERROR: File ended before closing comment.", checkFile(file(4)));
	}

	@Test
	void test5() throws FileNotFoundException {
		assertEquals("ERROR: Unmatched symbol at line 3 and column 18. Expected ], but read } instead.",
				checkFile(file(5)));
	}

	@Test
	void test6() throws FileNotFoundException {
		assertEquals("No errors found. All symbols match.", checkFile(file(6)));
	}

	@Test
	void test7() throws FileNotFoundException {
		assertEquals("ERROR: Unmatched symbol at line 3 and column 33. Expected ], but read ) instead.",
				checkFile(file(7)));
	}

	@Test
	void test8() throws FileNotFoundException {
		assertEquals("ERROR: Unmatched symbol at line 5 and column 30. Expected }, but read ) instead.",
				checkFile(file(8)));
	}

	@Test
	void test9() throws FileNotFoundException {
		assertEquals("ERROR: Unmatched symbol at line 3 and column 33. Expected ), but read ] instead.",
				checkFile(file(9)));
	}

	@Test
	void test10() throws FileNotFoundException {
		assertEquals("ERROR: Unmatched symbol at line 5 and column 10. Expected }, but read ] instead.",
				checkFile(file(10)));
	}

	@Test
	void test11() throws FileNotFoundException {
		assertEquals("ERROR: Unmatched symbol at the end of file. Expected }.", checkFile(file(11)));
	}

	@Test
	void test12() throws FileNotFoundException {
		assertEquals("No errors found. All symbols match.", checkFile(file(12)));
	}

	@Test
	void test13() throws FileNotFoundException {
		assertEquals("No errors found. All symbols match.", checkFile(file(13)));
	}

	@Test
	void test14() throws FileNotFoundException {
		assertEquals("No errors found. All symbols match.", checkFile(file(14)));
	}

	@Test
	void test15() throws FileNotFoundException {
		assertEquals("No errors found. All symbols match.", checkFile(file(15)));
	}

	@Test
	void test16() throws FileNotFoundException {
		assertEquals("No errors found. All symbols match.", checkFile(file(16)));
	}
	
	@Test
	void test17() throws FileNotFoundException {
		assertEquals("No errors found. All symbols match.", checkFile(file(17)));
	}
}
