package asteroids.game;

import static asteroids.game.Constants.*;
import java.awt.*;
import java.util.Iterator;
import javax.swing.*;
import asteroids.participants.Ship;

/**
 * The area of the display in which the game takes place.
 */
@SuppressWarnings("serial")
public class Screen extends JPanel
{
    /** Legend that is displayed across the screen */
    private String legend;

    /** The number of lives displayed */
    private int lives;

    /** The score that will be displayed */
    private String score;

    /** The level that will be displayed */
    private String level;

    /** Game controller */
    private Controller controller;

    /** Used to draw the lives on the screen */
    private Ship shipOutline;

    /**
     * Creates an empty screen
     */
    public Screen (Controller controller)
    {
        this.controller = controller;
        this.legend = "";
        this.score = "";
        this.level = "";
        this.lives = 0;
        setPreferredSize(new Dimension(SIZE, SIZE));
        setMinimumSize(new Dimension(SIZE, SIZE));
        setBackground(Color.black);
        setForeground(Color.white);
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 120));
        setFocusable(true);
        this.shipOutline = new Ship(0, 0, -(Math.PI / 2), null);
    }

    /**
     * Set the legend
     */
    public void setLegend (String legend)
    {
        this.legend = legend;
    }

    /**
     * Sets the Lives
     */
    public void setLives (int n)
    {
        this.lives = n;
    }

    /**
     * Sets the Lives
     */
    public void setScore (int n)
    {
        this.score = String.valueOf(n);
    }

    /**
     * Sets the Lives
     */
    public void setLevel (int n)
    {
        this.level = String.valueOf(n);
    }

    /**
     * Paint the participants onto this panel
     */
    @Override
    public void paintComponent (Graphics graphics)
    {
        // Use better resolution
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Do the default painting
        super.paintComponent(g);

        // Draw each participant in its proper place
        Iterator<Participant> iter = controller.getParticipants();
        while (iter.hasNext())
        {
            iter.next().draw(g);
        }

        // Draw the legend across the middle of the panel
        int size = g.getFontMetrics().stringWidth(legend);
        g.drawString(legend, (SIZE - size) / 2, SIZE / 2);

        // Draw the score in the top left corner
        g.setFont(new Font("SansSerif", 0, 30));
        g.drawString(String.valueOf(this.score), LABEL_HORIZONTAL_OFFSET, 50);

        // Draw the level in the top right corner
        g.drawString((String.valueOf(this.level)), (SIZE - LABEL_HORIZONTAL_OFFSET), 50);

        // Draw the lives remaining
        int centerValue = 50 + (g.getFontMetrics().stringWidth(score) / 2);

        int xValue = 0;
        if (this.lives == 3)
        {
            xValue = centerValue - (SHIP_SEPARATION + SHIP_WIDTH);
        }
        else if (this.lives == 2)
        {
            xValue = centerValue - ((SHIP_SEPARATION + SHIP_WIDTH) / 2);
        }
        else
        {
            xValue = centerValue;
        }

        for (int i = 0; i < this.lives; i++)
        {
            this.shipOutline.setPosition(xValue, 80.0);
            this.shipOutline.move();
            this.shipOutline.draw(g);
            xValue = xValue + (SHIP_WIDTH + SHIP_SEPARATION);
        }
    }
}
