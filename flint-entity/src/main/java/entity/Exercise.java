package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_EXERCISE", primary = { "EXERCISE_ID" })
public class Exercise implements  Entity{

	/**
	*编号
	*/
	private long   exerciseId;

	/**
	*学校编号
	*/
	private long   schoolId;

	/**
	*年级
	*/
	private int   grade;

	/**
	*科目
	*/
	private long   kind;

	/**
	*标题
	*/
	private String   subject;

	/**
	*课程或其他编号
	*/
	private long   subjectId;

	/**
	*
	*/
	private byte   subjectType;

	/**
	*时间限制，默认单位分钟
	*/
	private int   timeLimit;

	/**
	*标签
	*/
	private String   tag;

	/**
	*使用类型，课堂练习，课后练习，考试
	*/
	private byte   useType;

	/**
	*练习类型：手工组题，随机试题，在线生成（只word,pdf等文件上传，需要使用答题卡方式）
	*/
	private byte   type;

	/**
	*发送方式（如课后练习），邮箱，手机，客户端等
	*/
	private byte   sendType;

	/**
	*是否发送练习结果，用户做完练习后的结果
	*/
	private int   sendResult;

	/**
	*练习状态
	*/
	private byte   status;

	/**
	*存储路径，生成静态文件试题
	*/
	private String   dir;

	/**
	*创建人
	*/
	private long   userId;

	/**
	*创建时间
	*/
	private int   createTime;

	/**
	*备注
	*/
	private String   memo;

   
    public Exercise(){}
    
    public Exercise(long exerciseId )
    {
           this.exerciseId=exerciseId;
     }
	/**
	 * @param long   编号
	 */
	public void setExerciseId(long exerciseId)
	{
	 	this.exerciseId=exerciseId;
	}
	
	/**
	 * @return long  编号
	 */
	public long getExerciseId()
	{
	 	return this.exerciseId;
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
	 * @param int   年级
	 */
	public void setGrade(int grade)
	{
	 	this.grade=grade;
	}
	
	/**
	 * @return int  年级
	 */
	public int getGrade()
	{
	 	return this.grade;
	}
	/**
	 * @param long   科目
	 */
	public void setKind(long kind)
	{
	 	this.kind=kind;
	}
	
	/**
	 * @return long  科目
	 */
	public long getKind()
	{
	 	return this.kind;
	}
	/**
	 * @param String   标题
	 */
	public void setSubject(String subject)
	{
	 	this.subject=subject;
	}
	
	/**
	 * @return String  标题
	 */
	public String getSubject()
	{
	 	return this.subject;
	}
	/**
	 * @param long   课程或其他编号
	 */
	public void setSubjectId(long subjectId)
	{
	 	this.subjectId=subjectId;
	}
	
	/**
	 * @return long  课程或其他编号
	 */
	public long getSubjectId()
	{
	 	return this.subjectId;
	}
	/**
	 * @param byte   
	 */
	public void setSubjectType(byte subjectType)
	{
	 	this.subjectType=subjectType;
	}
	
	/**
	 * @return byte  
	 */
	public byte getSubjectType()
	{
	 	return this.subjectType;
	}
	/**
	 * @param int   时间限制，默认单位分钟
	 */
	public void setTimeLimit(int timeLimit)
	{
	 	this.timeLimit=timeLimit;
	}
	
	/**
	 * @return int  时间限制，默认单位分钟
	 */
	public int getTimeLimit()
	{
	 	return this.timeLimit;
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
	 * @param byte   使用类型，课堂练习，课后练习，考试
	 */
	public void setUseType(byte useType)
	{
	 	this.useType=useType;
	}
	
	/**
	 * @return byte  使用类型，课堂练习，课后练习，考试
	 */
	public byte getUseType()
	{
	 	return this.useType;
	}
	/**
	 * @param byte   练习类型：手工组题，随机试题，在线生成（只word,pdf等文件上传，需要使用答题卡方式）
	 */
	public void setType(byte type)
	{
	 	this.type=type;
	}
	
	/**
	 * @return byte  练习类型：手工组题，随机试题，在线生成（只word,pdf等文件上传，需要使用答题卡方式）
	 */
	public byte getType()
	{
	 	return this.type;
	}
	/**
	 * @param byte   发送方式（如课后练习），邮箱，手机，客户端等
	 */
	public void setSendType(byte sendType)
	{
	 	this.sendType=sendType;
	}
	
	/**
	 * @return byte  发送方式（如课后练习），邮箱，手机，客户端等
	 */
	public byte getSendType()
	{
	 	return this.sendType;
	}
	/**
	 * @param int   是否发送练习结果，用户做完练习后的结果
	 */
	public void setSendResult(int sendResult)
	{
	 	this.sendResult=sendResult;
	}
	
	/**
	 * @return int  是否发送练习结果，用户做完练习后的结果
	 */
	public int getSendResult()
	{
	 	return this.sendResult;
	}
	/**
	 * @param byte   练习状态
	 */
	public void setStatus(byte status)
	{
	 	this.status=status;
	}
	
	/**
	 * @return byte  练习状态
	 */
	public byte getStatus()
	{
	 	return this.status;
	}
	/**
	 * @param String   存储路径，生成静态文件试题
	 */
	public void setDir(String dir)
	{
	 	this.dir=dir;
	}
	
	/**
	 * @return String  存储路径，生成静态文件试题
	 */
	public String getDir()
	{
	 	return this.dir;
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
	    Map<String, Object> map=new HashMap<String, Object>((int)18);
	    map.put("exerciseId",exerciseId);
	    map.put("schoolId",schoolId);
	    map.put("grade",grade);
	    map.put("kind",kind);
	    map.put("subject",subject);
	    map.put("subjectId",subjectId);
	    map.put("subjectType",subjectType);
	    map.put("timeLimit",timeLimit);
	    map.put("tag",tag);
	    map.put("useType",useType);
	    map.put("type",type);
	    map.put("sendType",sendType);
	    map.put("sendResult",sendResult);
	    map.put("status",status);
	    map.put("dir",dir);
	    map.put("userId",userId);
	    map.put("createTime",createTime);
	    map.put("memo",memo);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {exerciseId};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          Exercise  entity=(Exercise)o;
          return exerciseId==entity.exerciseId;
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
	     sb.append("\"exerciseId\":").append(exerciseId).append(",");
	     sb.append("\"schoolId\":").append(schoolId).append(",");
	     sb.append("\"grade\":").append(grade).append(",");
	     sb.append("\"kind\":").append(kind).append(",");
	     sb.append("\"subject\":").append("\""+subject+"\"").append(",");
	     sb.append("\"subjectId\":").append(subjectId).append(",");
	     sb.append("\"subjectType\":").append(subjectType).append(",");
	     sb.append("\"timeLimit\":").append(timeLimit).append(",");
	     sb.append("\"tag\":").append("\""+tag+"\"").append(",");
	     sb.append("\"useType\":").append(useType).append(",");
	     sb.append("\"type\":").append(type).append(",");
	     sb.append("\"sendType\":").append(sendType).append(",");
	     sb.append("\"sendResult\":").append(sendResult).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"dir\":").append("\""+dir+"\"").append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"createTime\":").append(createTime).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
