package flint.business.constant;

public interface ClassroomConst {

	/**
	 * 教师
	 */
	final static String ROLE_TEACHER = "TEACHER";

	/**
	 * 学生
	 */
	final static String ROLE_STUDENT = "STUDENT";

	/* ==============教室类型============== */
	/**
	 * 临时教室
	 */
	final static byte ROOM_TEMP = 0;
	/**
	 * 问题教室
	 */
	final static byte ROOM_QUESTION = 1;
	/**
	 * 课程教室
	 */
	final static byte ROOM_COURSE = 2;

	/**
	 * 我来休息
	 */
	final static byte ROOM_SHOW = 3;

	/**
	 * 辅导教室
	 */
	final static byte ROOM_TUTOR = 4;

	/* =============用户登入教室过程状态======================== */

	/** 报名状态正常，但是并没有开始上课，用户报名课程即计入该状态 */
	final static byte JOIN_OK = 1;

	/**
	 * 正常状态，正在上课
	 */
	final static byte USER_OK = 2;

	/**
	 * 被老师踢出教室
	 */
	final static byte USER_KICKED = 3;
	/**
	 * 正常退出
	 */
	final static byte USER_OUT = 4;
	/**
	 * 异常退出
	 */
	final static byte USER_EXCEPTION = 5;

	/**
	 * 用户上课没有来报到，这种情况系统自动退费
	 */
	final static byte USER_NOT_COME = 6;

	/* =======登录前检查============ */
	/**
	 * 登录房间需要用户密码
	 */
	final static int USER_PASSWORD_NEED = -1;

	/**
	 * 用户密码错误
	 */
	final static int USER_PASSWORD_ERROR = -2;

	/**
	 * 用户不存在
	 */
	final static int USER_NOT_EXISTS = -3;

	/** 用户已登录 */
	final static int USER_HAS_LOGIN = -4;

	/** 用户已满 */
	final static int CAPACITY_MAX = -5;

	/* 用户身份不符 */
	final static int USER_TOKEN_ERROR = -6;
	/* =====================教室状态=================== */

	/**
	 * 课程已结束
	 */
	final static byte CLASS_OVER = 0;

	/**
	 * 状态正常
	 */
	final static byte CLASS_OK = 1;

	/**
	 * 教室不存在
	 */
	final static byte CLASS_NOT_EXISTS = 2;

	/**
	 * 课程取消
	 */
	final static byte CLASS_CANCLE = 3;

}
