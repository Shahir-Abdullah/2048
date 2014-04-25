package cat.santi.tttf.exceptions;

public class TTTFRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 7723683885793868467L;

	public TTTFRuntimeException() {
		super();
	}
	
	public TTTFRuntimeException(String message) {
		super(message);
	}
	
	public TTTFRuntimeException(Throwable cause) {
		super(cause);
	}
	
	public TTTFRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public TTTFRuntimeException(String message, Throwable cause, boolean enableSupression, boolean writableStackTrace) {
		super(message, cause, enableSupression, writableStackTrace);
	}
}
