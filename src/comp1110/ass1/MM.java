package comp1110.ass1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A basic version of the classic MasterMind game.
 *
 * https://en.wikipedia.org/wiki/Mastermind_(board_game)
 *
 * This class implements the basic logic of the game and offers a simple command-line version of the game.
 *
 */
public class MM {
    /** The number of rows (turns) in the game */
    public static final int ROWS = 10;

    /** Keep track of which turn we're up to in the game */
    private static int currentTurn = 0;
    /** @return the turn we're up to in the game */
    public static int getCurrentTurn() { return currentTurn; }

    /** The secret code that the player has to guess */
    private static MMRow secretCode;
    /** @return the secret code being guessed */
    public static MMRow getSecretCode() { return secretCode; }

    /** An array of advice on where the player has bade mistakes so far in the game */
    private static MMAdvice[] advice = new MMAdvice[ROWS];
    /** @param turn the turn in question @return the advice for a particular turn in the game */
    public static MMAdvice getAdvice(int turn) {return advice[turn]; }

    /** A record of the guesses taken so far in the game */
    private static MMRow[] guesses = new MMRow[ROWS];

    /** @return a deep copy of the guesses */
    public static MMRow[] getGuesses() {
        /* deep copy */
        MMRow[] rtn = new MMRow[ROWS];
        for (int i = 0; i < ROWS; i++) {
            rtn[i] = (guesses[i] == null) ? null : guesses[i].clone();
        }
        return rtn;
    }

    /** A record of the scores given so far in the game */
    private static MMScore[] scores = new MMScore[ROWS];
    /** @param turn the turn in question @return the score for a particular turn in the game */
    public static MMScore getScore(int turn) {return scores[turn]; }

    /**
     * Add a new guess, reflecting the player's next move.
     *
     * @param guess A row representing the player's next guess at the code
     */
    public static void addGuess(MMRow guess) {
        guesses[currentTurn] = guess;
        scores[currentTurn] = guess.getScore(secretCode);
        advice[currentTurn] = MMAdvice.advise(guesses, scores, currentTurn);
        currentTurn++;
    }

    /**
     * Set up for a new game, reinitializing the key variables
     */
    public static void newGame() {
        for(int i = 0; i < ROWS; i++) {
            guesses[i] = null;
            scores[i] = null;
            advice[i] = null;
        }
        secretCode = MMRow.create();
        currentTurn = 0;
    }

    /**
     * Read a player's guess from the console.
     *
     * @return An MMRow object reflecting the player's guess.
     */
    static MMRow getGuess() {
        MMRow rtn = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Your turn: ");
        String guess = null;
        try {
            guess = br.readLine();
            System.out.println("++++" + guess + "+++");
            rtn = MMRow.create(guess);
            while (rtn == null) {
                System.out.println("Invalid.  Please enter a sequence of " + MMRow.PEGS + " characters from " + MMPeg.colors());
                guess = br.readLine();
                rtn = MMRow.create(guess);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rtn;
    }

    public static void main(String[] args) {
        newGame();
        do {
            addGuess(getGuess());
            System.out.println(scores[currentTurn-1]);
        } while (!scores[currentTurn-1].perfect() && currentTurn < ROWS);
    }
}
