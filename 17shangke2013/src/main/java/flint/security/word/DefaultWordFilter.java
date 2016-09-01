package flint.security.word;

import java.util.regex.Matcher;

import flint.util.NumberHelper;

public class DefaultWordFilter implements WordFilter {

	/**
	 * 过滤词数据源
	 */
	private WordSource wordSource;

	public WordSource getWordSource() {
		return wordSource;
	}

	public void setWordSource(WordSource wordSource) {
		this.wordSource = wordSource;
	}

	public WordFilterResult doFilter(String content, int level) {
		Matcher matcher = wordSource.getPattern(level).matcher(content);
		matcher.reset();
		boolean result = matcher.find();
		StringBuffer sb = new StringBuffer();
		String denyWords = "";
		while (result) {
			String w = matcher.group();
			denyWords += w + ",";
			matcher.appendReplacement(sb, wordSource.getReplaceWord(w));
			result = matcher.find();
		}
		matcher.appendTail(sb);
		return new WordFilterResult(sb.toString(), denyWords);
	}

	@Override
	public WordFilterResult doFilter(String content, String type) {
		return doFilter(content, NumberHelper.toInt(type));
	}

}
