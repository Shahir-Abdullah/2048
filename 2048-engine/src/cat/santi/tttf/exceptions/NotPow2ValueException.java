package cat.santi.tttf.exceptions;

public class NotPow2ValueException extends TTTFRuntimeException {

	private static final long serialVersionUID = 1293316823789536041L;

	public NotPow2ValueException() {
		super("The value is not a power of two");
	}
}
