package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_POLL_OPTION", primary = { "ID" })
public class PollOption implements  Entity{

	/**
	*主键
	*/
	private int   id;

	/**
	*问题主键
	*/
	private int   topicId;

	/**
	*选项
	*/
	private String   option;

	/**
	*选项值
	*/
	private int   value;

	/**
	*用户选择数（选择题）
	*/
	private int   votes;

	/**
	*用户答案（填空题）
	*/
	private String   answer;

   
    public PollOption(){}
    
    public PollOption(int id )
    {
           this.id=id;
     }
	/**
	 * @param int   主键
	 */
	public void setId(int id)
	{
	 	this.id=id;
	}
	
	/**
	 * @return int  主键
	 */
	public int getId()
	{
	 	return this.id;
	}
	/**
	 * @param int   问题主键
	 */
	public void setTopicId(int topicId)
	{
	 	this.topicId=topicId;
	}
	
	/**
	 * @return int  问题主键
	 */
	public int getTopicId()
	{
	 	return this.topicId;
	}
	/**
	 * @param String   选项
	 */
	public void setOption(String option)
	{
	 	this.option=option;
	}
	
	/**
	 * @return String  选项
	 */
	public String getOption()
	{
	 	return this.option;
	}
	/**
	 * @param int   选项值
	 */
	public void setValue(int value)
	{
	 	this.value=value;
	}
	
	/**
	 * @return int  选项值
	 */
	public int getValue()
	{
	 	return this.value;
	}
	/**
	 * @param int   用户选择数（选择题）
	 */
	public void setVotes(int votes)
	{
	 	this.votes=votes;
	}
	
	/**
	 * @return int  用户选择数（选择题）
	 */
	public int getVotes()
	{
	 	return this.votes;
	}
	/**
	 * @param String   用户答案（填空题）
	 */
	public void setAnswer(String answer)
	{
	 	this.answer=answer;
	}
	
	/**
	 * @return String  用户答案（填空题）
	 */
	public String getAnswer()
	{
	 	return this.answer;
	}

   @Override
	public Map<String, Object> toMap() {
	    Map<String, Object> map=new HashMap<String, Object>((int)6);
	    map.put("id",id);
	    map.put("topicId",topicId);
	    map.put("option",option);
	    map.put("value",value);
	    map.put("votes",votes);
	    map.put("answer",answer);
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
          PollOption  entity=(PollOption)o;
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
	     sb.append("\"topicId\":").append(topicId).append(",");
	     sb.append("\"option\":").append("\""+option+"\"").append(",");
	     sb.append("\"value\":").append(value).append(",");
	     sb.append("\"votes\":").append(votes).append(",");
	     sb.append("\"answer\":").append("\""+answer+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
