package se.coredev.atm.service.operation;

import static se.coredev.atm.service.ATMServiceConstants.CHECK_BALANCE_OPERATION;
import static se.coredev.atm.service.ATMServiceConstants.WITHDRAW_OPERATION;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import se.coredev.atm.logic.ATMSession;

public final class OperationHandler extends AbstractATMOperationHandler
{

	@Override
	public String handle(Map<String, ATMSession> atmSessions, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		final Map<String, String> operationData = extractRequestBodyData(request);
		final String atmSessionId = getAtmSessionIdFromRequest(request);
		final String operationType = operationData.get("operation");
		final ATMSession atmSession = atmSessions.get(atmSessionId);

		if (WITHDRAW_OPERATION.equals(operationType))
		{
			final int amount = NumberUtils.toInt(operationData.get("amount"), 0);
			final Long transactionId = atmSession.withdrawAmount(amount);

			return transactionId.toString();
		}
		if (CHECK_BALANCE_OPERATION.equals(operationType))
		{
			return atmSession.checkBalance().toString();
		}

		throw new IllegalArgumentException("Invalid ATM Operation");
	}
}
