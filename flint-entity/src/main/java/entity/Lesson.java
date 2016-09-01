package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_LESSON", primary = { "LESSON_ID" })
public class Lesson implements  Entity{

	/**
	*
	*/
	private long   lessonId;

	/**
	*
	*/
	private String   lesson;

	/**
	*
	*/
	private long   schoolId;

	/**
	*圈子编号
	*/
	private long   groupId;

	/**
	*默认只在学校内部开放：0：内部开放，1：完全开放
	*/
	private byte   open;

	/**
	*
	*/
	private int   createTime;

	/**
	*
	*/
	private int   updateTime;

	/**
	*
	*/
	private String   intro;

	/**
	*创建人
	*/
	private long   userId;

	/**
	*可以任意制定
	*/
	private String   teacher;

	/**
	*0：已删除，1、正常
	*/
	private byte   status;

	/**
	*备注
	*/
	private String   memo;

   
    public Lesson(){}
    
    public Lesson(long lessonId )
    {
           this.lessonId=lessonId;
     }
	/**
	 * @param long   
	 */
	public void setLessonId(long lessonId)
	{
	 	this.lessonId=lessonId;
	}
	
	/**
	 * @return long  
	 */
	public long getLessonId()
	{
	 	return this.lessonId;
	}
	/**
	 * @param String   
	 */
	public void setLesson(String lesson)
	{
	 	this.lesson=lesson;
	}
	
	/**
	 * @return String  
	 */
	public String getLesson()
	{
	 	return this.lesson;
	}
	/**
	 * @param long   
	 */
	public void setSchoolId(long schoolId)
	{
	 	this.schoolId=schoolId;
	}
	
	/**
	 * @return long  
	 */
	public long getSchoolId()
	{
	 	return this.schoolId;
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
	 * @param byte   默认只在学校内部开放：0：内部开放，1：完全开放
	 */
	public void setOpen(byte open)
	{
	 	this.open=open;
	}
	
	/**
	 * @return byte  默认只在学校内部开放：0：内部开放，1：完全开放
	 */
	public byte getOpen()
	{
	 	return this.open;
	}
	/**
	 * @param int   
	 */
	public void setCreateTime(int createTime)
	{
	 	this.createTime=createTime;
	}
	
	/**
	 * @return int  
	 */
	public int getCreateTime()
	{
	 	return this.createTime;
	}
	/**
	 * @param int   
	 */
	public void setUpdateTime(int updateTime)
	{
	 	this.updateTime=updateTime;
	}
	
	/**
	 * @return int  
	 */
	public int getUpdateTime()
	{
	 	return this.updateTime;
	}
	/**
	 * @param String   
	 */
	public void setIntro(String intro)
	{
	 	this.intro=intro;
	}
	
	/**
	 * @return String  
	 */
	public String getIntro()
	{
	 	return this.intro;
	}
	/**
	 * @param long   创建人
	 */
	public void setUserId(long userId)
	{
	 	this.userId=userId;
	}
	
	/**
	 * @return long  创建人
	 */
	public long getUserId()
	{
	 	return this.userId;
	}
	/**
	 * @param String   可以任意制定
	 */
	public void setTeacher(String teacher)
	{
	 	this.teacher=teacher;
	}
	
	/**
	 * @return String  可以任意制定
	 */
	public String getTeacher()
	{
	 	return this.teacher;
	}
	/**
	 * @param byte   0：已删除，1、正常
	 */
	public void setStatus(byte status)
	{
	 	this.status=status;
	}
	
	/**
	 * @return byte  0：已删除，1、正常
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
	    map.put("lessonId",this.lessonId);
	    map.put("lesson",this.lesson);
	    map.put("schoolId",this.schoolId);
	    map.put("groupId",this.groupId);
	    map.put("open",this.open);
	    map.put("createTime",this.createTime);
	    map.put("updateTime",this.updateTime);
	    map.put("intro",this.intro);
	    map.put("userId",this.userId);
	    map.put("teacher",this.teacher);
	    map.put("status",this.status);
	    map.put("memo",this.memo);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {lessonId};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          Lesson  entity=(Lesson)o;
          return lessonId==entity.lessonId;
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
	     sb.append("\"lessonId\":").append(lessonId).append(",");
	     sb.append("\"lesson\":").append("\""+lesson+"\"").append(",");
	     sb.append("\"schoolId\":").append(schoolId).append(",");
	     sb.append("\"groupId\":").append(groupId).append(",");
	     sb.append("\"open\":").append(open).append(",");
	     sb.append("\"createTime\":").append(createTime).append(",");
	     sb.append("\"updateTime\":").append(updateTime).append(",");
	     sb.append("\"intro\":").append("\""+intro+"\"").append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"teacher\":").append("\""+teacher+"\"").append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
