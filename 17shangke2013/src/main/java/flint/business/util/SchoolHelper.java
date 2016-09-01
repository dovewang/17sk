package flint.business.util;

import flint.business.core.Domain;
import flint.util.SpringHelper;

public abstract class SchoolHelper {
	/**
	 * 通过访问的域名获取学校的编号
	 * 
	 * @param domain
	 * @return
	 */
	public static long getSchool(String domain) {
		if ("www".equals(domain) || "".equals(domain)) {
			return 0L;
		}
		DomainHelper domainHelper = SpringHelper.getBean(DomainHelper.class);
		Domain d = domainHelper.getDomain(domain);
		if (d != null) {
			return d.getId();
		}
		return 0L;
	}
}
