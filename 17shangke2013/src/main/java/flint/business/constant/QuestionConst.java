package flint.business.constant;

public interface QuestionConst {
	/**
	 * 问题或答案审核中
	 */
	final static byte WAITE_CHECK = 0;

	/* =========问题状态========= */
	/** 有人解答，但没有理想的答案 */
	final static byte QUESTION_STATUS_UNSOLVED = 1;
	/** 问题已解决 ,并有理想的答案 */
	final static byte QUESTION_STATUS_SOLVED = 2;
	/** 问题关闭 */
	final static byte QUESTION_STATUS_CLOSE = 3;
	/** 已删除的问题 */
	final static byte QUESTION_STATUS_DELETE = 9;

	/* 状态,0：未被查看，1：被查看但不是最佳答案：2，查看并被认定为最佳答案 */

	/**
	 * 状态正常
	 */
	final static byte OK = 1;

	/**
	 * 答案被删除
	 */
	final static byte ANSWER_DELETE = 9;

	/* =============================== 答案类型============================ */
	/**
	 * 视频方式
	 */
	final static byte ANSWER_VIDEO = 1;

	/**
	 * 文字方式
	 */
	final static byte ANSWER_TEXT = 2;
	
	/* =============================== 答案状态============================ */
	/** 已删除的答案  **/
	final static byte ANSWER_STATUS_DELETE = 9;
}
