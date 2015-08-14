package cat.santi.mod.gameboard.impl;

import cat.santi.mod.gameboard.TTFEGameBoard;

/**
 * Basic implementation of the TTFEGameBoard class.
 */
public class TTFEGameBoardImpl extends SquareGameBoardImpl<TileImpl>
        implements TTFEGameBoard<TileImpl> {

    private int mMaxValue;

    public TTFEGameBoardImpl() {
        super();

        mMaxValue = 0;
    }

    @Override
    public int getMaxValue() {
        return mMaxValue;
    }
}
