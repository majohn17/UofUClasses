package rational;

import static org.junit.Assert.*;
import java.math.BigInteger;
import org.junit.Test;

public class BigRatTests
{
    @Test
    public void testConstructor1 ()
    {
        BigRat r = new BigRat();
        assertEquals("0", r.toString());
    }

    @Test
    public void testConstructor2 ()
    {
        BigRat r = new BigRat(5);
        assertEquals("5", r.toString());
    }

    @Test
    public void testConstructor3 ()
    {
        BigRat r1 = new BigRat(1, 2);
        assertEquals("1/2", r1.toString());

        BigRat r2 = new BigRat(5, 15);
        assertEquals("1/3", r2.toString());

        BigRat r3 = new BigRat(-4, -2);
        assertEquals("2", r3.toString());

        BigRat r4 = new BigRat(6, -8);
        assertEquals("-3/4", r4.toString());

        BigRat r5 = new BigRat(3, -15);
        assertEquals("-1/5", r5.toString());

        try
        {
            new BigRat(4, 0);
            fail("No exception thrown");
        }
        catch (IllegalArgumentException e)
        {
        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorException ()
    {
        new BigRat(1, 0);
    }

    @Test
    public void testAdd1 ()
    {
        BigRat r1 = new BigRat(2, 5);
        BigRat r2 = new BigRat(3, 4);
        assertEquals("23/20", r1.add(r2).toString());

        r1 = new BigRat(1, 7);
        r2 = new BigRat(-1, 5);
        assertEquals("-2/35", r1.add(r2).toString());
    }

    @Test
    public void testAdd2 ()
    {
        BigRat r1 = new BigRat(4, 9);
        BigRat r2 = new BigRat(11, 13);
        assertEquals("151/117", r1.add(r2).toString());

        r1 = new BigRat(-3, 8);
        r2 = new BigRat(-11, 9);
        assertEquals("-115/72", r1.add(r2).toString());
    }

    @Test
    public void testSub1 ()
    {
        BigRat r1 = new BigRat(2, 5);
        BigRat r2 = new BigRat(3, 4);
        assertEquals("-7/20", r1.sub(r2).toString());

        r1 = new BigRat(1, 7);
        r2 = new BigRat(-1, 5);
        assertEquals("12/35", r1.sub(r2).toString());
    }

    @Test
    public void testSub2 ()
    {
        BigRat r1 = new BigRat(1, 6);
        BigRat r2 = new BigRat(1, 7);
        assertEquals("1/42", r1.sub(r2).toString());

        r1 = new BigRat(4, 7);
        r2 = new BigRat(9, 10);
        assertEquals("-23/70", r1.sub(r2).toString());
    }

    @Test
    public void testMul1 ()
    {
        BigRat r1 = new BigRat(2, 5);
        BigRat r2 = new BigRat(3, 4);
        assertEquals("3/10", r1.mul(r2).toString());

        r1 = new BigRat(1, 7);
        r2 = new BigRat(-1, 5);
        assertEquals("-1/35", r1.mul(r2).toString());
    }

    @Test
    public void testMul2 ()
    {
        BigRat r1 = new BigRat(1, 7);
        BigRat r2 = new BigRat(9, 10);
        assertEquals("9/70", r1.mul(r2).toString());

        r1 = new BigRat(3, 5);
        r2 = new BigRat(6, -5);
        assertEquals("-18/25", r1.mul(r2).toString());
    }

    @Test
    public void testDiv1 ()
    {
        BigRat r1 = new BigRat(2, 5);
        BigRat r2 = new BigRat(3, 4);
        assertEquals("8/15", r1.div(r2).toString());

        r1 = new BigRat(1, 7);
        r2 = new BigRat(-1, 5);
        assertEquals("-5/7", r1.div(r2).toString());

        try
        {
            r1 = new BigRat(3, 4);
            r2 = new BigRat(0);
            r1.div(r2);
            fail("No exception thrown");
        }
        catch (IllegalArgumentException e)
        {
        }
    }

    @Test
    public void testDiv2 ()
    {
        BigRat r1 = new BigRat(3, 10);
        BigRat r2 = new BigRat(5, 6);
        assertEquals("9/25", r1.div(r2).toString());

        r1 = new BigRat(2, 5);
        r2 = new BigRat(1, -9);
        assertEquals("-18/5", r1.div(r2).toString());

        try
        {
            r1 = new BigRat(new BigInteger("1823948712938471982374891723894789123748971289341"),
                    new BigInteger("80592834058203485028304589238409580234850923049520934850283405"));
            r2 = new BigRat();
            r1.div(r2);
            fail("No exception thrown");
        }
        catch (IllegalArgumentException e)
        {
        }
    }

    @Test
    public void testCompareTo1 ()
    {
        BigRat r1 = new BigRat(3, 4);
        BigRat r2 = new BigRat(6, 8);
        BigRat r3 = new BigRat(1, 2);
        assertEquals(0, r1.compareTo(r2));
        assertTrue(r1.compareTo(r3) > 0);
        assertTrue(r3.compareTo(r1) < 0);
    }

    @Test
    public void testCompareTo2 ()
    {
        BigRat r1 = new BigRat(new BigInteger("11111111111111111111"), new BigInteger("111111111111111111110"));
        BigRat r2 = new BigRat(1, 10);
        BigRat r3 = new BigRat(1, 9);
        BigRat r4 = new BigRat(1, 11);
        assertEquals(0, r1.compareTo(r2));
        assertTrue(r1.compareTo(r3) < 0);
        assertTrue(r3.compareTo(r1) > 0);
        assertEquals(1, r1.compareTo(r4));
    }

    @Test
    public void testBigExample ()
    {
        BigRat r1 = new BigRat(new BigInteger("987876765674565435423544657879080989878678676"),
                new BigInteger("89787676564654675786897988908987786785764654645675"));
        BigRat r2 = r1.div(r1);
        assertEquals("1", r2.toString());
    }
}
