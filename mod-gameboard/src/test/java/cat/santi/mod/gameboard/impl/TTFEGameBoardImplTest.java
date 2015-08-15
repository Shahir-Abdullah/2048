package cat.santi.mod.gameboard.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cat.santi.mod.gameboard.TTFEGameBoard;
import cat.santi.mod.gameboard.Tile;

/**
 * Tests for {@link TTFEGameBoardImplTest} class.
 */
public class TTFEGameBoardImplTest {

    /**
     * The TTFE game board instance to test.
     */
    private TTFEGameBoard<Tile> mTTFEGameBoard;

    @Before
    public void setUp() {
        // Create a default new ttfe game board
        mTTFEGameBoard = new TTFEGameBoardImpl<>();
    }

    @Test
    public void testGetMaxValue() throws Exception {
        //Put some tiles in the board
        Tile someTile = new TileImpl();
        someTile.setValue(8);
        Tile anotherTile = new TileImpl();
        anotherTile.setValue(256);
        Tile yetAnotherTile = new TileImpl();
        yetAnotherTile.setValue(32);
        Tile lastTile = new TileImpl();
        lastTile.setValue(128);
        mTTFEGameBoard.put(someTile, 0, 0);
        mTTFEGameBoard.put(anotherTile, 1, 1);
        mTTFEGameBoard.put(yetAnotherTile, 2, 2);
        mTTFEGameBoard.put(lastTile, 3, 3);

        // Remove a couple tiles
        mTTFEGameBoard.remove(1, 1);
        mTTFEGameBoard.remove(2, 2);

        // Test max value
        Assert.assertEquals(128, mTTFEGameBoard.getMaxValue());
    }
}