package flint.business.group.service.impl;

import java.util.List;
import java.util.Map;

import kiss.security.Identity;
import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import entity.Group;
import flint.base.BaseServiceImpl;
import flint.business.constant.GroupConst;
import flint.business.group.dao.GroupDAO;
import flint.business.group.service.GroupService;
import flint.common.Page;
import flint.exception.ApplicationException;

@Service
public class GroupServiceImpl extends BaseServiceImpl implements GroupService {

	@Autowired
	private GroupDAO dao;

	@Override
	public List<Group> my(long userId, byte type) {
		return dao.my(userId, type);
	}

	@Override
	public Page<Group> search(long schooId, long page, int size) {
		return null;
	}

	@Override
	public Map<String, Object> get(long groupId) {
		Map<String, Object> map = new ModelMap();
		map.put("group", getGroup(groupId));
		/* 获取圈子内部用户职务信息 */
		map.put("position", dao.getPosition(groupId));
		return map;
	}

	public Group getGroup(long groupId) {
		return dao.findById(groupId);
	}

	@Override
	public List<Long> findMembers(long groupId, int page, int size) {
		return dao.findMembers(groupId, page, size);
	}

	@Override
	public long apply(Group group) {
		return 0;
	}

	@Override
	public boolean checkGroupName(String name, long schoolId) {
		return dao.checkGroupName(name, schoolId);
	}

	@Override
	public int update(Group group) {
		return 0;
	}

	@Override
	public void joinApply(long groupId, long userId) {

	}

	@Override
	public void joinCheck(long groupId, long admin, long applyUserId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disband(long groupId, long userId, String reason) {

	}

	@Override
	public void setRole(long groupId, long userId, byte role) {

	}

	@Override
	public Page<Group> findGroup(long userid, byte type, long schoolId,
			int year, long page, int size) {
		return dao.findGroup(userid, type, schoolId, year, page, size);
	}

	@Override
	public int updateGroup(Group group) {
		return dao.updateGroup(group);
	}

	@Override
	public int saveGroup(Group group) {
		group.setStatus(GroupConst.GROUP_OK);
		group.setCreateTime(Q.now());
		group.setVerify(GroupConst.VERIFY_TRUE);
		return dao.save(group);
	}

	@Override
	public int deleteGroup(long id, boolean flay) {
		return dao.deleteGroup(id, flay);
	}

	@Override
	public List<Group> findGroupList(long schoolId, byte type) {
		return dao.findGroupList(schoolId, type);
	}

	@Override
	public Map<String, Object> getUser(long groupId, long userId) {
		return null;
	}

	@Override
	public boolean authenticate(long groupId, Identity user)
			throws ApplicationException {
		Boolean authz = (Boolean) user.getAttribute("group" + groupId
				+ ".authz");
		long userId = Long.parseLong(user.getId());
		/* 如果没有授权，这里授权 */
		if (authz == null) {
			// member,teacher,master,admin,squadviceSquad
			String role = dao.getRole(userId, groupId);
			if (role == null) {
				authz = false;
			} else {
				if (!Q.isEmpty(role)) {
					user.getPassport().grant(role);
				}
				authz = true;
			}
			user.setAttribute("group" + groupId + ".authz", authz);
		}
		return authz;
	}

	@Override
	public int announce(int groupId, String announce) {
		return dao.announce(groupId, announce);
	}

	@Override
	public List<Integer[]> findGroupYears(long schoolId, byte type) {
		return dao.findGroupYears(schoolId, type);
	}
}
