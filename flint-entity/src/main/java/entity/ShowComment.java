package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_SHOW_COMMENT", primary = { "COMMENT_ID" })
public class ShowComment implements  Entity{

	/**
	*
	*/
	private long   commentId;

	/**
	*
	*/
	private long   showId;

	/**
	*
	*/
	private String   content;

	/**
	*
	*/
	private int   dateline;

	/**
	*
	*/
	private byte   status;

	/**
	*
	*/
	private long   userId;

	/**
	*
	*/
	private String   netAddress;

	/**
	*默认为0，不为0时代表回复其他人的
	*/
	private long   parentId;

	/**
	*
	*/
	private String   memo;

   
    public ShowComment(){}
    
    public ShowComment(long commentId )
    {
           this.commentId=commentId;
     }
	/**
	 * @param long   
	 */
	public void setCommentId(long commentId)
	{
	 	this.commentId=commentId;
	}
	
	/**
	 * @return long  
	 */
	public long getCommentId()
	{
	 	return this.commentId;
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
	 * @param String   
	 */
	public void setContent(String content)
	{
	 	this.content=content;
	}
	
	/**
	 * @return String  
	 */
	public String getContent()
	{
	 	return this.content;
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
	 * @param byte   
	 */
	public void setStatus(byte status)
	{
	 	this.status=status;
	}
	
	/**
	 * @return byte  
	 */
	public byte getStatus()
	{
	 	return this.status;
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
	 * @param long   默认为0，不为0时代表回复其他人的
	 */
	public void setParentId(long parentId)
	{
	 	this.parentId=parentId;
	}
	
	/**
	 * @return long  默认为0，不为0时代表回复其他人的
	 */
	public long getParentId()
	{
	 	return this.parentId;
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
	    Map<String, Object> map=new HashMap<String, Object>((int)9);
	    map.put("commentId",commentId);
	    map.put("showId",showId);
	    map.put("content",content);
	    map.put("dateline",dateline);
	    map.put("status",status);
	    map.put("userId",userId);
	    map.put("netAddress",netAddress);
	    map.put("parentId",parentId);
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
          ShowComment  entity=(ShowComment)o;
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
	     sb.append("\"showId\":").append(showId).append(",");
	     sb.append("\"content\":").append("\""+content+"\"").append(",");
	     sb.append("\"dateline\":").append(dateline).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"netAddress\":").append("\""+netAddress+"\"").append(",");
	     sb.append("\"parentId\":").append(parentId).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
