package exception;

public class OverTrueOrFalseAnswerException extends Exception {

	private static final long serialVersionUID = 1L;

	public OverTrueOrFalseAnswerException() {
		super("Only 1 true answer and 2 false for a question !");
	}
}