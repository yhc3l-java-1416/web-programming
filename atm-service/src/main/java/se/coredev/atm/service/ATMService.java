package se.coredev.atm.service;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;

import java.io.IOException;
import java.util.ArrayList;
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
import se.coredev.atm.service.operation.ATMOperationHandler;
import se.coredev.atm.service.operation.OperationHandler;
import se.coredev.atm.service.operation.RequestATMSessionHandler;
import se.coredev.atm.service.operation.RequestReceiptHandler;

@WebServlet("/atm/*")
public final class ATMService extends HttpServlet
{
	private static final long serialVersionUID = -7516548587427690193L;
	private static final Map<String, ATMSession> atmSessions = new HashMap<>();
	private static final Map<String, ATMOperationHandler> handlers;

	static
	{
		// Fake data
		final List<Bank> banks = new ArrayList<>();
		banks.add(new FakeBank("1234-5678", 10000));

		// Mappings
		handlers = new HashMap<String, ATMOperationHandler>();
		
		handlers.put("^\\/transaction\\/\\d+\\/receipt\\/?", new RequestReceiptHandler());
		handlers.put("^\\/session\\/?", new RequestATMSessionHandler(new ATM(banks)));
		handlers.put("^\\/operation\\/?", new OperationHandler());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			final String result = getHandler(request).handle(atmSessions, request, response);
			writeResponse(response, result, SC_OK);
		}
		catch (Exception e)
		{
			writeResponse(response, "", SC_BAD_REQUEST);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			final String result = getHandler(request).handle(atmSessions, request, response);
			writeResponse(response, result, SC_OK);
		}
		catch (Exception e)
		{
			writeResponse(response, "", SC_BAD_REQUEST);
		}
	}

	protected static void writeResponse(final HttpServletResponse response, final String content, final int statusCode) throws IOException
	{
		response.setContentType("text/plain");
		response.setStatus(statusCode);
		response.getWriter().println(content);
	}
	
	private static ATMOperationHandler getHandler(final HttpServletRequest request)
	{
		final String path = request.getPathInfo();

		for (Entry<String, ATMOperationHandler> entry : handlers.entrySet())
		{
			if (path.matches(entry.getKey()))
			{
				return entry.getValue();
			}
		}

		return null;
	}
}
