package flint.business.security;

import kiss.security.Client;
import kiss.security.Identity;
import kiss.security.Passport;
import kiss.security.access.DomainInterceptor;
import kiss.security.authc.Authenticator;
import kiss.security.exception.SafetyException;
import flint.util.SpringHelper;

public class SchoolInterceptor extends DomainInterceptor {
	@Override
	public boolean doIntercept(Passport passport, Client client)
			throws SafetyException {
		Identity identity = passport.get(client.getDomain());
		if (identity == null) {
			Person person = new Person(passport);
			person.setDomain(client.getDomain());
			person.setDomainId(client.getDomainId());
			identity = person;
		}
		passport.runAs(identity);
		/* 如果不是游客，这里需要从数据库中用户的基本信息 */
		if (identity.getAttribute("authenticated.checked") == null
				&& passport.isAuthenticated() && !identity.isAuthenticated()) {
			if (!"0".equals(passport.getId())) {
				Authenticator authenticator = SpringHelper
						.getBean(Authenticator.class);
				authenticator.sso(passport);
			}
		}

		return true;
	}
}
