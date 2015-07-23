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
}
