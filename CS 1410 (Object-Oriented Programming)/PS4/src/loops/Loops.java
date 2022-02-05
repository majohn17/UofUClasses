package loops;

import java.util.Scanner;

/**
 * A collection of methods for part of the fourth assignment of CS 1410.
 * 
 * @author Matthew Johnsen
 */
public class Loops
{
    /**
     * Method used for experimenting with other methods.
     */
    public static void main (String[] args)
    {
        System.out.println("hello world\nthis is a test\nthis is another test");
        System.out.println(findMostWhitespace(new Scanner("")));
    }

    /**
     * This method returns returns true if t occurs as a token within s, and returns false otherwise.
     * 
     * @param s The string being checked for the token.
     * @param t The token that is being looked for in the string.
     */
    public static boolean containsToken (String s, String t)
    {
        Scanner scn = new Scanner(s);
        while (scn.hasNext())
        {
            String token = scn.next();
            if (token.equals(t))
            {
                scn.close();
                return true;
            }
        }
        scn.close();
        return false;
    }

    /**
     * This method returns a line from scn that contains t as a token (if a line with the token exists), and returns an
     * empty string otherwise.
     * 
     * @param scn The scanner that is being checked for the token.
     * @param t   The token that is being looked for in the scanner
     */
    public static String findLineWithToken (Scanner scn, String t)
    {
        while (scn.hasNext())
        {
            while (scn.hasNextLine())
            {
                String line = scn.nextLine();
                if (containsToken(line, t) == true)
                {
                    return line;
                }
            }
            return "";
        }
        return "";
    }

    /**
     * This method returns true if s is the same forwards and backwards, and returns false otherwise.
     * 
     * @param s The string being checked to see if it's a palindrome.
     */
    public static boolean isPalindrome (String s)
    {
        boolean isSame = false;
        if (s.equals(""))
        {
            return true;
        }
        else
        {
            for (int i = 0; i < s.length(); i++)
            {
                if (s.charAt(i) == s.charAt((s.length() - 1) - i))
                {
                    isSame = true;
                }
                else
                {
                    isSame = false;
                    return isSame;
                }
            }
        }
        return isSame;
    }

    /**
     * This method returns the longest token from scn that is a palindrome (if one exists) or an empty string otherwise.
     * 
     * @param scn The scanner being checked for the longest palindrome.
     */
    public static String findLongestPalindrome (Scanner scn)
    {
        String longest = "";
        while (scn.hasNext())
        {
            String temp = scn.next();
            if (isPalindrome(temp) == true)
            {
                if (temp.length() > longest.length())
                {
                    longest = temp;
                }
            }
        }
        return longest;
    }

    /**
     * This method finds the line in scn with the most whitespace characters and returns the number of whitespace
     * characters it contains (if scn contains at least one line) or -1 otherwise.
     * 
     * @param scn The scanner being checked for lines with whitespace.
     */
    public static int findMostWhitespace (Scanner scn)
    {
        int whitespaceCount = 0;
        while (scn.hasNextLine())
        {
            String line = scn.nextLine();
            if (countWhitespace(line) > whitespaceCount)
            {
                whitespaceCount = countWhitespace(line);
            }
        }
        if (whitespaceCount == 0)
        {
            return -1;
        }
        else
        {
            return whitespaceCount;
        }
    }

    /**
     * This method counts the total amount of whitespace in a string.
     * 
     * @param s The string that is checked for whitespace.
     */
    public static int countWhitespace (String s)
    {
        int count = 0;
        for (int i = 0; i < s.length(); i++)
        {
            if (Character.isWhitespace(s.charAt(i)) == true)
            {
                count++;
            }
        }
        return count;
    }
}
