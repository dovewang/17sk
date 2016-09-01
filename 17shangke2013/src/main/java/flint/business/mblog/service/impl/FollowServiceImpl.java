package flint.business.mblog.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.User;
import flint.base.BaseServiceImpl;
import flint.business.constant.AtConst;
import flint.business.mblog.dao.FollowDAO;
import flint.business.mblog.service.FollowService;
import flint.business.user.service.UserService;
import flint.common.Page;
import flint.util.NumberHelper;

@Service
public class FollowServiceImpl extends BaseServiceImpl implements FollowService {

	@Autowired
	private FollowDAO dao;

	@Autowired
	private UserService userService;

	@Override
	public int focus(long follower, long userId) {
		/* at 通知用户新粉丝 */
		dao.focus(follower, userId);
		/* userId 为粉丝 */
		return userService.at(userId, AtConst.FANS, String.valueOf(follower));
	}

	@Override
	public int blur(long follower, long userId) {
		return dao.blur(follower, userId);
	}

	@Override
	public int group(long follower, long userId, long groupId) {
		return dao.group(follower, userId, groupId);
	}

	@Override
	public Page<User> fans(long userId, long page, long size) {
		return dao.fans(userId, page, size);
	}

	@Override
	public Page<User> followers(long userId, long page, long size) {
		return dao.followers(userId, page, size);
	}

	@Override
	public Page<User> recommend(long userId) {
		return dao.recommend(userId);
	}

	@Override
	public List<User> guess(String focus, long userId, long page, long size) {
		return dao.guess(focus, userId, page, size);
	}

	@Override
	public Page<User> getFansAndAtStatus(long userId, long page, long size) {
		Page<User> pageList = dao.getFansAndAtStatus(userId, page, size);
		List<String> Ids = new ArrayList<String>();
		Iterator<User> it = pageList.getResult().iterator();
		while (it.hasNext()) {
			User u = it.next();
			if (NumberHelper.toByte(u.getMemo()) == AtConst.NO_READ) {
				String qid = String.valueOf(u.getUserId());
				Ids.add(qid);
			}
		}
		// At信息置为已读
		if (Ids.size() > 0) {
			userService.readAtStatus(Ids, userId, AtConst.FANS);
		}
		return pageList;
	}

}
