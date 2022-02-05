package lightsout;

import javax.swing.*;
import static lightsout.LightsOutModel.*;
import static lightsout.LightsOutView.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Implements a Connect 4 game with a GUI interface.
 * 
 * @author Joe Zachary
 */
@SuppressWarnings("serial")
public class LightsOutView extends JFrame implements ActionListener
{
    // Some formatting constants
    private final static int WIDTH = 600;
    private final static int HEIGHT = 650;
    public final static int ROWS = 5;
    public final static int COLS = 5;
    public final static Color BACKGROUND_COLOR = Color.GRAY;
    public final static Color ON_COLOR = Color.YELLOW;
    public final static Color OFF_COLOR = Color.BLACK;
    public final static Color BOARD_COLOR = new Color(125, 0, 150);
    public final static int BORDER = 5;
    public final static int FONT_SIZE = 20;

    /** The "smarts" of the game **/
    private LightsOutModel model;

    /** The number of ties there have been **/
    private JLabel wins;

    /** The button that reset the board */
    private JButton newGame;

    /** The button that changes to and from manual mode */
    private JButton manualMode;

    /** The portion of the GUI that contains the playing surface **/
    private Board board;

    /**
     * Creates and initializes the game.
     */
    public LightsOutView ()
    {
        // Set the title that appears at the top of the window
        setTitle("Lights Out!!");

        // Cause the application to end when the windows is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Give the window its initial dimensions
        setSize(WIDTH, HEIGHT);

        // The root panel contains everything
        JPanel root = new JPanel();
        root.setLayout(new BorderLayout());
        setContentPane(root);

        // The center portion contains the playing board
        model = new LightsOutModel(ROWS, COLS);
        board = new Board(model, this);
        root.add(board, "Center");

        // The north portion contains the labels label
        JPanel labels = new JPanel();
        labels.setLayout(new BorderLayout());
        labels.setBackground(BACKGROUND_COLOR);
        root.add(labels, "North");

        wins = new JLabel("Wins: 0");
        wins.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        wins.setForeground(Color.WHITE);
        wins.setHorizontalAlignment(SwingConstants.CENTER);
        labels.add(wins, "Center");

        // The south portion contains the buttons
        JPanel buttons = new JPanel();
        buttons.setLayout(new BorderLayout());
        buttons.setBackground(BACKGROUND_COLOR);
        root.add(buttons, "South");

        newGame = new JButton("New Game");
        newGame.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        newGame.setForeground(Color.WHITE);
        newGame.setBackground(new Color(39, 151, 230));
        newGame.addActionListener(this);
        newGame.setActionCommand("New");
        buttons.add(newGame, "Center");

        manualMode = new JButton("Enter Manual Setup");
        manualMode.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        manualMode.setForeground(Color.GREEN);
        manualMode.setBackground(BACKGROUND_COLOR);
        manualMode.addActionListener(this);
        manualMode.setActionCommand("Manual");
        buttons.add(manualMode, "East");

        // Refresh the display and we're ready
        model.startGame();
        board.refresh();
        setVisible(true);
    }

    /**
     * Sets the label that displays the win count.
     */
    public void setWins (int n)
    {
        wins.setText("Wins: " + n);

    }

    /**
     * Called when a button is clicked. Each button has a different function that is called.
     */
    @Override
    public void actionPerformed (ActionEvent e)
    {
        String action = e.getActionCommand();
        if (action.equals("New"))
        {
            manualMode.setForeground(Color.GREEN);
            manualMode.setText("Enter Manual Mode");
            model.startGame();
            board.refresh();
        }
        else
        {
            if (model.getWinStatus() == false)
            {
                if (model.getManualCheck() == false)
                {
                    JOptionPane.showMessageDialog(this,
                            "After entering manual mode, you can't win until you start a new game.");

                }
                model.setManual();
                if (model.getLock() == true && model.getWinStatus() == false)
                {
                    manualMode.setForeground(Color.RED);
                    manualMode.setText("Exit Manual Mode");
                }
                else
                {
                    manualMode.setForeground(Color.GREEN);
                    manualMode.setText("Enter Manual Mode");
                }
            }
        }
    }
}

/**
 * The playing surface of the game.
 */
@SuppressWarnings("serial")
class Board extends JPanel implements MouseListener
{
    /** The "smarts" of the game */
    private LightsOutModel model;

    /** The top level GUI for the game */
    private LightsOutView display;

    /**
     * Creates the board portion of the GUI.
     */
    public Board (LightsOutModel model, LightsOutView display)
    {
        // Record the model and the top-level display
        this.model = model;
        this.display = display;

        // Set the background color and the layout
        setBackground(BOARD_COLOR);
        setLayout(new GridLayout(ROWS, COLS));

        // Create and lay out the grid of squares that make up the game.
        for (int r = 0; r < ROWS; r++)
        {
            for (int c = 0; c < COLS; c++)
            {
                Square s = new Square(r, c);
                s.addMouseListener(this);
                add(s);
            }
        }
    }

    /**
     * Refreshes the display. This should be called whenever something changes in the model.
     */
    public void refresh ()
    {
        // Iterate through all of the squares that make up the grid
        Component[] squares = getComponents();
        for (Component c : squares)
        {
            Square s = (Square) c;

            // Set the color of the squares appropriately
            int status = model.getValue(s.getRow(), s.getCol());
            if (status == 1)
            {
                s.setColor(ON_COLOR);
            }
            else
            {
                s.setColor(OFF_COLOR);
            }
        }

        display.setWins(model.getWins());

        // Ask that this board be repainted
        repaint();
    }

    /**
     * Called whenever a Square is clicked. Notifies the model that a move has been attempted.
     */
    @Override
    public void mouseClicked (MouseEvent e)
    {
    }

    @Override
    public void mousePressed (MouseEvent e)
    {
        Square s = (Square) e.getSource();
        model.makeMove(s.getRow(), s.getCol());
        int status = model.updateGame();
        refresh();
        if (status == L_OFF)
        {
            JOptionPane.showMessageDialog(this, "You've successfully turned out all the lights! You Win!!!");
        }
    }

    @Override
    public void mouseReleased (MouseEvent e)
    {
    }

    @Override
    public void mouseEntered (MouseEvent e)
    {
    }

    @Override
    public void mouseExited (MouseEvent e)
    {
    }
}

/**
 * A single square on the board where a move can be made
 */
@SuppressWarnings("serial")
class Square extends JPanel
{
    /** The row within the board of this Square. Rows are numbered from zero; row zero is at the bottom of the board. */
    private int row;

    /** The column within the board of this Square. Columns are numbered from zero; column zero is at the left */
    private int col;

    /** The current Color of this Square */
    private Color color;

    /**
     * Creates a square and records its row and column
     */
    public Square (int row, int col)
    {
        this.row = row;
        this.col = col;
        this.color = BACKGROUND_COLOR;
    }

    /**
     * Returns the row of this Square
     */
    public int getRow ()
    {
        return row;
    }

    /**
     * Returns the column of this Square
     */
    public int getCol ()
    {
        return col;
    }

    /**
     * Sets the color of this square
     */
    public void setColor (Color color)
    {
        this.color = color;
    }

    /**
     * Paints this Square onto g
     */
    @Override
    public void paintComponent (Graphics g)
    {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(BOARD_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(color);
        g.fillOval(BORDER, BORDER, getWidth() - 2 * BORDER, getHeight() - 2 * BORDER);
    }
}
