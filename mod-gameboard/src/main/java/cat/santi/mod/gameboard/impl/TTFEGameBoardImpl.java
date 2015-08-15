package cat.santi.mod.gameboard.impl;

import cat.santi.mod.gameboard.TTFEGameBoard;
import cat.santi.mod.gameboard.Tile;

/**
 * Basic implementation of the TTFEGameBoard class.
 */
public class TTFEGameBoardImpl<Type extends Tile> extends SquareGameBoardImpl<Type>
        implements TTFEGameBoard<Type> {

    private int mMaxValue;

    public TTFEGameBoardImpl() {
        super();

        mMaxValue = 0;
    }

    @Override
    public int getMaxValue() {
        return mMaxValue;
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
}
