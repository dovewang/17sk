package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_ROOM", primary = { "ROOM_ID" })
public class Room implements  Entity{

	/**
	*教室流水号
	*/
	private long   roomId;

	/**
	*0、个人，1，学校，2、用户组
	*/
	private byte   orgType;

	/**
	*访问域，唯一性和组织对应，每种类型下面的代码具有唯一性，个人组，默认为0
	*/
	private String   orgId;

	/**
	*区域代码，区号
	*/
	private int   area;

	/**
	*主题
	*/
	private String   subject;

	/**
	*话题类型，0，问题教室及临时教师(教室编号由userid+cid组合)，2，课程教师
	*/
	private byte   subjectType;

	/**
	*话题编号，课程或问题ID
	*/
	private long   subjectId;

	/**
	*子话题编号，例如问题的答案编号
	*/
	private long   subId;

	/**
	*需要支付的金额，进入教室前的提示
	*/
	private int   price;

	/**
	*创建用户编号
	*/
	private long   userId;

	/**
	*创建者IP
	*/
	private String   netAddress;

	/**
	*创建时间
	*/
	private int   createTime;

	/**
	*主持人编号
	*/
	private long   host;

	/**
	*教室状态
	*/
	private byte   status;

	/**
	*时长
	*/
	private int   time;

	/**
	*登录人数
	*/
	private int   logins;

	/**
	*最大容量
	*/
	private int   capacity;

	/**
	*密码
	*/
	private String   password;

	/**
	*开始时间
	*/
	private int   startTime;

	/**
	*结束时间
	*/
	private int   endTime;

	/**
	*课程录像目录
	*/
	private String   dir;

	/**
	*备注
	*/
	private String   memo;

   
    public Room(){}
    
    public Room(long roomId )
    {
           this.roomId=roomId;
     }
	/**
	 * @param long   教室流水号
	 */
	public void setRoomId(long roomId)
	{
	 	this.roomId=roomId;
	}
	
	/**
	 * @return long  教室流水号
	 */
	public long getRoomId()
	{
	 	return this.roomId;
	}
	/**
	 * @param byte   0、个人，1，学校，2、用户组
	 */
	public void setOrgType(byte orgType)
	{
	 	this.orgType=orgType;
	}
	
	/**
	 * @return byte  0、个人，1，学校，2、用户组
	 */
	public byte getOrgType()
	{
	 	return this.orgType;
	}
	/**
	 * @param String   访问域，唯一性和组织对应，每种类型下面的代码具有唯一性，个人组，默认为0
	 */
	public void setOrgId(String orgId)
	{
	 	this.orgId=orgId;
	}
	
	/**
	 * @return String  访问域，唯一性和组织对应，每种类型下面的代码具有唯一性，个人组，默认为0
	 */
	public String getOrgId()
	{
	 	return this.orgId;
	}
	/**
	 * @param int   区域代码，区号
	 */
	public void setArea(int area)
	{
	 	this.area=area;
	}
	
	/**
	 * @return int  区域代码，区号
	 */
	public int getArea()
	{
	 	return this.area;
	}
	/**
	 * @param String   主题
	 */
	public void setSubject(String subject)
	{
	 	this.subject=subject;
	}
	
	/**
	 * @return String  主题
	 */
	public String getSubject()
	{
	 	return this.subject;
	}
	/**
	 * @param byte   话题类型，0，问题教室及临时教师(教室编号由userid+cid组合)，2，课程教师
	 */
	public void setSubjectType(byte subjectType)
	{
	 	this.subjectType=subjectType;
	}
	
	/**
	 * @return byte  话题类型，0，问题教室及临时教师(教室编号由userid+cid组合)，2，课程教师
	 */
	public byte getSubjectType()
	{
	 	return this.subjectType;
	}
	/**
	 * @param long   话题编号，课程或问题ID
	 */
	public void setSubjectId(long subjectId)
	{
	 	this.subjectId=subjectId;
	}
	
	/**
	 * @return long  话题编号，课程或问题ID
	 */
	public long getSubjectId()
	{
	 	return this.subjectId;
	}
	/**
	 * @param long   子话题编号，例如问题的答案编号
	 */
	public void setSubId(long subId)
	{
	 	this.subId=subId;
	}
	
	/**
	 * @return long  子话题编号，例如问题的答案编号
	 */
	public long getSubId()
	{
	 	return this.subId;
	}
	/**
	 * @param int   需要支付的金额，进入教室前的提示
	 */
	public void setPrice(int price)
	{
	 	this.price=price;
	}
	
	/**
	 * @return int  需要支付的金额，进入教室前的提示
	 */
	public int getPrice()
	{
	 	return this.price;
	}
	/**
	 * @param long   创建用户编号
	 */
	public void setUserId(long userId)
	{
	 	this.userId=userId;
	}
	
	/**
	 * @return long  创建用户编号
	 */
	public long getUserId()
	{
	 	return this.userId;
	}
	/**
	 * @param String   创建者IP
	 */
	public void setNetAddress(String netAddress)
	{
	 	this.netAddress=netAddress;
	}
	
	/**
	 * @return String  创建者IP
	 */
	public String getNetAddress()
	{
	 	return this.netAddress;
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
	 * @param long   主持人编号
	 */
	public void setHost(long host)
	{
	 	this.host=host;
	}
	
	/**
	 * @return long  主持人编号
	 */
	public long getHost()
	{
	 	return this.host;
	}
	/**
	 * @param byte   教室状态
	 */
	public void setStatus(byte status)
	{
	 	this.status=status;
	}
	
	/**
	 * @return byte  教室状态
	 */
	public byte getStatus()
	{
	 	return this.status;
	}
	/**
	 * @param int   时长
	 */
	public void setTime(int time)
	{
	 	this.time=time;
	}
	
	/**
	 * @return int  时长
	 */
	public int getTime()
	{
	 	return this.time;
	}
	/**
	 * @param int   登录人数
	 */
	public void setLogins(int logins)
	{
	 	this.logins=logins;
	}
	
	/**
	 * @return int  登录人数
	 */
	public int getLogins()
	{
	 	return this.logins;
	}
	/**
	 * @param int   最大容量
	 */
	public void setCapacity(int capacity)
	{
	 	this.capacity=capacity;
	}
	
	/**
	 * @return int  最大容量
	 */
	public int getCapacity()
	{
	 	return this.capacity;
	}
	/**
	 * @param String   密码
	 */
	public void setPassword(String password)
	{
	 	this.password=password;
	}
	
	/**
	 * @return String  密码
	 */
	public String getPassword()
	{
	 	return this.password;
	}
	/**
	 * @param int   开始时间
	 */
	public void setStartTime(int startTime)
	{
	 	this.startTime=startTime;
	}
	
	/**
	 * @return int  开始时间
	 */
	public int getStartTime()
	{
	 	return this.startTime;
	}
	/**
	 * @param int   结束时间
	 */
	public void setEndTime(int endTime)
	{
	 	this.endTime=endTime;
	}
	
	/**
	 * @return int  结束时间
	 */
	public int getEndTime()
	{
	 	return this.endTime;
	}
	/**
	 * @param String   课程录像目录
	 */
	public void setDir(String dir)
	{
	 	this.dir=dir;
	}
	
	/**
	 * @return String  课程录像目录
	 */
	public String getDir()
	{
	 	return this.dir;
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
	    Map<String, Object> map=new HashMap<String, Object>((int)22);
	    map.put("roomId",this.roomId);
	    map.put("orgType",this.orgType);
	    map.put("orgId",this.orgId);
	    map.put("area",this.area);
	    map.put("subject",this.subject);
	    map.put("subjectType",this.subjectType);
	    map.put("subjectId",this.subjectId);
	    map.put("subId",this.subId);
	    map.put("price",this.price);
	    map.put("userId",this.userId);
	    map.put("netAddress",this.netAddress);
	    map.put("createTime",this.createTime);
	    map.put("host",this.host);
	    map.put("status",this.status);
	    map.put("time",this.time);
	    map.put("logins",this.logins);
	    map.put("capacity",this.capacity);
	    map.put("password",this.password);
	    map.put("startTime",this.startTime);
	    map.put("endTime",this.endTime);
	    map.put("dir",this.dir);
	    map.put("memo",this.memo);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {roomId};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          Room  entity=(Room)o;
          return roomId==entity.roomId;
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
	     sb.append("\"roomId\":").append(roomId).append(",");
	     sb.append("\"orgType\":").append(orgType).append(",");
	     sb.append("\"orgId\":").append("\""+orgId+"\"").append(",");
	     sb.append("\"area\":").append(area).append(",");
	     sb.append("\"subject\":").append("\""+subject+"\"").append(",");
	     sb.append("\"subjectType\":").append(subjectType).append(",");
	     sb.append("\"subjectId\":").append(subjectId).append(",");
	     sb.append("\"subId\":").append(subId).append(",");
	     sb.append("\"price\":").append(price).append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"netAddress\":").append("\""+netAddress+"\"").append(",");
	     sb.append("\"createTime\":").append(createTime).append(",");
	     sb.append("\"host\":").append(host).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"time\":").append(time).append(",");
	     sb.append("\"logins\":").append(logins).append(",");
	     sb.append("\"capacity\":").append(capacity).append(",");
	     sb.append("\"password\":").append("\""+password+"\"").append(",");
	     sb.append("\"startTime\":").append(startTime).append(",");
	     sb.append("\"endTime\":").append(endTime).append(",");
	     sb.append("\"dir\":").append("\""+dir+"\"").append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
