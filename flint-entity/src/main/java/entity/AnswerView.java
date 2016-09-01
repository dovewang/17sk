package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_ANSWER_VIEW", primary = { "ID" })
public class AnswerView implements  Entity{

	/**
	*查看记录编号
	*/
	private long   id;

	/**
	*答案编号
	*/
	private long   answerId;

	/**
	*查看人编号
	*/
	private long   userId;

	/**
	*查看日期
	*/
	private int   dateline;

	/**
	*查看网络地址
	*/
	private String   netAddress;

	/**
	*是否采纳为答案
	*/
	private boolean   adopt;

	/**
	*状态，用户不投诉，才是有效记录
	*/
	private byte   status;

	/**
	*评论内容
	*/
	private String   comment;

	/**
	*是否有用的评分-1----3，作用不大为-1，一般为0，有用为1，非常有用为2
	*/
	private byte   answerScore;

	/**
	*评分项1
	*/
	private int   score1;

	/**
	*评分项2
	*/
	private int   score2;

	/**
	*评分项3
	*/
	private int   score3;

	/**
	*备注
	*/
	private String   memo;

   
    public AnswerView(){}
    
    public AnswerView(long id )
    {
           this.id=id;
     }
	/**
	 * @param long   查看记录编号
	 */
	public void setId(long id)
	{
	 	this.id=id;
	}
	
	/**
	 * @return long  查看记录编号
	 */
	public long getId()
	{
	 	return this.id;
	}
	/**
	 * @param long   答案编号
	 */
	public void setAnswerId(long answerId)
	{
	 	this.answerId=answerId;
	}
	
	/**
	 * @return long  答案编号
	 */
	public long getAnswerId()
	{
	 	return this.answerId;
	}
	/**
	 * @param long   查看人编号
	 */
	public void setUserId(long userId)
	{
	 	this.userId=userId;
	}
	
	/**
	 * @return long  查看人编号
	 */
	public long getUserId()
	{
	 	return this.userId;
	}
	/**
	 * @param int   查看日期
	 */
	public void setDateline(int dateline)
	{
	 	this.dateline=dateline;
	}
	
	/**
	 * @return int  查看日期
	 */
	public int getDateline()
	{
	 	return this.dateline;
	}
	/**
	 * @param String   查看网络地址
	 */
	public void setNetAddress(String netAddress)
	{
	 	this.netAddress=netAddress;
	}
	
	/**
	 * @return String  查看网络地址
	 */
	public String getNetAddress()
	{
	 	return this.netAddress;
	}
	/**
	 * @param boolean   是否采纳为答案
	 */
	public void setAdopt(boolean adopt)
	{
	 	this.adopt=adopt;
	}
	
	/**
	 * @return boolean  是否采纳为答案
	 */
	public boolean isAdopt()
	{
	 	return this.adopt;
	}
	/**
	 * @param byte   状态，用户不投诉，才是有效记录
	 */
	public void setStatus(byte status)
	{
	 	this.status=status;
	}
	
	/**
	 * @return byte  状态，用户不投诉，才是有效记录
	 */
	public byte getStatus()
	{
	 	return this.status;
	}
	/**
	 * @param String   评论内容
	 */
	public void setComment(String comment)
	{
	 	this.comment=comment;
	}
	
	/**
	 * @return String  评论内容
	 */
	public String getComment()
	{
	 	return this.comment;
	}
	/**
	 * @param byte   是否有用的评分-1----3，作用不大为-1，一般为0，有用为1，非常有用为2
	 */
	public void setAnswerScore(byte answerScore)
	{
	 	this.answerScore=answerScore;
	}
	
	/**
	 * @return byte  是否有用的评分-1----3，作用不大为-1，一般为0，有用为1，非常有用为2
	 */
	public byte getAnswerScore()
	{
	 	return this.answerScore;
	}
	/**
	 * @param int   评分项1
	 */
	public void setScore1(int score1)
	{
	 	this.score1=score1;
	}
	
	/**
	 * @return int  评分项1
	 */
	public int getScore1()
	{
	 	return this.score1;
	}
	/**
	 * @param int   评分项2
	 */
	public void setScore2(int score2)
	{
	 	this.score2=score2;
	}
	
	/**
	 * @return int  评分项2
	 */
	public int getScore2()
	{
	 	return this.score2;
	}
	/**
	 * @param int   评分项3
	 */
	public void setScore3(int score3)
	{
	 	this.score3=score3;
	}
	
	/**
	 * @return int  评分项3
	 */
	public int getScore3()
	{
	 	return this.score3;
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
	    Map<String, Object> map=new HashMap<String, Object>((int)13);
	    map.put("id",this.id);
	    map.put("answerId",this.answerId);
	    map.put("userId",this.userId);
	    map.put("dateline",this.dateline);
	    map.put("netAddress",this.netAddress);
	    map.put("adopt",this.adopt);
	    map.put("status",this.status);
	    map.put("comment",this.comment);
	    map.put("answerScore",this.answerScore);
	    map.put("score1",this.score1);
	    map.put("score2",this.score2);
	    map.put("score3",this.score3);
	    map.put("memo",this.memo);
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
          AnswerView  entity=(AnswerView)o;
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
	     sb.append("\"answerId\":").append(answerId).append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"dateline\":").append(dateline).append(",");
	     sb.append("\"netAddress\":").append("\""+netAddress+"\"").append(",");
	     sb.append("\"adopt\":").append(adopt).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"comment\":").append("\""+comment+"\"").append(",");
	     sb.append("\"answerScore\":").append(answerScore).append(",");
	     sb.append("\"score1\":").append(score1).append(",");
	     sb.append("\"score2\":").append(score2).append(",");
	     sb.append("\"score3\":").append(score3).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
