package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_TAG", primary = { "ID" })
public class Tag implements  Entity{

	/**
	*
	*/
	private long   id;

	/**
	*
	*/
	private String   name;

	/**
	*
	*/
	private int   dateline;

	/**
	*
	*/
	private byte   tagGroup;

	/**
	*
	*/
	private long   counts;

   
    public Tag(){}
    
    public Tag(long id )
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
	 * @param String   
	 */
	public void setName(String name)
	{
	 	this.name=name;
	}
	
	/**
	 * @return String  
	 */
	public String getName()
	{
	 	return this.name;
	}
	/**
	 * @param int   
	 */
	public void setDateline(int dateline)
	{
	 	this.dateline=dateline;
	}
	
	/**
	 * @return int  
	 */
	public int getDateline()
	{
	 	return this.dateline;
	}
	/**
	 * @param byte   
	 */
	public void setTagGroup(byte tagGroup)
	{
	 	this.tagGroup=tagGroup;
	}
	
	/**
	 * @return byte  
	 */
	public byte getTagGroup()
	{
	 	return this.tagGroup;
	}
	/**
	 * @param long   
	 */
	public void setCounts(long counts)
	{
	 	this.counts=counts;
	}
	
	/**
	 * @return long  
	 */
	public long getCounts()
	{
	 	return this.counts;
	}

   @Override
	public Map<String, Object> toMap() {
	    Map<String, Object> map=new HashMap<String, Object>((int)5);
	    map.put("id",this.id);
	    map.put("name",this.name);
	    map.put("dateline",this.dateline);
	    map.put("tagGroup",this.tagGroup);
	    map.put("counts",this.counts);
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
          Tag  entity=(Tag)o;
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
	     sb.append("\"dateline\":").append(dateline).append(",");
	     sb.append("\"tagGroup\":").append(tagGroup).append(",");
	     sb.append("\"counts\":").append(counts);
	    sb.append("}");
	    return sb.toString();
	}
}
