package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_FRIENDSHIP", primary = {  })
public class Friendship implements  Entity{

	/**
	*邀请用户ID
	*/
	private int   inviteUserid;

	/**
	*接受用户ID
	*/
	private int   acceptUserid;

	/**
	*状态(0：等等同意 1：已是好友，9黑名单)
	*/
	private byte   status;

	/**
	*联系次数
	*/
	private int   times;

	/**
	*最近联系时间
	*/
	private int   lastTime;

	/**
	*友好度
	*/
	private int   friendliness;

	/**
	*分组
	*/
	private String   friendGroup;

   
	/**
	 * @param int   邀请用户ID
	 */
	public void setInviteUserid(int inviteUserid)
	{
	 	this.inviteUserid=inviteUserid;
	}
	
	/**
	 * @return int  邀请用户ID
	 */
	public int getInviteUserid()
	{
	 	return this.inviteUserid;
	}
	/**
	 * @param int   接受用户ID
	 */
	public void setAcceptUserid(int acceptUserid)
	{
	 	this.acceptUserid=acceptUserid;
	}
	
	/**
	 * @return int  接受用户ID
	 */
	public int getAcceptUserid()
	{
	 	return this.acceptUserid;
	}
	/**
	 * @param byte   状态(0：等等同意 1：已是好友，9黑名单)
	 */
	public void setStatus(byte status)
	{
	 	this.status=status;
	}
	
	/**
	 * @return byte  状态(0：等等同意 1：已是好友，9黑名单)
	 */
	public byte getStatus()
	{
	 	return this.status;
	}
	/**
	 * @param int   联系次数
	 */
	public void setTimes(int times)
	{
	 	this.times=times;
	}
	
	/**
	 * @return int  联系次数
	 */
	public int getTimes()
	{
	 	return this.times;
	}
	/**
	 * @param int   最近联系时间
	 */
	public void setLastTime(int lastTime)
	{
	 	this.lastTime=lastTime;
	}
	
	/**
	 * @return int  最近联系时间
	 */
	public int getLastTime()
	{
	 	return this.lastTime;
	}
	/**
	 * @param int   友好度
	 */
	public void setFriendliness(int friendliness)
	{
	 	this.friendliness=friendliness;
	}
	
	/**
	 * @return int  友好度
	 */
	public int getFriendliness()
	{
	 	return this.friendliness;
	}
	/**
	 * @param String   分组
	 */
	public void setFriendGroup(String friendGroup)
	{
	 	this.friendGroup=friendGroup;
	}
	
	/**
	 * @return String  分组
	 */
	public String getFriendGroup()
	{
	 	return this.friendGroup;
	}

   @Override
	public Map<String, Object> toMap() {
	    Map<String, Object> map=new HashMap<String, Object>((int)7);
	    map.put("inviteUserid",inviteUserid);
	    map.put("acceptUserid",acceptUserid);
	    map.put("status",status);
	    map.put("times",times);
	    map.put("lastTime",lastTime);
	    map.put("friendliness",friendliness);
	    map.put("friendGroup",friendGroup);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {};
	}

	
	public String toString(){
	    StringBuilder sb=new StringBuilder("{");
	     sb.append("\"inviteUserid\":").append(inviteUserid).append(",");
	     sb.append("\"acceptUserid\":").append(acceptUserid).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"times\":").append(times).append(",");
	     sb.append("\"lastTime\":").append(lastTime).append(",");
	     sb.append("\"friendliness\":").append(friendliness).append(",");
	     sb.append("\"friendGroup\":").append("\""+friendGroup+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
