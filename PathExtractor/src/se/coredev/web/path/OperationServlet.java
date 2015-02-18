package se.coredev.web.path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/operation")
public final class OperationServlet extends HttpServlet
{
	private static final long serialVersionUID = 3040968058602007246L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// operation:withdraw,atmSessionId:8907654,amount:500
		final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String line = null;

		while ((line = bufferedReader.readLine()) != null)
		{
			final String[] parts = line.split(",");
			for (String part : parts)
			{
				final String[] pairs = part.split(":");
				for (String pair : pairs)
				{
					resp.getWriter().println(pair);
				}
			}
		}

	}
}
