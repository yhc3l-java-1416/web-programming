package se.coredev.atm.service;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import se.coredev.atm.logic.Bank;
import se.coredev.atm.model.BankReceipt;

public final class FakeBank implements Bank
{
	private final String bankId;
	private final AtomicLong transactionIds;

	public FakeBank(final String bankId)
	{
		this.bankId = bankId;
		this.transactionIds = new AtomicLong(100000);
	}

	@Override
	public String getBankId()
	{
		return bankId;
	}

	@Override
	public long getBalance(String accountHolderId)
	{
		// Fake value
		return 10000;
	}

	@Override
	public long withdrawAmount(int amount)
	{
		// Next fake transaction id
		return transactionIds.incrementAndGet();
	}

	@Override
	public BankReceipt requestReceipt(long transactionId)
	{
		// Fake receipt
		return new BankReceipt(bankId, transactionId, 10000, new Date());
	}
}