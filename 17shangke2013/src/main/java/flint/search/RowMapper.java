package flint.search;

import org.apache.lucene.document.Document;

public interface RowMapper<T> {
	/**
	 * 将luence查询的结果转为需要的值
	 * 
	 * @param index
	 * @param doc
	 * @return
	 */
	T map(int index, Document doc);
}
