package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_KNOWLEDGE", primary = { "KNOWLEDGE_ID" })
public class Knowledge implements  Entity{

	/**
	*知识点
	*/
	private long   knowledgeId;

	/**
	*知识点名
	*/
	private String   knowledge;

	/**
	*知识点描述
	*/
	private String   intro;

	/**
	*父知识点
	*/
	private long   parentKnowledgeId;

	/**
	*所属年级
	*/
	private long   kind;

	/**
	*课程分类
	*/
	private int   grade;

	/**
	*
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

   
    public Knowledge(){}
    
    public Knowledge(long knowledgeId )
    {
           this.knowledgeId=knowledgeId;
     }
	/**
	 * @param long   知识点
	 */
	public void setKnowledgeId(long knowledgeId)
	{
	 	this.knowledgeId=knowledgeId;
	}
	
	/**
	 * @return long  知识点
	 */
	public long getKnowledgeId()
	{
	 	return this.knowledgeId;
	}
	/**
	 * @param String   知识点名
	 */
	public void setKnowledge(String knowledge)
	{
	 	this.knowledge=knowledge;
	}
	
	/**
	 * @return String  知识点名
	 */
	public String getKnowledge()
	{
	 	return this.knowledge;
	}
	/**
	 * @param String   知识点描述
	 */
	public void setIntro(String intro)
	{
	 	this.intro=intro;
	}
	
	/**
	 * @return String  知识点描述
	 */
	public String getIntro()
	{
	 	return this.intro;
	}
	/**
	 * @param long   父知识点
	 */
	public void setParentKnowledgeId(long parentKnowledgeId)
	{
	 	this.parentKnowledgeId=parentKnowledgeId;
	}
	
	/**
	 * @return long  父知识点
	 */
	public long getParentKnowledgeId()
	{
	 	return this.parentKnowledgeId;
	}
	/**
	 * @param long   所属年级
	 */
	public void setKind(long kind)
	{
	 	this.kind=kind;
	}
	
	/**
	 * @return long  所属年级
	 */
	public long getKind()
	{
	 	return this.kind;
	}
	/**
	 * @param int   课程分类
	 */
	public void setGrade(int grade)
	{
	 	this.grade=grade;
	}
	
	/**
	 * @return int  课程分类
	 */
	public int getGrade()
	{
	 	return this.grade;
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
	    Map<String, Object> map=new HashMap<String, Object>((int)12);
	    map.put("knowledgeId",this.knowledgeId);
	    map.put("knowledge",this.knowledge);
	    map.put("intro",this.intro);
	    map.put("parentKnowledgeId",this.parentKnowledgeId);
	    map.put("kind",this.kind);
	    map.put("grade",this.grade);
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
		return new Object[] {knowledgeId};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          Knowledge  entity=(Knowledge)o;
          return knowledgeId==entity.knowledgeId;
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
	     sb.append("\"knowledgeId\":").append(knowledgeId).append(",");
	     sb.append("\"knowledge\":").append("\""+knowledge+"\"").append(",");
	     sb.append("\"intro\":").append("\""+intro+"\"").append(",");
	     sb.append("\"parentKnowledgeId\":").append(parentKnowledgeId).append(",");
	     sb.append("\"kind\":").append(kind).append(",");
	     sb.append("\"grade\":").append(grade).append(",");
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
