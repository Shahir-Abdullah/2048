package cat.santi.mod.gameboard.impl;

import cat.santi.mod.gameboard.Tile;

/**
 * Basic implementation for the Tile class.
 */
public class TileImpl implements Tile {

    private int mValue;

    public TileImpl() {
        this(0);
    }

    public TileImpl(int value) {
        mValue = value;
    }

    @Override
    public int getValue() {
        return mValue;
    }

    @Override
    public void setValue(int value) {
        mValue = value;
    }
}
