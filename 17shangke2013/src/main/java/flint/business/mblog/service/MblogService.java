package flint.business.mblog.service;

import java.util.List;

import entity.Mblog;
import entity.MblogComment;
import flint.base.BaseService;
import flint.common.Page;

public interface MblogService extends BaseService {

	/**
	 * 取最新的三条记录（课修改配置），最多两条公告，一条微博,公告不足时微博补齐
	 * 
	 * @param schoolId
	 *            学校编号
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
	 * 发送微博消息
	 * 
	 * @param mblog
	 * @return 微博编号
	 */
	long post(Mblog mblog);

	/**
	 * 草稿
	 * 
	 * @param userId
	 * @return
	 */
	Mblog draft(long userId);

	/**
	 * 删除用户的微博(用户个人删除或管理员删除,uid为0的时候代表是管理员的操作)
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	int delete(long id, long userId, String memo);

	/**
	 * 转发用户微博
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	int forward(long id, long userId);

	/**
	 * @return
	 */
	int comment(MblogComment comment);

	/**
	 * 评论加载
	 * 
	 * @param id
	 * @param page
	 * @param size
	 * @return
	 */
	Page<MblogComment> comments(long id, long page, long size);

	/**
	 * 查找某个用户发布的某条微博信息
	 * 
	 * @param id
	 *            微博编号
	 * @param userId
	 *            用户编号
	 * @return
	 */
	Mblog item(long id, long userId);

	/**
	 * 查看单条微博的信息
	 * 
	 * @param id
	 *            微博编号
	 * @param schoolId
	 *            学校编号
	 * @param groupId
	 *            组编号
	 * @return
	 */
	Mblog item(long id, long schoolId, long groupId);

	/**
	 * 删除微博评论
	 * 
	 * @param questionId
	 * @return
	 */
	int deleteComment(long id, long mid);
}
