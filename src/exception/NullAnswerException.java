package exception;

public class NullAnswerException extends Exception{

	private static final long serialVersionUID = 1L;

	public NullAnswerException() {
		super("Null answer is not possible !");
	}
}