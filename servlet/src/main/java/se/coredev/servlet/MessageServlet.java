package se.coredev.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/message")
public final class MessageServlet extends HttpServlet
{
	private static final long serialVersionUID = 3099172397826284082L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String message = req.getParameter("echo");
		
		if(message == null || message.trim().isEmpty())
		{
			message = getInitParameter("message");
		}
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Echo:" + message);
	}

}
