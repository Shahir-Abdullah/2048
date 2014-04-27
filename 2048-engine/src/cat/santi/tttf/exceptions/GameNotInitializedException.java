package cat.santi.tttf.exceptions;

/**
 * Exception thrown when trying to access a feature that needs an initialized
 * game.
 * 
 * @author Santiago Gonzalez <santiago.gon.ber@gmail.com>
 */
public class GameNotInitializedException extends TTTFRuntimeException {
	
	/** Serialization version UID. */
	private static final long serialVersionUID = 5856793768558601120L;

	/**
	 * Constructor for {@link GameNotInitializedException}.
	 */
	public GameNotInitializedException() {
		super("The game is not yet initialized. You should call 'reset(width, height)' first");
	}
}
