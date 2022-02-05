package cs1410;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import org.junit.Test;

public class PS5LibraryTests
{
    /**
     * This checks that the last line of the result ends with a newline, even if the last line in the scanner didn't.
     */
    @Test
    public void testScannerToString1 ()
    {
        assertEquals("This\nis a\ntest\n", PS5Library.scannerToString(new Scanner("This\nis a\ntest")));
    }

    @Test
    public void testScannerToString2 ()
    {
        assertEquals("", PS5Library.scannerToString(new Scanner("")));
        assertEquals("Testing whether this\nmethod works\nor not\n",
                PS5Library.scannerToString(new Scanner("Testing whether this\nmethod works\nor not")));
    }

    /**
     * This illustrates a way to do tests of methods that have a randomized behavior. When we ask for a randomly chosen
     * substring of length 4 of "abcde", about half the time we should get "abcd" and about half the time we should get
     * "bcde". So we call chooseSubstring 1000 times and count how many times we get each possibility. (If we get
     * anything else back, we immediately fail the test case.) Then we assert that we got each "about" half the time. It
     * is possible for a correct implementation to fail this test if we get extremely unlucky, but that is extremely
     * unlikely.
     */
    @Test
    public void testChooseSubstring1 ()
    {
        Random rand = new Random();
        int abcd = 0;
        int bcde = 0;

        for (int i = 0; i < 1000; i++)
        {
            String substring = PS5Library.chooseSubstring("abcde", 4, rand);
            if (substring.equals("abcd"))
            {
                abcd++;
            }
            else if (substring.equals("bcde"))
            {
                bcde++;
            }
            else
            {
                fail();
            }
        }

        assertTrue(400 <= abcd && abcd <= 600 && 400 <= bcde && bcde <= 600);
    }

    @Test
    public void testChooseSubstring2 ()
    {
        Random rand = new Random();
        int Hell = 0;
        int ello = 0;

        for (int i = 0; i < 1000; i++)
        {
            String substring = PS5Library.chooseSubstring("Hello", 4, rand);
            if (substring.equals("Hell"))
            {
                Hell++;
            }
            else if (substring.equals("ello"))
            {
                ello++;
            }
            else
            {
                fail();
            }
        }

        assertTrue(400 <= Hell && Hell <= 600 && 400 <= ello && ello <= 600);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChooseSubstring3 ()
    {
        PS5Library.chooseSubstring("hello", -1, new Random());
        PS5Library.chooseSubstring("hello", 6, new Random());
    }

    /**
     * This illustrates how to make assertions about ArrayLists.
     */
    @Test
    public void testGetCharsThatFollowPattern1 ()
    {
        ArrayList<Character> list = new ArrayList<Character>();
        list.add('b');
        list.add('b');
        assertEquals(list, PS5Library.getCharsThatFollowPattern("abababa", "aba"));
    }

    @Test
    public void testGetCharsThatFollowPattern2 ()
    {
        ArrayList<Character> list = new ArrayList<Character>();
        list.add('g');
        list.add('g');
        list.add('e');
        list.add('k');
        assertEquals(list, PS5Library.getCharsThatFollowPattern("testgsjktestgjskgjtestetslgtestkahsldtest", "test"));
    }

    @Test
    public void testGetCharsThatFollowPattern3 ()
    {
        ArrayList<Character> list = new ArrayList<Character>();
        assertEquals(list, PS5Library.getCharsThatFollowPattern("TestingStringForPattern", "vs"));
    }

    /**
     * This illustrates how to test that an exception is thrown when one is supposed to be thrown. If
     * pickCharThatFollowsPattern doesn't thrown the right kind of exception, the test will fail.
     */
    @Test(expected = NoSuchElementException.class)
    public void testPickCharThatFollowsPattern1 ()
    {
        PS5Library.pickCharThatFollowsPattern("hello", "o", new Random());
    }

    @Test
    public void testPickCharThatFollowsPattern2 ()
    {
        int wLetter = 0;
        int iLetter = 0;
        int tLetter = 0;
        int lLetter = 0;

        for (int i = 0; i < 1000; i++)
        {
            char letter = PS5Library.pickCharThatFollowsPattern("helloweirdoinghotimeholyhello", "o", new Random());
            if (letter == 'w')
            {
                wLetter++;
            }
            else if (letter == 'i')
            {
                iLetter++;
            }
            else if (letter == 't')
            {
                tLetter++;
            }
            else if (letter == 'l')
            {
                lLetter++;
            }
            else
            {
                fail();
            }
        }

        assertTrue(200 <= wLetter && wLetter <= 300 && 200 <= iLetter && iLetter <= 300 && 200 <= tLetter
                && tLetter <= 300 && 200 <= lLetter && lLetter <= 300);
    }

}
