package flint.business.group.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kiss.sql.SQLBuilder;
import kiss.sql.Select;

import org.springframework.stereotype.Repository;

import entity.Group;
import flint.base.BaseDAOImpl;
import flint.business.constant.GroupConst;
import flint.business.constant.UserConst;
import flint.business.group.dao.GroupDAO;
import flint.common.Page;
import flint.jdbc.RowMapper;
import flint.jdbc.RowSetHandler;

@Repository
public class GroupDAOImpl extends BaseDAOImpl<Group, Long> implements GroupDAO {

	public final static String queryString = "SELECT GROUP_ID,SCHOOL_ID,NAME,TYPE,STATUS,CATEGORY_ID,TAG,INTRO,CREATOR,CREATE_TIME,MASTER,MEMBERS,TOPICS,TALKS,VERIFY,CERTIFICATE,MEMO  FROM  TB_GROUP ";

	private RowMapper<Group> mapGroup() {
		return new RowMapper<Group>() {
			@Override
			public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
				Group group = new Group();
				group.setCategoryId(rs.getLong("CATEGORY_ID"));
				group.setCertificate(rs.getString("CERTIFICATE"));
				group.setCreateTime(rs.getInt("CREATE_TIME"));
				group.setCreator(rs.getLong("CREATOR"));
				group.setGroupId(rs.getLong("GROUP_ID"));
				group.setIntro(rs.getString("INTRO"));
				group.setMaster(rs.getLong("MASTER"));
				group.setMembers(rs.getInt("MEMBERS"));
				group.setMemo(rs.getString("MEMO"));
				group.setName(rs.getString("Name"));
				group.setSchoolId(rs.getLong("SCHOOL_ID"));
				group.setStatus(rs.getByte("STATUS"));
				group.setTag(rs.getString("TAG"));
				group.setTalks(rs.getInt("TALKS"));
				group.setTopics(rs.getInt("TOPICS"));
				group.setType(rs.getByte("TYPE"));
				group.setVerify(rs.getByte("VERIFY"));
				return group;
			}

		};
	}

	public Page<Group> search(Select select, long page, long size) {
		return this.queryPage(select.toString(),
				"select count(GROUP_ID) from TB_GROUP " + select.getWhere(),
				mapGroup(), page, size, select.getParameters());
	}

	@Override
	public Page<Group> findGroup(long userid, byte type, long schoolId,
			int year, long page, int size) {
		Select s = SQLBuilder.select(queryString);
		s.may(" where SCHOOL_ID = ? and TYPE = ?   and STATUS !=? ", schoolId,
				type, GroupConst.GROUP_DELETE);
		s.may(year != 0, " and year= ? ", year);
		s.orderBy(" CREATE_TIME desc ");
		return search(s, page, size);
	}

	@Override
	public List<Group> findGroupList(long schoolId, byte type) {
		return this.find(queryString
				+ " where SCHOOL_ID = ? and STATUS != ? and TYPE = ?",
				schoolId, GroupConst.GROUP_DELETE, type);
	}

	@Override
	public int updateGroup(Group group) {
		return update(
				"update TB_GROUP set NAME=?, INTRO=?, MEMO=? where GROUP_ID=?",
				group.getName(), group.getIntro(), group.getMemo(),
				group.getGroupId());
	}

	@Override
	public int deleteGroup(long id, boolean flay) {
		update("delete from TB_GROUP_MEMBER where GROUP_ID=?", id);
		if (flay) {
			return update("delete from TB_GROUP where GROUP_ID = ?", id);
		}
		return update("update TB_GROUP set STATUS = " + GroupConst.GROUP_DELETE
				+ " where GROUP_ID = " + id);
	}

	@Override
	public boolean checkGroupName(String name, long schoolId) {
		return count(
				"select count(1) from TB_GROUP  where school_id=? and name=?  ",
				schoolId, name) > 0;
	}

	@Override
	public List<Long> findMembers(long groupId, int page, int size) {
		long start = (page - 1) * size;
		long end = start + size;
		return query(
				"SELECT  USER_ID  FROM   TB_GROUP_MEMBER where group_id=? limit ?,?",
				new RowSetHandler<List<Long>>() {
					@Override
					public List<Long> populate(ResultSet rs)
							throws SQLException {
						List<Long> result = new ArrayList<Long>();
						while (rs.next()) {
							result.add(rs.getLong(1));
						}
						return result;
					}
				}, groupId, start, end);
	}

	@Override
	public List<Group> my(long userId, byte type) {
		String sql = queryString
				+ " where EXISTS(select 1 from TB_GROUP_MEMBER  where TB_GROUP_MEMBER.GROUP_ID=TB_GROUP.GROUP_ID  and TB_GROUP.`STATUS`=? and TB_GROUP.TYPE=? and TB_GROUP_MEMBER.USER_ID=? and TB_GROUP_MEMBER.`STATUS`=? )";
		return this.query(sql, mapGroup(), GroupConst.GROUP_OK, type, userId,
				GroupConst.MEMBER_OK);
	}

	@Override
	public String getRole(long userId, final long groupId) {
		String sql = "select s.USER_TYPE,g.ROLE FROM TB_GROUP_MEMBER g, TB_SCHOOL_MEMBER s where g.USER_ID=s.USER_ID and g.group_id=? and g.user_id=? and g.status=?";
		return this.query(sql, new RowSetHandler<String>() {

			@Override
			public String populate(ResultSet rs) throws SQLException {
				if (rs.next()) {
					String domain = "group" + groupId + ":";
					StringBuilder sb = new StringBuilder();
					byte utype = rs.getByte("USER_TYPE");
					byte role = rs.getByte("role");
					if (utype == UserConst.TEACHER) {
						sb.append(domain + "teacher,");
					} else if (utype == UserConst.ADMIN) {
						sb.append(domain + "admin,");
					} else if (utype == UserConst.STUDENT) {
						sb.append(domain + "student,");
					}
					if (role == GroupConst.MASTER) {
						sb.append(domain + "master,");
					} else if (role == GroupConst.SQUAD) {
						sb.append(domain + "squad,");
					} else if (role == GroupConst.VICE_SQUAD) {
						sb.append(domain + "vice_squad,");
					}
					int len = sb.length();
					if (len > 0)
						return sb.substring(0, len - 1);
					return "";
				}
				return null;
			}

		}, groupId, userId, GroupConst.MEMBER_OK);
	}

	@Override
	public int announce(int groupId, String announce) {
		return this.update("update TB_GROUP set announce=? where group_id=?",
				announce, groupId);
	}

	@Override
	public Map<String, String> getPosition(long groupId) {
		String sql = "SELECT  GROUP_CONCAT(USER_ID) USERS,ROLE from TB_GROUP_MEMBER where ROLE!=0 and group_id=? group by ROLE";
		return this.query(sql, new RowSetHandler<Map<String, String>>() {

			@Override
			public Map<String, String> populate(ResultSet rs)
					throws SQLException {
				Map<String, String> map = new HashMap<String, String>();
				while (rs.next()) {
					map.put("p" + rs.getString("ROLE"), rs.getString("USERS"));
				}
				return map;
			}

		}, groupId);
	}

	@Override
	public List<Integer[]> findGroupYears(long schoolId, byte type) {
		return this
				.query("SELECT  DISTINCT `YEAR`,count(1) from TB_GROUP where SCHOOL_ID=? and TYPE=? and status!=?  GROUP BY `YEAR`  order by YEAR desc",
						new RowMapper<Integer[]>() {
							@Override
							public Integer[] mapRow(ResultSet rs, int rowNum)
									throws SQLException {
								return new Integer[] { rs.getInt(1),
										rs.getInt(2) };
							}
						}, schoolId, type, GroupConst.GROUP_DELETE);
	}
}
