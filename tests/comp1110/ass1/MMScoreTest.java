package comp1110.ass1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by steveb on 14/07/2015.
 */
public class MMScoreTest {
    @Test
    public void testNone() {
        MMScore correct = new MMScore(0, 0);
        MMRow code;
        MMRow guess;
        MMScore score;

        code = MMRow.create("BBBB");

        guess = MMRow.create("RRRR");
        score = guess.getScore(code);
        assertTrue("Incorrect score "+score+" != "+correct+" for guess "+guess+" with code "+code, score.equals(correct));

        guess = MMRow.create("RGYW");
        score = guess.getScore(code);
        assertTrue("Incorrect score "+score+" != "+correct+" for guess "+guess+" with code "+code, score.equals(correct));

        guess = MMRow.create("YYYY");
        score = guess.getScore(code);
        assertTrue("Incorrect score "+score+" != "+correct+" for guess "+guess+" with code "+code, score.equals(correct));
    }

    @Test
    public void testOneExact() {
        MMScore correct = new MMScore(1, 0);
        MMRow code;
        MMRow guess;
        MMScore score;

        code = MMRow.create("BBBB");

        guess = MMRow.create("BRRR");
        score = guess.getScore(code);
        assertTrue("Incorrect score "+score+" != "+correct+" for guess "+guess+" with code "+code, score.equals(correct));

        guess = MMRow.create("RRRB");
        score = guess.getScore(code);
        assertTrue("Incorrect score "+score+" != "+correct+" for guess "+guess+" with code "+code, score.equals(correct));

        guess = MMRow.create("ARRB");
        score = guess.getScore(code);
        assertTrue("Incorrect score "+score+" != "+correct+" for guess "+guess+" with code "+code, score.equals(correct));

        guess = MMRow.create("BBGA");
        score = guess.getScore(code);
        assertTrue("Incorrect score "+score+" != "+correct+" for guess "+guess+" with code "+code, score.equals(correct));

        code = MMRow.create("YYYB");

        guess = MMRow.create("BBBB");
        score = guess.getScore(code);
        assertTrue("Incorrect score "+score+" != "+correct+" for guess "+guess+" with code "+code, score.equals(correct));
    }

    @Test
    public void testOneColor() {
        MMScore correct = new MMScore(0, 1);
        MMRow code;
        MMRow guess;
        MMScore score;

        code = MMRow.create("RRRB");

        guess = MMRow.create("BGGG");
        score = guess.getScore(code);
        assertTrue("Incorrect score "+score+" != "+correct+" for guess "+guess+" with code "+code, score.equals(correct));

        guess = MMRow.create("GGBG");
        score = guess.getScore(code);
        assertTrue("Incorrect score "+score+" != "+correct+" for guess "+guess+" with code "+code, score.equals(correct));

        guess = MMRow.create("ABGA");
        score = guess.getScore(code);
        assertTrue("Incorrect score "+score+" != "+correct+" for guess "+guess+" with code "+code, score.equals(correct));

        code = MMRow.create("BBYY");

        guess = MMRow.create("YGGG");
        score = guess.getScore(code);
        assertTrue("Incorrect score "+score+" != "+correct+" for guess "+guess+" with code "+code, score.equals(correct));
    }

    @Test
    public void testGame() {
        for (int i = 0; i < 5; i++) {
            MMScore score = guesses[i].getScore(code);
            assertTrue("Incorrect score "+score+" != "+scores[i]+" for guess "+guesses[i]+" with code "+code, scores[i].equals(score));
        }
    }

    private MMRow[] guesses = new MMRow[MM.ROWS];
    private MMScore[] scores = new MMScore[MM.ROWS];
    private MMRow code = MMRow.create("RBGA");

    @Before
    public void setupGame() {
        guesses[0] = MMRow.create("BRGA");
        guesses[1] = MMRow.create("BRAG");
        guesses[2] = MMRow.create("RBAG");
        guesses[3] = MMRow.create("BRGG");
        guesses[4] = MMRow.create("BRGY");
        guesses[5] = MMRow.create("RBGA");
        scores[0] = new MMScore(2,2);
        scores[1] = new MMScore(0,4);
        scores[2] = new MMScore(2,2);
        scores[3] = new MMScore(1,2);
        scores[4] = new MMScore(1,2);
        scores[5] = new MMScore(4,0);
    }
}
