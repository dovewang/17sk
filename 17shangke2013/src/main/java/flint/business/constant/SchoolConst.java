package flint.business.constant;

public interface SchoolConst {

	/* 用户在学校的身份 */

	final byte TYPE_STUDENT = 1;

	final byte TYPE_TEACHER = 2;
	
	final byte TYPE_ADMIN = 127;
	
	/* 学校状态*/
	
	final static byte SCHOOL_STATUS = 1;
	
	/**
	 * 未审核
	 */
	final static byte SCHOOL_VERIFY_NO = 0;
	
	/**
	 * 冻结状态
	 */
	final static byte SCHOOL_FROZEN = -1;
	
	/**
	 * 审核不通过
	 */
	final static byte SCHOOL_VERIFY_NOT_PASS = -4;

}
