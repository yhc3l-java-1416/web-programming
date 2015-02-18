package se.coredev.web.path;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/transaction/*")
public final class PathExtractorServlet extends HttpServlet
{
	private static final long serialVersionUID = -3497096956840674629L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// GET: atm-service/transaction/{transaction-id}
		// GET: atm-service/transaction/{transaction-id}/receipt
		final String pathInfo = req.getPathInfo();
		final String[] pathSegements = pathInfo.split("/");
		
		resp.getWriter().print("PathInfo:" + pathInfo);

		for (String segement : pathSegements)
		{
			resp.getWriter().println("Segement:" + segement);
		}
		
	}
	
}
