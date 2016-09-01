package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_TEMPLATE", primary = { "ID" })
public class Template implements  Entity{

	/**
	*模板编号
	*/
	private int   id;

	/**
	*模板名称
	*/
	private String   name;

	/**
	*模板路径
	*/
	private String   dir;

	/**
	*模板类型
	*/
	private byte   type;

	/**
	*模板简介
	*/
	private String   intro;

	/**
	*创建日期
	*/
	private int   createTime;

	/**
	*创建人
	*/
	private int   user;

	/**
	*备注
	*/
	private String   memo;

   
    public Template(){}
    
    public Template(int id )
    {
           this.id=id;
     }
	/**
	 * @param int   模板编号
	 */
	public void setId(int id)
	{
	 	this.id=id;
	}
	
	/**
	 * @return int  模板编号
	 */
	public int getId()
	{
	 	return this.id;
	}
	/**
	 * @param String   模板名称
	 */
	public void setName(String name)
	{
	 	this.name=name;
	}
	
	/**
	 * @return String  模板名称
	 */
	public String getName()
	{
	 	return this.name;
	}
	/**
	 * @param String   模板路径
	 */
	public void setDir(String dir)
	{
	 	this.dir=dir;
	}
	
	/**
	 * @return String  模板路径
	 */
	public String getDir()
	{
	 	return this.dir;
	}
	/**
	 * @param byte   模板类型
	 */
	public void setType(byte type)
	{
	 	this.type=type;
	}
	
	/**
	 * @return byte  模板类型
	 */
	public byte getType()
	{
	 	return this.type;
	}
	/**
	 * @param String   模板简介
	 */
	public void setIntro(String intro)
	{
	 	this.intro=intro;
	}
	
	/**
	 * @return String  模板简介
	 */
	public String getIntro()
	{
	 	return this.intro;
	}
	/**
	 * @param int   创建日期
	 */
	public void setCreateTime(int createTime)
	{
	 	this.createTime=createTime;
	}
	
	/**
	 * @return int  创建日期
	 */
	public int getCreateTime()
	{
	 	return this.createTime;
	}
	/**
	 * @param int   创建人
	 */
	public void setUser(int user)
	{
	 	this.user=user;
	}
	
	/**
	 * @return int  创建人
	 */
	public int getUser()
	{
	 	return this.user;
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
	    map.put("id",id);
	    map.put("name",name);
	    map.put("dir",dir);
	    map.put("type",type);
	    map.put("intro",intro);
	    map.put("createTime",createTime);
	    map.put("user",user);
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
          Template  entity=(Template)o;
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
	     sb.append("\"name\":").append("\""+name+"\"").append(",");
	     sb.append("\"dir\":").append("\""+dir+"\"").append(",");
	     sb.append("\"type\":").append(type).append(",");
	     sb.append("\"intro\":").append("\""+intro+"\"").append(",");
	     sb.append("\"createTime\":").append(createTime).append(",");
	     sb.append("\"user\":").append(user).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
