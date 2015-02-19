package se.coredev.atm.logic;

import se.coredev.atm.model.BankReceipt;

public interface Bank
{
	String getBankId();

	long getBalance(String accountHolderId);

	long withdrawAmount(int amount);

	BankReceipt requestReceipt(long transactionId);
}
