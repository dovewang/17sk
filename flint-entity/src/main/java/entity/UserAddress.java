package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_USER_ADDRESS", primary = { "ID" })
public class UserAddress implements  Entity{

	/**
	*主键
	*/
	private long   id;

	/**
	*所有者
	*/
	private long   userId;

	/**
	*用户名
	*/
	private String   name;

	/**
	*手机
	*/
	private String   phone;

	/**
	*邮箱
	*/
	private String   email;

	/**
	*区域
	*/
	private int   area;

	/**
	*详细地址
	*/
	private String   address;

	private byte status;
	/**
	*备注
	*/
	private String   memo;

   
    public UserAddress(){}
    
    public UserAddress(long id )
    {
           this.id=id;
     }
	/**
	 * @param long   主键
	 */
	public void setId(long id)
	{
	 	this.id=id;
	}
	
	/**
	 * @return long  主键
	 */
	public long getId()
	{
	 	return this.id;
	}
	/**
	 * @param long   所有者
	 */
	public void setUserId(long userId)
	{
	 	this.userId=userId;
	}
	
	/**
	 * @return long  所有者
	 */
	public long getUserId()
	{
	 	return this.userId;
	}
	/**
	 * @param String   用户名
	 */
	public void setName(String name)
	{
	 	this.name=name;
	}
	
	/**
	 * @return String  用户名
	 */
	public String getName()
	{
	 	return this.name;
	}
	/**
	 * @param String   手机
	 */
	public void setPhone(String phone)
	{
	 	this.phone=phone;
	}
	
	/**
	 * @return String  手机
	 */
	public String getPhone()
	{
	 	return this.phone;
	}
	/**
	 * @param String   邮箱
	 */
	public void setEmail(String email)
	{
	 	this.email=email;
	}
	
	/**
	 * @return String  邮箱
	 */
	public String getEmail()
	{
	 	return this.email;
	}
	/**
	 * @param int   区域
	 */
	public void setArea(int area)
	{
	 	this.area=area;
	}
	
	/**
	 * @return int  区域
	 */
	public int getArea()
	{
	 	return this.area;
	}
	/**
	 * @param String   详细地址
	 */
	public void setAddress(String address)
	{
	 	this.address=address;
	}
	
	/**
	 * @return String  详细地址
	 */
	public String getAddress()
	{
	 	return this.address;
	}

	
	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
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
	    map.put("id",this.id);
	    map.put("userId",this.userId);
	    map.put("name",this.name);
	    map.put("phone",this.phone);
	    map.put("email",this.email);
	    map.put("area",this.area);
	    map.put("address",this.address);
	    map.put("status",this.status);
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
          UserAddress  entity=(UserAddress)o;
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
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"name\":").append("\""+name+"\"").append(",");
	     sb.append("\"phone\":").append("\""+phone+"\"").append(",");
	     sb.append("\"email\":").append("\""+email+"\"").append(",");
	     sb.append("\"area\":").append(area).append(",");
	     sb.append("\"address\":").append("\""+address+"\"").append(",");
	     sb.append("\"status\":").append("\""+status+"\"").append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
