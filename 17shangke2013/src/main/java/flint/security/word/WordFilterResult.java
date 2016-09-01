package flint.security.word;

public class WordFilterResult {

	private String denyWords;

	private String content;

	protected WordFilterResult(String content, String denyWords) {
		this.content = content;
		this.denyWords = denyWords;
	}

	/**
	 * 是否包含敏感词
	 * 
	 * @return
	 */
	public boolean hasDenyWords() {
		return denyWords.length() != 0;
	}

	/**
	 * 返回包含的敏感词
	 * 
	 * @return
	 */
	public String getDenyWords() {
		if (hasDenyWords()) {
			return denyWords.substring(0, denyWords.length() - 1);
		}
		return null;
	}

	/**
	 * 替代敏感词，返回替代后的内容
	 * 
	 * @return
	 */
	public String getReplaceContent() {
		return content;
	}
}
