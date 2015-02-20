package se.coredev.atm.service.handler;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import se.coredev.atm.logic.ATM;
import se.coredev.atm.logic.ATMSession;
import se.coredev.atm.logic.ATMSessionInvalidationListener;
import se.coredev.atm.model.ATMCard;

public final class ATMSessionHandler extends AbstractATMHandler
{
	private static final String PIN_KEY = "pin";
	private static final String BANK_ID_KEY = "bankId";
	private static final String ACCOUNT_HOLDER_ID_KEY = "accountHolderId";
	private static final String ENTERED_PIN_KEY = "enteredPin";
	
	private final ATM atm;
	
	public ATMSessionHandler(ATM atm)
	{
		this.atm = atm;
	}

	@Override
	public String doHandle(Map<String, ATMSession> atmSessions, HttpServletRequest request) throws IOException
	{
		final Map<String, String> operationData = extractRequestBodyData(request);
		final ATMCard atmCard = getATMCard(operationData);
		final String enteredPin = operationData.get(ENTERED_PIN_KEY);
		final ATMSession atmSession = atm.verifyPin(enteredPin, atmCard);

		atmSession.addInvalidationListener(new ATMSessionInvalidationListener()
		{
			@Override
			public void sessionInvalidated(ATMSession atmSession)
			{
				atmSessions.remove(atmSession.getSessionId());
			}
		});

		atmSessions.put(atmSession.getSessionId(), atmSession);

		return atmSession.getSessionId();
	}
	
	private ATMCard getATMCard(Map<String, String> operationData)
	{
		return new ATMCard(operationData.get(ACCOUNT_HOLDER_ID_KEY), operationData.get(BANK_ID_KEY), operationData.get(PIN_KEY));
	}
}
