package flint.business.mblog.dao;

import java.util.List;

import entity.Follow;
import entity.User;
import flint.base.BaseDAO;
import flint.common.Page;

public interface FollowDAO extends BaseDAO<Follow, Long[]> {

	/**
	 * 
	 * @param follower
	 * @param userId
	 * @return
	 */
	int focus(long follower, long userId);

	int blur(long follower, long userId);

	int group(long follower, long userId, long groupId);
	
	/**
	 * 获取我的粉丝和At信息
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	Page<User> getFansAndAtStatus(long userId, long page, long size);

	Page<User> fans(long userId, long page, long size);

	Page<User> recommend(long userId);

	Page<User> followers(long userId, long page, long size);

	List<User> guess(String focus, long userId, long page, long size);

}
