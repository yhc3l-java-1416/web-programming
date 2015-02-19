package se.coredev.atm.service.operation;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import se.coredev.atm.logic.ATM;
import se.coredev.atm.logic.ATMSession;
import se.coredev.atm.logic.ATMSessionInvalidationListsner;
import se.coredev.atm.model.ATMCard;

public final class RequestATMSessionHandler extends AbstractATMRequestHandler
{
	private final ATM atm;
	
	public RequestATMSessionHandler(ATM atm)
	{
		this.atm = atm;
	}

	@Override
	public String doHandle(Map<String, ATMSession> atmSessions, HttpServletRequest request) throws IOException
	{
		final Map<String, String> operationData = extractRequestBodyData(request);
		final ATMCard atmCard = getATMCard(operationData);
		final String enteredPin = operationData.get("enteredPin");
		final ATMSession atmSession = atm.verifyPin(enteredPin, atmCard);

		atmSession.addInvalidationListener(new ATMSessionInvalidationListsner()
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
		return new ATMCard(operationData.get("accountHolderId"), operationData.get("bankId"), operationData.get("pin"));
	}

}
