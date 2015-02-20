package se.coredev.atm.service.handler;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import se.coredev.atm.logic.ATMSession;

public interface RequestHandler
{
	String handle(Map<String, ATMSession> atmSessions, HttpServletRequest request) throws IOException;
}
