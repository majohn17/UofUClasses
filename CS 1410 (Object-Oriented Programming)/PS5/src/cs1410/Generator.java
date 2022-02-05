package cs1410;

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import cs1410lib.Dialogs;

/**
 * This class is used to generate random text similar to the generate text method in the PS5Library class, except it
 * takes user input to generate the text rather than have predefinied values.
 * 
 * @author Matthew Johnsen
 */
public class Generator
{
    /**
     * Uses the generate text method and the user input that is acquired to generate random text from a file.
     */
    public static void main (String[] args) throws IOException
    {
        int level = getIntValue(1);
        if (level == -1)
        {
            return;
        }

        int length = getIntValue(2);
        if (length == -1)
        {
            return;
        }
        File textFile = getTextFile();

        if (textFile == null)
        {
            return;
        }
        int temp = testFileLength(textFile, level);

        if (temp == -1)
        {
            Dialogs.showMessageDialog(
                    "Sorry, text file is too short for your analysis level. (less than level + 1 characters). \nLevel: "
                            + level + "\nFile Length: " + textFile.length());
            return;
        }

        Scanner input = new Scanner(textFile);
        String randomText = generateText(input, level, length);
        Dialogs.showMessageDialog(randomText);
        System.out.print(randomText);
    }

    public static String generateText (Scanner input, int level, int length)
    {
        // Validate the parameters
        if (level < 0 || length < 0)
        {
            throw new IllegalArgumentException();
        }

        // Grab all the text from the Scanner and make sure it is long enough.
        String text = PS5Library.scannerToString(input);
        if (level >= text.length())
        {
            throw new IllegalArgumentException();
        }

        // Create a random number generator to pass to the methods that make random choices
        Random rand = new Random();

        // Get the initial pattern.
        String pattern = PS5Library.chooseSubstring(text, level, rand);

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
                char newChar = PS5Library.pickCharThatFollowsPattern(text, pattern, rand);
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
                pattern = PS5Library.chooseSubstring(text, level, rand);
            }
        }

        // Return the string we've accumulated.
        return result.toString();
    }

    /**
     * This method takes a user's input and ensures that it is a integer that is 0 or greater and returns that integer
     * to be used as the analysis level in the randomly generated text, or returns -1 if the dialog is cancelled. It
     * also takes an int as a parameter to determine what string to use to inform the user which value he is entering
     * the int for (analysis level or length).
     */
    public static int getIntValue (int choice)
    {
        boolean isSuccessful = false;
        int value = -1;
        String dialogMessage = "";
        // Analysis Level
        if (choice == 1)
        {
            dialogMessage = "Enter your desired analysis level (positive integer values or 0):";
        }
        // Length
        if (choice == 2)
        {
            dialogMessage = "Enter your desired length (positive integer values or 0):";
        }
        while (isSuccessful == false)
        {
            try
            {
                String input = Dialogs.showInputDialog(dialogMessage);
                if (input == null)
                {
                    return -1;
                }
                value = Integer.parseInt(input);
                if (value < 0)
                {
                    throw new IllegalArgumentException();
                }
                isSuccessful = true;
            }
            catch (NumberFormatException e)
            {
                Dialogs.showMessageDialog("You entered and invalid analysis level. Please try again.");
            }
            catch (IllegalArgumentException e)
            {
                Dialogs.showMessageDialog("You entered a negative integer. Please try again.");
            }
        }
        return value;
    }

    /**
     * This method asks a user to select a file, and if the file they selected is a folder rather than a file, it
     * reprompts them to select a new file. It also ends the program if the dialog is cancelled.
     */
    public static File getTextFile ()
    {
        File textDoc = null;
        boolean isSuccessful = false;
        while (isSuccessful == false)
        {
            try
            {
                textDoc = Dialogs.showOpenFileDialog("Select a Text File:");
                if (textDoc.isFile() == false)
                {
                    throw new FileNotFoundException();
                }
                isSuccessful = true;
            }
            catch (FileNotFoundException e)
            {
                Dialogs.showMessageDialog("Invalid file. Please select a valid text file.");
            }
            catch (NullPointerException e)
            {
                return null;
            }
        }
        return textDoc;
    }

    /**
     * This method takes a file and ensures that the length of the file is greater than the level. The method returns -1
     * if the file is not long enough, and 0 otherwise
     */
    public static int testFileLength (File textDoc, int level)
    {
        int value = -1;
        if (textDoc.length() < (level + 1))
        {
            return -1;
        }
        value = 0;
        return value;
    }
}
