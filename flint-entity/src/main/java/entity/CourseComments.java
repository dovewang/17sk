package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_COURSE_COMMENTS", primary = { "COMMENT_ID" })
public class CourseComments implements  Entity{

	/**
	*评论编号
	*/
	private long   commentId;

	/**
	*课程编号
	*/
	private long   courseId;

	/**
	*评价用户
	*/
	private long   userId;

	/**
	*评价时间
	*/
	private int   createTime;

	/**
	*评价来源，互联网ip，手机等
	*/
	private String   come;

	/**
	*课程得分
	*/
	private int   courseScore;

	/**
	*教师得分
	*/
	private int   teacherScore;

	/**
	*评价内容
	*/
	private String   content;

	/**
	*
	*/
	private int   score1;

	/**
	*
	*/
	private int   score2;

	/**
	*
	*/
	private int   score3;

	/**
	*
	*/
	private int   score4;

	/**
	*
	*/
	private int   score5;

	/**
	*
	*/
	private int   score6;

	/**
	*状态
	*/
	private byte   status;

	/**
	*
	*/
	private String   memo;

   
    public CourseComments(){}
    
    public CourseComments(long commentId )
    {
           this.commentId=commentId;
     }
	/**
	 * @param long   评论编号
	 */
	public void setCommentId(long commentId)
	{
	 	this.commentId=commentId;
	}
	
	/**
	 * @return long  评论编号
	 */
	public long getCommentId()
	{
	 	return this.commentId;
	}
	/**
	 * @param long   课程编号
	 */
	public void setCourseId(long courseId)
	{
	 	this.courseId=courseId;
	}
	
	/**
	 * @return long  课程编号
	 */
	public long getCourseId()
	{
	 	return this.courseId;
	}
	/**
	 * @param long   评价用户
	 */
	public void setUserId(long userId)
	{
	 	this.userId=userId;
	}
	
	/**
	 * @return long  评价用户
	 */
	public long getUserId()
	{
	 	return this.userId;
	}
	/**
	 * @param int   评价时间
	 */
	public void setCreateTime(int createTime)
	{
	 	this.createTime=createTime;
	}
	
	/**
	 * @return int  评价时间
	 */
	public int getCreateTime()
	{
	 	return this.createTime;
	}
	/**
	 * @param String   评价来源，互联网ip，手机等
	 */
	public void setCome(String come)
	{
	 	this.come=come;
	}
	
	/**
	 * @return String  评价来源，互联网ip，手机等
	 */
	public String getCome()
	{
	 	return this.come;
	}
	/**
	 * @param int   课程得分
	 */
	public void setCourseScore(int courseScore)
	{
	 	this.courseScore=courseScore;
	}
	
	/**
	 * @return int  课程得分
	 */
	public int getCourseScore()
	{
	 	return this.courseScore;
	}
	/**
	 * @param int   教师得分
	 */
	public void setTeacherScore(int teacherScore)
	{
	 	this.teacherScore=teacherScore;
	}
	
	/**
	 * @return int  教师得分
	 */
	public int getTeacherScore()
	{
	 	return this.teacherScore;
	}
	/**
	 * @param String   评价内容
	 */
	public void setContent(String content)
	{
	 	this.content=content;
	}
	
	/**
	 * @return String  评价内容
	 */
	public String getContent()
	{
	 	return this.content;
	}
	/**
	 * @param int   
	 */
	public void setScore1(int score1)
	{
	 	this.score1=score1;
	}
	
	/**
	 * @return int  
	 */
	public int getScore1()
	{
	 	return this.score1;
	}
	/**
	 * @param int   
	 */
	public void setScore2(int score2)
	{
	 	this.score2=score2;
	}
	
	/**
	 * @return int  
	 */
	public int getScore2()
	{
	 	return this.score2;
	}
	/**
	 * @param int   
	 */
	public void setScore3(int score3)
	{
	 	this.score3=score3;
	}
	
	/**
	 * @return int  
	 */
	public int getScore3()
	{
	 	return this.score3;
	}
	/**
	 * @param int   
	 */
	public void setScore4(int score4)
	{
	 	this.score4=score4;
	}
	
	/**
	 * @return int  
	 */
	public int getScore4()
	{
	 	return this.score4;
	}
	/**
	 * @param int   
	 */
	public void setScore5(int score5)
	{
	 	this.score5=score5;
	}
	
	/**
	 * @return int  
	 */
	public int getScore5()
	{
	 	return this.score5;
	}
	/**
	 * @param int   
	 */
	public void setScore6(int score6)
	{
	 	this.score6=score6;
	}
	
	/**
	 * @return int  
	 */
	public int getScore6()
	{
	 	return this.score6;
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
	 * @param String   
	 */
	public void setMemo(String memo)
	{
	 	this.memo=memo;
	}
	
	/**
	 * @return String  
	 */
	public String getMemo()
	{
	 	return this.memo;
	}

   @Override
	public Map<String, Object> toMap() {
	    Map<String, Object> map=new HashMap<String, Object>((int)16);
	    map.put("commentId",commentId);
	    map.put("courseId",courseId);
	    map.put("userId",userId);
	    map.put("createTime",createTime);
	    map.put("come",come);
	    map.put("courseScore",courseScore);
	    map.put("teacherScore",teacherScore);
	    map.put("content",content);
	    map.put("score1",score1);
	    map.put("score2",score2);
	    map.put("score3",score3);
	    map.put("score4",score4);
	    map.put("score5",score5);
	    map.put("score6",score6);
	    map.put("status",status);
	    map.put("memo",memo);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {commentId};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          CourseComments  entity=(CourseComments)o;
          return commentId==entity.commentId;
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
	     sb.append("\"commentId\":").append(commentId).append(",");
	     sb.append("\"courseId\":").append(courseId).append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"createTime\":").append(createTime).append(",");
	     sb.append("\"come\":").append("\""+come+"\"").append(",");
	     sb.append("\"courseScore\":").append(courseScore).append(",");
	     sb.append("\"teacherScore\":").append(teacherScore).append(",");
	     sb.append("\"content\":").append("\""+content+"\"").append(",");
	     sb.append("\"score1\":").append(score1).append(",");
	     sb.append("\"score2\":").append(score2).append(",");
	     sb.append("\"score3\":").append(score3).append(",");
	     sb.append("\"score4\":").append(score4).append(",");
	     sb.append("\"score5\":").append(score5).append(",");
	     sb.append("\"score6\":").append(score6).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
