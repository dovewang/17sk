package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_COLLECT", primary = { "ID" })
public class Collect implements  Entity{

	/**
	*
	*/
	private long   id;

	/**
	*标题
	*/
	private String   title;

	/**
	*收藏对象ID
	*/
	private String   objectId;

	/**
	*收藏对象类型(1、章节视频；2、整个课程，3、问题，4,，收藏课室)
	*/
	private byte   objectType;

	/**
	*收藏人
	*/
	private int   userId;

	/**
	*收藏时间
	*/
	private int   collectTime;

	/**
	*备注
	*/
	private String   memo;

   
    public Collect(){}
    
    public Collect(long id )
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
	 * @param String   标题
	 */
	public void setTitle(String title)
	{
	 	this.title=title;
	}
	
	/**
	 * @return String  标题
	 */
	public String getTitle()
	{
	 	return this.title;
	}
	/**
	 * @param String   收藏对象ID
	 */
	public void setObjectId(String objectId)
	{
	 	this.objectId=objectId;
	}
	
	/**
	 * @return String  收藏对象ID
	 */
	public String getObjectId()
	{
	 	return this.objectId;
	}
	/**
	 * @param byte   收藏对象类型(1、章节视频；2、整个课程，3、问题，4,，收藏课室)
	 */
	public void setObjectType(byte objectType)
	{
	 	this.objectType=objectType;
	}
	
	/**
	 * @return byte  收藏对象类型(1、章节视频；2、整个课程，3、问题，4,，收藏课室)
	 */
	public byte getObjectType()
	{
	 	return this.objectType;
	}
	/**
	 * @param int   收藏人
	 */
	public void setUserId(int userId)
	{
	 	this.userId=userId;
	}
	
	/**
	 * @return int  收藏人
	 */
	public int getUserId()
	{
	 	return this.userId;
	}
	/**
	 * @param int   收藏时间
	 */
	public void setCollectTime(int collectTime)
	{
	 	this.collectTime=collectTime;
	}
	
	/**
	 * @return int  收藏时间
	 */
	public int getCollectTime()
	{
	 	return this.collectTime;
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
	    map.put("title",title);
	    map.put("objectId",objectId);
	    map.put("objectType",objectType);
	    map.put("userId",userId);
	    map.put("collectTime",collectTime);
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
          Collect  entity=(Collect)o;
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
	     sb.append("\"title\":").append("\""+title+"\"").append(",");
	     sb.append("\"objectId\":").append("\""+objectId+"\"").append(",");
	     sb.append("\"objectType\":").append(objectType).append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"collectTime\":").append(collectTime).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
