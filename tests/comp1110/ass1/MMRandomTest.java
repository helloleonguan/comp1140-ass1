package comp1110.ass1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by steveb on 14/07/2015.
 */
public class MMRandomTest {
    private static final int RANDOM_TRIALS = 10000;

    @Test
    public void testRandom() {
        int[][] counts = new int[MMPeg.values().length][MMRow.PEGS];

        for (int i = 0; i < RANDOM_TRIALS; i++) {
            MMRow r = MMRow.create();
            for (int col = 0; col < MMRow.PEGS; col++) {
                counts[r.getPeg(col).ordinal()][col]++;
            }
        }

        for (int col = 0; col < MMRow.PEGS; col++) {
            for (int p = 0; p < MMPeg.values().length; p++) {
                float value = (float) counts[p][col]/RANDOM_TRIALS;
                float expected = (float)1/(MMPeg.values().length*2);
                assertTrue("Abnormal distribution position "+col+": "+value+", expected at least "+expected, value > expected);
            }
        }

    }
}
