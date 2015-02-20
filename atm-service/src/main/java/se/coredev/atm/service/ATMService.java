package se.coredev.atm.service;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.coredev.atm.logic.ATM;
import se.coredev.atm.logic.ATMSession;
import se.coredev.atm.logic.Bank;
import se.coredev.atm.service.handler.ATMOperationHandler;
import se.coredev.atm.service.handler.ATMReceiptHandler;
import se.coredev.atm.service.handler.ATMSessionHandler;
import se.coredev.atm.service.handler.RequestHandler;

@WebServlet("/atm/*")
public final class ATMService extends HttpServlet
{
	private static final long serialVersionUID = -7516548587427690193L;
	private static final Map<String, ATMSession> atmSessions = new HashMap<>();
	private static final Map<String, RequestHandler> handlers;

	static
	{
		// Fake data
		List<Bank> banks = Arrays.asList(new FakeBank("1234-5678"));
		
		// Mappings
		handlers = new HashMap<String, RequestHandler>();
		
		handlers.put("^\\/transaction\\/\\d+\\/receipt\\/?", new ATMReceiptHandler());
		handlers.put("^\\/session\\/?", new ATMSessionHandler(new ATM(banks)));
		handlers.put("^\\/operation\\/?", new ATMOperationHandler());
	}

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			final String result = getHandler(request).handle(atmSessions, request);
			writeResponse(response, result, SC_OK);
		}
		catch (Exception e)
		{
			writeResponse(response, "", SC_BAD_REQUEST);
		}
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			final String result = getHandler(request).handle(atmSessions, request);
			writeResponse(response, result, SC_OK);
		}
		catch (Exception e)
		{
			writeResponse(response, "", SC_BAD_REQUEST);
		}
	}

	private static void writeResponse(final HttpServletResponse response, final String content, final int statusCode) throws IOException
	{
		response.setContentType("text/plain");
		response.setStatus(statusCode);
		response.getWriter().println(content);
	}
	
	private static RequestHandler getHandler(final HttpServletRequest request)
	{
		final String path = request.getPathInfo();

		for (Entry<String, RequestHandler> entry : handlers.entrySet())
		{
			if (path.matches(entry.getKey()))
			{
				return entry.getValue();
			}
		}

		throw new IllegalArgumentException("Could not find handler for path:" + path);
	}
}
