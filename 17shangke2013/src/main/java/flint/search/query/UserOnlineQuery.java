package flint.search.query;

import org.apache.lucene.search.Query;

/**
 * <pre>
 * 目前暂不实现，使用{@link #flint.search.filter.UserOnlineFilter} 
 * 后期数据量较大时，检索需要提高性能，请实现该方法，修改{@link #flint.search.QueryBuilder}实现方式
 * </pre>
 * 
 * @author Dove Wang
 * 
 */
public class UserOnlineQuery extends Query {

	@Override
	public String toString(String field) {
		return null;
	}

}
