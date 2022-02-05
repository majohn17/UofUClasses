package cs1410;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import org.junit.Test;
import java.awt.Color;
import java.awt.Graphics;

public class MyTestCases
{
    /**
     * One sample test case. Don't conclude that your implementation is perfect simply because it passes this test!
     */
    @Test
    public void testReadTable1 ()
    {
        try (Scanner scn = new Scanner(
                "Utah\t10\nNevada\t3\nUtah\t2\nCalifornia\t14\nArizona\t21\nUtah\t2\nCalifornia\t7\nCalifornia\t6\nNevada\t11\nCalifornia\t1\n"))
        {
            TreeMap<String, ArrayList<Double>> actual = GraphingMethods.readTable(scn);

            TreeMap<String, ArrayList<Double>> expected = new TreeMap<>();
            ArrayList<Double> azList = new ArrayList<>();
            azList.add(21.0);
            expected.put("Arizona", azList);

            ArrayList<Double> caList = new ArrayList<>();
            caList.add(14.0);
            caList.add(7.0);
            caList.add(6.0);
            caList.add(1.0);
            expected.put("California", caList);

            ArrayList<Double> nvList = new ArrayList<>();
            nvList.add(3.0);
            nvList.add(11.0);
            expected.put("Nevada", nvList);

            ArrayList<Double> utList = new ArrayList<>();
            utList.add(10.0);
            utList.add(2.0);
            utList.add(2.0);
            expected.put("Utah", utList);

            assertEquals(expected, actual);
        }
    }

    @Test
    public void testReadTable2 ()
    {
        try (Scanner scn = new Scanner(
                "Utah\t3\nNevada\t19\nUtah\t7\nCalifornia\t17\nArizona\t5\nUtah\t4\nCalifornia\t3\nCalifornia\t13\nNevada\t4\nCalifornia\t12\n"))
        {
            TreeMap<String, ArrayList<Double>> actual = GraphingMethods.readTable(scn);

            TreeMap<String, ArrayList<Double>> expected = new TreeMap<>();
            ArrayList<Double> azList = new ArrayList<>();
            azList.add(5.0);
            expected.put("Arizona", azList);

            ArrayList<Double> caList = new ArrayList<>();
            caList.add(17.0);
            caList.add(3.0);
            caList.add(13.0);
            caList.add(12.0);
            expected.put("California", caList);

            ArrayList<Double> nvList = new ArrayList<>();
            nvList.add(19.0);
            nvList.add(4.0);
            expected.put("Nevada", nvList);

            ArrayList<Double> utList = new ArrayList<>();
            utList.add(3.0);
            utList.add(7.0);
            utList.add(4.0);
            expected.put("Utah", utList);

            assertEquals(expected, actual);
        }
    }

    @Test
    public void testReadTable3 ()
    {
        try (Scanner scn = new Scanner(
                "Oregon\t3\nWyoming\t12\nMaine\t7\nOregon\t4\nColorado\t6\nWyoming\t9\nWyoming\t2\nColorado\t13\nMaine\t16\nOregon\t19\nWyoming\t18\n"))
        {
            TreeMap<String, ArrayList<Double>> actual = GraphingMethods.readTable(scn);

            TreeMap<String, ArrayList<Double>> expected = new TreeMap<>();
            ArrayList<Double> orList = new ArrayList<>();
            orList.add(3.0);
            orList.add(4.0);
            orList.add(19.0);
            expected.put("Oregon", orList);

            ArrayList<Double> wyList = new ArrayList<>();
            wyList.add(12.0);
            wyList.add(9.0);
            wyList.add(2.0);
            wyList.add(18.0);
            expected.put("Wyoming", wyList);

            ArrayList<Double> meList = new ArrayList<>();
            meList.add(7.0);
            meList.add(16.0);
            expected.put("Maine", meList);

            ArrayList<Double> coList = new ArrayList<>();
            coList.add(6.0);
            coList.add(13.0);
            expected.put("Colorado", coList);

            assertEquals(expected, actual);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadTable4 ()
    {
        Scanner scn = new Scanner("");
        GraphingMethods.readTable(scn);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadTable5 ()
    {
        Scanner scn = new Scanner("Oregon 6\nTexas\t7\nOregon\t7\nWashington\t9\n");
        GraphingMethods.readTable(scn);
    }

    @Test
    public void testVerifyFormat ()
    {
        String temp = "Testing\t13";
        String temp2 = "Testing 13";
        String temp3 = " Test ing\t13";
        String temp4 = "Testing\t";
        String temp5 = "Testing\t15";
        String temp6 = "Testing\t\t18";
        assertEquals(true, GraphingMethods.verifyFormat(temp));
        assertEquals(false, GraphingMethods.verifyFormat(temp2));
        assertEquals(true, GraphingMethods.verifyFormat(temp3));
        assertEquals(false, GraphingMethods.verifyFormat(temp4));
        assertEquals(true, GraphingMethods.verifyFormat(temp5));
        assertEquals(false, GraphingMethods.verifyFormat(temp6));
    }

    @Test
    public void testContainsLine ()
    {
        Scanner scnr = new Scanner("");
        Scanner scnr2 = new Scanner("Testing\nTesting\n");
        Scanner scnr3 = new Scanner("Testing");
        assertEquals(false, GraphingMethods.containsLine(scnr));
        assertEquals(true, GraphingMethods.containsLine(scnr2));
        assertEquals(true, GraphingMethods.containsLine(scnr3));
    }

    @Test
    public void testPrepareGraph1 ()
    {
        try (Scanner scn = new Scanner(
                "Utah\t10\nNevada\t3\nUtah\t2\nCalifornia\t14\nArizona\t21\nUtah\t2\nCalifornia\t7\nCalifornia\t6\nNevada\t11\nCalifornia\t1\n"))
        {
            TreeMap<String, ArrayList<Double>> categoryMap = GraphingMethods.readTable(scn);
            TreeMap<String, Double> expected = new TreeMap<>();

            expected.put("Arizona", 21.00);

            expected.put("California", 14.00);

            expected.put("Nevada", 11.00);

            expected.put("Utah", 10.00);

            TreeMap<String, Double> actual = GraphingMethods.prepareGraph(categoryMap, 0);

            assertEquals(expected, actual);
        }
    }

    @Test
    public void testPrepareGraph2 ()
    {
        try (Scanner scn = new Scanner(
                "Utah\t10\nNevada\t3\nUtah\t2\nCalifornia\t14\nArizona\t21\nUtah\t2\nCalifornia\t7\nCalifornia\t6\nNevada\t11\nCalifornia\t1\n"))
        {
            TreeMap<String, ArrayList<Double>> categoryMap = GraphingMethods.readTable(scn);
            TreeMap<String, Double> expected = new TreeMap<>();

            expected.put("Arizona", 21.00);

            expected.put("California", 1.00);

            expected.put("Nevada", 3.00);

            expected.put("Utah", 2.00);

            TreeMap<String, Double> actual = GraphingMethods.prepareGraph(categoryMap, 1);

            assertEquals(expected, actual);
        }
    }

    @Test
    public void testPrepareGraph3 ()
    {
        try (Scanner scn = new Scanner(
                "Utah\t10\nNevada\t3\nUtah\t2\nCalifornia\t14\nArizona\t21\nUtah\t2\nCalifornia\t7\nCalifornia\t6\nNevada\t11\nCalifornia\t1\n"))
        {
            TreeMap<String, ArrayList<Double>> categoryMap = GraphingMethods.readTable(scn);
            TreeMap<String, Double> expected = new TreeMap<>();

            expected.put("Arizona", 21.00);

            expected.put("California", 28.00);

            expected.put("Nevada", 14.00);

            expected.put("Utah", 14.00);

            TreeMap<String, Double> actual = GraphingMethods.prepareGraph(categoryMap, 2);

            assertEquals(expected, actual);
        }
    }

    @Test
    public void testPrepareGraph4 ()
    {
        try (Scanner scn = new Scanner(
                "Utah\t10\nNevada\t3\nUtah\t2\nCalifornia\t14\nArizona\t21\nUtah\t2\nCalifornia\t7\nCalifornia\t6\nNevada\t11\nCalifornia\t1\n"))
        {
            TreeMap<String, ArrayList<Double>> categoryMap = GraphingMethods.readTable(scn);
            TreeMap<String, Double> expected = new TreeMap<>();

            expected.put("Arizona", 21.00);

            expected.put("California", 7.00);

            expected.put("Nevada", 7.00);

            expected.put("Utah", 4.67);

            TreeMap<String, Double> actual = GraphingMethods.prepareGraph(categoryMap, 3);

            assertEquals(expected, actual);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrepareGraph5 ()
    {
        TreeMap<String, ArrayList<Double>> categoryMap = new TreeMap<>();
        GraphingMethods.prepareGraph(categoryMap, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrepareGraph6 ()
    {
        ArrayList<Double> values = new ArrayList<>();
        values.add(-1.2);
        TreeMap<String, ArrayList<Double>> categoryMap = new TreeMap<>();
        categoryMap.put("Utah", values);
        GraphingMethods.prepareGraph(categoryMap, 0);
    }

    @Test
    public void testGetMax ()
    {
        try (Scanner scn = new Scanner(
                "Oregon\t3\nWyoming\t12\nMaine\t7\nOregon\t4\nColorado\t6\nWyoming\t9\nWyoming\t2\nColorado\t13\nMaine\t16\nOregon\t19\nWyoming\t18\n"))
        {
            TreeMap<String, ArrayList<Double>> categoryMap = GraphingMethods.readTable(scn);
            TreeMap<String, Double> expected = new TreeMap<>();

            expected.put("Oregon", 19.00);
            expected.put("Wyoming", 18.00);
            expected.put("Maine", 16.00);
            expected.put("Colorado", 13.00);

            TreeMap<String, Double> actual = new TreeMap<>();
            GraphingMethods.getMax(categoryMap, actual);

            assertEquals(expected, actual);
        }
    }

    @Test
    public void testGetMin ()
    {
        try (Scanner scn = new Scanner(
                "Oregon\t3\nWyoming\t12\nMaine\t7\nOregon\t4\nColorado\t6\nWyoming\t9\nWyoming\t2\nColorado\t13\nMaine\t16\nOregon\t19\nWyoming\t18\n"))
        {
            TreeMap<String, ArrayList<Double>> categoryMap = GraphingMethods.readTable(scn);
            TreeMap<String, Double> expected = new TreeMap<>();

            expected.put("Oregon", 3.00);
            expected.put("Wyoming", 2.00);
            expected.put("Maine", 7.00);
            expected.put("Colorado", 6.00);

            TreeMap<String, Double> actual = new TreeMap<>();
            GraphingMethods.getMin(categoryMap, actual);

            assertEquals(expected, actual);
        }
    }

    @Test
    public void testGetSum ()
    {
        try (Scanner scn = new Scanner(
                "Oregon\t3\nWyoming\t12\nMaine\t7\nOregon\t4\nColorado\t6\nWyoming\t9\nWyoming\t2\nColorado\t13\nMaine\t16\nOregon\t19\nWyoming\t18\n"))
        {
            TreeMap<String, ArrayList<Double>> categoryMap = GraphingMethods.readTable(scn);
            TreeMap<String, Double> expected = new TreeMap<>();

            expected.put("Oregon", 26.00);
            expected.put("Wyoming", 41.00);
            expected.put("Maine", 23.00);
            expected.put("Colorado", 19.00);

            TreeMap<String, Double> actual = new TreeMap<>();
            GraphingMethods.getSum(categoryMap, actual);

            assertEquals(expected, actual);
        }
    }

    @Test
    public void testGetAverage ()
    {
        try (Scanner scn = new Scanner(
                "Oregon\t3\nWyoming\t12\nMaine\t7\nOregon\t4\nColorado\t6\nWyoming\t9\nWyoming\t2\nColorado\t13\nMaine\t16\nOregon\t19\nWyoming\t18\n"))
        {
            TreeMap<String, ArrayList<Double>> categoryMap = GraphingMethods.readTable(scn);
            TreeMap<String, Double> expected = new TreeMap<>();

            expected.put("Oregon", 8.67);
            expected.put("Wyoming", 10.25);
            expected.put("Maine", 11.50);
            expected.put("Colorado", 9.50);

            TreeMap<String, Double> actual = new TreeMap<>();
            GraphingMethods.getAverage(categoryMap, actual);

            assertEquals(expected, actual);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDrawGraph1 ()
    {
        Graphics g = null;
        TreeMap<String, Color> colorMap = new TreeMap<>();
        TreeMap<String, Double> summaryMap = new TreeMap<>();
        GraphingMethods.drawGraph(g, summaryMap, colorMap, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDrawGraph2 ()
    {
        Graphics g = null;
        TreeMap<String, Color> colorMap = new TreeMap<>();
        colorMap.put("Wow", new Color(1, 1, 1));
        colorMap.put("Test", new Color(1, 2, 1));
        colorMap.put("Tester", new Color(1, 2, 2));

        TreeMap<String, Double> summaryMap = new TreeMap<>();
        summaryMap.put("Wow", 12.0);
        summaryMap.put("Test", 3.0);
        summaryMap.put("notTester", 17.0);
        summaryMap.put("tester", 1.0);

        GraphingMethods.drawGraph(g, summaryMap, colorMap, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDrawGraph3 ()
    {
        Graphics g = null;
        TreeMap<String, Color> colorMap = new TreeMap<>();
        colorMap.put("test1", new Color(1, 1, 1));
        colorMap.put("test2", new Color(1, 2, 1));
        colorMap.put("test3", new Color(1, 2, 2));

        TreeMap<String, Double> summaryMap = new TreeMap<>();
        summaryMap.put("test1", 12.0);
        summaryMap.put("test2", -7.0);
        summaryMap.put("test3", 9.0);

        GraphingMethods.drawGraph(g, summaryMap, colorMap, true);
    }

    @Test
    public void testMapContainsKey ()
    {
        TreeMap<String, Color> colorMap = new TreeMap<>();
        colorMap.put("test", new Color(1, 21, 22));
        colorMap.put("test4", new Color(2, 65, 21));

        TreeMap<String, Double> summaryMap = new TreeMap<>();
        summaryMap.put("test", 13.0);
        summaryMap.put("test2", 19.0);
        summaryMap.put("test3", 7.0);
        summaryMap.put("test4", 17.0);

        assertTrue(GraphingMethods.mapContainsKey(colorMap, summaryMap) == true);
    }

    @Test
    public void testGetTotalContains ()
    {
        TreeMap<String, Color> colorMap = new TreeMap<>();
        colorMap.put("test", new Color(1, 21, 22));
        colorMap.put("test4", new Color(2, 65, 21));

        TreeMap<String, Double> summaryMap = new TreeMap<>();
        summaryMap.put("test", 13.0);
        summaryMap.put("test2", 19.0);
        summaryMap.put("test3", 7.0);
        summaryMap.put("test4", 17.0);

        assertTrue(GraphingMethods.getTotalContains(summaryMap, colorMap) == 30.0);
    }
}
