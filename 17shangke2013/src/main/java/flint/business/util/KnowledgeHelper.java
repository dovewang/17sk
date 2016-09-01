package flint.business.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Element;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;
import net.sf.ehcache.search.expression.And;
import entity.Knowledge;
import flint.context.ContextCache;
import flint.jdbc.RowSetHandler;

/**
 * 知识点
 * 
 * @author Dove Wang
 * 
 */
public class KnowledgeHelper extends ContextCache<KnowledgeHelper> {

	@Override
	public void afterPropertiesSet() throws Exception {
		reload(-1, -1);
	}

	public void reload(long kind, long parentId) {
		String sql = " SELECT   KIND,GRADE,PARENT_KNOWLEDGE_ID,KNOWLEDGE_ID,KNOWLEDGE FROM  TB_KNOWLEDGE where 1=1 ";
		if (kind != -1) {
			sql += " and kind=" + kind;
		}
		if (parentId != -1) {
			sql += " and PARENT_KNOWLEDGE_ID=" + parentId;
		}
		getDao().query(sql, new RowSetHandler<Object>() {
			@Override
			public Object populate(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Knowledge knowledge = new Knowledge();
					knowledge.setKind(rs.getLong(1));
					knowledge.setGrade(rs.getInt(2));
					knowledge.setParentKnowledgeId(rs.getLong(3));
					knowledge.setKnowledgeId(rs.getLong(4));
					knowledge.setKnowledge(rs.getString(5));
					getCache().put(new Element(knowledge.getKnowledgeId(), knowledge));
				}
				return null;
			}
		});
		logger.debug("加载系统知识点完成！");
	}

	public List<Knowledge> getKnowledge(int grade, long kind, long parentId) {
		List<Knowledge> list = new ArrayList<Knowledge>();
		Attribute<Integer> gradeAttribute = getCache().getSearchAttribute("grade");
		Attribute<Long> kindAttribute = getCache().getSearchAttribute("kind");
		Attribute<Long> pAttribute = getCache().getSearchAttribute("parentKownledgeId");
		Query query = getCache().createQuery();
		query.includeValues();
		query.addCriteria(new And(gradeAttribute.eq(grade), kindAttribute.eq(kind)).and(pAttribute.eq(parentId)));
		Results results = query.execute();
		List<Result> rs = results.all();
		for (Result r : rs) {
			list.add((Knowledge) r.getValue());
		}
		logger.debug("年级：" + grade + ",分类：" + kind + "，父知识点：" + parentId + "共有知识点：" + list.size() + "条");
		return list;
	}

	public Map<String, String> getKnowledgeLink(long k1, long k2, long k3, long k4, long k5) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		long[] knowledges = new long[] { k1, k2, k3, k4, k5 };
		for (long k : knowledges) {
			if (k == 0) {
				break;
			}
			Element e = getCache().get(k);
			Knowledge knowledge = (Knowledge) e.getValue();
			map.put(String.valueOf(k), knowledge.getKnowledge());
		}
		return map;
	}

	@Override
	public KnowledgeHelper getObject() throws Exception {
		return this;
	}

	@Override
	public Class<KnowledgeHelper> getObjectType() {
		return KnowledgeHelper.class;
	}

}
