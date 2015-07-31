package cat.santi.ttfe.exception;

/**
 * Exception thrown when trying to find an available board space, being it completely full.
 * 
 * @author Santiago Gonzalez
 */
public class BoardFullException extends TTFEException {

    private static final String MESSAGE = "No more squares available on the board";

	/**
	 * Constructor.
	 */
	BoardFullException() {
		super(MESSAGE);
	}

    /**
     * Constructor.
     *
     * @param cause The cause that triggered this exception.
     */
	BoardFullException(Throwable cause) {
		super(MESSAGE, cause);
	}
}
