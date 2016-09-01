package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_FOLLOW", primary = { "FRIEND_ID","FANS_ID" })
public class Follow implements  Entity{

	/**
	*被关注的用户ID
	*/
	private long   friendId;

	/**
	*粉丝ID，关注时的用户
	*/
	private long   fansId;

	/**
	*关注时间
	*/
	private int   datetime;

	/**
	*用户分组
	*/
	private long   groupId;

	/**
	*备注
	*/
	private String   memo;

   
    public Follow(){}
    
    public Follow(long friendId ,long fansId )
    {
           this.friendId=friendId;
           this.fansId=fansId;
     }
	/**
	 * @param long   被关注的用户ID
	 */
	public void setFriendId(long friendId)
	{
	 	this.friendId=friendId;
	}
	
	/**
	 * @return long  被关注的用户ID
	 */
	public long getFriendId()
	{
	 	return this.friendId;
	}
	/**
	 * @param long   粉丝ID，关注时的用户
	 */
	public void setFansId(long fansId)
	{
	 	this.fansId=fansId;
	}
	
	/**
	 * @return long  粉丝ID，关注时的用户
	 */
	public long getFansId()
	{
	 	return this.fansId;
	}
	/**
	 * @param int   关注时间
	 */
	public void setDatetime(int datetime)
	{
	 	this.datetime=datetime;
	}
	
	/**
	 * @return int  关注时间
	 */
	public int getDatetime()
	{
	 	return this.datetime;
	}
	/**
	 * @param long   用户分组
	 */
	public void setGroupId(long groupId)
	{
	 	this.groupId=groupId;
	}
	
	/**
	 * @return long  用户分组
	 */
	public long getGroupId()
	{
	 	return this.groupId;
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
	    Map<String, Object> map=new HashMap<String, Object>((int)5);
	    map.put("friendId",friendId);
	    map.put("fansId",fansId);
	    map.put("datetime",datetime);
	    map.put("groupId",groupId);
	    map.put("memo",memo);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {friendId,fansId};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          Follow  entity=(Follow)o;
          return friendId==entity.friendId&&fansId==entity.fansId;
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
	     sb.append("\"friendId\":").append(friendId).append(",");
	     sb.append("\"fansId\":").append(fansId).append(",");
	     sb.append("\"datetime\":").append(datetime).append(",");
	     sb.append("\"groupId\":").append(groupId).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
