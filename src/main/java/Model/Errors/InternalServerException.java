package Model.Errors;

/**
 * Created by eric on 10/14/16.
 */
public class InternalServerException extends RuntimeException {
	public InternalServerException(String message) {
		super(message);
	}

	public InternalServerException(String message, Throwable cause) {
		super(message, cause);
	}
}
