package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_COURSE", primary = { "COURSE_ID" })
public class Course implements  Entity{

	/**
	*课程编号
	*/
	private long   courseId;

	/**
	*学校代码，默认为0，代表主站所有，否则对应子站
	*/
	private long   schoolId;

	/**
	*课程类型：默认为：1，在线课程，2、普通视频，3、动画片，4、宣传片
	*/
	private byte   type;

	/**
	*上课地址信息ID
	*/
	private long   addressId;

	/**
	*上课时的教室ID
	*/
	private long   roomId;

	/**
	*课程名称
	*/
	private String   name;

	/**
	*视频文件路径
	*/
	private String   dir;

	/**
	*课程分类
	*/
	private String   category;

	/**
	*标签，课程关键字，多个标签，空格分隔
	*/
	private String   tag;

	/**
	*课程适用范围
	*/
	private String   scope;

	/**
	*课程目的
	*/
	private String   aim;

	/**
	*课程封面LOGO
	*/
	private String   cover;

	/**
	*简介
	*/
	private String   intro;

	/**
	*发布人编号，和教室编号相同
	*/
	private long   userId;

	/**
	*授课教师，允许是多人，可以是朋友代讲
	*/
	private String   teacher;

	/**
	*定价
	*/
	private int   price;

	/**
	*原始价格，仅对线下课程有效
	*/
	private int   oldPrice;

	/**
	*预计开始时间
	*/
	private int   startTime;

	/**
	*课程长度，精确到秒
	*/
	private int   time;

	/**
	*实际开始时间
	*/
	private int   realStartTime;

	/**
	*实际结束时间
	*/
	private int   realEndTime;

	/**
	*首次发布时间
	*/
	private int   publishTime;

	/**
	*最后更新时间,，作为版本检测的时间
	*/
	private int   updateTime;

	/**
	*报名时段，线下课程
	*/
	private String   applyTime;

	/**
	*开课时段，线下课程
	*/
	private String   openTime;

	/**
	*有效期，线下课程
	*/
	private int   deadline;

	/**
	*课程状态(0:未发布1：报名阶段 2：已开课；3：课程取消，4：非正常结束  9：结束，视频课程直接设定为结束)
	*/
	private byte   status;

	/**
	*最大课程容量，当最大值为0时代表不限制容量
	*/
	private int   maxCapacity;

	/**
	*最小课程容量
	*/
	private int   minCapacity;

	/**
	*公开方式，0不公开，1、学校内部公开，2、对所有人公开
	*/
	private byte   shareModel;

	/**
	*公开观看录像价格
	*/
	private int   sharePrice;

	/**
	*试看时间，单位分钟
	*/
	private int   tryTime;

	/**
	*是否是推荐课程
	*/
	private boolean   suggest;

	/**
	*是否置顶（和推荐有点不同，推荐不一定置顶）
	*/
	private boolean   top;

	/**
	*是否有优惠
	*/
	private boolean   favor;

	/**
	*课程总得分，存储为96，代表9.6分
	*/
	private byte   score;

	/**
	*浏览人数
	*/
	private int   views;

	/**
	*顶的人数
	*/
	private int   ups;

	/**
	*踩的人数
	*/
	private int   downs;

	/**
	*收藏人数
	*/
	private int   collects;

	/**
	*已参与人数
	*/
	private int   joins;

	/**
	*退课人数
	*/
	private int   outs;

	/**
	*完成听课人数
	*/
	private int   completes;

	/**
	*评价总数
	*/
	private int   comments;

	/**
	*备注
	*/
	private String   memo;

	/**
	*
	*/
	private int   city;

   
    public Course(){}
    
    public Course(long courseId )
    {
           this.courseId=courseId;
     }
	/**
	 * @param long   课程编号
	 */
	public void setCourseId(long courseId)
	{
	 	this.courseId=courseId;
	}
	
	/**
	 * @return long  课程编号
	 */
	public long getCourseId()
	{
	 	return this.courseId;
	}
	/**
	 * @param long   学校代码，默认为0，代表主站所有，否则对应子站
	 */
	public void setSchoolId(long schoolId)
	{
	 	this.schoolId=schoolId;
	}
	
	/**
	 * @return long  学校代码，默认为0，代表主站所有，否则对应子站
	 */
	public long getSchoolId()
	{
	 	return this.schoolId;
	}
	/**
	 * @param byte   课程类型：默认为：1，在线课程，2、普通视频，3、动画片，4、宣传片
	 */
	public void setType(byte type)
	{
	 	this.type=type;
	}
	
	/**
	 * @return byte  课程类型：默认为：1，在线课程，2、普通视频，3、动画片，4、宣传片
	 */
	public byte getType()
	{
	 	return this.type;
	}
	/**
	 * @param long   上课地址信息ID
	 */
	public void setAddressId(long addressId)
	{
	 	this.addressId=addressId;
	}
	
	/**
	 * @return long  上课地址信息ID
	 */
	public long getAddressId()
	{
	 	return this.addressId;
	}
	/**
	 * @param long   上课时的教室ID
	 */
	public void setRoomId(long roomId)
	{
	 	this.roomId=roomId;
	}
	
	/**
	 * @return long  上课时的教室ID
	 */
	public long getRoomId()
	{
	 	return this.roomId;
	}
	/**
	 * @param String   课程名称
	 */
	public void setName(String name)
	{
	 	this.name=name;
	}
	
	/**
	 * @return String  课程名称
	 */
	public String getName()
	{
	 	return this.name;
	}
	/**
	 * @param String   视频文件路径
	 */
	public void setDir(String dir)
	{
	 	this.dir=dir;
	}
	
	/**
	 * @return String  视频文件路径
	 */
	public String getDir()
	{
	 	return this.dir;
	}
	/**
	 * @param long   课程分类
	 */
	public void setCategory(String category)
	{
	 	this.category=category;
	}
	
	/**
	 * @return long  课程分类
	 */
	public String getCategory()
	{
	 	return this.category;
	}
	/**
	 * @param String   标签，课程关键字，多个标签，空格分隔
	 */
	public void setTag(String tag)
	{
	 	this.tag=tag;
	}
	
	/**
	 * @return String  标签，课程关键字，多个标签，空格分隔
	 */
	public String getTag()
	{
	 	return this.tag;
	}
	/**
	 * @param String   课程适用范围
	 */
	public void setScope(String scope)
	{
	 	this.scope=scope;
	}
	
	/**
	 * @return String  课程适用范围
	 */
	public String getScope()
	{
	 	return this.scope;
	}
	/**
	 * @param String   课程目的
	 */
	public void setAim(String aim)
	{
	 	this.aim=aim;
	}
	
	/**
	 * @return String  课程目的
	 */
	public String getAim()
	{
	 	return this.aim;
	}
	/**
	 * @param String   课程封面LOGO
	 */
	public void setCover(String cover)
	{
	 	this.cover=cover;
	}
	
	/**
	 * @return String  课程封面LOGO
	 */
	public String getCover()
	{
	 	return this.cover;
	}
	/**
	 * @param String   简介
	 */
	public void setIntro(String intro)
	{
	 	this.intro=intro;
	}
	
	/**
	 * @return String  简介
	 */
	public String getIntro()
	{
	 	return this.intro;
	}
	/**
	 * @param long   发布人编号，和教室编号相同
	 */
	public void setUserId(long userId)
	{
	 	this.userId=userId;
	}
	
	/**
	 * @return long  发布人编号，和教室编号相同
	 */
	public long getUserId()
	{
	 	return this.userId;
	}
	/**
	 * @param String   授课教师，允许是多人，可以是朋友代讲
	 */
	public void setTeacher(String teacher)
	{
	 	this.teacher=teacher;
	}
	
	/**
	 * @return String  授课教师，允许是多人，可以是朋友代讲
	 */
	public String getTeacher()
	{
	 	return this.teacher;
	}
	/**
	 * @param int   定价
	 */
	public void setPrice(int price)
	{
	 	this.price=price;
	}
	
	/**
	 * @return int  定价
	 */
	public int getPrice()
	{
	 	return this.price;
	}
	/**
	 * @param int   原始价格，仅对线下课程有效
	 */
	public void setOldPrice(int oldPrice)
	{
	 	this.oldPrice=oldPrice;
	}
	
	/**
	 * @return int  原始价格，仅对线下课程有效
	 */
	public int getOldPrice()
	{
	 	return this.oldPrice;
	}
	/**
	 * @param int   预计开始时间
	 */
	public void setStartTime(int startTime)
	{
	 	this.startTime=startTime;
	}
	
	/**
	 * @return int  预计开始时间
	 */
	public int getStartTime()
	{
	 	return this.startTime;
	}
	/**
	 * @param int   课程长度，精确到秒
	 */
	public void setTime(int time)
	{
	 	this.time=time;
	}
	
	/**
	 * @return int  课程长度，精确到秒
	 */
	public int getTime()
	{
	 	return this.time;
	}
	/**
	 * @param int   实际开始时间
	 */
	public void setRealStartTime(int realStartTime)
	{
	 	this.realStartTime=realStartTime;
	}
	
	/**
	 * @return int  实际开始时间
	 */
	public int getRealStartTime()
	{
	 	return this.realStartTime;
	}
	/**
	 * @param int   实际结束时间
	 */
	public void setRealEndTime(int realEndTime)
	{
	 	this.realEndTime=realEndTime;
	}
	
	/**
	 * @return int  实际结束时间
	 */
	public int getRealEndTime()
	{
	 	return this.realEndTime;
	}
	/**
	 * @param int   首次发布时间
	 */
	public void setPublishTime(int publishTime)
	{
	 	this.publishTime=publishTime;
	}
	
	/**
	 * @return int  首次发布时间
	 */
	public int getPublishTime()
	{
	 	return this.publishTime;
	}
	/**
	 * @param int   最后更新时间,，作为版本检测的时间
	 */
	public void setUpdateTime(int updateTime)
	{
	 	this.updateTime=updateTime;
	}
	
	/**
	 * @return int  最后更新时间,，作为版本检测的时间
	 */
	public int getUpdateTime()
	{
	 	return this.updateTime;
	}
	/**
	 * @param String   报名时段，线下课程
	 */
	public void setApplyTime(String applyTime)
	{
	 	this.applyTime=applyTime;
	}
	
	/**
	 * @return String  报名时段，线下课程
	 */
	public String getApplyTime()
	{
	 	return this.applyTime;
	}
	/**
	 * @param String   开课时段，线下课程
	 */
	public void setOpenTime(String openTime)
	{
	 	this.openTime=openTime;
	}
	
	/**
	 * @return String  开课时段，线下课程
	 */
	public String getOpenTime()
	{
	 	return this.openTime;
	}
	/**
	 * @param int   有效期，线下课程
	 */
	public void setDeadline(int deadline)
	{
	 	this.deadline=deadline;
	}
	
	/**
	 * @return int  有效期，线下课程
	 */
	public int getDeadline()
	{
	 	return this.deadline;
	}
	/**
	 * @param byte   课程状态(0:未发布1：报名阶段 2：已开课；3：课程取消，4：非正常结束  9：结束，视频课程直接设定为结束)
	 */
	public void setStatus(byte status)
	{
	 	this.status=status;
	}
	
	/**
	 * @return byte  课程状态(0:未发布1：报名阶段 2：已开课；3：课程取消，4：非正常结束  9：结束，视频课程直接设定为结束)
	 */
	public byte getStatus()
	{
	 	return this.status;
	}
	/**
	 * @param int   最大课程容量，当最大值为0时代表不限制容量
	 */
	public void setMaxCapacity(int maxCapacity)
	{
	 	this.maxCapacity=maxCapacity;
	}
	
	/**
	 * @return int  最大课程容量，当最大值为0时代表不限制容量
	 */
	public int getMaxCapacity()
	{
	 	return this.maxCapacity;
	}
	/**
	 * @param int   最小课程容量
	 */
	public void setMinCapacity(int minCapacity)
	{
	 	this.minCapacity=minCapacity;
	}
	
	/**
	 * @return int  最小课程容量
	 */
	public int getMinCapacity()
	{
	 	return this.minCapacity;
	}
	/**
	 * @param byte   公开方式，0不公开，1、学校内部公开，2、对所有人公开
	 */
	public void setShareModel(byte shareModel)
	{
	 	this.shareModel=shareModel;
	}
	
	/**
	 * @return byte  公开方式，0不公开，1、学校内部公开，2、对所有人公开
	 */
	public byte getShareModel()
	{
	 	return this.shareModel;
	}
	/**
	 * @param int   公开观看录像价格
	 */
	public void setSharePrice(int sharePrice)
	{
	 	this.sharePrice=sharePrice;
	}
	
	/**
	 * @return int  公开观看录像价格
	 */
	public int getSharePrice()
	{
	 	return this.sharePrice;
	}
	/**
	 * @param int   试看时间，单位分钟
	 */
	public void setTryTime(int tryTime)
	{
	 	this.tryTime=tryTime;
	}
	
	/**
	 * @return int  试看时间，单位分钟
	 */
	public int getTryTime()
	{
	 	return this.tryTime;
	}
	/**
	 * @param boolean   是否是推荐课程
	 */
	public void setSuggest(boolean suggest)
	{
	 	this.suggest=suggest;
	}
	
	/**
	 * @return boolean  是否是推荐课程
	 */
	public boolean isSuggest()
	{
	 	return this.suggest;
	}
	/**
	 * @param boolean   是否置顶（和推荐有点不同，推荐不一定置顶）
	 */
	public void setTop(boolean top)
	{
	 	this.top=top;
	}
	
	/**
	 * @return boolean  是否置顶（和推荐有点不同，推荐不一定置顶）
	 */
	public boolean isTop()
	{
	 	return this.top;
	}
	/**
	 * @param boolean   是否有优惠
	 */
	public void setFavor(boolean favor)
	{
	 	this.favor=favor;
	}
	
	/**
	 * @return boolean  是否有优惠
	 */
	public boolean isFavor()
	{
	 	return this.favor;
	}
	/**
	 * @param byte   课程总得分，存储为96，代表9.6分
	 */
	public void setScore(byte score)
	{
	 	this.score=score;
	}
	
	/**
	 * @return byte  课程总得分，存储为96，代表9.6分
	 */
	public byte getScore()
	{
	 	return this.score;
	}
	/**
	 * @param int   浏览人数
	 */
	public void setViews(int views)
	{
	 	this.views=views;
	}
	
	/**
	 * @return int  浏览人数
	 */
	public int getViews()
	{
	 	return this.views;
	}
	/**
	 * @param int   顶的人数
	 */
	public void setUps(int ups)
	{
	 	this.ups=ups;
	}
	
	/**
	 * @return int  顶的人数
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
	 * @param int   收藏人数
	 */
	public void setCollects(int collects)
	{
	 	this.collects=collects;
	}
	
	/**
	 * @return int  收藏人数
	 */
	public int getCollects()
	{
	 	return this.collects;
	}
	/**
	 * @param int   已参与人数
	 */
	public void setJoins(int joins)
	{
	 	this.joins=joins;
	}
	
	/**
	 * @return int  已参与人数
	 */
	public int getJoins()
	{
	 	return this.joins;
	}
	/**
	 * @param int   退课人数
	 */
	public void setOuts(int outs)
	{
	 	this.outs=outs;
	}
	
	/**
	 * @return int  退课人数
	 */
	public int getOuts()
	{
	 	return this.outs;
	}
	/**
	 * @param int   完成听课人数
	 */
	public void setCompletes(int completes)
	{
	 	this.completes=completes;
	}
	
	/**
	 * @return int  完成听课人数
	 */
	public int getCompletes()
	{
	 	return this.completes;
	}
	/**
	 * @param int   评价总数
	 */
	public void setComments(int comments)
	{
	 	this.comments=comments;
	}
	
	/**
	 * @return int  评价总数
	 */
	public int getComments()
	{
	 	return this.comments;
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
	 * @param int   
	 */
	public void setCity(int city)
	{
	 	this.city=city;
	}
	
	/**
	 * @return int  
	 */
	public int getCity()
	{
	 	return this.city;
	}

   @Override
	public Map<String, Object> toMap() {
	    Map<String, Object> map=new HashMap<String, Object>((int)46);
	    map.put("courseId",this.courseId);
	    map.put("schoolId",this.schoolId);
	    map.put("type",this.type);
	    map.put("addressId",this.addressId);
	    map.put("roomId",this.roomId);
	    map.put("name",this.name);
	    map.put("dir",this.dir);
	    map.put("category",this.category);
	    map.put("tag",this.tag);
	    map.put("scope",this.scope);
	    map.put("aim",this.aim);
	    map.put("cover",this.cover);
	    map.put("intro",this.intro);
	    map.put("userId",this.userId);
	    map.put("teacher",this.teacher);
	    map.put("price",this.price);
	    map.put("oldPrice",this.oldPrice);
	    map.put("startTime",this.startTime);
	    map.put("time",this.time);
	    map.put("realStartTime",this.realStartTime);
	    map.put("realEndTime",this.realEndTime);
	    map.put("publishTime",this.publishTime);
	    map.put("updateTime",this.updateTime);
	    map.put("applyTime",this.applyTime);
	    map.put("openTime",this.openTime);
	    map.put("deadline",this.deadline);
	    map.put("status",this.status);
	    map.put("maxCapacity",this.maxCapacity);
	    map.put("minCapacity",this.minCapacity);
	    map.put("shareModel",this.shareModel);
	    map.put("sharePrice",this.sharePrice);
	    map.put("tryTime",this.tryTime);
	    map.put("suggest",this.suggest);
	    map.put("top",this.top);
	    map.put("favor",this.favor);
	    map.put("score",this.score);
	    map.put("views",this.views);
	    map.put("ups",this.ups);
	    map.put("downs",this.downs);
	    map.put("collects",this.collects);
	    map.put("joins",this.joins);
	    map.put("outs",this.outs);
	    map.put("completes",this.completes);
	    map.put("comments",this.comments);
	    map.put("memo",this.memo);
	    map.put("city",this.city);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {courseId};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          Course  entity=(Course)o;
          return courseId==entity.courseId;
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
	     sb.append("\"courseId\":").append(courseId).append(",");
	     sb.append("\"schoolId\":").append(schoolId).append(",");
	     sb.append("\"type\":").append(type).append(",");
	     sb.append("\"addressId\":").append(addressId).append(",");
	     sb.append("\"roomId\":").append(roomId).append(",");
	     sb.append("\"name\":").append("\""+name+"\"").append(",");
	     sb.append("\"dir\":").append("\""+dir+"\"").append(",");
	     sb.append("\"category\":").append(category).append(",");
	     sb.append("\"tag\":").append("\""+tag+"\"").append(",");
	     sb.append("\"scope\":").append("\""+scope+"\"").append(",");
	     sb.append("\"aim\":").append("\""+aim+"\"").append(",");
	     sb.append("\"cover\":").append("\""+cover+"\"").append(",");
	     sb.append("\"intro\":").append("\""+intro+"\"").append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"teacher\":").append("\""+teacher+"\"").append(",");
	     sb.append("\"price\":").append(price).append(",");
	     sb.append("\"oldPrice\":").append(oldPrice).append(",");
	     sb.append("\"startTime\":").append(startTime).append(",");
	     sb.append("\"time\":").append(time).append(",");
	     sb.append("\"realStartTime\":").append(realStartTime).append(",");
	     sb.append("\"realEndTime\":").append(realEndTime).append(",");
	     sb.append("\"publishTime\":").append(publishTime).append(",");
	     sb.append("\"updateTime\":").append(updateTime).append(",");
	     sb.append("\"applyTime\":").append("\""+applyTime+"\"").append(",");
	     sb.append("\"openTime\":").append("\""+openTime+"\"").append(",");
	     sb.append("\"deadline\":").append(deadline).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"maxCapacity\":").append(maxCapacity).append(",");
	     sb.append("\"minCapacity\":").append(minCapacity).append(",");
	     sb.append("\"shareModel\":").append(shareModel).append(",");
	     sb.append("\"sharePrice\":").append(sharePrice).append(",");
	     sb.append("\"tryTime\":").append(tryTime).append(",");
	     sb.append("\"suggest\":").append(suggest).append(",");
	     sb.append("\"top\":").append(top).append(",");
	     sb.append("\"favor\":").append(favor).append(",");
	     sb.append("\"score\":").append(score).append(",");
	     sb.append("\"views\":").append(views).append(",");
	     sb.append("\"ups\":").append(ups).append(",");
	     sb.append("\"downs\":").append(downs).append(",");
	     sb.append("\"collects\":").append(collects).append(",");
	     sb.append("\"joins\":").append(joins).append(",");
	     sb.append("\"outs\":").append(outs).append(",");
	     sb.append("\"completes\":").append(completes).append(",");
	     sb.append("\"comments\":").append(comments).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"").append(",");
	     sb.append("\"city\":").append(city);
	    sb.append("}");
	    return sb.toString();
	}
}
