package flint.business.security;

public class Resource {

	/**
	 * 资源id
	 */
	private String id;

	/** 资源映射路径 */
	private String path;

	/**
	 * 允许访问的主机
	 */
	private String[] host;

	/**
	 * 不允许访问时的跳转页面
	 */
	private String forward;

	private String domain;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String[] getHost() {
		return host;
	}

	public void setHost(String[] host) {
		this.host = host;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

}
