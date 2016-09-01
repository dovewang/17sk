package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_USER_EXTENDS", primary = { "USER_ID" })
public class UserExtends implements  Entity{

	/**
	*用户ID，邮箱
	*/
	private long   userId;

	/**
	*自我介绍
	*/
	private String   intro;

	/**
	*个人爱好
	*/
	private String   favor;

	/**
	*QQ
	*/
	private String   qq;

	/**
	*MSN
	*/
	private String   msn;

	/**
	*个人主页
	*/
	private String   home;

	/**
	*博客
	*/
	private String   blog;

	/**
	*生日
	*/
	private int   birthday;

	/**
	*国家
	*/
	private int   nation;

	/**
	*省/州/旗
	*/
	private int   province;

	/**
	*城市
	*/
	private int   city;

	/**
	*区/镇
	*/
	private int   town;

	/**
	*路
	*/
	private String   road;

	/**
	*楼栋
	*/
	private String   ban;

	/**
	*详细地址
	*/
	private String   address;

	/**
	*
	*/
	private String   attr1;

	/**
	*
	*/
	private String   attr2;

	/**
	*
	*/
	private String   attr3;

   
    public UserExtends(){}
    
    public UserExtends(long userId )
    {
           this.userId=userId;
     }
	/**
	 * @param long   用户ID，邮箱
	 */
	public void setUserId(long userId)
	{
	 	this.userId=userId;
	}
	
	/**
	 * @return long  用户ID，邮箱
	 */
	public long getUserId()
	{
	 	return this.userId;
	}
	/**
	 * @param String   自我介绍
	 */
	public void setIntro(String intro)
	{
	 	this.intro=intro;
	}
	
	/**
	 * @return String  自我介绍
	 */
	public String getIntro()
	{
	 	return this.intro;
	}
	/**
	 * @param String   个人爱好
	 */
	public void setFavor(String favor)
	{
	 	this.favor=favor;
	}
	
	/**
	 * @return String  个人爱好
	 */
	public String getFavor()
	{
	 	return this.favor;
	}
	/**
	 * @param String   QQ
	 */
	public void setQq(String qq)
	{
	 	this.qq=qq;
	}
	
	/**
	 * @return String  QQ
	 */
	public String getQq()
	{
	 	return this.qq;
	}
	/**
	 * @param String   MSN
	 */
	public void setMsn(String msn)
	{
	 	this.msn=msn;
	}
	
	/**
	 * @return String  MSN
	 */
	public String getMsn()
	{
	 	return this.msn;
	}
	/**
	 * @param String   个人主页
	 */
	public void setHome(String home)
	{
	 	this.home=home;
	}
	
	/**
	 * @return String  个人主页
	 */
	public String getHome()
	{
	 	return this.home;
	}
	/**
	 * @param String   博客
	 */
	public void setBlog(String blog)
	{
	 	this.blog=blog;
	}
	
	/**
	 * @return String  博客
	 */
	public String getBlog()
	{
	 	return this.blog;
	}
	/**
	 * @param int   生日
	 */
	public void setBirthday(int birthday)
	{
	 	this.birthday=birthday;
	}
	
	/**
	 * @return int  生日
	 */
	public int getBirthday()
	{
	 	return this.birthday;
	}
	/**
	 * @param int   国家
	 */
	public void setNation(int nation)
	{
	 	this.nation=nation;
	}
	
	/**
	 * @return int  国家
	 */
	public int getNation()
	{
	 	return this.nation;
	}
	/**
	 * @param int   省/州/旗
	 */
	public void setProvince(int province)
	{
	 	this.province=province;
	}
	
	/**
	 * @return int  省/州/旗
	 */
	public int getProvince()
	{
	 	return this.province;
	}
	/**
	 * @param int   城市
	 */
	public void setCity(int city)
	{
	 	this.city=city;
	}
	
	/**
	 * @return int  城市
	 */
	public int getCity()
	{
	 	return this.city;
	}
	/**
	 * @param int   区/镇
	 */
	public void setTown(int town)
	{
	 	this.town=town;
	}
	
	/**
	 * @return int  区/镇
	 */
	public int getTown()
	{
	 	return this.town;
	}
	/**
	 * @param String   路
	 */
	public void setRoad(String road)
	{
	 	this.road=road;
	}
	
	/**
	 * @return String  路
	 */
	public String getRoad()
	{
	 	return this.road;
	}
	/**
	 * @param String   楼栋
	 */
	public void setBan(String ban)
	{
	 	this.ban=ban;
	}
	
	/**
	 * @return String  楼栋
	 */
	public String getBan()
	{
	 	return this.ban;
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
	/**
	 * @param String   
	 */
	public void setAttr1(String attr1)
	{
	 	this.attr1=attr1;
	}
	
	/**
	 * @return String  
	 */
	public String getAttr1()
	{
	 	return this.attr1;
	}
	/**
	 * @param String   
	 */
	public void setAttr2(String attr2)
	{
	 	this.attr2=attr2;
	}
	
	/**
	 * @return String  
	 */
	public String getAttr2()
	{
	 	return this.attr2;
	}
	/**
	 * @param String   
	 */
	public void setAttr3(String attr3)
	{
	 	this.attr3=attr3;
	}
	
	/**
	 * @return String  
	 */
	public String getAttr3()
	{
	 	return this.attr3;
	}

   @Override
	public Map<String, Object> toMap() {
	    Map<String, Object> map=new HashMap<String, Object>((int)18);
	    map.put("userId",userId);
	    map.put("intro",intro);
	    map.put("favor",favor);
	    map.put("qq",qq);
	    map.put("msn",msn);
	    map.put("home",home);
	    map.put("blog",blog);
	    map.put("birthday",birthday);
	    map.put("nation",nation);
	    map.put("province",province);
	    map.put("city",city);
	    map.put("town",town);
	    map.put("road",road);
	    map.put("ban",ban);
	    map.put("address",address);
	    map.put("attr1",attr1);
	    map.put("attr2",attr2);
	    map.put("attr3",attr3);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {userId};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          UserExtends  entity=(UserExtends)o;
          return userId==entity.userId;
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
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"intro\":").append("\""+intro+"\"").append(",");
	     sb.append("\"favor\":").append("\""+favor+"\"").append(",");
	     sb.append("\"qq\":").append("\""+qq+"\"").append(",");
	     sb.append("\"msn\":").append("\""+msn+"\"").append(",");
	     sb.append("\"home\":").append("\""+home+"\"").append(",");
	     sb.append("\"blog\":").append("\""+blog+"\"").append(",");
	     sb.append("\"birthday\":").append(birthday).append(",");
	     sb.append("\"nation\":").append(nation).append(",");
	     sb.append("\"province\":").append(province).append(",");
	     sb.append("\"city\":").append(city).append(",");
	     sb.append("\"town\":").append(town).append(",");
	     sb.append("\"road\":").append("\""+road+"\"").append(",");
	     sb.append("\"ban\":").append("\""+ban+"\"").append(",");
	     sb.append("\"address\":").append("\""+address+"\"").append(",");
	     sb.append("\"attr1\":").append("\""+attr1+"\"").append(",");
	     sb.append("\"attr2\":").append("\""+attr2+"\"").append(",");
	     sb.append("\"attr3\":").append("\""+attr3+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
