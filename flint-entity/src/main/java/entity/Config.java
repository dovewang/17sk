package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "$CONFIG", primary = { "ID" })
public class Config implements Entity {

	/**
	 * 模块代码
	 */
	private String module;

	/**
	 * 配置项
	 */
	private String name;

	/**
	 * 配置项值
	 */
	private String value;

	/**
	 * 配置项状态
	 */
	private byte status;

	/**
	 * 验证项，默认为空，用户更具实际情况实现验证项接口
	 */
	private String validate;

	/**
	 * 项目标题
	 */
	private String title;

	/**
	 * 配置项描述
	 */
	private String descript;

	/**
	*
	*/
	private int displayOrder;

	/**
	*
	*/
	private int id;

	public Config() {
	}

	public Config(int id) {
		this.id = id;
	}

	/**
	 * @param String
	 *            模块代码
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * @return String 模块代码
	 */
	public String getModule() {
		return this.module;
	}

	/**
	 * @param String
	 *            配置项
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return String 配置项
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param String
	 *            配置项值
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return String 配置项值
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * @param byte 配置项状态
	 */
	public void setStatus(byte status) {
		this.status = status;
	}

	/**
	 * @return byte 配置项状态
	 */
	public byte getStatus() {
		return this.status;
	}

	/**
	 * @param String
	 *            验证项，默认为空，用户更具实际情况实现验证项接口
	 */
	public void setValidate(String validate) {
		this.validate = validate;
	}

	/**
	 * @return String 验证项，默认为空，用户更具实际情况实现验证项接口
	 */
	public String getValidate() {
		return this.validate;
	}

	/**
	 * @param String
	 *            项目标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return String 项目标题
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * @param String
	 *            配置项描述
	 */
	public void setDescript(String descript) {
		this.descript = descript;
	}

	/**
	 * @return String 配置项描述
	 */
	public String getDescript() {
		return this.descript;
	}

	/**
	 * @param int
	 */
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * @return int
	 */
	public int getDisplayOrder() {
		return this.displayOrder;
	}

	/**
	 * @param int
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return int
	 */
	public int getId() {
		return this.id;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>((int) 9);
		map.put("module", module);
		map.put("name", name);
		map.put("value", value);
		map.put("status", status);
		map.put("validate", validate);
		map.put("title", title);
		map.put("descript", descript);
		map.put("displayOrder", displayOrder);
		map.put("id", id);
		return map;
	}

	@Override
	public Object[] primaryValue() {
		return new Object[] { id };
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Config entity = (Config) o;
		return id == entity.id;
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
		sb.append("\"module\":").append("\"" + module + "\"").append(",");
		sb.append("\"name\":").append("\"" + name + "\"").append(",");
		sb.append("\"value\":").append("\"" + value + "\"").append(",");
		sb.append("\"status\":").append(status).append(",");
		sb.append("\"validate\":").append("\"" + validate + "\"").append(",");
		sb.append("\"title\":").append("\"" + title + "\"").append(",");
		sb.append("\"descript\":").append("\"" + descript + "\"").append(",");
		sb.append("\"displayOrder\":").append(displayOrder).append(",");
		sb.append("\"id\":").append(id);
		sb.append("}");
		return sb.toString();
	}
}
