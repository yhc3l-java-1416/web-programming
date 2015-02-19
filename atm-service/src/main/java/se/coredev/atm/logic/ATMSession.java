package se.coredev.atm.logic;

import se.coredev.atm.model.ATMReceipt;

public interface ATMSession
{
	String getSessionId();
	
	Long withdrawAmount(int amount);

	ATMReceipt requestReceipt(long transactionId);

	Long checkBalance();
	
	void addInvalidationListener(ATMSessionInvalidationListsner listener);
}
