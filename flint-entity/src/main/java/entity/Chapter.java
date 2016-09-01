package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_CHAPTER", primary = { "CHAPTER_ID" })
public class Chapter implements  Entity{

	/**
	*
	*/
	private long   chapterId;

	/**
	*
	*/
	private long   lessonId;

	/**
	*
	*/
	private String   title;

	/**
	*上课内容
	*/
	private String   content;

	/**
	*上课时间
	*/
	private int   classtime;

	/**
	*作息索引，多节课连上使用逗号分隔
	*/
	private String   classindex;

	/**
	*创建时间
	*/
	private int   dateline;

	/**
	*最后更新时间
	*/
	private int   updateTime;

	/**
	*状态
	*/
	private byte   status;

	/**
	*
	*/
	private int   displayNumber;

	/**
	*
	*/
	private String   memo;

   
    public Chapter(){}
    
    public Chapter(long chapterId )
    {
           this.chapterId=chapterId;
     }
	/**
	 * @param long   
	 */
	public void setChapterId(long chapterId)
	{
	 	this.chapterId=chapterId;
	}
	
	/**
	 * @return long  
	 */
	public long getChapterId()
	{
	 	return this.chapterId;
	}
	/**
	 * @param long   
	 */
	public void setLessonId(long lessonId)
	{
	 	this.lessonId=lessonId;
	}
	
	/**
	 * @return long  
	 */
	public long getLessonId()
	{
	 	return this.lessonId;
	}
	/**
	 * @param String   
	 */
	public void setTitle(String title)
	{
	 	this.title=title;
	}
	
	/**
	 * @return String  
	 */
	public String getTitle()
	{
	 	return this.title;
	}
	/**
	 * @param String   上课内容
	 */
	public void setContent(String content)
	{
	 	this.content=content;
	}
	
	/**
	 * @return String  上课内容
	 */
	public String getContent()
	{
	 	return this.content;
	}
	/**
	 * @param int   上课时间
	 */
	public void setClasstime(int classtime)
	{
	 	this.classtime=classtime;
	}
	
	/**
	 * @return int  上课时间
	 */
	public int getClasstime()
	{
	 	return this.classtime;
	}
	/**
	 * @param String   作息索引，多节课连上使用逗号分隔
	 */
	public void setClassindex(String classindex)
	{
	 	this.classindex=classindex;
	}
	
	/**
	 * @return String  作息索引，多节课连上使用逗号分隔
	 */
	public String getClassindex()
	{
	 	return this.classindex;
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
	 * @param int   最后更新时间
	 */
	public void setUpdateTime(int updateTime)
	{
	 	this.updateTime=updateTime;
	}
	
	/**
	 * @return int  最后更新时间
	 */
	public int getUpdateTime()
	{
	 	return this.updateTime;
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
	 * @param int   
	 */
	public void setDisplayNumber(int displayNumber)
	{
	 	this.displayNumber=displayNumber;
	}
	
	/**
	 * @return int  
	 */
	public int getDisplayNumber()
	{
	 	return this.displayNumber;
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
	    Map<String, Object> map=new HashMap<String, Object>((int)11);
	    map.put("chapterId",this.chapterId);
	    map.put("lessonId",this.lessonId);
	    map.put("title",this.title);
	    map.put("content",this.content);
	    map.put("classtime",this.classtime);
	    map.put("classindex",this.classindex);
	    map.put("dateline",this.dateline);
	    map.put("updateTime",this.updateTime);
	    map.put("status",this.status);
	    map.put("displayNumber",this.displayNumber);
	    map.put("memo",this.memo);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {chapterId};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          Chapter  entity=(Chapter)o;
          return chapterId==entity.chapterId;
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
	     sb.append("\"chapterId\":").append(chapterId).append(",");
	     sb.append("\"lessonId\":").append(lessonId).append(",");
	     sb.append("\"title\":").append("\""+title+"\"").append(",");
	     sb.append("\"content\":").append("\""+content+"\"").append(",");
	     sb.append("\"classtime\":").append(classtime).append(",");
	     sb.append("\"classindex\":").append("\""+classindex+"\"").append(",");
	     sb.append("\"dateline\":").append(dateline).append(",");
	     sb.append("\"updateTime\":").append(updateTime).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"displayNumber\":").append(displayNumber).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
