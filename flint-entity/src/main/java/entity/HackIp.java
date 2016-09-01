package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_HACK_IP", primary = {  })
public class HackIp implements  Entity{

	/**
	*IP地址
	*/
	private String   ip;

	/**
	*开始时间
	*/
	private int   start;

	/**
	*尝试次数
	*/
	private byte   trys;

	/**
	*状态
	*/
	private boolean   status;

	/**
	*客户端信息
	*/
	private String   clientInfo;

   
	/**
	 * @param String   IP地址
	 */
	public void setIp(String ip)
	{
	 	this.ip=ip;
	}
	
	/**
	 * @return String  IP地址
	 */
	public String getIp()
	{
	 	return this.ip;
	}
	/**
	 * @param int   开始时间
	 */
	public void setStart(int start)
	{
	 	this.start=start;
	}
	
	/**
	 * @return int  开始时间
	 */
	public int getStart()
	{
	 	return this.start;
	}
	/**
	 * @param byte   尝试次数
	 */
	public void setTrys(byte trys)
	{
	 	this.trys=trys;
	}
	
	/**
	 * @return byte  尝试次数
	 */
	public byte getTrys()
	{
	 	return this.trys;
	}
	/**
	 * @param boolean   状态
	 */
	public void setStatus(boolean status)
	{
	 	this.status=status;
	}
	
	/**
	 * @return boolean  状态
	 */
	public boolean isStatus()
	{
	 	return this.status;
	}
	/**
	 * @param String   客户端信息
	 */
	public void setClientInfo(String clientInfo)
	{
	 	this.clientInfo=clientInfo;
	}
	
	/**
	 * @return String  客户端信息
	 */
	public String getClientInfo()
	{
	 	return this.clientInfo;
	}

   @Override
	public Map<String, Object> toMap() {
	    Map<String, Object> map=new HashMap<String, Object>((int)5);
	    map.put("ip",ip);
	    map.put("start",start);
	    map.put("trys",trys);
	    map.put("status",status);
	    map.put("clientInfo",clientInfo);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {};
	}

	
	public String toString(){
	    StringBuilder sb=new StringBuilder("{");
	     sb.append("\"ip\":").append("\""+ip+"\"").append(",");
	     sb.append("\"start\":").append(start).append(",");
	     sb.append("\"trys\":").append(trys).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"clientInfo\":").append("\""+clientInfo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
