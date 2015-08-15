package cat.santi.mod.gameboard.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cat.santi.mod.gameboard.SquareGameBoard;

/**
 *
 */
public class SquareGameBoardImplTest {

    private SquareGameBoard<Object> mSquareGameBoard;

    @Before
    public void setUp() {
        // Create a default new game board
        mSquareGameBoard = new SquareGameBoardImpl<>();
    }

    @Test
    public void sizeTest() {
        // Test the board is DEFAULT_WIDTH x DEFAULT_HEIGHT
        final int EXPECTED_WIDTH = SquareGameBoardImpl.DEFAULT_WIDTH;
        final int EXPECTED_HEIGHT = SquareGameBoardImpl.DEFAULT_HEIGHT;
        Assert.assertEquals(EXPECTED_WIDTH, mSquareGameBoard.getWidth());
        Assert.assertEquals(EXPECTED_HEIGHT, mSquareGameBoard.getHeight());
    }

    @Test
    public void accessTest() {
        // Put an object in the board
        Object someObject = new Object();
        mSquareGameBoard.put(someObject, 2, 3);

        // Put another object in the board
        Object anotherObject = new Object();
        mSquareGameBoard.put(anotherObject, 1, 2);

        // Remove that second object
        mSquareGameBoard.remove(1, 2);

        // Replace the first object with another new one
        Object replacingObject = new Object();
        mSquareGameBoard.put(replacingObject, 2, 3);

        // Test get operation
        Assert.assertNotNull(mSquareGameBoard.get(2, 3));
        // Test put and replace operations
        Assert.assertEquals(replacingObject, mSquareGameBoard.get(2, 3));
        // Test remove operation
        Assert.assertNull(mSquareGameBoard.get(1, 2));
    }

    @Test
    public void findTest() {
        // Put some object in the board
        Object someObject = new Object();
        mSquareGameBoard.put(someObject, 2, 3);

        // Find the position of that object
        SquareGameBoard.IntPair position = mSquareGameBoard.find(someObject);
        Object foundObject = mSquareGameBoard.get(position.getColumn(), position.getRow());

        // Test the object found is the expected
        Assert.assertEquals(someObject, foundObject);
    }

    @Test
    public void moveTest() {
        // Put a couple objects in the board edges
        Object someObject = new Object();
        mSquareGameBoard.put(someObject, 1, 3);
        Object anotherObject = new Object();
        mSquareGameBoard.put(anotherObject, 0, 1);

        // Move those objects to the opposite edges
        mSquareGameBoard.move(1, 3, 1, 0);
        mSquareGameBoard.move(anotherObject, 3, 1);

        // Test the objects are in the correct position
        final Object EXPECTED_OBJECT_A = mSquareGameBoard.get(1, 0);
        final Object EXPECTED_OBJECT_B = mSquareGameBoard.get(3, 1);
        Assert.assertEquals(EXPECTED_OBJECT_A, someObject);
        Assert.assertEquals(EXPECTED_OBJECT_B, anotherObject);
    }

    @Test
    public void hasTest() {
        // Put some object in the game board
        Object someObject = new Object();
        mSquareGameBoard.put(someObject, 1, 1);

        // Test has
        Assert.assertTrue(mSquareGameBoard.has(someObject));
    }

    @Test
    public void isEmptyTest() {
        // Put some object in the game board
        Object someObject = new Object();
        mSquareGameBoard.put(someObject, 1, 1);

        // Test isEmpty methods
        Assert.assertFalse(mSquareGameBoard.isEmpty());
        Assert.assertFalse(mSquareGameBoard.isEmpty(1, 1));
    }

    @Test
    public void countTest() {
        // Fill the board with objects
        for (int w = 0; w < mSquareGameBoard.getWidth(); w++)
            for (int h = 0; h < mSquareGameBoard.getHeight(); h++)
                mSquareGameBoard.put(new Object(), w, h);

        // Test the count
        final int EXPECTED_COUNT = mSquareGameBoard.getWidth() * mSquareGameBoard.getHeight();
        Assert.assertEquals(EXPECTED_COUNT, mSquareGameBoard.getCount());
    }
}
