package flint.business.security;

import java.net.URLEncoder;

import kiss.security.Client;
import kiss.security.Passport;
import kiss.security.exception.SafetyException;
import kiss.util.Q;
import kiss.web.SecurityRequest;
import kiss.web.SecurityResponse;
import flint.util.SpringHelper;

public class ResourceInterceptor extends
		kiss.security.access.ResourceInterceptor {
	@Override
	public boolean doIntercept(Passport passport, Client client)
			throws SafetyException {
		try {
			WebClient webclient = (WebClient) client;
			SecurityRequest request = webclient.getRequest();
			SecurityResponse response = webclient.getResponse();
			ResourceManager manager = SpringHelper
					.getBean(ResourceManager.class);
			String url = request.getRequestURL().toString();
			if (!manager.allow(passport, webclient.getURI())) {
				if (passport.isAuthenticated()) {
					String forward = passport.getAttribute("forward");
					if (request.isAjax()) {
						Q.HTTP.render(response,
								"{\"status\":403,\"message\":\"权限不足!\",\"forward\":\""
										+ Q.isBlank(forward, "") + "\"}");
						return false;
					} else {
						url = Q.isBlank(forward, "/error/403.html");
					}

				} else {
					if (request.isAjax()) {
						Q.HTTP.render(response,
								"{\"status\":-3,\"message\":\"您还没有登录\"}");
						return false;
					} else {
						String param = request.getQueryString();
						if (param != null) {
							url += "?" + param;
						}
						url = "/passport/login.html" + "?url="
								+ URLEncoder.encode(url, "UTF-8");
					}
				}
				response.sendRedirect(url);
				return false;
			}
			return true;
		} catch (Exception e) {
			throw new SafetyException(e);
		}
	}
}
