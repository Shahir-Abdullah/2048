package cat.santi.mod.gameboard.impl;

import com.google.gson.Gson;

import cat.santi.mod.gameboard.SquareGameBoard;
import cat.santi.mod.gameboard.exception.NotEmptyException;
import cat.santi.mod.gameboard.exception.NotFoundException;

/**
 * Implementation of the square game board. This board has been conceived having it's initial
 * position, which is {@code [0,0]}, at the top left corner. Positive values move the position
 * down and right, knowing those as the {@code x} and {@code y} axis. For example, a board of
 * {@code width = 3} and {@code height = 2} would be one with three columns and two rows. See the
 * example below with a board of 4 by 4:
 * <pre>
 *     0   1   2     (width - x axis - 3 columns)
 *  0 [ ] [ ] [ ]
 *  1 [ ] [ ] [X] <- The X value is at [2, 1]
 *
 *  (height - y axis - 2 rows)
 * </pre>
 * As pictured in the schema, and applying to all methods of this class, every time a coordinate
 * input is required, it will always be in the form of {@code (column, row)}.
 */
public class SquareGameBoardImpl<Type> implements SquareGameBoard<Type> {

    /**
     * Default width.
     */
    public static final int DEFAULT_WIDTH = 4;
    /**
     * Default height.
     */
    public static final int DEFAULT_HEIGHT = 4;

    /**
     * The matrix of objects.
     */
    private Object[][] mBoardMatrix;

    // Store width, height and count values for quick access

    /**
     * The width of the board.
     */
    private int mWidth;
    /**
     * The height of the board.
     */
    private int mHeight;
    /**
     * The current number of objects in the board.
     */
    private int mCount;

    /**
     * Create a new board with {@value #DEFAULT_WIDTH} width and {@value #DEFAULT_HEIGHT} height.
     */
    public SquareGameBoardImpl() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Create a new board of <i>width</i> and <i>height</i> size.
     *
     * @param width  The number of columns of the matrix.
     * @param height The number of rows of the matrix.
     */
    public SquareGameBoardImpl(int width, int height) {
        if (width <= 0)
            throw new IndexOutOfBoundsException("width <= 0");
        if (height <= 0)
            throw new IndexOutOfBoundsException("height <= 0");

        mWidth = width;
        mHeight = height;
        mBoardMatrix = new Object[getWidth()][getWidth()];
        mCount = 0;
    }

    /**
     * Create a new board from the given <i>snapshot</i>.
     *
     * @param snapshot The previous board to copy.
     */
    public SquareGameBoardImpl(SquareGameBoard snapshot) {
        if (snapshot == null)
            throw new IllegalArgumentException("snapshot == null");

        mWidth = snapshot.getWidth();
        mHeight = snapshot.getHeight();
        mBoardMatrix = snapshot.getBoardMatrix();
        mCount = snapshot.getCount();
    }

    @Override
    public int getWidth() {
        return mWidth;
    }

    @Override
    public int getHeight() {
        return mHeight;
    }

    @Override
    public IntPair find(Type object) {
        if (object != null && !isEmpty())
            for (int w = 0; w < getWidth(); w++)
                for (int h = 0; h < getHeight(); h++)
                    if (object.equals(get(w, h)))
                        return new IntPairImpl(w, h);
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Type get(int col, int row) {
        if (col < 0 || col >= getHeight() || row < 0 || row >= getHeight())
            throw new IndexOutOfBoundsException();
        return (Type) mBoardMatrix[col][row];
    }

    @Override
    public void move(int colStart, int rowStart, int colEnd, int rowEnd) throws NotEmptyException {
        if (colStart < 0 || colStart >= getHeight() || rowStart < 0 || rowStart >= getHeight())
            throw new IndexOutOfBoundsException();
        if (colEnd < 0 || colEnd >= getHeight() || rowEnd < 0 || rowEnd >= getHeight())
            throw new IndexOutOfBoundsException();
        if (!isEmpty(colEnd, rowEnd))
            throw new NotEmptyException();
        put(remove(colStart, rowStart), colEnd, rowEnd);
    }

    @Override
    public void move(Type object, int colEnd, int rowEnd)
            throws NotFoundException, NotEmptyException {
        final IntPair position = find(object);
        if (position == null)
            throw new NotFoundException();
        move(position.getColumn(), position.getRow(), colEnd, rowEnd);
    }

    @Override
    public Type put(Type object, int col, int row) {
        if (col < 0 || col >= getHeight() || row < 0 || row >= getHeight())
            throw new IndexOutOfBoundsException();
        final Type prevItem = get(col, row);
        mBoardMatrix[col][row] = object;
        if (object != null)
            mCount++;
        return prevItem;
    }

    @Override
    public Type remove(int col, int row) {
        if (col < 0 || col >= getHeight() || row < 0 || row >= getHeight())
            throw new IndexOutOfBoundsException();
        final Type prevItem = put(null, col, row);
        if (prevItem != null)
            mCount--;
        return prevItem;
    }

    @Override
    public boolean has(Type object) {
        return find(object) != null;
    }

    @Override
    public boolean isEmpty(int col, int row) {
        if (col < 0 || col >= getHeight() || row < 0 || row >= getHeight())
            throw new IndexOutOfBoundsException();
        return get(col, row) == null;
    }

    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Snapshot takeSnapshot() {
        return new SnapshotImpl(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Type[][] getBoardMatrix() {
        return (Type[][]) mBoardMatrix;
    }

    @Override
    public boolean same(SquareGameBoard sample) {
        if (sample == null)
            return false;
        if (sample.getWidth() != getWidth() || sample.getHeight() != getHeight())
            return false;
        if (sample.getCount() != getCount())
            return false;
        for (int width = 0; width < getWidth(); width++)
            for (int height = 0; height < getHeight(); height++)
                if (sample.isEmpty(width, height) != isEmpty(width, height))
                    return false;
                else if (!sample.isEmpty(width, height)
                        && !sample.get(width, height).equals(get(width, height)))
                    return false;
        return true;
    }

    /**
     * Container for a pair of {@code int} values.
     */
    public static class IntPairImpl implements IntPair {

        /**
         * X value.
         */
        private final int x;
        /**
         * Y value.
         */
        private final int y;

        /**
         * Constructor.
         *
         * @param x The X value.
         * @param y The Y value.
         */
        public IntPairImpl(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int getColumn() {
            return x;
        }

        @Override
        public int getRow() {
            return y;
        }
    }

    /**
     * Implementation for the Snapshot class.
     */
    public static class SnapshotImpl implements Snapshot {

        /**
         * The Json data of the board.
         */
        private String mJsonData;

        /**
         * Constructor. Creates a snapshot with the given <i>data</i>, which can't be {@code null}.
         *
         * @param data The data to create the snapshot.
         */
        public SnapshotImpl(Object data) {
            if (data == null)
                throw new IllegalArgumentException("data == null");
            mJsonData = new Gson().toJson(data);
        }

        @Override
        public String getJson() {
            return mJsonData;
        }

        @Override
        public <Type> Type from(Class<Type> classOfType) throws ClassNotFoundException {
            return new Gson().fromJson(mJsonData, classOfType);
        }
    }
}
