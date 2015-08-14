package cat.santi.mod.gameboard.exception;

/**
 * Exception raised when the position is not empty.
 */
public class NotEmptyException extends RuntimeException {

    /**
     * Constructor.
     */
    public NotEmptyException() {
        super("Not empty");
    }
}
