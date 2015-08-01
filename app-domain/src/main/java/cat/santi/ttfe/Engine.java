package cat.santi.ttfe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cat.santi.ttfe.exception.BoardFullException;
import cat.santi.ttfe.exception.ExceptionFactory;

/**
 * 2048 game engine. Keeps track of everything related to the game.
 * // TODO: Review documentation after refactor
 *
 * @author Santiago Gonzalez
 */
public class Engine {

    /*
     * Features still TODO:
	 * - More game modes (normal, infinite, time attack...).
	 * - Keep a registry of movements done, to be able to undo them.
	 * - Save and load games.
	 * - Create some kind of AI for auto-play | auto-suggest.
	 * - Improve performance. (non-fatal issue atm)
	 */

    /**
     * The minimum allowed board size.
     */
    public static final int MIN_BORDER_SIZE = 4;
    /**
     * Value returned when a board square has no value.
     */
    public static final int VOID_VALUE = 0;
    /**
     * The default board width.
     */
    public static final int DEFAULT_COLUMNS = MIN_BORDER_SIZE;
    /**
     * The default board height.
     */
    public static final int DEFAULT_ROWS = MIN_BORDER_SIZE;
    /**
     * The default value of the greatest tile needed to win.
     */
    public static final int DEFAULT_TILE_VALUE_TO_WIN = 2048;
    /**
     * Array defining the allowed values for newly created tiles.
     */
    public static final int[] DEFAULT_ALLOWED_VALUES = {2, 4};

    /**
     * This <i>singleton</i>'s sInstance.
     */
    private static Engine sInstance = null;
    /**
     * The {@link Random} generator engine.
     */
    private Random mRandom = null;
    /**
     * The playing board.
     */
    private Board mBoard = null;
    /**
     * The accumulated score.
     */
    private int mScore = -1;
    /**
     * The count of elapsed turns.
     */
    private int mMovements = -1;
    /**
     * The value of the greatest tile needed to win
     */
    private int mTileValueToWin = -1;
    /**
     * The game's current state.
     */
    private State mState = State.NOT_PREPARED;
    /**
     * Listener to notify about game state changes.
     */
    private Listener mListener = null;

    /**
     * Empty constructor to thwart instantiation.
     */
    private Engine() {
    }

    /**
     * Get (and initialize, if needed) this singleton's sInstance.
     * <p/>
     * Please note: this method will not create and initialize a <i>game</i> by it's own. Instead,
     * you should call {@link #reset()} in order to do this. Please, refer to that method
     * documentation to learn more.
     *
     * @return This <i>singleton</i>'s sInstance.
     * @see #reset()
     */
    public static synchronized Engine getInstance() {

        if (sInstance == null)
            sInstance = new Engine();
        return sInstance;
    }

    /**
     * Get the current listener for game state changes.
     *
     * @return The current listener for game state changes.
     */
    public Listener getListener() {

        return mListener;
    }

    /**
     * Set the <i>listener</i> for this engine.
     *
     * @param listener The listener to settle.
     */
    public void setListener(Listener listener) {

        mListener = listener;
    }

    /**
     * Remove the listener for this engine.
     */
    public void removeListener() {

        setListener(null);
    }

    /**
     * Prepare this singleton for a new game, discarding any current changes, they exist, of
     * {@link #DEFAULT_ROWS} and {@link #DEFAULT_COLUMNS} size.
     * <p/>
     * This should be the first method called before playing any move, or requesting board
     * contents, as this will initialize the game.
     */
    public void reset() {

        reset(DEFAULT_ROWS, DEFAULT_COLUMNS, DEFAULT_TILE_VALUE_TO_WIN);
    }

    /**
     * Prepare this singleton for a new game, discarding any current changes, they exist, of the
     * given <i>rows</i> and <i>columns</i> size.
     * <p/>
     * This should be the first method called before playing any move, or requesting board
     * contents, as this will initialize the game.
     *
     * @param rows           The amount of rows height.
     * @param columns        The amount of columns width.
     * @param tileValueToWin The amount needed on any <i>tile</i> to win the game.
     */
    public void reset(int rows, int columns, int tileValueToWin) {

        init(rows, columns, tileValueToWin);
    }

    /**
     *
     */
    public boolean play(Direction direction, boolean simulate) {

        Board board = !simulate ? this.mBoard : Board.clone(this.mBoard);

        if (!simulate) {

            if (!mState.equals(State.IDLE)) {

                if (mListener != null)
                    mListener.onNotReady();
                return false;
            } else if (!canPlay(direction)) {

                if (mListener != null)
                    mListener.onDisallowedMove();
                return false;
            }

            switch (direction) {

                case DOWN:
                    setState(State.PLAYING_DOWN);
                    break;
                case LEFT:
                    setState(State.PLAYING_LEFT);
                    break;
                case RIGHT:
                    setState(State.PLAYING_RIGHT);
                    break;
                case UP:
                    setState(State.PLAYING_UP);
                    break;
            }
        }

        boolean moveDone = false;
        switch (direction) {

            case DOWN:
                for (int indexR = board.getRows() - 2; indexR >= 0; indexR--)
                    for (int indexC = 0; indexC < board.getColumns(); indexC++)
                        moveDone = resolvePlay(direction, board, indexR, indexC, simulate)
                                || moveDone;
                break;
            case LEFT:
                for (int indexC = 1; indexC < board.getColumns(); indexC++)
                    for (int indexR = 0; indexR < board.getRows(); indexR++)
                        moveDone = resolvePlay(direction, board, indexR, indexC, simulate)
                                || moveDone;
                break;
            case RIGHT:
                for (int indexC = board.getColumns() - 2; indexC >= 0; indexC--)
                    for (int indexR = 0; indexR < board.getRows(); indexR++)
                        moveDone = resolvePlay(direction, board, indexR, indexC, simulate)
                                || moveDone;
                break;
            case UP:
                for (int indexR = 1; indexR < board.getRows(); indexR++)
                    for (int indexC = 0; indexC < board.getColumns(); indexC++)
                        moveDone = resolvePlay(direction, board, indexR, indexC, simulate)
                                || moveDone;
                break;
        }

        if (!simulate)
            endTurn(moveDone);
        return moveDone;
    }

    /**
     * Get a matrix [row][column] (being [0][0] the top-left most square, and [height-1][width-1]
     * the bottom-left most square), of {@link Tile}s representing the board contents.
     * <p/>
     * The {@link Tile} matrix returned by this method is a clone of the one held by this
     * controller, so use it only to get and print information. Do not attempt to modify the node
     * attributes.
     *
     * @return A matrix of {@link Tile}s, representing the actual board contents.
     */
    public Tile[][] getTiles() {

        if (mState.equals(State.NOT_PREPARED))
            return null;

        return mBoard.getTiles();
    }

    /**
     * Get the count of elapsed turns.
     *
     * @return The number of turns elapsed, or <code>-1</code> if the game was not yet initialized.
     */
    public int getMovements() {

        return mMovements;
    }

    /**
     * Get the total score earned by the user.
     *
     * @return The score earned by the user so far, or <code>-1</code> if the game was not yet
     * initialized.
     */
    public int getScore() {

        return mScore;
    }

    /**
     * Get the game {@link State}.
     *
     * @return The game {@link State}.
     */
    public State getState() {

        return mState;
    }

    /**
     * Set the {@link State} of the game.
     *
     * @param state The new {@link State} of the game.
     */
    public void setState(State state) {

        this.mState = state;

        if (mListener != null)
            mListener.onStateChange(state);
    }

    /**
     * Get the number of rows (height) that the board have, filled or empty.
     *
     * @return The total number of rows on the board.
     */
    public int getBoardRows() {

        return mBoard.rows;
    }

    /**
     * Get the number of columns (width) that the board have, filled or empty.
     *
     * @return The total number of columns on the board.
     */
    public int getBoardColumns() {

        return mBoard.columns;
    }

    /**
     * Get the total amount of tiles that the board have, filled or empty.
     *
     * @return The total number of tiles on the board.
     */
    public int getBoardSize() {

        return getBoardRows() * getBoardColumns();
    }

    /**
     * Return A COPY of the {@link Tile}s that form a row at the given index.
     * <p/>
     * Do not perform modifications on the {@link Tile}s returned by this method.
     *
     * @param row The row index to get the {@link Tile}s from.
     * @return A COPY of the row at the given index.
     */
    public Tile[] getBoardRow(int row) {

        Tile[] result = new Tile[getBoardColumns()];

        for (int indexC = 0; indexC < getBoardColumns(); indexC++)
            result[indexC] = Tile.clone(mBoard.getTiles()[row][indexC]);

        return result;
    }

    /**
     * Return A COPY of the {@link Tile}s that form a column at the given index.
     * <p/>
     * Do not perform modifications on the {@link Tile}s returned by this method.
     *
     * @param column The column index to get the {@link Tile}s from.
     * @return A COPY of the column at the given index.
     */
    public Tile[] getBoardColumn(int column) {

        Tile[] result = new Tile[getBoardRows()];

        for (int indexR = 0; indexR < getBoardRows(); indexR++)
            result[indexR] = Tile.clone(mBoard.getTiles()[indexR][column]);

        return result;
    }

    /**
     * Print an ASCII version of the board and it's contents.
     * <p/>
     * This method should be only a debugging helper, and should not be used in production apps.
     */
    @Deprecated
    public void printBoard() {

        System.out.println(toString());
    }

    /**
     * Print an ASCII version of the board and it's contents.
     * <p/>
     * This method should be only a debugging helper, and should not be used in production apps.
     */
    @Override
    public String toString() {

        if (mState.equals(State.NOT_PREPARED))
            return "Game not prepared";

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("========== TURN : %4d | SCORE : %6d ==========\n\n",
                getMovements() + 1, mScore));
        for (int indexR = 0; indexR < mBoard.getRows(); indexR++) {

            for (int indexC = 0; indexC < mBoard.getColumns(); indexC++) {

                final int value = mBoard.getTile(indexR, indexC).getValue();
                final boolean isJustCreated = mBoard.getTile(indexR, indexC).isJustCreated();

                builder.append("  ");
                if (value != VOID_VALUE) {

                    if (isJustCreated)
                        builder.append(String.format("[%04d]", value)).append(" ");
                    else
                        builder.append(String.format(" %04d ", value)).append(" ");
                } else {

                    builder.append(" ____  ");
                }
            }
            builder.append("\n\n");
        }
        builder.append("==================================================\n");
        return builder.toString();
    }

    private boolean canPlay() {
        return canPlay(Direction.UP) || canPlay(Direction.DOWN)
                || canPlay(Direction.LEFT) || canPlay(Direction.RIGHT);
    }

    private boolean canPlay(Direction direction) {
        return play(direction, true);
    }

    /**
     * Try to move the <i>tile</i> contained at <code>(srcRow, srcColumn)</code> to the position at
     * <code>(dstRow, dstColumn)</code>.
     *
     * @param board     The board to apply changed onto.
     * @param srcRow    The <i>tile</i>'s source row to be moved from.
     * @param srcColumn The <i>tile</i>'s source column to be moved from.
     * @param dstRow    The <i>tile</i>'s destiny row to be moved to.
     * @param dstColumn The <i>tile</i>'s destiny column to be moved to.
     * @return The {@link PlayResult} of the move.
     */
    private PlayResult moveTile(Board board, int srcRow, int srcColumn, int dstRow, int dstColumn) {

        Tile fromSquare;
        Tile toSquare;
        try {

            // Get involved items
            fromSquare = board.getTile(srcRow, srcColumn);
            toSquare = board.getTile(dstRow, dstColumn);
        } catch (ArrayIndexOutOfBoundsException ex) {

            // Wall hit, so do nothing more
            // Return the PlayResult
            return new PlayResult(srcRow, srcColumn, false);
        }

        // Calculate direction (in case recursive call is needed)
        Direction direction;
        if (srcRow < dstRow)
            direction = Direction.DOWN;
        else if (srcRow > dstRow)
            direction = Direction.UP;
        else if (srcColumn < dstColumn)
            direction = Direction.RIGHT;
        else if (srcColumn > dstColumn)
            direction = Direction.LEFT;
        else
            direction = null;

        if (fromSquare.getValue() == VOID_VALUE) {

            // No value on 'from', so do nothing
            return null;
        } else if (toSquare.isVoid()) {

            // No value on 'to', so move (and try to move again)
            board.setValue(fromSquare.getValue(), dstRow, dstColumn);
            board.setValue(VOID_VALUE, srcRow, srcColumn);

            if (direction != null)
                switch (direction) {

                    case DOWN:

                        return moveTile(board, srcRow + 1, srcColumn, dstRow + 1, dstColumn);
                    case LEFT:

                        return moveTile(board, srcRow, srcColumn - 1, dstRow, dstColumn - 1);
                    case RIGHT:

                        return moveTile(board, srcRow, srcColumn + 1, dstRow, dstColumn + 1);
                    case UP:

                        return moveTile(board, srcRow - 1, srcColumn, dstRow - 1, dstColumn);
                }

            // note: will never get here (switch - default compiler ambiguous case)
            return null;
        } else if (toSquare.shouldNotMergeThisTurn()) {

            // The 'to' square was already merged, so do nothing
            // Return the PlayResult
            return new PlayResult(srcRow, srcColumn, false);
        } else if (fromSquare.getValue() == toSquare.getValue()) {

            // Same value on 'from' and 'to', so move (and sum)
            board.setValue(toSquare.getValue() * 2, dstRow, dstColumn, true);
            board.setValue(VOID_VALUE, srcRow, srcColumn);

            // Add the new value as score
            addScore(board.getTile(dstRow, dstColumn).value);

            // Return the PlayResult
            return new PlayResult(dstRow, dstColumn, true);
        } else {

            // Different values on 'from' and 'to', so do nothing
            // Return the PlayResult
            return new PlayResult(srcRow, srcColumn, false);
        }
    }

    /**
     * Add the given <i>score</i>.
     *
     * @param score The amount of score to add.
     */
    private void addScore(int score) {

        this.mScore += score;
    }

    /**
     * Initialize a new game, with the given <i>rows</i> height and <i>columns</i>
     * width:
     * <ul>
     * <li>Create a new {@link Random} object.</li>
     * <li>Create a new {@link Board} object.</li>
     * <li>Settle the <i>score</i> and <i>turns</i> to 0.</li>
     * <li>Create two <i>tiles</i> with value of 2 at random places.</li>
     * </ul>
     *
     * @param rows           The amount of rows height.
     * @param columns        The amount of columns width.
     * @param tileValueToWin The amount needed on any <i>tile</i> to win the game.
     */
    private void init(int rows, int columns, int tileValueToWin) {

        setState(State.PREPARING);

        // Initialize the random if needed
        if (this.mRandom == null)
            this.mRandom = new Random(System.currentTimeMillis());

        // Create a new board
        this.mBoard = new Board(rows, columns);

        // Clean state variables
        this.mScore = 0;
        this.mMovements = 0;
        this.mTileValueToWin = tileValueToWin;

        // Create two values to start
        createTile(createRandomValue());
        createTile(createRandomValue());

        setState(State.IDLE);
    }

    private boolean resolvePlay(Direction direction, Board board, int indexR, int indexC,
                                boolean simulate) {
        PlayResult result = null;
        switch (direction) {

            case DOWN:
                result = moveTile(board, indexR, indexC, indexR + 1, indexC);
                break;
            case LEFT:
                result = moveTile(board, indexR, indexC, indexR, indexC - 1);
                break;
            case RIGHT:
                result = moveTile(board, indexR, indexC, indexR, indexC + 1);
                break;
            case UP:
                result = moveTile(board, indexR, indexC, indexR - 1, indexC);
                break;
        }

        if (result != null && (indexR != result.row || indexC != result.column)) {

            if (!simulate)
                if (mListener != null)
                    mListener.onTileMoved(indexR, indexC,
                            result.row, result.column,
                            direction, result.merged);
            return true;
        }

        return false;
    }

    /**
     * End the current turn:
     * <ul>
     * <li>Call {@link Board#endTurn()}.</li>
     * <li>Add +1 to <i>turns</i>.</li>
     * <li>Create one <i>tile</i> with an allowed value at a random place.</li>
     * </ul>
     */
    private void endTurn(boolean turnDone) {

        if (findGreatestTile() >= mTileValueToWin) {
            // The game is 'victory'

            if (mListener != null)
                mListener.onGameFinished(true, getMovements(), mScore);
            setState(State.VICTORY);
        } else if (turnDone) {
            // The turn was completed successfully

            // Call (required) Board's endTurn() and create a new tile
            mBoard.endTurn();
            createTile(createRandomValue());

            if (!canPlay()) {
                // No more available moves. Game is over

                if (mListener != null)
                    mListener.onGameFinished(false, getMovements(), mScore);
                setState(State.DEFEAT);
            } else {
                // Game is not over yet

                // Add one more turn
                mMovements++;
                setState(State.IDLE);
            }
        } else {
            // Couldn't perform the play this turn

            if (mListener != null)
                mListener.onDisallowedMove();
            setState(State.IDLE);
        }
    }

    /**
     * Create a <i>tile</i> with the given <i>value</i> at a random position.
     *
     * @param value The integer value of the <i>tile</i>.
     */
    private void createTile(int value) {

        createTileAtPosition(value, findRandomAvailableSquare());
    }

    /**
     * Create a <i>tile</i> with the given <i>value</i> at the given
     * <i>point</i>.
     *
     * @param value The integer value of the <i>tile</i>.
     * @param point The {@link Point} in which to create the <i>tile</i>.
     * @see #createTileAtPosition(int, int, int)
     */
    private void createTileAtPosition(int value, Point point) {

        createTileAtPosition(value, point.row, point.column);
    }

    /**
     * Create a <i>tile</i> with the given <i>value</i> at the point from
     * the given <i>row</i> and <i>column</i>.
     *
     * @param value  The integer value of the <i>tile</i>.
     * @param row    The row in which to create the <i>tile</i>.
     * @param column The column in which to create the <i>tile</i>.
     * @see #createTileAtPosition(int, Point)
     */
    private void createTileAtPosition(int value, int row, int column) {

        mBoard.setValue(value, row, column, false, true);

        if (mListener != null)
            mListener.onTileCreated(row, column, value);
    }

    /**
     * Create a random value from the allowed tile values.
     *
     * @return A random value.
     */
    private int createRandomValue() {

        return DEFAULT_ALLOWED_VALUES[mRandom.nextInt(DEFAULT_ALLOWED_VALUES.length)];
    }

    /**
     * Find a random square, with value {@link #VOID_VALUE} from the board.
     *
     * @return The {@link Point} of a random board square with value
     * {@link #VOID_VALUE}.
     * @throws BoardFullException If there are no available space on the board.
     */
    private Point findRandomAvailableSquare() throws BoardFullException {

        List<Point> candidates = new ArrayList<>();

        for (int indexR = 0; indexR < mBoard.getRows(); indexR++)
            for (int indexC = 0; indexC < mBoard.getColumns(); indexC++)
                if (mBoard.getTile(indexR, indexC).isVoid())
                    candidates.add(new Point(indexR, indexC));

        if (candidates.size() == 0)
            throw ExceptionFactory.createBoardFullException(null);

        return candidates.get(mRandom.nextInt(candidates.size()));
    }

    /**
     * Find out how many times should 2 be powered in order to create the given <i>value</i>.
     * <p/>
     * That means {@link Math#pow(double, double)} with arguments <code>(2, return)</code> will get
     * <i>value</i> as a result.
     *
     * @param value The value to get it's pow 2 argument.
     * @return A value greater than 0 which powered that many times will
     * get the given <i>value</i>, or 0 if the given <i>value</i> is not
     * a power of 2.
     */
    @SuppressWarnings("unused")
    private int findPow2Multiplier(int value) {

        int count = 1;
        do {

            if (value == 2)
                return count;

            value /= 2;
            count++;
        } while (value >= 2 && value % 2 == 0);
        return 0;
    }

    /**
     * Find out the greatest value from all the <i>tiles</i> actually on the board.
     *
     * @return The value of the greatest <i>tile</i> playing.
     */
    private int findGreatestTile() {

        int result = 0;

        for (int indexR = 0; indexR < mBoard.getRows(); indexR++)
            for (int indexC = 0; indexC < mBoard.getColumns(); indexC++)
                if (mBoard.getTile(indexR, indexC).getValue() > result)
                    result = mBoard.getTile(indexR, indexC).getValue();

        return result;
    }

    /**
     * Game state enumeration.
     * <p/>
     * First of all, call {@link Engine#reset()} to eventually set the game on {@link #IDLE} state;
     * never attempt to play moves, or get game information while not on {@link #IDLE} state.
     */
    public enum State {

        /**
         * The game is ready to accept plays.
         */
        IDLE,
        /**
         * The game is still not prepared. Do not attempt to do plays.
         */
        NOT_PREPARED,
        /**
         * The game is preparing. Do not attempt to do plays.
         */
        PREPARING,
        /**
         * Playing a DOWN movement. Do not attempt to do plays.
         */
        PLAYING_DOWN,
        /**
         * Playing a LEFT movement. Do not attempt to do plays.
         */
        PLAYING_LEFT,
        /**
         * Playing a RIGHT movement. Do not attempt to do plays.
         */
        PLAYING_RIGHT,
        /**
         * Playing a UP movement. Do not attempt to do plays.
         */
        PLAYING_UP,
        /**
         * The game ended in victory.
         */
        VICTORY,
        /**
         * The game ended in defeat.
         */
        DEFEAT,
    }

    /**
     * Enumeration for allowed directions to be played.
     */
    public enum Direction {

        /**
         * Play a DOWN movement.
         */
        DOWN,
        /**
         * Play a LEFT movement.
         */
        LEFT,
        /**
         * Play a RIGHT movement.
         */
        RIGHT,
        /**
         * Play a UP movement.
         */
        UP,
    }

    /**
     * Listener to notify whenever a change on the <i>game state</i> occurred.
     */
    public interface Listener {

        /**
         * Triggered whenever there's any change on the <i>game state</i>.
         *
         * @param state The game {@link State} currently on.
         */
        void onStateChange(State state);

        /**
         * Triggered when the game is finished, either for victory or defeat.
         *
         * @param victory Whether it's a victory (<code>true</code>) or defeat (<code>false</code>).
         * @param turns   The amount of turns elapsed.
         * @param score   The score achieved.
         */
        void onGameFinished(boolean victory, int turns, int score);

        /**
         * Triggered whenever a new <i>tile</i> is created.
         *
         * @param row    The creation row.
         * @param column The creation column.
         * @param value  The value of the <i>tile</i>.
         */
        void onTileCreated(int row, int column, int value);

        /**
         * Triggered whenever a <i>tile</i> was moved.
         *
         * @param srcRow    The source row of the <i>tile</i>.
         * @param srcColumn The source column of the <i>tile</i>.
         * @param dstRow    The destiny row of the <i>tile</i>.
         * @param dstColumn The destiny column of the <i>tile</i>.
         * @param direction The {@link Direction} on which the <i>tile</i> is moving towards.
         * @param merged    If this movement triggered a merge (<code>true</code>) or not
         *                  (<code>false</code>).
         */
        void onTileMoved(int srcRow, int srcColumn, int dstRow, int dstColumn, Direction direction,
                         boolean merged);

        /**
         * Triggered whenever an action that requires the game to be ready is tried to be
         * performed, but without the game being ready.1
         */
        void onNotReady();

        /**
         * Triggered whenever the user tried to play a non-allowed move.
         */
        void onDisallowedMove();
    }

    /**
     * Convenience class to reference a 2D point.
     */
    public static class Point {

        /**
         * The row.
         */
        int row;
        /**
         * The column.
         */
        int column;

        /**
         * Constructor for {@link Point}. Create a new {@link Point} with the given <i>row</i> and
         * <i>column</i>.
         *
         * @param row    The row to settle.
         * @param column The column to settle.
         */
        public Point(int row, int column) {

            this.row = row;
            this.column = column;
        }
    }

    /**
     * Convenience class wrapping a movement play result.
     * <p/>
     * A {@link PlayResult} is conformed from a destiny row and column, as well as a flag
     * indicating if the move ended as a merge.
     */
    public static class PlayResult {

        /**
         * The ending row for the play.
         */
        int row;
        /**
         * The ending column for the play.
         */
        int column;
        /**
         * Flag indicating if the play ended with a merge.
         */
        boolean merged;

        /**
         * Constructor for {@link PlayResult}.
         *
         * @param row    The ending row.
         * @param column The ending column.
         * @param merged Set to <code>true</code> if the play ended with a merge.
         */
        public PlayResult(int row, int column, boolean merged) {

            this.row = row;
            this.column = column;
            this.merged = merged;
        }
    }

    /**
     * Convenience class to reference a playing board.
     * <p/>
     * A board is a 4 x 4 chess style game field. From left to right are the columns, which goes
     * from positions 0 to 3. From top to bottom are the rows, also going from 0 to 3. Something
     * like so:
     * <pre>
     *        col.0  col.1  col.2  col.3
     * row.1  [   ]  [   ]  [   ]  [   ]
     * row.2  [   ]  [   ]  [   ]  [   ]
     * row.3  [   ]  [   ]  [   ]  [   ]
     * row.4  [   ]  [   ]  [   ]  [   ]
     * </pre>
     * <i>Figure - Board diagram</i>
     */
    public static class Board {

        private int rows;
        private int columns;
        private Tile[][] tiles;

        /**
         * Constructor for {@link Board}.
         * <p/>
         * Will create an empty board, with <i>rows</i> and <i>columns</i> size.
         *
         * @param rows    The height size (number of rows).
         * @param columns The width size (number of columns).
         */
        Board(int rows, int columns) {

            // Check that rows and columns are, at least, MIN_BORDER_SIZE
            if (rows < MIN_BORDER_SIZE)
                rows = MIN_BORDER_SIZE;
            if (columns < MIN_BORDER_SIZE)
                columns = MIN_BORDER_SIZE;

            // Initialize this object's attributes
            this.rows = rows;
            this.columns = columns;
            this.tiles = new Tile[getRows()][getColumns()];

            // Create an empty board
            for (int indexR = 0; indexR < getRows(); indexR++)
                for (int indexC = 0; indexC < getColumns(); indexC++)
                    tiles[indexR][indexC] = new Tile();
        }

        /**
         * Constructor for {@link Board}.
         *
         * @param source Will create a copy of the given {@link Board}.
         */
        Board(Board source) {

            this.rows = source.rows;
            this.columns = source.columns;

            this.tiles = new Tile[getRows()][getColumns()];
            for (int indexR = 0; indexR < getRows(); indexR++)
                for (int indexC = 0; indexC < getColumns(); indexC++)
                    tiles[indexR][indexC] = Tile.clone(source.getTile(indexR, indexC));
        }

        /**
         * Return a copy of this {@link Board}.
         */
        public static Board clone(Board board) {

            return new Board(board);
        }

        /**
         * Get this {@link Board}'s rows.
         *
         * @return This {@link Board}'s rows.
         */
        public int getRows() {

            return rows;
        }

        /**
         * Get this {@link Board}'s columns.
         *
         * @return This {@link Board}'s columns.
         */
        public int getColumns() {

            return columns;
        }

        /**
         * Get this {@link Board}'s {@link Tile}, at the given <i>row</i> and <i>column</i>.
         *
         * @param row    The row to get the {@link Tile} from.
         * @param column The column to get the {@link Tile} from.
         * @return The {@link Tile} at the given position.
         */
        public Tile getTile(int row, int column) {

            return tiles[row][column];
        }

        /**
         * Return a clone of this objet's {@link Tile} matrix.
         *
         * @return A clone of this {@link Tile} matrix.
         */
        public Tile[][] getTiles() {

            return tiles.clone();
        }

        /**
         * Set the given <i>value</i> on the {@link Tile} at the given <i>row</i> and <i>column</i>.
         * <p/>
         * The {@link Tile} attributes {@link Tile#notMergeThisTurn} and
         * {@link Tile#justCreated} will be both <code>false</code>.
         *
         * @param value  The value to settle.
         * @param row    The target {@link Tile}'s row.
         * @param column The target {@link Tile}'s column.
         */
        public void setValue(int value, int row, int column) {

            setValue(value, row, column, false);
        }

        /**
         * Set the given <i>value</i> on the {@link Tile} at the given <i>row</i> and
         * <i>column</i>, and also apply it's {@link Tile#notMergeThisTurn} property.
         * <p/>
         * The {@link Tile} attribute {@link Tile#justCreated} will be <code>false</code>.
         *
         * @param value          The value to settle.
         * @param row            The target {@link Tile}'s row.
         * @param column         The target {@link Tile}'s column.
         * @param shouldNotMerge Give <code>true</code> if this {@link Tile} should not be merged
         *                       by other {@link Tile}s this turn.
         */
        public void setValue(int value, int row, int column, boolean shouldNotMerge) {

            setValue(value, row, column, shouldNotMerge, false);
        }

        /**
         * Set the given <i>value</i> on the {@link Tile} at the given <i>row</i> and
         * <i>column</i>, and also apply it's {@link Tile#notMergeThisTurn} property.
         * <p/>
         * If this method is called by creating a fresh NEW {@link Tile}, pass <code>true</code> on
         * the <i>justCreated</i> field.
         *
         * @param value          The value to settle.
         * @param row            The target {@link Tile}'s row.
         * @param column         The target {@link Tile}'s column.
         * @param shouldNotMerge Give <code>true</code> if this {@link Tile} should not be merged
         *                       by other {@link Tile}s this turn.
         * @param justCreated    Give <code>true</code> if this method was called for creating a
         *                       fresh NEW {@link Tile}.
         */
        public void setValue(int value, int row, int column, boolean shouldNotMerge,
                             boolean justCreated) {

            tiles[row][column].setValue(value);
            tiles[row][column].setNotMergeThisTurn(shouldNotMerge);
            tiles[row][column].setJustCreated(justCreated);
        }

        /**
         * <b>Important:</b> Remember to call this method when the turn finishes, as it will reset
         * the flags of all <i>tiles</i>.
         * <p/>
         * This means, every {@link Tile} will have it's attributes {@link Tile#notMergeThisTurn}
         * and {@link Tile#justCreated} to <code>false</code>.
         */
        public void endTurn() {

            for (int indexR = 0; indexR < getRows(); indexR++) {

                for (int indexC = 0; indexC < getColumns(); indexC++) {

                    tiles[indexR][indexC].setNotMergeThisTurn(false);
                    tiles[indexR][indexC].setJustCreated(false);
                }
            }
        }
    }

    /**
     * Convenience class to reference the <i>tiles</i> contained on the board.
     */
    public static class Tile {

        /**
         * The integer value. For a non-settled value, it will be {@link Engine#VOID_VALUE}.
         */
        private int value;
        /**
         * Flag indicating this <i>tile</i> was created this turn (not on creation by merge)
         */
        private boolean justCreated;
        /**
         * Flag indicating this <i>tile</i> must not be merged this turn with other <i>tiles</i>.
         */
        private boolean notMergeThisTurn;

        /**
         * Constructor for {@link Tile}.
         */
        Tile() {

            setValue(VOID_VALUE);
            setJustCreated(false);
            setNotMergeThisTurn(false);
        }

        /**
         * Constructor for {@link Tile}.
         *
         * @param source Will create a copy of the given {@link Tile}.
         */
        Tile(Tile source) {

            this.value = source.value;
            this.justCreated = source.justCreated;
            this.notMergeThisTurn = source.notMergeThisTurn;
        }

        /**
         * Return a copy of this {@link Tile}.
         */
        public static Tile clone(Tile tile) {

            return new Tile(tile);
        }

        /**
         * Get the integer value of this {@link Tile}.
         *
         * @return The integer value of this {@link Tile}.
         */
        public int getValue() {

            return value;
        }

        /**
         * Set the integer value of this {@link Tile}.
         *
         * @param value The integer value of this {@link Tile}.
         */
        public void setValue(int value) {

            this.value = value;
        }

        /**
         * Get whether this {@link Tile} is void (don't have any value) or
         * not.
         *
         * @return Will return <code>true</code> if this {@link Tile} has no value, and
         * <code>false</code> otherwise.
         */
        public boolean isVoid() {

            return getValue() == VOID_VALUE;
        }

        /**
         * Get whether this {@link Tile} should not be merged this turn.
         * <p/>
         * <i>(A {@link Tile} should not be merged more than one time each turn)</i>
         *
         * @return Whether this {@link Tile} should not be merged this turn.
         */
        public boolean shouldNotMergeThisTurn() {

            return notMergeThisTurn;
        }

        /**
         * Set whether this {@link Tile} should not be merged this turn.
         *
         * @param notMergeThisTurn Set <code>true</code> if this {@link Tile} should not be merged
         *                         this turn, or <code>false</code> otherwise.
         */
        public void setNotMergeThisTurn(boolean notMergeThisTurn) {

            this.notMergeThisTurn = notMergeThisTurn;
        }

        /**
         * Get whether this {@link Tile} was created by the controller in this turn.
         *
         * @return Whether this {@link Tile} was created by the controller this turn.
         */
        public boolean isJustCreated() {

            return justCreated;
        }

        /**
         * Set whether this {@link Tile} was created this turn by the controller.
         *
         * @param justCreated Set <code>true</code> if this {@link Tile} was created this turn by
         *                    the controller, or <code>false</code> otherwise.
         */
        public void setJustCreated(boolean justCreated) {

            this.justCreated = justCreated;
        }
    }

//    /**
//     * Container for everything that happened on a play turn.
//     */
//    public class Turn {
//
//        /**
//         * The resulting {@link PlayResult}.
//         */
//        ArrayList<PlayResult> playList;
//        /**
//         * The game {@link UUID}.
//         */
//        private UUID gameUUID;
//        /**
//         * The turn number.
//         */
//        private int turnNumber;
//        /**
//         * The {@link Direction} played.
//         */
//        private Direction playDirection;
//
//        /**
//         * Constructor for {@link Turn}.
//         *
//         * @param gameUUID      The game {@link UUID}.
//         * @param turnNumber    The turn number.
//         * @param playDirection The play direction.
//         * @param playList      the resulting {@link PlayResult}.
//         */
//        Turn(UUID gameUUID, int turnNumber, Direction playDirection, ArrayList<PlayResult> playList) {
//
//            this.gameUUID = gameUUID;
//            this.turnNumber = turnNumber;
//            this.playDirection = playDirection;
//            this.playList = playList;
//        }
//    }
}
