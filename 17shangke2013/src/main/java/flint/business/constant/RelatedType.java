package flint.business.constant;

public enum RelatedType {
	/**
	 * 课程
	 */
	COURSE,
	/**
	 * 课程章节
	 */
	CHAPTER,
	/**
	 * 问题类型
	 */
	QUESTION;

	public byte byteValue() {
		return (byte) ordinal();
	}
}
