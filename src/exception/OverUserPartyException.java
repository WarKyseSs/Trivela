package exception;

public class OverUserPartyException extends Exception{

	private static final long serialVersionUID = 1L;

	public OverUserPartyException() {
		super("Too much user in party ! (Max 8 users)");
	}
}
