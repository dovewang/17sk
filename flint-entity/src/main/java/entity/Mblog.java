package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_MBLOG", primary = { "ID" })
public class Mblog implements  Entity{

	/**
	*微博ID
	*/
	private long   id;

	/**
	*微博内容
	*/
	private String   content;

	/**
	*发布时间
	*/
	private int   dateline;

	/**
	*发布人ID
	*/
	private long   userId;

	/**
	*微博类型：0、原创，1、发布的问题，2、转发，3评论，4、话题
	*/
	private byte   type;

	/**
	*开放状态
	*/
	private byte   open;

	/**
	*图片地址，存储为json格式，可能为视频等，提问，课程发布消息等
	*/
	private String   media;

	/**
	*来源：web,wap,mobile,qq,msn,api,sina,qqwb,vote
	*/
	private String   come;

	/**
	*评论数目
	*/
	private int   comments;

	/**
	*转发数目
	*/
	private int   forwards;

	/**
	*赞
	*/
	private int   ups;

	/**
	*默认为0，代表本站，即公共的
	*/
	private long   schoolId;

	/**
	*圈子编号，特定发布在某个圈子内的，默认为0，代表无分组
	*/
	private long   groupId;

	/**
	*是否置顶，粉丝头条专用
	*/
	private boolean   top;

	/**
	*到期时间
	*/
	private int   topDeadline;

	/**
	*用户自定义标签，只能对自己发
	*/
	private String   tag;

   
    public Mblog(){}
    
    public Mblog(long id )
    {
           this.id=id;
     }
	/**
	 * @param long   微博ID
	 */
	public void setId(long id)
	{
	 	this.id=id;
	}
	
	/**
	 * @return long  微博ID
	 */
	public long getId()
	{
	 	return this.id;
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
	public void setDateline(int dateline)
	{
	 	this.dateline=dateline;
	}
	
	/**
	 * @return int  发布时间
	 */
	public int getDateline()
	{
	 	return this.dateline;
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
	 * @param byte   微博类型：0、原创，1、发布的问题，2、转发，3评论，4、话题
	 */
	public void setType(byte type)
	{
	 	this.type=type;
	}
	
	/**
	 * @return byte  微博类型：0、原创，1、发布的问题，2、转发，3评论，4、话题
	 */
	public byte getType()
	{
	 	return this.type;
	}
	/**
	 * @param byte   开放状态
	 */
	public void setOpen(byte open)
	{
	 	this.open=open;
	}
	
	/**
	 * @return byte  开放状态
	 */
	public byte getOpen()
	{
	 	return this.open;
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
	 * @param String   来源：web,wap,mobile,qq,msn,api,sina,qqwb,vote
	 */
	public void setCome(String come)
	{
	 	this.come=come;
	}
	
	/**
	 * @return String  来源：web,wap,mobile,qq,msn,api,sina,qqwb,vote
	 */
	public String getCome()
	{
	 	return this.come;
	}
	/**
	 * @param int   评论数目
	 */
	public void setComments(int comments)
	{
	 	this.comments=comments;
	}
	
	/**
	 * @return int  评论数目
	 */
	public int getComments()
	{
	 	return this.comments;
	}
	/**
	 * @param int   转发数目
	 */
	public void setForwards(int forwards)
	{
	 	this.forwards=forwards;
	}
	
	/**
	 * @return int  转发数目
	 */
	public int getForwards()
	{
	 	return this.forwards;
	}
	/**
	 * @param int   赞
	 */
	public void setUps(int ups)
	{
	 	this.ups=ups;
	}
	
	/**
	 * @return int  赞
	 */
	public int getUps()
	{
	 	return this.ups;
	}
	/**
	 * @param long   默认为0，代表本站，即公共的
	 */
	public void setSchoolId(long schoolId)
	{
	 	this.schoolId=schoolId;
	}
	
	/**
	 * @return long  默认为0，代表本站，即公共的
	 */
	public long getSchoolId()
	{
	 	return this.schoolId;
	}
	/**
	 * @param long   圈子编号，特定发布在某个圈子内的，默认为0，代表无分组
	 */
	public void setGroupId(long groupId)
	{
	 	this.groupId=groupId;
	}
	
	/**
	 * @return long  圈子编号，特定发布在某个圈子内的，默认为0，代表无分组
	 */
	public long getGroupId()
	{
	 	return this.groupId;
	}
	/**
	 * @param boolean   是否置顶，粉丝头条专用
	 */
	public void setTop(boolean top)
	{
	 	this.top=top;
	}
	
	/**
	 * @return boolean  是否置顶，粉丝头条专用
	 */
	public boolean isTop()
	{
	 	return this.top;
	}
	/**
	 * @param int   到期时间
	 */
	public void setTopDeadline(int topDeadline)
	{
	 	this.topDeadline=topDeadline;
	}
	
	/**
	 * @return int  到期时间
	 */
	public int getTopDeadline()
	{
	 	return this.topDeadline;
	}
	/**
	 * @param String   用户自定义标签，只能对自己发
	 */
	public void setTag(String tag)
	{
	 	this.tag=tag;
	}
	
	/**
	 * @return String  用户自定义标签，只能对自己发
	 */
	public String getTag()
	{
	 	return this.tag;
	}

   @Override
	public Map<String, Object> toMap() {
	    Map<String, Object> map=new HashMap<String, Object>((int)16);
	    map.put("id",this.id);
	    map.put("content",this.content);
	    map.put("dateline",this.dateline);
	    map.put("userId",this.userId);
	    map.put("type",this.type);
	    map.put("open",this.open);
	    map.put("media",this.media);
	    map.put("come",this.come);
	    map.put("comments",this.comments);
	    map.put("forwards",this.forwards);
	    map.put("ups",this.ups);
	    map.put("schoolId",this.schoolId);
	    map.put("groupId",this.groupId);
	    map.put("top",this.top);
	    map.put("topDeadline",this.topDeadline);
	    map.put("tag",this.tag);
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
          Mblog  entity=(Mblog)o;
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
	     sb.append("\"content\":").append("\""+content+"\"").append(",");
	     sb.append("\"dateline\":").append(dateline).append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"type\":").append(type).append(",");
	     sb.append("\"open\":").append(open).append(",");
	     sb.append("\"media\":").append("\""+media+"\"").append(",");
	     sb.append("\"come\":").append("\""+come+"\"").append(",");
	     sb.append("\"comments\":").append(comments).append(",");
	     sb.append("\"forwards\":").append(forwards).append(",");
	     sb.append("\"ups\":").append(ups).append(",");
	     sb.append("\"schoolId\":").append(schoolId).append(",");
	     sb.append("\"groupId\":").append(groupId).append(",");
	     sb.append("\"top\":").append(top).append(",");
	     sb.append("\"topDeadline\":").append(topDeadline).append(",");
	     sb.append("\"tag\":").append("\""+tag+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
