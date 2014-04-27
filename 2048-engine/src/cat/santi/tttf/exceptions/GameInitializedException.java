package cat.santi.tttf.exceptions;

/**
 * Exception thrown when trying to access a feature that needs a non initialized
 * game.
 * 
 * @author Santiago Gonzalez <santiago.gon.ber@gmail.com>
 */
public class GameInitializedException extends TTTFRuntimeException {
	
	/** Serialization version UID. */
	private static final long serialVersionUID = 5821341494577936756L;

	/**
	 * Constructor for {@link GameInitializedException}.
	 */
	public GameInitializedException() {
		super("The game is already initialized. Consider calling 'reset(width, height)' instead");
	}
}
