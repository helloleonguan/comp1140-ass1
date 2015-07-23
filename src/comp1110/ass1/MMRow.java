package comp1110.ass1;

import java.util.Random;

/**
 * This class represents a row of colored pegs in the mastermind game, either a player's guess, or the secret code.
 */
public class MMRow {
    /** The number of pegs in a row (a constant) */
    public static int PEGS = 4;

    /** The pegs in this row, represented as an array of MMPeg */
    private MMPeg pegs[] = new MMPeg[PEGS];

    /**
     * Return the peg for a given column within this row.
     * @param column The column whose peg is to be returned.
     * @return The peg at the given column in this row.
     */
    public MMPeg getPeg(int column) {return pegs[column];}

    /**
     * Set the peg for a given column within this row.
     * @param peg The value of the peg to be set.
     * @param column The column to be updated.
     */
    public void setPeg(MMPeg peg, int column) { pegs[column] = peg;}

    /**
     * Return a new row given a string representing a set of pegs
     * @param pegs A string containing one character for each peg in the row to be created.
     * @return A new row that corresponds to the pegs
     */
    static MMRow create(String pegs) {
        if (pegs.length() != PEGS)
            return null;
        MMRow rtn = new MMRow();

        for(int i = 0; i < pegs.length(); i++) {
            rtn.pegs[i] = MMPeg.getPeg(pegs.toUpperCase().charAt(i));
            if (rtn.pegs[i] == null) return null;
        }
        return rtn;
    }

    /**
     * Return a random row.  Each peg in the row must be chosen randomly.
     * @return An MMRow object where each peg is randomly chosen.
     */
    public static MMRow create() {
        /* FIXME */
        return create("RGBY");
    }

    /**
     * Give a score for this row, given a secret code.
     * @param secretCode A row object reflecting code that this turn is trying to guess.
     * @return An MMScore object that reflects the score for this guess given the secretCode
     */
    public MMScore getScore(MMRow secretCode) {
        /* FIXME */
        return new MMScore(0,0);
    }

    /**
     * Return a string representation of a given row.
     * @return A string that lists the colors of each of the pegs in the row.
     */
    @Override
    public String toString() {
        String rtn = "[ ";
        for(MMPeg p : pegs) {
            rtn += p+" ";
        }
        rtn += "]";
        return rtn;
    }

    /**
     * @return a deep copy of this row
     */
    @Override
    public MMRow clone() {
        MMRow rtn = new MMRow();
        for(int i = 0; i < PEGS; i++) {
            rtn.pegs[i] = pegs[i];
        }
        return rtn;
    }
}
