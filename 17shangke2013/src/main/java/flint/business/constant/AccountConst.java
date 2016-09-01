package flint.business.constant;

public interface AccountConst {

	final static String AUTHORIZE_ONCE_KEY = "authorizeOnceKey";
	
	final static String AUTHORIZE_TOKEN = "authorizeToken";

	final static byte STATUS_OK = 1;

	final static byte STATUS_LOCK = 0;

	final static byte STATUS_EXCEPTION = -1;

	/* 系统金额与现金的兑换比率 */
	final static int RATE = 10;

	/**
	 * 系统赠送点数
	 */
	final static byte GIFT = 0;

	/**
	 * 充值
	 */
	final static byte RECHARGE = 1;
	/**
	 * 取现
	 */
	final static byte DRAW = 2;

	/**
	 * 冻结，通常只交易，如购买课程预付款，解答问题预付款
	 */
	final static byte FRONE = 3;

	/**
	 * 解冻，非付款，通常是指交易取消
	 */
	final static byte UNFRONE = 4;

	/**
	 * 支付交易
	 */
	final static byte PAYMENT = 5;

	/**
	 * 收入
	 */
	final static byte INCOME = 6;

	/* 验证方式 */
	final static byte CHECK_TYPE_PASSWORD = 0;
	/* 短信验证 */
	final static byte CHECK_TYPE_SMS = 1;

	/* 取款方式 */

	/* 充值方式 */
}
