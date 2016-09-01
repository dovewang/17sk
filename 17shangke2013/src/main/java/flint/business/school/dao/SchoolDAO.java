package flint.business.school.dao;

import java.util.List;
import java.util.Map;

import entity.School;
import entity.SchoolAddress;
import flint.base.BaseDAO;
import flint.common.Page;

public interface SchoolDAO extends BaseDAO<School, Long> {

	/**
	 * 根据网站管理员获取学校编号
	 * 
	 * @param webmaster
	 * @return
	 */
	long getSchoolIdByWebmaster(long webmaster);

	School findSchoolByDomain(String domain);

	int updateUser(long userid, String username, String tel, String id);

	int addSchoolUser(long userid, String name, String email, String tel,
			byte type, String id, long schoolId, long classid, boolean flay,
			String activeCode);

	Map<String, Object> getClassUser(long classId, long userId);

	Map<String, Object> getClassName(long classId);

	Map<String, Object> findUserByEmail(String email);

	Page<Map<String, Object>> findClass(long schooIdl, long page, int size);

	int addClassUser(long userid, long classid);

	/**
	 * 查询学校用户的基本信息
	 * 
	 * @param schoolId
	 * @param userId
	 * @return
	 */
	Map<String, Object> findSchoolMember(long schoolId, long userId);

	/**
	 * 设置用户在班里面的角色
	 * 
	 * @param classid
	 * @param userid
	 * @param role
	 * @return
	 */
	int setRole(long classid, long userid, byte role);

	int lockUser(long schoolId, long userId);

	int unlockUser(long schoolId, long userId);

	int deleteUser(long schoolId, long userId);

	Page<Map<String, Object>> search(long schoolId, long classId, byte type,
			long page, long size);

	/**
	 * 获取未分组的成员
	 * 
	 * @param schoolId
	 * @param type
	 *            0班级 1学习圈
	 * @param page
	 * @param size
	 * @return
	 */
	Page<Map<String, Object>> searchUserUnGroup(long schoolId, byte type,
			long page, long size);

	String checkEmail(String email, long schoolId);

	boolean checkClassUser(long schoolid, long classid, long userid);

	Page<School> list(long page, long size);

	List<SchoolAddress> findAddress(long schooId);

	int deleteAddress(long id);

	SchoolAddress getAddress(long id);

	/**
	 * 更新学校的状态
	 * 
	 * @param schoolId
	 * @param status
	 * @return
	 */
	int updataSchoolStatus(String domain, byte status);

	/**
	 * 审核学校
	 * 
	 * @param schoolId
	 * @param memo
	 *            备注
	 * @return
	 */
	int verifySchool(long schoolId, String memo, byte status);

	String timetable(long schoolId);

	int saveTimetable(long schoolId, boolean isNew, String timetable);

}
