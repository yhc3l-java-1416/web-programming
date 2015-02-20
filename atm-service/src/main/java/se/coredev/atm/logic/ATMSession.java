package se.coredev.atm.logic;

import se.coredev.atm.model.ATMReceipt;

public interface ATMSession
{
	String getSessionId();
	
	Long withdrawAmount(int amount);

	ATMReceipt requestReceipt(long transactionId);

	Long checkBalance();
	
	/**
	 * Listeners will be notified when this ATM session is no longer valid
	 * 
	 * @param listener
	 */
	void addInvalidationListener(ATMSessionInvalidationListener listener);
}
