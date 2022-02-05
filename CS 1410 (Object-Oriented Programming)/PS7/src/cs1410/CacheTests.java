package cs1410;

import static org.junit.Assert.*;
import org.junit.Test;

public class CacheTests
{
    @Test(expected = IllegalArgumentException.class)
    public void testInfoLength1 ()
    {
        new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045\tExtraInfo:)");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInfoLength2 ()
    {
        new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850");
    }

    @Test
    public void testGcCode1 ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
        assertEquals("GCRQWK", c.getGcCode());
    }

    @Test
    public void testGcCode2 ()
    {
        Cache c = new Cache("GCNF2K\tWeirdTest Name\tI'mTheOwner\t1\t2.5\tN40 47.850\tW112 49.569");
        assertEquals("GCNF2K", c.getGcCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGcCode3 ()
    {
        new Cache("GCnF2K\tWeirdTest Name\tI'mTheOwner\t1\t2.5\tN40 47.850\tW112 49.569");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGcCode4 ()
    {
        new Cache("GCNF2k\tWeirdTest Name\tI'mTheOwner\t1\t2.5\tN40 47.850\tW112 49.569");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGcCode5 ()
    {
        new Cache("GC\tWeirdTest Name\tI'mTheOwner\t1\t2.5\tN40 47.850\tW112 49.569");
    }

    @Test
    public void testTitle1 ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
        assertEquals("Old Three Tooth", c.getTitle());
    }

    @Test
    public void testTitle2 ()
    {
        Cache c = new Cache("GCNF2K\tWeirdTest Name\tI'mTheOwner\t1\t2.5\tN40 47.850\tW112 49.569");
        assertEquals("WeirdTest Name", c.getTitle());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTitle3 ()
    {
        new Cache("GCRQWK\t    \tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
    }

    @Test
    public void testOwner1 ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
        assertEquals("geocadet", c.getOwner());
    }

    @Test
    public void testOwner2 ()
    {
        Cache c = new Cache("GCNF2K\tWeirdTest Name\tI'mTheOwner\t1\t2.5\tN40 47.850\tW112 49.569");
        assertEquals("I'mTheOwner", c.getOwner());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOwner3 ()
    {
        new Cache("GCNF2K\tWeirdTest Name\t   \t1\t2.5\tN40 47.850\tW112 49.569");
    }

    @Test
    public void testDifficulty1 ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
        assertEquals(3.5, c.getDifficulty(), 0.000000001);
    }

    @Test
    public void testDifficulty2 ()
    {
        Cache c = new Cache("GCNF2K\tWeirdTest Name\tI'mTheOwner\t1\t2.5\tN40 47.850\tW112 49.569");
        assertEquals(1.0, c.getDifficulty(), 0.000000001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDifficulty3 ()
    {
        new Cache("GCNF2K\tWeirdTest Name\tI'mTheOwner\t1.0001\t2.5\tN40 47.850\tW112 49.569");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDifficulty4 ()
    {
        new Cache("GCNF2K\tWeirdTest Name\tI'mTheOwner\t1.75\t2.5\tN40 47.850\tW112 49.569");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDifficulty5 ()
    {
        new Cache("GCNF2K\tWeirdTest Name\tI'mTheOwner\tWhy is there a string here?\t2.5\tN40 47.850\tW112 49.569");
    }

    @Test
    public void testTerrain1 ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
        assertEquals(3.0, c.getTerrain(), 0.000000001);
    }

    @Test
    public void testTerrain2 ()
    {
        Cache c = new Cache("GCNF2K\tWeirdTest Name\tI'mTheOwner\t1\t2.5\tN40 47.850\tW112 49.569");
        assertEquals(2.5, c.getTerrain(), 0.000000001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTerrain3 ()
    {
        new Cache("GCNF2K\tWeirdTest Name\tI'mTheOwner\t1\t2.50001\tN40 47.850\tW112 49.569");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTerrain4 ()
    {
        new Cache("GCNF2K\tWeirdTest Name\tI'mTheOwner\t1\t2.75\tN40 47.850\tW112 49.569");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTerrain5 ()
    {
        new Cache("GCNF2K\tWeirdTest Name\tI'mTheOwner\t1\tWhy's there ANother String here??\tN40 47.850\tW112 49.569");
    }

    @Test
    public void testLatitude1 ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
        assertEquals("N40 45.850", c.getLatitude());
    }

    @Test
    public void testLatitude2 ()
    {
        Cache c = new Cache("GCNF2K\tWeirdTest Name\tI'mTheOwner\t1\t2.5\tN40 47.850\tW112 49.569");
        assertEquals("N40 47.850", c.getLatitude());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLatitude3 ()
    {
        new Cache("GCNF2K\tWeirdTest Name\tI'mTheOwner\t1\t2.5\t          \tW112 49.569");
    }

    @Test
    public void testLongitude1 ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
        assertEquals("W111 48.045", c.getLongitude());
    }

    @Test
    public void testLongitude2 ()
    {
        Cache c = new Cache("GCNF2K\tWeirdTest Name\tI'mTheOwner\t1\t2.5\tN40 47.850\tW112 49.569");
        assertEquals("W112 49.569", c.getLongitude());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLongitude3 ()
    {
        new Cache("GCNF2K\tWeirdTest Name\tI'mTheOwner\t1\t2.5\tN40 47.850\t  ");
    }

    @Test
    public void testToString1 ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
        assertEquals("Old Three Tooth by geocadet", c.toString());
    }

    @Test
    public void testToString2 ()
    {
        Cache c = new Cache("GCNF2K\tWeirdTest Name\tI'mTheOwner\t1\t2.5\tN40 47.850\tW112 49.569");
        assertEquals("WeirdTest Name by I'mTheOwner", c.toString());
    }
}
