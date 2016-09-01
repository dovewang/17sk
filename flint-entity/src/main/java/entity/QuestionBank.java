package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_QUESTION_BANK", primary = { "QUESTION_ID" })
public class QuestionBank implements  Entity{

	/**
	*问题标号
	*/
	private long   questionId;

	/**
	*学校编号，限学校内部题库检索
	*/
	private long   schoolId;

	/**
	*年级
	*/
	private int   grade;

	/**
	*科目
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
	*难度系数
	*/
	private int   difficult;

	/**
	*问题描述
	*/
	private String   content;

	/**
	*问题类型，单选，多选，填空，判断，简答，计算，听力，看图，视频等
	*/
	private byte   type;

	/**
	*选项，设置可选项A,B,C,D，如为图片类型，需要用户手动输入，在线建立的练习可由系统自动生成
	*/
	private String   picks;

	/**
	*参考问题答案
	*/
	private String   answer;

	/**
	*问题解析
	*/
	private String   analyse;

	/**
	*状态
	*/
	private byte   status;

	/**
	*价格
	*/
	private int   price;

	/**
	*入库时间
	*/
	private int   createTime;

	/**
	*入库人
	*/
	private long   userId;

	/**
	*使用该试题的次数
	*/
	private int   useTimes;

	/**
	*支持、觉得这道题不错的人数
	*/
	private int   ups;

	/**
	*觉得这道题很差的人数
	*/
	private int   downs;

	/**
	*问题评析数
	*/
	private int   analyses;

	/**
	*备注
	*/
	private String   memo;

   
    public QuestionBank(){}
    
    public QuestionBank(long questionId )
    {
           this.questionId=questionId;
     }
	/**
	 * @param long   问题标号
	 */
	public void setQuestionId(long questionId)
	{
	 	this.questionId=questionId;
	}
	
	/**
	 * @return long  问题标号
	 */
	public long getQuestionId()
	{
	 	return this.questionId;
	}
	/**
	 * @param long   学校编号，限学校内部题库检索
	 */
	public void setSchoolId(long schoolId)
	{
	 	this.schoolId=schoolId;
	}
	
	/**
	 * @return long  学校编号，限学校内部题库检索
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
	 * @param long   科目
	 */
	public void setKind(long kind)
	{
	 	this.kind=kind;
	}
	
	/**
	 * @return long  科目
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
	 * @param int   难度系数
	 */
	public void setDifficult(int difficult)
	{
	 	this.difficult=difficult;
	}
	
	/**
	 * @return int  难度系数
	 */
	public int getDifficult()
	{
	 	return this.difficult;
	}
	/**
	 * @param String   问题描述
	 */
	public void setContent(String content)
	{
	 	this.content=content;
	}
	
	/**
	 * @return String  问题描述
	 */
	public String getContent()
	{
	 	return this.content;
	}
	/**
	 * @param byte   问题类型，单选，多选，填空，判断，简答，计算，听力，看图，视频等
	 */
	public void setType(byte type)
	{
	 	this.type=type;
	}
	
	/**
	 * @return byte  问题类型，单选，多选，填空，判断，简答，计算，听力，看图，视频等
	 */
	public byte getType()
	{
	 	return this.type;
	}
	/**
	 * @param String   选项，设置可选项A,B,C,D，如为图片类型，需要用户手动输入，在线建立的练习可由系统自动生成
	 */
	public void setPicks(String picks)
	{
	 	this.picks=picks;
	}
	
	/**
	 * @return String  选项，设置可选项A,B,C,D，如为图片类型，需要用户手动输入，在线建立的练习可由系统自动生成
	 */
	public String getPicks()
	{
	 	return this.picks;
	}
	/**
	 * @param String   参考问题答案
	 */
	public void setAnswer(String answer)
	{
	 	this.answer=answer;
	}
	
	/**
	 * @return String  参考问题答案
	 */
	public String getAnswer()
	{
	 	return this.answer;
	}
	/**
	 * @param String   问题解析
	 */
	public void setAnalyse(String analyse)
	{
	 	this.analyse=analyse;
	}
	
	/**
	 * @return String  问题解析
	 */
	public String getAnalyse()
	{
	 	return this.analyse;
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
	 * @param int   价格
	 */
	public void setPrice(int price)
	{
	 	this.price=price;
	}
	
	/**
	 * @return int  价格
	 */
	public int getPrice()
	{
	 	return this.price;
	}
	/**
	 * @param int   入库时间
	 */
	public void setCreateTime(int createTime)
	{
	 	this.createTime=createTime;
	}
	
	/**
	 * @return int  入库时间
	 */
	public int getCreateTime()
	{
	 	return this.createTime;
	}
	/**
	 * @param long   入库人
	 */
	public void setUserId(long userId)
	{
	 	this.userId=userId;
	}
	
	/**
	 * @return long  入库人
	 */
	public long getUserId()
	{
	 	return this.userId;
	}
	/**
	 * @param int   使用该试题的次数
	 */
	public void setUseTimes(int useTimes)
	{
	 	this.useTimes=useTimes;
	}
	
	/**
	 * @return int  使用该试题的次数
	 */
	public int getUseTimes()
	{
	 	return this.useTimes;
	}
	/**
	 * @param int   支持、觉得这道题不错的人数
	 */
	public void setUps(int ups)
	{
	 	this.ups=ups;
	}
	
	/**
	 * @return int  支持、觉得这道题不错的人数
	 */
	public int getUps()
	{
	 	return this.ups;
	}
	/**
	 * @param int   觉得这道题很差的人数
	 */
	public void setDowns(int downs)
	{
	 	this.downs=downs;
	}
	
	/**
	 * @return int  觉得这道题很差的人数
	 */
	public int getDowns()
	{
	 	return this.downs;
	}
	/**
	 * @param int   问题评析数
	 */
	public void setAnalyses(int analyses)
	{
	 	this.analyses=analyses;
	}
	
	/**
	 * @return int  问题评析数
	 */
	public int getAnalyses()
	{
	 	return this.analyses;
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
	    map.put("schoolId",schoolId);
	    map.put("grade",grade);
	    map.put("kind",kind);
	    map.put("k1",k1);
	    map.put("k2",k2);
	    map.put("k3",k3);
	    map.put("k4",k4);
	    map.put("k5",k5);
	    map.put("tag",tag);
	    map.put("difficult",difficult);
	    map.put("content",content);
	    map.put("type",type);
	    map.put("picks",picks);
	    map.put("answer",answer);
	    map.put("analyse",analyse);
	    map.put("status",status);
	    map.put("price",price);
	    map.put("createTime",createTime);
	    map.put("userId",userId);
	    map.put("useTimes",useTimes);
	    map.put("ups",ups);
	    map.put("downs",downs);
	    map.put("analyses",analyses);
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
          QuestionBank  entity=(QuestionBank)o;
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
	     sb.append("\"schoolId\":").append(schoolId).append(",");
	     sb.append("\"grade\":").append(grade).append(",");
	     sb.append("\"kind\":").append(kind).append(",");
	     sb.append("\"k1\":").append(k1).append(",");
	     sb.append("\"k2\":").append(k2).append(",");
	     sb.append("\"k3\":").append(k3).append(",");
	     sb.append("\"k4\":").append(k4).append(",");
	     sb.append("\"k5\":").append(k5).append(",");
	     sb.append("\"tag\":").append("\""+tag+"\"").append(",");
	     sb.append("\"difficult\":").append(difficult).append(",");
	     sb.append("\"content\":").append("\""+content+"\"").append(",");
	     sb.append("\"type\":").append(type).append(",");
	     sb.append("\"picks\":").append("\""+picks+"\"").append(",");
	     sb.append("\"answer\":").append("\""+answer+"\"").append(",");
	     sb.append("\"analyse\":").append("\""+analyse+"\"").append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"price\":").append(price).append(",");
	     sb.append("\"createTime\":").append(createTime).append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"useTimes\":").append(useTimes).append(",");
	     sb.append("\"ups\":").append(ups).append(",");
	     sb.append("\"downs\":").append(downs).append(",");
	     sb.append("\"analyses\":").append(analyses).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
