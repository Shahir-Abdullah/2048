package cat.santi.mod.gameboard.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cat.santi.mod.gameboard.Tile;

/**
 * Tests for {@link TileImpl} class.
 */
public class TileImplTest {

    /**
     * The tile instance to test.
     */
    private Tile mTile;

    @Before
    public void setUp() {
        // Create a new tile
        mTile = new TileImpl(16);
    }

    @Test
    public void testGetValue() {
        // Test the value is expected
        Assert.assertEquals(16, mTile.getValue());
    }

    @Test
    public void testSetValue() {
        // Set a new value
        mTile.setValue(64);
        // Test the value is expected
        Assert.assertEquals(64, mTile.getValue());
    }
}
