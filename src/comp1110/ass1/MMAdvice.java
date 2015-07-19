package comp1110.ass1;

import java.util.HashSet;

/**
 * This class provides advice to the player identifying any moves they made that were illogical.
 */
public class MMAdvice {
    /** An array of booleans encapsulating the feedback for one guess.  True is used to indicate a bad move. */
    private boolean[] badMove = new boolean[MMRow.PEGS];
    /** @return the array of booleans reflecting any bad moves made for a given guess. */
    public boolean[] getBadMove() { return badMove; }

    /**
     * Provide advice to the player on any pegs that were illogically placed in a given move.
     *
     * Frequently, particularly early in the game, the player will have no choice but to make a random guess.
     * However as the game progresses, scores for previous turns can inform the player, and rule in and out
     * particular choices.  This method will provide the player with feedback on any time where they have made
     * a move that is provably not logical.
     *
     * If a given peg or group of pegs played was clearly illogical, then the player is informed by setting
     * the appropriate boolean/s in the badMove array of the returned MMAdvice object.
     *
     * @param guesses All of the guesses made by the player so far
     * @param scores All of the scores given to the player so far
     * @param target The turn for which advice is being sought
     * @return An MMAdvice object reporting any bad moves made by the player in the turn specified by target.
     */
    static MMAdvice advise(MMRow[] guesses, MMScore[] scores, int target) {
        /* FIXME */
        MMAdvice rtn = new MMAdvice();

        return rtn;
     }


    private static boolean[] getSilly(MMRow[] guesses, MMScore[] scores, int target) {
        boolean[] silly = new boolean[MMRow.PEGS];
        MMPeg[] newGuess = new MMPeg[MMRow.PEGS];
        MMPeg[] oldGuess = new MMPeg[MMRow.PEGS];

        for (int row = 0; row < target; row++) {
            int overlap = 0;
            for (int col = 0; col < MMRow.PEGS; col++) {
                newGuess[col] = guesses[target].getPeg(col);
                oldGuess[col] = guesses[row].getPeg(col);
            }

            for (int newCol = 0; newCol < MMRow.PEGS; newCol++) {
                for (int oldCol = 0; oldCol < MMRow.PEGS; oldCol++) {
                    if (oldGuess[oldCol] != null && oldGuess[oldCol] == newGuess[newCol]) {
                        overlap++;
                        oldGuess[oldCol] = null;
                        newGuess[newCol] = null;
                    }
                }
            }

             if (overlap > scores[row].getExact()+scores[row].getColor()) {
                for (int newCol = 0; newCol < MMRow.PEGS; newCol++) {
                    if (newGuess[newCol] == null) {
                         silly[newCol] = true;
                    }
                }
            }
         }
        return silly;
    }

    private static void getImpossible(HashSet<MMPeg>[] impossible, MMPeg[] definite, MMRow[] guesses, MMScore[] scores, int target) {
        for(int row = 0; row < target; row++) {
            int knownExact = 0;
            for (int col = 0; col < MMRow.PEGS; col++) {
                if (definite[col] == guesses[row].getPeg(col)) knownExact++;
            }
            if (scores[row].getExact() == knownExact) {
                /* any non-exact peg must be in the wrong position */
                for (int col = 0; col < MMRow.PEGS; col++) {
                    if (definite[col] != guesses[row].getPeg(col)) {
                        impossible[col].add(guesses[row].getPeg(col));
                    }
                }
            }
            if (scores[row].getExact() + scores[row].getColor() == MMRow.PEGS) {
                /* all possible colors are in this row; mark others as impossible */
                HashSet<MMPeg> possible = new HashSet<MMPeg>();
                for (int col = 0; col < MMRow.PEGS; col++)
                    possible.add(guesses[row].getPeg(col));
                for (MMPeg p : MMPeg.values()) {
                    if (!possible.contains(p)) {
                        for (int col = 0; col < MMRow.PEGS; col++)
                            impossible[col].add(p);
                    }
                }
            }
        }
    }

    private static void getDefinite(HashSet<MMPeg>[] impossible, MMPeg[] definite, MMRow[] guesses, MMScore[] scores, int target) {
        for(int row = 0; row < target; row++) {
            int knownImpossible = 0;
            for (int col = 0; col < MMRow.PEGS; col++) {
                if (impossible[col].contains(guesses[row].getPeg(col))) knownImpossible++;
            }
            if (scores[row].getExact() == MMRow.PEGS - knownImpossible) {
                for (int col = 0; col < MMRow.PEGS; col++) {
                    if (!impossible[col].contains(guesses[row].getPeg(col)))
                        definite[col] = guesses[row].getPeg(col);
                }
            }
        }
    }
}
