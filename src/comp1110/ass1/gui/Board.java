package comp1110.ass1.gui;

import comp1110.ass1.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * This is a JavaFX application that gives a graphical user interface (GUI) to the simple mastermind game.
 *
 * The tasks set for assignment one do NOT require you to refer to this class, so you may ignore it
 * entirely while you do assignment one.  However, the class also serves as a working example of a
 * number of JavaFX concepts that you are likely to need later in the semester, so you may wish to refer
 * to it later.
 *
 * Among other things, the class demonstrates:
 *   - Using inner classes that subclass standard JavaFX classes such as Circle and Image view
 *   - Using JavaFX groups to control properties such as visibility and lifetime of a collection of objects
 *   - Using transparency
 *   - Using mouse events to implement a draggable object
 *   - Making dropped objects snap to legal destinations
 *   - Using keyboard events to implement toggles controlled by the player
 *   - Using bitmap images
 *   - Using an mp3 audio track
 */
public class Board extends Application {

    /* Board layout constants */
    private static final int PEG_MARGIN = 30;

    private static final int BOARD_MARGIN = 30;
    private static final int BOARD_WIDTH = 277;
    private static final int BOARD_HEIGHT = 502;

    private static final int GAME_MARGIN = 10;
    private static final int GAME_WIDTH = BOARD_WIDTH + 2 * BOARD_MARGIN + PEG_MARGIN + 2 * GAME_MARGIN;
    private static final int GAME_HEIGHT = BOARD_HEIGHT + 2 * BOARD_MARGIN + 2 * GAME_MARGIN;

    private static final double ROW_PITCH = -40.33;
    private static final int ROW_OFFSET = 485;
    private static final double COL_PITCH = 35.33;
    private static final int COL_OFFSET = 142;

    private static final int DRAGGABLE_PEG_X_OFF = 340;
    private static final int DRAGGABLE_PEG_CENTER = 16;

    private static final int PIN_X_PITCH = 18;
    private static final int PIN_X_OFF = 54;
    private static final int PIN_Y_PITCH = -17;
    private static final int PIN_Y_OFF = 16;

    private static final int SHIELD_X_OFF = 121;
    private static final int SHIELD_Y_OFF = 44;

    private static final int SOLUTION_ROW_OFFSET = 58;


    /* locations of image and sound resources */
    private static final String URI_BASE = "assets/";
    private static final String BOARD_URI = Board.class.getResource(URI_BASE+"board.png").toString();
    private static final String SHIELD_URI = Board.class.getResource(URI_BASE+"shield.png").toString();
    private static final String WHITEPIN_URI = Board.class.getResource(URI_BASE+"whitepin.png").toString();
    private static final String BLACKPIN_URI = Board.class.getResource(URI_BASE+"blackpin.png").toString();
    // Loop in public domain CC 0 http://www.freesound.org/people/oceanictrancer/sounds/211684/
    private static final String LOOP_URI = Board.class.getResource(URI_BASE+"211684__oceanictrancer__classic-house-loop-128-bpm.wav").toString();


    /* JavaFX object groups */
    private Group root = new Group();
    private Group currentPegs = new Group();
    private Group currentPins = new Group();
    private Group currentHighlights = new Group();

    /* Misc JavaFX */
    private Shield shield;
    private AudioClip loop;

    /* game variables */
    private boolean loopPlaying = false;
    private boolean inPlay = false;
    private MMRow currentRow = new MMRow();
    private int setPegs = 0;


    /**
     * Inner class used to represent a highlight, which is used to identify bad moves.
     *
     * The class extends a JavaFX Circle, adding row and column placement, and the desired graphical effect
     * (semi-transparent red circle with soft edges).
     */
    class Highlight extends Circle {
        /**
         * Constructor.  Create a new highlight.
         * @param row The row in which to create the highlight.
         * @param col The column in which to create the highlight.
         */
        Highlight(int row, int col) {
            setFill(Color.RED);
            setOpacity(0.66);
            setRadius(18);
            setLayoutY(ROW_OFFSET + 17 + (row * ROW_PITCH));
            setLayoutX(COL_OFFSET + 16 + (col * COL_PITCH));
            setEffect(new GaussianBlur());
        }
    }

    /**
     * Inner class used to represent the shield that covers up the secret code.
     *
     * The class extends a JavaFX ImageView, using a png image to represent the shield.
     */
    class Shield extends ImageView {
        /**
         * Constructor, creates the shield object and sets up handler for mouse clicks on the shield.
         */
        Shield() {
            super(SHIELD_URI);
            setLayoutX(SHIELD_X_OFF);
            setLayoutY(SHIELD_Y_OFF);
            this.setOnMouseClicked(event -> {
                if (inPlay) {
                    gameOver();
                } else {
                    newGame();
                }
            });
        }

        /**
         * Reveal the secret code by making the shield transparent.
         */
        void reveal() {
            setOpacity(0);
        }

        /**
         * Hide the secret code by making the shield opaque.
         */
        void hide() {
            setOpacity(1);
        }
    }


    /**
     * Inner class used to represent game pegs.
     *
     * The class extends the JavaFX ImageView, using pngs to represent the different colored pegs.
     */
    class Peg extends ImageView {
        /**
         * Constructor for pegs that are part of the secret code.  Creates a peg at the desired column offset
         * in the secret code area.
         * @param peg The peg type defining the color of this peg.
         * @param col The column in which this peg will appear in the secret code.
         */
        Peg(MMPeg peg, int col) {
            super(Board.class.getResource(URI_BASE + peg.toString().toLowerCase() + ".png").toString());
            setLayoutY(SOLUTION_ROW_OFFSET);
            setLayoutX(COL_OFFSET + (col * COL_PITCH));
        }

        /**
         * Constructor for pegs that are part of the player's guesses.
         * @param peg The peg type defining the color of this peg.
         * @param row The row in the game in which this peg is to appear.
         * @param col The column in which this peg is to appear.
         */
        Peg(MMPeg peg, int row, int col) {
            super(Board.class.getResource(URI_BASE + peg.toString().toLowerCase() + ".png").toString());
            setLayoutY(ROW_OFFSET + (row * ROW_PITCH));
            setLayoutX(COL_OFFSET + (col * COL_PITCH));
        }
    }

    /**
     * Inner class used to represent a draggable peg.
     *
     * This class extends the JavaFX ImageView type, sets the color of the peg using a provided png, and
     * uses mouse events to create the drag effect.
     */
    class DraggablePeg extends ImageView {
        private MMPeg peg;
        private Board board;
        /* variables to track the dragging action */
        private double mousex, mousey, x, y;
        private boolean dragged = false;

        /**
         * Constructor, which creates the draggable peg based on the provided MMPeg.
         *
         * @param peg The MMPeg on which this draggable peg is based.
         * @param board The board with which this draggable peg will interact.
         */
        DraggablePeg(MMPeg peg, Board board) {
            super(Board.class.getResource(URI_BASE + peg.toString().toLowerCase() + ".png").toString());
            this.peg = peg;
            this.board = board;
            moveToStartpoint();

            /* Establish event handler for when the mouse is release (end of drag). */
            this.setOnMouseReleased(event -> {
                dragged = false;
                board.attemptToDrop(this);
                moveToStartpoint();
            });

            /* Establish event handler to follow movement while the mouse is being dragged. */
            this.setOnMouseDragged(event -> {
                dragged = true;
                double offsetX = event.getSceneX() - mousex;
                double offsetY = event.getSceneY() - mousey;
                x += offsetX;
                y += offsetY;
                setLayoutX(x - DRAGGABLE_PEG_CENTER);
                setLayoutY(y - DRAGGABLE_PEG_CENTER);
                toFront();
                mousex = event.getSceneX();
                mousey = event.getSceneY();
                event.consume();
            });
        }

        /**
         * Move the draggable peg back to its start.
         */
        void moveToStartpoint() {
            setLayoutX(DRAGGABLE_PEG_X_OFF);
            setLayoutY(ROW_OFFSET + (peg.ordinal() * ROW_PITCH));
        }

    }




    /* The standard main method used to start a JavaFX application. */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Give the user a hint by revealing the secret code.
     */
    private void hint() { shield.reveal(); }

    /**
     * Remove the user's hint by covering up the secret code.
     */
    private void unhint() { shield.hide(); }

    /**
     * Turn the sound loop on or off
     */
    private void toggleSoundLoop() {
        if (loopPlaying)
            loop.stop();
        else
            loop.play();
        loopPlaying = !loopPlaying;
    }

    /**
     * Add a new scoring pin to the current game.   The pin will be part of the currentPins group so all pins
     * can be deleted together when the game is over, by deleting that group.
     *
     * @param row The row to which this pin applies.
     * @param pos The ordinal position of this particular pin.
     * @param uri The URI to use to fetch the image for this pin.
     */
    private void addPin(int row, int pos, String uri) {
        ImageView pin = new ImageView(uri);
        pin.setLayoutX(COL_OFFSET - PIN_X_OFF + ((pos%2)* PIN_X_PITCH));
        pin.setLayoutY(ROW_OFFSET + (row * ROW_PITCH) + PIN_Y_OFF + ((pos/2)* PIN_Y_PITCH));
        currentPins.getChildren().add(pin);
    }

    /**
     * Given a score and a row number, add the appropriate pins to the board.
     *
     * @param row The row to which the score applies.
     * @param score The score to be added.
     */
    private void addScore(int row, MMScore score) {
        int pos = 0;
        for (int white = score.getColor(); white > 0; white--) {
            addPin(row, pos, WHITEPIN_URI);
            pos++;
        }
        for (int black = score.getExact(); black > 0; black--) {
            addPin(row, pos, BLACKPIN_URI);
            pos++;
        }
    }

    /**
     * Add advice on bad moves to the board for a given row, placing a red highlight under each peg that was
     * deemed to be the result of a bad move.   The highlights are kept in a separate group so that they
     * can be all removed at once.
     *
     * @param row The row to which the advice applies.
     * @param advice An MMAdvice object containing advice for this row.
     */
    private void addAdvice(int row, MMAdvice advice) {
        int pos = 0;
        for (boolean a : advice.getBadMove()) {
            if (a) currentHighlights.getChildren().add(new Highlight(row, pos));
            pos++;
        }

    }

    /**
     * Convert from a y offset to a row using simple maths.   This is necessary to make pegs snap to legal positions.
     * @param y The y position to be coverted
     * @return The row in the game to which this y position corresponds.
     */
    static int getRow(double y) {
        return (int) (0.5+((y - ROW_OFFSET) / ROW_PITCH));
    }

    /**
     * Convert from an x offset to a column using simple maths.   This is necessary to make pegs snap to legal positions.
     * @param x The x position to be coverted
     * @return The column in the game to which this x position corresponds.
     */
    static int getCol(double x) {
        return (int) (0.5+((x - COL_OFFSET) / COL_PITCH));
    }

    /**
     * A daggable peg has been released.  Attempt to drop it on the board.   If it is dropped at a legal position,
     * then create a new peg in that position and return the draggable peg to its start.   Otherwise just return
     * the draggable peg to its start.
     *
     * @param dpeg The draggable peg that has been released
     */
    void attemptToDrop(DraggablePeg dpeg) {
        /* work out the nearest position to where it is being dropped */
        int row = getRow(dpeg.getLayoutY());
        int col = getCol(dpeg.getLayoutX());

        if (inPlay && row == MM.getCurrentTurn() && (col >= 0 && col < MMRow.PEGS)) {
            /* the game is in play and the position is legal  */
            if (currentRow.getPeg(col) == null) {
                /* this is the first time a peg has been dropped in this position */
                currentRow.setPeg(dpeg.peg, col);
                setPegs++;
                if (setPegs == MMRow.PEGS) {
                    /* all of the pegs in the current row are now set, so the turn is complete */
                    int turn = MM.getCurrentTurn();
                    MM.addGuess(currentRow);
                    currentRow = new MMRow();
                    addScore(turn, MM.getScore(turn));
                    addAdvice(turn, MM.getAdvice(turn));
                    if (MM.getScore(turn).perfect()) {
                        /* they guessed it */
                        gameOver();
                    } else if (MM.getCurrentTurn() == MM.ROWS - 1) {
                        /* they exhausted their turns */
                        gameOver();
                    } else {
                        setPegs = 0;
                    }
                }
            } else
                currentRow.setPeg(dpeg.peg, col);

            /* in all of these cases, add the dropped peg to the board */
            currentPegs.getChildren().add(new Peg(dpeg.peg, row, col));
        }
    }

    /**
     * The current game is over, so reveal the secret code.
     */
    void gameOver() {
        shield.reveal();
        inPlay = false;
    }

    /**
     * Start a fresh game, setting up game state and removing any objects from the last game.
     */
    void newGame() {
        /* start new game */
        MM.newGame();
        setPegs = 0;
        currentRow = new MMRow();

        /* clear last game's objects */
        currentPegs.getChildren().clear();
        currentPegs.toFront();
        currentPins.getChildren().clear();
        currentPins.toFront();
        currentHighlights.getChildren().clear();

        /* draw secret code and hide it */
        shield.hide();
        shield.toFront();
        for (int col = 0; col < MMRow.PEGS; col++) {
            currentPegs.getChildren().add(new Peg(MM.getSecretCode().getPeg(col), col));
        }

        inPlay = true;
    }

    /**
     * The standard JavaFX start method.  JavaFX calls this when it is ready to start a JavaFX application.
     *
     * @param primaryStage  The window into which this application will draw.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("MasterMind");
        Scene scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT);

         /* create handlers for key press and release events */
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.M) {
                toggleSoundLoop();
                event.consume();
            } else if (event.getCode() == KeyCode.Q) {
                Platform.exit();
                event.consume();
            } else if (event.getCode() == KeyCode.SLASH) {
                hint();
                event.consume();
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SLASH) {
                unhint();
                event.consume();
            }
        });

        /* set up the sound loop */
        loop = new AudioClip(LOOP_URI);
        loop.setCycleCount(AudioClip.INDEFINITE);

        /* set up the board on which the game is played */
        ImageView board = new ImageView(BOARD_URI);
        board.setLayoutX(GAME_MARGIN);
        board.setLayoutY(GAME_MARGIN);
        root.getChildren().add(board);

        /* set up the shield used to hide the secret code */
        shield = new Shield();
        root.getChildren().add(shield);

        /* create each of the draggable pegs in their respective starting places */
        for (MMPeg p : MMPeg.values()) {
            root.getChildren().add(new DraggablePeg(p, this));
        }

        /* add the various groups to the root group */
        root.getChildren().add(currentPegs);
        root.getChildren().add(currentPins);
        root.getChildren().add(currentHighlights);

        /* reset everything for a new game */
        newGame();

        /* establish the scene and show the stage */
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
