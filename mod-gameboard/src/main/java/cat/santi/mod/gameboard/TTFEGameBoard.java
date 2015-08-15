package cat.santi.mod.gameboard;

/**
 * The game board interface fot the TTFE game.
 */
public interface TTFEGameBoard<E extends Tile> extends SquareGameBoard<E> {

    /**
     * Get the max value reached currently in this board.
     *
     * @return The max value of the board.
     */
    int getMaxValue();
}
