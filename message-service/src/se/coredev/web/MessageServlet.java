package se.coredev.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class MessageServlet extends HttpServlet
{
	private static final long serialVersionUID = 4124700350495052756L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// ?prefix=some_prefix
		String prefix = req.getParameter("prefix");
		
		if(prefix == null || prefix.trim().isEmpty())
		{
			prefix = getServletConfig().getInitParameter("prefix");
		}
		
		// ?message=some_message
		String message = req.getParameter("message");
		
		if(message == null || message.trim().isEmpty())
		{
			message = getServletContext().getInitParameter("default-message");
		}
		
		resp.getWriter().println(prefix + " " + message);
	}
}
