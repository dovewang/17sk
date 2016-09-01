package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_MESSAGE", primary = { "MESSAGE_ID" })
public class Message implements  Entity{

	/**
	*消息id
	*/
	private long   messageId;

	/**
	*学校编号
	*/
	private long   schoolId;

	/**
	*消息主题
	*/
	private String   subject;

	/**
	*消息类型
	*/
	private byte   type;

	/**
	*消息内容
	*/
	private String   message;

	/**
	*发送人
	*/
	private long   sender;

	/**
	*接受人
	*/
	private long   receiver;

	/**
	*发送消息状态，0未发送（草稿箱），1发件箱，2已删除
	*/
	private byte   sendStatus;

	/**
	*接收消息状态，0未读，1，已读，2已删除
	*/
	private byte   receiveStatus;

	/**
	*发送时间
	*/
	private int   sendTime;

	/**
	*阅读时间
	*/
	private int   readTime;

	/**
	*备注
	*/
	private String   memo;

   
    public Message(){}
    
    public Message(long messageId )
    {
           this.messageId=messageId;
     }
	/**
	 * @param long   消息id
	 */
	public void setMessageId(long messageId)
	{
	 	this.messageId=messageId;
	}
	
	/**
	 * @return long  消息id
	 */
	public long getMessageId()
	{
	 	return this.messageId;
	}
	/**
	 * @param long   学校编号
	 */
	public void setSchoolId(long schoolId)
	{
	 	this.schoolId=schoolId;
	}
	
	/**
	 * @return long  学校编号
	 */
	public long getSchoolId()
	{
	 	return this.schoolId;
	}
	/**
	 * @param String   消息主题
	 */
	public void setSubject(String subject)
	{
	 	this.subject=subject;
	}
	
	/**
	 * @return String  消息主题
	 */
	public String getSubject()
	{
	 	return this.subject;
	}
	/**
	 * @param byte   消息类型
	 */
	public void setType(byte type)
	{
	 	this.type=type;
	}
	
	/**
	 * @return byte  消息类型
	 */
	public byte getType()
	{
	 	return this.type;
	}
	/**
	 * @param String   消息内容
	 */
	public void setMessage(String message)
	{
	 	this.message=message;
	}
	
	/**
	 * @return String  消息内容
	 */
	public String getMessage()
	{
	 	return this.message;
	}
	/**
	 * @param long   发送人
	 */
	public void setSender(long sender)
	{
	 	this.sender=sender;
	}
	
	/**
	 * @return long  发送人
	 */
	public long getSender()
	{
	 	return this.sender;
	}
	/**
	 * @param long   接受人
	 */
	public void setReceiver(long receiver)
	{
	 	this.receiver=receiver;
	}
	
	/**
	 * @return long  接受人
	 */
	public long getReceiver()
	{
	 	return this.receiver;
	}
	/**
	 * @param byte   发送消息状态，0未发送（草稿箱），1发件箱，2已删除
	 */
	public void setSendStatus(byte sendStatus)
	{
	 	this.sendStatus=sendStatus;
	}
	
	/**
	 * @return byte  发送消息状态，0未发送（草稿箱），1发件箱，2已删除
	 */
	public byte getSendStatus()
	{
	 	return this.sendStatus;
	}
	/**
	 * @param byte   接收消息状态，0未读，1，已读，2已删除
	 */
	public void setReceiveStatus(byte receiveStatus)
	{
	 	this.receiveStatus=receiveStatus;
	}
	
	/**
	 * @return byte  接收消息状态，0未读，1，已读，2已删除
	 */
	public byte getReceiveStatus()
	{
	 	return this.receiveStatus;
	}
	/**
	 * @param int   发送时间
	 */
	public void setSendTime(int sendTime)
	{
	 	this.sendTime=sendTime;
	}
	
	/**
	 * @return int  发送时间
	 */
	public int getSendTime()
	{
	 	return this.sendTime;
	}
	/**
	 * @param int   阅读时间
	 */
	public void setReadTime(int readTime)
	{
	 	this.readTime=readTime;
	}
	
	/**
	 * @return int  阅读时间
	 */
	public int getReadTime()
	{
	 	return this.readTime;
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
	    Map<String, Object> map=new HashMap<String, Object>((int)12);
	    map.put("messageId",messageId);
	    map.put("schoolId",schoolId);
	    map.put("subject",subject);
	    map.put("type",type);
	    map.put("message",message);
	    map.put("sender",sender);
	    map.put("receiver",receiver);
	    map.put("sendStatus",sendStatus);
	    map.put("receiveStatus",receiveStatus);
	    map.put("sendTime",sendTime);
	    map.put("readTime",readTime);
	    map.put("memo",memo);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {messageId};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          Message  entity=(Message)o;
          return messageId==entity.messageId;
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
	     sb.append("\"messageId\":").append(messageId).append(",");
	     sb.append("\"schoolId\":").append(schoolId).append(",");
	     sb.append("\"subject\":").append("\""+subject+"\"").append(",");
	     sb.append("\"type\":").append(type).append(",");
	     sb.append("\"message\":").append("\""+message+"\"").append(",");
	     sb.append("\"sender\":").append(sender).append(",");
	     sb.append("\"receiver\":").append(receiver).append(",");
	     sb.append("\"sendStatus\":").append(sendStatus).append(",");
	     sb.append("\"receiveStatus\":").append(receiveStatus).append(",");
	     sb.append("\"sendTime\":").append(sendTime).append(",");
	     sb.append("\"readTime\":").append(readTime).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
