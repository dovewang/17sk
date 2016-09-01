package flint.business.constant;

public interface GroupConst {

	enum TYPE {
		/**
		 * 班级
		 */
		CLASS,
		/**
		 * 学习圈
		 */
		GROUP
	}

	/** 班级 */
	final static byte CLASS = 0;
	
	/** 圈子 */
	final static byte GROUP = 1;
	
	/** 一般成员 */
	final byte MEMBER = 0;
	/** 群主/班主任 */
	final byte MASTER = 1;
	/** 班长 */
	final byte SQUAD = 2;
	/** 副班长 */
	final byte VICE_SQUAD = 3;
	
	
	/* 班级状态 */
	/*班级删除*/
	final byte GROUP_DELETE = 0;
	/*班级正常*/
	final byte GROUP_OK = 1;
	/*班级解散*/
	final byte GROUP_DISMISS = 2;
	/*班级毕业*/
	final byte GROUP_GRADUATE = 3;
	
	/* 成员状态 */
	final byte MEMBER_OK = 1;
	
	final byte MEMBER_DELETE = 9;

	final byte MEMBER_LOCK = 0;

	/* 验证状态 */
	final byte VERIFY_FALSE = 0;//允许任何人加入
	
	final byte VERIFY_TRUE = 1;//任何人需要验证
	
	final byte VERIFY_ALL_NO = 2;//不允许任何人加入
}
