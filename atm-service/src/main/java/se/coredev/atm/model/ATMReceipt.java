package se.coredev.atm.model;

import java.util.Date;

public final class ATMReceipt
{
	private final long transactionId;
	private final int amount;
	private final Date date;

	public ATMReceipt(BankReceipt bankReceipt)
	{
		this(bankReceipt.getTransactionId(), bankReceipt.getAmount());
	}
	
	public ATMReceipt(long transactionId, int amount)
	{
		this.transactionId = transactionId;
		this.amount = amount;
		this.date = new Date();
	}

	public long getTransactionId()
	{
		return transactionId;
	}

	public int getAmount()
	{
		return amount;
	}

	public Date getDate()
	{
		return date;
	}
}
