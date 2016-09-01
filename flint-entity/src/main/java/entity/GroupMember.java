package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_GROUP_MEMBER", primary = { "GROUP_ID","USER_ID" })
public class GroupMember implements  Entity{

	/**
	*组织编号
	*/
	private long   groupId;

	/**
	*用户编号
	*/
	private long   userId;

	/**
	*状态
	*/
	private byte   status;

	/**
	*用户角色,，默认member，普通成员
	*/
	private byte   role;

	/**
	*255位权限实现方式
	*/
	private String   authority;

	/**
	*加入时间
	*/
	private int   joinTime;

	/**
	*退出时间
	*/
	private int   leaveTime;

	/**
	*备注
	*/
	private String   memo;

   
    public GroupMember(){}
    
    public GroupMember(long groupId ,long userId )
    {
           this.groupId=groupId;
           this.userId=userId;
     }
	/**
	 * @param long   组织编号
	 */
	public void setGroupId(long groupId)
	{
	 	this.groupId=groupId;
	}
	
	/**
	 * @return long  组织编号
	 */
	public long getGroupId()
	{
	 	return this.groupId;
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
	 * @param byte   用户角色,，默认member，普通成员
	 */
	public void setRole(byte role)
	{
	 	this.role=role;
	}
	
	/**
	 * @return byte  用户角色,，默认member，普通成员
	 */
	public byte getRole()
	{
	 	return this.role;
	}
	/**
	 * @param String   255位权限实现方式
	 */
	public void setAuthority(String authority)
	{
	 	this.authority=authority;
	}
	
	/**
	 * @return String  255位权限实现方式
	 */
	public String getAuthority()
	{
	 	return this.authority;
	}
	/**
	 * @param int   加入时间
	 */
	public void setJoinTime(int joinTime)
	{
	 	this.joinTime=joinTime;
	}
	
	/**
	 * @return int  加入时间
	 */
	public int getJoinTime()
	{
	 	return this.joinTime;
	}
	/**
	 * @param int   退出时间
	 */
	public void setLeaveTime(int leaveTime)
	{
	 	this.leaveTime=leaveTime;
	}
	
	/**
	 * @return int  退出时间
	 */
	public int getLeaveTime()
	{
	 	return this.leaveTime;
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
	    Map<String, Object> map=new HashMap<String, Object>((int)8);
	    map.put("groupId",this.groupId);
	    map.put("userId",this.userId);
	    map.put("status",this.status);
	    map.put("role",this.role);
	    map.put("authority",this.authority);
	    map.put("joinTime",this.joinTime);
	    map.put("leaveTime",this.leaveTime);
	    map.put("memo",this.memo);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {groupId,userId};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          GroupMember  entity=(GroupMember)o;
          return groupId==entity.groupId&&userId==entity.userId;
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
	     sb.append("\"groupId\":").append(groupId).append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"role\":").append(role).append(",");
	     sb.append("\"authority\":").append("\""+authority+"\"").append(",");
	     sb.append("\"joinTime\":").append(joinTime).append(",");
	     sb.append("\"leaveTime\":").append(leaveTime).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
