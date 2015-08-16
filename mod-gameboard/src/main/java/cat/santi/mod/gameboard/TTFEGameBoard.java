package cat.santi.mod.gameboard;

/**
 * The game board interface fot the TTFE game.
 */
public interface TTFEGameBoard<E extends Tile> extends SquareGameBoard<E> {

    /**
     * Direction to play an 'UP' move.
     */
    int DIRECTION_UP = 0;
    /**
     * Direction to play a 'DOWN' move.
     */
    int DIRECTION_DOWN = 1;
    /**
     * Direction to play a 'RIGHT' move.
     */
    int DIRECTION_RIGHT = 2;
    /**
     * Direction to play a 'LEFT' move.
     */
    int DIRECTION_LEFT = 3;

    /**
     * Get the max value reached currently in this board.
     *
     * @return The max value of the board.
     */
    int getMaxValue();

    /**
     * Make a play, moving all tile to the given <i>direction</i>.
     *
     * @param direction The direction in which to move all the tiles.
     */
    void play(int direction);
}
