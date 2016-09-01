package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_SUBJECT", primary = { "SUBJECT" })
public class Subject implements  Entity{

	/**
	*编号
	*/
	private long   subject;

	/**
	*名称
	*/
	private String   name;

	/**
	*页面关键字
	*/
	private String   keyword;

	/**
	*页面描述
	*/
	private String   descript;

   
    public Subject(){}
    
    public Subject(long subject )
    {
           this.subject=subject;
     }
	/**
	 * @param long   编号
	 */
	public void setSubject(long subject)
	{
	 	this.subject=subject;
	}
	
	/**
	 * @return long  编号
	 */
	public long getSubject()
	{
	 	return this.subject;
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
	 * @param String   页面关键字
	 */
	public void setKeyword(String keyword)
	{
	 	this.keyword=keyword;
	}
	
	/**
	 * @return String  页面关键字
	 */
	public String getKeyword()
	{
	 	return this.keyword;
	}
	/**
	 * @param String   页面描述
	 */
	public void setDescript(String descript)
	{
	 	this.descript=descript;
	}
	
	/**
	 * @return String  页面描述
	 */
	public String getDescript()
	{
	 	return this.descript;
	}

   @Override
	public Map<String, Object> toMap() {
	    Map<String, Object> map=new HashMap<String, Object>((int)4);
	    map.put("subject",this.subject);
	    map.put("name",this.name);
	    map.put("keyword",this.keyword);
	    map.put("descript",this.descript);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {subject};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          Subject  entity=(Subject)o;
          return subject==entity.subject;
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
	     sb.append("\"subject\":").append(subject).append(",");
	     sb.append("\"name\":").append("\""+name+"\"").append(",");
	     sb.append("\"keyword\":").append("\""+keyword+"\"").append(",");
	     sb.append("\"descript\":").append("\""+descript+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
