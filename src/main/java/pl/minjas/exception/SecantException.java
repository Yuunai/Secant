package pl.minjas.exception;

public class SecantException extends Exception {
	
	public SecantException() {
	}
	
	public SecantException(String message) {
		super(message);
	}
	
	public SecantException(String message, Throwable cause) {
		super(message, cause);
	}
}
