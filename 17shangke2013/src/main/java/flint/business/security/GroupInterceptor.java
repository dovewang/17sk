package flint.business.security;

import org.springframework.util.AntPathMatcher;

import kiss.security.Client;
import kiss.security.Passport;
import kiss.security.access.Interceptor;
import kiss.web.SecurityRequest;
import kiss.web.SecurityResponse;

public class GroupInterceptor implements Interceptor {

	AntPathMatcher urlmatch = new AntPathMatcher();

	@Override
	public boolean doIntercept(Passport passport, Client client) {
		// /group/{groupId}
		WebClient webclient = (WebClient) client;
		SecurityRequest request = webclient.getRequest();
		SecurityResponse response = webclient.getResponse();
		if (urlmatch.match("/group/**", webclient.getURI())||urlmatch.match("/class/**", webclient.getURI())) {
                /*检查用户访问权限*/
		}
		return true;
	}

}
