package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_ACCOUNT", primary = { "USER_ID" })
public class Account implements  Entity{

	/**
	*用户编号，代表唯一的账户编号
	*/
	private long   userId;

	/**
	*账户密码
	*/
	private String   password;

	/**
	*账户安全级别，默认为0，账户密码和网站密码一致
	*/
	private byte   safety;

	/**
	*账户验证方式，默认为0、密码验证，1、短信验证，2密码和短信验证
	*/
	private byte   checkType;

	/**
	*验证号，如使用手机验证
	*/
	private String   code;

	/**
	*本站账户余额
	*/
	private int   balance;

	/**
	*冻结金额
	*/
	private int   frozen;

	/**
	*可用余额
	*/
	private int   available;

	/**
	*账户状态
	*/
	private byte   status;

	/**
	*账户创建时间
	*/
	private int   createTime;

	/**
	*账户积分
	*/
	private int   scores;

	/**
	*账户性质，个人，企业
	*/
	private byte   type;

	/**
	*账户安全提问
	*/
	private String   question;

	/**
	*账户安全答案
	*/
	private String   answer;

	/**
	*备注
	*/
	private String   memo;

   
    public Account(){}
    
    public Account(long userId )
    {
           this.userId=userId;
     }
	/**
	 * @param long   用户编号，代表唯一的账户编号
	 */
	public void setUserId(long userId)
	{
	 	this.userId=userId;
	}
	
	/**
	 * @return long  用户编号，代表唯一的账户编号
	 */
	public long getUserId()
	{
	 	return this.userId;
	}
	/**
	 * @param String   账户密码
	 */
	public void setPassword(String password)
	{
	 	this.password=password;
	}
	
	/**
	 * @return String  账户密码
	 */
	public String getPassword()
	{
	 	return this.password;
	}
	/**
	 * @param byte   账户安全级别，默认为0，账户密码和网站密码一致
	 */
	public void setSafety(byte safety)
	{
	 	this.safety=safety;
	}
	
	/**
	 * @return byte  账户安全级别，默认为0，账户密码和网站密码一致
	 */
	public byte getSafety()
	{
	 	return this.safety;
	}
	/**
	 * @param byte   账户验证方式，默认为0、密码验证，1、短信验证，2密码和短信验证
	 */
	public void setCheckType(byte checkType)
	{
	 	this.checkType=checkType;
	}
	
	/**
	 * @return byte  账户验证方式，默认为0、密码验证，1、短信验证，2密码和短信验证
	 */
	public byte getCheckType()
	{
	 	return this.checkType;
	}
	/**
	 * @param String   验证号，如使用手机验证
	 */
	public void setCode(String code)
	{
	 	this.code=code;
	}
	
	/**
	 * @return String  验证号，如使用手机验证
	 */
	public String getCode()
	{
	 	return this.code;
	}
	/**
	 * @param int   本站账户余额
	 */
	public void setBalance(int balance)
	{
	 	this.balance=balance;
	}
	
	/**
	 * @return int  本站账户余额
	 */
	public int getBalance()
	{
	 	return this.balance;
	}
	/**
	 * @param int   冻结金额
	 */
	public void setFrozen(int frozen)
	{
	 	this.frozen=frozen;
	}
	
	/**
	 * @return int  冻结金额
	 */
	public int getFrozen()
	{
	 	return this.frozen;
	}
	/**
	 * @param int   可用余额
	 */
	public void setAvailable(int available)
	{
	 	this.available=available;
	}
	
	/**
	 * @return int  可用余额
	 */
	public int getAvailable()
	{
	 	return this.available;
	}
	/**
	 * @param byte   账户状态
	 */
	public void setStatus(byte status)
	{
	 	this.status=status;
	}
	
	/**
	 * @return byte  账户状态
	 */
	public byte getStatus()
	{
	 	return this.status;
	}
	/**
	 * @param int   账户创建时间
	 */
	public void setCreateTime(int createTime)
	{
	 	this.createTime=createTime;
	}
	
	/**
	 * @return int  账户创建时间
	 */
	public int getCreateTime()
	{
	 	return this.createTime;
	}
	/**
	 * @param int   账户积分
	 */
	public void setScores(int scores)
	{
	 	this.scores=scores;
	}
	
	/**
	 * @return int  账户积分
	 */
	public int getScores()
	{
	 	return this.scores;
	}
	/**
	 * @param byte   账户性质，个人，企业
	 */
	public void setType(byte type)
	{
	 	this.type=type;
	}
	
	/**
	 * @return byte  账户性质，个人，企业
	 */
	public byte getType()
	{
	 	return this.type;
	}
	/**
	 * @param String   账户安全提问
	 */
	public void setQuestion(String question)
	{
	 	this.question=question;
	}
	
	/**
	 * @return String  账户安全提问
	 */
	public String getQuestion()
	{
	 	return this.question;
	}
	/**
	 * @param String   账户安全答案
	 */
	public void setAnswer(String answer)
	{
	 	this.answer=answer;
	}
	
	/**
	 * @return String  账户安全答案
	 */
	public String getAnswer()
	{
	 	return this.answer;
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
	    Map<String, Object> map=new HashMap<String, Object>((int)15);
	    map.put("userId",userId);
	    map.put("password",password);
	    map.put("safety",safety);
	    map.put("checkType",checkType);
	    map.put("code",code);
	    map.put("balance",balance);
	    map.put("frozen",frozen);
	    map.put("available",available);
	    map.put("status",status);
	    map.put("createTime",createTime);
	    map.put("scores",scores);
	    map.put("type",type);
	    map.put("question",question);
	    map.put("answer",answer);
	    map.put("memo",memo);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {userId};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          Account  entity=(Account)o;
          return userId==entity.userId;
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
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"password\":").append("\""+password+"\"").append(",");
	     sb.append("\"safety\":").append(safety).append(",");
	     sb.append("\"checkType\":").append(checkType).append(",");
	     sb.append("\"code\":").append("\""+code+"\"").append(",");
	     sb.append("\"balance\":").append(balance).append(",");
	     sb.append("\"frozen\":").append(frozen).append(",");
	     sb.append("\"available\":").append(available).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"createTime\":").append(createTime).append(",");
	     sb.append("\"scores\":").append(scores).append(",");
	     sb.append("\"type\":").append(type).append(",");
	     sb.append("\"question\":").append("\""+question+"\"").append(",");
	     sb.append("\"answer\":").append("\""+answer+"\"").append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
