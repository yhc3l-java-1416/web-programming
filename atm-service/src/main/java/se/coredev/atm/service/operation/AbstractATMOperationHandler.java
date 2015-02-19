package se.coredev.atm.service.operation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public abstract class AbstractATMOperationHandler implements ATMOperationHandler
{
	protected static long getTransactionIdFromRequest(final HttpServletRequest request)
	{
		final String transactionId = StringUtils.substringBetween(request.getPathInfo(), "transaction/", "/receipt");
		return NumberUtils.toLong(transactionId, -1);
	}

	protected static String getAtmSessionIdFromRequest(final HttpServletRequest request)
	{
		return request.getHeader("atmSessionId").trim();
	}

	protected static Map<String, String> extractRequestBodyData(final HttpServletRequest request) throws IOException
	{
		final Map<String, String> requestBodyData = new HashMap<>();
		final String requestBody = IOUtils.toString(request.getInputStream());
		
		for (String entry : requestBody.split(","))
		{
			final String[] part = entry.split(":");
			final String key = part[0];
			final String value = part[1];

			requestBodyData.put(key.trim(), value.trim());
		}

		return requestBodyData;
	}

}
