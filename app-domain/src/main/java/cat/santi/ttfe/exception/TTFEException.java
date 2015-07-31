package cat.santi.ttfe.exception;

/**
 * Base 2048 exception.
 * 
 * @author Santiago Gonzalez
 */
class TTFEException extends RuntimeException {

	/**
	 * Constructor.
	 *
	 * @param message The exception message.
	 */
	TTFEException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 *
	 * @param message The exception message.
	 * @param cause The cause that triggered this exception.
	 */
	TTFEException(String message, Throwable cause) {
		super(message, cause);
	}
}
