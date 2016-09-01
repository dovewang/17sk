package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_USER", primary = { "USER_ID" })
public class User implements  Entity{

	/**
	*用户ID
	*/
	private long   userId;

	/**
	*用户昵称，唯一
	*/
	private String   name;

	/**
	*用户真实姓名（验证代号4）
	*/
	private String   realName;

	/**
	*邮箱（验证代号1），登录使用
	*/
	private String   email;

	/**
	*用户密码，SHA加密
	*/
	private String   password;

	/**
	*用户状态：0、未激活；1、正常；2、冻结；3、删除；4，证书过期
	*/
	private byte   status;

	/**
	*参考角色表，公共网站的角色，和学校网站角色不同，1代表学生，2代表老师，4代表家长，127代表公共网站系统管理员
	*/
	private long   userType;

	/**
	*用户类型基础下细分角色时使用
	*/
	private String   role;

	/**
	*用户域名
	*/
	private String   domain;

	/**
	*VIP等级
	*/
	private int   vip;

	/**
	*学生年级
	*/
	private int   grade;

	/**
	*注册时间
	*/
	private int   regiditTime;

	/**
	*头像
	*/
	private String   face;

	/**
	*0，女，1男，2保密
	*/
	private byte   sex;

	/**
	*手机（验证代号2）
	*/
	private String   tel;

	/**
	*学位（验证代号8）
	*/
	private String   degree;

	/**
	*激活状态（使用二进制和计算，如1,2,4,8）
	*/
	private byte   active;

	/**
	*激活代码，发送到邮箱的代码，较长
	*/
	private String   activeCode;

	/**
	*短信验证码，一般是数字，较短
	*/
	private String   smsActiveCode;

	/**
	*验证码产生时间
	*/
	private int   codeTime;

	/**
	*最后登录IP地址
	*/
	private String   netAddress;

	/**
	*最后登录时间
	*/
	private int   lastTime;

	/**
	*连续登录失败次数
	*/
	private int   logins;

	/**
	*教师等级，教师信誉
	*/
	private byte   teacherLevel;

	/**
	*学生等级，学生信誉
	*/
	private byte   studentLevel;

	/**
	*教师经验
	*/
	private int   teacherExperience;

	/**
	*学生经验
	*/
	private int   studentExperience;

	/**
	*积分
	*/
	private int   scores;

	/**
	*平均收费
	*/
	private int   fee;

	/**
	*学生总数
	*/
	private int   students;

	/**
	*回答问题总数
	*/
	private int   answers;

	/**
	*解决问题
	*/
	private int   correctAnswers;

	/**
	*创建课程总数
	*/
	private int   courses;

	/**
	*成功开展的课程
	*/
	private int   successCourse;

	/**
	*精品课程
	*/
	private int   goodCourses;

	/**
	*好评率
	*/
	private byte   favor;

	/**
	*在线时长，默认存储分钟
	*/
	private int   onlineTime;

	/**
	*特长，以逗号分割，在
	*/
	private String   expert;

	/**
	*我的关注
	*/
	private String   focus;

	/**
	*经历经验，个人简介
	*/
	private String   experience;

	/**
	*是否在线
	*/
	private int   online;

	/**
	*所在的城市
	*/
	private int   city;

	/**
	*cookie，存储用户登录信息
	*/
	private String   cookie;

	/**
	*备注
	*/
	private String   memo;

	/**
	*
	*/
	private int   credits;

   
    public User(){}
    
    public User(long userId )
    {
           this.userId=userId;
     }
	/**
	 * @param long   用户ID
	 */
	public void setUserId(long userId)
	{
	 	this.userId=userId;
	}
	
	/**
	 * @return long  用户ID
	 */
	public long getUserId()
	{
	 	return this.userId;
	}
	/**
	 * @param String   用户昵称，唯一
	 */
	public void setName(String name)
	{
	 	this.name=name;
	}
	
	/**
	 * @return String  用户昵称，唯一
	 */
	public String getName()
	{
	 	return this.name;
	}
	/**
	 * @param String   用户真实姓名（验证代号4）
	 */
	public void setRealName(String realName)
	{
	 	this.realName=realName;
	}
	
	/**
	 * @return String  用户真实姓名（验证代号4）
	 */
	public String getRealName()
	{
	 	return this.realName;
	}
	/**
	 * @param String   邮箱（验证代号1），登录使用
	 */
	public void setEmail(String email)
	{
	 	this.email=email;
	}
	
	/**
	 * @return String  邮箱（验证代号1），登录使用
	 */
	public String getEmail()
	{
	 	return this.email;
	}
	/**
	 * @param String   用户密码，SHA加密
	 */
	public void setPassword(String password)
	{
	 	this.password=password;
	}
	
	/**
	 * @return String  用户密码，SHA加密
	 */
	public String getPassword()
	{
	 	return this.password;
	}
	/**
	 * @param byte   用户状态：0、未激活；1、正常；2、冻结；3、删除；4，证书过期
	 */
	public void setStatus(byte status)
	{
	 	this.status=status;
	}
	
	/**
	 * @return byte  用户状态：0、未激活；1、正常；2、冻结；3、删除；4，证书过期
	 */
	public byte getStatus()
	{
	 	return this.status;
	}
	/**
	 * @param long   参考角色表，公共网站的角色，和学校网站角色不同，1代表学生，2代表老师，4代表家长，127代表公共网站系统管理员
	 */
	public void setUserType(long userType)
	{
	 	this.userType=userType;
	}
	
	/**
	 * @return long  参考角色表，公共网站的角色，和学校网站角色不同，1代表学生，2代表老师，4代表家长，127代表公共网站系统管理员
	 */
	public long getUserType()
	{
	 	return this.userType;
	}
	/**
	 * @param String   用户类型基础下细分角色时使用
	 */
	public void setRole(String role)
	{
	 	this.role=role;
	}
	
	/**
	 * @return String  用户类型基础下细分角色时使用
	 */
	public String getRole()
	{
	 	return this.role;
	}
	/**
	 * @param String   用户域名
	 */
	public void setDomain(String domain)
	{
	 	this.domain=domain;
	}
	
	/**
	 * @return String  用户域名
	 */
	public String getDomain()
	{
	 	return this.domain;
	}
	/**
	 * @param int   VIP等级
	 */
	public void setVip(int vip)
	{
	 	this.vip=vip;
	}
	
	/**
	 * @return int  VIP等级
	 */
	public int getVip()
	{
	 	return this.vip;
	}
	/**
	 * @param int   学生年级
	 */
	public void setGrade(int grade)
	{
	 	this.grade=grade;
	}
	
	/**
	 * @return int  学生年级
	 */
	public int getGrade()
	{
	 	return this.grade;
	}
	/**
	 * @param int   注册时间
	 */
	public void setRegiditTime(int regiditTime)
	{
	 	this.regiditTime=regiditTime;
	}
	
	/**
	 * @return int  注册时间
	 */
	public int getRegiditTime()
	{
	 	return this.regiditTime;
	}
	/**
	 * @param String   头像
	 */
	public void setFace(String face)
	{
	 	this.face=face;
	}
	
	/**
	 * @return String  头像
	 */
	public String getFace()
	{
	 	return this.face;
	}
	/**
	 * @param byte   0，女，1男，2保密
	 */
	public void setSex(byte sex)
	{
	 	this.sex=sex;
	}
	
	/**
	 * @return byte  0，女，1男，2保密
	 */
	public byte getSex()
	{
	 	return this.sex;
	}
	/**
	 * @param String   手机（验证代号2）
	 */
	public void setTel(String tel)
	{
	 	this.tel=tel;
	}
	
	/**
	 * @return String  手机（验证代号2）
	 */
	public String getTel()
	{
	 	return this.tel;
	}
	/**
	 * @param String   学位（验证代号8）
	 */
	public void setDegree(String degree)
	{
	 	this.degree=degree;
	}
	
	/**
	 * @return String  学位（验证代号8）
	 */
	public String getDegree()
	{
	 	return this.degree;
	}
	/**
	 * @param byte   激活状态（使用二进制和计算，如1,2,4,8）
	 */
	public void setActive(byte active)
	{
	 	this.active=active;
	}
	
	/**
	 * @return byte  激活状态（使用二进制和计算，如1,2,4,8）
	 */
	public byte getActive()
	{
	 	return this.active;
	}
	/**
	 * @param String   激活代码，发送到邮箱的代码，较长
	 */
	public void setActiveCode(String activeCode)
	{
	 	this.activeCode=activeCode;
	}
	
	/**
	 * @return String  激活代码，发送到邮箱的代码，较长
	 */
	public String getActiveCode()
	{
	 	return this.activeCode;
	}
	/**
	 * @param String   短信验证码，一般是数字，较短
	 */
	public void setSmsActiveCode(String smsActiveCode)
	{
	 	this.smsActiveCode=smsActiveCode;
	}
	
	/**
	 * @return String  短信验证码，一般是数字，较短
	 */
	public String getSmsActiveCode()
	{
	 	return this.smsActiveCode;
	}
	/**
	 * @param int   验证码产生时间
	 */
	public void setCodeTime(int codeTime)
	{
	 	this.codeTime=codeTime;
	}
	
	/**
	 * @return int  验证码产生时间
	 */
	public int getCodeTime()
	{
	 	return this.codeTime;
	}
	/**
	 * @param String   最后登录IP地址
	 */
	public void setNetAddress(String netAddress)
	{
	 	this.netAddress=netAddress;
	}
	
	/**
	 * @return String  最后登录IP地址
	 */
	public String getNetAddress()
	{
	 	return this.netAddress;
	}
	/**
	 * @param int   最后登录时间
	 */
	public void setLastTime(int lastTime)
	{
	 	this.lastTime=lastTime;
	}
	
	/**
	 * @return int  最后登录时间
	 */
	public int getLastTime()
	{
	 	return this.lastTime;
	}
	/**
	 * @param int   连续登录失败次数
	 */
	public void setLogins(int logins)
	{
	 	this.logins=logins;
	}
	
	/**
	 * @return int  连续登录失败次数
	 */
	public int getLogins()
	{
	 	return this.logins;
	}
	/**
	 * @param byte   教师等级，教师信誉
	 */
	public void setTeacherLevel(byte teacherLevel)
	{
	 	this.teacherLevel=teacherLevel;
	}
	
	/**
	 * @return byte  教师等级，教师信誉
	 */
	public byte getTeacherLevel()
	{
	 	return this.teacherLevel;
	}
	/**
	 * @param byte   学生等级，学生信誉
	 */
	public void setStudentLevel(byte studentLevel)
	{
	 	this.studentLevel=studentLevel;
	}
	
	/**
	 * @return byte  学生等级，学生信誉
	 */
	public byte getStudentLevel()
	{
	 	return this.studentLevel;
	}
	/**
	 * @param int   教师经验
	 */
	public void setTeacherExperience(int teacherExperience)
	{
	 	this.teacherExperience=teacherExperience;
	}
	
	/**
	 * @return int  教师经验
	 */
	public int getTeacherExperience()
	{
	 	return this.teacherExperience;
	}
	/**
	 * @param int   学生经验
	 */
	public void setStudentExperience(int studentExperience)
	{
	 	this.studentExperience=studentExperience;
	}
	
	/**
	 * @return int  学生经验
	 */
	public int getStudentExperience()
	{
	 	return this.studentExperience;
	}
	/**
	 * @param int   积分
	 */
	public void setScores(int scores)
	{
	 	this.scores=scores;
	}
	
	/**
	 * @return int  积分
	 */
	public int getScores()
	{
	 	return this.scores;
	}
	/**
	 * @param int   平均收费
	 */
	public void setFee(int fee)
	{
	 	this.fee=fee;
	}
	
	/**
	 * @return int  平均收费
	 */
	public int getFee()
	{
	 	return this.fee;
	}
	/**
	 * @param int   学生总数
	 */
	public void setStudents(int students)
	{
	 	this.students=students;
	}
	
	/**
	 * @return int  学生总数
	 */
	public int getStudents()
	{
	 	return this.students;
	}
	/**
	 * @param int   回答问题总数
	 */
	public void setAnswers(int answers)
	{
	 	this.answers=answers;
	}
	
	/**
	 * @return int  回答问题总数
	 */
	public int getAnswers()
	{
	 	return this.answers;
	}
	/**
	 * @param int   解决问题
	 */
	public void setCorrectAnswers(int correctAnswers)
	{
	 	this.correctAnswers=correctAnswers;
	}
	
	/**
	 * @return int  解决问题
	 */
	public int getCorrectAnswers()
	{
	 	return this.correctAnswers;
	}
	/**
	 * @param int   创建课程总数
	 */
	public void setCourses(int courses)
	{
	 	this.courses=courses;
	}
	
	/**
	 * @return int  创建课程总数
	 */
	public int getCourses()
	{
	 	return this.courses;
	}
	/**
	 * @param int   成功开展的课程
	 */
	public void setSuccessCourse(int successCourse)
	{
	 	this.successCourse=successCourse;
	}
	
	/**
	 * @return int  成功开展的课程
	 */
	public int getSuccessCourse()
	{
	 	return this.successCourse;
	}
	/**
	 * @param int   精品课程
	 */
	public void setGoodCourses(int goodCourses)
	{
	 	this.goodCourses=goodCourses;
	}
	
	/**
	 * @return int  精品课程
	 */
	public int getGoodCourses()
	{
	 	return this.goodCourses;
	}
	/**
	 * @param byte   好评率
	 */
	public void setFavor(byte favor)
	{
	 	this.favor=favor;
	}
	
	/**
	 * @return byte  好评率
	 */
	public byte getFavor()
	{
	 	return this.favor;
	}
	/**
	 * @param int   在线时长，默认存储分钟
	 */
	public void setOnlineTime(int onlineTime)
	{
	 	this.onlineTime=onlineTime;
	}
	
	/**
	 * @return int  在线时长，默认存储分钟
	 */
	public int getOnlineTime()
	{
	 	return this.onlineTime;
	}
	/**
	 * @param String   特长，以逗号分割，在
	 */
	public void setExpert(String expert)
	{
	 	this.expert=expert;
	}
	
	/**
	 * @return String  特长，以逗号分割，在
	 */
	public String getExpert()
	{
	 	return this.expert;
	}
	/**
	 * @param String   我的关注
	 */
	public void setFocus(String focus)
	{
	 	this.focus=focus;
	}
	
	/**
	 * @return String  我的关注
	 */
	public String getFocus()
	{
	 	return this.focus;
	}
	/**
	 * @param String   经历经验，个人简介
	 */
	public void setExperience(String experience)
	{
	 	this.experience=experience;
	}
	
	/**
	 * @return String  经历经验，个人简介
	 */
	public String getExperience()
	{
	 	return this.experience;
	}
	/**
	 * @param boolean   是否在线
	 */
	public void setOnline(int online)
	{
	 	this.online=online;
	}
	
	/**
	 * @return boolean  是否在线
	 */
	public int getOnline()
	{
	 	return this.online;
	}
	/**
	 * @param int   所在的城市
	 */
	public void setCity(int city)
	{
	 	this.city=city;
	}
	
	/**
	 * @return int  所在的城市
	 */
	public int getCity()
	{
	 	return this.city;
	}
	/**
	 * @param String   cookie，存储用户登录信息
	 */
	public void setCookie(String cookie)
	{
	 	this.cookie=cookie;
	}
	
	/**
	 * @return String  cookie，存储用户登录信息
	 */
	public String getCookie()
	{
	 	return this.cookie;
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
	public void setCredits(int credits)
	{
	 	this.credits=credits;
	}
	
	/**
	 * @return int  
	 */
	public int getCredits()
	{
	 	return this.credits;
	}

   @Override
	public Map<String, Object> toMap() {
	    Map<String, Object> map=new HashMap<String, Object>((int)45);
	    map.put("userId",this.userId);
	    map.put("name",this.name);
	    map.put("realName",this.realName);
	    map.put("email",this.email);
	    map.put("password",this.password);
	    map.put("status",this.status);
	    map.put("userType",this.userType);
	    map.put("role",this.role);
	    map.put("domain",this.domain);
	    map.put("vip",this.vip);
	    map.put("grade",this.grade);
	    map.put("regiditTime",this.regiditTime);
	    map.put("face",this.face);
	    map.put("sex",this.sex);
	    map.put("tel",this.tel);
	    map.put("degree",this.degree);
	    map.put("active",this.active);
	    map.put("activeCode",this.activeCode);
	    map.put("smsActiveCode",this.smsActiveCode);
	    map.put("codeTime",this.codeTime);
	    map.put("netAddress",this.netAddress);
	    map.put("lastTime",this.lastTime);
	    map.put("logins",this.logins);
	    map.put("teacherLevel",this.teacherLevel);
	    map.put("studentLevel",this.studentLevel);
	    map.put("teacherExperience",this.teacherExperience);
	    map.put("studentExperience",this.studentExperience);
	    map.put("scores",this.scores);
	    map.put("fee",this.fee);
	    map.put("students",this.students);
	    map.put("answers",this.answers);
	    map.put("correctAnswers",this.correctAnswers);
	    map.put("courses",this.courses);
	    map.put("successCourse",this.successCourse);
	    map.put("goodCourses",this.goodCourses);
	    map.put("favor",this.favor);
	    map.put("onlineTime",this.onlineTime);
	    map.put("expert",this.expert);
	    map.put("focus",this.focus);
	    map.put("experience",this.experience);
	    map.put("online",this.online);
	    map.put("city",this.city);
	    map.put("cookie",this.cookie);
	    map.put("memo",this.memo);
	    map.put("credits",this.credits);
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
          User  entity=(User)o;
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
	     sb.append("\"name\":").append("\""+name+"\"").append(",");
	     sb.append("\"realName\":").append("\""+realName+"\"").append(",");
	     sb.append("\"email\":").append("\""+email+"\"").append(",");
	     sb.append("\"password\":").append("\""+password+"\"").append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"userType\":").append(userType).append(",");
	     sb.append("\"role\":").append("\""+role+"\"").append(",");
	     sb.append("\"domain\":").append("\""+domain+"\"").append(",");
	     sb.append("\"vip\":").append(vip).append(",");
	     sb.append("\"grade\":").append(grade).append(",");
	     sb.append("\"regiditTime\":").append(regiditTime).append(",");
	     sb.append("\"face\":").append("\""+face+"\"").append(",");
	     sb.append("\"sex\":").append(sex).append(",");
	     sb.append("\"tel\":").append("\""+tel+"\"").append(",");
	     sb.append("\"degree\":").append("\""+degree+"\"").append(",");
	     sb.append("\"active\":").append(active).append(",");
	     sb.append("\"activeCode\":").append("\""+activeCode+"\"").append(",");
	     sb.append("\"smsActiveCode\":").append("\""+smsActiveCode+"\"").append(",");
	     sb.append("\"codeTime\":").append(codeTime).append(",");
	     sb.append("\"netAddress\":").append("\""+netAddress+"\"").append(",");
	     sb.append("\"lastTime\":").append(lastTime).append(",");
	     sb.append("\"logins\":").append(logins).append(",");
	     sb.append("\"teacherLevel\":").append(teacherLevel).append(",");
	     sb.append("\"studentLevel\":").append(studentLevel).append(",");
	     sb.append("\"teacherExperience\":").append(teacherExperience).append(",");
	     sb.append("\"studentExperience\":").append(studentExperience).append(",");
	     sb.append("\"scores\":").append(scores).append(",");
	     sb.append("\"fee\":").append(fee).append(",");
	     sb.append("\"students\":").append(students).append(",");
	     sb.append("\"answers\":").append(answers).append(",");
	     sb.append("\"correctAnswers\":").append(correctAnswers).append(",");
	     sb.append("\"courses\":").append(courses).append(",");
	     sb.append("\"successCourse\":").append(successCourse).append(",");
	     sb.append("\"goodCourses\":").append(goodCourses).append(",");
	     sb.append("\"favor\":").append(favor).append(",");
	     sb.append("\"onlineTime\":").append(onlineTime).append(",");
	     sb.append("\"expert\":").append("\""+expert+"\"").append(",");
	     sb.append("\"focus\":").append("\""+focus+"\"").append(",");
	     sb.append("\"experience\":").append("\""+experience+"\"").append(",");
	     sb.append("\"online\":").append(online).append(",");
	     sb.append("\"city\":").append(city).append(",");
	     sb.append("\"cookie\":").append("\""+cookie+"\"").append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"").append(",");
	     sb.append("\"credits\":").append(credits);
	    sb.append("}");
	    return sb.toString();
	}
}
