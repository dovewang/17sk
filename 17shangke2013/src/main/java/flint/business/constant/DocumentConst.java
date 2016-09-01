package flint.business.constant;

public abstract class DocumentConst {
	public final String SCOPE_ALL = "all";
	public final String SCOPE_SCHOOL = "school";
	public final String SCOPE_GROUP = "group";
	/* 默认只允许上传图片 */
	public final String ALLOW_DOC_TYPE_ALL = "";
	public final String ALLOW_DOC_TYPE_SCHOOL = "";
	public final String ALLOW_DOC_TYPE_GROUP = null;
	public final String ALLOW_DOC_TYPE_COURSE = "\\.(gif|jpg|jpeg|bmp|png|tif|ppt|pptx)$";
	public final String ALLOW_DOC_TYPE_QUESTION = "\\.(gif|jpg|jpeg|bmp|png|tif|ppt|pptx)$";

}
