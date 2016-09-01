package flint.business.user.dao.impl;

import static kiss.util.Helper.$;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kiss.util.Q;

import org.springframework.stereotype.Repository;

import entity.Collect;
import entity.User;
import entity.UserAddress;
import entity.UserExtends;
import flint.base.BaseDAOImpl;
import flint.business.constant.AtConst;
import flint.business.constant.UserConst;
import flint.business.user.dao.UserDAO;
import flint.common.Page;
import flint.exception.DataAccessException;
import flint.jdbc.RowMapper;
import flint.jdbc.RowSetHandler;
import flint.util.RandomGenerator;
import flint.util.StringHelper;

/**
 * 
 * 功能描述：用户类型分为：匿名用户，学生，老师，家长，不同的用户类型同时还可以具有不同的角色<br>
 * 
 * 
 * 日 期：2011-5-25 18:52:45<br>
 * 
 * 作 者：HuangShuai<br>
 * 
 * 版权所有：©mosai<br>
 * 
 * 版 本 ：v0.01<br>
 * 
 * 联系方式：<a href="mailto:hs0910@163.com">hs0910@163.com</a><br>
 * 
 * 修改备注：{修改人|修改时间}<br>
 * 
 * 
 **/
@Repository
public class UserDAOImpl extends BaseDAOImpl<User, Long> implements UserDAO {

	@Override
	public void email(long userId, String activeCode) {
		update("UPDATE TB_USER SET active_code=?,code_time=? WHERE user_id=? ",
				activeCode, Q.now(), userId);
	}

	@Override
	public boolean validateEmail(long userId, String activeCode)
			throws DataAccessException {
		long count = count(
				"select count(1) from TB_USER where user_id=? and active_code=?",
				userId, activeCode);
		if (count > 0) {
			update("update TB_USER set active=active+1 where user_id=?", userId);
			return true;
		}
		return false;
	}

	@Override
	public boolean validateDegree(long userId) throws DataAccessException {
		long count = count(
				"select count(1) from TB_USER where user_id=? and active_code=?",
				userId);
		if (count > 0) {
			update("update TB_USER set active=active+2 where user_id=?", userId);
			return true;
		}
		return false;
	}

	@Override
	public boolean validateRealName(long userId) throws DataAccessException {
		long count = count(
				"select count(1) from TB_USER where user_id=? and active_code=?",
				userId);
		if (count > 0) {
			update("update TB_USER set active=active+4 where user_id=?", userId);
			return true;
		}
		return false;
	}

	/* ==============密码处理相关================== */

	@Override
	public int changePassword(long userId, String oldPassword,
			String newPassword) {
		return update(
				"UPDATE TB_USER SET password=?  WHERE user_id=? AND password=?",
				newPassword, userId, oldPassword);
	}

	@Override
	public int recovery(String userName, String email, String activeCode)
			throws DataAccessException {
		return update(
				"UPDATE TB_USER SET active_code=?,code_time=? WHERE name=? AND email=?",
				activeCode, Q.now(), userName, email);
	}

	@Override
	public int updateStatus(long userid, byte status)
			throws DataAccessException {
		return update("UPDATE TB_USER SET status=? WHERE user_id=?", status,
				userid);
	}

	@Override
	public List<Object> existsUserName(String... names)
			throws DataAccessException {
		return queryForList("select name from TB_USER where name in("
				+ $(names).holder() + ") ", $(new Object[] {}).concat(names)
				.toArray());
	}

	@Override
	public boolean existsEmail(String email, String name)
			throws DataAccessException {
		if (name != null) {
			return (Long) this.getObject(
					"select count(1) from TB_USER where name=? and email=? ",
					name, email) > 0;
		} else {
			return (Long) this.getObject(
					"select count(1) from TB_USER where email=? ", email) > 0;
		}
	}

	@Override
	public int updateUser(long schoolId, User user) throws DataAccessException {
		String sql_update_user = "";
		if (!Q.isBlank(user.getExpert())) {
			this.expert(user.getUserId(), user.getExpert());
		}
		if (schoolId == 0) {
			sql_update_user = "UPDATE TB_USER SET real_name=?,grade=?,expert=?,experience=?,focus=?,city=? WHERE user_id=?";
			return update(sql_update_user, user.getRealName(), user.getGrade(),
					user.getExpert(), user.getExperience(), user.getFocus(),
					user.getCity(), user.getUserId());
		} else {
			sql_update_user = "UPDATE TB_USER SET grade=?,expert=?,experience=?,focus=?,city=? WHERE user_id=?";
			update(sql_update_user, user.getGrade(), user.getExpert(),
					user.getExperience(), user.getFocus(), user.getCity(),
					user.getUserId());
			return update(
					"update TB_SCHOOL_MEMBER set name=? where user_id=? ",
					user.getRealName(), user.getUserId());
		}
	}

	@Override
	public int updateUserExtends(UserExtends userExtends)
			throws DataAccessException {
		String sql_update_user = "UPDATE  TB_USER_EXTENDS   SET intro=?,qq=?,msn=?,blog=?,home=?,birthday=?,nation=?,province=?,city=?,town=?,road=?,ban=?,address=? WHERE user_id=?";
		return update(sql_update_user, userExtends.getIntro(),
				userExtends.getQq(), userExtends.getMsn(),
				userExtends.getBlog(), userExtends.getHome(),
				userExtends.getBirthday(), userExtends.getNation(),
				userExtends.getProvince(), userExtends.getCity(),
				userExtends.getTown(), userExtends.getRoad(),
				userExtends.getBan(), userExtends.getAddress(),
				userExtends.getUserId());
	}

	@Override
	public int saveAvatar(String faceUrl, long userId)
			throws DataAccessException {
		return update("UPDATE TB_USER SET face=? WHERE user_id=? ", faceUrl,
				userId);
	}

	@Override
	public int updateUserType(byte type, long userId)
			throws DataAccessException {
		String sql_update_role = "UPDATE TB_USER SET user_type=? WHERE user_id=?";
		return update(sql_update_role, type, userId);
	}

	/** ================================用户查询相关============================== */
	private String getQueryString(long nowUser, long schoolId, long groupId,
			String cols) {
		if (cols == null) {
			cols = "teacher_level,student_level,face,active,FEE,STUDENTS,ANSWERS,CORRECT_ANSWERS,COURSES,SUCCESS_COURSE,expert,experience,focus,online,city";
		}
		StringBuilder sb = new StringBuilder(
				"SELECT TB_USER.USER_ID,#special#," + cols);
		sb.append(",IFNULL(f1.follow,0) follow");
		sb.append(",IFNULL(f2.fans,0) fans");
		sb.append("  FROM TB_USER    ");
		sb.append(
				"   LEFT  JOIN (select count(1) follow,FRIEND_ID as UID from TB_FOLLOW  where FANS_ID=")
				.append(nowUser)
				.append("  GROUP BY  FRIEND_ID) f1 on f1.UID=TB_USER.USER_ID    ");
		sb.append(
				"   LEFT  JOIN (select count(1) fans,FANS_ID as UID from TB_FOLLOW  where FRIEND_ID=")
				.append(nowUser)
				.append(" GROUP BY  FANS_ID) f2 on f2.UID=TB_USER.USER_ID    ");
		/* 学习圈的名片，预留，暂时不处理 */
//		if (groupId != 0) {
//
//		} else {
			int special = sb.indexOf("#special#"), len = "#special#".length();
			if (schoolId != 0) {
				sb = sb.replace(
						special,
						special + len,
						"TB_SCHOOL_MEMBER.NAME  REAL_NAME,TB_SCHOOL_MEMBER.NAME,TB_SCHOOL_MEMBER.USER_TYPE,TB_SCHOOL_MEMBER.USER_ROLE ROLE");
				sb.append("  JOIN  TB_SCHOOL_MEMBER ON TB_USER.USER_ID=TB_SCHOOL_MEMBER.USER_ID  and TB_SCHOOL_MEMBER.SCHOOL_ID="
						+ schoolId);
			} else {
				sb = sb.replace(special, special + len,
						"REAL_NAME,NAME,USER_TYPE,ROLE");
			}
//		}

		return sb.toString();
	}

	private RowMapper<User> mapperUser() {
		return new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User u = new User();
				u.setUserId(rs.getInt("user_id"));
				u.setUserType(rs.getLong("user_type"));
				u.setName(Q.isBlank(rs.getString("real_name"),
						rs.getString("name")));
				u.setStudentLevel(rs.getByte("student_level"));
				u.setTeacherLevel(rs.getByte("teacher_level"));
				u.setFace(rs.getString("face"));
				u.setActive(rs.getByte("active"));
				u.setStudents(rs.getInt("students"));
				u.setAnswers(rs.getInt("answers"));
				u.setCorrectAnswers(rs.getInt("correct_answers"));
				u.setCourses(rs.getInt("courses"));
				u.setSuccessCourse(rs.getInt("success_course"));
				u.setFee(rs.getInt("fee"));
				u.setExpert(rs.getString("expert"));
				u.setExperience(rs.getString("experience"));
				u.setFocus(rs.getString("focus"));
				u.setCity(rs.getInt("city"));
				u.setOnline(rs.getInt("online"));
				int follow = rs.getInt("follow");
				int fans = rs.getInt("fans");
				u.setMemo(String.valueOf(follow + 2 * fans));// 0代表没关注，1代表已关注，2代表被关注，3代表互相关注
				return u;
			}
		};
	}

	private int level(int credits) {
		if (credits < 100) {
			return 1;
		} else if (credits < 1000) {
			if (credits >= 100 && credits < 250) {
				return 2;
			} else if (credits >= 250 && credits <= 500) {
				return 3;
			} else {
				return 4;
			}
		} else {
			return (int) Math.log10(credits) + 6;
		}
	}

	private RowSetHandler<String> mapSimpleUsers() {
		return new RowSetHandler<String>() {
			@Override
			public String populate(ResultSet rs) throws SQLException {
				StringBuilder sb = new StringBuilder("[");
				while (rs.next()) {
					sb.append("{\"id\":\"").append(rs.getString("user_id"))
							.append("\",");
					sb.append("\"name\":\"")
							.append(Q.isBlank(rs.getString("real_name"),
									rs.getString("name"))).append("\",");
					String face = rs.getString("face");
					if (!StringHelper.isEmpty(face)) {
						sb.append("\"face\":\"").append(face).append("\",");
					}
					sb.append("\"credit\":\"")
							.append(level(rs.getInt("CREDITS"))).append("\",");
					sb.append("\"expert\":\"").append(rs.getString("expert"))
							.append("\",");
					sb.append("\"follow\":").append(rs.getBoolean("follow"))
							.append(",");
					sb.append("\"fans\":").append(rs.getBoolean("fans"))
							.append(",");
					sb.append("\"type\":\"").append(rs.getString("user_type"))
							.append("\",");
					sb.append("\"role\":\"").append(rs.getString("role"))
							.append("\",");
					sb.append("\"online\":\"").append(rs.getString("online"))
							.append("\",");
					sb.append("\"status\":\"").append(rs.getString("online"))
							.append("\"}");
					sb.append(",");
				}
				if (sb.length() > 2) {
					return sb.substring(0, sb.length() - 1) + "]";
				}
				sb.append("]");
				return sb.toString();
			}

		};
	}

	@Override
	public List<User> findUserByIds(long schoolId, long nowUser,
			Collection<String> ids) throws DataAccessException {
		String sql = getQueryString(nowUser, schoolId, 0, null)
				+ " where TB_USER.USER_ID in(" + $(ids).join() + ")";
		return query(sql, mapperUser());
	}

	@Override
	public Page<User> search(long schoolId, long nowUser, String where,
			String orderBy, long page, long size) throws DataAccessException {
		return queryPage(getQueryString(nowUser, schoolId, 0, null) + where
				+ " " + orderBy,
				"SELECT COUNT(user_id)  FROM TB_USER " + where, mapperUser(),
				page, size);
	}

	@Override
	public String ping(String[] userIds, long schoolId, long groupId,
			long nowUser) throws DataAccessException {
		String sql = getQueryString(nowUser, schoolId, groupId,
				"expert,CREDITS,TEACHER_EXPERIENCE,face,`ONLINE`")
				+ " where TB_USER.USER_ID in(" + $(userIds).holder() + ")";
		return query(sql, mapSimpleUsers(), (Object[]) userIds);
	}

	@Override
	public User getUsercard(long userId, long schoolId, long groupId,
			long nowUser) throws DataAccessException {
		String sql = getQueryString(nowUser, schoolId, groupId, null)
				+ " where TB_USER.USER_ID =?";
		return queryFirst(sql, mapperUser(), userId);
	}

	@Override
	public UserExtends findUserExtends(long userId) {
		return findFirst(UserExtends.class,
				"select * from TB_USER_EXTENDS where user_id=? ", userId);
	}

	@Override
	public User getUser(long userId) {
		return findFirst(
				User.class,
				"SELECT USER_ID,user_type,role,`NAME`,DOMAIN,VIP,GRADE,FACE,SEX,EMAIL,TEL,REAL_NAME,ACTIVE,TEACHER_LEVEL,STUDENT_LEVEL,TEACHER_EXPERIENCE,STUDENT_EXPERIENCE,SCORES,FEE,STUDENTS,ANSWERS,CORRECT_ANSWERS,COURSES,SUCCESS_COURSE,GOOD_COURSES,FAVOR,EXPERT,EXPERIENCE,online  FROM  TB_USER  where user_id=? ",
				userId);
	}

	@Override
	public int collect(long uid, String objectId, byte objectType) {
		/* 检查收藏的内容是否存在 */
		if (count(
				"select count(1) from TB_COLLECT where USER_ID=? and OBJECT_ID=? and OBJECT_TYPE=?",
				uid, objectId, objectType) > 0) {
			return 0;
		}
		return update(
				"insert into  TB_COLLECT(OBJECT_ID,OBJECT_TYPE,USER_ID,COLLECT_TIME)values(?,?,?,?)",
				objectId, objectType, uid, Q.now());
	}

	@Override
	public void at(long objectId, byte objectType, long... users) {
		List<Object[]> s = new ArrayList<Object[]>();
		int time = Q.now();
		for (int i = 0; i < users.length; i++) {
			s.add(new Object[] { objectId, users[i], objectType,
					AtConst.NO_READ, time });
		}
		this.batchUpdate(
				"insert into TB_AT(AT_ID, USER_ID, TYPE, STATUS, DATELINE)values(?,?,?,?,?)",
				s);
	}

	@Override
	public int readAtStatus(List<String> objectIds, long uid, byte objectType) {
		return update(
				"update TB_AT SET STATUS=? where AT_ID in ("
						+ $(objectIds).join() + ") and USER_ID=? and TYPE=?",
				AtConst.READ, uid, objectType);
	}

	@Override
	public int deleteCollect(long uid, String objectId, byte objectType) {
		return update(
				"delete from TB_COLLECT  where OBJECT_ID=? and OBJECT_TYPE=? and USER_ID=?",
				objectId, objectType, uid);
	}

	@Override
	public List<Collect> getcollect(long uid, String objectIds, byte objectType) {
		String sql = "select OBJECT_ID from TB_COLLECT where OBJECT_TYPE = ? and USER_ID = ? and OBJECT_ID IN ("
				+ objectIds + ");";
		return this.find(Collect.class, sql, objectType, uid);
	}

	@Override
	public String getSomeUsers(long school, long nowuser, int nums, int type) {
		String sql = "select u.user_id,real_name,u.name,expert,CREDITS,face,online,role,u.user_type,0 follow ,0  fans from TB_USER ";
		if (nowuser != 0) {
			sql = "select u.user_id,real_name,u.name,expert,CREDITS,face,role,u.user_type,`ONLINE`,(select count(1) from TB_FOLLOW where FANS_ID="
					+ nowuser
					+ " and TB_FOLLOW.FRIEND_ID=u.USER_ID) follow,(select count(1) from TB_FOLLOW where FRIEND_ID="
					+ nowuser
					+ " and TB_FOLLOW.FANS_ID=u.USER_ID) fans from TB_USER  ";
		}
		if (school != 0) {
			sql += "  ,TB_SCHOOL_MEMBER u  where u.USER_ID=TB_USER.USER_ID and school_id="
					+ school + " and  u.user_type=?  and u.status=1 ";
		} else {
			sql += "u  where u.user_type=? and status=1 ";
		}
		return query(sql + "  ORDER by `ONLINE` desc limit 0,?",
				mapSimpleUsers(), type, nums);
	}

	@Override
	public void expert(long userId, String expert) {
		/* 查找用户原有学科的积分 */
		Map ext = this.query(
				"select EXPERT,score from TB_USER_EXPERT where user_id=? ",
				new RowSetHandler<Map<String, Integer>>() {
					Map<String, Integer> map = new HashMap<String, Integer>();

					@Override
					public Map<String, Integer> populate(ResultSet rs)
							throws SQLException {
						while (rs.next()) {
							map.put(rs.getString(1), rs.getInt(2));
						}
						return map;
					}
				}, userId);
		String[] e = expert.split(",");
		List<Object[]> s = new ArrayList<Object[]>();
		for (int i = 0; i < e.length; i++) {
			s.add(new Object[] { userId, Long.parseLong(e[i]), ext.get(e[i]) });
		}
		/* 删除原有数据 */
		this.update("delete from TB_USER_EXPERT where user_id=?  ", userId);
		this.batchUpdate(
				"insert into TB_USER_EXPERT(USER_ID,EXPERT,score) values(?,?,?)",
				s);
	}

	@Override
	public List<User> getExpertUser(long grade, long kind, long schoolId,
			long userId) {
		String where = "";
		if (schoolId != 0) {
			where += " and exists (select 1 from TB_SCHOOL_MEMBER where TB_SCHOOL_MEMBER.USER_ID = TB_USER.USER_ID and TB_SCHOOL_MEMBER.SCHOOL_ID = "
					+ schoolId + ")";
		}
		if (grade != 0 && kind == 0) {
			where += " and GRADE = " + grade;
		}
		if (kind != 0) {
			where += " and EXPERT regexp '[[:<:]]" + kind + "[[:>:]]'";
		}
		String order = " order by ONLINE desc, FAVOR desc";
		return this
				.find("select USER_ID, (CASE WHEN (REAL_NAME = '') THEN NAME ELSE IFNULL(REAL_NAME,NAME) END) NAME, FACE from TB_USER where USER_ID != ? and USER_TYPE = ? and STATUS = ? "
						+ where + order + " limit 0,5", userId,
						UserConst.TEACHER, UserConst.STATUS_NORMAL);
	}

	@Override
	public String getEmail(long userId) {
		return getString("select email from TB_USER where user_id=?", userId);
	}

	@Override
	public boolean validateRecovery(String email, String activeCode) {
		return count(
				"select count(1) from TB_USER where email =? and active_code=?",
				email, activeCode) > 0;
	}

	@Override
	public int resetPassword(String email, String activeCode, String newPassword) {
		return update(
				"update TB_USER set password=?,active_code=? where email =? and active_code=?",
				newPassword, RandomGenerator.getRandomWord(32), email,
				activeCode);
	}

	@Override
	public void addScore(long userId, int score) {
		update("update TB_USER SET scores=scores+? where user_id=?", score,
				userId);
	}

	@Override
	public void addCredit(long userId, int credit) {
		update("update TB_USER SET credits=credits+? where user_id=?", credit,
				userId);
	}

	@Override
	public int addStudent(long teacherId, long studentId) {
		if (count(
				"select count(1) from TB_TEACHER_STUDENT where teacher_id=? and student_id=?",
				teacherId, studentId) == 0) {
			update("insert into TB_TEACHER_STUDENT(TEACHER_ID,STUDENT_ID,DATELINE) values(?,?,?)",
					teacherId, studentId, Q.now());
			update("update TB_USER SET students=students+1 where user_id=?",
					teacherId);
		}
		return 1;
	}

	@Override
	public void addAnswers(long userId, int answers) {
		update("update TB_USER SET answers=answers+? where user_id=?", answers,
				userId);
	}

	@Override
	public void addAdoptAnswers(long userId, int adopt) {
		update("update TB_USER SET correct_Answers=correct_answers+? where user_id=?",
				adopt, userId);
	}

	@Override
	public void addCourses(long userId, int courses) {
		update("update TB_USER SET courses=courses+? where user_id=?", courses,
				userId);
	}

	@Override
	public void addGoodCourses(long userId, int courses) {
		update("update TB_USER SET good_Courses=good_courses+? where user_id=?",
				courses, userId);
	}

	@Override
	public List<UserAddress> findAddress(long userId) {
		return this
				.find(UserAddress.class,
						"select * from TB_USER_ADDRESS where status>0 and user_id=? order by status desc",
						userId);
	}

	@Override
	public int deleteAddress(long id) {
		return update("update TB_USER_ADDRESS set status=0 where id=?", id);
	}

	@Override
	public UserAddress getAddress(long addressId) {
		return this.findFirst(UserAddress.class,
				"select * from TB_USER_ADDRESS where id=? ", addressId);
	}

	@Override
	public List<User> searchOherUser(long userId) {
		String sql = "SELECT t.USER_ID,IFNULL(t.REAL_NAME,t.NAME) NAME,t.STATUS,t.USER_TYPE from TB_USER t where t.USER_ID in(select BIND_ID from TB_USER_MUSER where USER_ID = ?) or t.USER_ID = ? ORDER BY t.regidit_time";
		return find(User.class, sql, userId, userId);
	}

	@Override
	public Page<User> query(int page, int size, Map<String, String[]> params)
			throws DataAccessException {
		long userid = $($(params.get("userid")).get()).toLong();// 编号
		String email = $($(params.get("email")).get()).toString();
		String sql = " SELECT t.USER_ID,IFNULL(t.REAL_NAME,t.NAME) NAME,t.EMAIL,t.STATUS,t.ROLE,t.REGIDIT_TIME,t.TEL";
		sql += " from TB_USER t ";
		String where = " where 1=1 ";
		if (userid != 0) {
			where += " and t.USER_ID = " + userid;
		}
		if (!Q.isBlank(email)) {
			where += " and t.EMAIL like '%" + email + "%'";
		}
		where += " ORDER BY t.REGIDIT_TIME desc";
		return findPage(User.class, sql + where,
				"SELECT count(USER_ID) from TB_USER t" + where, page, size);
	}

	@Override
	public Page<User> searchHotTeacher(int nums, long userid) {
		String where = "WHERE USER_TYPE = " + UserConst.TEACHER
				+ " and `STATUS` != 3";
		String order = "order by FAVOR desc, STUDENTS desc , COURSES desc, ANSWERS DESC,LAST_TIME desc";
		return search(0, userid, where, order, 1, nums);
	}

	@Override
	public boolean validatePassword(long userId, String password) {
		return count(
				"select count(1) from TB_USER where USER_ID =? and PASSWORD=?",
				userId, password) > 0;
	}
}
