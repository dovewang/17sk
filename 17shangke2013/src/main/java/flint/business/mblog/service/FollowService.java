package flint.business.mblog.service;

import java.util.List;

import entity.User;
import flint.base.BaseService;
import flint.common.Page;

public interface FollowService extends BaseService {

	int focus(long follower, long userId);

	int blur(long follower, long userId);

	int group(long follower, long userId, long groupId);

	Page<User> fans(long userId, long page, long size);

	Page<User> recommend(long userId);

	List<User> guess(String focus, long userId, long page, long size);

	Page<User> followers(long userId, long page, long size);

	/**
	 * 获取我的粉丝和At信息
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	Page<User> getFansAndAtStatus(long userId, long page, long size);
}
