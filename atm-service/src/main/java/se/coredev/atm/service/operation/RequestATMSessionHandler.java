package se.coredev.atm.service.operation;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.coredev.atm.logic.ATM;
import se.coredev.atm.logic.ATMSession;
import se.coredev.atm.logic.ATMSessionInvalidationListsner;

public final class RequestATMSessionHandler extends AbstractATMOperationHandler
{
	private final ATM atm;
	
	public RequestATMSessionHandler(ATM atm)
	{
		this.atm = atm;
	}

	@Override
	public String handle(Map<String, ATMSession> atmSessions, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		final Map<String, String> operationData = extractRequestBodyData(request);
		final ATMSession atmSession = null;

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

}
