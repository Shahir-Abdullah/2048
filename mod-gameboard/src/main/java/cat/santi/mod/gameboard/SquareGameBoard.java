package cat.santi.mod.gameboard;

import java.io.Serializable;

import cat.santi.mod.gameboard.exception.NotEmptyException;
import cat.santi.mod.gameboard.exception.NotFoundException;

/**
 * Square game board with basic operations.
 */
public interface SquareGameBoard<Type> {

    /**
     * The width of the game board.
     *
     * @return The number of columns.
     */
    int getWidth();

    /**
     * The height of the game board.
     *
     * @return The number of rows.
     */
    int getHeight();

    /**
     * Find the coordinates of the provided <i>object</i> in the game board. If the <i>object</i>
     * is {@code null} or was not found, then {@code null} will be returned, otherwise the
     * IntPair object will have its coordinates.
     *
     * @param object The object to be found.
     * @return The coordinates of the object, or {@code null} if not found.
     */
    IntPair find(Type object);

    /**
     * Get the object at the specified <i>column</i> and <i>row</i>.
     *
     * @param col The column.
     * @param row The row.
     * @return The object at the location, or {@code null} if it was empty.
     */
    Type get(int col, int row);

    /**
     * Move the object at <i>colStart</i> and <i>rowStart</i> to the specified <i>colEnd</i> and
     * <i>rowEnd</i>. If the ending position is occupied, then a NotEmptyException will be thrown.
     *
     * @param colStart The starting column.
     * @param rowStart The staring row.
     * @param colEnd   The ending column.
     * @param rowEnd   The ending row.
     * @throws NotEmptyException If the ending position was occupied.
     */
    void move(int colStart, int rowStart, int colEnd, int rowEnd) throws NotEmptyException;

    /**
     * Move the given <i>object</i> in the board to the specified <i>colEnd</i> and <i>rowEnd</i>.
     * If the ending position is occupied, then a NotEmptyException will be thrown.
     *
     * @param object The object to be moved.
     * @param colEnd The ending column.
     * @param rowEnd The ending row.
     * @throws NotFoundException If the object was not focund in the board.
     * @throws NotEmptyException If the ending position was occupied.
     */
    void move(Type object, int colEnd, int rowEnd) throws NotFoundException, NotEmptyException;

    /**
     * Put the given <i>object</i> to the specified <i>column</i> and <i>row</i>. This will
     * override any object on that position, which would be returned at the end of the execution.
     * The {@code null} value can be placed, which will simply replace any previous object.
     *
     * @param object The object to be placed, or {@code null} to remove any previous object.
     * @param col    The column in which to place the object.
     * @param row    The row in which to place the object.
     * @return The previous value at the position, if any
     */
    Type put(Type object, int col, int row);

    /**
     * Remove the object at the specified <i>column</i> and <i>row</i>. The removed object, if any,
     * will be returned.
     *
     * @param col The column.
     * @param row The row.
     * @return The previous object, or {@code null} if the position was empty.
     */
    Type remove(int col, int row);

    /**
     * Check whether or not the game board contains the given <i>object</i>.
     *
     * @param object The object to look for.
     * @return {@code true} if the object was found, or {@code false} otherwise.
     */
    boolean has(Type object);

    /**
     * Check whether or not the position specified by the given <i>column</i> and <i>row</i> is
     * empty or not.
     *
     * @param col The colunm to check against.
     * @param row The row to check against.
     * @return {@code true} if the position is empty, or {@code false} otherwise.
     */
    boolean isEmpty(int col, int row);

    /**
     * Check whether or not the game board is empty.
     *
     * @return {@code true} if the board contains no objects, or {@code false} otherwise.
     */
    boolean isEmpty();

    /**
     * Get the total number of objects currently present in the game board.
     *
     * @return The object this board contains.
     */
    int getCount();

    /**
     * Take an snapshot of the current board. It can later create a new board.
     */
    Snapshot takeSnapshot();

    /**
     * Get the board matrix representation of the board. This method is intended to used to create
     * a board from an snapshot. You should not use this method.
     *
     * @return The matrix game board.
     */
    Type[][] getBoardMatrix();

    /**
     * Check whether or not this board is the same size and has the same objects as the given
     * <i>sample</i>.
     *
     * @param sample The sample to check against.
     * @return {@code true} if the boards matches, {@code false} otherwise.
     */
    boolean same(SquareGameBoard sample);

    /**
     * Basic pair of {@code int} values.
     */
    interface IntPair {

        /**
         * @return The X value.
         */
        int getColumn();

        /**
         * @return The Y value.
         */
        int getRow();
    }

    /**
     * An snapshot is a container for a board state. With them it's possible to easily store and
     * resume a game. The class implements the {@link Serializable} interface;
     */
    interface Snapshot extends Serializable {

        /**
         * Get the board data as a Json string.
         *
         * @return The board data Json formatted.
         */
        String getJson();

        /**
         * Get a new board from the data of this snapshot, represented in the given
         * <i>classOfType</i> format.
         *
         * @return The new board in a class of <i>classOfType</i>.
         */
        <Type> Type from(Class<Type> classOfType) throws ClassNotFoundException;
    }
}
