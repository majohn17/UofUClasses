package c4;

import static org.junit.Assert.*;
import org.junit.Test;

public class C4Tests
{
    @Test(expected = IllegalArgumentException.class)
    public void testConstructor1 ()
    {
        new C4Board(3, 12);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor2 ()
    {
        new C4Board(4, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor3 ()
    {
        new C4Board(-1, 12);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOccupant1 ()
    {
        C4Board test = new C4Board(5, 12);
        test.getOccupant(5, 12);
    }

    @Test
    public void testGetOccupant2 ()
    {
        C4Board test = new C4Board(4, 4);
        test.moveTo(3);
        test.moveTo(3);
        test.moveTo(3);
        assertEquals(0, test.getOccupant(3, 3));
        test.moveTo(3);
        assertEquals(2, test.getOccupant(3, 3));
    }

    @Test
    public void testNewGame ()
    {
        C4Board test = new C4Board(4, 4);
        test.newGame();
        assertEquals(2, test.getToMove());
        test.newGame();
        assertEquals(1, test.getToMove());
        assertEquals(0, test.getWinsForP1());
        assertEquals(0, test.getWinsForP2());
        assertEquals(0, test.getTies());
    }

    @Test
    public void testCheckFull ()
    {
        C4Board test = new C4Board(4, 4);
        assertEquals(false, test.checkFullBoard());
    }

    @Test
    public void testCheckFull2 ()
    {
        C4Board test = new C4Board(4, 4);
        test.moveTo(3);
        test.moveTo(2);
        test.moveTo(1);
        test.moveTo(0);
        test.moveTo(0);
        test.moveTo(1);
        test.moveTo(2);
        test.moveTo(3);
        test.moveTo(3);
        test.moveTo(2);
        test.moveTo(1);
        test.moveTo(0);
        test.moveTo(3);
        test.moveTo(2);
        test.moveTo(1);
        assertEquals(false, test.checkFullBoard());
        test.moveTo(0);
        assertEquals(true, test.checkFullBoard());
    }

    @Test
    public void testCheckColumn1 ()
    {
        C4Board test = new C4Board(4, 4);
        test.moveTo(0);
        test.moveTo(0);
        test.moveTo(0);
        assertEquals(3, test.checkColumn(0));
        test.moveTo(0);
        assertEquals(-1, test.checkColumn(0));
    }

    @Test
    public void testCheckColumn2 ()
    {
        C4Board test = new C4Board(4, 4);
        assertEquals(0, test.checkColumn(3));
        test.moveTo(3);
        assertEquals(1, test.checkColumn(3));
        test.moveTo(3);
        assertEquals(2, test.checkColumn(3));
        test.moveTo(3);
        assertEquals(3, test.checkColumn(3));
        test.moveTo(3);
        assertEquals(-1, test.checkColumn(3));
    }

    @Test
    public void testGame1 ()
    {
        C4Board b = new C4Board(6, 7);
        b.moveTo(6);
        b.moveTo(5);
        b.moveTo(4);
        b.moveTo(6);
        b.moveTo(5);
        b.moveTo(4);
        assertEquals(2, b.getOccupant(0, 5));
        assertTrue(b.checkFullBoard() == false);
        b.moveTo(6);
        b.moveTo(5);
        b.moveTo(6);
        b.moveTo(5);
        b.moveTo(6);
        b.moveTo(5);
        b.moveTo(6);
        assertEquals(1, b.getWinsForP1());
        b.newGame();
        assertEquals(2, b.getToMove());
    }

    @Test
    public void testGame2 ()
    {
        C4Board b = new C4Board(6, 7);
        b.moveTo(6);
        b.moveTo(5);
        b.moveTo(4);
        b.moveTo(3);
        b.moveTo(2);
        b.moveTo(1);
        b.moveTo(0);
        assertEquals(0, b.getOccupant(1, 3));
        assertTrue(b.checkFullBoard() == false);
        b.moveTo(0);
        b.moveTo(1);
        b.moveTo(2);
        b.moveTo(3);
        b.moveTo(4);
        b.moveTo(5);
        b.moveTo(6);
        assertEquals(0, b.getWinsForP1());
        b.moveTo(6);
        b.moveTo(5);
        b.moveTo(4);
        b.moveTo(3);
        b.moveTo(2);
        b.moveTo(1);
        b.moveTo(0);
        assertEquals(2, b.getToMove());
        b.moveTo(0);
        assertEquals(1, b.getWinsForP2());
        b.newGame();
        b.moveTo(6);
        b.moveTo(5);
        b.moveTo(6);
        b.moveTo(5);
        b.moveTo(6);
        b.moveTo(5);
        b.moveTo(6);
        assertEquals(2, b.getWinsForP2());
        b.newGame();
        assertEquals(1, b.getToMove());
    }

    @Test
    public void testGame3 ()
    {
        C4Board b = new C4Board(5, 4);
        b.moveTo(3);
        b.moveTo(2);
        b.moveTo(1);
        b.moveTo(0);
        assertEquals(1, b.getOccupant(0, 3));
        b.moveTo(0);
        b.moveTo(1);
        b.moveTo(2);
        b.moveTo(3);
        b.moveTo(3);
        b.moveTo(2);
        b.moveTo(1);
        b.moveTo(0);
        assertEquals(1, b.getToMove());
        b.moveTo(3);
        b.moveTo(2);
        b.moveTo(1);
        b.moveTo(0);
        b.moveTo(3);
        b.moveTo(2);
        b.moveTo(1);
        b.moveTo(0);
        assertEquals(1, b.getTies());
        b.newGame();
        assertEquals(2, b.getToMove());
        b.moveTo(0);
        b.moveTo(1);
        b.moveTo(0);
        b.moveTo(1);
        b.moveTo(0);
        b.moveTo(1);
        b.moveTo(0);
        assertEquals(1, b.getTies());
        assertEquals(1, b.getWinsForP2());
        b.newGame();
        assertEquals(1, b.getToMove());
    }
}
