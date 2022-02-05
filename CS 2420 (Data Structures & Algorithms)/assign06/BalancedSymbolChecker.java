package assign06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class containing the checkFile method for checking if the (, [, and { symbols
 * in an input file are correctly matched.
 * 
 * @author Erin Parker, Issac Gibson, and Matthew Johnsen
 * @version February 25, 2019
 */
public class BalancedSymbolChecker {

	/**
	 * Generates a message indicating whether the input file has unmatched symbols.
	 * (Use the helper methods below for constructing messages.)
	 * 
	 * @param filename - name of the input file to check
	 * @return a message indicating whether the input file has unmatched symbols
	 * @throws FileNotFoundException if the file does not exist
	 */
	public static String checkFile(String filename) throws FileNotFoundException {
		Scanner fileIn = new Scanner(new File(filename));
		return checker(fileIn);
	}

	private static String checker(Scanner scn) {
		ArrayStack<Character> stack = new ArrayStack<Character>();
		int line = 0, col = 0;
		while (scn.hasNextLine()) {
			String temp = scn.nextLine();
			line++;
			char[] chars = temp.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				col = i + 1;
				if (stack.isEmpty() || stack.peek() != '"') {
					if (stack.isEmpty() || stack.peek() != '\'')
						if (stack.isEmpty() || stack.peek() != '*') {
							if (chars[i] == '(' || chars[i] == '{' || chars[i] == '[') {
								stack.push(chars[i]);
							}
							if (chars[i] == ')' || chars[i] == '}' || chars[i] == ']') {
								if (!stack.isEmpty()) {
									char read = stack.pop();
									if ((read == '(' && chars[i] != ')') || (read == '{' && chars[i] != '}')
											|| (read == '[' && chars[i] != ']')) {
										return unmatchedSymbol(line, col, chars[i], flipSign(read));
									}
								} else {
									return unmatchedSymbol(line, col, chars[i], ' ');
								}
							}
							if (chars[i] == '/' && chars.length != i + 1) {
								if (chars[i + 1] == '*') {
									stack.push('*');
								}
								if (chars[i + 1] == '/') {
									break;
								}
							}
							if (chars[i] == '"') {
								stack.push('"');
								continue;
							}
							if (chars[i] == '\'') {
								stack.push('\'');
								continue;
							}
						}
				}
				if (chars[i] == '*' && chars.length != i + 1 && chars[i + 1] == '/') {
					stack.pop();
				}
				if (chars[i] == '"' && chars[i - 1] != '\\' && stack.peek() == '"') {
					stack.pop();
				}
				if (chars[i] == '\'' && chars[i - 1] != '\\' && stack.peek() == '\'') {
					stack.pop();
				}
			}
		}
		if (stack.size() > 0) {
			if (stack.peek() == '*') {
				return unfinishedComment();
			}
			return unmatchedSymbolAtEOF(flipSign(stack.peek()));
		}
		return allSymbolsMatch();
	}

	private static char flipSign(char c) {
		if (c == '(') {
			c = ')';
		} else if (c == '{') {
			c = '}';
		} else {
			c = ']';
		}
		return c;
	}

	/**
	 * Use this error message in the case of an unmatched symbol.
	 * 
	 * @param lineNumber     - the line number of the input file where the matching
	 *                       symbol was expected
	 * @param colNumber      - the column number of the input file where the
	 *                       matching symbol was expected
	 * @param symbolRead     - the symbol read that did not match
	 * @param symbolExpected - the matching symbol expected
	 * @return the error message
	 */
	private static String unmatchedSymbol(int lineNumber, int colNumber, char symbolRead, char symbolExpected) {
		return "ERROR: Unmatched symbol at line " + lineNumber + " and column " + colNumber + ". Expected "
				+ symbolExpected + ", but read " + symbolRead + " instead.";
	}

	/**
	 * Use this error message in the case of an unmatched symbol at the end of the
	 * file.
	 * 
	 * @param symbolExpected - the matching symbol expected
	 * @return the error message
	 */
	private static String unmatchedSymbolAtEOF(char symbolExpected) {
		return "ERROR: Unmatched symbol at the end of file. Expected " + symbolExpected + ".";
	}

	/**
	 * Use this error message in the case of an unfinished comment (i.e., a file
	 * that ends with an open /* comment).
	 * 
	 * @return the error message
	 */
	private static String unfinishedComment() {
		return "ERROR: File ended before closing comment.";
	}

	/**
	 * Use this message when no unmatched symbol errors are found in the entire
	 * file.
	 * 
	 * @return the success message
	 */
	private static String allSymbolsMatch() {
		return "No errors found. All symbols match.";
	}
}