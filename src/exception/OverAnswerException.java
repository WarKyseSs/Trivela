package exception;

public class OverAnswerException extends Exception{

	private static final long serialVersionUID = 1L;

	public OverAnswerException()
	{
		super("Only 3 answers for 1 question !");
	}
}