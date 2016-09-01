package flint.business.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kiss.security.Expression;
import kiss.security.Identity;
import kiss.security.Passport;
import kiss.security.PassportImpl;
import kiss.security.authc.AuthenticationToken;
import kiss.security.authc.CookieToken;
import kiss.security.authc.OauthToken;
import kiss.security.authc.SafetyAuthenticator;
import kiss.security.authc.UsernamePasswordToken;
import kiss.security.exception.AuthenticationException;
import kiss.security.exception.PassportDisabledException;
import kiss.security.exception.PassportNotFoundException;
import kiss.util.Q;

import org.springframework.jdbc.datasource.DataSourceUtils;

import entity.User;
import flint.business.constant.UserConst;
import flint.business.constant.UserConst.UserType;
import flint.business.message.CometdService;
import flint.business.school.service.SchoolService;
import flint.business.user.dao.UserDAO;
import flint.util.SpringHelper;

public class DefaultAuthenticator extends SafetyAuthenticator {
	private final String loginQueryString = "SELECT USER_ID,REAL_NAME,NAME,EMAIL,GRADE,USER_TYPE,ROLE,DOMAIN,STATUS,LOGINS,GRADE,FACE,ACTIVE,TEACHER_EXPERIENCE,STUDENT_EXPERIENCE,FOCUS,EXPERT FROM TB_USER";

	@Override
	public <P, C> Passport doAuthenticate(Passport passport,
			AuthenticationToken<P, C> token) throws AuthenticationException {
		if (token == null) {

		}
		/* 最基本的 */
		if (token instanceof UsernamePasswordToken) {
			// check name---check password--check status---bind info
			UsernamePasswordToken utoken = (UsernamePasswordToken) token;
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				String username = utoken.getPrincipal();
				String password = utoken.getCredentials();
				conn = DataSourceUtils.getConnection(getDataSource());
				ps = conn
						.prepareStatement("select USER_ID,STATUS,LOGINS,last_time from TB_USER where NAME=? or EMAIL=?");
				ps.setString(1, username);
				ps.setString(2, username);
				rs = ps.executeQuery();
				if (rs.next()) {
					/* 检查用户状态 */
					if (rs.getByte("STATUS") != UserConst.STATUS_NORMAL) {
						/* 用户状态异常 */
						throw new PassportDisabledException(
								"USER_STATUS_ILLEGAL");
					}
					String logins = "LOGINS";
					/* 检查登录次数 */
					if (rs.getInt("LOGINS") > 3) {
						/* 检查一下限制用户登录的时间距离现在是否有3个小时 */
						if (Q.now() - rs.getInt("last_time") < 60 * 60 * 3) {
							/* 用户状态异常 */
							throw new AuthenticationException(
									"USER_TRY_LOGIN_MANY_TIMES");
						}
						logins = "0";
					}
					long userId = rs.getLong("USER_ID");
					/* 检查密码 */
					PreparedStatement ps2 = conn
							.prepareStatement(loginQueryString
									+ " where USER_ID=? and PASSWORD=?");
					ps2.setLong(1, userId);
					ps2.setString(2, password);
					ResultSet rs2 = ps2.executeQuery();
					/* 用户登录成功 ,这里开始加载资源信息 */
					if (rs2.next()) {
						onSuccess(passport, token, rs2);
					} else {
						/* 用戶密码错误 */
						PreparedStatement ps3 = conn
								.prepareStatement("update TB_USER set LOGINS="
										+ logins
										+ "+1,last_time=? where USER_ID=?");
						ps3.setInt(1, Q.now());
						ps3.setLong(2, userId);
						ps3.executeUpdate();
						throw new AuthenticationException("USER_PASSWORD_ERROR");
					}
				} else {
					/* 用戶不存在 */
					throw new PassportNotFoundException("USER_NOT_EXIST");
				}
			} catch (SQLException e) {
				logger.error("datasource connection error:{}", e);
			} finally {
				DataSourceUtils.releaseConnection(conn, getDataSource());
			}
		} else if (token instanceof OauthToken) {
			oauth(passport, token, ((OauthToken) token).getPrincipal());
		} else if (token instanceof CookieToken) {
			CookieToken utoken = (CookieToken) token;
			cookie(passport, token, (String) utoken.getCredentials());
		}
		return passport;
	}

	private void oauth(Passport passport, AuthenticationToken token,
			String userId) throws AuthenticationException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceUtils.getConnection(getDataSource());
			ps = conn.prepareStatement(loginQueryString + " where user_id=? ");
			ps.setLong(1, Long.parseLong(userId));
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				onSuccess(passport, token, rs);
			}
		} catch (SQLException e) {
			logger.error("datasource connection error:{}", e);
		} finally {
			DataSourceUtils.releaseConnection(conn, getDataSource());
		}
	}

	private void cookie(Passport passport, AuthenticationToken token,
			String cookie) throws AuthenticationException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceUtils.getConnection(getDataSource());
			ps = conn.prepareStatement(loginQueryString + " where cookie=? ");
			ps.setString(1, cookie);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				onSuccess(passport, token, rs);
			}
		} catch (SQLException e) {
			logger.error("datasource connection error:{}", e);
		} finally {
			DataSourceUtils.releaseConnection(conn, getDataSource());
		}
	}

	public void sso(Passport passport) throws AuthenticationException {
		Person person = (Person) passport.get();
		person.setAttribute("authenticated.checked", true);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DataSourceUtils.getConnection(this.getDataSource());
			ps = conn
					.prepareStatement("SELECT  TB_SCHOOL_MEMBER.`NAME`,TB_SCHOOL_MEMBER.`STATUS`,USER_TYPE,USER_ROLE from TB_SCHOOL_MEMBER ,TB_SCHOOL where  TB_SCHOOL_MEMBER.SCHOOL_ID=TB_SCHOOL.SCHOOL_ID   and TB_SCHOOL_MEMBER.USER_ID=? and TB_SCHOOL_MEMBER.school_id=?");
			ps.setLong(1, Long.parseLong(person.getId()));
			ps.setLong(2, person.getDomainId());
			rs = ps.executeQuery();
			if (rs.next()) {
				person.setUserType(rs.getByte("USER_TYPE"));
				person.setName(rs.getString("NAME"));
				person.setAuthenticated(rs.getByte("status") == UserConst.STATUS_NORMAL);
				String role = Q.isEmpty(rs.getString("USER_ROLE"), "ANONYMOUS");
				role = role + "|" + UserType.getName(person.getUserType());
				passport.grant("school" + person.getDomainId()
						+ Expression.OBJECT + role);/* 用户角色 */
				passport.grant("school" + person.getDomainId()
						+ Expression.RESOURCE
						+ getResources(person, role.split("\\|")));/* 用户允许访问的资源 */
				// passport.grant("school0:"+Expression.AUTHORITIY+"");/* 用户的权限
				// *
				person.setAuthenticated(true);// 区域授权
			} else {
				/* 无访问权限 */
				throw new AuthenticationException("USER_SCHOOL_ACCESS_ILLEGAL");
			}
		} catch (SQLException e) {
			logger.error("datasource connection error:{}", e);
		} finally {
			DataSourceUtils.releaseConnection(conn, this.getDataSource());
		}

	}

	private void onSuccess(Passport passport, AuthenticationToken token,
			ResultSet rs) throws SQLException, AuthenticationException {
		PassportImpl member = (PassportImpl) passport;
		member.setId(rs.getString("USER_ID"));
		member.setName(Q.isEmpty(rs.getString("REAL_NAME"),
				rs.getString("NAME")));
		// 绑定区域权限
		Person current = (Person) passport.get();
		Person person = null;
		if (current.getDomainId() != 0) {
			/* 先从缓存中读取，判断是否已产生主站信息 */
			person = (Person) passport.get("www");
			if (person == null) {
				person = new Person(passport);
			}
			person.setDomain("www");
			person.setDomainId(0);
			/* 暂存 */
			passport.runAs(person);
			/* 还原当前访问域 */
			passport.runAs(current);
		} else {
			person = current;
		}
		/* 主站自动绑定 */
		person.setUserType(rs.getByte("USER_TYPE"));
		person.setName(member.getName());
		person.setAuthenticated(true);
		String role = Q.isEmpty(rs.getString("ROLE"), "ANONYMOUS") + "|"
				+ UserType.getName(person.getUserType());
		passport.grant("school0" + Expression.OBJECT + role);/* 用户角色 */
		passport.grant("school0" + Expression.RESOURCE
				+ getResources(person, role.split("\\|")));/* 用户允许访问的资源 */
		/* 学校类型的账户，所有 */
		if (person.is(UserType.ENTERPRISE.byteValue())) {
			/* 查询用户是某个学校的管理员 */
			SchoolService service = SpringHelper.getBean(SchoolService.class);
			person.setAttribute("school", service.getSchoolIdByWebmaster(Long
					.parseLong(passport.getId())));
		}

		/* 多用户切换 */
		if (person.one("school0:MUSER")) {
			// 这里编写查询的信息
			UserDAO userDao = SpringHelper.getBean(UserDAO.class);
			List<User> userList = userDao.searchOherUser(Long
					.parseLong(passport.getId()));
			passport.setAttribute("musers.id", userList);
			Map<String, Passport> musers = new HashMap<String, Passport>();
			musers.put(passport.getId(), passport);
			passport.setAttribute("musers", musers);
		}

		/* 绑定子站信息 */
		if (current.getDomainId() != 0) {
			sso(passport);
		}
		member.setAuthenticated(true);// 全局授权
		logger.debug("user-login:" + member);

		/* ==================== 公共信息============================== */
		/* 用户自定义域名 */
		member.setAttribute("grade", rs.getString("GRADE"));
		member.setAttribute("loginTime", Q.now());
		member.setAttribute("face", rs.getString("face"));
		member.setAttribute("active", rs.getInt("active"));
		member.setAttribute("teacherExperience",
				rs.getInt("teacher_experience"));
		member.setAttribute("studentExperience",
				rs.getInt("student_experience"));
		member.setAttribute("email", rs.getString("email"));
		member.setAttribute("focus", rs.getString("focus"));
		member.setAttribute("expert", rs.getString("expert"));
		member.setAttribute("grade", rs.getString("grade"));
		Connection conn = DataSourceUtils.getConnection(getDataSource());
		try {
			PreparedStatement ps3 = conn
					.prepareStatement("update TB_USER set LOGINS=0,online=1,cookie=? where USER_ID=?");
			ps3.setString(1, (String) member.getAttribute("cookie"));
			ps3.setLong(2, Long.parseLong(member.getId()));
			ps3.executeUpdate();
		} catch (SQLException e) {
			logger.error("datasource connection error:{}", e);
		} finally {
			DataSourceUtils.releaseConnection(conn, getDataSource());
		}
	}

	private String getResources(Identity usr, String[] roles) {
		StringBuilder sql = new StringBuilder(
				"SELECT  RESOURCE  FROM   TB_RESOURCE_PERMISSION,TB_RESOURCE where TB_RESOURCE.ID=TB_RESOURCE_PERMISSION.RESOURCE and TB_RESOURCE.DOMAIN="
						+ (usr.getDomainId() == 0 ? 0 : 1) + "");
		sql.append("  and ( (type=0  and object='" + usr.getId() + "')");
		if (roles != null) {
			sql.append(" or ( type=1 and object in('"
					+ Q.Array(roles).join("','") + "'))");
		}
		sql.append(")     ");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DataSourceUtils.getConnection(getDataSource());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			String[] resourecs = new String[16];
			int i = 0;
			while (rs.next()) {
				/* 扩容 */
				if (i == resourecs.length) {
					String[] newresourecs = new String[resourecs.length * 2];
					System.arraycopy(resourecs, 0, newresourecs, 0,
							resourecs.length);
					resourecs = newresourecs;
				}
				resourecs[i++] = rs.getString(1);
			}
			/* 清楚末尾的空位置 */
			String[] newresourecs = new String[i];
			System.arraycopy(resourecs, 0, newresourecs, 0, i);
			resourecs = newresourecs;
			return Q.Array(resourecs).join("|");
		} catch (SQLException e) {
			logger.error("datasource connection error:{}", e);
		} finally {
			DataSourceUtils.releaseConnection(conn, getDataSource());
		}
		return null;
	}

	public void status(String userId, byte status) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceUtils.getConnection(this.getDataSource());
			ps = conn
					.prepareStatement("update TB_USER set online=? where USER_ID=?");
			ps.setLong(1, status);
			ps.setLong(2, Long.parseLong(userId));
			/* 更新成功则通知其他人 */
			if (ps.executeUpdate() > 0) {
				CometdService cs = SpringHelper.getBean(CometdService.class);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("event", "user.status");
				data.put("status", status);
				data.put("id", userId);
				cs.systemBroadcast(data);
			}
		} catch (SQLException e) {
			logger.error("datasource connection error:{}", e);
		} finally {
			DataSourceUtils.releaseConnection(conn, getDataSource());
		}
	}

	@Override
	public void on(String userId) {
		this.status(userId, (byte) 1);
	}

	@Override
	public void hide(String userId) {
		this.status(userId, (byte) 2);
	}

	@Override
	public void busy(String userId) {
		this.status(userId, (byte) 3);
	}

	@Override
	public void off(String userId) {
		this.status(userId, (byte) 0);
	}
}
