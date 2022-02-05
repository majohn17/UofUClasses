package animation;

import java.awt.Color;
import java.awt.Graphics2D;

public class Animation
{
    /** Name to display in title bar */
    public final static String STUDENT_NAME = "Matthew Johnsen";  // PUT YOUR NAME HERE!!!

    /**
     * This is the method that you need to rewrite to create a custom animation. This method is called repeatedly as the
     * animation proceeds. It needs to draw on g how the animation should look after time milliseconds have passed.
     * 
     * The method returns true if the end of the animation has been reached or false if the animation should continue.
     * 
     * @param g      Graphics object on which to draw
     * @param time   Number of milliseconds that have passed since animation started
     * @param height Current height of the frame
     * @param width  Current width of the frame
     */
    public static boolean paintFrame (Graphics2D g, int time, int height, int width)
    {
        Color random = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
        Color random1 = new Color((int) (Math.random() * 255), (int) (Math.random() * 255),
                (int) (Math.random() * 255));
        Color random2 = new Color((int) (Math.random() * 255), (int) (Math.random() * 255),
                (int) (Math.random() * 255));
        Color random3 = new Color((int) (Math.random() * 255), (int) (Math.random() * 255),
                (int) (Math.random() * 255));

        if (time >= 1)
        {
            drawBall(g, 0 + (time / 22), 0 + (time / 25), 5 + (time / 40), random);
        }

        if (time > 250)
        {
            drawBall(g, (int) (975 - (time / 14)), 0 + (time / 25), 5 + ((time - 250) / 39), random1);
        }

        if (time > 500)
        {
            drawBall(g, 975 - (time / 14), 530 - (time / 15), 5 + ((time - 500) / 38), random2);
        }

        if (time > 750)
        {
            drawBall(g, 0 + (time / 22), 530 - (time / 15), 5 + ((time - 750) / 37), random3);
        }

        if (time > 1000)
        {
            drawBall(g, 50 + (time / 22), 0 + (time / 25), 5 + ((time - 1000) / 36), random);
        }

        if (time > 1250)
        {
            drawBall(g, 925 - (time / 14), 0 + (time / 25), 5 + ((time - 1250) / 35), random1);
        }

        if (time > 1500)
        {
            drawBall(g, 925 - (time / 14), 530 - (time / 15), 5 + ((time - 1500) / 34), random2);
        }

        if (time > 1750)
        {
            drawBall(g, 50 + (time / 22), 530 - (time / 15), 5 + ((time - 1750) / 33), random3);
        }

        if (time > 2000)
        {
            drawBall(g, 100 + (time / 22), 0 + (time / 25), 5 + ((time - 2000) / 32), random);
        }

        if (time > 2250)
        {
            drawBall(g, 875 - (time / 14), 0 + (time / 25), 5 + ((time - 2250) / 31), random1);
        }

        if (time > 2500)
        {
            drawBall(g, 875 - (time / 14), 530 - (time / 15), 5 + ((time - 2500) / 30), random2);
        }

        if (time > 2750)
        {
            drawBall(g, 100 + (time / 22), 530 - (time / 15), 5 + ((time - 2750) / 29), random3);
        }

        if (time > 3000)
        {
            drawBall(g, 150 + (time / 22), 0 + (time / 25), 5 + ((time - 3000) / 28), random);
        }

        if (time > 3250)
        {
            drawBall(g, 825 - (time / 14), 0 + (time / 25), 5 + ((time - 3250) / 27), random1);
        }

        if (time > 3500)
        {
            drawBall(g, 825 - (time / 14), 530 - (time / 15), 5 + ((time - 3500) / 26), random2);
        }

        if (time > 3750)
        {
            drawBall(g, 150 + (time / 22), 530 - (time / 15), 5 + ((time - 3750) / 25), random3);
        }

        if (time > 4000)
        {
            drawBall(g, 200 + (time / 22), 0 + (time / 25), 5 + ((time - 4000) / 24), random);
        }

        if (time > 4250)
        {
            drawBall(g, 775 - (time / 14), 0 + (time / 25), 5 + ((time - 4250) / 23), random1);
        }

        if (time > 4500)
        {
            drawBall(g, 775 - (time / 14), 530 - (time / 15), 5 + ((time - 4500) / 22), random2);
        }

        if (time > 4750)
        {
            drawBall(g, 200 + (time / 22), 530 - (time / 15), 5 + ((time - 4750) / 21), random3);
        }

        if (time > 5000)
        {
            drawBall(g, 250 + (time / 22), 0 + (time / 25), 5 + ((time - 5000) / 20), random);
        }

        if (time > 5250)
        {
            drawBall(g, 725 - (time / 14), 0 + (time / 25), 5 + ((time - 5250) / 19), random1);
        }

        if (time > 5500)
        {
            drawBall(g, 725 - (time / 14), 530 - (time / 15), 5 + ((time - 5500) / 18), random2);
        }

        if (time > 5750)
        {
            drawBall(g, 250 + (time / 22), 530 - (time / 15), 5 + ((time - 5750) / 17), random3);
        }

        if (time > 6000)
        {
            drawBall(g, 300 + (time / 22), 0 + (time / 25), 5 + ((time - 6000) / 16), random);
        }

        if (time > 6250)
        {
            drawBall(g, 675 - (time / 14), 0 + (time / 25), 5 + ((time - 6250) / 15), random1);
        }

        if (time > 6500)
        {
            drawBall(g, 675 - (time / 14), 530 - (time / 15), 5 + ((time - 6500) / 14), random2);
        }

        if (time > 6750)
        {
            drawBall(g, 300 + (time / 22), 530 - (time / 15), 5 + ((time - 6750) / 13), random3);
        }

        if (time > 8000 && time <= 11000)
        {
            drawTriangle(g, -500 + (time / 10), 300, 100, Color.RED);
            drawTriangle(g, 1400 - (time / 10), 300, 100, Color.BLUE);
        }

        if (time > 11000 && time <= 13300)
        {
            drawTriangle(g, 1333 - (time / 15), 300, 100, Color.RED);
            drawTriangle(g, -433 + (time / 15), 300, 100, Color.BLUE);
        }

        if (time > 13300)
        {
            randomTriangle(g, 1177);
        }

        // Stop the animation if it has run long enough
        return time >= 15000;
    }

    /**
     * Draws a ball as it would appear at the given time (in msec), assuming it was fired at the given angle at time
     * zero. The provided color is used. Nothing is displayed for negative times. If the ball would be on the ground at
     * the given time, it is displayed on the ground unless disappear is true.
     */
    public static void drawBall (Graphics2D g, int x, int y, int size, Color color)
    {
        {
            g.setColor(color);
            g.fillOval(x, y, size, size);
        }
    }

    /**
     * Draws a triangle based on the parameters given.
     */
    public static void drawTriangle (Graphics2D g, int x, int y, int size, Color color)
    {
        {
            g.setColor(color);
            g.drawLine(x, y, x + (size / 2), (int) (y - (Math.sqrt(Math.pow(size, 2) - Math.pow(size / 2, 2)))));
            g.drawLine(x + (size / 2), (int) (y - (Math.sqrt(Math.pow(size, 2) - Math.pow(size / 2, 2)))), x + size, y);
            g.drawLine(x + size, y, x, y);
        }
    }

    /**
     * Draws an amount of random triangles :).
     */
    public static void randomTriangle (Graphics2D g, int amount)
    {
        for (int i = 0; i < amount; i++)
        {
            drawTriangle(g, ((int) (Math.random() * 900)), ((int) (Math.random() * 560)), ((int) (Math.random() * 100)),
                    new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
        }
    }
}
