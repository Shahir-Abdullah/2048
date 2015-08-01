package cat.santi.ttfe.exception;

/**
 * Factory class to instantiate exceptions.
 *
 * @author Santiago Gonzalez
 */
public class ExceptionFactory {

    private ExceptionFactory() {
        // Private constructor to thwart instantiation
    }

    /**
     * Create a {@link BoardFullException}, with the given optional <i>cause</i>, if any.
     *
     * @param cause The cause that triggered the exception. Can be {@code null}.
     * @return A new exception created.
     */
    public static TTFEException createBoardFullException(Throwable cause) {
        return cause != null ? new BoardFullException(cause) : new BoardFullException();
    }
}
