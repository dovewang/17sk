package flint.business.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import kiss.security.Expression;
import kiss.security.Identity;
import kiss.security.Passport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.AntPathMatcher;

import flint.business.constant.UserConst.UserType;

public class ResourceManager implements FactoryBean<ResourceManager>,
		BeanNameAware, InitializingBean {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final static String ALLOW_ALL_IP = "*";
	/**
	 * 受保护的资源
	 */
	private List<Resource> resources = new ArrayList<Resource>();

	private String queryString;

	private DataSource dataSource;

	@SuppressWarnings("unused")
	private String beanName;

	AntPathMatcher urlmatch = new AntPathMatcher();

	/**
	 * 
	 * @param passport
	 * @param url
	 * @return
	 */
	public boolean allow(Passport passport, String url) {
		Identity user = passport.get();
		/* 判断是否是配置过学校子站域名，如果是则为1，否则为0 */
		String type = user.getDomainId() == 0 ? "0" : "1";

		/* 是否是受保护的资源，实行一票否决制 */
		Resource protectedResource = null;
		for (Resource resource : resources) {
			if (!type.equals(resource.getDomain())) {
				continue;
			}

			if (urlmatch.match(resource.getPath(), url)) {
				/** 检查路径层次最深的，例如匹配/a/* */
				protectedResource = resource;
			}
		}
		/* 用戶访问受保护的资源 */
		if (protectedResource != null) {
			/* 不同的域可能授权不同，用户是否获得授权 */
			if (!user.isAuthenticated()) {
				/* 检查匿名用户可以访问的区域 */
				return false;
			}

			/* IP地址检查 */
			if (!ALLOW_ALL_IP.equals(protectedResource.getHost()[0])) {
				String netAddress = user.getAttribute("netAddress");
				boolean allowIp = false;
				/* ip地址匹配检查 */
				for (String ip : protectedResource.getHost()) {
					if (ip.equals(netAddress)) {
						allowIp = true;
						break;
					}
				}
				if (!allowIp) {
					logger.warn("invalid user:userId=" + user.getId()
							+ ",userName=" + user.getName() + ",netAddress="
							+ netAddress);
					return false;
				}
			}
			/* 主站的管理员对所有站点放行 */
			if (user.one("school0:" + UserType.ADMIN.name() + ",school"
					+ user.getDomainId() + Expression.RESOURCE
					+ protectedResource.getId())) {
				return true;
			}
			/* 不具有访问权限 */
			passport.setAttribute("forward", protectedResource.getForward());
			return false;
		}
		/* 未受保护的资源默认放行 */
		return true;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void afterPropertiesSet() throws Exception {
		/* 加载所有的资源列表 */
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DataSourceUtils.getConnection(dataSource);
			ps = conn.prepareStatement(queryString);
			rs = ps.executeQuery();
			while (rs.next()) {
				Resource r = new Resource();
				r.setId(rs.getString("id"));
				r.setPath(rs.getString("path"));
				r.setHost(new String[] { rs.getString("host") });
				r.setForward(rs.getString("FORWARD"));
				r.setDomain(rs.getString("domain"));
				resources.add(r);
			}
		} catch (SQLException e) {
			logger.error("datasource connection error:{}", e);
		} finally {
			DataSourceUtils.releaseConnection(conn, dataSource);
		}
		logger.debug("load access resource success!");
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public ResourceManager getObject() throws Exception {
		return this;
	}

	public Class<ResourceManager> getObjectType() {
		return ResourceManager.class;
	}

	public boolean isSingleton() {
		return true;
	}
}
