package flint.business.util;

import static kiss.util.Helper.$;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kiss.util.Q;

import net.sf.ehcache.Element;
import entity.Kind;
import flint.context.ContextCache;
import flint.jdbc.RowSetHandler;
import flint.util.StringHelper;

/**
 * 
 * @author Dove Wang
 */
public class KindHelper extends ContextCache<KindHelper> {

	@Override
	public void afterPropertiesSet() throws Exception {
		loadGrades();
	}

	/**
	 * 加载所有的年级信息
	 * 
	 * @return
	 */
	public Map<String, String> loadGrades() {
		Map<String, String> map = getDao().query(
				"  SELECT   GRADE,`NAME`  FROM  TB_GRADE order by grade",
				new RowSetHandler<Map<String, String>>() {
					@Override
					public Map<String, String> populate(ResultSet rs)
							throws SQLException {
						Map<String, String> map = new LinkedHashMap<String, String>();
						while (rs.next()) {
							String grade = rs.getString(1);
							map.put(grade, rs.getString(2));
							getKind(grade);
						}
						return map;
					}
				});
		getCache().put(new Element("grade", map));
		return map;
	}

	/**
	 * 加载所有分类信息
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getGrades() {
		Element grade = getCache().get("grade");
		if (grade == null) {
			return loadGrades();
		}
		return (Map<String, String>) grade.getValue();
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getKinds() {
		Element kind = getCache().get("kind");
		if (kind == null) {
			Map<String, String> map = getDao()
					.query("SELECT  DISTINCT(KIND) kind ,`NAME`  FROM  TB_KIND   where status=1 order by KIND",
							new RowSetHandler<Map<String, String>>() {
								@Override
								public Map<String, String> populate(ResultSet rs)
										throws SQLException {
									Map<String, String> map = new LinkedHashMap<String, String>();
									while (rs.next()) {
										map.put(rs.getString(1),
												rs.getString(2));
									}
									return map;
								}
							});
			getCache().put(new Element("kind", map));
			return map;
		}
		return (Map<String, String>) kind.getValue();
	}

	@SuppressWarnings("unchecked")
	public List<Kind> getKind(String grade) {
		Element kind = getCache().get("grade" + grade);
		if (kind == null) {
			List<Kind> list = getDao()
					.query(" SELECT  KIND,GRADE,`NAME`,TOOLS  FROM  TB_KIND   where grade=? and status=1 order by KIND",
							new RowSetHandler<List<Kind>>() {
								@Override
								public List<Kind> populate(ResultSet rs)
										throws SQLException {
									List<Kind> list = new ArrayList<Kind>();
									while (rs.next()) {
										Kind kind = new Kind();
										kind.setKind(rs.getLong(1));
										kind.setGrade(rs.getInt(2));
										kind.setName(rs.getString(3));
										kind.setTools(rs.getString(4));
										list.add(kind);
									}
									return list;
								}
							}, grade);
			getCache().put(new Element("grade" + grade, list));
			return list;
		}
		return (List<Kind>) kind.getValue();
	}

	public String getGradeLabel(String grade) {
		if ($(grade).isEmpty())
			return "";
		Map<String, String> grades = getGrades();
		String[] tmp = grade.split(",");
		String[] lables = new String[tmp.length];
		for (int i = 0; i < lables.length; i++) {
			lables[i] = $(grades.get(tmp[i])).isEmpty("");
		}
		return $(lables).join("，");
	}

	public String getKindLabel(String kind) {
		return Q.isEmpty(getKinds().get(kind), "");
	}

	public String getKindLabels(String[] kind) {
		Map<String, String> kinds = getKinds();
		for (int i = 0; i < kind.length; i++) {
			kind[i] = kinds.get(kind[i]);
		}
		return $(kind).join("，");
	}

	public String getKindLabels(String kind) {
		if (Q.isEmpty(kind))
			return "";
		return getKindLabels(kind.split(","));
	}

	@Override
	public KindHelper getObject() throws Exception {
		return this;
	}

	@Override
	public Class<KindHelper> getObjectType() {
		return KindHelper.class;
	}

}
