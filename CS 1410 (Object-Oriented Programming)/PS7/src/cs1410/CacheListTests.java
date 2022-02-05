package cs1410;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.Test;

public class CacheListTests
{
    @Test
    public void test1 () throws IOException
    {
        CacheList clist = new CacheList(new Scanner(
                "GCABCD\tAs The World Turns\tbunny\t1\t1\tN40 45.875\tW111 48.986\nGCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045\n"));
        clist.setTitleConstraint("Turns");
        ArrayList<Cache> selected = clist.select();
        assertEquals(1, selected.size());
        assertEquals("GCABCD", selected.get(0).getGcCode());
    }

    @Test
    public void test2 () throws IOException
    {
        CacheList clist = new CacheList(new Scanner(
                "GCABCD\tAs The World Turns\tbunny\t1\t1\tN40 45.875\tW111 48.986\nGCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045\n"));
        clist.setOwnerConstraint("geocadet");
        ArrayList<Cache> selected = clist.select();
        assertEquals(1, selected.size());
        assertEquals("GCRQWK", selected.get(0).getGcCode());
    }

    @Test
    public void test3 () throws IOException
    {
        CacheList clist = new CacheList(new Scanner(
                "GCABCD\tAs The World Turns\tbunny\t1\t1\tN40 45.875\tW111 48.986\nGCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045\n"));
        clist.setDifficultyConstraints(3.5, 5.0);
        ArrayList<Cache> selected = clist.select();
        assertEquals(1, selected.size());
        assertEquals("GCRQWK", selected.get(0).getGcCode());
    }

    @Test
    public void test4 () throws IOException
    {
        CacheList clist = new CacheList(new Scanner(
                "GCABCD\tAs The World Turns\tbunny\t1\t1\tN40 45.875\tW111 48.986\nGCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045\n"));
        clist.setTerrainConstraints(1.0, 2.5);
        ArrayList<Cache> selected = clist.select();
        assertEquals(1, selected.size());
        assertEquals("GCABCD", selected.get(0).getGcCode());
    }

    @Test
    public void test5 () throws IOException
    {
        CacheList clist = new CacheList(new Scanner(
                "GCABCD\tAs The World Turns\tbunny\t1\t1\tN40 45.875\tW111 48.986\nGCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045\nGCXXDS\tAs The World Turns2\tbunny\t2\t5\tN40 45.875\tW111 48.986"));
        clist.setOwnerConstraint("bunny");
        clist.setDifficultyConstraints(1.0, 2.5);
        ArrayList<Cache> selected = clist.select();
        assertEquals(2, selected.size());
        assertEquals("GCXXDS", selected.get(1).getGcCode());
    }

    @Test
    public void test6 () throws IOException
    {
        CacheList clist = new CacheList(new Scanner(
                "GC1111\tTest\tTest\t2\t3\tfake\tfake\nGC1112\tTets\tTest\t2\t3\tfake\tfake\nGC1113\tTesting\tTest\t2\t3\tfake\tfake\n"));
        clist.setTitleConstraint("Test");
        clist.setDifficultyConstraints(1.0, 2.5);
        ArrayList<Cache> selected = clist.select();
        assertEquals(2, selected.size());
        assertEquals("GC1111", selected.get(0).getGcCode());
    }

    @Test
    public void test7 () throws IOException
    {
        CacheList clist = new CacheList(new Scanner(
                "GC1111\tTest\tTest\t2\t3\tfake\tfake\nGC1112\tTets\tTest\t2\t5\tfake\tfake\nGC1113\tTesting\tTest\t2\t4\tfake\tfake\n"));
        clist.setOwnerConstraint("Test");
        clist.setTerrainConstraints(3.5, 5.0);
        ArrayList<Cache> selected = clist.select();
        assertEquals(2, selected.size());
        assertEquals("GC1112", selected.get(1).getGcCode());
    }

    @Test
    public void test8 () throws IOException
    {
        CacheList clist = new CacheList(new Scanner(
                "GC1111\tTest\tTest\t2\t3\tfake\tfake\nGC1112\tTets\tTest\t2\t5\tfake\tfake\nGC1113\tTesting\tTest\t1\t4\tfake\tfake\n"));
        clist.setOwnerConstraint("Test");
        clist.setDifficultyConstraints(1.0, 1.5);
        ArrayList<Cache> selected = clist.select();
        assertEquals(1, selected.size());
        assertEquals("GC1113", selected.get(0).getGcCode());
    }

    @Test
    public void testGetOwners1 () throws IOException
    {
        CacheList clist = new CacheList(new Scanner(
                "GCABCD\tAs The World Turns\tbunny\t1\t1\tN40 45.875\tW111 48.986\nGCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045\n"));
        ArrayList<String> owners = clist.getOwners();
        assertEquals(2, owners.size());
        assertEquals("geocadet", owners.get(1));
    }

    @Test
    public void testGetOwners2 () throws IOException
    {
        CacheList clist = new CacheList(new Scanner(
                "GCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tS\tB\t1\t1\tLat\tLong\nGCAAAA\tD\tA\t1\t1\tLat\tLong\n"));
        ArrayList<String> owners = clist.getOwners();
        assertEquals(2, owners.size());
        assertEquals("B", owners.get(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException1 () throws IOException
    {
        new CacheList(new Scanner(
                "GCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tS\tB\t1\t1\tLat\tLong\nGCAAAn\tD\tA\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\n"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException2 () throws IOException
    {
        new CacheList(new Scanner(
                "GCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tS\tB\t1\t1\tLat\tLong\nGCAAAA\tD\tA\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\t          \t1\t1\tLat\tLong\nGCAAAA\t\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\n"));
    }

    @Test
    public void testExceptionMessage1 () throws IOException
    {
        try
        {
            new CacheList(new Scanner(
                    "GCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tS\tB\t1\t1\tLat\tLong\nGCAAAn\tD\tA\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\n"));
        }
        catch (IllegalArgumentException e)
        {
            assertEquals("3", e.getMessage());
        }
    }

    @Test
    public void testExceptionMessage2 () throws IOException
    {
        try
        {
            new CacheList(new Scanner(
                    "GCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tS\tB\t1\t1\tLat\tLong\nGCAAAA\tD\tA\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\t          \t1\t1\tLat\tLong\nGCAAAA\t\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\nGCAAAA\tA\tB\t1\t1\tLat\tLong\n"));
        }
        catch (IllegalArgumentException e)
        {
            assertEquals("17", e.getMessage());
        }
    }
}
