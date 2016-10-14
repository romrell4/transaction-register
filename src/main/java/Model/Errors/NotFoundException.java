package Model.Errors;

/**
 * Created by eric on 10/14/16.
 */
public class NotFoundException extends RuntimeException {
	public ErrorMessage error = new ErrorMessage();

	public NotFoundException() {
	}

	public NotFoundException(String message) {
		super(message);
		this.error.setMessage(message);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
		this.error.setMessage(message);
		this.error.setThrowable(cause);
	}
}
