package lightsout;

import java.util.Random;

public class LightsOutModel
{
    /** The 2d array that holds the board */
    private int[][] board;

    /** The number used to indicate Light On */
    public static final int L_ON = 1;

    /** The number used to indicate Light Off */
    public static final int L_OFF = 2;

    /** The number of wins */
    private int wins;

    /** Used to prevent normal moves during manual mode */
    private boolean lock;

    /** Used to determine if the user has won yet */
    private boolean winStatus;

    /** Used to determine if manual mode has been entered to prevent wins if manual mode has been entered */
    private boolean manualCheck;

    public LightsOutModel (int rows, int cols)
    {
        this.board = new int[rows][cols];
        this.wins = 0;
        this.lock = false;
        this.winStatus = false;
        this.manualCheck = false;
    }

    /** This resets the board for a new game */
    public void startGame ()
    {
        this.lock = false;
        this.winStatus = false;
        this.manualCheck = false;
        randomizeBoard();
    }

    /** Used to check if the user has won yet and updates necessary endgame information */
    public int updateGame ()
    {
        if (checkWin() == true && this.manualCheck == false && this.winStatus == false)
        {
            this.winStatus = true;
            this.wins++;
            return L_OFF;
        }
        else
        {
            return L_ON;
        }
    }

    /** Checks to see if all the lights are off */
    public boolean checkWin ()
    {
        for (int r = 0; r < this.board.length; r++)
        {
            for (int c = 0; c < this.board[r].length; c++)
            {
                if (this.board[r][c] == 1)
                {
                    return false;
                }
            }
        }
        return true;
    }

    /** Creates a randomized new board */
    public void randomizeBoard ()
    {
        for (int r = 0; r < this.board.length; r++)
        {
            for (int c = 0; c < this.board[r].length; c++)
            {
                this.board[r][c] = 2;
            }
        }

        Random randomRow = new Random();
        Random randomCol = new Random();

        for (int i = 0; i < 17; i++)
        {
            int tempRow = randomRow.nextInt(5);
            int tempCol = randomCol.nextInt(5);
            makeMove(tempRow, tempCol);
        }
    }

    /** Makes a move that changes the values for different situations*/
    public void makeMove (int rows, int cols)
    {
        if (this.lock == false && this.winStatus == false)
        {
            if (rows == 0 && cols == 0)
            {
                changeValue(rows, cols);
                changeValue(rows + 1, cols);
                changeValue(rows, cols + 1);
            }
            else if (rows == 0 && cols == this.board[0].length - 1)
            {
                changeValue(rows, cols);
                changeValue(rows + 1, cols);
                changeValue(rows, cols - 1);
            }
            else if (rows == this.board.length - 1 && cols == 0)
            {
                changeValue(rows, cols);
                changeValue(rows - 1, cols);
                changeValue(rows, cols + 1);
            }
            else if (rows == this.board.length - 1 && cols == this.board[0].length - 1)
            {
                changeValue(rows, cols);
                changeValue(rows - 1, cols);
                changeValue(rows, cols - 1);
            }
            else if (rows == 0)
            {
                changeValue(rows, cols);
                changeValue(rows + 1, cols);
                changeValue(rows, cols + 1);
                changeValue(rows, cols - 1);
            }
            else if (rows == this.board.length - 1)
            {
                changeValue(rows, cols);
                changeValue(rows - 1, cols);
                changeValue(rows, cols + 1);
                changeValue(rows, cols - 1);
            }
            else if (cols == 0)
            {
                changeValue(rows, cols);
                changeValue(rows + 1, cols);
                changeValue(rows - 1, cols);
                changeValue(rows, cols + 1);
            }
            else if (cols == this.board[0].length - 1)
            {
                changeValue(rows, cols);
                changeValue(rows + 1, cols);
                changeValue(rows - 1, cols);
                changeValue(rows, cols - 1);
            }
            else
            {
                changeValue(rows, cols);
                changeValue(rows + 1, cols);
                changeValue(rows - 1, cols);
                changeValue(rows, cols + 1);
                changeValue(rows, cols - 1);
            }
        }
        else if (this.lock == true && this.winStatus == false)
        {
            changeValue(rows, cols);
        }
        else
        {
            // Do nothing
        }
    }

    /** Changes light from on to off */
    public void changeValue (int rows, int cols)
    {
        if (this.board[rows][cols] == 1)
        {
            this.board[rows][cols] = 2;
        }
        else
        {
            this.board[rows][cols] = 1;
        }
    }

    /** Changes the value of the manual check to be used for the manual button*/
    public void setManual ()
    {
        this.manualCheck = true;
        if (this.lock == false)
        {
            this.lock = true;
        }
        else
        {
            this.lock = false;
        }
    }

    /** Returns value corresponding to light on or off */
    public int getValue (int rows, int cols)
    {
        return this.board[rows][cols];
    }

    /** Returns value corresponding the lock */
    public boolean getLock ()
    {
        return this.lock;
    }

    /** Returns value corresponding to the win status */
    public boolean getWinStatus ()
    {
        return this.winStatus;
    }

    /** Returns value corresponding to the win status */
    public boolean getManualCheck ()
    {
        return this.manualCheck;
    }

    /** Returns the number of wins a player has had since the game was created */
    public int getWins ()
    {
        return this.wins;
    }

    // The Next 2 methods are used only for the testing the model class

    /** Gets the board **/
    public int[][] getBoard ()
    {
        return this.board;
    }

    /** Sets the board **/
    public void setBoard (int row, int col, int value)
    {
        this.board[row][col] = value;
    }
}
