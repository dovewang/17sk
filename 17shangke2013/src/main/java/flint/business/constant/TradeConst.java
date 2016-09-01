package flint.business.constant;

/**
 * 
 * <pre>
 *        问题流程：
 *           查看答案---预付款-----确认付款--评价
 *        课程流程：
 *           1、线上支付：报名----预付款----上课-----付款---评价
 *           2、线下：报名----学校付款---上课---领取回扣--评价
 *       临时课程：
 *         同线上支付
 * </pre>
 * 
 */
public interface TradeConst {

	final static String TRADE_ONCE_KEY = "tradeOnceKey";

	/**
	 * 问题
	 */
	final static byte TYPE_QUESTION = 1;

	/**
	 * 在线课程
	 */
	final static byte TYPE_COURSE_ONLINE = 2;

	/**
	 * 线下课程
	 */
	final static byte TYPE_COURSE_LOCAL = 3;

	/** 临时教室 */
	final static byte TYPE_ROOM_TEMP = 4;

	/** 辅导教室 */
	final static byte TYPE_ROOM_TUTOR = 5;

	/* ==========================交易状态================================ */
	/** 生成订单 */
	final static byte ORDER = 1;

	/** 预付款，确认阶段 */
	final static byte ORDER_CONFIRM = 2;

	/** 上课 */
	final static byte ON_COURSE = 3;

	/** 付款,确认即代表交易成功 */
	final static byte PAYMENY = 9;
	/** 评价完成才算完满 */
	final static byte COMMENT = 10;

	/* 交易超过期限未付款或由于其他原因交易取消而关闭 */
	final static byte CLOSE = -1;

	/* 交易过程发生异常 */
	final static byte EXCEPTION = -2;

	/* 异常申诉过程中 */
	final static byte EXCEPTION_APPLY = -3;

	/* ====支付类型===== */
	/* 在线支付 */
	final static byte PAY_ONLINE = 1;
	/* 线下支付 */
	final static byte PAY_LOCAL = 2;
}
