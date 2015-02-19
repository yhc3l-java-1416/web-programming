package se.coredev.atm.exception;

public final class ATMSecurityException extends RuntimeException
{
	private static final long serialVersionUID = 8305466940412573706L;

	public ATMSecurityException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ATMSecurityException(String message)
	{
		super(message);
	}

	public ATMSecurityException(Throwable cause)
	{
		super(cause);
	}
}
