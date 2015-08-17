package cat.santi.mod.gameboard.impl;

import cat.santi.mod.gameboard.Tile;

/**
 * Basic implementation for the Tile class.
 */
public class TileImpl implements Tile {

    /**
     * The tile value.
     */
    private int mValue;
    /**
     * Whether or not it has been merged with another tile this turn.
     */
    private boolean mMergedThisTurn;

    /**
     * Constructor.
     */
    public TileImpl() {
        this(0);
    }

    /**
     * Constructor.
     */
    public TileImpl(int value) {
        mValue = value;
        mMergedThisTurn = false;
    }

    @Override
    public int getValue() {
        return mValue;
    }

    @Override
    public void setValue(int value) {
        mValue = value;
    }

    @Override
    public boolean hasMergedThisTurn() {
        return mMergedThisTurn;
    }

    @Override
    public void setMergedThisTurn(boolean mergedThisTurn) {
        mMergedThisTurn = mergedThisTurn;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof TileImpl && ((TileImpl) obj).mValue == mValue;
    }
}
