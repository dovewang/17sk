package flint.search;

import net.sf.ehcache.Cache;

import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public interface SearchConfig {

	/**
	 * 索引配置名称
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 获取检索目录，不同模块存储到不同的目录来提高建立索引的效率
	 * 
	 * @return
	 */
	Directory getDirectory();

	/**
	 * 内存目录，用来存储实时更新的数据，所以要求模块必须基于单利模式
	 * 
	 * @return
	 */
	Directory getRAMDirectory();

	void setRAMDirectory(RAMDirectory ramDirectory);

	/**
	 * 获取索引建立工具配置
	 * 
	 * @return
	 */
	IndexWriterConfig getIndexWriterConfig();

	/**
	 * 内存索引配置
	 * 
	 * @return
	 */
	IndexWriterConfig getRAMIndexWriterConfig();

	/**
	 * 全文检索查询时获得的最大记录数
	 * 
	 * @param moduleName
	 *            模块名
	 * @return
	 */
	int getMaxRecord();

	/**
	 * 自动建立索引的时间间隔,不同的模块自动添加索引的时间可以不同
	 * 
	 * @return
	 */
	long getAutoIndexInterval();

	/**
	 * 检索池大小
	 * 
	 * @return
	 */
	int getSearchPoolSize();

	/**
	 * 获取数据缓存
	 * 
	 * @return
	 */
	Cache getCache();

	int getBatchIndexSize();
}
