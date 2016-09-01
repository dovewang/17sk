package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_CHAPTER_RESOURCE", primary = { "ID" })
public class ChapterResource implements  Entity{

	/**
	*
	*/
	private long   id;

	/**
	*章节编号
	*/
	private long   chapterId;

	/**
	*课程编号
	*/
	private long   lessonId;

	/**
	*类型，视频，音频，图片，文档，文本
	*/
	private byte   type;

	/**
	*文档编号
	*/
	private long   docId;

	/**
	*外部资源url
	*/
	private String   url;

	/**
	*标题
	*/
	private String   title;

	/**
	*文本内容
	*/
	private String   text;

	/**
	*创建时间
	*/
	private int   dateline;

	/**
	*更新时间
	*/
	private int   updateTime;

	/**
	*作者
	*/
	private long   userId;

	/**
	*备注
	*/
	private String   memo;

   
    public ChapterResource(){}
    
    public ChapterResource(long id )
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
	 * @param long   章节编号
	 */
	public void setChapterId(long chapterId)
	{
	 	this.chapterId=chapterId;
	}
	
	/**
	 * @return long  章节编号
	 */
	public long getChapterId()
	{
	 	return this.chapterId;
	}
	/**
	 * @param long   课程编号
	 */
	public void setLessonId(long lessonId)
	{
	 	this.lessonId=lessonId;
	}
	
	/**
	 * @return long  课程编号
	 */
	public long getLessonId()
	{
	 	return this.lessonId;
	}
	/**
	 * @param byte   类型，视频，音频，图片，文档，文本
	 */
	public void setType(byte type)
	{
	 	this.type=type;
	}
	
	/**
	 * @return byte  类型，视频，音频，图片，文档，文本
	 */
	public byte getType()
	{
	 	return this.type;
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
	 * @param String   外部资源url
	 */
	public void setUrl(String url)
	{
	 	this.url=url;
	}
	
	/**
	 * @return String  外部资源url
	 */
	public String getUrl()
	{
	 	return this.url;
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
	 * @param String   文本内容
	 */
	public void setText(String text)
	{
	 	this.text=text;
	}
	
	/**
	 * @return String  文本内容
	 */
	public String getText()
	{
	 	return this.text;
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
	 * @param int   更新时间
	 */
	public void setUpdateTime(int updateTime)
	{
	 	this.updateTime=updateTime;
	}
	
	/**
	 * @return int  更新时间
	 */
	public int getUpdateTime()
	{
	 	return this.updateTime;
	}
	/**
	 * @param long   作者
	 */
	public void setUserId(long userId)
	{
	 	this.userId=userId;
	}
	
	/**
	 * @return long  作者
	 */
	public long getUserId()
	{
	 	return this.userId;
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
	    Map<String, Object> map=new HashMap<String, Object>((int)12);
	    map.put("id",this.id);
	    map.put("chapterId",this.chapterId);
	    map.put("lessonId",this.lessonId);
	    map.put("type",this.type);
	    map.put("docId",this.docId);
	    map.put("url",this.url);
	    map.put("title",this.title);
	    map.put("text",this.text);
	    map.put("dateline",this.dateline);
	    map.put("updateTime",this.updateTime);
	    map.put("userId",this.userId);
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
          ChapterResource  entity=(ChapterResource)o;
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
	     sb.append("\"chapterId\":").append(chapterId).append(",");
	     sb.append("\"lessonId\":").append(lessonId).append(",");
	     sb.append("\"type\":").append(type).append(",");
	     sb.append("\"docId\":").append(docId).append(",");
	     sb.append("\"url\":").append("\""+url+"\"").append(",");
	     sb.append("\"title\":").append("\""+title+"\"").append(",");
	     sb.append("\"text\":").append("\""+text+"\"").append(",");
	     sb.append("\"dateline\":").append(dateline).append(",");
	     sb.append("\"updateTime\":").append(updateTime).append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
