package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_USER_EXERCISE", primary = { "ID" })
public class UserExercise implements  Entity{

	/**
	*流水号
	*/
	private long   id;

	/**
	*教室编号，记录用户在那个教室做的练习，默认为0，代表非教室练习
	*/
	private long   roomId;

	/**
	*练习编号
	*/
	private long   exerciseId;

	/**
	*用户编号
	*/
	private long   userId;

	/**
	*总耗时
	*/
	private int   totalTime;

	/**
	*对题数
	*/
	private int   corrects;

	/**
	*错题数
	*/
	private int   wrongs;

	/**
	*错题所有的编号，以逗号分割
	*/
	private String   wrongQuestions;

	/**
	*总得分
	*/
	private int   score;

	/**
	*知识点弱项
	*/
	private String   weak;

	/**
	*练习时间
	*/
	private int   exerciseTime;

	/**
	*评卷人
	*/
	private long   marker;

	/**
	*评卷时间
	*/
	private int   markTime;

	/**
	*备注
	*/
	private String   memo;

   
    public UserExercise(){}
    
    public UserExercise(long id )
    {
           this.id=id;
     }
	/**
	 * @param long   流水号
	 */
	public void setId(long id)
	{
	 	this.id=id;
	}
	
	/**
	 * @return long  流水号
	 */
	public long getId()
	{
	 	return this.id;
	}
	/**
	 * @param long   教室编号，记录用户在那个教室做的练习，默认为0，代表非教室练习
	 */
	public void setRoomId(long roomId)
	{
	 	this.roomId=roomId;
	}
	
	/**
	 * @return long  教室编号，记录用户在那个教室做的练习，默认为0，代表非教室练习
	 */
	public long getRoomId()
	{
	 	return this.roomId;
	}
	/**
	 * @param long   练习编号
	 */
	public void setExerciseId(long exerciseId)
	{
	 	this.exerciseId=exerciseId;
	}
	
	/**
	 * @return long  练习编号
	 */
	public long getExerciseId()
	{
	 	return this.exerciseId;
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
	 * @param int   总耗时
	 */
	public void setTotalTime(int totalTime)
	{
	 	this.totalTime=totalTime;
	}
	
	/**
	 * @return int  总耗时
	 */
	public int getTotalTime()
	{
	 	return this.totalTime;
	}
	/**
	 * @param int   对题数
	 */
	public void setCorrects(int corrects)
	{
	 	this.corrects=corrects;
	}
	
	/**
	 * @return int  对题数
	 */
	public int getCorrects()
	{
	 	return this.corrects;
	}
	/**
	 * @param int   错题数
	 */
	public void setWrongs(int wrongs)
	{
	 	this.wrongs=wrongs;
	}
	
	/**
	 * @return int  错题数
	 */
	public int getWrongs()
	{
	 	return this.wrongs;
	}
	/**
	 * @param String   错题所有的编号，以逗号分割
	 */
	public void setWrongQuestions(String wrongQuestions)
	{
	 	this.wrongQuestions=wrongQuestions;
	}
	
	/**
	 * @return String  错题所有的编号，以逗号分割
	 */
	public String getWrongQuestions()
	{
	 	return this.wrongQuestions;
	}
	/**
	 * @param int   总得分
	 */
	public void setScore(int score)
	{
	 	this.score=score;
	}
	
	/**
	 * @return int  总得分
	 */
	public int getScore()
	{
	 	return this.score;
	}
	/**
	 * @param String   知识点弱项
	 */
	public void setWeak(String weak)
	{
	 	this.weak=weak;
	}
	
	/**
	 * @return String  知识点弱项
	 */
	public String getWeak()
	{
	 	return this.weak;
	}
	/**
	 * @param int   练习时间
	 */
	public void setExerciseTime(int exerciseTime)
	{
	 	this.exerciseTime=exerciseTime;
	}
	
	/**
	 * @return int  练习时间
	 */
	public int getExerciseTime()
	{
	 	return this.exerciseTime;
	}
	/**
	 * @param long   评卷人
	 */
	public void setMarker(long marker)
	{
	 	this.marker=marker;
	}
	
	/**
	 * @return long  评卷人
	 */
	public long getMarker()
	{
	 	return this.marker;
	}
	/**
	 * @param int   评卷时间
	 */
	public void setMarkTime(int markTime)
	{
	 	this.markTime=markTime;
	}
	
	/**
	 * @return int  评卷时间
	 */
	public int getMarkTime()
	{
	 	return this.markTime;
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
	    Map<String, Object> map=new HashMap<String, Object>((int)14);
	    map.put("id",id);
	    map.put("roomId",roomId);
	    map.put("exerciseId",exerciseId);
	    map.put("userId",userId);
	    map.put("totalTime",totalTime);
	    map.put("corrects",corrects);
	    map.put("wrongs",wrongs);
	    map.put("wrongQuestions",wrongQuestions);
	    map.put("score",score);
	    map.put("weak",weak);
	    map.put("exerciseTime",exerciseTime);
	    map.put("marker",marker);
	    map.put("markTime",markTime);
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
          UserExercise  entity=(UserExercise)o;
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
	     sb.append("\"roomId\":").append(roomId).append(",");
	     sb.append("\"exerciseId\":").append(exerciseId).append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"totalTime\":").append(totalTime).append(",");
	     sb.append("\"corrects\":").append(corrects).append(",");
	     sb.append("\"wrongs\":").append(wrongs).append(",");
	     sb.append("\"wrongQuestions\":").append("\""+wrongQuestions+"\"").append(",");
	     sb.append("\"score\":").append(score).append(",");
	     sb.append("\"weak\":").append("\""+weak+"\"").append(",");
	     sb.append("\"exerciseTime\":").append(exerciseTime).append(",");
	     sb.append("\"marker\":").append(marker).append(",");
	     sb.append("\"markTime\":").append(markTime).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
