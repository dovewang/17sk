package flint.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;

import flint.common.Page;
import flint.search.index.Indexer;

public class SearchTemplate extends Indexer {

	/**
	 * 缓存数据分页
	 * 
	 * @param result
	 * @param page
	 * @param size
	 * @return
	 */
	private List getPageResult(List result, int page, int size) {
		if (result.size() == 0) {
			return result;
		}
		List list = new ArrayList();
		for (int i = (page - 1) * size; (i < page * size)
				&& (i < result.size()); i++) {
			list.add(result.get(i));
		}
		return list;
	}

	/**
	 * 全文检索分页查询
	 * 
	 * @param <X>
	 * @param config
	 *            全文检索配置
	 * @param qb
	 *            全文检索查询构建器
	 * @param page
	 *            当前页
	 * @param size
	 *            分页大小
	 * @return
	 * @throws SearchException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public Page queryPage(SearchConfig config, QueryBuilder qb, int page,
			int size) throws SearchException {
		logger.debug("Lucence Query:" + qb.toString());
		Page pageResult = new Page((long) size, (long) page);
		Cache cache = config.getCache();
		CachePage cachePage = null;
		/* 优先从从缓存中读取数据 ,在缓存中存储尽可能少的数据，比如只存储查询数据的结构的主键，其他数据从数据库中读取，提高全文检索的效率 */
		if (cache != null) {
			Element element = cache.get("$" + config.getName() + "-"
					+ qb.getCacheId());
			cachePage = (CachePage) element.getValue();
		} else {
			cachePage = new CachePage();
			IndexSearcher search = null;
			try {
				IndexReader indexReader = getIndexReader(config);
				/* 从缓存中读取索引文件 */
				search = new IndexSearcher(indexReader);

				/* 最多返回的记录数 */
				int maxRecord = Math.min(page * size, search.getIndexReader()
						.maxDoc());
				if (maxRecord < config.getMaxRecord()) {
					maxRecord = config.getMaxRecord();
				}

				TopDocs docs = null;
				if (qb.getSort() != null && qb.getFilter() != null) {
					docs = search.search(qb.getQuery(), qb.getFilter(),
							maxRecord, qb.getSort());
				} else if (qb.getSort() != null) {
					docs = search
							.search(qb.getQuery(), maxRecord, qb.getSort());
				} else if (qb.getFilter() != null) {
					docs = search.search(qb.getQuery(), qb.getFilter(),
							maxRecord);
				} else {
					docs = search.search(qb.getQuery(), maxRecord);
				}
				pageResult.setTotalCount((long) docs.totalHits);// 命中的总记录数;
				cachePage.setTotalCount(docs.totalHits);
				List list = new ArrayList();
				for (int i = 0; (i < maxRecord && i < docs.totalHits); i++) {
					Document doc = search.doc(docs.scoreDocs[i].doc,
							qb.getFieldsToLoad());
					list.add(qb.getRowMapper().map(i, doc));
				}
				cachePage.setResult(list);
				/* 将命中数据载入缓存中 */
				if (cache != null) {
					cache.put(new Element("$" + config.getName() + "-"
							+ qb.getCacheId(), cachePage));
				}
			} catch (CorruptIndexException e) {
				throw new SearchException(e);
			} catch (IOException e) {
				throw new SearchException(e);
			}
		}

		/* 将数据放入分页 */
		pageResult.setTotalCount((long) cachePage.getTotalCount());
		pageResult.setResult(getPageResult(cachePage.getResult(),
				(int) pageResult.getCurPage(), (int) pageResult.getSize()));
		return pageResult;
	}

}
