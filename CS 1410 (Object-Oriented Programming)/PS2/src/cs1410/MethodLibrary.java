package cs1410;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * A collection of methods for the second assignment of CS 1410.
 * 
 * @author Matthew Johnsen
 */
public class MethodLibrary
{
    /**
     * You can use this main method for experimenting with your methods if you like, but it is not part of the
     * assignment. You might find it easier to experiment using JShell.
     */
    public static void main (String[] args)
    {
        boolean temp = isEarlierThan("04-20-2000", "04-19-2000");
        System.out.println(temp);

        boolean temp2 = isEarlierThan("04-20-2000", "04-21-2000");
        System.out.println(temp2);
    }

    /**
     * Returns the nth root of x, where n is positive. For example, nthRoot(27.0, 3) is 3.0 (the cube root of 27), and
     * nthRoot(64.0, 6) is 2.0. NOTE: A small amount of roundoff error is acceptable.
     * 
     * The number x is required to have a real-valued nth root, and n is required to be positive. If this requirement is
     * violated, the behavior of the method is undefined (it does not matter what it does).
     */
    public static double nthRoot (double x, int n)
    {
        return Math.pow(x, (1.0 / n));
    }

    /**
     * Reports whether or not c is a vowel ('a', 'e', 'i', 'o', 'u' or the upper-case version). For example,
     * isVowel('a') and isVowel('U') are true; isVowel('x') and isVowel('H') are false.
     */
    public static boolean isVowel (char c)
    {
        char temp = Character.toUpperCase(c);
        return (temp == 'A' || temp == 'E' || temp == 'I' || temp == 'O' || temp == 'U');
    }

    /**
     * Reports whether or not number is a multiple of both factor1 and factor2. For example, isMultipleOf(15, 3, 5) is
     * true and isMutipleOf(27, 3, 4) is false.
     * 
     * Both factor1 and factor2 are required to be non-zero. If this requirement is violated, the behavior of the method
     * is undefined (it does not matter what it does).
     */
    public static boolean isMultipleOf (int number, int factor1, int factor2)
    {
        return ((number % factor1 == 0) && (number % factor2 == 0));
    }

    /**
     * Returns the string that results from capitalizing the first character of s, which is required to have at least
     * one character. For example, capitalize("hello") is "Hello" and capitalize("Jack") is "Jack".
     * 
     * The string s is required to be non-empty. If this requirement is violated, the behavior of the method is
     * undefined (it does not matter what it does).
     * 
     * IMPLEMENTATION HINT: The Character.toUpperCase() method will be helpful. The method s.substring() [where s is a
     * String] will also be helpful. Learn more about them before starting!
     */
    public static String capitalize (String s)
    {
        char firstLetter = s.charAt(0);
        return (Character.toString(Character.toUpperCase(firstLetter)) + s.substring(1));
    }

    /**
     * Returns a new string that (1) begins with all the characters of original that come after the first occurrence of
     * target and (2) ends with all the characters of original that come before the first occurrence of pattern. For
     * example, flip("abcdefg", 'd') is "efgabc", flip("ababad", 'b') is "abada", and flip("x", 'x') is "".
     * 
     * The string original is required to contain the character target. If this requirement is violated, the behavior of
     * the method is undefined (it does not matter what it does).
     * 
     * IMPLEMENTATION HINT: The methods s.indexOf() and s.substring() [where s is a String] will be helpful.
     */
    public static String flip (String original, char target)
    {
        int index = original.indexOf(target);
        String end = original.substring(index + 1);
        String beginning = original.substring(0, index);
        return end + beginning;
    }

    /**
     * Returns a new string that is just like s except all of the lower-case vowels ('a', 'e', 'i', 'o', 'u') have been
     * capitalized. For example, capitalizeVowels("hello") is "hEllO", capitalizeVowels("String") is "StrIng", and
     * capitalizeVowels("nth") is "nth".
     * 
     * IMPLEMENTATION HINT: The method s.replace() [where s is a String] will be helpful.
     */
    public static String capitalizeVowels (String s)
    {
        String temp = s.replace('a', 'A');
        temp = temp.replace('e', 'E');
        temp = temp.replace('i', 'I');
        temp = temp.replace('o', 'O');
        temp = temp.replace('u', 'U');
        return temp;
    }

    /**
     * Reports whether s1 and s2 end with the same n characters. For example, sameEnding("abcde" "xde", 2) is true and
     * sameEnding("abcde", "xde", 3) is false.
     * 
     * The value of n is required to be non-negative, and the two strings must each contain at least n characters. If
     * this requirement is violated, the behavior of the method is undefined (it does not matter what it does).
     * 
     * IMPLEMENTATION HINT: The methods s.length() and s.substring() [where s is a String] will be helpful.
     */
    public static boolean sameEnding (String s1, String s2, int n)
    {
        String temp1 = s1.substring(s1.length() - n);
        String temp2 = s2.substring(s2.length() - n);
        return temp1.equals(temp2);
    }

    /**
     * Returns the value of the largest of the five int literals, separated by white space, that make up the integerList
     * parameter. For example, largestOfFive(" 15 28 -99 62 44 ") is 62.
     * 
     * The string integerList is required to consist of exactly five int literals surrounded by white space. If this
     * requirement is violated, the behavior of the method is undefined (it does not matter what it does).
     * 
     * IMPLEMENTATION HINT: The class java.util.Scanner will be helpful. Use the one-argument constructor that takes a
     * String as a parameter and the nextInt() method.
     */
    public static int largestOfFive (String integerList)
    {
        Scanner scnr = new Scanner(integerList);
        int int1 = scnr.nextInt();
        int int2 = scnr.nextInt();
        int int3 = scnr.nextInt();
        int int4 = scnr.nextInt();
        int int5 = scnr.nextInt();
        scnr.close();
        return Math.max(int5, Math.max(int4, Math.max(int3, Math.max(int2, int1))));
    }

    /**
     * Reports whether or not date1 comes earlier in time than date2. For example, isEarlierThan("12-01-2015",
     * "02-15-2017") is true but isEarlierThan("10-11-2016", "10-11-2016") and isEarlierThan("09-09-1967", "02-15-1933")
     * is false.
     * 
     * The two parameters must be of the form MM-DD-YYYY where YYYY is a year, MM is the two-digit number of a month, DD
     * is a two-digit number of a day, and the entire date is valid. If this requirement is violated, the behavior of
     * the method is undefined (it does not matter what it does).
     * 
     * IMPLEMENTATION HINT: Turn this into a String comparison problem.
     */
    public static boolean isEarlierThan (String date1, String date2)
    {
        int month1 = Integer.parseInt(date1.substring(0, date1.indexOf("-")));
        int day1 = Integer.parseInt(date1.substring((date1.indexOf("-") + 1), (date1.indexOf("-") + 3)));
        int year1 = Integer.parseInt(date1.substring((date1.indexOf("-") + 4)));
        int month2 = Integer.parseInt(date2.substring(0, date2.indexOf("-")));
        int day2 = Integer.parseInt(date2.substring((date2.indexOf("-") + 1), (date2.indexOf("-") + 3)));
        int year2 = Integer.parseInt(date2.substring((date2.indexOf("-") + 4)));

        return ((year1 < year2) || ((year1 == year2) && (month1 < month2))
                || ((year1 == year2) && (month1 == month2) && (day1 < day2)));
    }

    /**
     * Returns the integer numeral that represents the sum of the integer numerals integer1 and integer2. For example,
     * addNumerals("125", "64") is "189". The method works for numerals of any length, including numerals that consist
     * of hundreds or thousands of digits and are far too big to parse as ints or longs.
     * 
     * The two parameters must both be valid integer numerals. That is, both must consist of one or more digits
     * optionally preceded by a + and - sign. If this requirement is violated, the behavior of the method is undefined
     * (it does not matter what it does).
     * 
     * IMPLEMENTATION HINT: The class java.math.BigInteger will be helpful. Use the one-argument constructor that takes
     * a String as a parameter, the add method(), and the toString() method.
     */
    public static String addNumerals (String integer1, String integer2)
    {
        BigInteger temp1 = new BigInteger(integer1);
        BigInteger temp2 = new BigInteger(integer2);
        temp1 = temp1.add(temp2);
        return temp1.toString();
    }
}
