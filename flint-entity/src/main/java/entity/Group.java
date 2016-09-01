package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_GROUP", primary = { "GROUP_ID" })
public class Group implements  Entity{

	/**
	*圈子编号
	*/
	private long   groupId;

	/**
	*学校编号
	*/
	private long   schoolId;

	/**
	*圈子名称
	*/
	private String   name;

	/**
	*圈子LOGO
	*/
	private String   logo;

	/**
	*圈子类型（0班级，1学习圈）
	*/
	private byte   type;

	/**
	*状态
	*/
	private byte   status;

	/**
	*分类编号
	*/
	private long   categoryId;

	/**
	*标签
	*/
	private String   tag;

	/**
	*班级描述
	*/
	private String   intro;

	/**
	*创建人
	*/
	private long   creator;

	/**
	*入学年度
	*/
	private int   year;

	/**
	*创建时间
	*/
	private int   createTime;

	/**
	*圈主/班主任
	*/
	private long   master;

	/**
	*成员数
	*/
	private int   members;

	/**
	*话题数
	*/
	private int   topics;

	/**
	*发言数
	*/
	private int   talks;

	/**
	*0：公开的，允许任何人加入，1需要验证才能加入，2、不允许人任何人加入
	*/
	private byte   verify;

	/**
	*验证内容
	*/
	private String   certificate;

	/**
	*0：公开的，允许所有人访问（班级只能允许学校内部访问），1、只允许圈内人访问
	*/
	private byte   open;

	/**
	*备注
	*/
	private String   memo;

   
    public Group(){}
    
    public Group(long groupId )
    {
           this.groupId=groupId;
     }
	/**
	 * @param long   圈子编号
	 */
	public void setGroupId(long groupId)
	{
	 	this.groupId=groupId;
	}
	
	/**
	 * @return long  圈子编号
	 */
	public long getGroupId()
	{
	 	return this.groupId;
	}
	/**
	 * @param long   学校编号
	 */
	public void setSchoolId(long schoolId)
	{
	 	this.schoolId=schoolId;
	}
	
	/**
	 * @return long  学校编号
	 */
	public long getSchoolId()
	{
	 	return this.schoolId;
	}
	/**
	 * @param String   圈子名称
	 */
	public void setName(String name)
	{
	 	this.name=name;
	}
	
	/**
	 * @return String  圈子名称
	 */
	public String getName()
	{
	 	return this.name;
	}
	/**
	 * @param String   圈子LOGO
	 */
	public void setLogo(String logo)
	{
	 	this.logo=logo;
	}
	
	/**
	 * @return String  圈子LOGO
	 */
	public String getLogo()
	{
	 	return this.logo;
	}
	/**
	 * @param byte   圈子类型（0班级，1学习圈）
	 */
	public void setType(byte type)
	{
	 	this.type=type;
	}
	
	/**
	 * @return byte  圈子类型（0班级，1学习圈）
	 */
	public byte getType()
	{
	 	return this.type;
	}
	/**
	 * @param byte   状态
	 */
	public void setStatus(byte status)
	{
	 	this.status=status;
	}
	
	/**
	 * @return byte  状态
	 */
	public byte getStatus()
	{
	 	return this.status;
	}
	/**
	 * @param long   分类编号
	 */
	public void setCategoryId(long categoryId)
	{
	 	this.categoryId=categoryId;
	}
	
	/**
	 * @return long  分类编号
	 */
	public long getCategoryId()
	{
	 	return this.categoryId;
	}
	/**
	 * @param String   标签
	 */
	public void setTag(String tag)
	{
	 	this.tag=tag;
	}
	
	/**
	 * @return String  标签
	 */
	public String getTag()
	{
	 	return this.tag;
	}
	/**
	 * @param String   班级描述
	 */
	public void setIntro(String intro)
	{
	 	this.intro=intro;
	}
	
	/**
	 * @return String  班级描述
	 */
	public String getIntro()
	{
	 	return this.intro;
	}
	/**
	 * @param long   创建人
	 */
	public void setCreator(long creator)
	{
	 	this.creator=creator;
	}
	
	/**
	 * @return long  创建人
	 */
	public long getCreator()
	{
	 	return this.creator;
	}
	/**
	 * @param int   入学年度
	 */
	public void setYear(int year)
	{
	 	this.year=year;
	}
	
	/**
	 * @return int  入学年度
	 */
	public int getYear()
	{
	 	return this.year;
	}
	/**
	 * @param int   创建时间
	 */
	public void setCreateTime(int createTime)
	{
	 	this.createTime=createTime;
	}
	
	/**
	 * @return int  创建时间
	 */
	public int getCreateTime()
	{
	 	return this.createTime;
	}
	/**
	 * @param long   圈主/班主任
	 */
	public void setMaster(long master)
	{
	 	this.master=master;
	}
	
	/**
	 * @return long  圈主/班主任
	 */
	public long getMaster()
	{
	 	return this.master;
	}
	/**
	 * @param int   成员数
	 */
	public void setMembers(int members)
	{
	 	this.members=members;
	}
	
	/**
	 * @return int  成员数
	 */
	public int getMembers()
	{
	 	return this.members;
	}
	/**
	 * @param int   话题数
	 */
	public void setTopics(int topics)
	{
	 	this.topics=topics;
	}
	
	/**
	 * @return int  话题数
	 */
	public int getTopics()
	{
	 	return this.topics;
	}
	/**
	 * @param int   发言数
	 */
	public void setTalks(int talks)
	{
	 	this.talks=talks;
	}
	
	/**
	 * @return int  发言数
	 */
	public int getTalks()
	{
	 	return this.talks;
	}
	/**
	 * @param byte   0：公开的，允许任何人加入，1需要验证才能加入，2、不允许人任何人加入
	 */
	public void setVerify(byte verify)
	{
	 	this.verify=verify;
	}
	
	/**
	 * @return byte  0：公开的，允许任何人加入，1需要验证才能加入，2、不允许人任何人加入
	 */
	public byte getVerify()
	{
	 	return this.verify;
	}
	/**
	 * @param String   验证内容
	 */
	public void setCertificate(String certificate)
	{
	 	this.certificate=certificate;
	}
	
	/**
	 * @return String  验证内容
	 */
	public String getCertificate()
	{
	 	return this.certificate;
	}
	/**
	 * @param byte   0：公开的，允许所有人访问（班级只能允许学校内部访问），1、只允许圈内人访问
	 */
	public void setOpen(byte open)
	{
	 	this.open=open;
	}
	
	/**
	 * @return byte  0：公开的，允许所有人访问（班级只能允许学校内部访问），1、只允许圈内人访问
	 */
	public byte getOpen()
	{
	 	return this.open;
	}
	/**
	 * @param String   备注
	 */
	public void setMemo(String memo)
	{
	 	this.memo=memo;
	}
	
	/**
	 * @return String  备注
	 */
	public String getMemo()
	{
	 	return this.memo;
	}

   @Override
	public Map<String, Object> toMap() {
	    Map<String, Object> map=new HashMap<String, Object>((int)20);
	    map.put("groupId",this.groupId);
	    map.put("schoolId",this.schoolId);
	    map.put("name",this.name);
	    map.put("logo",this.logo);
	    map.put("type",this.type);
	    map.put("status",this.status);
	    map.put("categoryId",this.categoryId);
	    map.put("tag",this.tag);
	    map.put("intro",this.intro);
	    map.put("creator",this.creator);
	    map.put("year",this.year);
	    map.put("createTime",this.createTime);
	    map.put("master",this.master);
	    map.put("members",this.members);
	    map.put("topics",this.topics);
	    map.put("talks",this.talks);
	    map.put("verify",this.verify);
	    map.put("certificate",this.certificate);
	    map.put("open",this.open);
	    map.put("memo",this.memo);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {groupId};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          Group  entity=(Group)o;
          return groupId==entity.groupId;
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
	
	
	public String toString(){
	    StringBuilder sb=new StringBuilder("{");
	     sb.append("\"groupId\":").append(groupId).append(",");
	     sb.append("\"schoolId\":").append(schoolId).append(",");
	     sb.append("\"name\":").append("\""+name+"\"").append(",");
	     sb.append("\"logo\":").append("\""+logo+"\"").append(",");
	     sb.append("\"type\":").append(type).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"categoryId\":").append(categoryId).append(",");
	     sb.append("\"tag\":").append("\""+tag+"\"").append(",");
	     sb.append("\"intro\":").append("\""+intro+"\"").append(",");
	     sb.append("\"creator\":").append(creator).append(",");
	     sb.append("\"year\":").append(year).append(",");
	     sb.append("\"createTime\":").append(createTime).append(",");
	     sb.append("\"master\":").append(master).append(",");
	     sb.append("\"members\":").append(members).append(",");
	     sb.append("\"topics\":").append(topics).append(",");
	     sb.append("\"talks\":").append(talks).append(",");
	     sb.append("\"verify\":").append(verify).append(",");
	     sb.append("\"certificate\":").append("\""+certificate+"\"").append(",");
	     sb.append("\"open\":").append(open).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
