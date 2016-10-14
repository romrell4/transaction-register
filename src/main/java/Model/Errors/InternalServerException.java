package Model.Errors;

/**
 * Created by eric on 10/14/16.
 */
public class InternalServerException extends RuntimeException {
	public ErrorMessage error = new ErrorMessage();

	public InternalServerException() {
	}

	public InternalServerException(String message) {
		super(message);
		this.error.setMessage(message);
	}

	public InternalServerException(String message, Throwable cause) {
		super(message, cause);
		this.error.setMessage(message);
		this.error.setThrowable(cause);
	}
}
