package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_SCHOOL_ADDRESS", primary = { "ID" })
public class SchoolAddress implements  Entity{

	/**
	*编号
	*/
	private long   id;

	/**
	*主校名
	*/
	private String   main;

	/**
	*分校名
	*/
	private String   branch;

	/**
	*联系人
	*/
	private String   contact;

	/**
	*联系电话
	*/
	private String   phone;

	/**
	*地区代码
	*/
	private int   area;

	/**
	*地址
	*/
	private String   address;

	/**
	*所有人
	*/
	private long   owner;

	/**
	*创建时间
	*/
	private int   dateline;

	/**
	*状态
	*/
	private byte   status;

	/**
	*邮编
	*/
	private String   zipCode;

	/**
	*
	*/
	private String   memo;

   
    public SchoolAddress(){}
    
    public SchoolAddress(long id )
    {
           this.id=id;
     }
	/**
	 * @param long   编号
	 */
	public void setId(long id)
	{
	 	this.id=id;
	}
	
	/**
	 * @return long  编号
	 */
	public long getId()
	{
	 	return this.id;
	}
	/**
	 * @param String   主校名
	 */
	public void setMain(String main)
	{
	 	this.main=main;
	}
	
	/**
	 * @return String  主校名
	 */
	public String getMain()
	{
	 	return this.main;
	}
	/**
	 * @param String   分校名
	 */
	public void setBranch(String branch)
	{
	 	this.branch=branch;
	}
	
	/**
	 * @return String  分校名
	 */
	public String getBranch()
	{
	 	return this.branch;
	}
	/**
	 * @param String   联系人
	 */
	public void setContact(String contact)
	{
	 	this.contact=contact;
	}
	
	/**
	 * @return String  联系人
	 */
	public String getContact()
	{
	 	return this.contact;
	}
	/**
	 * @param String   联系电话
	 */
	public void setPhone(String phone)
	{
	 	this.phone=phone;
	}
	
	/**
	 * @return String  联系电话
	 */
	public String getPhone()
	{
	 	return this.phone;
	}
	/**
	 * @param int   地区代码
	 */
	public void setArea(int area)
	{
	 	this.area=area;
	}
	
	/**
	 * @return int  地区代码
	 */
	public int getArea()
	{
	 	return this.area;
	}
	/**
	 * @param String   地址
	 */
	public void setAddress(String address)
	{
	 	this.address=address;
	}
	
	/**
	 * @return String  地址
	 */
	public String getAddress()
	{
	 	return this.address;
	}
	/**
	 * @param long   所有人
	 */
	public void setOwner(long owner)
	{
	 	this.owner=owner;
	}
	
	/**
	 * @return long  所有人
	 */
	public long getOwner()
	{
	 	return this.owner;
	}
	/**
	 * @param int   创建时间
	 */
	public void setDateline(int dateline)
	{
	 	this.dateline=dateline;
	}
	
	/**
	 * @return int  创建时间
	 */
	public int getDateline()
	{
	 	return this.dateline;
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
	 * @param String   邮编
	 */
	public void setZipCode(String zipCode)
	{
	 	this.zipCode=zipCode;
	}
	
	/**
	 * @return String  邮编
	 */
	public String getZipCode()
	{
	 	return this.zipCode;
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
	    Map<String, Object> map=new HashMap<String, Object>((int)12);
	    map.put("id",this.id);
	    map.put("main",this.main);
	    map.put("branch",this.branch);
	    map.put("contact",this.contact);
	    map.put("phone",this.phone);
	    map.put("area",this.area);
	    map.put("address",this.address);
	    map.put("owner",this.owner);
	    map.put("dateline",this.dateline);
	    map.put("status",this.status);
	    map.put("zipCode",this.zipCode);
	    map.put("memo",this.memo);
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
          SchoolAddress  entity=(SchoolAddress)o;
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
	     sb.append("\"main\":").append("\""+main+"\"").append(",");
	     sb.append("\"branch\":").append("\""+branch+"\"").append(",");
	     sb.append("\"contact\":").append("\""+contact+"\"").append(",");
	     sb.append("\"phone\":").append("\""+phone+"\"").append(",");
	     sb.append("\"area\":").append(area).append(",");
	     sb.append("\"address\":").append("\""+address+"\"").append(",");
	     sb.append("\"owner\":").append(owner).append(",");
	     sb.append("\"dateline\":").append(dateline).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"zipCode\":").append("\""+zipCode+"\"").append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
