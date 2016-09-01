package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_POLL", primary = { "ID" })
public class Poll implements  Entity{

	/**
	*编号
	*/
	private int   id;

	/**
	*主题
	*/
	private String   subject;

	/**
	*描述
	*/
	private String   descript;

	/**
	*问卷生成路径
	*/
	private String   dir;

	/**
	*创建时间
	*/
	private int   createTime;

	/**
	*创建人
	*/
	private int   creator;

	/**
	*统计开始时间
	*/
	private int   startTime;

	/**
	*统计结束时间
	*/
	private int   endTime;

	/**
	*状态
	*/
	private byte   status;

	/**
	*是否允许发送邮件统计
	*/
	private boolean   mail;

	/**
	*审核时间
	*/
	private int   auditTime;

	/**
	*审核人
	*/
	private String   auditor;

	/**
	*未通过原因
	*/
	private String   reason;

   
    public Poll(){}
    
    public Poll(int id )
    {
           this.id=id;
     }
	/**
	 * @param int   编号
	 */
	public void setId(int id)
	{
	 	this.id=id;
	}
	
	/**
	 * @return int  编号
	 */
	public int getId()
	{
	 	return this.id;
	}
	/**
	 * @param String   主题
	 */
	public void setSubject(String subject)
	{
	 	this.subject=subject;
	}
	
	/**
	 * @return String  主题
	 */
	public String getSubject()
	{
	 	return this.subject;
	}
	/**
	 * @param String   描述
	 */
	public void setDescript(String descript)
	{
	 	this.descript=descript;
	}
	
	/**
	 * @return String  描述
	 */
	public String getDescript()
	{
	 	return this.descript;
	}
	/**
	 * @param String   问卷生成路径
	 */
	public void setDir(String dir)
	{
	 	this.dir=dir;
	}
	
	/**
	 * @return String  问卷生成路径
	 */
	public String getDir()
	{
	 	return this.dir;
	}
	/**
	 * @param int   创建时间
	 */
	public void setCreateTime(int createTime)
	{
	 	this.createTime=createTime;
	}
	
	/**
	 * @return int  创建时间
	 */
	public int getCreateTime()
	{
	 	return this.createTime;
	}
	/**
	 * @param int   创建人
	 */
	public void setCreator(int creator)
	{
	 	this.creator=creator;
	}
	
	/**
	 * @return int  创建人
	 */
	public int getCreator()
	{
	 	return this.creator;
	}
	/**
	 * @param int   统计开始时间
	 */
	public void setStartTime(int startTime)
	{
	 	this.startTime=startTime;
	}
	
	/**
	 * @return int  统计开始时间
	 */
	public int getStartTime()
	{
	 	return this.startTime;
	}
	/**
	 * @param int   统计结束时间
	 */
	public void setEndTime(int endTime)
	{
	 	this.endTime=endTime;
	}
	
	/**
	 * @return int  统计结束时间
	 */
	public int getEndTime()
	{
	 	return this.endTime;
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
	 * @param boolean   是否允许发送邮件统计
	 */
	public void setMail(boolean mail)
	{
	 	this.mail=mail;
	}
	
	/**
	 * @return boolean  是否允许发送邮件统计
	 */
	public boolean isMail()
	{
	 	return this.mail;
	}
	/**
	 * @param int   审核时间
	 */
	public void setAuditTime(int auditTime)
	{
	 	this.auditTime=auditTime;
	}
	
	/**
	 * @return int  审核时间
	 */
	public int getAuditTime()
	{
	 	return this.auditTime;
	}
	/**
	 * @param String   审核人
	 */
	public void setAuditor(String auditor)
	{
	 	this.auditor=auditor;
	}
	
	/**
	 * @return String  审核人
	 */
	public String getAuditor()
	{
	 	return this.auditor;
	}
	/**
	 * @param String   未通过原因
	 */
	public void setReason(String reason)
	{
	 	this.reason=reason;
	}
	
	/**
	 * @return String  未通过原因
	 */
	public String getReason()
	{
	 	return this.reason;
	}

   @Override
	public Map<String, Object> toMap() {
	    Map<String, Object> map=new HashMap<String, Object>((int)13);
	    map.put("id",id);
	    map.put("subject",subject);
	    map.put("descript",descript);
	    map.put("dir",dir);
	    map.put("createTime",createTime);
	    map.put("creator",creator);
	    map.put("startTime",startTime);
	    map.put("endTime",endTime);
	    map.put("status",status);
	    map.put("mail",mail);
	    map.put("auditTime",auditTime);
	    map.put("auditor",auditor);
	    map.put("reason",reason);
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
          Poll  entity=(Poll)o;
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
	     sb.append("\"subject\":").append("\""+subject+"\"").append(",");
	     sb.append("\"descript\":").append("\""+descript+"\"").append(",");
	     sb.append("\"dir\":").append("\""+dir+"\"").append(",");
	     sb.append("\"createTime\":").append(createTime).append(",");
	     sb.append("\"creator\":").append(creator).append(",");
	     sb.append("\"startTime\":").append(startTime).append(",");
	     sb.append("\"endTime\":").append(endTime).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"mail\":").append(mail).append(",");
	     sb.append("\"auditTime\":").append(auditTime).append(",");
	     sb.append("\"auditor\":").append("\""+auditor+"\"").append(",");
	     sb.append("\"reason\":").append("\""+reason+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
