package comp1110.ass1;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertTrue;

/**
 * Created by steveb on 14/07/2015.
 */
public class MMRandomTest {
    private static final int RANDOM_TRIALS = 10000;
    private static final int FULL_COVERAGE = (int) Math.pow(MMPeg.values().length,MMRow.PEGS);

    @Test
    public void testRandom() {
        int[][] counts = new int[MMPeg.values().length][MMRow.PEGS];

        HashSet<String> samples = new HashSet<>();
        for (int i = 0; i < RANDOM_TRIALS; i++) {
            MMRow r = MMRow.create();
            samples.add(MMRow.create().toString());
            for (int col = 0; col < MMRow.PEGS; col++) {
                counts[r.getPeg(col).ordinal()][col]++;
            }
        }

        /* check each peg sees a good distribution */
        for (int col = 0; col < MMRow.PEGS; col++) {
            for (int p = 0; p < MMPeg.values().length; p++) {
                float value = (float) counts[p][col]/RANDOM_TRIALS;
                float expected = (float)1/(MMPeg.values().length*2);
                assertTrue("Abnormal distribution position "+col+": "+value+", expected at least "+expected, value > expected);
            }
        }

        /* check that something like the true range of possibilities is covered */
        assertTrue("Unable to generate full coverage.  Got "+samples.size()+" variations, should have seen " + FULL_COVERAGE, samples.size() > FULL_COVERAGE/2);
    }
}
