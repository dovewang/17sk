package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_TUTOR", primary = { "TUTOR_ID" })
public class Tutor implements Entity {

	/**
	*
	*/
	private long tutorId;

	/**
	 * 服务提供者编号
	 */
	private long provider;

	/**
	 * 服务者类型，0个人，1学校
	 */
	private byte providerType;

	/**
	 * 服务状态
	 */
	private byte status;

	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 辅导年级，0代表不限，限选1项
	 */
	private long grade;

	/**
	 * 辅导科目1，最多选择3项，但是用户可以发布多个
	 */
	private long kind1;

	/**
	 * 辅导科目2
	 */
	private long kind2;

	/**
	 * 辅导科目3
	 */
	private long kind3;

	/**
	 * 0代表网络教学辅导、1本地辅导 ,1一对一 2、辅导班 3 寒暑假 4、大学生家教
	 */
	private byte tutorType;

	/**
	 * 辅导方式
	 */
	private byte tutorMode;

	/**
	 * 工作日
	 */
	private int workDay;

	/**
	 * 营业开始时间
	 */
	private int openTime;

	/**
	 * 营业结束时间
	 */
	private int closeTime;

	/**
	 * 地图标注链接
	 */
	private String map;

	/**
	 * 地区
	 */
	private int city;

	private String title;
	/**
	 * 要求
	 */
	private String intro;

	/**
	 * 线上辅导价格
	 */
	private int onlinePrice;

	/**
	 * 价格，按每小时计算（地区选择时，限上和线下的价格不同）
	 */
	private int price;

	/**
	 * 发布时间
	 */
	private int dateline;

	/**
	 * 有效期，1周、2周、1个月
	 */
	private int validity;

	/**
	 * 浏览人数
	 */
	private int views;

	/**
	 * 备注
	 */
	private String memo;

	public Tutor() {
	}

	public Tutor(long tutorId) {
		this.tutorId = tutorId;
	}

	/**
	 * @param long
	 */
	public void setTutorId(long tutorId) {
		this.tutorId = tutorId;
	}

	/**
	 * @return long
	 */
	public long getTutorId() {
		return this.tutorId;
	}

	/**
	 * @param long 服务提供者编号
	 */
	public void setProvider(long provider) {
		this.provider = provider;
	}

	/**
	 * @return long 服务提供者编号
	 */
	public long getProvider() {
		return this.provider;
	}

	/**
	 * @param byte 服务者类型，0个人，1学校
	 */
	public void setProviderType(byte providerType) {
		this.providerType = providerType;
	}

	/**
	 * @return byte 服务者类型，0个人，1学校
	 */
	public byte getProviderType() {
		return this.providerType;
	}

	/**
	 * @param byte 服务状态
	 */
	public void setStatus(byte status) {
		this.status = status;
	}

	/**
	 * @return byte 服务状态
	 */
	public byte getStatus() {
		return this.status;
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
	 * @param long 辅导年级，0代表不限，限选1项
	 */
	public void setGrade(long grade) {
		this.grade = grade;
	}

	/**
	 * @return long 辅导年级，0代表不限，限选1项
	 */
	public long getGrade() {
		return this.grade;
	}

	/**
	 * @param long 辅导科目1，最多选择3项，但是用户可以发布多个
	 */
	public void setKind1(long kind1) {
		this.kind1 = kind1;
	}

	/**
	 * @return long 辅导科目1，最多选择3项，但是用户可以发布多个
	 */
	public long getKind1() {
		return this.kind1;
	}

	/**
	 * @param long 辅导科目2
	 */
	public void setKind2(long kind2) {
		this.kind2 = kind2;
	}

	/**
	 * @return long 辅导科目2
	 */
	public long getKind2() {
		return this.kind2;
	}

	/**
	 * @param long 辅导科目3
	 */
	public void setKind3(long kind3) {
		this.kind3 = kind3;
	}

	/**
	 * @return long 辅导科目3
	 */
	public long getKind3() {
		return this.kind3;
	}

	/**
	 * @param byte 0代表网络教学辅导、1本地辅导 ,1一对一 2、辅导班 3 寒暑假 4、大学生家教
	 */
	public void setTutorType(byte tutorType) {
		this.tutorType = tutorType;
	}

	/**
	 * @return byte 0代表网络教学辅导、1本地辅导 ,1一对一 2、辅导班 3 寒暑假 4、大学生家教
	 */
	public byte getTutorType() {
		return this.tutorType;
	}

	/**
	 * @param byte 辅导方式
	 */
	public void setTutorMode(byte tutorMode) {
		this.tutorMode = tutorMode;
	}

	/**
	 * @return byte 辅导方式
	 */
	public byte getTutorMode() {
		return this.tutorMode;
	}

	/**
	 * @param int 工作日
	 */
	public void setWorkDay(int workDay) {
		this.workDay = workDay;
	}

	/**
	 * @return int 工作日
	 */
	public int getWorkDay() {
		return this.workDay;
	}

	/**
	 * @param int 营业开始时间
	 */
	public void setOpenTime(int openTime) {
		this.openTime = openTime;
	}

	/**
	 * @return int 营业开始时间
	 */
	public int getOpenTime() {
		return this.openTime;
	}

	/**
	 * @param int 营业结束时间
	 */
	public void setCloseTime(int closeTime) {
		this.closeTime = closeTime;
	}

	/**
	 * @return int 营业结束时间
	 */
	public int getCloseTime() {
		return this.closeTime;
	}

	/**
	 * @param String
	 *            地图标注链接
	 */
	public void setMap(String map) {
		this.map = map;
	}

	/**
	 * @return String 地图标注链接
	 */
	public String getMap() {
		return this.map;
	}

	/**
	 * @param int 地区
	 */
	public void setCity(int city) {
		this.city = city;
	}

	/**
	 * @return int 地区
	 */
	public int getCity() {
		return this.city;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param String
	 *            要求
	 */
	public void setIntro(String intro) {
		this.intro = intro;
	}

	/**
	 * @return String 要求
	 */
	public String getIntro() {
		return this.intro;
	}

	/**
	 * @param int 线上辅导价格
	 */
	public void setOnlinePrice(int onlinePrice) {
		this.onlinePrice = onlinePrice;
	}

	/**
	 * @return int 线上辅导价格
	 */
	public int getOnlinePrice() {
		return this.onlinePrice;
	}

	/**
	 * @param int 价格，按每小时计算（地区选择时，限上和线下的价格不同）
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return int 价格，按每小时计算（地区选择时，限上和线下的价格不同）
	 */
	public int getPrice() {
		return this.price;
	}

	/**
	 * @param int 发布时间
	 */
	public void setDateline(int dateline) {
		this.dateline = dateline;
	}

	/**
	 * @return int 发布时间
	 */
	public int getDateline() {
		return this.dateline;
	}

	/**
	 * @param int 有效期，1周、2周、1个月
	 */
	public void setValidity(int validity) {
		this.validity = validity;
	}

	/**
	 * @return int 有效期，1周、2周、1个月
	 */
	public int getValidity() {
		return this.validity;
	}

	/**
	 * @param int 浏览人数
	 */
	public void setViews(int views) {
		this.views = views;
	}

	/**
	 * @return int 浏览人数
	 */
	public int getViews() {
		return this.views;
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
		Map<String, Object> map = new HashMap<String, Object>((int) 23);
		map.put("tutorId", tutorId);
		map.put("provider", provider);
		map.put("providerType", providerType);
		map.put("status", status);
		map.put("phone", phone);
		map.put("grade", grade);
		map.put("kind1", kind1);
		map.put("kind2", kind2);
		map.put("kind3", kind3);
		map.put("tutorType", tutorType);
		map.put("tutorMode", tutorMode);
		map.put("workDay", workDay);
		map.put("openTime", openTime);
		map.put("closeTime", closeTime);
		map.put("map", this.map);
		map.put("city", city);
		map.put("title", title);
		map.put("intro", intro);
		map.put("onlinePrice", onlinePrice);
		map.put("price", price);
		map.put("dateline", dateline);
		map.put("validity", validity);
		map.put("views", views);
		map.put("memo", memo);
		return map;
	}

	@Override
	public Object[] primaryValue() {
		return new Object[] { tutorId };
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Tutor entity = (Tutor) o;
		return tutorId == entity.tutorId;
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
		sb.append("\"tutorId\":").append(tutorId).append(",");
		sb.append("\"provider\":").append(provider).append(",");
		sb.append("\"providerType\":").append(providerType).append(",");
		sb.append("\"status\":").append(status).append(",");
		sb.append("\"phone\":").append("\"" + phone + "\"").append(",");
		sb.append("\"grade\":").append(grade).append(",");
		sb.append("\"kind1\":").append(kind1).append(",");
		sb.append("\"kind2\":").append(kind2).append(",");
		sb.append("\"kind3\":").append(kind3).append(",");
		sb.append("\"tutorType\":").append(tutorType).append(",");
		sb.append("\"tutorMode\":").append(tutorMode).append(",");
		sb.append("\"workDay\":").append(workDay).append(",");
		sb.append("\"openTime\":").append(openTime).append(",");
		sb.append("\"closeTime\":").append(closeTime).append(",");
		sb.append("\"map\":").append("\"" + map + "\"").append(",");
		sb.append("\"city\":").append(city).append(",");
		sb.append("\"intro\":").append("\"" + intro + "\"").append(",");
		sb.append("\"onlinePrice\":").append(onlinePrice).append(",");
		sb.append("\"price\":").append(price).append(",");
		sb.append("\"dateline\":").append(dateline).append(",");
		sb.append("\"validity\":").append(validity).append(",");
		sb.append("\"views\":").append(views).append(",");
		sb.append("\"memo\":").append("\"" + memo + "\"");
		sb.append("}");
		return sb.toString();
	}
}
