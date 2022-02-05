package c4;

import java.lang.ArrayIndexOutOfBoundsException;

/**
 * Represents the state of a Connect Four board on which multiple games can be played. The board consists of a
 * rectangular grid of squares in which playing pieces can be placed. Squares are identified by their zero-based row and
 * column numbers, where rows are numbered starting with the bottom row, and columns are numbered by starting from the
 * leftmost column.
 * 
 * Multiple games can be played on a single board. Whenever a new game begins, the board is empty. There are two
 * players, identified by the constants P1 (Player 1) and P2 (Player 2). P1 moves first in the first game, P2 moves
 * first in the second game, and so on in alternating fashion.
 * 
 * A C4Board also keeps track of the outcomes of the games that have been played on it. It tracks the number of wins by
 * P1, the number of wins by P2, and the number of ties. It does not track games that were abandoned before being
 * completed.
 */
public class C4Board
{
    /** The number used to indicate Player 1 */
    public static final int P1 = 1;

    /** The number used to indicate Player 2 */
    public static final int P2 = 2;

    /** The number used to indicate a tie */
    public static final int TIE = 3;

    /** The 2d array that holds the board */
    private int[][] board;

    /** The number used to indicate who went first in the previous game */
    private int wasFirst;

    /** The number used to indicate who's turn it is to move */
    private int move;

    /** The number of Player 1 wins */
    private int p1Wins;

    /** The number of Player 2 wins */
    private int p2Wins;

    /** The number of ties */
    private int ties;

    /**
     * Creates a C4Board with the specified number of rows and columns. There should be no pieces on the board and it
     * should be player 1's turn to move.
     * 
     * However, if either rows or cols is less than four, throws an IllegalArgumentException.
     */
    public C4Board (int rows, int cols)
    {
        if (rows < 4 || cols < 4)
        {
            throw new IllegalArgumentException();
        }
        this.board = new int[rows][cols];
        this.p1Wins = 0;
        this.p2Wins = 0;
        this.ties = 0;
        this.wasFirst = 1;
        this.move = 1;
    }

    /**
     * Sets up the board to play a new game, whether or not the current game is complete. All of the pieces should be
     * removed from the board. The player who had the second move in the previous game should have the first move in the
     * new game.
     */
    public void newGame ()
    {
        if (this.wasFirst == 1)
        {
            this.move = 2;
            this.wasFirst = 2;
        }
        else
        {
            this.move = 1;
            this.wasFirst = 1;
        }

        for (int r = 0; r < this.board.length; r++)
        {
            for (int c = 0; c < this.board[r].length; c++)
            {
                this.board[r][c] = 0;
            }
        }
    }

    /**
     * Records a move, by the player whose turn it is to move, in the first open square in the specified column. Returns
     * P1 if the move resulted in a win for player 1, returns P2 if the move resulted in a win for player 2, returns TIE
     * if the move resulted in a tie, and returns 0 otherwise.
     * 
     * If the column is full, or if the game is over because a win or a tie has previously occurred, does nothing but
     * return zero.
     * 
     * If a non-existent column is specified, throws an IllegalArgumentException.
     */
    public int moveTo (int col)
    {
        if (col < 0 || col >= this.board[0].length)
        {
            throw new IllegalArgumentException();
        }
        if (checkFullBoard() == true || checkColumn(col) == -1 || FourInARow.fourInRow(this.board) == true)
        {
            return 0;
        }
        else
        {
            this.board[checkColumn(col)][col] = this.move;
            return updateInfo();
        }
    }

    /**
     * Checks to see if someone has won after a move and returns the player who won or tie
     */
    public int updateInfo ()
    {
        if (this.move == 1 && FourInARow.fourInRow(this.board))
        {
            this.p1Wins++;
            this.move = 0;
            return 1;
        }

        if (FourInARow.fourInRow(this.board))
        {
            this.p2Wins++;
            this.move = 0;
            return 2;
        }

        if (checkFullBoard() == true)
        {
            this.ties++;
            this.move = 0;
            return 3;
        }

        if (this.move == 1)
        {
            this.move = 2;
        }
        else
        {
            this.move = 1;
        }
        return 0;
    }

    /**
     * Checks to see if this board is full
     */
    public boolean checkFullBoard ()
    {
        for (int r = 0; r < this.board.length; r++)
        {
            for (int c = 0; c < this.board[r].length; c++)
            {
                if (this.board[r][c] == 0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks to find the next open spot in a column from bottom up and returns that row or returns -1 if column is full
     */
    public int checkColumn (int col)
    {
        for (int r = 0; r < this.board.length; r++)
        {
            if (this.board[r][col] == 0)
            {
                return r;
            }
        }
        return -1;
    }

    /**
     * Reports the occupant of the board square at the specified row and column. If the row or column doesn't exist,
     * throws an IllegalArgumentException. If the square is unoccupied, returns 0. Otherwise, returns P1 (if the square
     * is occupied by player 1) or P2 (if the square is occupied by player 2).
     */
    public int getOccupant (int row, int col)
    {
        try
        {
            return this.board[row][col];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Reports whose turn it is to move. Returns P1 (if it is player 1's turn to move), P2 (if it is player 2's turn to
     * move, or 0 (if it is neither player's turn to move because the current game is over because of a win or tie).
     */
    public int getToMove ()
    {
        return this.move;
    }

    /**
     * Reports how many games have been won by player 1 since this board was created.
     */
    public int getWinsForP1 ()
    {
        return this.p1Wins;
    }

    /**
     * Reports how many games have been won by player 2 since this board was created.
     */
    public int getWinsForP2 ()
    {
        return this.p2Wins;
    }

    /**
     * Reports how many ties have occurred since this board was created.
     */
    public int getTies ()
    {
        return this.ties;
    }
}
