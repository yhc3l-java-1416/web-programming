package se.coredev.atm.service.operation;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.coredev.atm.logic.ATMSession;

public interface ATMOperationHandler
{
	String handle(Map<String, ATMSession> atmSessions, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
