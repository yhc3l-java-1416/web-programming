package se.coredev.atm.service.operation;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.coredev.atm.logic.ATMSession;
import se.coredev.atm.model.ATMReceipt;

public final class RequestReceiptHandler extends AbstractATMOperationHandler
{

	@Override
	public String handle(Map<String, ATMSession> atmSessions, HttpServletRequest request, HttpServletResponse response)
	{
		final long transactionId = getTransactionIdFromRequest(request);
		final ATMSession atmSession = atmSessions.get(transactionId);
		final ATMReceipt receipt = atmSession.requestReceipt(transactionId);

		return String.format("%s,%s,%s", receipt.getDate(), receipt.getTransactionId(), receipt.getAmount());
	}

}
