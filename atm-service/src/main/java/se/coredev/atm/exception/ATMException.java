package se.coredev.atm.exception;

public final class ATMException extends RuntimeException
{
	private static final long serialVersionUID = 6253996591400951419L;

	public ATMException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ATMException(String message)
	{
		super(message);
	}

	public ATMException(Throwable cause)
	{
		super(cause);
	}
}

