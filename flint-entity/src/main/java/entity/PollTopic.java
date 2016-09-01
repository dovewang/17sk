package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_POLL_TOPIC", primary = { "ID" })
public class PollTopic implements  Entity{

	/**
	*主键
	*/
	private int   id;

	/**
	*调查主题编号
	*/
	private int   investigateId;

	/**
	*题号
	*/
	private int   no;

	/**
	*问题
	*/
	private String   question;

	/**
	*问题类型：1、单选 2、多选、3、填空
	*/
	private byte   type;

   
    public PollTopic(){}
    
    public PollTopic(int id )
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
	 * @param int   调查主题编号
	 */
	public void setInvestigateId(int investigateId)
	{
	 	this.investigateId=investigateId;
	}
	
	/**
	 * @return int  调查主题编号
	 */
	public int getInvestigateId()
	{
	 	return this.investigateId;
	}
	/**
	 * @param int   题号
	 */
	public void setNo(int no)
	{
	 	this.no=no;
	}
	
	/**
	 * @return int  题号
	 */
	public int getNo()
	{
	 	return this.no;
	}
	/**
	 * @param String   问题
	 */
	public void setQuestion(String question)
	{
	 	this.question=question;
	}
	
	/**
	 * @return String  问题
	 */
	public String getQuestion()
	{
	 	return this.question;
	}
	/**
	 * @param byte   问题类型：1、单选 2、多选、3、填空
	 */
	public void setType(byte type)
	{
	 	this.type=type;
	}
	
	/**
	 * @return byte  问题类型：1、单选 2、多选、3、填空
	 */
	public byte getType()
	{
	 	return this.type;
	}

   @Override
	public Map<String, Object> toMap() {
	    Map<String, Object> map=new HashMap<String, Object>((int)5);
	    map.put("id",id);
	    map.put("investigateId",investigateId);
	    map.put("no",no);
	    map.put("question",question);
	    map.put("type",type);
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
          PollTopic  entity=(PollTopic)o;
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
	     sb.append("\"investigateId\":").append(investigateId).append(",");
	     sb.append("\"no\":").append(no).append(",");
	     sb.append("\"question\":").append("\""+question+"\"").append(",");
	     sb.append("\"type\":").append(type);
	    sb.append("}");
	    return sb.toString();
	}
}
