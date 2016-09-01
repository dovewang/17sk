package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_SCHOOL", primary = { "SCHOOL_ID" })
public class School implements Entity {

	/**
	 * 学校编号
	 */
	private long schoolId;

	/**
	 * 学校名称，一般不超过80个字符
	 */
	private String name;

	/**
	 * 学校简介
	 */
	private String intro;

	/**
	 * 关键字，一般不超过100个字符
	 */
	private String keyword;

	/**
	 * 简要描述，一般不超过200个字符
	 */
	private String description;

	/**
	 * 是否开启SEO优化
	 */
	private boolean seo;

	/**
	 * 学校类型
	 */
	private byte type;

	/**
	 * 组织机构代码证编号
	 */
	private String cert;

	private String certDir;

	/**
	 * 校长
	 */
	private String master;

	/**
	 * 市
	 */
	private int city;

	/**
	 * 语言，默认中文
	 */
	private String language;

	/**
	 * 地址
	 */
	private String shcoolAddress;

	/**
	 * 网站管理员
	 */
	private long webmaster;

	/**
	 * 联系人
	 */
	private String contact;

	/**
	 * 手机
	 */
	private long tel;

	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 传真
	 */
	private String fax;

	/**
	 * 即时通讯
	 */
	private String im;

	/**
	 * 联系邮箱
	 */
	private String email;

	/**
	 * 通讯地址
	 */
	private String address;

	/**
	 * 组织主页
	 */
	private String homepage;

	/**
	 * 本站二级域名
	 */
	private String domain;

	/**
	 * LOGO
	 */
	private String logo;

	/**
	 * 是否是DNS解析域名，如果是，本站域名将直接指向学校主页
	 */
	private boolean dns;

	/**
	 * 主题编号，编号为0时代表自定义主题，主题的样式将存储在学校配置表中
	 */
	private int themeId;

	/**
	 * 录入时间
	 */
	private int createTime;

	/**
	 * 组织状态
	 */
	private byte status;

	/**
	 * 备注
	 */
	private String memo;

	public School() {
	}

	public School(long schoolId) {
		this.schoolId = schoolId;
	}

	/**
	 * @param long 学校编号
	 */
	public void setSchoolId(long schoolId) {
		this.schoolId = schoolId;
	}

	/**
	 * @return long 学校编号
	 */
	public long getSchoolId() {
		return this.schoolId;
	}

	/**
	 * @param String
	 *            学校名称，一般不超过80个字符
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return String 学校名称，一般不超过80个字符
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param String
	 *            学校简介
	 */
	public void setIntro(String intro) {
		this.intro = intro;
	}

	/**
	 * @return String 学校简介
	 */
	public String getIntro() {
		return this.intro;
	}

	/**
	 * @param String
	 *            关键字，一般不超过100个字符
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return String 关键字，一般不超过100个字符
	 */
	public String getKeyword() {
		return this.keyword;
	}

	/**
	 * @param String
	 *            简要描述，一般不超过200个字符
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return String 简要描述，一般不超过200个字符
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param boolean 是否开启SEO优化
	 */
	public void setSeo(boolean seo) {
		this.seo = seo;
	}

	/**
	 * @return boolean 是否开启SEO优化
	 */
	public boolean isSeo() {
		return this.seo;
	}

	/**
	 * @param byte 学校类型
	 */
	public void setType(byte type) {
		this.type = type;
	}

	/**
	 * @return byte 学校类型
	 */
	public byte getType() {
		return this.type;
	}

	/**
	 * @param String
	 *            组织机构代码证编号
	 */
	public void setCert(String cert) {
		this.cert = cert;
	}

	/**
	 * @return String 组织机构代码证编号
	 */
	public String getCert() {
		return this.cert;
	}

	public String getCertDir() {
		return certDir;
	}

	public void setCertDir(String certDir) {
		this.certDir = certDir;
	}

	/**
	 * @param String
	 *            校长
	 */
	public void setMaster(String master) {
		this.master = master;
	}

	/**
	 * @return String 校长
	 */
	public String getMaster() {
		return this.master;
	}

	/**
	 * @param int 市
	 */
	public void setCity(int city) {
		this.city = city;
	}

	/**
	 * @return int 市
	 */
	public int getCity() {
		return this.city;
	}

	/**
	 * @param String
	 *            语言，默认中文
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return String 语言，默认中文
	 */
	public String getLanguage() {
		return this.language;
	}

	/**
	 * @param String
	 *            地址
	 */
	public void setShcoolAddress(String shcoolAddress) {
		this.shcoolAddress = shcoolAddress;
	}

	/**
	 * @return String 地址
	 */
	public String getShcoolAddress() {
		return this.shcoolAddress;
	}

	/**
	 * @param long 网站管理员
	 */
	public void setWebmaster(long webmaster) {
		this.webmaster = webmaster;
	}

	/**
	 * @return long 网站管理员
	 */
	public long getWebmaster() {
		return this.webmaster;
	}

	/**
	 * @param String
	 *            联系人
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * @return String 联系人
	 */
	public String getContact() {
		return this.contact;
	}

	/**
	 * @param long 手机
	 */
	public void setTel(long tel) {
		this.tel = tel;
	}

	/**
	 * @return long 手机
	 */
	public long getTel() {
		return this.tel;
	}

	/**
	 * @param String
	 *            联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return String 联系电话
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * @param String
	 *            传真
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return String 传真
	 */
	public String getFax() {
		return this.fax;
	}

	/**
	 * @param String
	 *            即时通讯
	 */
	public void setIm(String im) {
		this.im = im;
	}

	/**
	 * @return String 即时通讯
	 */
	public String getIm() {
		return this.im;
	}

	/**
	 * @param String
	 *            联系邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return String 联系邮箱
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param String
	 *            通讯地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return String 通讯地址
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * @param String
	 *            组织主页
	 */
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	/**
	 * @return String 组织主页
	 */
	public String getHomepage() {
		return this.homepage;
	}

	/**
	 * @param String
	 *            本站二级域名
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @return String 本站二级域名
	 */
	public String getDomain() {
		return this.domain;
	}

	/**
	 * @param String
	 *            LOGO
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * @return String LOGO
	 */
	public String getLogo() {
		return this.logo;
	}

	/**
	 * @param boolean 是否是DNS解析域名，如果是，本站域名将直接指向学校主页
	 */
	public void setDns(boolean dns) {
		this.dns = dns;
	}

	/**
	 * @return boolean 是否是DNS解析域名，如果是，本站域名将直接指向学校主页
	 */
	public boolean isDns() {
		return this.dns;
	}

	/**
	 * @param int 主题编号，编号为0时代表自定义主题，主题的样式将存储在学校配置表中
	 */
	public void setThemeId(int themeId) {
		this.themeId = themeId;
	}

	/**
	 * @return int 主题编号，编号为0时代表自定义主题，主题的样式将存储在学校配置表中
	 */
	public int getThemeId() {
		return this.themeId;
	}

	/**
	 * @param int 录入时间
	 */
	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return int 录入时间
	 */
	public int getCreateTime() {
		return this.createTime;
	}

	/**
	 * @param byte 组织状态
	 */
	public void setStatus(byte status) {
		this.status = status;
	}

	/**
	 * @return byte 组织状态
	 */
	public byte getStatus() {
		return this.status;
	}

	/**
	 * @param String
	 *            备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return String 备注
	 */
	public String getMemo() {
		return this.memo;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>((int) 28);
		map.put("schoolId", this.schoolId);
		map.put("name", this.name);
		map.put("intro", this.intro);
		map.put("keyword", this.keyword);
		map.put("description", this.description);
		map.put("seo", this.seo);
		map.put("type", this.type);
		map.put("cert", this.cert);
		map.put("certDir", this.certDir);
		map.put("master", this.master);
		map.put("city", this.city);
		map.put("language", this.language);
		map.put("shcoolAddress", this.shcoolAddress);
		map.put("webmaster", this.webmaster);
		map.put("contact", this.contact);
		map.put("tel", this.tel);
		map.put("phone", this.phone);
		map.put("fax", this.fax);
		map.put("im", this.im);
		map.put("email", this.email);
		map.put("address", this.address);
		map.put("homepage", this.homepage);
		map.put("domain", this.domain);
		map.put("logo", this.logo);
		map.put("dns", this.dns);
		map.put("themeId", this.themeId);
		map.put("createTime", this.createTime);
		map.put("status", this.status);
		map.put("memo", this.memo);
		return map;
	}

	@Override
	public Object[] primaryValue() {
		return new Object[] { schoolId };
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		School entity = (School) o;
		return schoolId == entity.schoolId;
	}

	@Override
	public int hashCode() {
		int c = 0;
		int i = 7;
		for (Object o : primaryValue()) {
			if (o != null) {
				c += (2 ^ i + 1) * o.hashCode();
			} else {
				c += (2 ^ i + 1) * NULL_HASH;
			}
			i++;
		}
		return c;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"schoolId\":").append(schoolId).append(",");
		sb.append("\"name\":").append("\"" + name + "\"").append(",");
		sb.append("\"intro\":").append("\"" + intro + "\"").append(",");
		sb.append("\"keyword\":").append("\"" + keyword + "\"").append(",");
		sb.append("\"description\":").append("\"" + description + "\"")
				.append(",");
		sb.append("\"seo\":").append(seo).append(",");
		sb.append("\"type\":").append(type).append(",");
		sb.append("\"cert\":").append("\"" + cert + "\"").append(",");
		sb.append("\"certDir\":").append("\"" + certDir + "\"").append(",");
		sb.append("\"master\":").append("\"" + master + "\"").append(",");
		sb.append("\"city\":").append(city).append(",");
		sb.append("\"language\":").append("\"" + language + "\"").append(",");
		sb.append("\"shcoolAddress\":").append("\"" + shcoolAddress + "\"")
				.append(",");
		sb.append("\"webmaster\":").append(webmaster).append(",");
		sb.append("\"contact\":").append("\"" + contact + "\"").append(",");
		sb.append("\"tel\":").append(tel).append(",");
		sb.append("\"phone\":").append("\"" + phone + "\"").append(",");
		sb.append("\"fax\":").append("\"" + fax + "\"").append(",");
		sb.append("\"im\":").append("\"" + im + "\"").append(",");
		sb.append("\"email\":").append("\"" + email + "\"").append(",");
		sb.append("\"address\":").append("\"" + address + "\"").append(",");
		sb.append("\"homepage\":").append("\"" + homepage + "\"").append(",");
		sb.append("\"domain\":").append("\"" + domain + "\"").append(",");
		sb.append("\"logo\":").append("\"" + logo + "\"").append(",");
		sb.append("\"dns\":").append(dns).append(",");
		sb.append("\"themeId\":").append(themeId).append(",");
		sb.append("\"createTime\":").append(createTime).append(",");
		sb.append("\"status\":").append(status).append(",");
		sb.append("\"memo\":").append("\"" + memo + "\"");
		sb.append("}");
		return sb.toString();
	}
}
