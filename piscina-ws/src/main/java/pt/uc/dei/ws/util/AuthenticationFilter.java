package pt.uc.dei.ws.util;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pt.uc.dei.ws.beans.MySessionBean;


@WebFilter("*.xhtml")
public class AuthenticationFilter implements Filter {

	@Inject
	private MySessionBean mySessionBean;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		if (mySessionBean.getToken()==null && !request.getRequestURI().contains("index.xhtml") && !request.getRequestURI().contains("registar.xhtml") && !request.getRequestURI().contains("registration_confirmation.xhtml") && !request.getRequestURI().contains("recover_passw.xhtml") && !request.getRequestURI().contains("forgot_passw.xhtml") && !request.getRequestURI().contains("/javax.faces.resource/")) {
			response.sendRedirect("index.xhtml");
		} else {
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}