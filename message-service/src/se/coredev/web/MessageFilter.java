package se.coredev.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public final class MessageFilter implements Filter
{
	@Override
	public void destroy()
	{
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
	{
		final HttpServletResponse httpResponse = (HttpServletResponse) resp;
		final String secretKey = req.getParameter("secret-key");
		
		if("yoda".equals(secretKey))
		{
			chain.doFilter(req, resp);
			return;
		}
		
		httpResponse.sendError(401, "Invalid secret key");		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
	}

}
