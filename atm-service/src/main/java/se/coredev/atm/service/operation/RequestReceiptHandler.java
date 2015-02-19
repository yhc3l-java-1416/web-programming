package se.coredev.atm.service.operation;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import se.coredev.atm.logic.ATMSession;
import se.coredev.atm.model.ATMReceipt;

public final class RequestReceiptHandler extends AbstractATMRequestHandler
{

	@Override
	public String doHandle(Map<String, ATMSession> atmSessions, HttpServletRequest request)
	{
		final long transactionId = getTransactionIdFromRequest(request);
		final ATMReceipt receipt = currentATMSession.requestReceipt(transactionId);

		return String.format("%s,%s,%s", receipt.getDate(), receipt.getTransactionId(), receipt.getAmount());
	}

	private static long getTransactionIdFromRequest(final HttpServletRequest request)
	{
		final String transactionId = StringUtils.substringBetween(request.getPathInfo(), "transaction/", "/receipt");

		return NumberUtils.toLong(transactionId, -1);
	}
}