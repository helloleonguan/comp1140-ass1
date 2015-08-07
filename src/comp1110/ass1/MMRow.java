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
        /*Generate a random number(0-5) 4 times to form a MMRow*/
        String colors = "";
        for (int i = 0; i < PEGS ; i++ ) {
            Random rand = new Random();
            int spot = rand.nextInt(6);
            switch (spot)
            {
                case 0: colors = colors + "B"; break;
                case 1: colors = colors + "W"; break;
                case 2: colors = colors + "R"; break;
                case 3: colors = colors + "G"; break;
                case 4: colors = colors + "A"; break;
                case 5: colors = colors + "Y"; break;
            }
        }
        return create(colors);
    }

    /**
     * Give a score for this row, given a secret code.
     * @param secretCode A row object reflecting code that this turn is trying to guess.
     * @return An MMScore object that reflects the score for this guess given the secretCode
     */
    public MMScore getScore(MMRow secretCode) {
        /*
        * Using an array to denote whether it is in the correct spot or only get the color right.
        * 0 stands for wrong peg
        * 1 stands for the color is right but in wrong spot
        * 2 stands for the peg in the right spot
        */
        if (this==null)
            return new MMScore (0,0);

        int a = 0;
        int b = 0;
        int flags[];
        flags = new int[PEGS];

        for (int i = 0; i < PEGS ; i ++) {
            if (this.getPeg(i) == secretCode.getPeg(i))
            {
                flags[i] = 2;
                continue;
            }
            for (int j = 0; j < PEGS ; j ++ ) {
                if (this.getPeg(i) == secretCode.getPeg(j) && secretCode.getPeg(j) != this.getPeg(j) && flags[j] == 0)
                {
                    flags[j] = 1;
                    break;
                }
            }
        }

        /*Counting*/
        for (int i = 0; i < PEGS ; i++ ) {
            if (flags[i] == 2) {
                a ++;
            }

            if (flags[i] == 1) {
                b ++;
            }
        }
        return new MMScore(a,b);
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
