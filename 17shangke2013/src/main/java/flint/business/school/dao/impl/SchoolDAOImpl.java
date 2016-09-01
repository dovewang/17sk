package flint.business.school.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kiss.util.Q;

import org.springframework.stereotype.Repository;

import entity.Account;
import entity.School;
import entity.SchoolAddress;
import entity.User;
import entity.UserExtends;
import flint.base.BaseDAOImpl;
import flint.business.constant.AccountConst;
import flint.business.constant.GroupConst;
import flint.business.constant.SchoolConst;
import flint.business.constant.UserConst;
import flint.business.school.dao.SchoolDAO;
import flint.common.Page;
import flint.jdbc.RowSetHandler;

@Repository
public class SchoolDAOImpl extends BaseDAOImpl<School, Long> implements
		SchoolDAO {
	@Override
	public long getSchoolIdByWebmaster(long webmaster) {
		return this.getLong(
				"select SCHOOL_ID from TB_SCHOOL where webmaster=?", webmaster);
	}

	@Override
	public School findSchoolByDomain(String domain) {
		return findFirst(School.class,
				"select * from TB_SCHOOL where domain=?   ", domain);
	}

	@Override
	public Page<Map<String, Object>> findClass(long schoolId, long page,
			int size) {
		return this
				.findPageForMap(
						"select * from TB_GROUP where SCHOOL_ID = ? and STATUS != ? and TYPE = ?",
						page, size, schoolId, GroupConst.GROUP_DELETE,
						GroupConst.CLASS);
	}

	@Override
	public int updateUser(long userid, String username, String tel, String id) {
		/* 更新用户基本信息表 */
		if (!Q.isBlank(tel)) {
			// update("update TB_USER set tel = ? where user_id=? ", tel,
			// userid);
		}
		update("update TB_SCHOOL_MEMBER set id=?, name=? where user_id=? ", id,
				username, userid);
		return 1;
	}

	/**
	 * 添加学校用户
	 * 
	 * @return 返回的新建用户状态 1：(成功，原来存在主站并存在该学校的用户)
	 *         2：（成功，新的用户）3:(成功，存在于主站，但是不存在在这个学校)
	 */
	@Override
	public int addSchoolUser(long userid, String name, String email,
			String tel, byte type, String id, long schoolId, long classid,
			boolean flay, String activeCode) {
		int result = 1;
		if (!flay) {
			/* 添加到用户基本信息表 */
			User u1 = new User();
			u1.setEmail(email);
			u1.setRealName(name);
			u1.setTel(tel);
			u1.setStatus(UserConst.STATUS_NORMAL);
			u1.setActiveCode(activeCode);
			if (type == UserConst.ENTERPRISE) {// 是否添加的是企业账号
				u1.setName(name);
				u1.setUserType(UserConst.ENTERPRISE);
			} else {// 学校管理员添加的学校内部管理员账户(默认给的外部角色为教师)
				u1.setUserType(type == SchoolConst.TYPE_ADMIN ? UserConst.TEACHER
						: type);
			}
			u1.setFace(this.configHelper.getString("face", "default",
					"/image.server/avatar/default.jpg"));
			userid = insert(u1);
			// 企业账户添加设置网站管理员值
			if (type == UserConst.ENTERPRISE) {
				update("update TB_SCHOOL set WEBMASTER = ? where SCHOOL_ID = ?",
						userid, schoolId);
				type = SchoolConst.TYPE_ADMIN;// (默认给的内部角色为管理员)
			}
			/* 初始化用户扩展信息表 */
			UserExtends entity = new UserExtends();
			entity.setUserId(userid);
			insert(entity);
			/* 初始化用户账户 ,初始用户注册，默认为账户密码，用户初次充值时需要提醒用户变更账户密码 */
			Account account = new Account();
			account.setUserId(userid);
			account.setPassword(u1.getPassword());
			account.setStatus(AccountConst.STATUS_OK);
			account.setCreateTime(Q.now());
			insert(account);
			result = 2;
		}
		if (findSchoolMember(schoolId, userid) == null) {
			/* 用户学校对应关系表 */
			update("insert into TB_SCHOOL_MEMBER(SCHOOL_ID,USER_ID,NAME,ID,`STATUS`,USER_TYPE) values(?,?,?,?,?,?)",
					schoolId, userid, name, id, GroupConst.MEMBER_OK, type);
			if (result == 1)
				result = 3;
		}
		if (classid != 0) {// 为0情况是不属于任何一个分组
			addClassUser(userid, classid);
		}
		return result;
	}

	@Override
	public int addClassUser(long userid, long classid) {
		/* 用户学校班级对应关系表 */
		return update(
				"insert into TB_GROUP_MEMBER(GROUP_ID,USER_ID,`STATUS`,ROLE) values(?,?,?,?)",
				classid, userid, GroupConst.MEMBER_OK, GroupConst.MEMBER);
	}

	@Override
	public Map<String, Object> findSchoolMember(long schoolId, long userId) {
		String sql = "select * from TB_SCHOOL_MEMBER a where a.SCHOOL_ID = ? and a.USER_ID = ?";
		List<Map<String, Object>> map = this.findForMap(sql, schoolId, userId);
		if (map.size() > 0) {
			return map.get(0);
		} else {
			return null;
		}
	}

	@Override
	public int setRole(long classid, long userid, byte role) {
		update("update TB_GROUP_MEMBER set ROLE = ? where GROUP_ID = ? and ROLE = ?",
				GroupConst.MEMBER, classid, role);
		return update(
				"update TB_GROUP_MEMBER set ROLE = ? where GROUP_ID = ? and USER_ID = ?",
				role, classid, userid);
	}

	@Override
	public int lockUser(long schoolId, long userId) {
		return update(
				"update TB_SCHOOL_MEMBER set status=? where SCHOOL_ID=? and USER_ID=?",
				GroupConst.MEMBER_LOCK, schoolId, userId);
	}

	@Override
	public int unlockUser(long schoolId, long userId) {
		return update(
				"update TB_SCHOOL_MEMBER set status=? where SCHOOL_ID=? and USER_ID=?",
				GroupConst.MEMBER_OK, schoolId, userId);
	}

	@Override
	public int deleteUser(long classid, long userId) {
		return update(
				"delete from TB_GROUP_MEMBER where GROUP_ID=? and USER_ID=?",
				classid, userId);
	}

	@Override
	public Map<String, Object> getClassUser(long groupId, long userId) {
		String sql = "SELECT b.ROLE,c.USER_TYPE,a.GROUP_ID,a.NAME AS CLASS_NAME, c.ID, c.`STATUS`,c.USER_ID,c.NAME,d.EMAIL,d.TEL FROM TB_GROUP a,TB_GROUP_MEMBER b,TB_SCHOOL_MEMBER c,TB_USER d where a.GROUP_ID=b.GROUP_ID and b.USER_ID = c.USER_ID and c.USER_ID = d.USER_ID ";
		List<Map<String, Object>> map = this.findForMap(sql
				+ " and a.GROUP_ID=? and c.user_id=? ", groupId, userId);
		if (map.size() > 0) {
			return map.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Object> getClassName(long classId) {
		String sql = "SELECT a.GROUP_ID,a.NAME AS CLASS_NAME FROM TB_GROUP a where a.GROUP_ID=?";
		List<Map<String, Object>> map = this.findForMap(sql, classId);
		if (map.size() > 0) {
			return map.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Page<Map<String, Object>> search(long schoolId, long classId,
			byte type, long page, long size) {
		String sql = "SELECT b.ROLE,c.USER_TYPE,(SELECT d.GROUP_ID from TB_GROUP d,TB_GROUP_MEMBER e where e.GROUP_ID=d.GROUP_ID and e.USER_ID=b.USER_ID and e.GROUP_ID = ?) AS GROUP_ID,(SELECT d.NAME from TB_GROUP d,TB_GROUP_MEMBER e where e.GROUP_ID=d.GROUP_ID and e.USER_ID=b.USER_ID and e.GROUP_ID = ?) AS CLASS_NAME, c.ID, c.`STATUS`,c.USER_ID,c.NAME,d.EMAIL,d.TEL FROM TB_GROUP_MEMBER b,TB_SCHOOL_MEMBER c,TB_USER d where b.USER_ID = c.USER_ID and c.USER_ID = d.USER_ID ";
		String where = " and b.STATUS!=? and c.SCHOOL_ID=? and b.GROUP_ID = ?";
		if (type != 0) {// 0代表查找所有 1学生，2老师，4管理员
			where += " and c.USER_TYPE = " + type;
		}
		return findPageForMap(sql + where, page, size, classId, classId,
				GroupConst.MEMBER_DELETE, schoolId, classId);
	}

	@Override
	public Page<Map<String, Object>> searchUserUnGroup(long schoolId,
			byte type, long page, long size) {
		String sql = "SELECT a.USER_ID,a.ID,a.NAME,a.USER_TYPE,a.STATUS,b.EMAIL,b.TEL from TB_SCHOOL_MEMBER a,TB_USER b WHERE NOT EXISTS(SELECT 1 FROM TB_GROUP_MEMBER, TB_GROUP WHERE TB_GROUP.GROUP_ID = TB_GROUP_MEMBER.GROUP_ID and TB_GROUP_MEMBER.USER_ID = a.USER_ID and TB_GROUP.TYPE = ? and TB_GROUP.SCHOOL_ID = ?) AND a.USER_ID = b.USER_ID AND a.SCHOOL_ID=?";
		return findPageForMap(sql, page, size, type, schoolId, schoolId);
	}

	@Override
	public Map<String, Object> findUserByEmail(String email) {
		List<Map<String, Object>> map = this.findForMap(
				"SELECT USER_ID, EMAIL, TEL FROM TB_USER where EMAIL=?", email);
		if (map.size() > 0) {
			return map.get(0);
		} else {
			return null;
		}
	}

	@Override
	public String checkEmail(String email, long schoolId) {
		return query(
				"SELECT COUNT(1) as global ,(SELECT COUNT(1)  FROM TB_SCHOOL_MEMBER where SCHOOL_ID=? and TB_USER.USER_ID=TB_SCHOOL_MEMBER.USER_ID) as school FROM TB_USER where  EMAIL=? ",
				new RowSetHandler<String>() {
					@Override
					public String populate(ResultSet rs) throws SQLException {
						StringBuilder sb = new StringBuilder("{");
						if (rs.next()) {
							sb.append("\"global\":")
									.append(rs.getBoolean("global"))
									.append(",");
							sb.append("\"school\":").append(
									rs.getBoolean("school"));
						}
						return sb.append("}").toString();
					}

				}, schoolId, email);
	}

	@Override
	public boolean checkClassUser(long schoolid, long classid, long userid) {
		String sql = "select count(1) from TB_GROUP t1,TB_GROUP_MEMBER t2 where t1.SCHOOL_ID = ? and t1.GROUP_ID = t2.GROUP_ID and t2.GROUP_ID = ? and t2.USER_ID = ?";
		int count = getInteger(sql, schoolid, classid, userid);
		return count > 0 ? true : false;
	}

	@Override
	public Page<School> list(long page, long size) {
		return findPage("select * from TB_SCHOOL ", page, size);
	}

	@Override
	public List<SchoolAddress> findAddress(long schooId) {
		return find(
				SchoolAddress.class,
				"select * from TB_SCHOOL_ADDRESS  where owner=? and status=1 order by dateline desc",
				schooId);
	}

	@Override
	public int deleteAddress(long id) {
		return update("update TB_SCHOOL_ADDRESS set status=9 where id=?", id);
	}

	@Override
	public SchoolAddress getAddress(long id) {
		return this.findFirst(SchoolAddress.class,
				"select * from TB_SCHOOL_ADDRESS where id=? ", id);
	}

	@Override
	public int updataSchoolStatus(String domain, byte status) {
		return update("update TB_SCHOOL set STATUS = ? where DOMAIN = ?",
				status, domain);
	}

	@Override
	public int verifySchool(long schoolId, String memo, byte status) {
		return update(
				"update TB_SCHOOL set STATUS = ?, MEMO = ? where SCHOOL_ID = ?",
				status, memo, schoolId);
	}

	@Override
	public String timetable(long schoolId) {
		return this.query("select * from TB_TIMETABLE where school_id=? ",
				new RowSetHandler<String>() {
					@Override
					public String populate(ResultSet rs) throws SQLException {
						StringBuilder sb = new StringBuilder("{");
						if (rs.next()) {
							sb.append("\"id\":\"").append(rs.getString("id"))
									.append("\",");
							sb.append("\"start\":").append(rs.getInt("start"))
									.append(",");
							sb.append("\"end\":").append(rs.getInt("end"))
									.append(",");
							sb.append("\"list\":").append(
									rs.getString("detail"));
						}
						return sb.append("}").toString();
					}

				}, schoolId);
	}

	@Override
	public int saveTimetable(long schoolId, boolean isNew, String timetable) {
		int time = Q.now();
		if (isNew) {
			return update(
					"INSERT INTO TB_TIMETABLE(`NAME`,SCHOOL_ID,`START`,DETAIL) values(?,?,?,?)",
					"作息时间表", schoolId, time, timetable);
		}
		/* 先插入历史记录 */
		update("INSERT INTO TB_TIMETABLE_HISTORY SELECT  ID,`NAME`,SCHOOL_ID,`START`,"
				+ time
				+ " end ,DETAIL, MEMO from TB_TIMETABLE where school_id=?",
				schoolId);
		/* 更新到新的状态 */
		return update(
				"update TB_TIMETABLE set start=?,DETAIL=? where school_id=?",
				time, timetable, schoolId);
	}
}
