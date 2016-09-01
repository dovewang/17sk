package flint.business.constant;

import static kiss.util.Helper.*;

public abstract class CourseConst {
	// 0:未发布1：报名阶段 2：报名结束，3已开课；4：课程取消，5：非正常结束 9：结束

	/* ===========课程状态=============== */
	public final static byte COURSE_CHECK_NOT_PASS = -2;
	/* 未发布 */
	public final static byte COURSE_NOPUBLISH = -1;
	/** 审核中 */
	public final static byte COURSE_CHECK = 0;
	/** 报名阶段 */
	public final static byte COURSE_SIGN = 1;

	/** 报名结束 */
	public final static byte COURSE_SIGN_OVER = 2;
	/**
	 * 已开课
	 */
	public final static byte COURSE_START = 3;
	/**
	 * 课程取消，取消可以用户查看
	 */
	public final static byte COURSE_CANCLE = 4;

	/**
	 * 课程非正常结束
	 */
	public final static byte COURSE_EXCEPTION_OVER = 8;

	/** 课程正常结束 */
	public final static byte COURSE_SUCCESS = 9;

	/**
	 * 课程关闭，课程关闭后用户不能查看
	 */
	public final static byte COURSE_CLOSE = 10;

	public static boolean isOk(byte status) {
		return $(COURSE_SIGN, COURSE_SIGN_OVER, COURSE_START, COURSE_SUCCESS)
				.contains(status);
	}

	public static boolean isViewable(byte status) {
		return status != COURSE_CLOSE;
	}

	/* 课程类型 */

	/**
	 * 在线课程
	 */
	public final static byte COURSE_TYPE_ONLINE = 1;

	/**
	 * 视频课程
	 */
	public final static byte COURSE_TYPE_VIDEO = 2;

	/**
	 * 线下课程，只有学校才能发布
	 */
	public final static byte COURSE_TYPE_LOACL = 3;

	/**
	 * 转发视频
	 */
	public final static byte COURSE_TYPE_FORWARD = 4;

}
