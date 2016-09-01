package flint.business.util;

import kiss.util.Q;

public class ImageHelper {
	private String domain;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * 只解析满足条件的地址
	 * 
	 * @param path
	 * @param rule
	 * @return
	 */
	public String resolve(String path, String rule) {
		if (domain.equals(Q.getDomain(path)))
			return kiss.image.ImageHelper.resolve(path, rule);
		return path;
	}
}
