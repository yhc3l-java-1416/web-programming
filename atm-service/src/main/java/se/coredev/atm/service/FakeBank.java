package se.coredev.atm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import se.coredev.atm.logic.Bank;
import se.coredev.atm.model.BankReceipt;

public final class FakeBank implements Bank
{
	private final String bankId;
	private long balance;
	private final Map<Long, Integer> transactions;
	private final AtomicLong transactionIds;

	public FakeBank(final String bankId, final long initialBalance)
	{
		this.bankId = bankId;
		this.balance = initialBalance;
		this.transactions = new HashMap<>();
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
		return balance;
	}

	@Override
	public long withdrawAmount(int amount)
	{
		balance = balance - amount;
		long transactionId = transactionIds.incrementAndGet();
		transactions.put(transactionId, amount);
		
		return transactionId;
	}

	@Override
	public BankReceipt requestReceipt(long transactionId)
	{
		return new BankReceipt(bankId, transactionId, transactions.get(transactionId), new Date());
	}

}
