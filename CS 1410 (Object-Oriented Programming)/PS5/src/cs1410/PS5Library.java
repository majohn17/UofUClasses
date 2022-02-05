package cs1410;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

/**
 * A library of methods for implementing the random text generation algorithm described in PS5, Fall 2017.
 * 
 * @author Matthew Johnsen and Joe Zachary
 */
public class PS5Library
{
    /**
     * Demonstrates the use of the generateText method.
     */
    public static void main (String[] args) throws IOException
    {
        // You won't need to use this feature in PS5, but this shows how to include a resource (in this
        // case a text file) as part of a project. I created a package called "books", then put two
        // text files into the package. I was then able to open one of those files as shown below. When
        // I export the project, the resources go along with it.
        try (InputStream book = PS5Library.class.getResourceAsStream("/books/PrideAndPrejudice.txt");
                Scanner input = new Scanner(book))
        {
            System.out.println(generateText(input, 1, 10000));
        }
    }

    /**
     * Uses all the text in the input to generate and return random text with the specified level and length, using the
     * algorithm described in PS5, CS 1410, Fall 2018.
     * 
     * @throws IllegalArgumentException if level < 0, or length < 0, or there are less than level+1 chars in the input.
     */
    public static String generateText (Scanner input, int level, int length)
    {
        // Validate the parameters
        if (level < 0 || length < 0)
        {
            throw new IllegalArgumentException();
        }

        // Grab all the text from the Scanner and make sure it is long enough.
        String text = scannerToString(input);
        if (level >= text.length())
        {
            throw new IllegalArgumentException();
        }

        // Create a random number generator to pass to the methods that make random choices
        Random rand = new Random();

        // Get the initial pattern.
        String pattern = chooseSubstring(text, level, rand);

        // Build up the final result one character at a time. We use a StringBuilder because
        // it is more efficient than using a String when doing long sequences of appends.
        StringBuilder result = new StringBuilder();
        while (result.length() < length)
        {
            try
            {
                // Pick at random a character that follows the pattern in the text and append it
                // to the result. If there is no such character (which can happen if the pattern
                // occurs only once, at the very end of text), the method we're calling will throw
                // a NoSuchElementException, which is caught below.
                char newChar = pickCharThatFollowsPattern(text, pattern, rand);
                result.append(newChar);

                // Update the pattern by removing its first character and adding on the new
                // character. The length of the pattern remains the same. If the pattern is
                // the empty string, though, it never changes.)
                if (pattern.length() > 0)
                {
                    pattern = pattern.substring(1) + newChar;
                }
            }
            catch (NoSuchElementException e)
            {
                // It is possible to get stuck if the pattern occurs only once in the text and
                // that occurrence is at the very end of the text. In this case, we pick a new
                // seed and keep going.
                pattern = chooseSubstring(text, level, rand);
            }
        }

        // Return the string we've accumulated.
        return result.toString();
    }

    /**
     * This method returns a string that consists of all the characters in the scanner in their original order,
     * including the newlines. The last line (assuming there are any lines) should always end with a newline, even if it
     * didn't in the original text.
     */
    public static String scannerToString (Scanner scnr)
    {
        String words = "";
        StringBuilder wordCombo = new StringBuilder();
        while (scnr.hasNextLine())
        {
            String nextLine = scnr.nextLine();
            wordCombo.append(nextLine + "\n");
        }
        words = wordCombo.toString();
        return words;
    }

    /**
     * This method uses a random number generator to return a randomly chosen substring of text that has the specified
     * length.
     * 
     * @throws IllegalArgumentException if length is < 0 or length > text.length()
     */
    public static String chooseSubstring (String text, int length, Random rand)
    {
        String randString = "";
        if (length < 0 || length > text.length())
        {
            throw new IllegalArgumentException(
                    "Length must be greater than -1 or less than or equal to length of the text");
        }
        int randIndex = rand.nextInt((text.length() + 1) - length);
        randString = text.substring(randIndex, randIndex + length);
        return randString;
    }

    /**
     * This method returns a list contains the character that follows each non-tail occurrence of the pattern in the
     * text. The length of the list must be the same as the number of non-tail occurrences of the pattern.
     */
    public static ArrayList<Character> getCharsThatFollowPattern (String text, String pattern)
    {
        ArrayList<Character> patternChars = new ArrayList<Character>();

        int index = text.indexOf(pattern);
        while (((index + pattern.length()) < text.length()) && index >= 0)
        {
            patternChars.add(text.charAt(index + pattern.length()));
            index = text.indexOf(pattern, index + 1);
        }
        return patternChars;
    }

    /**
     * This method randomly chooses a non-tail occurrence of the pattern in the text and returns the character that
     * immediately follows that occurrence of the pattern.
     * 
     * @throws NoSuchElementException if there are no non-tail occurrences of the pattern in the text
     */
    public static char pickCharThatFollowsPattern (String text, String pattern, Random rand)
    {
        ArrayList<Character> charList = new ArrayList<Character>();
        charList = getCharsThatFollowPattern(text, pattern);
        if (charList.size() == 0)
        {
            throw new NoSuchElementException("No non-tail occurences of the pattern were found");
        }
        int randValue = rand.nextInt(charList.size());
        char randLetter = charList.get(randValue);
        return randLetter;
    }
}
