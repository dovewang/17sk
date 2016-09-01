package flint.business.constant;

public interface UserConst {
	/* ===========用户登录基本信息设置=========== */
	/**
	 * 最多用户级别分类，小学，初中，高中。。。。
	 */
	final int MAX_GRADE = 10;

	/* =============用户登录状态===================== */

	/**
	 * 登录成功
	 */
	final static byte USER_LOGIN_SUCCESS = 1;

	/**
	 * 需要输入验证码
	 */
	final static byte USER_NEED_VERIFYNUM = -1;

	/**
	 * 非法ip,非法用户登录三次
	 */
	final static byte USER_ILLEGL_IP = -2;

	/**
	 * 不存在的用户
	 */
	final static byte USER_NOT_EXIST = -3;

	/**
	 * 冻结状态，三小时以内，禁止登录
	 */
	final static byte USER_FROZEN = -4;

	/**
	 * 密码输入不正确
	 */
	final static byte USER_INCORRENT_PASSWORD = -5;

	/**
	 * 账户未激活，或已删除，或处于忘记密码状态
	 */
	final static byte USER_OTHER_STATE = -6;

	/* =================用户状态==================== */

	final static byte STATUS_NORMAL = 1;

	final static byte STATUS_FREEZE = 2;

	/* ==============前台用户角色================== */
	/** 匿名用户 */
	final static byte ANONYMOUS = 0;
	/** 学生 */
	final static byte STUDENT = 1;
	/** 教师 */
	final static byte TEACHER = 2;
	/** 家长 */
	final static byte CURATOR = 3;
	/** 企业 */
	final static byte ENTERPRISE = 4;
	/** 管理员 */
	final static byte ADMIN = Byte.MAX_VALUE;

	enum UserType {
		/** 游客 */
		ANONYMOUS((byte) 0),
		/** 学生 */
		STUDENT((byte) 1),
		/** 老师 */
		TEACHER((byte) 2),
		/** 家长 */
		CURATOR((byte) 3),
		/** 企业 */
		ENTERPRISE((byte) 4),
		/** 只允许从学校登录，外部不能登录和查看 */
		SCHOOL_ONLY((byte) 126),
		/** 管理员 */
		ADMIN((byte) 127);

		private byte value;

		UserType(byte value) {
			this.value = value;
		}

		public byte byteValue() {
			return value;
		}

		public static String getName(byte value) {
			for (UserType u : UserType.values()) {
				if (u.byteValue() == value) {
					return u.name();
				}
			}
			return null;
		}
	}

	/* ========认证类型========= */

	/* 无认证信息 */
	final static byte ACTIVE_NO = 0;
	/* 邮箱 */
	final static byte ACTIVE_EMAIL = 1;
	/* 手机 */
	final static byte ACTIVE_TEL = 2;
	/* 实名 */
	final static byte ACTIVE_REALNAME = 4;

	final static byte ACTIVE_DEGREE = 8;
}
