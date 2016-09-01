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

public class BBCodeWordSource implements FactoryBean<WordSource>,
		BeanNameAware, InitializingBean, WordSource {
	public final Logger logger = LoggerFactory.getLogger(getClass());
	private String beanName;
	private DataSource dataSource;
	private Pattern pattern;

	private Map<String, BBWord> replaceWords = new HashMap<String, BBWord>();

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public int getMaxLevel() {
		return 0;
	}

	@Override
	public Pattern getPattern(int level) {
		return pattern;
	}

	public String getReplaceWord(String word) {
		return replaceWords.get(word).getReplace();
	}

	public Map<String, String> getWords() {
		Map<String, String> map = new HashMap<String, String>();
		for (Map.Entry<String, BBWord> w : replaceWords.entrySet()) {
			map.put(w.getKey(), w.getValue().getValue());
		}
		return map;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder w = new StringBuilder();
		try {
			conn = DataSourceUtils.getConnection(dataSource);
			ps = conn
					.prepareStatement("SELECT   `VALUE`,`CODE`,CATEGORY,HOT,MEMO  FROM  TB_BBCODE where `STATUS`=1");
			rs = ps.executeQuery();

			while (rs.next()) {
				String value = rs.getString("VALUE");
				w.append(value.replaceAll("\\[(.+?)\\]", "\\\\[$1\\\\]"))
						.append("|");
				BBWord b = new BBWord();
				b.setName(value);
				b.setReplace("<em><img src=\"" + rs.getString("CODE")
						+ "\" alt=\"" + value + "\" title=\"" + value
						+ "\"/></em>");
				b.setValue(rs.getString("CODE"));
				replaceWords.put(value, b);
			}

		} catch (SQLException e) {
			logger.error("datasource connection error:{}", e);
		} finally {
			DataSourceUtils.releaseConnection(conn, dataSource);
		}
		if (w.length() > 0) {
			pattern = Pattern.compile("(" + w.substring(0, w.length() - 1)
					+ ")");
		}
	}

	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	@Override
	public WordSource getObject() throws Exception {
		return this;
	}

	@Override
	public Class<WordSource> getObjectType() {
		return WordSource.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}

class BBWord {
	private String name;
	private String value;
	private String replace;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getReplace() {
		return replace;
	}

	public void setReplace(String replace) {
		this.replace = replace;
	}
}
