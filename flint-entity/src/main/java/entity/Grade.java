package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_GRADE", primary = { "GRADE" })
public class Grade implements  Entity{

	/**
	*年级，目前是小学一年级到高三，也可以到更多
	*/
	private int   grade;

	/**
	*名称
	*/
	private String   name;

	/**
	*年级工具设置
	*/
	private String   tools;

	/**
	*备注
	*/
	private String   memo;

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

	/**
	*
	*/
	private String   attr4;

	/**
	*
	*/
	private String   attr5;

   
    public Grade(){}
    
    public Grade(int grade )
    {
           this.grade=grade;
     }
	/**
	 * @param int   年级，目前是小学一年级到高三，也可以到更多
	 */
	public void setGrade(int grade)
	{
	 	this.grade=grade;
	}
	
	/**
	 * @return int  年级，目前是小学一年级到高三，也可以到更多
	 */
	public int getGrade()
	{
	 	return this.grade;
	}
	/**
	 * @param String   名称
	 */
	public void setName(String name)
	{
	 	this.name=name;
	}
	
	/**
	 * @return String  名称
	 */
	public String getName()
	{
	 	return this.name;
	}
	/**
	 * @param String   年级工具设置
	 */
	public void setTools(String tools)
	{
	 	this.tools=tools;
	}
	
	/**
	 * @return String  年级工具设置
	 */
	public String getTools()
	{
	 	return this.tools;
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
	/**
	 * @param String   
	 */
	public void setAttr4(String attr4)
	{
	 	this.attr4=attr4;
	}
	
	/**
	 * @return String  
	 */
	public String getAttr4()
	{
	 	return this.attr4;
	}
	/**
	 * @param String   
	 */
	public void setAttr5(String attr5)
	{
	 	this.attr5=attr5;
	}
	
	/**
	 * @return String  
	 */
	public String getAttr5()
	{
	 	return this.attr5;
	}

   @Override
	public Map<String, Object> toMap() {
	    Map<String, Object> map=new HashMap<String, Object>((int)9);
	    map.put("grade",this.grade);
	    map.put("name",this.name);
	    map.put("tools",this.tools);
	    map.put("memo",this.memo);
	    map.put("attr1",this.attr1);
	    map.put("attr2",this.attr2);
	    map.put("attr3",this.attr3);
	    map.put("attr4",this.attr4);
	    map.put("attr5",this.attr5);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {grade};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          Grade  entity=(Grade)o;
          return grade==entity.grade;
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
	     sb.append("\"grade\":").append(grade).append(",");
	     sb.append("\"name\":").append("\""+name+"\"").append(",");
	     sb.append("\"tools\":").append("\""+tools+"\"").append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"").append(",");
	     sb.append("\"attr1\":").append("\""+attr1+"\"").append(",");
	     sb.append("\"attr2\":").append("\""+attr2+"\"").append(",");
	     sb.append("\"attr3\":").append("\""+attr3+"\"").append(",");
	     sb.append("\"attr4\":").append("\""+attr4+"\"").append(",");
	     sb.append("\"attr5\":").append("\""+attr5+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
