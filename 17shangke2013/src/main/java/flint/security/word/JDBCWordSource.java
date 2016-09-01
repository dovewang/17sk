package flint.security.word;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.DataSourceUtils;

/**
 * 从数据库读取敏感词，对用户输入的数据进行替换和检查
 * 
 * @author Dove Wang
 * 
 */
public class JDBCWordSource implements FactoryBean<WordSource>, BeanNameAware, InitializingBean, WordSource {

	public final Logger logger = LoggerFactory.getLogger(getClass());

	private DataSource dataSource;

	private int maxLevel = DEFAULT_MAX_LEVEL;

	private String sql = "select word,replace_word from $SENSITIVE_WORDS where level=?";

	/**
	 * 默认不按数据库数据进行替换，直接替换为*号字符
	 */
	private boolean replaceModel = false;

	/**
	 * 当replaceModel==false时设定该值有效
	 */
	private String replaceWord = "*";

	private String beanName;

	/**
	 * 敏感词组，依次按等级存放，存储在内存中
	 */
	private String[] words = null;

	/**
	 * 存储替换词与敏感词对应关系，仅replaceModel==true时有效
	 */
	private Map<String, String> replaceWords = new HashMap<String, String>();

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void afterPropertiesSet() throws Exception {
		logger.debug("loading sensitive word ......!");
		words = new String[maxLevel + 1];
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DataSourceUtils.getConnection(dataSource);
			for (int i = 0; i < maxLevel + 1; i++) {
				ps = conn.prepareStatement(sql);
				ps.setByte(1, (byte) i);
				rs = ps.executeQuery();
				StringBuilder w = new StringBuilder();
				while (rs.next()) {
					w.append(rs.getString("word")).append("|");
					/* 装入对应关系 */
					if (replaceModel) {
						replaceWords.put(rs.getString("word"), rs.getString("replace_word"));
					}
				}
				/* 如果该级别已不存在数据，则不再向更高级别的数据遍历 */
				if (w.length() == 0) {
					maxLevel = i - 1;
					break;
				}
				words[i] = w.substring(0, w.length() - 1);
			}

		} catch (SQLException e) {
			logger.error("datasource connection error:{}", e);
		} finally {
			DataSourceUtils.releaseConnection(conn, dataSource);
		}
		logger.debug("loading sensitive word  success! the max level is {}.", maxLevel);
	}

	public Pattern getPattern(int level) {
		if (level > maxLevel) {
			throw new MaxLevelException(" the filter word level does not exists! ");
		}
		StringBuilder w = new StringBuilder("(");
		for (int index = level;; index++) {
			w.append(words[index]);
			if (index == maxLevel) {
				w.append(")");
				return Pattern.compile(w.toString());
			}
			w.append("|");
		}
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public boolean isReplaceModel() {
		return replaceModel;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setReplaceModel(boolean replaceModel) {
		this.replaceModel = replaceModel;
	}

	public String getReplaceWord(String word) {
		if (!replaceModel) {
			return replaceWord;
		} else {
			String w = replaceWords.get(word);
			if (w == null) {
				w = replaceWord;
			}
			return w;
		}
	}

	public void setReplaceWord(String replaceWord) {
		this.replaceWord = replaceWord;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public WordSource getObject() throws Exception {
		return this;
	}

	public Class<WordSource> getObjectType() {
		return WordSource.class;
	}

	public boolean isSingleton() {
		return true;
	}
}
