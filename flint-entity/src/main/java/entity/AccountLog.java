package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_ACCOUNT_LOG", primary = { "ID" })
public class AccountLog implements  Entity{

	/**
	*
	*/
	private long   id;

	/**
	*账户
	*/
	private long   account;

	/**
	*验证时间
	*/
	private int   checkTime;

	/**
	*验证类型
	*/
	private byte   checkType;

	/**
	*验证结果
	*/
	private byte   status;

	/**
	*登录位置
	*/
	private String   netAddress;

	/**
	*备注
	*/
	private String   memo;

   
    public AccountLog(){}
    
    public AccountLog(long id )
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
	 * @param long   账户
	 */
	public void setAccount(long account)
	{
	 	this.account=account;
	}
	
	/**
	 * @return long  账户
	 */
	public long getAccount()
	{
	 	return this.account;
	}
	/**
	 * @param int   验证时间
	 */
	public void setCheckTime(int checkTime)
	{
	 	this.checkTime=checkTime;
	}
	
	/**
	 * @return int  验证时间
	 */
	public int getCheckTime()
	{
	 	return this.checkTime;
	}
	/**
	 * @param byte   验证类型
	 */
	public void setCheckType(byte checkType)
	{
	 	this.checkType=checkType;
	}
	
	/**
	 * @return byte  验证类型
	 */
	public byte getCheckType()
	{
	 	return this.checkType;
	}
	/**
	 * @param byte   验证结果
	 */
	public void setStatus(byte status)
	{
	 	this.status=status;
	}
	
	/**
	 * @return byte  验证结果
	 */
	public byte getStatus()
	{
	 	return this.status;
	}
	/**
	 * @param String   登录位置
	 */
	public void setNetAddress(String netAddress)
	{
	 	this.netAddress=netAddress;
	}
	
	/**
	 * @return String  登录位置
	 */
	public String getNetAddress()
	{
	 	return this.netAddress;
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
	    Map<String, Object> map=new HashMap<String, Object>((int)7);
	    map.put("id",id);
	    map.put("account",account);
	    map.put("checkTime",checkTime);
	    map.put("checkType",checkType);
	    map.put("status",status);
	    map.put("netAddress",netAddress);
	    map.put("memo",memo);
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
          AccountLog  entity=(AccountLog)o;
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
	     sb.append("\"account\":").append(account).append(",");
	     sb.append("\"checkTime\":").append(checkTime).append(",");
	     sb.append("\"checkType\":").append(checkType).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"netAddress\":").append("\""+netAddress+"\"").append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
