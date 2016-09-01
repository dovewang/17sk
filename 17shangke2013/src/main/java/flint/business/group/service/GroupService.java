package flint.business.group.service;

import java.util.List;
import java.util.Map;

import kiss.security.Identity;
import entity.Group;
import flint.base.BaseService;
import flint.common.Page;
import flint.exception.ApplicationException;

public interface GroupService extends BaseService {

	/***********************************************************************
	 * 圈子操作
	 ************************************************************************/

	/**
	 * 圈子检索
	 * 
	 * @param schooId
	 * @param page
	 * @param size
	 * @return
	 */
	Page<Group> search(long schooId, long page, int size);

	/**
	 * 获取某个圈子的信息
	 * 
	 * @param groupId
	 * @return
	 */
	Map<String, Object> get(long groupId);

	int announce(int groupId, String announce);

	/**
	 * 申请一个圈子
	 * 
	 * @param group
	 * @return 圈子编号
	 */
	long apply(Group group);

	/**
	 * 检查圈子名是否存在
	 * 
	 * @param name
	 *            班级名称
	 * @param schooId
	 *            学校编号
	 * @return
	 */
	boolean checkGroupName(String name, long schooId);

	/**
	 * 更新圈子信息
	 * 
	 * @param group
	 * @return
	 */
	int update(Group group);

	/**
	 * 解散某个圈子
	 * 
	 * @param groupId
	 *            解散的圈子编号号
	 * @param userId
	 *            解散人
	 * @param reason
	 *            解散原因
	 */
	void disband(long groupId, long userId, String reason);

	/***********************************************************************
	 * 人员操作
	 ************************************************************************/

	/**
	 * 申请加入某个圈子
	 * 
	 * @param groupId
	 *            圈子编号
	 * @param userId
	 *            加入用户ID
	 */
	void joinApply(long groupId, long userId);

	/**
	 * 审核申请人
	 * 
	 * @param groupId
	 *            圈子编号
	 * @param admin
	 *            圈子管理员(可能是管理员或者圈主)
	 * @param applyUserId
	 *            申请人编号
	 */
	void joinCheck(long groupId, long admin, long applyUserId);

	/**
	 * 设置成员角色
	 * 
	 * @param groupId
	 * @param userId
	 * @param role
	 */
	void setRole(long groupId, long userId, byte role);

	/**
	 * 获取用户群名片信息
	 * 
	 * @param groupId
	 * @param userId
	 * @return
	 */
	Map<String, Object> getUser(long groupId, long userId);

	/**
	 * 获取圈子成员列表信息
	 * 
	 * @param groupId
	 * @param page
	 * @param size
	 * @return
	 */
	List<Long> findMembers(long groupId, int page, int size);

	/** ==================================================== */

	/**
	 * 获取学校或者圈子的列表
	 * 
	 * @param type
	 *            （0：班级，1：学习圈）
	 * @param schooId
	 * @param year
	 *            年份
	 * @param page
	 * @param size
	 * @return
	 */
	Page<Group> findGroup(long userid, byte type, long schooId, int year,
			long page, int size);

	List<Group> findGroupList(long schoolId, byte type);

	int deleteGroup(long id, boolean flay);

	int saveGroup(Group group);

	int updateGroup(Group group);

	/**
	 * 我的圈子信息
	 * 
	 * @param userId
	 * @param type
	 *            班级或者学习圈
	 * @return
	 */
	List<Group> my(long userId, byte type);

	List<Integer[]> findGroupYears(long schoolId, byte type);

	boolean authenticate(long groupId, Identity user)
			throws ApplicationException;

	Group getGroup(long groupId);

}
