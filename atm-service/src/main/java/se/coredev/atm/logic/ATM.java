package se.coredev.atm.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.coredev.atm.exception.ATMException;
import se.coredev.atm.exception.ATMSecurityException;
import se.coredev.atm.model.ATMCard;

public final class ATM
{
	private final Map<String, Bank> banks;

	public ATM(List<Bank> banks)
	{
		if (banks == null || banks.isEmpty())
		{
			throw new ATMException("Can't initialize an ATM without any banks");
		}
		
		this.banks = new HashMap<>();
		
		banks.forEach(bank -> this.banks.put(bank.getBankId(), bank));
	}

	public ATMSession verifyPin(String pin, ATMCard card)
	{
		if(card.verifyPin(pin))
		{
			final Bank bank = getBank(card);
			return new ATMSessionImpl(card, bank);
		}
		
		throw new ATMSecurityException("Invalid pin code");
	}

	private Bank getBank(ATMCard card)
	{
		if(banks.containsKey(card.getBankId()))
		{
			return banks.get(card.getBankId());
		}
		
		throw new ATMException("ATMCard is not connected to any recognized bank");
	}
}
