package flint.security.word;

public interface WordFilter {

	/**
	 * 过滤检查相关内容
	 * 
	 * @param content
	 *            检查的内容
	 * @param level
	 * @return 过滤后的结果信息
	 */
	WordFilterResult doFilter(String content, int level);

	/**
	 * 按参数类型过滤，主要提供给页面模板使用
	 * 
	 * @param content
	 * @param type
	 * @return
	 */
	WordFilterResult doFilter(String content, String type);

}
