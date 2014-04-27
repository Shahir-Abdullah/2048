package cat.santi.tttf.exceptions;

/**
 * Exception thrown when trying to find an available board space, being
 * it completely full.
 * 
 * @author Santiago Gonzalez <santiago.gon.ber@gmail.com>
 */
public class NoAvailableSquaresException extends TTTFRuntimeException {

	/** Serialization version UID. */
	private static final long serialVersionUID = 7667884840322980636L;

	/**
	 * Constructor for {@link NoAvailableSquaresException}.
	 */
	public NoAvailableSquaresException() {
		super("No more squares available on the board");
	}
}
