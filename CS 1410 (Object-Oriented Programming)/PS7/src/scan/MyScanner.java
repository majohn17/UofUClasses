package scan;

import java.util.NoSuchElementException;
import java.util.InputMismatchException;

/**
 * This class acts the exact same a scanner which has a string parameter
 */
public class MyScanner
{
    // Instance Variables
    private String[] tokens;
    private int position;

    // Constructor
    public MyScanner (String text)
    {
        this.tokens = text.trim().split("\\s+");
        this.position = -1;
    }

    /**
     * Returns true if the scanner has another token and returns false otherwise.
     */
    public boolean hasNext ()
    {
        if (this.position + 1 < this.tokens.length)
        {
            if (this.tokens.length == 1 && this.tokens[0] == "")
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns the next token in the scanner if it has one.
     */
    public String next ()
    {
        this.position++;
        if (this.position >= this.tokens.length || this.tokens.length == 1 && this.tokens[0] == "")
        {
            throw new NoSuchElementException();
        }
        else
        {
            return this.tokens[this.position];
        }
    }

    /**
     * Returns true if the next token in the scanner is an int and returns false otherwise.
     */
    public boolean hasNextInt ()
    {
        try
        {
            Integer.parseInt(this.tokens[this.position + 1]);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    /**
     * Returns and in if the next token in the scanner is an int and throws an exception otherwise.
     */
    public int nextInt ()
    {
        this.position++;
        if (this.position >= this.tokens.length)
        {
            throw new NoSuchElementException();
        }
        else
        {
            try
            {
                return Integer.parseInt(this.tokens[this.position]);
            }
            catch (NumberFormatException e)
            {
                throw new InputMismatchException();
            }
        }
    }
}
