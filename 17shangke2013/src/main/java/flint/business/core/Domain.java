package flint.business.core;

public class Domain implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6620968953218818090L;

	/**
	 * 域编号
	 */
	private long id;

	/**
	 * 子域名，不包括后最如.17shangke.com,具有唯一性，作为主要的检索依据
	 */
	private String domain = "";

	/**
	 * 网站名称
	 */
	private String name = "";

	/**
	 * 网站关键字
	 */
	private String keyword;

	/**
	 * 网站描述
	 */
	private String description;

	/**
	 * 网站logo
	 */
	private String logo = "";

	/**
	 * 主题路径
	 */
	private String themePath = "";

	/**
	 * 网站图标
	 */
	private String favicon = "";

	/**
	 * 网站状态
	 */
	private byte status;

	/**
	 * 网站关闭原因
	 */
	private String reason = "您的网站由于某些原因已被关闭，请您及时与管理员联系！";

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getThemePath() {
		return themePath;
	}

	public void setThemePath(String themePath) {
		this.themePath = themePath;
	}

	public String getFavicon() {
		return favicon;
	}

	public void setFavicon(String favicon) {
		this.favicon = favicon;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
