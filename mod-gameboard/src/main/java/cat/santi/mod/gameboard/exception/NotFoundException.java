package cat.santi.mod.gameboard.exception;

/**
 * Exception raised when an object could not be found.
 */
public class NotFoundException extends RuntimeException {

    /**
     * Constructor.
     */
    public NotFoundException() {
        super("Not found");
    }
}
