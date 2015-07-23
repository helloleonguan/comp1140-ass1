package comp1110.ass1;

/**
 * An enumeration used to represent the different colored pegs in the mastermind game.
 */
public enum MMPeg {

    BLACK ('B'),
    WHITE ('W'),
    RED ('R'),
    GREEN ('G'),
    AQUA ('A'),
    YELLOW ('Y');

    /** the single-character identifier for the peg */
    private char id;

    /** Constructor for an MMPeg which sets the id */
    MMPeg(char id) {
        this.id = id;
    }

    /** Return the peg correspoding to a character.
     * @param c the character encoding
     * @return the corresponding peg
     */
    static MMPeg getPeg(char c) {
        for (MMPeg color : MMPeg.values())
            if (c == color.id) return color;
        return null;
    }

    /**
     * Return the set of peg colors
     * @return A string listing all of the pegs' colors.
     */
    static String colors() {
        String rtn = "";
        for (MMPeg color : MMPeg.values()) {
            rtn += color.id + " ";
        }
        return rtn;
    }
}
