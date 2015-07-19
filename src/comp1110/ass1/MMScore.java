package comp1110.ass1;

/**
 * A representation of a score for a given turn of the mastermind game.
 */
public class MMScore {
    /** the number of pegs that were exactly right (right color, right position) */
    private int exact;
    /** @return the number of pegs that were exactly right */
    public int getExact() { return exact; }

    /** the number of pegs that were the right color, but were in the wrong place */
    private int color;
    /** @return the number of pegs that are the correct color but in the wrong position */
    public int getColor() { return color; }

    /**
     * Constructor.  Create a new MMScore given exact and color values
     * @param exact The number of pegs that were exactly right (correct color and position).
     * @param color The number of pegs that were the right color but in the wrong position.
     */
    public MMScore(int exact, int color) {
        this.exact = exact;
        this.color = color;
    }

    /**
     * Returns true if this score represents a perfect score.
     * @return true if this score represents a perfect score.
     */
    public boolean perfect() {
        return exact == MMRow.PEGS;
    }

    /**
     * Return a string reflecting the score.
     * @return A string that represents this score.
     */
    public String toString() {
        if (perfect())
            return "CORRECT!";
        else
            return exact+"B "+color+"W";
    }

    /**
     * Perform object equality, returning true if two objects reflect the same score.
     * @param other The object to be compared with
     * @return True if both objects represent the same score.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MMScore))
            return false;
        else
            return exact == ((MMScore) other).exact && color == ((MMScore) other).color;
    }
}
