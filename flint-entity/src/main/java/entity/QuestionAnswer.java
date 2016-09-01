package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_QUESTION_ANSWER", primary = { "ANSWER_ID" })
public class QuestionAnswer implements  Entity{

	/**
	*答案编号
	*/
	private long   answerId;

	/**
	*问题的ID
	*/
	private long   questionId;

	/**
	*解答人ID
	*/
	private long   answer;

	/**
	*解答时间
	*/
	private int   answerTime;

	/**
	*0，答案审核中；1、正常，9已被删除
	*/
	private byte   status;

	/**
	*答案类型，1是教室上课方式（推荐） 2、是文字解答方式
	*/
	private byte   type;

	/**
	*上课的教室编号
	*/
	private long   roomId;

	/**
	*文字解答方式的内容
	*/
	private String   content;

	/**
	*公开价格
	*/
	private int   price;

	/**
	*平均得分
	*/
	private double   score;

	/**
	*查看人数
	*/
	private int   views;

	/**
	*采纳答案人数
	*/
	private int   adoptions;

	/**
	*作用不大的人数1
	*/
	private int   mark1;

	/**
	*一般的人数2
	*/
	private int   mark2;

	/**
	*有用的人数3
	*/
	private int   mark3;

	/**
	*非常有用的人数4
	*/
	private int   mark4;

	/**
	*完美5
	*/
	private int   mark5;

	/**
	*备注
	*/
	private String   memo;

   
    public QuestionAnswer(){}
    
    public QuestionAnswer(long answerId )
    {
           this.answerId=answerId;
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
	 * @param long   问题的ID
	 */
	public void setQuestionId(long questionId)
	{
	 	this.questionId=questionId;
	}
	
	/**
	 * @return long  问题的ID
	 */
	public long getQuestionId()
	{
	 	return this.questionId;
	}
	/**
	 * @param long   解答人ID
	 */
	public void setAnswer(long answer)
	{
	 	this.answer=answer;
	}
	
	/**
	 * @return long  解答人ID
	 */
	public long getAnswer()
	{
	 	return this.answer;
	}
	/**
	 * @param int   解答时间
	 */
	public void setAnswerTime(int answerTime)
	{
	 	this.answerTime=answerTime;
	}
	
	/**
	 * @return int  解答时间
	 */
	public int getAnswerTime()
	{
	 	return this.answerTime;
	}
	/**
	 * @param byte   0，答案审核中；1、正常，9已被删除
	 */
	public void setStatus(byte status)
	{
	 	this.status=status;
	}
	
	/**
	 * @return byte  0，答案审核中；1、正常，9已被删除
	 */
	public byte getStatus()
	{
	 	return this.status;
	}
	/**
	 * @param byte   答案类型，1是教室上课方式（推荐） 2、是文字解答方式
	 */
	public void setType(byte type)
	{
	 	this.type=type;
	}
	
	/**
	 * @return byte  答案类型，1是教室上课方式（推荐） 2、是文字解答方式
	 */
	public byte getType()
	{
	 	return this.type;
	}
	/**
	 * @param long   上课的教室编号
	 */
	public void setRoomId(long roomId)
	{
	 	this.roomId=roomId;
	}
	
	/**
	 * @return long  上课的教室编号
	 */
	public long getRoomId()
	{
	 	return this.roomId;
	}
	/**
	 * @param String   文字解答方式的内容
	 */
	public void setContent(String content)
	{
	 	this.content=content;
	}
	
	/**
	 * @return String  文字解答方式的内容
	 */
	public String getContent()
	{
	 	return this.content;
	}
	/**
	 * @param int   公开价格
	 */
	public void setPrice(int price)
	{
	 	this.price=price;
	}
	
	/**
	 * @return int  公开价格
	 */
	public int getPrice()
	{
	 	return this.price;
	}
	/**
	 * @param double   平均得分
	 */
	public void setScore(double score)
	{
	 	this.score=score;
	}
	
	/**
	 * @return double  平均得分
	 */
	public double getScore()
	{
	 	return this.score;
	}
	/**
	 * @param int   查看人数
	 */
	public void setViews(int views)
	{
	 	this.views=views;
	}
	
	/**
	 * @return int  查看人数
	 */
	public int getViews()
	{
	 	return this.views;
	}
	/**
	 * @param int   采纳答案人数
	 */
	public void setAdoptions(int adoptions)
	{
	 	this.adoptions=adoptions;
	}
	
	/**
	 * @return int  采纳答案人数
	 */
	public int getAdoptions()
	{
	 	return this.adoptions;
	}
	/**
	 * @param int   作用不大的人数1
	 */
	public void setMark1(int mark1)
	{
	 	this.mark1=mark1;
	}
	
	/**
	 * @return int  作用不大的人数1
	 */
	public int getMark1()
	{
	 	return this.mark1;
	}
	/**
	 * @param int   一般的人数2
	 */
	public void setMark2(int mark2)
	{
	 	this.mark2=mark2;
	}
	
	/**
	 * @return int  一般的人数2
	 */
	public int getMark2()
	{
	 	return this.mark2;
	}
	/**
	 * @param int   有用的人数3
	 */
	public void setMark3(int mark3)
	{
	 	this.mark3=mark3;
	}
	
	/**
	 * @return int  有用的人数3
	 */
	public int getMark3()
	{
	 	return this.mark3;
	}
	/**
	 * @param int   非常有用的人数4
	 */
	public void setMark4(int mark4)
	{
	 	this.mark4=mark4;
	}
	
	/**
	 * @return int  非常有用的人数4
	 */
	public int getMark4()
	{
	 	return this.mark4;
	}
	/**
	 * @param int   完美5
	 */
	public void setMark5(int mark5)
	{
	 	this.mark5=mark5;
	}
	
	/**
	 * @return int  完美5
	 */
	public int getMark5()
	{
	 	return this.mark5;
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
	    map.put("answerId",this.answerId);
	    map.put("questionId",this.questionId);
	    map.put("answer",this.answer);
	    map.put("answerTime",this.answerTime);
	    map.put("status",this.status);
	    map.put("type",this.type);
	    map.put("roomId",this.roomId);
	    map.put("content",this.content);
	    map.put("price",this.price);
	    map.put("score",this.score);
	    map.put("views",this.views);
	    map.put("adoptions",this.adoptions);
	    map.put("mark1",this.mark1);
	    map.put("mark2",this.mark2);
	    map.put("mark3",this.mark3);
	    map.put("mark4",this.mark4);
	    map.put("mark5",this.mark5);
	    map.put("memo",this.memo);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {answerId};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          QuestionAnswer  entity=(QuestionAnswer)o;
          return answerId==entity.answerId;
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
	     sb.append("\"answerId\":").append(answerId).append(",");
	     sb.append("\"questionId\":").append(questionId).append(",");
	     sb.append("\"answer\":").append(answer).append(",");
	     sb.append("\"answerTime\":").append(answerTime).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"type\":").append(type).append(",");
	     sb.append("\"roomId\":").append(roomId).append(",");
	     sb.append("\"content\":").append("\""+content+"\"").append(",");
	     sb.append("\"price\":").append(price).append(",");
	     sb.append("\"score\":").append(score).append(",");
	     sb.append("\"views\":").append(views).append(",");
	     sb.append("\"adoptions\":").append(adoptions).append(",");
	     sb.append("\"mark1\":").append(mark1).append(",");
	     sb.append("\"mark2\":").append(mark2).append(",");
	     sb.append("\"mark3\":").append(mark3).append(",");
	     sb.append("\"mark4\":").append(mark4).append(",");
	     sb.append("\"mark5\":").append(mark5).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
