package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_USER_EXERCISE_RECORD", primary = {  })
public class UserExerciseRecord implements  Entity{

	/**
	*流水号
	*/
	private long   id;

	/**
	*问题编号
	*/
	private long   questionId;

	/**
	*用户ID，可以方便用户查找自己的错题，存储到错题表，这里只作为历史记录
	*/
	private long   userId;

	/**
	*知识点，方便统计用户的知识点弱点，可以对症下药
	*/
	private long   knowledge;

	/**
	*用户答案
	*/
	private String   answer;

	/**
	*是否正确，只判断最终结构
	*/
	private byte   correct;

	/**
	*得分，有可能是部分正确，例如计算简答等，这里可以设置分数
	*/
	private int   score;

   
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
	 * @param long   问题编号
	 */
	public void setQuestionId(long questionId)
	{
	 	this.questionId=questionId;
	}
	
	/**
	 * @return long  问题编号
	 */
	public long getQuestionId()
	{
	 	return this.questionId;
	}
	/**
	 * @param long   用户ID，可以方便用户查找自己的错题，存储到错题表，这里只作为历史记录
	 */
	public void setUserId(long userId)
	{
	 	this.userId=userId;
	}
	
	/**
	 * @return long  用户ID，可以方便用户查找自己的错题，存储到错题表，这里只作为历史记录
	 */
	public long getUserId()
	{
	 	return this.userId;
	}
	/**
	 * @param long   知识点，方便统计用户的知识点弱点，可以对症下药
	 */
	public void setKnowledge(long knowledge)
	{
	 	this.knowledge=knowledge;
	}
	
	/**
	 * @return long  知识点，方便统计用户的知识点弱点，可以对症下药
	 */
	public long getKnowledge()
	{
	 	return this.knowledge;
	}
	/**
	 * @param String   用户答案
	 */
	public void setAnswer(String answer)
	{
	 	this.answer=answer;
	}
	
	/**
	 * @return String  用户答案
	 */
	public String getAnswer()
	{
	 	return this.answer;
	}
	/**
	 * @param byte   是否正确，只判断最终结构
	 */
	public void setCorrect(byte correct)
	{
	 	this.correct=correct;
	}
	
	/**
	 * @return byte  是否正确，只判断最终结构
	 */
	public byte getCorrect()
	{
	 	return this.correct;
	}
	/**
	 * @param int   得分，有可能是部分正确，例如计算简答等，这里可以设置分数
	 */
	public void setScore(int score)
	{
	 	this.score=score;
	}
	
	/**
	 * @return int  得分，有可能是部分正确，例如计算简答等，这里可以设置分数
	 */
	public int getScore()
	{
	 	return this.score;
	}

   @Override
	public Map<String, Object> toMap() {
	    Map<String, Object> map=new HashMap<String, Object>((int)7);
	    map.put("id",id);
	    map.put("questionId",questionId);
	    map.put("userId",userId);
	    map.put("knowledge",knowledge);
	    map.put("answer",answer);
	    map.put("correct",correct);
	    map.put("score",score);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {};
	}

	
	public String toString(){
	    StringBuilder sb=new StringBuilder("{");
	     sb.append("\"id\":").append(id).append(",");
	     sb.append("\"questionId\":").append(questionId).append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"knowledge\":").append(knowledge).append(",");
	     sb.append("\"answer\":").append("\""+answer+"\"").append(",");
	     sb.append("\"correct\":").append(correct).append(",");
	     sb.append("\"score\":").append(score);
	    sb.append("}");
	    return sb.toString();
	}
}
