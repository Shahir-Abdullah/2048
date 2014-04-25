package cat.santi.tttf.exceptions;

public class GameInitializedException extends TTTFRuntimeException {
	
	private static final long serialVersionUID = 5821341494577936756L;

	public GameInitializedException() {
		super("The game is already initialized. Consider calling 'reset(width, height)' instead");
	}
}
