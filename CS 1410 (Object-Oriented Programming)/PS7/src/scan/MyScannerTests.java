package scan;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;

public class MyScannerTests
{
    @Test
    public void testHasNext1 ()
    {
        MyScanner s = new MyScanner("TEsting test test test test");
        assertEquals(true, s.hasNext());
    }

    @Test
    public void testHasNext2 ()
    {
        MyScanner s = new MyScanner("Test");
        assertEquals(true, s.hasNext());
    }

    @Test
    public void testHasNext3 ()
    {
        MyScanner s = new MyScanner("");
        assertEquals(false, s.hasNext());
    }

    @Test
    public void testNext1 ()
    {
        MyScanner s = new MyScanner("TEsting test1 test2 test3 test4");
        assertEquals("TEsting", s.next());
        assertEquals("test1", s.next());
        assertEquals("test2", s.next());
        assertEquals("test3", s.next());
        assertEquals("test4", s.next());
    }

    @Test
    public void testNext2 ()
    {
        MyScanner s = new MyScanner("I was born around 2000");
        assertEquals("I", s.next());
        s.next();
        s.next();
        s.next();
        assertEquals("2000", s.next());
    }

    @Test
    public void testNext3 ()
    {
        MyScanner s = new MyScanner("Testing Whether a Scanner works in 2018 ?");
        assertEquals("Testing", s.next());
        s.next();
        s.next();
        s.next();
        assertEquals("works", s.next());
        s.next();
        assertEquals("2018", s.next());
        assertEquals("?", s.next());
    }

    @Test(expected = NoSuchElementException.class)
    public void testNext4 ()
    {
        MyScanner s = new MyScanner("TEsting test1 test2 test3 test4");
        for (int i = 1; i < 7; i++)
        {
            s.next();
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testNext5 ()
    {
        MyScanner s = new MyScanner("");
        s.next();
    }

    @Test
    public void testHasNextInt1 ()
    {
        MyScanner s = new MyScanner("75 test test test test");
        assertEquals(true, s.hasNext());
    }

    @Test
    public void testHasNextInt2 ()
    {
        MyScanner s = new MyScanner("75 test test 654 test");
        s.next();
        s.next();
        s.next();
        assertEquals(true, s.hasNextInt());
    }

    @Test
    public void testHasNextInt3 ()
    {
        MyScanner s = new MyScanner("75 test test 654 test");
        s.next();
        assertEquals(false, s.hasNextInt());
    }

    @Test
    public void testHasNextInt4 ()
    {
        MyScanner s = new MyScanner("");
        assertEquals(false, s.hasNextInt());
    }

    @Test
    public void testNextInt1 ()
    {
        MyScanner s = new MyScanner("TEsting test1 17 test3 test4");
        s.next();
        s.next();
        assertEquals(17, s.nextInt());
    }

    @Test
    public void testNextInt2 ()
    {
        MyScanner s = new MyScanner("I was born around 2000");
        s.next();
        s.next();
        s.next();
        s.next();
        assertEquals(2000, s.nextInt());
    }

    @Test
    public void testNextInt3 ()
    {
        MyScanner s = new MyScanner("Testing Whether 8 Scanners works in 2018 ?");
        s.next();
        s.next();
        assertEquals(8, s.nextInt());
        s.next();
        s.next();
        s.next();
        assertEquals(2018, s.nextInt());
    }

    @Test(expected = NoSuchElementException.class)
    public void testNextInt4 ()
    {
        MyScanner s = new MyScanner("145");
        s.nextInt();
        s.nextInt();
    }

    @Test(expected = InputMismatchException.class)
    public void testNextInt5 ()
    {
        MyScanner s = new MyScanner("");
        s.nextInt();
    }

    @Test(expected = InputMismatchException.class)
    public void testNextInt6 ()
    {
        MyScanner s = new MyScanner("10000000000");
        s.nextInt();
    }

    @Test(expected = InputMismatchException.class)
    public void testNextInt7 ()
    {
        MyScanner s = new MyScanner("-10000000000");
        s.nextInt();
    }

    @Test(expected = InputMismatchException.class)
    public void testNextInt8 ()
    {
        MyScanner s = new MyScanner("Probably Not An Int 8t");
        s.next();
        s.next();
        s.next();
        s.next();
        s.nextInt();
    }
}
