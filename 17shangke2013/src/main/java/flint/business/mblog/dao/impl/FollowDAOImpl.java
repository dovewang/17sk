package flint.business.mblog.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import kiss.util.Q;

import org.springframework.stereotype.Repository;

import entity.Follow;
import entity.User;
import flint.base.BaseDAOImpl;
import flint.business.constant.AtConst;
import flint.business.mblog.dao.FollowDAO;
import flint.common.Page;
import flint.jdbc.RowMapper;

@Repository
public class FollowDAOImpl extends BaseDAOImpl<Follow, Long[]> implements
		FollowDAO {

	@Override
	public int focus(long follower, long userId) {
		return update(
				"insert into TB_FOLLOW(FRIEND_ID,FANS_ID,DATETIME,GROUP_ID,MEMO) values(?,?,?,0,'')",
				follower, userId, Q.now());
	}

	@Override
	public int blur(long follower, long userId) {
		return update("delete from TB_FOLLOW where FRIEND_ID=? and FANS_ID=?",
				follower, userId);
	}

	@Override
	public int group(long follower, long userId, long groupId) {
		return update(
				"update TB_FOLLOW set GROUP_ID=? where FRIEND_ID=? and FANS_ID=?",
				groupId, follower, userId);
	}

	@Override
	public Page<User> fans(long userId, long page, long size) {
		return this
				.findPage(
						User.class,
						"select * from TB_USER where exists(select 1 from TB_FOLLOW where FRIEND_ID=? and TB_FOLLOW.FANS_ID=TB_USER.USER_ID)",
						page, size, userId);
	}

	@Override
	public Page<User> recommend(long userId) {
		return null;
	}

	@Override
	public Page<User> followers(long userId, long page, long size) {
		return this
				.findPage(
						User.class,
						"select * from TB_USER where exists(select 1 from TB_FOLLOW where FANS_ID=? and TB_FOLLOW.FRIEND_ID=TB_USER.USER_ID)",
						page, size, userId);
	}

	@Override
	public List<User> guess(String focus, long userId, long page, long size) {
		String queryString = " select  user_id,REAL_NAME,name,expert FROM   TB_USER   where 1=1 ";

		/* 不显示本人的关注及已关注的 */
		if (userId != 0) {
			queryString += " and user_id!=" + userId
					+ " and  not exists(select 1 from TB_FOLLOW where FANS_ID="
					+ userId + " and TB_FOLLOW.FRIEND_ID=TB_USER.USER_ID)";
		}
		StringBuilder sql = new StringBuilder(queryString);
		/* 添加用户擅长查询条件 */
		if (!Q.isEmpty(focus)) {
			String[] tmp = focus.split(",");
			sql.append("   and  ( expert like '%" + tmp[0] + "%'");
			for (int i = 1; i < tmp.length; i++) {
				sql.append("or  expert like '%" + tmp[i] + "%'");
			}
			sql.append(")   ");
		}

		sql.append("   order by  REGIDIT_TIME desc");
		List<User> user = this.queryPage(sql.toString(), new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setUserId(rs.getLong("user_id"));
				user.setName(Q.isBlank(rs.getString("REAL_NAME"),
						rs.getString("name")));
				user.setExpert(rs.getString("expert"));
				return user;
			}

		}, page, size).getResult();
		if (user.size() < size) {
			List<User> b = this.queryPage(
					queryString + "  order by  REGIDIT_TIME desc ",
					new RowMapper<User>() {
						@Override
						public User mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							User user = new User();
							user.setUserId(rs.getLong("user_id"));
							user.setName(Q.isBlank(rs.getString("REAL_NAME"),
									rs.getString("name")));
							user.setExpert(rs.getString("expert"));
							return user;
						}
					}, page, size - user.size()).getResult();
			user.addAll(b);
		}
		return user;
	}

	@Override
	public Page<User> getFansAndAtStatus(long userId, long page, long size) {
		return this
				.findPage(
						User.class,
						"select TB_USER.USER_ID, TB_USER.FACE, TB_USER.EXPERIENCE, TB_USER.REAL_NAME, TB_USER.NAME," +
						" ifnull((select `STATUS` from TB_AT where TB_USER.USER_ID = TB_AT.AT_ID and TB_AT.USER_ID = ? and TB_AT.TYPE = "+AtConst.FANS+"),1) AS MEMO" +
						" from TB_USER where exists(select 1 from TB_FOLLOW where FRIEND_ID=? and TB_FOLLOW.FANS_ID=TB_USER.USER_ID) " +
						" order by MEMO ASC",//按未读排在前面
						page, size, userId, userId);
	}
}
