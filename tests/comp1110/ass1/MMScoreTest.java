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

        guess = MMRow.create("BBGA");
        score = guess.getScore(code);
        assertTrue("Incorrect score "+score+" != "+correct+" for guess "+guess+" with code "+code, score.equals(correct));

        code = MMRow.create("BBYY");

        guess = MMRow.create("YGGG");
        score = guess.getScore(code);
        assertTrue("Incorrect score "+score+" != "+correct+" for guess "+guess+" with code "+code, score.equals(correct));
    }

    @Test
    public void testGame() {
        for (int i = 0; i < 5; i++)
            for(int j = 0; j < 3; j++){
                MMScore score = guesses[i][j].getScore(code[j]);
                assertTrue("Incorrect score "+score+" != "+scores[i][j]+" for guess "+guesses[i][j]+" with code "+code[j],
                        scores[i][j].equals(score));
            }
    }

    private MMRow[][] guesses = new MMRow[MM.ROWS][3];
    private MMScore[][] scores = new MMScore[MM.ROWS][3];
    private MMRow[] code = new MMRow[3];

    @Before
    public void setupGame() {
        code[0] = MMRow.create("RBGA");
        code[1] = MMRow.create("YGWG");
        code[2] = MMRow.create("WWYW");

        guesses[0][0] = MMRow.create("BRGA");
        guesses[1][0] = MMRow.create("BRAG");
        guesses[2][0] = MMRow.create("RBAG");
        guesses[3][0] = MMRow.create("BRGG");
        guesses[4][0] = MMRow.create("BRGY");
        guesses[5][0] = MMRow.create("RBGA");

        guesses[0][1] = MMRow.create("RYWR");
        guesses[1][1] = MMRow.create("YYWB");
        guesses[2][1] = MMRow.create("GWGR");
        guesses[3][1] = MMRow.create("AWAW");
        guesses[4][1] = MMRow.create("GBWW");
        guesses[5][1] = MMRow.create("YWBG");

        guesses[0][2] = MMRow.create("WGAY");
        guesses[1][2] = MMRow.create("AYAR");
        guesses[2][2] = MMRow.create("WGRW");
        guesses[3][2] = MMRow.create("YAAY");
        guesses[4][2] = MMRow.create("WRAA");
        guesses[5][2] = MMRow.create("YWGW");

        scores[0][0] = new MMScore(2,2);
        scores[1][0] = new MMScore(0,4);
        scores[2][0] = new MMScore(2,2);
        scores[3][0] = new MMScore(1,2);
        scores[4][0] = new MMScore(1,2);
        scores[5][0] = new MMScore(4,0);

        scores[0][1] = new MMScore(1,1);
        scores[1][1] = new MMScore(2,0);
        scores[2][1] = new MMScore(0,3);
        scores[3][1] = new MMScore(0,1);
        scores[4][1] = new MMScore(1,1);
        scores[5][1] = new MMScore(1,2);

        scores[0][2] = new MMScore(1,1);
        scores[1][2] = new MMScore(0,1);
        scores[2][2] = new MMScore(2,0);
        scores[3][2] = new MMScore(0,1);
        scores[4][2] = new MMScore(1,0);
        scores[5][2] = new MMScore(2,1);
    }
}
