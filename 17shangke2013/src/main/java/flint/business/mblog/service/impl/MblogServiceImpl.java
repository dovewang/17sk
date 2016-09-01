package flint.business.mblog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Mblog;
import entity.MblogComment;
import flint.base.BaseServiceImpl;
import flint.business.mblog.dao.MblogDAO;
import flint.business.mblog.service.MblogService;
import flint.business.message.CometdService;
import flint.common.Page;

@Service
public class MblogServiceImpl extends BaseServiceImpl implements MblogService {

	@Autowired
	private MblogDAO dao;

	/* cometd实时消息发布 */
	@Autowired
	private CometdService cometdService;

	@Override
	public List<Mblog> getTop(long schoolId, int nums) {
		return dao.getTop(schoolId, nums);
	}

	@Override
	public Page<Mblog> list(long schoolId, long groupId, long userId,
			long mygroup, long page, long size) {
		return dao.list(schoolId, groupId, userId, mygroup, page, size);
	}

	@Override
	public long post(Mblog mblog) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("type", "mblog");
		data.put("poster", mblog.getUserId());
		cometdService.broadcast(mblog.getSchoolId(), mblog.getGroupId(), data);// 广播消息
		return dao.insert(mblog);
	}

	@Override
	public int delete(long id, long userId, String memo) {
		return dao.delete(id, userId, memo);
	}

	@Override
	public int forward(long id, long userId) {
		return dao.forward(id, userId);
	}

	@Override
	public int comment(MblogComment comment) {
		return dao.comment(comment);
	}

	@Override
	public Page<MblogComment> comments(long id, long page, long size) {
		return dao.comments(id, page, size);
	}

	@Override
	public int deleteComment(long id, long mid) {
		return dao.deleteComment(id, mid);
	}

	@Override
	public Mblog item(long id, long userId) {
		return dao.item(id, userId);
	}

	@Override
	public Mblog item(long id, long schoolId, long groupId) {
		return null;
	}

	@Override
	public Mblog draft(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
