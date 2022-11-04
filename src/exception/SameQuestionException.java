package exception;

public class SameQuestionException extends Exception{

	private static final long serialVersionUID = 1L;

	public SameQuestionException() 	{
		super("You already have this question");
	}
}