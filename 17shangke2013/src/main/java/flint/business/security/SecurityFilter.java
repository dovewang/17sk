package flint.business.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kiss.security.Guard;
import kiss.security.Passport;
import kiss.security.authc.Authenticator;
import kiss.security.authc.CookieToken;
import kiss.security.exception.SafetyException;
import kiss.web.SecurityRequest;
import kiss.web.SecurityResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SecurityFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(servletRequest.getServletContext());
		SecurityRequest request = new SecurityRequest(null,
				(HttpServletRequest) servletRequest);
		SecurityResponse response = new SecurityResponse(
				(HttpServletResponse) servletResponse);
		Guard guard = wac.getBean(Guard.class);
		try {
			/* 先检查cookie的值 */
			Passport passport = request.getPassport();
			final String cookie = request
					.getSecureCookie(Passport.COOKIE_PASSPORT);
			if (cookie != null) {
				Authenticator authenticator = wac.getBean(Authenticator.class);
				authenticator.authenticate(passport,
						new CookieToken<String, String>() {

							@Override
							public String getPrincipal() {
								return null;
							}

							@Override
							public String getCredentials() {
								return cookie;
							}

							@Override
							public boolean isRememberMe() {
								return true;
							}

						});
			}
			guard.tour(passport, new WebClient(request, response));
		} catch (SafetyException e) {
			e.printStackTrace();
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
