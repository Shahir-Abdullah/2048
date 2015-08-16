package cat.santi.mod.gameboard.impl;

import cat.santi.mod.gameboard.TTFEGameBoard;
import cat.santi.mod.gameboard.Tile;

/**
 * Basic implementation of the TTFEGameBoard class.
 */
public class TTFEGameBoardImpl<Type extends Tile> extends SquareGameBoardImpl<Type>
        implements TTFEGameBoard<Type> {

    /**
     * Default width.
     */
    public static final int DEFAULT_WIDTH = 4;
    /**
     * Default height.
     */
    public static final int DEFAULT_HEIGHT = 4;

    /**
     * The highest value present in the board.
     */
    private int mMaxValue;

    /**
     * Create a new game board for TTFE.
     */
    public TTFEGameBoardImpl() {
        super(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        mMaxValue = 0;
    }

    @Override
    public int getMaxValue() {
        return mMaxValue;
    }

    @Override
    public void play(int direction) {
        switch (direction) {
            case DIRECTION_UP:
                playUpInternal();
                break;
            case DIRECTION_DOWN:
                playDownInternal();
                break;
            case DIRECTION_RIGHT:
                playRightInternal();
                break;
            case DIRECTION_LEFT:
                playLeftInternal();
                break;
        }
        endTurn();
    }

    @Override
    public Type put(Type tile, int col, int row) {
        // Put the tile in the board
        final Type result = super.put(tile, col, row);
        // Check if the max value has changed
        if (tile != null && tile.getValue() > getMaxValue())
            mMaxValue = tile.getValue();
        // Return the result
        return result;
    }

    @Override
    public Type remove(int col, int row) {
        // Remove the tile
        final Type result = super.remove(col, row);
        // Check if the max value has changed
        // If so, we need to check the entire board to see if there's another max value present
        if (result != null && result.getValue() == getMaxValue()) {
            mMaxValue = 0;
            for (int w = 0; w < getWidth(); w++)
                for (int h = 0; h < getHeight(); h++) {
                    final Type tile = get(w, h);
                    if (tile != null && tile.getValue() > getMaxValue())
                        mMaxValue = tile.getValue();
                }
        }
        // Return the result
        return result;
    }

    /**
     * Play an 'UP', move. Move all tiles up, merging the ones with same value, as long as the flag
     * 'mergedThisTurn' is set to false.
     */
    private void playUpInternal() {
        // Iterate left to right & up to down, from [0][1] to [size - 1[size - 1]
        for (int height = 1; height < getHeight(); height++)
            for (int width = 0; width < getWidth(); width++)
                moveTile(width, height, DIRECTION_UP);
    }

    /**
     * Play a 'DOWN', move. Move all tiles down, merging the ones with same value, as long as the
     * flag 'mergedThisTurn' is set to false.
     */
    private void playDownInternal() {
        // Iterate left to right & down to up, from [0][size - 2] to [size - 1][row 0]
        for (int height = getHeight() - 2; height >= 0; height--)
            for (int width = 0; width < getWidth(); width++)
                moveTile(width, height, DIRECTION_DOWN);
    }

    /**
     * Play a 'RIGHT', move. Move all tiles to the right, merging the ones with same value, as long
     * as the flag 'mergedThisTurn' is set to false.
     */
    private void playRightInternal() {
        // Iterate up to down & right to left, from [size - 2][0] to [0][size - 1]
        for (int width = getWidth() - 2; width >= 0; width--)
            for (int height = 0; height < getHeight(); height++)
                moveTile(width, height, DIRECTION_RIGHT);
    }

    /**
     * Play a 'LEFT', move. Move all tiles to the left, merging the ones with same value, as long
     * as the flag 'mergedThisTurn' is set to false.
     */
    private void playLeftInternal() {
        // Iterate up to down & left to right, from [1][0] to [size - 1][size - 1]
        for (int width = 1; width < getWidth(); width++)
            for (int height = 0; height < getHeight(); height++)
                moveTile(width, height, DIRECTION_LEFT);
    }

    /**
     * Move the tile in the given <i>col</i> and <i>row</i> to the given <i>direction</i> (use
     * integers fount in the implementing interface). If the given parameters are outside the
     * bounds of the game board, an exception will be thrown.
     *
     * @param col       The column of the tile to move.
     * @param row       The row of the tile to move.
     * @param direction The direction for moving the tile.
     */
    private void moveTile(int col, int row, int direction) {
        // Select the target column and row based on direction of play
        int targetCol = col, targetRow = row;
        switch (direction) {
            case DIRECTION_UP:
                targetCol = col;
                targetRow = row - 1;
                break;
            case DIRECTION_DOWN:
                targetCol = col;
                targetRow = row + 1;
                break;
            case DIRECTION_RIGHT:
                targetCol = col + 1;
                targetRow = row;
                break;
            case DIRECTION_LEFT:
                targetCol = col - 1;
                targetRow = row;
                break;
        }

        // Check the target is within the bounds
        if (targetCol < 0 || targetCol > getWidth() - 1
                || targetRow < 0 || targetRow > getHeight() - 1)
            return;

        // Get the instance of the tile and the target tile (if any)
        final Type tile = get(col, row);
        final Type targetTile = get(targetCol, targetRow);

        // Evaluate move and move on recursively (with 'targetRow' and 'targetCol') if needed
        if (evaluateMove(tile, targetTile, col, row, targetCol, targetRow))
            moveTile(targetCol, targetRow, direction);
    }

    /**
     * Evaluate the move from the given <i>tile</i> to the given <i>target</i>. The target can hold
     * various kinds of values, like {@code null} (which means there is no tile in the target),
     * <i>a value same of the one from the tile</i> (which could be merged, if the flag
     * 'mergedThisTurn' is set to {@code false}), or <i>any other tile</i> (which means the tile
     * could not move).
     * <p/>
     * This method will perform any needed moving operations between tiles, and will also keep
     * track of score changes. All this will be done using the <i>tileCol</i>, <i>tileRow</i>,
     * <i>targetCol</i> and <i>targetRow</i>. You must make sure those values hold the true
     * positions of <i>tile</i> and <i>target</i>.
     * <p/>
     * <i>Here is the mother of the lamb...</i>
     *
     * @param tile      The tile to be moved.
     * @param target    The tile to move onto.
     * @param tileCol   The column of the tile to be moved.
     * @param tileRow   The row of the tile to be moved.
     * @param targetCol The column of the target tile.
     * @param targetRow Thw row of the target tile.
     * @return {@code true} if the recursion should continue, {@code false} otherwise.
     */
    private boolean evaluateMove(Type tile, Type target, int tileCol, int tileRow, int targetCol,
                                 int targetRow) {
        if (tile == null) {
            // There is no tile to be moved
            return false; // Finish recursion
        } else if (target == null) {
            // There is no tile in the destination
            move(tileCol, tileRow, targetCol, targetRow);
            return true; // Continue recursion
        } else if (target.getValue() == tile.getValue() && !target.hasMergedThisTurn()) {
            // There is a tile with the same value in the destination, which has not merged already
            remove(targetCol, targetRow);
            move(tileCol, tileRow, targetCol, targetRow);
            tile.setValue(tile.getValue() * 2);
            tile.setMergedThisTurn(true);
            return false; // Finish recursion
        }
        // There is a tile with another value, or that tile has already merged
        return false; // Finish recursion
    }

    /**
     * End the turn, resetting the 'mergedThisTurn' flag.
     */
    private void endTurn() {
        for (int width = 0; width < getWidth(); width++)
            for (int height = 0; height < getHeight(); height++)
                if (!isEmpty(width, height))
                    get(width, height).setMergedThisTurn(false);
    }
}
