package flint.security.word;

import java.util.regex.Pattern;

/**
 * 过滤词数据源
 * 
 * @author Dove Wang
 * 
 */
public interface WordSource {

	/**
	 * 默认敏感词最高级别
	 */
	final static int DEFAULT_MAX_LEVEL = 8;

	/**
	 * 获取敏感词的最高级别
	 * 
	 * @return
	 */
	int getMaxLevel();

	/**
	 * 获取需要过滤词的匹配项
	 * 
	 * @param levels
	 *            限制词等级
	 * 
	 *            <pre>
	 *   <ul>
	 *           <li> 0:为最低级别默认给用户名使用，即包含所有级别>=0的敏感词</li>
	 *           <li> 1:一般级别的敏感词，即包含所有级别>=1的敏感词</li>
	 *           <li> 2:按顺序依次类推，直到maxLevel，即包含所有级别>=2的</li>
	 * </ul>
	 * </pre>
	 * @return
	 */
	Pattern getPattern(int level);

	/**
	 * 取得当前词的替代词
	 * 
	 * @param word
	 *            当前词
	 * @return
	 */
	String getReplaceWord(String word);
}
