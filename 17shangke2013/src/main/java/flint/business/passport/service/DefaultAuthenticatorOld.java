//package flint.business.passport.service;
//
//import static kiss.util.Helper.$;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.sql.DataSource;
//
//import kiss.security.Identity;
//import kiss.security.Passport;
//import kiss.security.PassportImpl;
//import kiss.util.Q;
//import kiss.web.SecurityRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jdbc.datasource.DataSourceUtils;
//
//import entity.User;
//import flint.business.constant.UserConst;
//import flint.business.constant.UserConst.UserType;
//import flint.business.message.CometdService;
//import flint.business.school.service.SchoolService;
//import flint.business.security.Person;
//import flint.business.user.dao.UserDAO;
//import flint.exception.ApplicationException;
//import flint.security.Credentials;
//import flint.security.OAuthAuthenticator;
//import flint.util.SpringHelper;
//import flint.web.util.CookieHelper;
//
//public class DefaultAuthenticatorOld implements OAuthAuthenticator {
//
//	public final Logger logger = LoggerFactory.getLogger(getClass());
//
//	private final String loginQueryString = "SELECT USER_ID,REAL_NAME,NAME,EMAIL,GRADE,USER_TYPE,ROLE,DOMAIN,STATUS,LOGINS,GRADE,FACE,ACTIVE,TEACHER_EXPERIENCE,STUDENT_EXPERIENCE,FOCUS,EXPERT FROM TB_USER";
//
//	private DataSource dataSource;
//
//	public DataSource getDataSource() {
//		return dataSource;
//	}
//
//	public void setDataSource(DataSource dataSource) {
//		this.dataSource = dataSource;
//	}
//
//	@Override
//	public Passport authenticate(Passport user, Credentials<?> credentials,
//			HttpServletResponse response) throws ApplicationException {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			String username = user.removeAttribute(Passport.USER);
//			String password = (String) credentials.getCredentials();
//			conn = DataSourceUtils.getConnection(dataSource);
//			ps = conn
//					.prepareStatement("select USER_ID,STATUS,LOGINS,last_time from TB_USER where NAME=? or EMAIL=?");
//			ps.setString(1, username);
//			ps.setString(2, username);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				/* 检查用户状态 */
//				if (rs.getByte("STATUS") != UserConst.STATUS_NORMAL) {
//					/* 用户状态异常 */
//					throw new ApplicationException("USER_STATUS_ILLEGAL");
//				}
//				String logins = "LOGINS";
//				/* 检查登录次数 */
//				if (rs.getInt("LOGINS") > 3) {
//					/* 检查一下限制用户登录的时间距离现在是否有3个小时 */
//					if (Q.now() - rs.getInt("last_time") < 60 * 60 * 3) {
//						/* 用户状态异常 */
//						throw new ApplicationException(
//								"USER_TRY_LOGIN_MANY_TIMES");
//					}
//					logins = "0";
//				}
//				long userId = rs.getLong("USER_ID");
//				/* 检查密码 */
//				PreparedStatement ps2 = conn.prepareStatement(loginQueryString
//						+ " where USER_ID=? and PASSWORD=?");
//				ps2.setLong(1, userId);
//				ps2.setString(2, password);
//				ResultSet rs2 = ps2.executeQuery();
//				/* 用戶登錄成功 */
//				if (rs2.next()) {
//					mappingLoginInfo(user, rs2, Q.uuid());
//				} else {
//					/* 用戶密码错误 */
//					PreparedStatement ps3 = conn
//							.prepareStatement("update TB_USER set LOGINS="
//									+ logins + "+1,last_time=? where USER_ID=?");
//					ps3.setInt(1, Q.now());
//					ps3.setLong(2, userId);
//					ps3.executeUpdate();
//					throw new ApplicationException("USER_PASSWORD_ERROR");
//				}
//			} else {
//				/* 用戶不存在 */
//				throw new ApplicationException("USER_NOT_EXIST");
//			}
//
//			if (user.isAuthenticated()) {
//				this.bindCookie(user, response);
//			}
//		} catch (SQLException e) {
//			logger.error("datasource connection error:{}", e);
//		} finally {
//			DataSourceUtils.releaseConnection(conn, dataSource);
//		}
//		return user;
//	}
//
//	private void bindCookie(Passport user, HttpServletResponse response) {
//		CookieHelper.addCookie(response, Passport.COOKIE_USER_ID,
//				String.valueOf(user.getId()), -1, "/", false);
//		CookieHelper.addCookie(response, Passport.COOKIE_USER_NAME,
//				user.getName(), -1, "/", false);
//		String focus = user.getAttribute("focus");
//		String expert = user.getAttribute("expert");
//		if (!Q.isEmpty(focus))
//			CookieHelper.addCookie(response, "user.focus", focus,
//					Passport.COOKIE_MAX_AGE, "/", false);
//		if (!Q.isEmpty(expert))
//			CookieHelper.addCookie(response, "user.expert", expert,
//					Passport.COOKIE_MAX_AGE, "/", false);
//		/* 默认保存用户类型 */
////		CookieHelper.addCookie(response, Passport.COOKIE_USER_TYPE,
////				String.valueOf(user.getUserType()), Passport.COOKIE_MAX_AGE, "/",
////				false);
//		CookieHelper.addCookie(response, "user.grade",
//				(String) user.getAttribute("grade"), Passport.COOKIE_MAX_AGE,
//				"/", false);
//	}
//
//	/**
//	 * 登录存储信息
//	 * 
//	 * @param member
//	 * @param rs
//	 * @throws SQLException
//	 * @throws ApplicationException
//	 */
//	private void mappingLoginInfo(Passport passport, ResultSet rs, String cookie)
//			throws SQLException, ApplicationException {
//		PassportImpl member = (PassportImpl) passport;
//		member.setId(rs.getString("USER_ID"));
//		member.setName(Q.isEmpty(rs.getString("REAL_NAME"),
//				rs.getString("NAME")));
//		/* 添加默认登录账户 */
//		byte userType = rs.getByte("USER_TYPE");
//		String role = Q.isEmpty(rs.getString("ROLE"), "ANONYMOUS");
//		role = role + "," + UserType.getName(userType);
//
//		Person main = new Person(passport);
//		main.setDomainId(0);
//		main.setUserType(userType);
//		main.setName(member.getName());
//		// main.setRoles(role.split(","));
//		// main.setResources(getResources(main)); /* 用户允许访问的资源 */
//		// main.setAuthrities(getAuthoritys(main));/* 用户的权限 */
//		// member.add("www", main);
//
//		/* 学校类型的账户，所有 */
//		if (userType == UserType.ENTERPRISE.byteValue()) {
//			/* 查询用户是某个学校的管理员 */
//			SchoolService service = SpringHelper.getBean(SchoolService.class);
//			main.setAttribute("school",
//					service.getSchoolIdByWebmaster(rs.getLong("USER_ID")));
//		}
//
//		/* 多用户切换 */
//		if (main.one("school0:MUSER")) {
//			// 这里编写查询的信息
//			UserDAO userDao = SpringHelper.getBean(UserDAO.class);
//			List<User> userList = userDao.searchOherUser($(
//					rs.getString("USER_ID")).toLong());
//			member.setAttribute("musers.id", userList);
//			Map<String, Passport> musers = new HashMap<String, Passport>();
//			musers.put(member.getId(), member);
//			member.setAttribute("musers", musers);
//		}
//
//		/* 检查用户在访问域内的权限，即学校的权限 */
//		this.bindSchool(member);
//
//		member.setAuthenticated(true);// 全局授权
//		main.setAuthenticated(true);// 区域授权
//		logger.debug("user-login:" + member);
//
//		/* ==================== 公共信息============================== */
//		/* 用户自定义域名 */
//		String domain = rs.getString("domain");
//		if (Q.isEmpty(domain)) {
//			domain = member.getId();
//		}
//		//member.setDomain(domain);
//		member.setAttribute("grade", rs.getString("GRADE"));
//		member.setAttribute("loginTime", Q.now());
//		member.setAttribute("face", rs.getString("face"));
//		member.setAttribute("active", rs.getInt("active"));
//		member.setAttribute("teacherExperience",
//				rs.getInt("teacher_experience"));
//		member.setAttribute("studentExperience",
//				rs.getInt("student_experience"));
//		member.setAttribute("email", rs.getString("email"));
//
//		member.setAttribute("focus", rs.getString("focus"));
//		member.setAttribute("expert", rs.getString("expert"));
//		member.setAttribute("grade", rs.getString("grade"));
//		Connection conn = DataSourceUtils.getConnection(dataSource);
//		try {
//			PreparedStatement ps3 = conn
//					.prepareStatement("update TB_USER set LOGINS=0,online=1,cookie=? where USER_ID=?");
//			ps3.setString(1, cookie);
//			ps3.setLong(2, Long.parseLong(member.getId()));
//			ps3.executeUpdate();
//		} catch (SQLException e) {
//			logger.error("datasource connection error:{}", e);
//		} finally {
//			DataSourceUtils.releaseConnection(conn, dataSource);
//		}
//		member.setAttribute("cookie", cookie);
//	}
//
//	/**
//	 * 绑定用户的学校信息
//	 * 
//	 * @param user
//	 * @throws Exception
//	 */
//	private void bindSchool(Passport user) throws ApplicationException {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			conn = DataSourceUtils.getConnection(dataSource);
//			ps = conn
//					.prepareStatement("SELECT  TB_SCHOOL_MEMBER.SCHOOL_ID,TB_SCHOOL.DOMAIN,TB_SCHOOL.`NAME` SNAME,TB_SCHOOL_MEMBER.`NAME`,TB_SCHOOL_MEMBER.`STATUS`,USER_TYPE,USER_ROLE from TB_SCHOOL_MEMBER ,TB_SCHOOL where  TB_SCHOOL_MEMBER.SCHOOL_ID=TB_SCHOOL.SCHOOL_ID   and TB_SCHOOL_MEMBER.USER_ID=? ");
//			ps.setLong(1, Long.parseLong(user.getId()));
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				Person p = new Person(user);
//				p.setDomainId(rs.getLong("SCHOOL_ID"));
//				// p.setSchoolName(rs.getString("SNAME"));
//				p.setDomain(rs.getString("DOMAIN"));
//				p.setUserType(rs.getByte("USER_TYPE"));
//				p.setName(rs.getString("NAME"));
//				p.setAuthenticated(rs.getBoolean("status"));
//
//				/* 检查进入学校时的授权 */
//				// if (p.getSchoolDomain().equals(user.getEnterDomain())) {
//				// if (!p.isAuthenticated()) {
//				// throw new ApplicationException(
//				// "USER_SCHOOL_ACCESS_ILLEGAL");
//				// }
//				// }
//
//				String role = Q.isEmpty(rs.getString("USER_ROLE"), "ANONYMOUS");
//				role = role + "," + UserType.getName(p.getUserType());
//				// p.setRoles(role.split(","));
//				// p.setResources(getResources(p)); /* 用户允许访问的资源 */
//				// p.setAuthrities(getAuthoritys(p));/* 用户的权限 */
//				// user.add(p.getSchoolDomain(), p);
//			}
//		} catch (SQLException e) {
//			logger.error("datasource connection error:{}", e);
//		} finally {
//			DataSourceUtils.releaseConnection(conn, dataSource);
//		}
//	}
//
//	public void status(String userId, byte status) {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		try {
//			conn = DataSourceUtils.getConnection(dataSource);
//			ps = conn
//					.prepareStatement("update TB_USER set online=? where USER_ID=?");
//			ps.setLong(1, status);
//			ps.setLong(2, Long.parseLong(userId));
//			/* 更新成功则通知其他人 */
//			if (ps.executeUpdate() > 0) {
//				CometdService cs = SpringHelper.getBean(CometdService.class);
//				Map<String, Object> data = new HashMap<String, Object>();
//				data.put("event", "user.status");
//				data.put("status", status);
//				data.put("id", userId);
//				cs.systemBroadcast(data);
//			}
//		} catch (SQLException e) {
//			logger.error("datasource connection error:{}", e);
//		} finally {
//			DataSourceUtils.releaseConnection(conn, dataSource);
//		}
//	}
//
//	/**
//	 * 资源权限
//	 * 
//	 * @param usr
//	 * @return
//	 */
//	private String[] getResources(Identity usr) {
//		// StringBuilder sql = new StringBuilder(
//		// "SELECT  RESOURCE  FROM   TB_RESOURCE_PERMISSION,TB_RESOURCE where TB_RESOURCE.ID=TB_RESOURCE_PERMISSION.RESOURCE and TB_RESOURCE.DOMAIN="
//		// + (usr.getSchoolId() == 0 ? 0 : 1) + "");
//		// /* 用户组权限 */
//		// String[] groups = usr.getGroups();
//		// /* 用户角色权限 */
//		// String[] roles = usr.getRoles();
//		// sql.append("  and ( (type=0  and object='" + usr.getId() + "')");
//		// if (groups != null) {
//		// sql.append(" or ( type=2  and  object in(" + $(groups).join()
//		// + "))");
//		// }
//		// if (roles != null) {
//		// sql.append(" or ( type=1 and object in('" + $(roles).join("','")
//		// + "'))");
//		// }
//		// sql.append(")     ");
//		//
//		// Connection conn = null;
//		// PreparedStatement ps = null;
//		// ResultSet rs = null;
//		// try {
//		// conn = DataSourceUtils.getConnection(dataSource);
//		// ps = conn.prepareStatement(sql.toString());
//		// rs = ps.executeQuery();
//		// String[] resourecs = new String[16];
//		// int i = 0;
//		// while (rs.next()) {
//		// /* 扩容 */
//		// if (i == resourecs.length) {
//		// String[] newresourecs = new String[resourecs.length * 2];
//		// System.arraycopy(resourecs, 0, newresourecs, 0,
//		// resourecs.length);
//		// resourecs = newresourecs;
//		// }
//		// resourecs[i++] = rs.getString(1);
//		// }
//		// /* 清楚末尾的空位置 */
//		// String[] newresourecs = new String[i];
//		// System.arraycopy(resourecs, 0, newresourecs, 0, i);
//		// resourecs = newresourecs;
//		// return resourecs;
//		// } catch (SQLException e) {
//		// logger.error("datasource connection error:{}", e);
//		// } finally {
//		// DataSourceUtils.releaseConnection(conn, dataSource);
//		// }
//		return null;
//	}
//
//	private List getAuthoritys(Identity usr) {
//		// StringBuilder sql = new StringBuilder(
//		// "SELECT  RESOURCE  FROM   TB_AUTHORITY_PERMISSION  where 1=1");
//		// /* 用户组权限 */
//		// String[] groups = usr.getGroups();
//		// /* 用户角色权限 */
//		// String[] roles = usr.getRoles();
//		// sql.append("  and ( (type=0  and object='" + usr.getId() + "')");
//		// if (groups != null) {
//		// sql.append(" or ( type=2  and  object in('"
//		// + StringHelper.arrayToDelimitedString(groups, "','")
//		// + "'))");
//		// }
//		// if (roles != null) {
//		// sql.append(" or ( type=1 and object in('"
//		// + StringHelper.arrayToDelimitedString(roles, "','") + "'))");
//		// }
//		// sql.append(")     ");
//		//
//		// Connection conn = null;
//		// PreparedStatement ps = null;
//		// ResultSet rs = null;
//		// try {
//		// conn = DataSourceUtils.getConnection(dataSource);
//		// ps = conn.prepareStatement(sql.toString());
//		// rs = ps.executeQuery();
//		// String[] resourecs = new String[16];
//		// int i = 0;
//		// while (rs.next()) {
//		//
//		// }
//		// return null;
//		// } catch (SQLException e) {
//		// logger.error("datasource connection error:{}", e);
//		// } finally {
//		// DataSourceUtils.releaseConnection(conn, dataSource);
//		// }
//		return null;
//
//	}
//
//	@Override
//	public Passport authenticateByCookie(String cookieValue,
//			HttpServletRequest request, HttpServletResponse response)
//			throws ApplicationException {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		Passport member = ((SecurityRequest) request).getPassport();
//		try {
//			conn = DataSourceUtils.getConnection(dataSource);
//			ps = conn.prepareStatement(loginQueryString + " where cookie=? ");
//			ps.setString(1, cookieValue);
//			ResultSet rs = ps.executeQuery();
//			if (rs.next()) {
//				mappingLoginInfo(member, rs, cookieValue);
//			}
//			if (member.isAuthenticated()) {
//				this.bindCookie(member, response);
//			}
//		} catch (SQLException e) {
//			logger.error("datasource connection error:{}", e);
//		} finally {
//			DataSourceUtils.releaseConnection(conn, dataSource);
//		}
//		return member;
//	}
//
//	@Override
//	public Passport authenticateByOAuthUserId(long userId,
//			HttpServletResponse response) throws ApplicationException {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		Passport member = new PassportImpl(null);
//		try {
//			conn = DataSourceUtils.getConnection(dataSource);
//			ps = conn.prepareStatement(loginQueryString + " where user_id=? ");
//			ps.setLong(1, userId);
//			ResultSet rs = ps.executeQuery();
//			if (rs.next()) {
//				mappingLoginInfo(member, rs, "");
//				this.bindCookie(member, response);
//			}
//		} catch (SQLException e) {
//			logger.error("datasource connection error:{}", e);
//		} finally {
//			DataSourceUtils.releaseConnection(conn, dataSource);
//		}
//		return member;
//	}
//
//	@Override
//	public void changeUserType(Identity user, byte userType) {
//		Person member = (Person) user;
//		member.setUserType(userType);
////		member.setRoles(new String[] { UserType.getName(userType) });
////		member.setResources(this.getResources(member));
////		member.setAuthrities(this.getAuthoritys(member));
//	}
//
//	@Override
//	public Passport changeUser(Passport user, long userId,
//			HttpServletResponse response) throws ApplicationException {
//		Map<String, Passport> musers = user.getAttribute("musers");
//		Passport nu = musers.get(String.valueOf(userId));
//		String oldUserId = user.getAttribute("musers.current");
//		if (oldUserId == null) {
//			oldUserId = user.getId();
//		}
//		if (nu == null) {
//			nu = this.authenticateByOAuthUserId(userId, response);// 从数据库读取新的数据
//			/* 复制登录用户的session到所有子账户 */
//			nu.setAttribute("musers.id", user.getAttribute("musers.id"));
//			nu.setAttribute("musers", musers);
//			musers.put(String.valueOf(userId), nu);
//		}
//		/* 由于前台消息会继续保持原有session，难以判断用户是否退出，这里需要强制退出 */
//		off(oldUserId);
//		on(String.valueOf(userId));
//		user.setAttribute("musers.current", String.valueOf(userId));
//		return nu;
//	}
//
//	@Override
//	public void on(String userId) {
//		this.status(userId, (byte) 1);
//	}
//
//	@Override
//	public void hide(String userId) {
//		this.status(userId, (byte) 2);
//	}
//
//	@Override
//	public void busy(String userId) {
//		this.status(userId, (byte) 3);
//	}
//
//	@Override
//	public void off(String userId) {
//		this.status(userId, (byte) 0);
//	}
//}
