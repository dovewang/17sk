package flint.search.index;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import flint.search.SearchConfig;
import flint.search.SearchException;
import flint.search.SearchTemplate;

public class Indexer {
	public final static Logger logger = LoggerFactory
			.getLogger(SearchTemplate.class);

	/**
	 * 无效索引标记，仅限内存中不存在索引时，创建索引片段，添加的无效标记索引
	 */
	public final static String INVALID_INDEX = "INVALID_INDEX";

	/**
	 * 目录索引
	 * */
	private Map<String, IndexWriter> indexWriterPool = new ConcurrentHashMap<String, IndexWriter>();

	/**
	 * 读取索引文件工具池
	 */
	private Map<String, DirectoryReader> directoryReaderPool = new ConcurrentHashMap<String, DirectoryReader>();

	/**
	 * 分词分析工具
	 */
	private Analyzer analyzer;

	/**
	 * 索引配置
	 */
	private IndexWriterConfig indexWriterConfig;

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	/**
	 * 全文检索分析器
	 * 
	 * @return
	 */
	public Analyzer getAnalyzer() {
		return analyzer;
	}

	/*
	 * ==========================================================================
	 * 
	 * 
	 * 创建索引基础方法
	 * 
	 * 
	 * ==========================================================================
	 */

	/**
	 * 从池中获取目录模块对应的索引创建工具
	 * 
	 * @param config
	 * @return
	 * @throws LockObtainFailedException
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	private IndexWriter getIndexWriter(SearchConfig config)
			throws CorruptIndexException, LockObtainFailedException,
			IOException {
		if (!indexWriterPool.containsKey(config.getName())) {
			synchronized (indexWriterPool) {
				indexWriterPool.put(
						config.getName(),
						new IndexWriter(config.getDirectory(), config
								.getIndexWriterConfig()));
			}
		}
		IndexWriter w = indexWriterPool.get(config.getName());
		logger.debug(" get indexWriter from pool:{} " + w);
		return w;
	}

	/**
	 * 从内存中读取索引,每次用完后必须关闭，否则会产生内存锁，导致索引无法读取
	 * 
	 * @param config
	 * @return
	 * @throws CorruptIndexException
	 * @throws LockObtainFailedException
	 * @throws IOException
	 */
	public IndexWriter getRAMIndexWriter(SearchConfig config)
			throws CorruptIndexException, LockObtainFailedException,
			IOException {
		IndexWriter w = new IndexWriter(config.getRAMDirectory(),
				config.getRAMIndexWriterConfig());
		logger.debug(" create new  indexRAMWriter :{} " + w);
		return w;

	}

	/**
	 * 关闭单个索引创建工具
	 * 
	 * @param config
	 * @throws SearchException
	 */
	public void closeIndexWriter(SearchConfig config) throws SearchException {
		try {
			closeIndexWriter(getIndexWriter(config));
			indexWriterPool.remove(config.getName());
		} catch (CorruptIndexException e) {
			throw new SearchException(e);
		} catch (IOException e) {
			throw new SearchException(e);
		}
	}

	/**
	 * 关闭创建索引工具
	 * 
	 * @param writer
	 * @throws SearchException
	 * @throws IOException
	 * @throws CorruptIndexException
	 */
	private void closeIndexWriter(IndexWriter writer)
			throws CorruptIndexException, IOException {
		if (writer != null) {
			writer.close();
		}
	}

	/**
	 * 关闭池中所有的索引创建工具
	 * 
	 * @throws SearchException
	 */
	public void closeIndexWriter() throws SearchException {
		SearchException last = null;
		for (Map.Entry<String, IndexWriter> entry : indexWriterPool.entrySet()) {
			try {
				closeIndexWriter(entry.getValue());
				indexWriterPool.remove(entry.getKey());
			} catch (CorruptIndexException e) {
				last = new SearchException(e);
			} catch (IOException e) {
				last = new SearchException(e);
			}
		}
		if (last != null) {
			throw last;
		}
	}

	/**
	 * 提交保存单个索引
	 * 
	 * @param config
	 * @throws SearchException
	 */
	public void commit(SearchConfig config) throws SearchException {
		try {
			getIndexWriter(config).commit();
		} catch (CorruptIndexException e) {
			throw new SearchException(e);
		} catch (IOException e) {
			throw new SearchException(e);
		}
	}

	/**
	 * 提交保存索引
	 * 
	 * @throws SearchException
	 */
	public void commit() throws SearchException {
		SearchException last = null;
		for (Map.Entry<String, IndexWriter> entry : indexWriterPool.entrySet()) {
			try {
				entry.getValue().commit();
			} catch (CorruptIndexException e) {
				last = new SearchException(e);
			} catch (IOException e) {
				last = new SearchException(e);
			}
		}
		if (last != null) {
			throw last;
		}
	}

	/*
	 * ==========================================================================
	 * 
	 * 
	 * 读取索引方法
	 * 
	 * 
	 * ==========================================================================
	 */

	/**
	 * 获取一个索引读取工具,这里建立两个索引目录，一个是内存索引，一个是目录索引，当内存数据达到一定量时进行数据合并
	 */
	public MultiReader getIndexReader(SearchConfig config)
			throws CorruptIndexException, IOException {
		/* 内存目录 */
		Directory ramDirectory = config.getRAMDirectory();
		/* 判断内存中是否存在索引，如果不存在则添加一条无效索引，阻止抛出索引找不到的异常 */
		if (ramDirectory.listAll().length == 0) {
			Document doc = new Document();
			doc.add(new StringField("id", INVALID_INDEX, Field.Store.NO));
			createRAMIndex(config, doc);
			logger.debug(" the  RAMDirectory has no index,create one invalid  index success!");
		}
		synchronized (directoryReaderPool) {
			if (!directoryReaderPool.containsKey(config.getName())) {
				directoryReaderPool.put(config.getName(),
						DirectoryReader.open(config.getDirectory()));
			}

			/**
			 * 检查索引文件是否发生变化
			 */
			DirectoryReader dirReader = directoryReaderPool.get(config
					.getName());
			if (!dirReader.isCurrent()) {
				dirReader = DirectoryReader.openIfChanged(dirReader);
				directoryReaderPool.put(config.getName(), dirReader);
				logger.debug(" the  DirIndexReader has change,reload index success!");
			}
			DirectoryReader ramIndexReader = DirectoryReader.open(config
					.getRAMDirectory());
			/* 构建新的reader */
			return new MultiReader(ramIndexReader, dirReader);
		}
	}

	/**
	 * 
	 * @param indexReader
	 * @throws IOException
	 */
	private void closeIndexReader(DirectoryReader indexReader)
			throws IOException {
		if (indexReader != null) {
			indexReader.close();
		}
	}

	/**
	 * 关闭池中所有的索引读取工具
	 * 
	 * @throws SearchException
	 */
	public void closeIndexReader() throws SearchException {
		SearchException last = null;
		for (Map.Entry<String, DirectoryReader> entry : directoryReaderPool
				.entrySet()) {
			try {
				closeIndexReader(entry.getValue());
				directoryReaderPool.remove(entry.getKey());
			} catch (IOException e) {
				last = new SearchException(e);
			}
		}
		if (last != null) {
			throw last;
		}
	}

	/**
	 * 合并索引
	 * 
	 * @throws IOException
	 * @throws LockObtainFailedException
	 * @throws CorruptIndexException
	 */
	public void mergeIndex(SearchConfig config) {
		try {
			Directory ramDirectory = config.getRAMDirectory();
			if (ramDirectory.listAll().length != 0) {
				IndexWriter indexWriter = getIndexWriter(config);
				indexWriter.addIndexes(config.getRAMDirectory());
				/* 就的内存再使用完后会自动释放,分配新的内存，如果在这段时间检索可能检索不到即将合并的内存数据 */
				indexWriter.commit();
				config.setRAMDirectory(new RAMDirectory());
				logger.debug(" merge  memory index into directory index success!");
			} else {
				logger.debug("memory has no index,do nothing!");
			}
		} catch (IOException e) {
			logger.error("merge index error:{}", e);
			throw new SearchException(e);
		}
	}

	/*
	 * ==========================================================================
	 * 
	 * 
	 * 索引方法封装
	 * 
	 * 
	 * ==========================================================================
	 */

	public void createRAMIndex(SearchConfig config, Document... documents)
			throws SearchException {
		try {
			IndexWriter ramWriter = getRAMIndexWriter(config);
			for (Document doc : documents) {
				ramWriter.addDocument(doc);
			}
			ramWriter.close();
		} catch (CorruptIndexException e) {
			logger.error(e.getMessage(), e);
			throw new SearchException(e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new SearchException(e);
		}

	}

	/**
	 * 更新内存索引，同时删除文件索引
	 * 
	 * @param config
	 * @param term
	 * @param document
	 * @throws SearchException
	 */
	public void add(SearchConfig config, Collection<Document> documents)
			throws SearchException {
		try {
			IndexWriter ramWriter = getRAMIndexWriter(config);
			IndexWriter writer = getIndexWriter(config);
			for (Document doc : documents) {
				/* 删除内存索引 */
				ramWriter.deleteDocuments(new Term("id", doc.get("id")));
				/* 删除文件索引 */
				writer.deleteDocuments(new Term("id", doc.get("id")));
				ramWriter.addDocument(doc);
			}
			writer.commit();
			ramWriter.commit();
			ramWriter.close();
			logger.info("update index success!");
		} catch (CorruptIndexException e) {
			logger.error(e.getMessage(), e);
			throw new SearchException(e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new SearchException(e);
		}

	}

	/**
	 * 按字段删除索引
	 * 
	 * @param config
	 * @param terms
	 * @throws SearchException
	 */
	public void delete(SearchConfig config, Term... terms)
			throws SearchException {
		try {
			IndexWriter ramWriter = getRAMIndexWriter(config);
			IndexWriter writer = getIndexWriter(config);
			writer.deleteDocuments(terms);
			ramWriter.deleteDocuments(terms);
			ramWriter.commit();
			ramWriter.close();
			writer.commit();
			logger.debug("delete index success!");
		} catch (CorruptIndexException e) {
			throw new SearchException(e);
		} catch (IOException e) {
			throw new SearchException(e);
		}
	}

	/**
	 * 按查询的条件删除索引
	 * 
	 * @param config
	 * @param queries
	 * @throws SearchException
	 */
	public void delete(SearchConfig config, Query... queries)
			throws SearchException {
		try {
			IndexWriter ramWriter = getRAMIndexWriter(config);
			IndexWriter writer = getIndexWriter(config);
			writer.deleteDocuments(queries);
			ramWriter.deleteDocuments(queries);
			ramWriter.commit();
			ramWriter.close();
		} catch (CorruptIndexException e) {
			throw new SearchException(e);
		} catch (IOException e) {
			throw new SearchException(e);
		}
	}

}
