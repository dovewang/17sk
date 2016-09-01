package flint.business.group.dao;

import java.util.List;
import java.util.Map;

import entity.Group;
import flint.base.BaseDAO;
import flint.common.Page;

public interface GroupDAO extends BaseDAO<Group, Long> {

	boolean checkGroupName(String name, long schoolId);

	/**
	 * 获取班级或者圈子的列表
	 * 
	 * @param type
	 *            （0：班级，1：学习圈）
	 * @param schooIdl
	 * @param page
	 * @param size
	 * @return
	 */
	Page<Group> findGroup(long userid, byte type, long schoolId,int year, long page,
			int size);

	List<Group> findGroupList(long schoolId, byte type);

	int deleteGroup(long id, boolean flay);

	int updateGroup(Group group);

	/**
	 * 获取圈子成员列表信息
	 * 
	 * @param groupId
	 * @param page
	 * @param size
	 * @return
	 */
	List<Long> findMembers(long groupId, int page, int size);

	List<Group> my(long userId, byte type);

	String getRole(long userId, long groupId);

	int announce(int groupId, String announce);

	Map<String, String> getPosition(long groupId);

	 List<Integer[]>findGroupYears(long schoolId, byte type);

}
