package flint.search;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import net.sf.ehcache.Cache;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.OpenBitSet;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

import flint.common.Page;

public class SearchSupport implements SearchConfig {

	/**
	 * 目录索引配置
	 */
	private IndexWriterConfig indexWriterConfig;

	/**
	 * 内存索引配置
	 */
	private IndexWriterConfig ramIndexWriterConfig;
	/**
	 * 查询返回的最多结果记录数目
	 */
	private int maxRecord = 100;

	/**
	 * 索引及数据文件存储的目录
	 */
	private Directory directory;

	/**
	 * 自动更新索引的时间间隔
	 */
	private long autoIndexInterval;

	/**
	 * 检索池大小
	 */
	private int searchPoolSize;

	/**
	 * 全文检索模板
	 */
	private SearchTemplate searchTemplate;

	private DataSource dataSource;

	public final Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 数据缓存
	 */
	private Cache Cache;

	private Directory ramDirectory;

	private OpenBitSet dels;

	public SearchSupport() {
		ramDirectory = new RAMDirectory();
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getName() {
		return getClass().getName();
	}

	public int getMaxRecord() {
		return maxRecord;
	}

	public void setMaxRecord(int maxRecord) {
		this.maxRecord = maxRecord;
	}

	public Directory getDirectory() {
		return directory;
	}

	public void setDirectory(Directory directory) {
		this.directory = directory;
	}

	public long getAutoIndexInterval() {
		return autoIndexInterval;
	}

	public void setAutoIndexInterval(long autoIndexInterval) {
		this.autoIndexInterval = autoIndexInterval;
	}

	public SearchTemplate getSearchTemplate() {
		return searchTemplate;
	}

	public void setSearchTemplate(SearchTemplate searchTemplate) {
		this.searchTemplate = searchTemplate;
		if (indexWriterConfig == null) {
			indexWriterConfig = new IndexWriterConfig(Version.LUCENE_41,
					searchTemplate.getAnalyzer());
		}
	}

	public int getSearchPoolSize() {
		return searchPoolSize;
	}

	public void setSearchPoolSize(int searchPoolSize) {
		this.searchPoolSize = searchPoolSize;
	}

	public IndexWriterConfig getIndexWriterConfig() {
		return indexWriterConfig;
	}

	public Cache getCache() {
		return Cache;
	}

	public void setCache(Cache cache) {
		Cache = cache;
	}

	@Override
	public void setRAMDirectory(RAMDirectory ramDirectory) {
		this.ramDirectory = ramDirectory;
	}

	@Override
	public Directory getRAMDirectory() {
		return ramDirectory;
	}

	@Override
	public IndexWriterConfig getRAMIndexWriterConfig() {
		return new IndexWriterConfig(Version.LUCENE_41,
				searchTemplate.getAnalyzer());
	}

	/**
	 * 提交更新索引
	 */
	public void commit() {
		searchTemplate.commit(this);
	}

	/**
	 * 将内存的索引和目录索引进行合并
	 */
	public void mergeIndex() {
		searchTemplate.mergeIndex(this);
	}

	public void add(Collection<Document> documents) throws SearchException {
		searchTemplate.add(this, documents);
	}

	public Page queryPage(QueryBuilder qb, int page, int size)
			throws SearchException {
		return searchTemplate.queryPage(this, qb, page, size);
	}

	/**
	 * 索引重建
	 * 
	 * @param sql
	 * @param parameters
	 */
	public void rebuild(String sql, Object... parameters) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DataSourceUtils.getConnection(dataSource);
			ps = conn.prepareStatement(sql);
			int i = 1;
			for (Object p : parameters) {
				ps.setObject(i, p);
				i++;
			}
			rs = ps.executeQuery();
			List<Document> ls = new ArrayList<Document>();
			while (rs.next()) {
				ls.add(mapperDocument(rs));
				if ((ls.size() + 1) % getBatchIndexSize() == 0) {
					add(ls);
					ls = new ArrayList<Document>();
				}
			}

			/* 处理最后的索引 */
			if (ls.size() > 0) {
				add(ls);
			}
			commit();
		} catch (SQLException e) {
			logger.error("datasource connection error:{}", e);
		} finally {
			DataSourceUtils.releaseConnection(conn, dataSource);
		}
	}

	public Document mapperDocument(ResultSet rs) throws SearchException,
			SQLException {
		return null;
	}

	@Override
	public int getBatchIndexSize() {
		return 50;
	}

	public void destory() {
		mergeIndex();
		logger.debug("destory lucene.......!");

	}

	public void delete(Term... terms) {
		searchTemplate.delete(this, terms);
	}

}
