package comp1110.ass1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by steveb on 14/07/2015.
 */
public class MMAdviceTest {
    @Test
    public void testWasteful() {
        MMAdvice advice = MMAdvice.advise(g1guesses, g1scores, 1);
        boolean[] bm = advice.getBadMove();
        assertTrue("Third and fourth pegs can't both be right since only one was right in first move", (!bm[0] && !bm[1] && bm[2] && bm[3]));
    }

    @Test
    public void testFirstTurn() {
        MMAdvice advice = MMAdvice.advise(g2guesses, g2scores, 0);
        boolean gaveAdvice = false;
        for(boolean b : advice.getBadMove()) {
            if (b) gaveAdvice = true;
        }
        assertFalse("First turn can never be wrong", gaveAdvice);
    }

    @Test
    public void testSecondTurn() {
        MMAdvice advice = MMAdvice.advise(g2guesses, g2scores, 1);
        boolean gaveAdvice = false;
        for(boolean b : advice.getBadMove()) {
            if (b) gaveAdvice = true;
        }
        assertFalse("Second turn is logical", gaveAdvice);
    }

    @Test
    public void testThirdTurn() {
        MMAdvice advice = MMAdvice.advise(g2guesses, g2scores, 2);
        boolean[] bm = advice.getBadMove();
        assertTrue("Third and fourth pegs repeat incorrect moves", (!bm[0] && !bm[1] && bm[2] && bm[3]));
    }

    @Test
    public void testFourthTurn() {
        MMAdvice advice = MMAdvice.advise(g2guesses, g2scores, 3);
        boolean[] bm = advice.getBadMove();
        assertTrue("Only third peg is logical", (bm[0] && bm[1] && !bm[2] && bm[3]));
    }

    @Test
    public void testFifthTurn() {
        MMAdvice advice = MMAdvice.advise(g2guesses, g2scores, 4);
        boolean[] bm = advice.getBadMove();
        assertTrue("Only third peg is logical.  Fourth peg is illogical because second turn shows black, red, aqua and green are only viable colors.", (bm[0] && bm[1] && !bm[2] && bm[3]));
    }


    private MMRow[] g1guesses = new MMRow[MM.ROWS];
    private MMScore[] g1scores = new MMScore[MM.ROWS];
    private MMRow[] g2guesses = new MMRow[MM.ROWS];
    private MMScore[] g2scores = new MMScore[MM.ROWS];

    @Before
    public void setupGame() {
        g1guesses[0] = MMRow.create("RWAB");
        g1guesses[1] = MMRow.create("GYWR");
        g1guesses[2] = MMRow.create("YGWW");
        g1guesses[3] = MMRow.create("YGWG");
        g1guesses[4] = MMRow.create("YGYW");
        g1scores[0] = new MMScore(0,1);
        g1scores[1] = new MMScore(0,3);
        g1scores[2] = new MMScore(3,0);
        g1scores[3] = new MMScore(2,1);

        g2guesses[0] = MMRow.create("BRGA");
        g2guesses[1] = MMRow.create("BRAG");
        g2guesses[2] = MMRow.create("RBAG");
        g2guesses[3] = MMRow.create("BRGG");
        g2guesses[4] = MMRow.create("BRGY");
        g2scores[0] = new MMScore(2,2);
        g2scores[1] = new MMScore(0,4);
        g2scores[2] = new MMScore(2,2);
        g2scores[3] = new MMScore(1,2);
        g2scores[4] = new MMScore(1,2);
    }
}
