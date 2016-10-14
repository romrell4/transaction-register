package Model.Errors;

/**
 * Created by eric on 10/14/16.
 */
class ErrorMessage {
	private String message;
	private Throwable throwable;

	ErrorMessage() {}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
}
