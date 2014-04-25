package cat.santi.tttf.exceptions;

public class GameNotInitializedException extends TTTFRuntimeException {
	
	private static final long serialVersionUID = 5856793768558601120L;

	public GameNotInitializedException() {
		super("The game is not yet initialized. You should call 'reset(width, height)' first");
	}
}
