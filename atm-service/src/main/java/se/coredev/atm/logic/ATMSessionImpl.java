package se.coredev.atm.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import se.coredev.atm.exception.ATMException;
import se.coredev.atm.model.ATMCard;
import se.coredev.atm.model.ATMReceipt;

final class ATMSessionImpl extends AbstractATMSession
{
	private final List<ATMSessionInvalidationListener> listeners;
	private final AtomicBoolean sessionIsOpen; 
	private final AtomicBoolean sessionIsValid; 
	private final String sessionId;
	
	public ATMSessionImpl(ATMCard atmCard, Bank bank)
	{
		super(atmCard, bank);
		
		this.listeners = new ArrayList<>();
		this.sessionIsOpen = new AtomicBoolean(true);
		this.sessionIsValid = new AtomicBoolean(true);
		this.sessionId = UUID.randomUUID().toString();
	}

	@Override
	public Long withdrawAmount(int amount)
	{
		if(sessionIsOpen.getAndSet(false))
		{
			return bank.withdrawAmount(amount);
		}
		throw new ATMException("Session is closed");
	}

	@Override
	public ATMReceipt requestReceipt(long transactionId)
	{
		if(sessionIsValid.getAndSet(false))
		{
			ATMReceipt receipt = new ATMReceipt(bank.requestReceipt(transactionId));
			notifyListeners();
			
			return receipt;
		}
		throw new ATMException("Session is invalid");
	}

	@Override
	public Long checkBalance()
	{
		if(sessionIsOpen.getAndSet(false) && sessionIsValid.getAndSet(false))
		{
			final Long balance = bank.getBalance(atmCard.getAccountHolderId());
			notifyListeners();
			
			return balance;
		}
		throw new ATMException("Session is closed");
	}

	@Override
	public String getSessionId()
	{
		return sessionId;
	}

	@Override
	public void addInvalidationListener(ATMSessionInvalidationListener listener)
	{
		listeners.add(listener);
	}
	
	private void notifyListeners()
	{
		for (ATMSessionInvalidationListener listener : listeners)
		{
			listener.sessionInvalidated(this);
		}
	}
}
