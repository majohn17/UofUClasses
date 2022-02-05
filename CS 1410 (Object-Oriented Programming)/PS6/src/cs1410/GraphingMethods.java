package cs1410;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.text.DecimalFormat;

/**
 * Methods in support of PS6.
 * 
 * @author Matthew Johnsen and Joe Zachary
 */
public class GraphingMethods
{
    /**
     * Constant used to request a max operation
     */
    public final static int MAX = 0;

    /**
     * Constant used to request a min operation
     */
    public final static int MIN = 1;

    /**
     * Constant used to request a sum operation
     */
    public final static int SUM = 2;

    /**
     * Constant used to request an average operation
     */
    public final static int AVG = 3;

    /**
     * The dataSource must consist of one or more lines. If there is not at least one line, the method throws an
     * IllegalArgumentException whose message explains what is wrong.
     * 
     * Each line must consist of some text (a key), followed by a tab character, followed by a double literal (a value),
     * followed by a newline.
     * 
     * If any lines are encountered that don't meet this criteria, the method throws an IllegalArgumentException whose
     * message explains what is wrong.
     * 
     * Otherwise, the map returned by the method (here called categoryMap) must have all of these properties:
     * 
     * (1) The set of keys contained by categoryMap must be the same as the set of keys that occur in the Scanner
     * 
     * (2) The list valueMap.get(key) must contain exactly the same numbers that appear as values on lines in the
     * Scanner that begin with key. The values must occur in the list in the same order as they appear in the Scanner.
     * 
     * For example, if the Scanner contains
     * 
     * <pre>
     * Utah        10
     * Nevada       3
     * Utah         2
     * California  14
     * Arizona     21
     * Utah         2
     * California   7
     * California   6
     * Nevada      11
     * California   1
     * </pre>
     * 
     * (where the spaces in each line are intended to be a single tab), then this map should be returned:
     * 
     * <pre>
     *  Arizona    {21}
     *  California {14, 7, 6, 1} 
     *  Nevada     {3, 11}
     *  Utah       {10, 2, 2}
     * </pre>
     */
    public static TreeMap<String, ArrayList<Double>> readTable (Scanner dataSource)
    {
        TreeMap<String, ArrayList<Double>> map = new TreeMap<>();
        if (containsLine(dataSource) == false)
        {
            throw new IllegalArgumentException("The file selected contains no lines.");
        }
        else
        {
            while (dataSource.hasNextLine())
            {
                String line = dataSource.nextLine();
                String key = "";
                double value = -1;
                if (verifyFormat(line) == false)
                {
                    throw new IllegalArgumentException(
                            "The file contains 1 or more lines that don't meet the correct format. (\"key\"[tab]\"value\"[newLine])");
                }
                else
                {
                    key = line.substring(0, line.indexOf("\t"));
                    value = (Double.parseDouble(line.substring(line.indexOf("\t") + 1)));
                    if (map.containsKey(key))
                    {
                        map.get(key).add(value);
                    }
                    else
                    {
                        ArrayList<Double> tempList = new ArrayList<Double>();
                        tempList.add(value);
                        map.put(key, tempList);
                    }
                }
            }
            return map;
        }
    }

    /**
     * This helper method is used to verify if each line of the scanner matches the correct format used in the
     * readTable() method.
     */
    public static boolean verifyFormat (String line)
    {
        boolean isValid = false;
        if (line.indexOf("\t") == -1 || line.indexOf("\t") == 0 || line.split("\t").length > 2)
        {
            return false;
        }
        else
        {
            if (line.length() - 1 > line.indexOf("\t"))
            {
                for (int i = line.indexOf("\t") + 1; i < line.length(); i++)
                {
                    if (Character.isDigit(line.charAt(i)) == true)
                    {
                        isValid = true;
                    }
                    else
                    {
                        return false;
                    }
                }
                return isValid;
            }
            else
            {
                return false;
            }
        }
    }

    /**
     * Checks if the scanner past as a parameter contains at least 1 line and returns true or false.
     */
    public static boolean containsLine (Scanner text)
    {
        if (text.hasNextLine())
        {
            return true;
        }
        return false;
    }

    /**
     * If categoryMap is of size zero, throws an IllegalArgumentException whose message explains what is wrong.
     * 
     * Else if any of the values in the category map is an empty set, throws an IllegalArgumentException whose message
     * explains what is wrong.
     * 
     * Else if any of the numbers in the categoryMap is not positive, throws an IllegalAgumentException whose message
     * explains what is wrong.
     * 
     * Else if operation is anything other than SUM, AVG, MAX, or MIN, throws an IllegalArgumentException whose message
     * explains what is wrong.
     *
     * Else, returns a TreeMap<String, Double> (here called summaryMap) such that:
     * 
     * (1) The sets of keys contained by categoryMap and summaryMap are the same
     * 
     * (2) For all keys, summaryMap.get(key) is the result of combining the numbers contained in the set
     * categoryMap.get(key) using the specified operation. If the operation is MAX, "combining" means finding the
     * largest of the numbers. If the operation is MIN, "combining" means finding the smallest of the numbers. If the
     * operation is SUM, combining means summing the numbers. If the operation is AVG, combining means averaging the
     * numbers.
     * 
     * For example, suppose the categoryMap maps like this:
     * 
     * <pre>
     *  Arizona    {21
     *  California {14, 7, 6, 1} 
     *  Nevada     {3, 11}
     *  Utah       {10, 2, 2}
     * </pre>
     * 
     * and the operation is SUM. The map that is returned must map like this:
     * 
     * <pre>
     *  Arizona    21
     *  California 28 
     *  Nevada     14
     *  Utah       14
     * </pre>
     */
    public static TreeMap<String, Double> prepareGraph (TreeMap<String, ArrayList<Double>> categoryMap, int operation)
    {
        TreeMap<String, Double> summaryMap = new TreeMap<>();
        if (categoryMap.isEmpty())
        {
            throw new IllegalArgumentException("The map must contain a mapping");
        }
        else if (isNegative(categoryMap) == true)
        {
            throw new IllegalArgumentException("The map must contain no negative values");
        }
        else if (operation < 0 || operation > 3)
        {
            throw new IllegalArgumentException("The attempted operation is invalid");
        }
        else
        {
            if (operation == MAX)
            {
                getMax(categoryMap, summaryMap);
            }
            else if (operation == MIN)
            {
                getMin(categoryMap, summaryMap);
            }
            else if (operation == SUM)
            {
                getSum(categoryMap, summaryMap);
            }
            else
            {
                getAverage(categoryMap, summaryMap);
            }
        }
        return summaryMap;
    }

    /**
     * Searches through all the values in every arrayList value paired with a key to make sure there are no negative
     * values.
     */
    public static boolean isNegative (TreeMap<String, ArrayList<Double>> valueMap)
    {
        boolean valid = false;
        for (Entry<String, ArrayList<Double>> entry : valueMap.entrySet())
        {
            ArrayList<Double> numList = entry.getValue();
            for (Double temp : numList)
            {
                if (temp < 0)
                {
                    return true;
                }
                else
                {
                    valid = false;
                }
            }
        }
        return valid;
    }

    /**
     * Finds the max of each arrayList value in the TreeMap and pairs it with it's corresponding key into a specified
     * TreeMap.
     */
    public static void getMax (TreeMap<String, ArrayList<Double>> valueMap, TreeMap<String, Double> changeMap)
    {
        for (Entry<String, ArrayList<Double>> entry : valueMap.entrySet())
        {
            double max = 0.0;
            String key = entry.getKey();
            ArrayList<Double> numList = entry.getValue();
            for (Double temp : numList)
            {
                if (temp > max)
                {
                    max = temp;
                }
            }
            changeMap.put(key, max);
        }
    }

    /**
     * Finds the min of each arrayList value in the TreeMap and pairs it with it's corresponding key into a specified
     * TreeMap.
     */
    public static void getMin (TreeMap<String, ArrayList<Double>> valueMap, TreeMap<String, Double> changeMap)
    {
        for (Entry<String, ArrayList<Double>> entry : valueMap.entrySet())
        {
            double min = Double.MAX_VALUE;
            String key = entry.getKey();
            ArrayList<Double> numList = entry.getValue();
            for (Double temp : numList)
            {
                if (temp < min)
                {
                    min = temp;
                }
            }
            changeMap.put(key, min);
        }
    }

    /**
     * Takes each ArrayList value in the TreeMap, adds all the values in each ArrayList together, and pairs that sum
     * with its corresponding key into a specified TreeMap.
     */
    public static void getSum (TreeMap<String, ArrayList<Double>> valueMap, TreeMap<String, Double> changeMap)
    {
        for (Entry<String, ArrayList<Double>> entry : valueMap.entrySet())
        {
            double sum = 0.0;
            String key = entry.getKey();
            ArrayList<Double> numList = entry.getValue();
            for (Double temp : numList)
            {
                sum = sum + temp;
            }
            changeMap.put(key, sum);
        }
    }

    /**
     * Takes each ArrayList value in the TreeMap, computes the average of all the values in the ArrayList, and pairs
     * that average with its corresponding key into a specified TreeMap.
     */
    public static void getAverage (TreeMap<String, ArrayList<Double>> valueMap, TreeMap<String, Double> changeMap)
    {
        for (Entry<String, ArrayList<Double>> entry : valueMap.entrySet())
        {
            DecimalFormat formatter = new DecimalFormat("#0.00");
            double average = 0.0;
            String key = entry.getKey();
            ArrayList<Double> numList = entry.getValue();
            for (Double temp : numList)
            {
                average = average + temp;
            }
            average = average / numList.size();
            average = Double.parseDouble(formatter.format(average));
            changeMap.put(key, average);
        }
    }

    /**
     * If colorMap is empty, throws an IllegalArgumentException.
     * 
     * If there is a key in colorMap that does not occur in summaryMap, throws an IllegalArgumentException whose message
     * explains what is wrong.
     * 
     * If any of the numbers in the summaryMap is non-positive, throws an IllegalArgumentException whose message
     * explains what is wrong.
     * 
     * Otherwise, displays on g the subset of the data contained in summaryMap that has a key that appears in colorMap
     * with either a pie chart (if usePieChart is true) or a bar graph (otherwise), using the colors in colorMap.
     * 
     * Let SUM be the sum of all the values in summaryMap whose keys also appear in colorMap, let KEY be a key in
     * colorMap, let VALUE be the value to which KEY maps in summaryMap, and let COLOR be the color to which KEY maps in
     * colorMap. The area of KEY's slice (in a pie chart) and the length of KEY's bar (in a bar graph) must be
     * proportional to VALUE/SUM. The slice/bar should be labeled with both KEY and VALUE, and it should be colored with
     * COLOR.
     * 
     * For example, suppose summaryMap has this mapping:
     * 
     * <pre>
     *  Arizona    21
     *  California 28 
     *  Nevada     14
     *  Utah       14
     * </pre>
     * 
     * and colorMap has this mapping:
     * 
     * <pre>
     *  California Color.GREEN
     *  Nevada     Color.BLUE
     *  Utah       Color.RED
     * </pre>
     * 
     * Since Arizona does not appear as a key in colorMap, Arizona's entry in summaryMap is ignored.
     * 
     * In a pie chart Utah and Nevada should each have a quarter of the pie and California should have half. In a bar
     * graph, California's line should be twice as long as Utah's and Nevada's. Utah's slice/bar should be red, Nevada's
     * blue, and California's green.
     * 
     * The method should display the pie chart or bar graph by drawing on the g parameter. The example code below draws
     * both a pie chart and a bar graph for the situation described above.
     */
    public static void drawGraph (Graphics g, TreeMap<String, Double> summaryMap, TreeMap<String, Color> colorMap,
            boolean usePieChart)
    {
        final int TOP = 10;        // Offset of graph from top edge
        final int LEFT = 10;       // Offset of graph from left edge
        final int DIAM = 300;      // Diameter of pie chart
        final int WIDTH = 10;      // Width of bar in bar chart

        if (colorMap.isEmpty())
        {
            throw new IllegalArgumentException("The map must contain a color mapping");
        }
        else if (mapContainsKey(colorMap, summaryMap) == false)
        {
            throw new IllegalArgumentException("The color map and summary map must contain the same keys");
        }
        else if (isNegative2(summaryMap) == true)
        {
            throw new IllegalArgumentException("The summary map contains negative values");
        }
        else
        {
            if (usePieChart)
            {
                double sum = getTotalContains(summaryMap, colorMap);
                int start = 0;
                int yPosition = 0;
                int rectNum = 0;
                int counter = 0;
                int tempSpace = 9;
                for (Entry<String, Color> entry : colorMap.entrySet())
                {
                    String key = entry.getKey();
                    double value = summaryMap.get(key);
                    int proportion = (int) ((value / sum) * 360 + 1);

                    g.setColor(entry.getValue());
                    g.fillArc(LEFT, TOP, DIAM, DIAM, start, proportion);
                    int rectChange = rectNum * WIDTH;
                    g.fillRect(LEFT + DIAM + 2 * WIDTH, TOP + rectChange, WIDTH, WIDTH);
                    g.setColor(Color.black);
                    if (counter == 0)
                    {
                        g.drawString(key + " " + value, LEFT + DIAM + 4 * WIDTH, TOP + WIDTH);
                    }
                    else
                    {
                        g.drawString(key + " " + value, LEFT + DIAM + 4 * WIDTH, TOP + tempSpace + yPosition * WIDTH);
                    }

                    start = start + proportion;
                    rectNum = rectNum + 3;
                    yPosition = yPosition + 3;
                    counter++;
                }
            }
            else
            {
                double sum = getTotalContains(summaryMap, colorMap);
                int barConst = LEFT + DIAM + 4 * WIDTH;
                int counter = 0;
                int changeY = 0;
                int stringChange = 50;
                for (Entry<String, Color> entry : colorMap.entrySet())
                {
                    String key = entry.getKey();
                    double value = summaryMap.get(key);
                    int barWidth = (int) ((value / sum) * 500);

                    g.setColor(entry.getValue());
                    g.fillRect(barConst - (barWidth + 20), TOP + changeY, barWidth, 2 * WIDTH);
                    g.setColor(Color.black);
                    g.drawString(key + " " + value, barConst, TOP + WIDTH + WIDTH / 2 + (stringChange * counter));

                    changeY = changeY + 50;
                    counter++;
                }
                // // Draw California's bar, plus its label
                // g.setColor(Color.GREEN);
                // g.fillRect(LEFT + DIAM - DIAM / 2, TOP, DIAM / 2, 2 * WIDTH);
                // g.setColor(Color.black);
                // g.drawString("California 28.00", LEFT + DIAM + 2 * WIDTH, TOP + WIDTH + WIDTH / 2);
                //
                // // Draw Nevada's bar, plus its label
                // g.setColor(Color.BLUE);
                // g.fillRect(LEFT + DIAM - DIAM / 4, TOP + 3 * WIDTH, DIAM / 4, 2 * WIDTH);
                // g.setColor(Color.black);
                // g.drawString("Nevada 14.00", LEFT + DIAM + 2 * WIDTH, TOP + 4 * WIDTH + WIDTH / 2);
                //
                // // Draw Utah's bar, plus its label
                // g.setColor(Color.RED);
                // g.fillRect(LEFT + DIAM - DIAM / 4, TOP + 6 * WIDTH, DIAM / 4, 2 * WIDTH);
                // g.setColor(Color.black);
                // g.drawString("Utah 14.00", LEFT + DIAM + 2 * WIDTH, TOP + 7 * WIDTH + WIDTH / 2);
            }
        }
    }

    /**
     * Searches through all the keys of color map to ensure that they occur in summary map.
     */
    public static boolean mapContainsKey (TreeMap<String, Color> colorMap, TreeMap<String, Double> summaryMap)
    {
        boolean valid = false;
        for (Entry<String, Color> entry : colorMap.entrySet())
        {
            if (summaryMap.keySet().contains(entry.getKey()))
            {
                valid = true;
            }
            else
            {
                return false;
            }
        }
        return valid;
    }

    /**
     * Searches through all the values paired with keys in the summary map to make sure there are no negative values.
     */
    public static boolean isNegative2 (TreeMap<String, Double> summaryMap)
    {
        boolean valid = false;
        for (Entry<String, Double> entry : summaryMap.entrySet())
        {
            Double num = entry.getValue();
            if (num < 0)
            {
                return true;
            }
            else
            {
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Gets the total of the values with keys that occur in colorMap from summaryMap
     */
    public static double getTotalContains (TreeMap<String, Double> summaryMap, TreeMap<String, Color> colorMap)
    {
        double total = 0.0;
        for (Entry<String, Double> entry : summaryMap.entrySet())
        {
            if (colorMap.keySet().contains(entry.getKey()))
            {
                total = total + entry.getValue();
            }
        }
        return total;
    }
}
