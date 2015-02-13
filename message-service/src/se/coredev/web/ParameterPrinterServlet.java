package se.coredev.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ParameterPrinterServlet extends HttpServlet
{
	private static final long serialVersionUID = 6549616992098403137L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final PrintWriter writer = resp.getWriter();

		req.getParameterMap().forEach((name, values) -> {
			writer.println(String.format("paramater-name:%s parameter-value:%s", name, String.join(",", values)));
		});
	}

}
