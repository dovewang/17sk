package flint.business.mblog.dao.impl;

import java.util.List;

import kiss.util.Q;

import org.springframework.stereotype.Repository;

import entity.Mblog;
import entity.MblogComment;
import flint.base.BaseDAOImpl;
import flint.business.mblog.dao.MblogDAO;
import flint.common.Page;

@Repository
public class MblogDAOImpl extends BaseDAOImpl<Mblog, Long> implements MblogDAO {

	@Override
	public List<Mblog> getTop(long schoolId, int nums) {
		return find(
				"select * from TB_MBLOG where SCHOOL_ID= ?   ORDER BY  dateline DESC  LIMIT 0,?",
				schoolId, nums);
	}

	@Override
	public Page<Mblog> list(long schoolId, long groupId, long userId,
			long mygroup, long page, long size) {
		String sql = "select * from TB_MBLOG where school_id=? and group_id=?";
		String count = "select count(1) from  TB_MBLOG where school_id=?  and group_id=? ";
		if (userId != 0) {
			/* 目前只有分组1:我关注的 */
			if (mygroup != 0) {
				sql = sql
						+ " and EXISTS(SELECT 1 from TB_FOLLOW where TB_FOLLOW.FANS_ID="
						+ userId
						+ " and TB_FOLLOW.FRIEND_ID=TB_MBLOG.USER_ID)  ";
				count = count
						+ " and EXISTS(SELECT 1 from TB_FOLLOW where TB_FOLLOW.FANS_ID="
						+ userId
						+ " and TB_FOLLOW.FRIEND_ID=TB_MBLOG.USER_ID)  ";
			} else {
				// 我自己
				sql = sql + "   and user_id=" + userId;
				count = count + "  and user_id=" + userId;
			}
		}

		return findPage(Mblog.class, sql + " order by  dateline DESC", count,
				page, size, schoolId, groupId);
	}

	@Override
	public int comment(MblogComment comment) {
		update("update TB_MBLOG set comments=comments+1 where id=?",
				comment.getMid());
		return this.save(comment);
	}

	@Override
	public int forward(long id, long userId) {
		return 0;
	}

	@Override
	public int delete(long id, long userId, String memo) {
		/* 删除原有微博 */
		update("insert into  TB_MBLOG_DELETE select ?,?,?, TB_MBLOG.* from  TB_MBLOG  where id=? ",
				Q.now(), userId, memo, id);
		return update("delete from TB_MBLOG   where id=?  ", id);
	}

	@Override
	public Page<MblogComment> comments(long id, long page, long size) {
		return findPage(
				MblogComment.class,
				"select * from TB_MBLOG_COMMENT where mid=? order by create_time desc ",
				"select count(id) from TB_MBLOG_COMMENT where mid=? ", page,
				size, id);
	}

	@Override
	public int deleteComment(long id, long mid) {
		update("delete from TB_MBLOG_COMMENT where ID=? ", id);
		return update("update TB_MBLOG set comments=comments-1 where id=?", mid);
	}

	@Override
	public Mblog item(long id, long userId) {
		return this.findFirst(Mblog.class,
				"select * from TB_MBLOG where id=? and USER_ID=? ", id, userId);
	}

}
