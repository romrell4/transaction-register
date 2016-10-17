package Model.Errors;

/**
 * Created by eric on 10/17/16.
 */
public class BadRequestException extends RuntimeException {
	public ErrorMessage error = new ErrorMessage();

	public BadRequestException() {
	}

	public BadRequestException(String message) {
		super(message);
		this.error.setMessage(message);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
		this.error.setMessage(message);
		this.error.setThrowable(cause);
	}
}
