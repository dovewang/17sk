package flint.business.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import kiss.util.Q;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;
import flint.business.core.Domain;
import flint.context.ContextCache;
import flint.jdbc.RowSetHandler;

public class DomainHelper extends ContextCache<DomainHelper> {

	private final static String sql = "SELECT  TB_SCHOOL.SCHOOL_ID,  TB_SCHOOL.`NAME`,TB_SCHOOL.KEYWORD,TB_SCHOOL.DESCRIPTION,TB_SCHOOL.DOMAIN, TB_SCHOOL.LOGO,TB_THEME.DIR , TB_SCHOOL.status,TB_SCHOOL.MEMO MEMO FROM  TB_SCHOOL ,TB_THEME  where TB_SCHOOL.THEME_ID=TB_THEME.THEME_ID";

	@Override
	public DomainHelper getObject() throws Exception {
		return this;
	}

	@Override
	public Class<DomainHelper> getObjectType() {
		return DomainHelper.class;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		reload();
	}

	/**
	 * 加载所有域名的配置信息
	 */
	public void reload() {
		getDao().query(sql, new RowSetHandler<Object>() {
			@Override
			public Object populate(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Domain domain = mapRow(rs);
					getCache().put(
							new Element(getPrefix() + domain.getDomain(),
									domain));
				}
				return null;
			}
		});
	}

	/**
	 * 获得单个域名的信息
	 * 
	 * @param domain
	 * @return
	 */
	public Domain getDomain(String domain) {
		Element value = getCache().get(getPrefix() + domain);
		if (value == null) {
			return reload(domain);
		}
		return (Domain) value.getValue();
	}

	public Domain getDomainById(long schoolId) {
		Domain domain = null;
		Query query = getCache().createQuery();
		Attribute<Long> id = getCache().getSearchAttribute("id");
		query.includeValues();
		query.addCriteria(id.eq(schoolId));
		Results results = query.execute();
		List<Result> rs = results.all();
		if (rs.size() > 0) {
			domain = (Domain) (rs.get(0).getValue());
		} else {
			/* 找不到时默认返回主域名信息 */
			domain = getDomainById(0);
		}
		return domain;
	}

	public Domain reload(String domain) {
		return getDao().query(sql + "  and  TB_SCHOOL.DOMAIN =? ",
				new RowSetHandler<Domain>() {
					@Override
					public Domain populate(ResultSet rs) throws SQLException {
						if (rs.next()) {
							Domain domain = mapRow(rs);
							String s = domain.getDomain();
							getCache().put(
									new Element(getPrefix()
											+ domain.getDomain(), domain));
							return domain;
						}
						return null;
					}
				}, domain);
	}

	public void remove(String domain) {
		getCache().remove(getPrefix() + domain);
	}

	public Domain mapRow(ResultSet rs) throws SQLException {
		Domain domain = new Domain();
		domain.setId(rs.getLong("school_id"));
		domain.setName(rs.getString("name"));
		domain.setDomain(rs.getString("domain"));
		domain.setLogo(rs.getString("logo"));
		domain.setKeyword(rs.getString("keyword"));
		domain.setDescription(rs.getString("description"));
		domain.setThemePath(rs.getString("DIR"));
		domain.setStatus(rs.getByte("status"));
		domain.setReason(Q.isEmpty(rs.getString("MEMO"), domain.getReason()));
		return domain;
	}

}
