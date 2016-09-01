package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_MBLOG_COMMENT", primary = { "ID" })
public class MblogComment implements  Entity{

	/**
	*评论ID
	*/
	private long   id;

	/**
	*原始微博ID
	*/
	private long   mid;

	/**
	*微博内容
	*/
	private String   content;

	/**
	*发布时间
	*/
	private int   createTime;

	/**
	*发布人ID
	*/
	private long   userId;

	/**
	*图片地址，存储为json格式，可能为视频等，提问，课程发布消息等
	*/
	private String   media;

	/**
	*来源：web,wap,mobile,qq,msn,api,sina,qqwb,vote
	*/
	private byte   come;

   
    public MblogComment(){}
    
    public MblogComment(long id )
    {
           this.id=id;
     }
	/**
	 * @param long   评论ID
	 */
	public void setId(long id)
	{
	 	this.id=id;
	}
	
	/**
	 * @return long  评论ID
	 */
	public long getId()
	{
	 	return this.id;
	}
	/**
	 * @param long   原始微博ID
	 */
	public void setMid(long mid)
	{
	 	this.mid=mid;
	}
	
	/**
	 * @return long  原始微博ID
	 */
	public long getMid()
	{
	 	return this.mid;
	}
	/**
	 * @param String   微博内容
	 */
	public void setContent(String content)
	{
	 	this.content=content;
	}
	
	/**
	 * @return String  微博内容
	 */
	public String getContent()
	{
	 	return this.content;
	}
	/**
	 * @param int   发布时间
	 */
	public void setCreateTime(int createTime)
	{
	 	this.createTime=createTime;
	}
	
	/**
	 * @return int  发布时间
	 */
	public int getCreateTime()
	{
	 	return this.createTime;
	}
	/**
	 * @param long   发布人ID
	 */
	public void setUserId(long userId)
	{
	 	this.userId=userId;
	}
	
	/**
	 * @return long  发布人ID
	 */
	public long getUserId()
	{
	 	return this.userId;
	}
	/**
	 * @param String   图片地址，存储为json格式，可能为视频等，提问，课程发布消息等
	 */
	public void setMedia(String media)
	{
	 	this.media=media;
	}
	
	/**
	 * @return String  图片地址，存储为json格式，可能为视频等，提问，课程发布消息等
	 */
	public String getMedia()
	{
	 	return this.media;
	}
	/**
	 * @param byte   来源：web,wap,mobile,qq,msn,api,sina,qqwb,vote
	 */
	public void setCome(byte come)
	{
	 	this.come=come;
	}
	
	/**
	 * @return byte  来源：web,wap,mobile,qq,msn,api,sina,qqwb,vote
	 */
	public byte getCome()
	{
	 	return this.come;
	}

   @Override
	public Map<String, Object> toMap() {
	    Map<String, Object> map=new HashMap<String, Object>((int)7);
	    map.put("id",id);
	    map.put("mid",mid);
	    map.put("content",content);
	    map.put("createTime",createTime);
	    map.put("userId",userId);
	    map.put("media",media);
	    map.put("come",come);
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
          MblogComment  entity=(MblogComment)o;
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
	     sb.append("\"mid\":").append(mid).append(",");
	     sb.append("\"content\":").append("\""+content+"\"").append(",");
	     sb.append("\"createTime\":").append(createTime).append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"media\":").append("\""+media+"\"").append(",");
	     sb.append("\"come\":").append(come);
	    sb.append("}");
	    return sb.toString();
	}
}
