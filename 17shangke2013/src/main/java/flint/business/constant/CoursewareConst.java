package flint.business.constant;

public interface CoursewareConst {
	/* 草稿 */
	final static byte DRAFT = 0;
	/* 正常 */
	final static byte OK = 1;
	/* 已归档的课程 */
	final static byte ARCHIVED = 2;

	final static byte DELETE = 9;
	/**
	 * @see kiss.io.check.FileTyper
	 */
	final static int RESOURCE_TEXT = 0;
	final static int RESOURCE_IMAGE = 1;
	final static int RESOURCE_DOCUMENT = 2;
	final static int RESOURCE_VIDEO = 3;
	final static int RESOURCE_AUDIO = 4;
}
