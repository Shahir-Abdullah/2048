package cat.santi.tttf.exceptions;

/**
 * The base TTTF {@link RuntimeException}.
 * 
 * @author Santiago Gonzalez <santiago.gon.ber@gmail.com>
 */
public class TTTFRuntimeException extends RuntimeException {

	/** Serialization version UID. */
	private static final long serialVersionUID = 7723683885793868467L;

	/**
	 * Constructor for {@link TTTFRuntimeException}.
	 */
	public TTTFRuntimeException() {
		super();
	}

	/**
	 * Constructor for {@link TTTFRuntimeException}.
	 */
	public TTTFRuntimeException(String message) {
		super(message);
	}
	
	/**
	 * Constructor for {@link TTTFRuntimeException}.
	 */
	public TTTFRuntimeException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor for {@link TTTFRuntimeException}.
	 */
	public TTTFRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructor for {@link TTTFRuntimeException}.
	 */
	public TTTFRuntimeException(String message, Throwable cause, boolean enableSupression, boolean writableStackTrace) {
		super(message, cause, enableSupression, writableStackTrace);
	}
}
