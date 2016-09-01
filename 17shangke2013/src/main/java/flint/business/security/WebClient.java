package flint.business.security;

import java.util.Date;

import kiss.security.Client;
import kiss.web.SecurityRequest;
import kiss.web.SecurityResponse;
import flint.business.util.DomainHelper;
import flint.util.SpringHelper;
import flint.util.WebHelper;

public class WebClient implements Client {
	private SecurityRequest request;

	private SecurityResponse response;

	public WebClient(SecurityRequest request, SecurityResponse response) {
		this.request = request;
		this.response = response;
	}

	@Override
	public long getCreateTime() {
		return new Date().getTime();
	}

	@Override
	public long getLastAccessTime() {
		return 0;
	}

	@Override
	public String getDomain() {
		return WebHelper.getSubDomain(request, "17shangke.com");
	}

	@Override
	public String getURI() {
		return request.getRequestURI();
	}

	@Override
	public long getDomainId() {
		DomainHelper domainHelper = SpringHelper.getBean(DomainHelper.class);
		return domainHelper.getDomain(getDomain()).getId();
	}

	public SecurityRequest getRequest() {
		return request;
	}

	public SecurityResponse getResponse() {
		return response;
	}
}
