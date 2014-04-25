package cat.santi.tttf.exceptions;

public class NoAvailableSquaresException extends TTTFRuntimeException {

	private static final long serialVersionUID = 7667884840322980636L;

	public NoAvailableSquaresException() {
		super("No more squares available on the board");
	}
}
