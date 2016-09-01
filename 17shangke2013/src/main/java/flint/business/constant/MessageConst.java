package flint.business.constant;

public interface MessageConst {

	/* ==============消息状态=========== */
	/**
	 * 未读消息
	 */
	final byte MESSAGE_NO_READ = 0;

	/**
	 * 未发送，草稿性
	 */
	final byte MESSAGE_NO_SEND = 0;

	/**
	 * 已读消息
	 */
	final byte MESSAGE_READ = 1;

	final byte MESSAGE_SEND = 1;
	/**
	 * 用户删除消息
	 */
	final byte MESSAGE_SEND_DELETE = 2;

	final byte MESSAGE_RECEIVE_DELETE = 2;

	/* ==========消息类型======== */

	/**
	 * 私人消息
	 */
	final byte PRIVATE = 0;

	/**
	 * 学校消息
	 */
	final byte SCHOOL = 1;
	/**
	 * 系统消息
	 */
	final byte SYSTEM = 2;
	
	
	
	

	/**
	 * 房间创建消息通知
	 */
	final byte ROOM_INVITE = 30;
	
	/**
	 * 问作业
	 */
	final byte ROOM_REPLY = 31;

	/**
	 * 有人解答时的消息
	 */
	final byte QUESRION_ANSWER = 32;

	/**
	 * 实时在线解答，进入教室前的通知
	 */
	final byte QUESRION_ANSWER_INSTANCE = 33;

	/**
	 * 有人查看了您的解答时的消息
	 */
	final byte QUESRION_ANSWER_VIEW = 34;

	/**
	 * 选择了答案
	 */
	final byte QUESRION_ANSWER_SELECT = 35;
	
	/**
	 * 课程通知
	 */
	final byte COURSE = 40;
	/**
	 * 课程更新
	 */
	final byte COURSE_UPDATE = 41;

	/**
	 * 报名课程
	 */
	final byte COURSE_JOIN = 42;

	/**
	 * 提到某人
	 */
	final byte AT = 6;

}
