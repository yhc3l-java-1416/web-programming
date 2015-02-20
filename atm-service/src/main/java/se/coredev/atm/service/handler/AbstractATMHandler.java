package se.coredev.atm.service.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import se.coredev.atm.logic.ATMSession;

public abstract class AbstractATMHandler implements RequestHandler
{
	private static final String ATM_SESSION_ID = "atmSessionId";

	protected String currentATMSessionId;
	protected ATMSession currentATMSession;
	
	@Override
	public String handle(Map<String, ATMSession> atmSessions, HttpServletRequest request) throws IOException
	{
		currentATMSessionId = getAtmSessionIdFromRequest(request);
		currentATMSession = atmSessions.get(currentATMSessionId);
		
		return doHandle(atmSessions, request);
	}
	
	protected abstract String doHandle(Map<String, ATMSession> atmSessions, HttpServletRequest request) throws IOException;
	
	protected static String getAtmSessionIdFromRequest(final HttpServletRequest request)
	{
		final String atmSessionIdHeader = request.getHeader(ATM_SESSION_ID); 
		return StringUtils.defaultIfEmpty(atmSessionIdHeader, StringUtils.EMPTY);
	}

	protected static Map<String, String> extractRequestBodyData(final HttpServletRequest request) throws IOException
	{
		final Map<String, String> requestBodyData = new HashMap<>();
		final String requestBody = IOUtils.toString(request.getInputStream());

		if (!requestBody.isEmpty())
		{
			for (String entry : requestBody.split(","))
			{
				final String[] pair = entry.split(":");
				final String key = StringUtils.defaultIfEmpty(pair[0], "");
				final String value = StringUtils.defaultIfEmpty(pair[1], "");

				requestBodyData.put(key.trim(), value.trim());
			}
		}

		return requestBodyData;
	}

}
