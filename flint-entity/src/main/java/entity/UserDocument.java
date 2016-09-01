package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_USER_DOCUMENT", primary = { "ID" })
public class UserDocument implements  Entity{

	/**
	*主键
	*/
	private long   id;

	/**
	*文档编号
	*/
	private long   docId;

	/**
	*用户编号
	*/
	private long   userId;

	/**
	*文档名称，用户可以自由修改不影响原文档名称
	*/
	private String   name;

	/**
	*文档来源：0 所有者，1、购买，2、上课分发，3、赠送
	*/
	private byte   come;

	/**
	*当前目录
	*/
	private String   dir;

	/**
	*父目录，默认“我的文档”为顶级目录，不可修改
	*/
	private String   parentDir;

	/**
	*类型，0，自由文档，1，课程绑定文档，不允许删除
	*/
	private byte   type;

	/**
	*创建时间
	*/
	private int   createTime;

	/**
	*扩展属性1（课程编号）
	*/
	private String   attr1;

	/**
	*扩展属性2（章节编号）
	*/
	private String   attr2;

	/**
	*扩展属性3
	*/
	private String   attr3;

	/**
	*用户备注
	*/
	private String   memo;

   
    public UserDocument(){}
    
    public UserDocument(long id )
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
	 * @param long   文档编号
	 */
	public void setDocId(long docId)
	{
	 	this.docId=docId;
	}
	
	/**
	 * @return long  文档编号
	 */
	public long getDocId()
	{
	 	return this.docId;
	}
	/**
	 * @param long   用户编号
	 */
	public void setUserId(long userId)
	{
	 	this.userId=userId;
	}
	
	/**
	 * @return long  用户编号
	 */
	public long getUserId()
	{
	 	return this.userId;
	}
	/**
	 * @param String   文档名称，用户可以自由修改不影响原文档名称
	 */
	public void setName(String name)
	{
	 	this.name=name;
	}
	
	/**
	 * @return String  文档名称，用户可以自由修改不影响原文档名称
	 */
	public String getName()
	{
	 	return this.name;
	}
	/**
	 * @param byte   文档来源：0 所有者，1、购买，2、上课分发，3、赠送
	 */
	public void setCome(byte come)
	{
	 	this.come=come;
	}
	
	/**
	 * @return byte  文档来源：0 所有者，1、购买，2、上课分发，3、赠送
	 */
	public byte getCome()
	{
	 	return this.come;
	}
	/**
	 * @param String   当前目录
	 */
	public void setDir(String dir)
	{
	 	this.dir=dir;
	}
	
	/**
	 * @return String  当前目录
	 */
	public String getDir()
	{
	 	return this.dir;
	}
	/**
	 * @param String   父目录，默认“我的文档”为顶级目录，不可修改
	 */
	public void setParentDir(String parentDir)
	{
	 	this.parentDir=parentDir;
	}
	
	/**
	 * @return String  父目录，默认“我的文档”为顶级目录，不可修改
	 */
	public String getParentDir()
	{
	 	return this.parentDir;
	}
	/**
	 * @param byte   类型，0，自由文档，1，课程绑定文档，不允许删除
	 */
	public void setType(byte type)
	{
	 	this.type=type;
	}
	
	/**
	 * @return byte  类型，0，自由文档，1，课程绑定文档，不允许删除
	 */
	public byte getType()
	{
	 	return this.type;
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
	 * @param String   扩展属性1（课程编号）
	 */
	public void setAttr1(String attr1)
	{
	 	this.attr1=attr1;
	}
	
	/**
	 * @return String  扩展属性1（课程编号）
	 */
	public String getAttr1()
	{
	 	return this.attr1;
	}
	/**
	 * @param String   扩展属性2（章节编号）
	 */
	public void setAttr2(String attr2)
	{
	 	this.attr2=attr2;
	}
	
	/**
	 * @return String  扩展属性2（章节编号）
	 */
	public String getAttr2()
	{
	 	return this.attr2;
	}
	/**
	 * @param String   扩展属性3
	 */
	public void setAttr3(String attr3)
	{
	 	this.attr3=attr3;
	}
	
	/**
	 * @return String  扩展属性3
	 */
	public String getAttr3()
	{
	 	return this.attr3;
	}
	/**
	 * @param String   用户备注
	 */
	public void setMemo(String memo)
	{
	 	this.memo=memo;
	}
	
	/**
	 * @return String  用户备注
	 */
	public String getMemo()
	{
	 	return this.memo;
	}

   @Override
	public Map<String, Object> toMap() {
	    Map<String, Object> map=new HashMap<String, Object>((int)13);
	    map.put("id",id);
	    map.put("docId",docId);
	    map.put("userId",userId);
	    map.put("name",name);
	    map.put("come",come);
	    map.put("dir",dir);
	    map.put("parentDir",parentDir);
	    map.put("type",type);
	    map.put("createTime",createTime);
	    map.put("attr1",attr1);
	    map.put("attr2",attr2);
	    map.put("attr3",attr3);
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
          UserDocument  entity=(UserDocument)o;
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
	     sb.append("\"docId\":").append(docId).append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"name\":").append("\""+name+"\"").append(",");
	     sb.append("\"come\":").append(come).append(",");
	     sb.append("\"dir\":").append("\""+dir+"\"").append(",");
	     sb.append("\"parentDir\":").append("\""+parentDir+"\"").append(",");
	     sb.append("\"type\":").append(type).append(",");
	     sb.append("\"createTime\":").append(createTime).append(",");
	     sb.append("\"attr1\":").append("\""+attr1+"\"").append(",");
	     sb.append("\"attr2\":").append("\""+attr2+"\"").append(",");
	     sb.append("\"attr3\":").append("\""+attr3+"\"").append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
