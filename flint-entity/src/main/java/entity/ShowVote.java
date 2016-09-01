package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_SHOW_VOTE", primary = { "ID" })
public class ShowVote implements  Entity{

	/**
	*
	*/
	private long   id;

	/**
	*
	*/
	private long   showId;

	/**
	*
	*/
	private long   userId;

	/**
	*
	*/
	private int   dateline;

	/**
	*
	*/
	private String   netAddress;

	/**
	*
	*/
	private int   score;

	/**
	*
	*/
	private boolean   support;

   
    public ShowVote(){}
    
    public ShowVote(long id )
    {
           this.id=id;
     }
	/**
	 * @param long   
	 */
	public void setId(long id)
	{
	 	this.id=id;
	}
	
	/**
	 * @return long  
	 */
	public long getId()
	{
	 	return this.id;
	}
	/**
	 * @param long   
	 */
	public void setShowId(long showId)
	{
	 	this.showId=showId;
	}
	
	/**
	 * @return long  
	 */
	public long getShowId()
	{
	 	return this.showId;
	}
	/**
	 * @param long   
	 */
	public void setUserId(long userId)
	{
	 	this.userId=userId;
	}
	
	/**
	 * @return long  
	 */
	public long getUserId()
	{
	 	return this.userId;
	}
	/**
	 * @param int   
	 */
	public void setDateline(int dateline)
	{
	 	this.dateline=dateline;
	}
	
	/**
	 * @return int  
	 */
	public int getDateline()
	{
	 	return this.dateline;
	}
	/**
	 * @param String   
	 */
	public void setNetAddress(String netAddress)
	{
	 	this.netAddress=netAddress;
	}
	
	/**
	 * @return String  
	 */
	public String getNetAddress()
	{
	 	return this.netAddress;
	}
	/**
	 * @param int   
	 */
	public void setScore(int score)
	{
	 	this.score=score;
	}
	
	/**
	 * @return int  
	 */
	public int getScore()
	{
	 	return this.score;
	}
	/**
	 * @param boolean   
	 */
	public void setSupport(boolean support)
	{
	 	this.support=support;
	}
	
	/**
	 * @return boolean  
	 */
	public boolean isSupport()
	{
	 	return this.support;
	}

   @Override
	public Map<String, Object> toMap() {
	    Map<String, Object> map=new HashMap<String, Object>((int)7);
	    map.put("id",id);
	    map.put("showId",showId);
	    map.put("userId",userId);
	    map.put("dateline",dateline);
	    map.put("netAddress",netAddress);
	    map.put("score",score);
	    map.put("support",support);
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
          ShowVote  entity=(ShowVote)o;
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
	     sb.append("\"showId\":").append(showId).append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"dateline\":").append(dateline).append(",");
	     sb.append("\"netAddress\":").append("\""+netAddress+"\"").append(",");
	     sb.append("\"score\":").append(score).append(",");
	     sb.append("\"support\":").append(support);
	    sb.append("}");
	    return sb.toString();
	}
}
