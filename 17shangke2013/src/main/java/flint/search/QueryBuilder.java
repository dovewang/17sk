package flint.search;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeFilter;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.Version;

import flint.util.SpringHelper;

public class QueryBuilder {
	/**
	 * 字段排序
	 */
	public static enum Direction {
		ASC, DESC
	}

	/**
	 * 检索条件
	 */
	private BooleanQuery booleanQuery;

	/** 排序字段 */
	private SortField[] sortField = new SortField[8];
	private int sortCount = 0;
	private Sort sort = null;

	private Analyzer analyzer = SpringHelper.getBean(Analyzer.class);
	/** 数据过滤 */
	private Filter filter;

	/** 数据缓存ID */
	private String cacheId;

	/** 字段选择器,默认只选择id字段 */
	private Set<String> fieldsToLoad;

	private RowMapper rowMapper;

	/** 文档建立的主键 */
	private final static String KEY = "id";

	public QueryBuilder() {
		this.booleanQuery = new BooleanQuery();
		/* 默认只选择主键，其他数据从数据库读取 */
		fieldsToLoad = new HashSet<String>();
		fieldsToLoad.add(KEY);
		/* 默认只映射主键的值 */
		rowMapper = new RowMapper<String>() {
			public String map(int index, Document doc) {
				return doc.get(KEY);
			}
		};
	}

	public String getCacheId() {
		return cacheId;
	}

	public void setCacheId(String cacheId) {
		this.cacheId = cacheId;
	}

	/**
	 * 全文检索字段
	 * 
	 * @param field
	 *            字段名
	 * @param query
	 *            英文不区分大小写，全部转为小写 关键字
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public QueryBuilder orFullText(String field, String query)
			throws ParseException {
		return fullText(field, query, BooleanClause.Occur.SHOULD);
	}

	/**
	 * 全文检索字段
	 * 
	 * @param field
	 *            字段名
	 * @param query
	 *            关键字
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public QueryBuilder andFullText(String field, String query)
			throws ParseException {
		return fullText(field, query, BooleanClause.Occur.MUST);
	}

	/**
	 * 多字段检索统一关键字,其中之一满足即可
	 * 
	 * @param query
	 * @param field
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public QueryBuilder andFullText(String query, String[] fields)
			throws ParseException {
		Occur[] flags = new Occur[fields.length];
		for (int i = 0; i < flags.length; i++) {
			flags[i] = Occur.SHOULD;
		}
		addQuery(MultiFieldQueryParser.parse(Version.LUCENE_36, query, fields,
				flags, analyzer), BooleanClause.Occur.MUST);
		return this;
	}

	/**
	 * 全文检索字段
	 * 
	 * @param field
	 *            字段名称
	 * @param query
	 *            关键字
	 * @param occur
	 *            <pre>
	 *            BooleanClause.Occur.MUST表示and
	 *            BooleanClause.Occur.MUST_NOT表示not
	 *            BooleanClause.Occur.SHOULD表示or
	 * </pre>
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public QueryBuilder fullText(String field, String query,
			BooleanClause.Occur occur) throws ParseException {
		QueryParser qp = new QueryParser(Version.LUCENE_41, field, analyzer);
		// qp.setDefaultOperator(QueryParser.AND_OPERATOR);
		Query q = qp.parse(query);
		addQuery(q, occur);
		return this;
	}

	/**
	 * 检索的字段值与检索值相等
	 * 
	 * @param field
	 *            字段名称
	 * @param query
	 *            检索值
	 * @return
	 * @throws IOException
	 */
	public QueryBuilder orEq(String field, String query) {
		return eq(field, query, BooleanClause.Occur.SHOULD);
	}

	/**
	 * 检索的字段值与检索值相等
	 * 
	 * @param field
	 *            字段名称
	 * @param query
	 *            检索值
	 * @return
	 * @throws IOException
	 */
	public QueryBuilder andEq(String field, String query) {
		return eq(field, query, BooleanClause.Occur.MUST);
	}

	/**
	 * 检索的字段值与检索值相等
	 * 
	 * @param field
	 *            字段名称
	 * @param query
	 *            检索值
	 * @param occur
	 *            <pre>
	 *            BooleanClause.Occur.MUST表示and
	 *            BooleanClause.Occur.MUST_NOT表示not
	 *            BooleanClause.Occur.SHOULD表示or
	 * </pre>
	 * @return
	 * @throws IOException
	 */
	public QueryBuilder eq(String field, String query, BooleanClause.Occur occur) {
		WildcardQuery q = new WildcardQuery(new Term(field, query));
		addQuery(q, occur);
		return this;
	}

	/**
	 * 检索的字段值不等于检索值
	 * 
	 * @param field
	 *            字段名称
	 * @param query
	 *            检索值
	 * @return
	 */
	public QueryBuilder orNeq(String field, String query) {
		return neq(field, query, BooleanClause.Occur.SHOULD);
	}

	/**
	 * 检索的字段值不等于检索值
	 * 
	 * @param field
	 *            字段名称
	 * @param query
	 *            检索值
	 * @return
	 */
	public QueryBuilder andNeq(String field, String query) {
		return neq(field, query, BooleanClause.Occur.MUST_NOT);
	}

	/**
	 * 检索的字段值不等于检索值
	 * 
	 * @param field
	 *            字段名称
	 * @param query
	 *            检索值
	 * @param occur
	 *            <pre>
	 *            BooleanClause.Occur.MUST表示and
	 *            BooleanClause.Occur.MUST_NOT表示not
	 *            BooleanClause.Occur.SHOULD表示or
	 * </pre>
	 * @return
	 */
	public QueryBuilder neq(String field, String query,
			BooleanClause.Occur occur) {
		addQuery(new TermQuery(new Term(field, query)), occur);
		return this;
	}

	/**
	 * 检索字段值大于检索值，只适用于数字类型的数据
	 * 
	 * @param field
	 *            字段名称
	 * @param query
	 *            检索值
	 * @return
	 */
	public QueryBuilder andGt(String field, Number query) {
		return gt(field, query, BooleanClause.Occur.MUST);
	}

	/**
	 * 检索字段值大于检索值，只适用于数字类型的数据
	 * 
	 * @param field
	 *            字段名称
	 * @param query
	 *            检索值
	 * @return
	 */
	public QueryBuilder orGt(String field, Number query) {
		return gt(field, query, BooleanClause.Occur.SHOULD);
	}

	/**
	 * 检索字段值不大于检索值，只适用于数字类型的数据
	 * 
	 * @param field
	 *            字段名称
	 * @param query
	 *            检索值
	 * @return
	 */
	public QueryBuilder andNgt(String field, Number query) {
		return ngt(field, query, BooleanClause.Occur.MUST_NOT);
	}

	/**
	 * 检索字段值不大于检索值，只适用于数字类型的数据
	 * 
	 * @param field
	 *            字段名称
	 * @param query
	 *            检索值
	 * @return
	 */
	public QueryBuilder orNgt(String field, Number query) {
		return ngt(field, query, BooleanClause.Occur.SHOULD);
	}

	/**
	 * 检索字段值不大于检索值，只适用于数字类型的数据
	 * 
	 * @param field
	 *            字段名称
	 * @param query
	 *            检索值
	 * @param occur
	 *            <pre>
	 *            BooleanClause.Occur.MUST表示and
	 *            BooleanClause.Occur.MUST_NOT表示not
	 *            BooleanClause.Occur.SHOULD表示or
	 * </pre>
	 * @return
	 */
	public QueryBuilder ngt(String field, Number query,
			BooleanClause.Occur occur) {
		return between(field, Long.MIN_VALUE, query.longValue(), occur);
	}

	/**
	 * 检索字段值小于检索值，只适用于数字类型的数据
	 * 
	 * @param field
	 *            字段名称
	 * @param query
	 *            检索值
	 * @return
	 */
	public QueryBuilder andLt(String field, Number query) {
		return lt(field, query, BooleanClause.Occur.MUST);
	}

	/**
	 * 检索字段值小于检索值，只适用于数字类型的数据
	 * 
	 * @param field
	 *            字段名称
	 * @param query
	 *            检索值
	 * @return
	 */
	public QueryBuilder orLt(String field, Number query) {
		return lt(field, query, BooleanClause.Occur.SHOULD);
	}

	/**
	 * 检索字段值小于检索值，只适用于数字类型的数据
	 * 
	 * @param field
	 *            字段名称
	 * @param query
	 *            检索值
	 * @param occur
	 *            <pre>
	 *            BooleanClause.Occur.MUST表示and
	 *            BooleanClause.Occur.MUST_NOT表示not
	 *            BooleanClause.Occur.SHOULD表示or
	 * </pre>
	 * @return
	 */
	public QueryBuilder lt(String field, Number query, BooleanClause.Occur occur) {
		return range(field, String.valueOf(Long.MIN_VALUE), query.toString(),
				occur);
	}

	/**
	 * 检索字段值不小于检索值，只适用于数字类型的数据
	 * 
	 * @param field
	 *            字段名称
	 * @param query
	 *            检索值
	 * @return
	 */
	public QueryBuilder andNlt(String field, Number query) {
		return nlt(field, query, BooleanClause.Occur.MUST_NOT);
	}

	/**
	 * 检索字段值不小于检索值，只适用于数字类型的数据
	 * 
	 * @param field
	 *            字段名称
	 * @param query
	 *            检索值
	 * @return
	 */
	public QueryBuilder orNlt(String field, Number query) {
		return nlt(field, query, BooleanClause.Occur.SHOULD);
	}

	/**
	 * 检索字段值不小于检索值，只适用于数字类型的数据
	 * 
	 * @param field
	 *            字段名称
	 * @param query
	 *            检索值
	 * @param occur
	 *            <pre>
	 *            BooleanClause.Occur.MUST表示and
	 *            BooleanClause.Occur.MUST_NOT表示not
	 *            BooleanClause.Occur.SHOULD表示or
	 * </pre>
	 * @return
	 */
	public QueryBuilder nlt(String field, Number query,
			BooleanClause.Occur occur) {
		return between(field, query.longValue(), Long.MAX_VALUE, occur);
	}

	/**
	 * 检索的字段值前缀与检索值相同
	 * 
	 * @param field
	 *            字段
	 * @param query
	 *            检索值
	 * @return 当前查询创建器
	 */
	public QueryBuilder andLlike(String field, String query) {
		return llike(field, query, BooleanClause.Occur.MUST);
	}

	/**
	 * 检索字段值大于检索值，只适用于数字类型的数据
	 * 
	 * @param field
	 *            字段名称
	 * @param query
	 *            检索值
	 * @param occur
	 *            <pre>
	 *            BooleanClause.Occur.MUST表示and
	 *            BooleanClause.Occur.MUST_NOT表示not
	 *            BooleanClause.Occur.SHOULD表示or
	 * </pre>
	 * @return
	 */
	public QueryBuilder gt(String field, Number query, BooleanClause.Occur occur) {
		return range(field, query.toString(), String.valueOf(Long.MAX_VALUE),
				occur);
	}

	/**
	 * 检索的字段值前缀与检索值相同
	 * 
	 * @param field
	 *            字段
	 * @param query
	 *            检索值
	 * @return 当前查询创建器
	 */
	public QueryBuilder orLlike(String field, String query) {
		return llike(field, query, BooleanClause.Occur.SHOULD);
	}

	/**
	 * 检索的字段值前缀与检索值相同
	 * 
	 * @param field
	 *            字段
	 * @param query
	 *            检索值
	 * @param occur
	 *            <pre>
	 *            BooleanClause.Occur.MUST表示and
	 *            BooleanClause.Occur.MUST_NOT表示not
	 *            BooleanClause.Occur.SHOULD表示or
	 * </pre>
	 * @return 当前查询创建器
	 */
	public QueryBuilder llike(String field, String query,
			BooleanClause.Occur occur) {
		PrefixQuery q = new PrefixQuery(new Term(field, query));
		addQuery(q, BooleanClause.Occur.SHOULD);
		return this;
	}

	/**
	 * 检索字段的后缀与检索值相同
	 * 
	 * @param field
	 *            字段
	 * @param query
	 *            检索值
	 * @return 当前查询创建器
	 */
	public QueryBuilder andRlike(String field, String query) {
		return rlike(field, query, BooleanClause.Occur.MUST);
	}

	/**
	 * 检索字段的后缀与检索值相同
	 * 
	 * @param field
	 *            字段
	 * @param query
	 *            检索值
	 * @return 当前查询创建器
	 */
	public QueryBuilder orRlike(String field, String query,
			BooleanClause.Occur occur) {
		return rlike(field, query, BooleanClause.Occur.SHOULD);
	}

	/**
	 * 检索字段的后缀与检索值相同
	 * 
	 * @param field
	 *            字段
	 * @param query
	 *            检索值
	 * @param occur
	 *            <pre>
	 *            BooleanClause.Occur.MUST表示and
	 *            BooleanClause.Occur.MUST_NOT表示not
	 *            BooleanClause.Occur.SHOULD表示or
	 * </pre>
	 * @return 当前查询创建器
	 */
	public QueryBuilder rlike(String field, String query,
			BooleanClause.Occur occur) {
		WildcardQuery q = new WildcardQuery(new Term(field, "*" + query));
		addQuery(q, occur);
		return this;
	}

	/**
	 * 检索字段值中包含检索值
	 * 
	 * @param field
	 *            字段
	 * @param query
	 *            检索值
	 * @return 当前查询创建器
	 */
	public QueryBuilder andLike(String field, String query) {
		return like(field, query, BooleanClause.Occur.MUST);
	}

	/**
	 * 检索字段值中包含检索值
	 * 
	 * @param field
	 *            字段
	 * @param query
	 *            检索值
	 * @return 当前查询创建器
	 */
	public QueryBuilder orLike(String field, String query) {
		return like(field, query, BooleanClause.Occur.SHOULD);
	}

	/**
	 * 检索字段值中包含检索值
	 * 
	 * @param field
	 *            字段
	 * @param query
	 *            检索值
	 * @param occur
	 *            <pre>
	 *            BooleanClause.Occur.MUST表示and
	 *            BooleanClause.Occur.MUST_NOT表示not
	 *            BooleanClause.Occur.SHOULD表示or
	 * </pre>
	 * @return 当前查询创建器
	 */
	public QueryBuilder like(String field, String query,
			BooleanClause.Occur occur) {
		WildcardQuery q = new WildcardQuery(new Term(field, "*" + query + "*"));
		addQuery(q, occur);
		return this;
	}

	/**
	 * 范围检索，不包含边界值
	 * 
	 * @param field
	 *            字段
	 * @param lowerTerm
	 *            最小值
	 * @param upperTerm
	 *            最大值
	 * @return
	 */
	public QueryBuilder andRange(String field, String lowerTerm,
			String upperTerm) {
		return range(field, lowerTerm, upperTerm, BooleanClause.Occur.MUST);
	}

	/**
	 * 范围检索，不包含边界值
	 * 
	 * @param field
	 *            字段
	 * @param lowerTerm
	 *            最小值
	 * @param upperTerm
	 *            最大值
	 * @return
	 */
	public QueryBuilder orRange(String field, String lowerTerm, String upperTerm) {
		return range(field, lowerTerm, upperTerm, BooleanClause.Occur.SHOULD);
	}

	/**
	 * 范围检索，不包含边界值
	 * 
	 * @param field
	 *            字段
	 * @param lowerTerm
	 *            最小值
	 * @param upperTerm
	 *            最大值
	 * @param occur
	 *            <pre>
	 *            BooleanClause.Occur.MUST表示and
	 *            BooleanClause.Occur.MUST_NOT表示not
	 *            BooleanClause.Occur.SHOULD表示or
	 * </pre>
	 * @return
	 */
	public QueryBuilder range(String field, String lowerTerm, String upperTerm,
			BooleanClause.Occur occur) {
		addQuery(TermRangeQuery.newStringRange(field, lowerTerm, upperTerm,
				false, false), occur);
		return this;
	}

	/**
	 * 范围检索，包含边界值
	 * 
	 * @param field
	 *            字段
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @return
	 */
	public QueryBuilder andBetween(String field, long min, long max) {
		return between(field, min, max, BooleanClause.Occur.MUST);
	}

	/**
	 * 范围检索，包含边界值
	 * 
	 * @param field
	 *            字段
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @return
	 */
	public QueryBuilder orBetween(String field, long min, long max) {
		return between(field, min, max, BooleanClause.Occur.SHOULD);
	}

	/**
	 * 范围检索，包含边界值
	 * 
	 * @param field
	 *            字段
	 * @param lowerTerm
	 *            最小值
	 * @param upperTerm
	 *            最大值
	 * @param occur
	 *            <pre>
	 *            BooleanClause.Occur.MUST表示and
	 *            BooleanClause.Occur.MUST_NOT表示not
	 *            BooleanClause.Occur.SHOULD表示or
	 * </pre>
	 * @return
	 */
	public QueryBuilder between(String field, long min, long max,
			BooleanClause.Occur occur) {
		addQuery(NumericRangeQuery.newLongRange(field, min, max, true, true),
				occur);
		return this;
	}

	/**
	 * in查询
	 * 
	 * @param field
	 *            字段
	 * @param values
	 *            集合值
	 * @return
	 */
	public QueryBuilder andIn(String field, String... values) {
		BooleanQuery in = new BooleanQuery();
		for (String v : values) {
			WildcardQuery q = new WildcardQuery(new Term(field, v));
			in.add(q, BooleanClause.Occur.SHOULD);
		}
		this.booleanQuery.add(in, BooleanClause.Occur.MUST);
		return this;
	}

	/**
	 * 添加一个查询
	 * 
	 * @param query
	 * @param occur
	 * @return
	 */
	public QueryBuilder addQuery(Query query, BooleanClause.Occur occur) {
		this.booleanQuery.add(query, occur);
		return this;
	}

	/**
	 * 设置数据过滤
	 * 
	 * @param fliter
	 * @return
	 */
	public QueryBuilder setFilter(Filter fliter) {
		this.filter = fliter;
		return this;
	}

	/**
	 * 设置数据过滤范围，主要用于日期,不包含边界值
	 * 
	 * @param field
	 *            字段
	 * @param start
	 *            开始
	 * @param end
	 *            结束
	 * @return
	 */
	public QueryBuilder setRangeFilter(String field, String start, String end) {
		return setFilter(TermRangeFilter.newStringRange(field, start, end,
				false, false));
	}

	/**
	 * 设置数据过滤范围，主要用于日期,包含边界值
	 * 
	 * @param field
	 *            字段
	 * @param start
	 *            开始
	 * @param end
	 *            结束
	 * @return
	 */
	public QueryBuilder setBetweenFilter(String field, String start, String end) {
		return setFilter(TermRangeFilter.newStringRange(field, start, end,
				true, true));
	}

	/**
	 * 返回查询器
	 * 
	 * @return
	 */
	public Query getQuery() {
		return this.booleanQuery;
	}

	/**
	 * 返回数据过滤器
	 * 
	 * @return
	 */
	public Filter getFilter() {
		return filter;
	}

	/**
	 * 添加一个排序字段
	 * 
	 * @param field
	 * @return
	 */
	public QueryBuilder addSortField(SortField field) {
		/* 扩容 */
		if (sortCount == sortField.length) {
			SortField[] copy = new SortField[sortCount * 2];
			System.arraycopy(sortField, 0, copy, 0, sortCount);
			sortField = copy;
		}
		sortField[sortCount++] = field;
		return this;
	}

	/**
	 * 添加排序字段
	 * 
	 * @param field
	 *            字段名
	 * @param type
	 *            字段类型
	 * @param direction
	 *            方向，默认升序：QueryBuilder.Direction.ASC
	 * @return
	 */
	public QueryBuilder addSortField(String field, Type type,
			QueryBuilder.Direction direction) {
		addSortField(new SortField(field, type,
				direction.equals(QueryBuilder.Direction.DESC)));
		return this;
	}

	/**
	 * 获取排序字段，外部包不允许访问
	 * 
	 * @return
	 */
	protected Sort getSort() {
		/* sort复制之后就不再重新取值 */
		if (sort == null && sortCount > 0) {
			this.addSortField(SortField.FIELD_SCORE);
			sortField = Arrays.copyOf(sortField, sortCount);
			sort = new Sort(sortField);
		}
		return sort;
	}

	/**
	 * 返回检索语句
	 */
	public String toString() {
		String queryString = this.booleanQuery.toString();
		if (filter != null) {
			queryString += "  @filter " + filter;
		}

		if (getSort() != null) {
			queryString += "  @order  " + getSort().toString();
		}

		return queryString;
	}

	public void setFieldSelector(Set<String> fieldsToLoad) {
		this.fieldsToLoad = fieldsToLoad;
	}

	public Set<String> getFieldsToLoad() {
		return fieldsToLoad;
	}

	public RowMapper getRowMapper() {
		return rowMapper;
	}

	public void setRowMapper(RowMapper rowMapper) {
		this.rowMapper = rowMapper;
	}
}
