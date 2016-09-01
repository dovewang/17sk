package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_USER_SCHEDULE", primary = { "ID" })
public class UserSchedule implements  Entity,Comparable<UserSchedule>{

	/**
	*主键
	*/
	private long   id;

	/**
	*用户编号
	*/
	private long   userId;

	/**
	*计划名称
	*/
	private String   name;

	/**
	*计划类型，1：上课，2：教课：
	*/
	private byte   type;

	/**
	*计划主键，当用户类型为自定义计划时可为空
	*/
	private long   relatedId;

	/**
	*计划关联类型
	*/
	private byte   relatedType;

	/**
	*计划预计开始时间
	*/
	private int   predictStartTime;

	/**
	*计划预计结束时间
	*/
	private int   predictEndTime;

	/**
	*实际开始时间
	*/
	private int   startTime;

	/**
	*实际结束时间
	*/
	private int   endTime;

	/**
	*正常，取消，已结束
	*/
	private byte   status;

	/**
	*备注
	*/
	private String   memo;

   
    public UserSchedule(){}
    
    public UserSchedule(long id )
    {
           this.id=id;
     }
	/**
	 * @param long   主键
	 */
	public void setId(long id)
	{
	 	this.id=id;
	}
	
	/**
	 * @return long  主键
	 */
	public long getId()
	{
	 	return this.id;
	}
	/**
	 * @param long   用户编号
	 */
	public void setUserId(long userId)
	{
	 	this.userId=userId;
	}
	
	/**
	 * @return long  用户编号
	 */
	public long getUserId()
	{
	 	return this.userId;
	}
	/**
	 * @param String   计划名称
	 */
	public void setName(String name)
	{
	 	this.name=name;
	}
	
	/**
	 * @return String  计划名称
	 */
	public String getName()
	{
	 	return this.name;
	}
	/**
	 * @param byte   计划类型，1：上课，2：教课：
	 */
	public void setType(byte type)
	{
	 	this.type=type;
	}
	
	/**
	 * @return byte  计划类型，1：上课，2：教课：
	 */
	public byte getType()
	{
	 	return this.type;
	}
	/**
	 * @param long   计划主键，当用户类型为自定义计划时可为空
	 */
	public void setRelatedId(long relatedId)
	{
	 	this.relatedId=relatedId;
	}
	
	/**
	 * @return long  计划主键，当用户类型为自定义计划时可为空
	 */
	public long getRelatedId()
	{
	 	return this.relatedId;
	}
	/**
	 * @param byte   计划关联类型
	 */
	public void setRelatedType(byte relatedType)
	{
	 	this.relatedType=relatedType;
	}
	
	/**
	 * @return byte  计划关联类型
	 */
	public byte getRelatedType()
	{
	 	return this.relatedType;
	}
	/**
	 * @param int   计划预计开始时间
	 */
	public void setPredictStartTime(int predictStartTime)
	{
	 	this.predictStartTime=predictStartTime;
	}
	
	/**
	 * @return int  计划预计开始时间
	 */
	public int getPredictStartTime()
	{
	 	return this.predictStartTime;
	}
	/**
	 * @param int   计划预计结束时间
	 */
	public void setPredictEndTime(int predictEndTime)
	{
	 	this.predictEndTime=predictEndTime;
	}
	
	/**
	 * @return int  计划预计结束时间
	 */
	public int getPredictEndTime()
	{
	 	return this.predictEndTime;
	}
	/**
	 * @param int   实际开始时间
	 */
	public void setStartTime(int startTime)
	{
	 	this.startTime=startTime;
	}
	
	/**
	 * @return int  实际开始时间
	 */
	public int getStartTime()
	{
	 	return this.startTime;
	}
	/**
	 * @param int   实际结束时间
	 */
	public void setEndTime(int endTime)
	{
	 	this.endTime=endTime;
	}
	
	/**
	 * @return int  实际结束时间
	 */
	public int getEndTime()
	{
	 	return this.endTime;
	}
	/**
	 * @param byte   正常，取消，已结束
	 */
	public void setStatus(byte status)
	{
	 	this.status=status;
	}
	
	/**
	 * @return byte  正常，取消，已结束
	 */
	public byte getStatus()
	{
	 	return this.status;
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
	    Map<String, Object> map=new HashMap<String, Object>((int)12);
	    map.put("id",id);
	    map.put("userId",userId);
	    map.put("name",name);
	    map.put("type",type);
	    map.put("relatedId",relatedId);
	    map.put("relatedType",relatedType);
	    map.put("predictStartTime",predictStartTime);
	    map.put("predictEndTime",predictEndTime);
	    map.put("startTime",startTime);
	    map.put("endTime",endTime);
	    map.put("status",status);
	    map.put("memo",memo);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {id};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          UserSchedule  entity=(UserSchedule)o;
          return id==entity.id;
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
	     sb.append("\"id\":").append(id).append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"name\":").append("\""+name+"\"").append(",");
	     sb.append("\"type\":").append(type).append(",");
	     sb.append("\"relatedId\":").append(relatedId).append(",");
	     sb.append("\"relatedType\":").append(relatedType).append(",");
	     sb.append("\"predictStartTime\":").append(predictStartTime).append(",");
	     sb.append("\"predictEndTime\":").append(predictEndTime).append(",");
	     sb.append("\"startTime\":").append(startTime).append(",");
	     sb.append("\"endTime\":").append(endTime).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}

	@Override
	public int compareTo(UserSchedule o) {
		return 0;
	}
}
