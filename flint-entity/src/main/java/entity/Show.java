package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_SHOW", primary = { "SHOW_ID" })
public class Show implements  Entity{

	/**
	*秀秀编号
	*/
	private long   showId;

	/**
	*主题标号，为0代表无主题秀
	*/
	private long   activeId;

	/**
	*发布人编号
	*/
	private long   userId;

	/**
	*标题
	*/
	private String   title;

	/**
	*学习心得，应试技巧，家长专题，课程教学，专题辅导，其他
	*/
	private int   category;

	/**
	*年级
	*/
	private long   grade;

	/**
	*科目
	*/
	private long   subject;

	/**
	*标签
	*/
	private String   tag;

	/**
	*封面
	*/
	private String   cover;

	/**
	*简单描述
	*/
	private String   intro;

	/**
	*秀秀类型：1在线秀秀，2视频秀秀
	*/
	private byte   type;

	/**
	*秀秀状态
	*/
	private byte   status;

	/**
	*发布时间
	*/
	private int   dateline;

	/**
	*视频地址
	*/
	private String   dir;

	/**
	*在线秀房间编号
	*/
	private long   roomId;

	/**
	*观看数
	*/
	private int   views;

	/**
	*支持数，人气
	*/
	private int   ups;

	/**
	*踩的人数
	*/
	private int   downs;

	/**
	*评论数
	*/
	private int   comments;

	/**
	*收藏数
	*/
	private int   collects;

	/**
	*得分
	*/
	private int   scores;

	/**
	*是否入围
	*/
	private boolean   finalist;

	/**
	*获得奖励
	*/
	private boolean   rewarded;

	/**
	*备注
	*/
	private String   memo;

   
    public Show(){}
    
    public Show(long showId )
    {
           this.showId=showId;
     }
	/**
	 * @param long   秀秀编号
	 */
	public void setShowId(long showId)
	{
	 	this.showId=showId;
	}
	
	/**
	 * @return long  秀秀编号
	 */
	public long getShowId()
	{
	 	return this.showId;
	}
	/**
	 * @param long   主题标号，为0代表无主题秀
	 */
	public void setActiveId(long activeId)
	{
	 	this.activeId=activeId;
	}
	
	/**
	 * @return long  主题标号，为0代表无主题秀
	 */
	public long getActiveId()
	{
	 	return this.activeId;
	}
	/**
	 * @param long   发布人编号
	 */
	public void setUserId(long userId)
	{
	 	this.userId=userId;
	}
	
	/**
	 * @return long  发布人编号
	 */
	public long getUserId()
	{
	 	return this.userId;
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
	 * @param int   学习心得，应试技巧，家长专题，课程教学，专题辅导，其他
	 */
	public void setCategory(int category)
	{
	 	this.category=category;
	}
	
	/**
	 * @return int  学习心得，应试技巧，家长专题，课程教学，专题辅导，其他
	 */
	public int getCategory()
	{
	 	return this.category;
	}
	/**
	 * @param long   年级
	 */
	public void setGrade(long grade)
	{
	 	this.grade=grade;
	}
	
	/**
	 * @return long  年级
	 */
	public long getGrade()
	{
	 	return this.grade;
	}
	/**
	 * @param long   科目
	 */
	public void setSubject(long subject)
	{
	 	this.subject=subject;
	}
	
	/**
	 * @return long  科目
	 */
	public long getSubject()
	{
	 	return this.subject;
	}
	/**
	 * @param String   标签
	 */
	public void setTag(String tag)
	{
	 	this.tag=tag;
	}
	
	/**
	 * @return String  标签
	 */
	public String getTag()
	{
	 	return this.tag;
	}
	/**
	 * @param String   封面
	 */
	public void setCover(String cover)
	{
	 	this.cover=cover;
	}
	
	/**
	 * @return String  封面
	 */
	public String getCover()
	{
	 	return this.cover;
	}
	/**
	 * @param String   简单描述
	 */
	public void setIntro(String intro)
	{
	 	this.intro=intro;
	}
	
	/**
	 * @return String  简单描述
	 */
	public String getIntro()
	{
	 	return this.intro;
	}
	/**
	 * @param byte   秀秀类型：1在线秀秀，2视频秀秀
	 */
	public void setType(byte type)
	{
	 	this.type=type;
	}
	
	/**
	 * @return byte  秀秀类型：1在线秀秀，2视频秀秀
	 */
	public byte getType()
	{
	 	return this.type;
	}
	/**
	 * @param byte   秀秀状态
	 */
	public void setStatus(byte status)
	{
	 	this.status=status;
	}
	
	/**
	 * @return byte  秀秀状态
	 */
	public byte getStatus()
	{
	 	return this.status;
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
	 * @param String   视频地址
	 */
	public void setDir(String dir)
	{
	 	this.dir=dir;
	}
	
	/**
	 * @return String  视频地址
	 */
	public String getDir()
	{
	 	return this.dir;
	}
	/**
	 * @param long   在线秀房间编号
	 */
	public void setRoomId(long roomId)
	{
	 	this.roomId=roomId;
	}
	
	/**
	 * @return long  在线秀房间编号
	 */
	public long getRoomId()
	{
	 	return this.roomId;
	}
	/**
	 * @param int   观看数
	 */
	public void setViews(int views)
	{
	 	this.views=views;
	}
	
	/**
	 * @return int  观看数
	 */
	public int getViews()
	{
	 	return this.views;
	}
	/**
	 * @param int   支持数，人气
	 */
	public void setUps(int ups)
	{
	 	this.ups=ups;
	}
	
	/**
	 * @return int  支持数，人气
	 */
	public int getUps()
	{
	 	return this.ups;
	}
	/**
	 * @param int   踩的人数
	 */
	public void setDowns(int downs)
	{
	 	this.downs=downs;
	}
	
	/**
	 * @return int  踩的人数
	 */
	public int getDowns()
	{
	 	return this.downs;
	}
	/**
	 * @param int   评论数
	 */
	public void setComments(int comments)
	{
	 	this.comments=comments;
	}
	
	/**
	 * @return int  评论数
	 */
	public int getComments()
	{
	 	return this.comments;
	}
	/**
	 * @param int   收藏数
	 */
	public void setCollects(int collects)
	{
	 	this.collects=collects;
	}
	
	/**
	 * @return int  收藏数
	 */
	public int getCollects()
	{
	 	return this.collects;
	}
	/**
	 * @param int   得分
	 */
	public void setScores(int scores)
	{
	 	this.scores=scores;
	}
	
	/**
	 * @return int  得分
	 */
	public int getScores()
	{
	 	return this.scores;
	}
	/**
	 * @param boolean   是否入围
	 */
	public void setFinalist(boolean finalist)
	{
	 	this.finalist=finalist;
	}
	
	/**
	 * @return boolean  是否入围
	 */
	public boolean isFinalist()
	{
	 	return this.finalist;
	}
	/**
	 * @param boolean   获得奖励
	 */
	public void setRewarded(boolean rewarded)
	{
	 	this.rewarded=rewarded;
	}
	
	/**
	 * @return boolean  获得奖励
	 */
	public boolean isRewarded()
	{
	 	return this.rewarded;
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
	    Map<String, Object> map=new HashMap<String, Object>((int)24);
	    map.put("showId",this.showId);
	    map.put("activeId",this.activeId);
	    map.put("userId",this.userId);
	    map.put("title",this.title);
	    map.put("category",this.category);
	    map.put("grade",this.grade);
	    map.put("subject",this.subject);
	    map.put("tag",this.tag);
	    map.put("cover",this.cover);
	    map.put("intro",this.intro);
	    map.put("type",this.type);
	    map.put("status",this.status);
	    map.put("dateline",this.dateline);
	    map.put("dir",this.dir);
	    map.put("roomId",this.roomId);
	    map.put("views",this.views);
	    map.put("ups",this.ups);
	    map.put("downs",this.downs);
	    map.put("comments",this.comments);
	    map.put("collects",this.collects);
	    map.put("scores",this.scores);
	    map.put("finalist",this.finalist);
	    map.put("rewarded",this.rewarded);
	    map.put("memo",this.memo);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {showId};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          Show  entity=(Show)o;
          return showId==entity.showId;
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
	     sb.append("\"showId\":").append(showId).append(",");
	     sb.append("\"activeId\":").append(activeId).append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"title\":").append("\""+title+"\"").append(",");
	     sb.append("\"category\":").append(category).append(",");
	     sb.append("\"grade\":").append(grade).append(",");
	     sb.append("\"subject\":").append(subject).append(",");
	     sb.append("\"tag\":").append("\""+tag+"\"").append(",");
	     sb.append("\"cover\":").append("\""+cover+"\"").append(",");
	     sb.append("\"intro\":").append("\""+intro+"\"").append(",");
	     sb.append("\"type\":").append(type).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"dateline\":").append(dateline).append(",");
	     sb.append("\"dir\":").append("\""+dir+"\"").append(",");
	     sb.append("\"roomId\":").append(roomId).append(",");
	     sb.append("\"views\":").append(views).append(",");
	     sb.append("\"ups\":").append(ups).append(",");
	     sb.append("\"downs\":").append(downs).append(",");
	     sb.append("\"comments\":").append(comments).append(",");
	     sb.append("\"collects\":").append(collects).append(",");
	     sb.append("\"scores\":").append(scores).append(",");
	     sb.append("\"finalist\":").append(finalist).append(",");
	     sb.append("\"rewarded\":").append(rewarded).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
