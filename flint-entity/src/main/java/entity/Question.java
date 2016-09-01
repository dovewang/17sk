package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_QUESTION", primary = { "QUESTION_ID" })
public class Question implements  Entity{

	/**
	*主键
	*/
	private long   questionId;

	/**
	*问题标题(关键字检索区域)
	*/
	private String   title;

	/**
	*问题的描述
	*/
	private String   intro;

	/**
	*学校代码，默认为0，代表主站所有，否则对应子站
	*/
	private long   schoolId;

	/**
	*年级
	*/
	private int   grade;

	/**
	*学科
	*/
	private long   kind;

	/**
	*知识点深度1，默认0
	*/
	private long   k1;

	/**
	*知识点深度2，默认0
	*/
	private long   k2;

	/**
	*知识点深度3，默认0
	*/
	private long   k3;

	/**
	*知识点深度4，默认0
	*/
	private long   k4;

	/**
	*知识点深度5，默认0
	*/
	private long   k5;

	/**
	*知识点，最后节点
	*/
	private String   tag;

	/**
	*问题价格
	*/
	private int   price;

	/**
	*是否显示提问人，0:不匿名 ,1：匿名
	*/
	private boolean   anonymous;

	/**
	*提问人id
	*/
	private long   asker;

	/**
	*问题提交时间
	*/
	private int   createTime;

	/**
	*最后的修改时间
	*/
	private int   lastUpdateTime;

	/**
	*截止日期
	*/
	private int   deadline;

	/**
	*问题状态(0:新建，1：未解决，2：已解决，3：已关闭)
	*/
	private byte   status;

	/**
	*最佳答案
	*/
	private long   bestAnswerId;

	/**
	*解答人数
	*/
	private int   answers;

	/**
	*浏览人数
	*/
	private int   views;

	/**
	*收藏人数
	*/
	private int   collects;

	/**
	*补充说明（修改的时候会用到）
	*/
	private String   addInfo;

	/**
	*备注
	*/
	private String   memo;

   
    public Question(){}
    
    public Question(long questionId )
    {
           this.questionId=questionId;
     }
	/**
	 * @param long   主键
	 */
	public void setQuestionId(long questionId)
	{
	 	this.questionId=questionId;
	}
	
	/**
	 * @return long  主键
	 */
	public long getQuestionId()
	{
	 	return this.questionId;
	}
	/**
	 * @param String   问题标题(关键字检索区域)
	 */
	public void setTitle(String title)
	{
	 	this.title=title;
	}
	
	/**
	 * @return String  问题标题(关键字检索区域)
	 */
	public String getTitle()
	{
	 	return this.title;
	}
	/**
	 * @param String   问题的描述
	 */
	public void setIntro(String intro)
	{
	 	this.intro=intro;
	}
	
	/**
	 * @return String  问题的描述
	 */
	public String getIntro()
	{
	 	return this.intro;
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
	 * @param int   年级
	 */
	public void setGrade(int grade)
	{
	 	this.grade=grade;
	}
	
	/**
	 * @return int  年级
	 */
	public int getGrade()
	{
	 	return this.grade;
	}
	/**
	 * @param long   学科
	 */
	public void setKind(long kind)
	{
	 	this.kind=kind;
	}
	
	/**
	 * @return long  学科
	 */
	public long getKind()
	{
	 	return this.kind;
	}
	/**
	 * @param long   知识点深度1，默认0
	 */
	public void setK1(long k1)
	{
	 	this.k1=k1;
	}
	
	/**
	 * @return long  知识点深度1，默认0
	 */
	public long getK1()
	{
	 	return this.k1;
	}
	/**
	 * @param long   知识点深度2，默认0
	 */
	public void setK2(long k2)
	{
	 	this.k2=k2;
	}
	
	/**
	 * @return long  知识点深度2，默认0
	 */
	public long getK2()
	{
	 	return this.k2;
	}
	/**
	 * @param long   知识点深度3，默认0
	 */
	public void setK3(long k3)
	{
	 	this.k3=k3;
	}
	
	/**
	 * @return long  知识点深度3，默认0
	 */
	public long getK3()
	{
	 	return this.k3;
	}
	/**
	 * @param long   知识点深度4，默认0
	 */
	public void setK4(long k4)
	{
	 	this.k4=k4;
	}
	
	/**
	 * @return long  知识点深度4，默认0
	 */
	public long getK4()
	{
	 	return this.k4;
	}
	/**
	 * @param long   知识点深度5，默认0
	 */
	public void setK5(long k5)
	{
	 	this.k5=k5;
	}
	
	/**
	 * @return long  知识点深度5，默认0
	 */
	public long getK5()
	{
	 	return this.k5;
	}
	/**
	 * @param String   知识点，最后节点
	 */
	public void setTag(String tag)
	{
	 	this.tag=tag;
	}
	
	/**
	 * @return String  知识点，最后节点
	 */
	public String getTag()
	{
	 	return this.tag;
	}
	/**
	 * @param int   问题价格
	 */
	public void setPrice(int price)
	{
	 	this.price=price;
	}
	
	/**
	 * @return int  问题价格
	 */
	public int getPrice()
	{
	 	return this.price;
	}
	/**
	 * @param boolean   是否显示提问人，0:不匿名 ,1：匿名
	 */
	public void setAnonymous(boolean anonymous)
	{
	 	this.anonymous=anonymous;
	}
	
	/**
	 * @return boolean  是否显示提问人，0:不匿名 ,1：匿名
	 */
	public boolean isAnonymous()
	{
	 	return this.anonymous;
	}
	/**
	 * @param long   提问人id
	 */
	public void setAsker(long asker)
	{
	 	this.asker=asker;
	}
	
	/**
	 * @return long  提问人id
	 */
	public long getAsker()
	{
	 	return this.asker;
	}
	/**
	 * @param int   问题提交时间
	 */
	public void setCreateTime(int createTime)
	{
	 	this.createTime=createTime;
	}
	
	/**
	 * @return int  问题提交时间
	 */
	public int getCreateTime()
	{
	 	return this.createTime;
	}
	/**
	 * @param int   最后的修改时间
	 */
	public void setLastUpdateTime(int lastUpdateTime)
	{
	 	this.lastUpdateTime=lastUpdateTime;
	}
	
	/**
	 * @return int  最后的修改时间
	 */
	public int getLastUpdateTime()
	{
	 	return this.lastUpdateTime;
	}
	/**
	 * @param int   截止日期
	 */
	public void setDeadline(int deadline)
	{
	 	this.deadline=deadline;
	}
	
	/**
	 * @return int  截止日期
	 */
	public int getDeadline()
	{
	 	return this.deadline;
	}
	/**
	 * @param byte   问题状态(0:新建，1：未解决，2：已解决，3：已关闭)
	 */
	public void setStatus(byte status)
	{
	 	this.status=status;
	}
	
	/**
	 * @return byte  问题状态(0:新建，1：未解决，2：已解决，3：已关闭)
	 */
	public byte getStatus()
	{
	 	return this.status;
	}
	/**
	 * @param long   最佳答案
	 */
	public void setBestAnswerId(long bestAnswerId)
	{
	 	this.bestAnswerId=bestAnswerId;
	}
	
	/**
	 * @return long  最佳答案
	 */
	public long getBestAnswerId()
	{
	 	return this.bestAnswerId;
	}
	/**
	 * @param int   解答人数
	 */
	public void setAnswers(int answers)
	{
	 	this.answers=answers;
	}
	
	/**
	 * @return int  解答人数
	 */
	public int getAnswers()
	{
	 	return this.answers;
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
	 * @param String   补充说明（修改的时候会用到）
	 */
	public void setAddInfo(String addInfo)
	{
	 	this.addInfo=addInfo;
	}
	
	/**
	 * @return String  补充说明（修改的时候会用到）
	 */
	public String getAddInfo()
	{
	 	return this.addInfo;
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
	    Map<String, Object> map=new HashMap<String, Object>((int)25);
	    map.put("questionId",questionId);
	    map.put("title",title);
	    map.put("intro",intro);
	    map.put("schoolId",schoolId);
	    map.put("grade",grade);
	    map.put("kind",kind);
	    map.put("k1",k1);
	    map.put("k2",k2);
	    map.put("k3",k3);
	    map.put("k4",k4);
	    map.put("k5",k5);
	    map.put("tag",tag);
	    map.put("price",price);
	    map.put("anonymous",anonymous);
	    map.put("asker",asker);
	    map.put("createTime",createTime);
	    map.put("lastUpdateTime",lastUpdateTime);
	    map.put("deadline",deadline);
	    map.put("status",status);
	    map.put("bestAnswerId",bestAnswerId);
	    map.put("answers",answers);
	    map.put("views",views);
	    map.put("collects",collects);
	    map.put("addInfo",addInfo);
	    map.put("memo",memo);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {questionId};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          Question  entity=(Question)o;
          return questionId==entity.questionId;
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
	     sb.append("\"questionId\":").append(questionId).append(",");
	     sb.append("\"title\":").append("\""+title+"\"").append(",");
	     sb.append("\"intro\":").append("\""+intro+"\"").append(",");
	     sb.append("\"schoolId\":").append(schoolId).append(",");
	     sb.append("\"grade\":").append(grade).append(",");
	     sb.append("\"kind\":").append(kind).append(",");
	     sb.append("\"k1\":").append(k1).append(",");
	     sb.append("\"k2\":").append(k2).append(",");
	     sb.append("\"k3\":").append(k3).append(",");
	     sb.append("\"k4\":").append(k4).append(",");
	     sb.append("\"k5\":").append(k5).append(",");
	     sb.append("\"tag\":").append("\""+tag+"\"").append(",");
	     sb.append("\"price\":").append(price).append(",");
	     sb.append("\"anonymous\":").append(anonymous).append(",");
	     sb.append("\"asker\":").append(asker).append(",");
	     sb.append("\"createTime\":").append(createTime).append(",");
	     sb.append("\"lastUpdateTime\":").append(lastUpdateTime).append(",");
	     sb.append("\"deadline\":").append(deadline).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"bestAnswerId\":").append(bestAnswerId).append(",");
	     sb.append("\"answers\":").append(answers).append(",");
	     sb.append("\"views\":").append(views).append(",");
	     sb.append("\"collects\":").append(collects).append(",");
	     sb.append("\"addInfo\":").append("\""+addInfo+"\"").append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
