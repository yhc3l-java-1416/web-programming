package se.coredev.atm.model;

public final class ATMCard
{
	private final String accountHolderId;
	private final String bankId;
	private final String pin;

	public ATMCard(String accountHolderId, String bankId, String pin)
	{
		this.accountHolderId = accountHolderId;
		this.bankId = bankId;
		this.pin = pin;
	}

	public String getAccountHolderId()
	{
		return accountHolderId;
	}

	public String getBankId()
	{
		return bankId;
	}
	
	public boolean verifyPin(String pin)
	{
		return this.pin.equals(pin);
	}
}
