package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_TUTOR_DEMAND", primary = { "DEMAND_ID" })
public class TutorDemand implements  Entity{

	/**
	*
	*/
	private long   demandId;

	/**
	*发布辅导需求人编号
	*/
	private long   userId;

	/**
	*状态
	*/
	private byte   status;

	/**
	*联系电话
	*/
	private String   phone;

	/**
	*辅导年级，0代表不限，限选1项
	*/
	private long   grade;

	/**
	*辅导科目1，最多选择3项，但是用户可以发布多个
	*/
	private long   kind1;

	/**
	*辅导科目2
	*/
	private long   kind2;

	/**
	*辅导科目3
	*/
	private long   kind3;

	/**
	*辅导类型：0代表网络教学辅导、1本地辅导 ,1一对一  2、辅导班 3 寒暑假    4、大学生家教
	*/
	private byte   tutorType;

	/**
	*辅导方式：1学生上门辅导，2老师上门辅导
	*/
	private byte   tutorMode;

	/**
	*辅导标题，限140子以内
	*/
	private String   title;

	/**
	*辅导要求
	*/
	private String   intro;

	/**
	*辅导地区
	*/
	private int   city;

	/**
	*地图标注链接
	*/
	private String   map;

	/**
	*最低价，为0时代表面议，和订单的价格不一定相同
	*/
	private int   price;

	/**
	*最高价，为0时代表只有最低价
	*/
	private int   highPrice;

	/**
	*发布时间
	*/
	private int   dateline;

	/**
	*有效期，1周、2周、1个月
	*/
	private int   validity;

	/**
	*候选人数
	*/
	private int   candidates;

	/**
	*浏览人数
	*/
	private int   views;

	/**
	*备注
	*/
	private String   memo;

   
    public TutorDemand(){}
    
    public TutorDemand(long demandId )
    {
           this.demandId=demandId;
     }
	/**
	 * @param long   
	 */
	public void setDemandId(long demandId)
	{
	 	this.demandId=demandId;
	}
	
	/**
	 * @return long  
	 */
	public long getDemandId()
	{
	 	return this.demandId;
	}
	/**
	 * @param long   发布辅导需求人编号
	 */
	public void setUserId(long userId)
	{
	 	this.userId=userId;
	}
	
	/**
	 * @return long  发布辅导需求人编号
	 */
	public long getUserId()
	{
	 	return this.userId;
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
	 * @param String   联系电话
	 */
	public void setPhone(String phone)
	{
	 	this.phone=phone;
	}
	
	/**
	 * @return String  联系电话
	 */
	public String getPhone()
	{
	 	return this.phone;
	}
	/**
	 * @param long   辅导年级，0代表不限，限选1项
	 */
	public void setGrade(long grade)
	{
	 	this.grade=grade;
	}
	
	/**
	 * @return long  辅导年级，0代表不限，限选1项
	 */
	public long getGrade()
	{
	 	return this.grade;
	}
	/**
	 * @param long   辅导科目1，最多选择3项，但是用户可以发布多个
	 */
	public void setKind1(long kind1)
	{
	 	this.kind1=kind1;
	}
	
	/**
	 * @return long  辅导科目1，最多选择3项，但是用户可以发布多个
	 */
	public long getKind1()
	{
	 	return this.kind1;
	}
	/**
	 * @param long   辅导科目2
	 */
	public void setKind2(long kind2)
	{
	 	this.kind2=kind2;
	}
	
	/**
	 * @return long  辅导科目2
	 */
	public long getKind2()
	{
	 	return this.kind2;
	}
	/**
	 * @param long   辅导科目3
	 */
	public void setKind3(long kind3)
	{
	 	this.kind3=kind3;
	}
	
	/**
	 * @return long  辅导科目3
	 */
	public long getKind3()
	{
	 	return this.kind3;
	}
	/**
	 * @param byte   辅导类型：0代表网络教学辅导、1本地辅导 ,1一对一  2、辅导班 3 寒暑假    4、大学生家教
	 */
	public void setTutorType(byte tutorType)
	{
	 	this.tutorType=tutorType;
	}
	
	/**
	 * @return byte  辅导类型：0代表网络教学辅导、1本地辅导 ,1一对一  2、辅导班 3 寒暑假    4、大学生家教
	 */
	public byte getTutorType()
	{
	 	return this.tutorType;
	}
	/**
	 * @param byte   辅导方式：1学生上门辅导，2老师上门辅导
	 */
	public void setTutorMode(byte tutorMode)
	{
	 	this.tutorMode=tutorMode;
	}
	
	/**
	 * @return byte  辅导方式：1学生上门辅导，2老师上门辅导
	 */
	public byte getTutorMode()
	{
	 	return this.tutorMode;
	}
	/**
	 * @param String   辅导标题，限140子以内
	 */
	public void setTitle(String title)
	{
	 	this.title=title;
	}
	
	/**
	 * @return String  辅导标题，限140子以内
	 */
	public String getTitle()
	{
	 	return this.title;
	}
	/**
	 * @param String   辅导要求
	 */
	public void setIntro(String intro)
	{
	 	this.intro=intro;
	}
	
	/**
	 * @return String  辅导要求
	 */
	public String getIntro()
	{
	 	return this.intro;
	}
	/**
	 * @param int   辅导地区
	 */
	public void setCity(int city)
	{
	 	this.city=city;
	}
	
	/**
	 * @return int  辅导地区
	 */
	public int getCity()
	{
	 	return this.city;
	}
	/**
	 * @param String   地图标注链接
	 */
	public void setMap(String map)
	{
	 	this.map=map;
	}
	
	/**
	 * @return String  地图标注链接
	 */
	public String getMap()
	{
	 	return this.map;
	}
	/**
	 * @param int   最低价，为0时代表面议，和订单的价格不一定相同
	 */
	public void setPrice(int price)
	{
	 	this.price=price;
	}
	
	/**
	 * @return int  最低价，为0时代表面议，和订单的价格不一定相同
	 */
	public int getPrice()
	{
	 	return this.price;
	}
	/**
	 * @param int   最高价，为0时代表只有最低价
	 */
	public void setHighPrice(int highPrice)
	{
	 	this.highPrice=highPrice;
	}
	
	/**
	 * @return int  最高价，为0时代表只有最低价
	 */
	public int getHighPrice()
	{
	 	return this.highPrice;
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
	 * @param int   有效期，1周、2周、1个月
	 */
	public void setValidity(int validity)
	{
	 	this.validity=validity;
	}
	
	/**
	 * @return int  有效期，1周、2周、1个月
	 */
	public int getValidity()
	{
	 	return this.validity;
	}
	/**
	 * @param int   候选人数
	 */
	public void setCandidates(int candidates)
	{
	 	this.candidates=candidates;
	}
	
	/**
	 * @return int  候选人数
	 */
	public int getCandidates()
	{
	 	return this.candidates;
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
	    Map<String, Object> map=new HashMap<String, Object>((int)21);
	    map.put("demandId",this.demandId);
	    map.put("userId",this.userId);
	    map.put("status",this.status);
	    map.put("phone",this.phone);
	    map.put("grade",this.grade);
	    map.put("kind1",this.kind1);
	    map.put("kind2",this.kind2);
	    map.put("kind3",this.kind3);
	    map.put("tutorType",this.tutorType);
	    map.put("tutorMode",this.tutorMode);
	    map.put("title",this.title);
	    map.put("intro",this.intro);
	    map.put("city",this.city);
	    map.put("map",this.map);
	    map.put("price",this.price);
	    map.put("highPrice",this.highPrice);
	    map.put("dateline",this.dateline);
	    map.put("validity",this.validity);
	    map.put("candidates",this.candidates);
	    map.put("views",this.views);
	    map.put("memo",this.memo);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {demandId};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          TutorDemand  entity=(TutorDemand)o;
          return demandId==entity.demandId;
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
	     sb.append("\"demandId\":").append(demandId).append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"phone\":").append("\""+phone+"\"").append(",");
	     sb.append("\"grade\":").append(grade).append(",");
	     sb.append("\"kind1\":").append(kind1).append(",");
	     sb.append("\"kind2\":").append(kind2).append(",");
	     sb.append("\"kind3\":").append(kind3).append(",");
	     sb.append("\"tutorType\":").append(tutorType).append(",");
	     sb.append("\"tutorMode\":").append(tutorMode).append(",");
	     sb.append("\"title\":").append("\""+title+"\"").append(",");
	     sb.append("\"intro\":").append("\""+intro+"\"").append(",");
	     sb.append("\"city\":").append(city).append(",");
	     sb.append("\"map\":").append("\""+map+"\"").append(",");
	     sb.append("\"price\":").append(price).append(",");
	     sb.append("\"highPrice\":").append(highPrice).append(",");
	     sb.append("\"dateline\":").append(dateline).append(",");
	     sb.append("\"validity\":").append(validity).append(",");
	     sb.append("\"candidates\":").append(candidates).append(",");
	     sb.append("\"views\":").append(views).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
