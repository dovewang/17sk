package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_DOCUMENT", primary = { "DOC_ID" })
public class Document implements Entity {

	/**
	 * 文档编号
	 */
	private long docId;

	/**
	 * 实际文件标号
	 */
	private String fileId;

	/**
	 * 文档名称
	 */
	private String name;

	/**
	 * 标签
	 */
	private String tags;

	/**
	 * 上传时间
	 */
	private int dateline;

	/**
	 * 上传用户ID
	 */
	private long owner;

	/**
	 * 文档状态
	 */
	private byte status;

	/**
	 * 学校编号
	 */
	private long schoolId;

	/**
	 * 圈子编号
	 */
	private long groupId;

	private int category;

	/**
	 * 使用范围，常用来标记课程
	 */
	private String scope;

	/**
	 * 共享模式，0、完全保密，1、对所有人公开，2、仅对学校内部公开，3.特定组（群）公开
	 */
	private byte shareMode;

	/**
	 * 共享对象：学校代码，组代码
	 */
	private long shareScope;

	/**
	 * 销售价格，可以使用积分或点数购买，这里默认问点数，可以用积分到相应的地方对换
	 */
	private int price;

	/**
	 * 元数据，使用json格式进行数据存储，不同文件可以自定义不同的模版
	 */
	private String metadata;

	/**
	 * 下载次数
	 */
	private int downloads;

	/**
	 * 购买人数
	 */
	private int buyers;

	/**
	 * 赠送人数
	 */
	private int gives;

	/**
	 * 得分
	 */
	private byte scores;

	/**
	 * 备注
	 */
	private String memo;

	public Document() {
	}

	public Document(long docId) {
		this.docId = docId;
	}

	/**
	 * @param long 文档编号
	 */
	public void setDocId(long docId) {
		this.docId = docId;
	}

	/**
	 * @return long 文档编号
	 */
	public long getDocId() {
		return this.docId;
	}

	/**
	 * @param String
	 *            实际文件标号
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return String 实际文件标号
	 */
	public String getFileId() {
		return this.fileId;
	}

	/**
	 * @param String
	 *            文档名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return String 文档名称
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param String
	 *            标签
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @return String 标签
	 */
	public String getTags() {
		return this.tags;
	}

	/**
	 * @param int 上传时间
	 */
	public void setDateline(int dateline) {
		this.dateline = dateline;
	}

	/**
	 * @return int 上传时间
	 */
	public int getDateline() {
		return this.dateline;
	}

	/**
	 * @param long 上传用户ID
	 */
	public void setOwner(long owner) {
		this.owner = owner;
	}

	/**
	 * @return long 上传用户ID
	 */
	public long getOwner() {
		return this.owner;
	}

	/**
	 * @param byte 文档状态
	 */
	public void setStatus(byte status) {
		this.status = status;
	}

	/**
	 * @return byte 文档状态
	 */
	public byte getStatus() {
		return this.status;
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
	 * @param long 圈子编号
	 */
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return long 圈子编号
	 */
	public long getGroupId() {
		return this.groupId;
	}

	/**
	 * @param String
	 *            使用范围，常用来标记课程
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @return String 使用范围，常用来标记课程
	 */
	public String getScope() {
		return this.scope;
	}

	/**
	 * @param byte 共享模式，0、完全保密，1、对所有人公开，2、仅对学校内部公开，3.特定组（群）公开
	 */
	public void setShareMode(byte shareMode) {
		this.shareMode = shareMode;
	}

	/**
	 * @return byte 共享模式，0、完全保密，1、对所有人公开，2、仅对学校内部公开，3.特定组（群）公开
	 */
	public byte getShareMode() {
		return this.shareMode;
	}

	/**
	 * @param long 共享对象：学校代码，组代码
	 */
	public void setShareScope(long shareScope) {
		this.shareScope = shareScope;
	}

	/**
	 * @return long 共享对象：学校代码，组代码
	 */
	public long getShareScope() {
		return this.shareScope;
	}

	/**
	 * @param int 销售价格，可以使用积分或点数购买，这里默认问点数，可以用积分到相应的地方对换
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return int 销售价格，可以使用积分或点数购买，这里默认问点数，可以用积分到相应的地方对换
	 */
	public int getPrice() {
		return this.price;
	}

	/**
	 * @param String
	 *            元数据，使用json格式进行数据存储，不同文件可以自定义不同的模版
	 */
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	/**
	 * @return String 元数据，使用json格式进行数据存储，不同文件可以自定义不同的模版
	 */
	public String getMetadata() {
		return this.metadata;
	}

	/**
	 * @param int 下载次数
	 */
	public void setDownloads(int downloads) {
		this.downloads = downloads;
	}

	/**
	 * @return int 下载次数
	 */
	public int getDownloads() {
		return this.downloads;
	}

	/**
	 * @param int 购买人数
	 */
	public void setBuyers(int buyers) {
		this.buyers = buyers;
	}

	/**
	 * @return int 购买人数
	 */
	public int getBuyers() {
		return this.buyers;
	}

	/**
	 * @param int 赠送人数
	 */
	public void setGives(int gives) {
		this.gives = gives;
	}

	/**
	 * @return int 赠送人数
	 */
	public int getGives() {
		return this.gives;
	}

	/**
	 * @param byte 得分
	 */
	public void setScores(byte scores) {
		this.scores = scores;
	}

	/**
	 * @return byte 得分
	 */
	public byte getScores() {
		return this.scores;
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
		Map<String, Object> map = new HashMap<String, Object>((int) 20);
		map.put("docId", this.docId);
		map.put("fileId", this.fileId);
		map.put("name", this.name);
		map.put("tags", this.tags);
		map.put("dateline", this.dateline);
		map.put("owner", this.owner);
		map.put("status", this.status);
		map.put("schoolId", this.schoolId);
		map.put("groupId", this.groupId);
		map.put("category", this.category);
		map.put("scope", this.scope);
		map.put("shareMode", this.shareMode);
		map.put("shareScope", this.shareScope);
		map.put("price", this.price);
		map.put("metadata", this.metadata);
		map.put("downloads", this.downloads);
		map.put("buyers", this.buyers);
		map.put("gives", this.gives);
		map.put("scores", this.scores);
		map.put("memo", this.memo);
		return map;
	}

	@Override
	public Object[] primaryValue() {
		return new Object[] { docId };
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Document entity = (Document) o;
		return docId == entity.docId;
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

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"docId\":").append(docId).append(",");
		sb.append("\"fileId\":").append("\"" + fileId + "\"").append(",");
		sb.append("\"name\":").append("\"" + name + "\"").append(",");
		sb.append("\"tags\":").append("\"" + tags + "\"").append(",");
		sb.append("\"dateline\":").append(dateline).append(",");
		sb.append("\"owner\":").append(owner).append(",");
		sb.append("\"status\":").append(status).append(",");
		sb.append("\"schoolId\":").append(schoolId).append(",");
		sb.append("\"groupId\":").append(groupId).append(",");
		sb.append("\"scope\":").append("\"" + scope + "\"").append(",");
		sb.append("\"category\":").append(category).append(",");
		sb.append("\"shareMode\":").append(shareMode).append(",");
		sb.append("\"shareScope\":").append(shareScope).append(",");
		sb.append("\"price\":").append(price).append(",");
		sb.append("\"metadata\":").append("\"" + metadata + "\"").append(",");
		sb.append("\"downloads\":").append(downloads).append(",");
		sb.append("\"buyers\":").append(buyers).append(",");
		sb.append("\"gives\":").append(gives).append(",");
		sb.append("\"scores\":").append(scores).append(",");
		sb.append("\"memo\":").append("\"" + memo + "\"");
		sb.append("}");
		return sb.toString();
	}
}
