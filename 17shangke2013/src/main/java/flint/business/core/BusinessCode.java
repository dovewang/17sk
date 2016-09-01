package flint.business.core;

import flint.core.ErrorCode;

/**
 * 业务代码定义
 * 
 * @author Dove Wang
 * 
 */
public interface BusinessCode extends ErrorCode {

	/* ========问题模块========== */
	/**
	 * 问题不存在
	 */
	final static int QUESTION_NOT_EXISTS = 30;

	/**
	 * 不允许自问自答
	 */
	final static int QUESTION_ANSEWER_SELF = 31;

	/* =======课程报名模块========== */
	/**
	 * 课程不存在
	 */
	final static int COURSE_NOT_EXISTS = 50;

	/**
	 * 已经报名参加了该课程
	 */
	final static int COURSE_JOIN_EXISTS = 51;

	/**
	 * 用户报名已满
	 */
	final static int COURSE_JOIN_MAX = 52;

	/**
	 * 不能报名自己创建的课程
	 */
	final static int COURSE_JOIN_SELF = 53;

	/**
	 * 课程安排有冲突
	 */
	final static int COURSE_SEC = 54;

	/**
	 * 课程不在报名阶段
	 */
	final static int COURSE__JOIN_STATUS_NOT_SIGN = 55;
	
	/* =======课程修改模块========== */

	/**
	 * 已有用户报名
	 */
	final static int COURSE_HAS_JOINS = 71;

	/* 登录课室 */
	/**
	 * 课程不存在
	 */

	final static int CLASS_NOT_EXISTS = 101;

	/**
	 * 课程已结束
	 */
	final static int CLASS_OVER = 102;

	/**
	 * 课程已取消
	 */
	final static int CLASS_CANCLE = 103;

	/**
	 * 登录教室需要密码
	 */
	final static int CLASS_PASSWORD_NEED = 104;
	/**
	 * 登录教室密码错误
	 */
	final static int CLASS_PASSWORD_ERROR = 105;

	/**
	 * 用户不存在这个教室
	 */
	final static int CLASS_USER_INVALID = 106;

	/**
	 * 同一用户登录同一教室
	 */
	final static int CLASS_USER_REPEAT = 107;

	/**
	 * 课程还没开始，根据定义参考开课开始时间
	 */
	final static int CLASS_NOT_START = 108;

}
