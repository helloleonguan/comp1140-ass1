package comp1110.ass1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by steveb on 14/07/2015.
 */
public class MMGuessTest {

    @Test
    public void testCanPlay() {
        for(int game = 0; game < 10; game++) {
            MM.newGame();
            turn = 0;
            boolean won = false;
            while (!won && turn < MM.ROWS) {
                MMRow guess = MMGuesser.guess();
                MM.addGuess(guess);
                won = MM.getScore(turn).perfect();
                System.out.println(turn + " " + guess + "->" + MM.getScore(turn));
                MMAdvice advice = MM.getAdvice(turn);
                boolean[] bm = advice.getBadMove();
                assertTrue("Guesser should never make bad guesses", (!bm[0] && !bm[1] && !bm[2] && !bm[3]));
                turn++;
            }
            assertTrue("Need to be able to complete in " + MM.ROWS + " turns", turn < MM.ROWS);
        }
    }

    private MMRow[] guesses = new MMRow[MM.ROWS];
    private int turn;
}
