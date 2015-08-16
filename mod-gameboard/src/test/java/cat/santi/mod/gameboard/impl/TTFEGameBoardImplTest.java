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

    @Test
    public void testPlayUp() {
        // Put some tiles in the board
        fillTestBoard();
        // Play an 'LEFT' move
        mTTFEGameBoard.play(TTFEGameBoard.DIRECTION_UP);
        // Check result
        assertTestBoardUp();
    }

    @Test
    public void testPlayDown() {
        // Put some tiles in the board
        fillTestBoard();
        // Play an 'LEFT' move
        mTTFEGameBoard.play(TTFEGameBoard.DIRECTION_DOWN);
        // Check result
        assertTestBoardDown();
    }

    @Test
    public void testPlayRight() {
        // Put some tiles in the board
        fillTestBoard();
        // Play an 'LEFT' move
        mTTFEGameBoard.play(TTFEGameBoard.DIRECTION_RIGHT);
        // Check result
        assertTestBoardRight();
    }

    @Test
    public void testPlayLeft() {
        // Put some tiles in the board
        fillTestBoard();
        // Play an 'LEFT' move
        mTTFEGameBoard.play(TTFEGameBoard.DIRECTION_LEFT);
        // Check result
        assertTestBoardLeft();
    }

    /**
     * Fill a test board with values
     */
    private void fillTestBoard() {
        // Create some tiles
        Tile someTileA = new TileImpl(2);
        Tile someTileB = new TileImpl(2);
        Tile someTileC = new TileImpl(4);
        Tile someTileD = new TileImpl(2);
        Tile someTileE = new TileImpl(4);
        Tile someTileF = new TileImpl(4);
        Tile someTileG = new TileImpl(4);
        Tile someTileH = new TileImpl(2);
        Tile someTileI = new TileImpl(4);
        Tile someTileJ = new TileImpl(4);
        
        // Put them in the board
        mTTFEGameBoard.put(someTileA, 0, 0);
        mTTFEGameBoard.put(someTileB, 0, 2);
        mTTFEGameBoard.put(someTileC, 1, 0);
        mTTFEGameBoard.put(someTileD, 1, 1);
        mTTFEGameBoard.put(someTileE, 1, 3);
        mTTFEGameBoard.put(someTileF, 2, 1);
        mTTFEGameBoard.put(someTileG, 2, 3);
        mTTFEGameBoard.put(someTileH, 3, 0);
        mTTFEGameBoard.put(someTileI, 3, 2);
        mTTFEGameBoard.put(someTileJ, 3, 3);
    }

    /**
     * Assert an 'UP' move done to the test boad.
     */
    private void assertTestBoardUp() {
        // Assert the tiles in the board
        Assert.assertEquals(4, mTTFEGameBoard.get(0, 0).getValue());
        Assert.assertEquals(4, mTTFEGameBoard.get(1, 0).getValue());
        Assert.assertEquals(2, mTTFEGameBoard.get(1, 1).getValue());
        Assert.assertEquals(4, mTTFEGameBoard.get(1, 2).getValue());
        Assert.assertEquals(8, mTTFEGameBoard.get(2, 0).getValue());
        Assert.assertEquals(2, mTTFEGameBoard.get(3, 0).getValue());
        Assert.assertEquals(8, mTTFEGameBoard.get(3, 1).getValue());

        // Assert the max value is the expected
        Assert.assertEquals(8, mTTFEGameBoard.getMaxValue());

        // Assert there are the expected amount of tiles in the board
        Assert.assertEquals(7, mTTFEGameBoard.getCount());
    }

    /**
     * Assert a 'DOWN' move done to the test boad.
     */
    private void assertTestBoardDown() {
        // Assert the tiles in the board
        Assert.assertEquals(4, mTTFEGameBoard.get(0, 3).getValue());
        Assert.assertEquals(4, mTTFEGameBoard.get(1, 1).getValue());
        Assert.assertEquals(2, mTTFEGameBoard.get(1, 2).getValue());
        Assert.assertEquals(4, mTTFEGameBoard.get(1, 3).getValue());
        Assert.assertEquals(8, mTTFEGameBoard.get(2, 3).getValue());
        Assert.assertEquals(2, mTTFEGameBoard.get(3, 2).getValue());
        Assert.assertEquals(8, mTTFEGameBoard.get(3, 3).getValue());

        // Assert the max value is the expected
        Assert.assertEquals(8, mTTFEGameBoard.getMaxValue());

        // Assert there are the expected amount of tiles in the board
        Assert.assertEquals(7, mTTFEGameBoard.getCount());
    }

    /**
     * Assert a 'RIGHT' move done to the test boad.
     */
    private void assertTestBoardRight() {
        // Assert the tiles in the board
        Assert.assertEquals(2, mTTFEGameBoard.get(1, 0).getValue());
        Assert.assertEquals(4, mTTFEGameBoard.get(2, 0).getValue());
        Assert.assertEquals(2, mTTFEGameBoard.get(2, 1).getValue());
        Assert.assertEquals(2, mTTFEGameBoard.get(2, 2).getValue());
        Assert.assertEquals(4, mTTFEGameBoard.get(2, 3).getValue());
        Assert.assertEquals(2, mTTFEGameBoard.get(3, 0).getValue());
        Assert.assertEquals(4, mTTFEGameBoard.get(3, 1).getValue());
        Assert.assertEquals(4, mTTFEGameBoard.get(3, 2).getValue());
        Assert.assertEquals(8, mTTFEGameBoard.get(3, 3).getValue());

        // Assert the max value is the expected
        Assert.assertEquals(8, mTTFEGameBoard.getMaxValue());

        // Assert there are the expected amount of tiles in the board
        Assert.assertEquals(9, mTTFEGameBoard.getCount());
    }

    /**
     * Assert a 'LEFT' move done to the test boad.
     */
    private void assertTestBoardLeft() {
        // Assert the tiles in the board
        Assert.assertEquals(2, mTTFEGameBoard.get(0, 0).getValue());
        Assert.assertEquals(2, mTTFEGameBoard.get(0, 1).getValue());
        Assert.assertEquals(2, mTTFEGameBoard.get(0, 2).getValue());
        Assert.assertEquals(8, mTTFEGameBoard.get(0, 3).getValue());
        Assert.assertEquals(4, mTTFEGameBoard.get(1, 0).getValue());
        Assert.assertEquals(4, mTTFEGameBoard.get(1, 1).getValue());
        Assert.assertEquals(4, mTTFEGameBoard.get(1, 2).getValue());
        Assert.assertEquals(4, mTTFEGameBoard.get(1, 3).getValue());
        Assert.assertEquals(2, mTTFEGameBoard.get(2, 0).getValue());

        // Assert the max value is the expected
        Assert.assertEquals(8, mTTFEGameBoard.getMaxValue());

        // Assert there are the expected amount of tiles in the board
        Assert.assertEquals(9, mTTFEGameBoard.getCount());
    }
}