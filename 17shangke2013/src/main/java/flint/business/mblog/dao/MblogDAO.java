package flint.business.mblog.dao;

import java.util.List;

import entity.Mblog;
import entity.MblogComment;
import flint.base.BaseDAO;
import flint.common.Page;

public interface MblogDAO extends BaseDAO<Mblog, Long> {

	/**
	 * 获取最新发布的公告
	 * 
	 * @param schoolId
	 *            学校编号
	 * @param nums
	 *            最大记录数
	 * @return
	 */
	List<Mblog> getTop(long schoolId, int nums);

	/**
	 * 微博消息
	 * 
	 * @param schoolId
	 *            学校编号
	 * @param groupId
	 *            圈子编号
	 * @param userId
	 *            用户编号
	 * @param mygroup
	 *            用户定义的分组编号
	 * @param page
	 *            当前页
	 * @param size
	 *            分页大小
	 * @return
	 */
	Page<Mblog> list(long schoolId, long groupId, long userId, long mygroup,
			long page, long size);

	/**
	 * 
	 * @param comment
	 * @return
	 */
	int comment(MblogComment comment);

	int forward(long id, long userId);

	int delete(long id, long userId, String memo);

	Page<MblogComment> comments(long id, long page, long size);

	/**
	 * 删除微博评论
	 * 
	 * @param questionId
	 * @return
	 */
	int deleteComment(long id, long mid);

	Mblog item(long id, long userId);
}
