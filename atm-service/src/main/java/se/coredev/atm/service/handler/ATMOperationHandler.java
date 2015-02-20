package se.coredev.atm.service.handler;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;

import se.coredev.atm.logic.ATMSession;

public final class ATMOperationHandler extends AbstractATMHandler
{
	private static final String AMOUNT_KEY = "amount";
	private static final String OPERATION_KEY = "operation";
	private static final String WITHDRAW_OPERATION = "withdraw";
	private static final String CHECK_BALANCE_OPERATION = "checkBalance";

	@Override
	public String doHandle(Map<String, ATMSession> atmSessions, HttpServletRequest request) throws IOException
	{
		final Map<String, String> operationData = extractRequestBodyData(request);
		final String operationType = operationData.get(OPERATION_KEY);
		
		if (WITHDRAW_OPERATION.equals(operationType))
		{
			final int amount = NumberUtils.toInt(operationData.get(AMOUNT_KEY), 0);
			final Long transactionId = currentATMSession.withdrawAmount(amount);

			return transactionId.toString();
		}
		if (CHECK_BALANCE_OPERATION.equals(operationType))
		{
			return currentATMSession.checkBalance().toString();
		}

		throw new IllegalArgumentException("Invalid ATM Operation");
	}
}
