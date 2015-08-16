package cat.santi.mod.gameboard;

/**
 * Time to play the TTFE game.
 */
public interface Tile {

    /**
     * Get the tile value.
     *
     * @return The tile value.
     */
    int getValue();

    /**
     * Set the tile value.
     *
     * @param value The tile value.
     */
    void setValue(int value);

    /**
     * Check whether or not this tile has been merged this turn. Tiles that have merged should not
     * be merged again. Call {@link #setMergedThisTurn(boolean)} with a {@code false} value to
     * reset this flag at the end of turn.
     *
     * @return {@code true} if this tile has already merged this turn, {@code false} otherwise.
     */
    boolean hasMergedThisTurn();

    /**
     * Set whether or not this tile has been merged this turn. Set this flag to {@code true} when
     * a merge move is done, and set it back to {@code false} when the turn has ended.
     *
     * @param mergedThisTurn Whether or not the tile has been merged this turn.
     */
    void setMergedThisTurn(boolean mergedThisTurn);
}
