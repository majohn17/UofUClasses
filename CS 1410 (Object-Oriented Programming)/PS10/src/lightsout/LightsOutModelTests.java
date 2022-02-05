package lightsout;

import static org.junit.Assert.*;
import org.junit.Test;

public class LightsOutModelTests
{

    @Test
    public void test ()
    {
        LightsOutModel test = new LightsOutModel(5, 5);
        test.randomizeBoard();
        assertTrue(test.checkWin() == false);
    }

    @Test
    public void test2 ()
    {
        LightsOutModel test = new LightsOutModel(5, 5);
        test.randomizeBoard();
        assertTrue(test.checkWin() == false);
        for (int r = 0; r < test.getBoard().length; r++)
        {
            for (int c = 0; c < test.getBoard()[0].length; c++)
            {
                test.setBoard(r, c, 2);
            }
        }
        assertTrue(test.checkWin() == true);
    }

}
