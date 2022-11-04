package exception;

public class SameUserNameException extends Exception{

	private static final long serialVersionUID = 1L;

	public SameUserNameException() {
		super("You can't have the same name !");
	}
}
