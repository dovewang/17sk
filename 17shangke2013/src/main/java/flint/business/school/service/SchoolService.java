package flint.business.school.service;

import java.util.List;
import java.util.Map;

import entity.School;
import entity.SchoolAddress;
import flint.base.BaseService;
import flint.common.Page;
import flint.exception.ApplicationException;

public interface SchoolService extends BaseService {

	/**
	 * 列出所有学校的信息
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	Page<School> list(long page, long size);

	/**
	 * 获取学校信息
	 * 
	 * @param schoolId
	 * @return
	 */
	School get(long schoolId);

	/**
	 * 根据网站管理员获取学校编号
	 * 
	 * @param webmaster
	 * @return
	 */
	long getSchoolIdByWebmaster(long webmaster);

	/**
	 * 创建新的学校
	 * 
	 * @param school
	 * @return
	 */
	int create(School school, String activeCode) throws Exception;

	/**
	 * 更新学校信息
	 * 
	 * @param school
	 * @return
	 */
	int update(School school);

	/**
	 * 根据域名查找学校
	 * 
	 * @param domain
	 * @return
	 */
	School findSchoolByDomain(String domain);

	Page<Map<String, Object>> findClass(long school, long page, int size);

	/* =============学校用户管理相关================== */

	/**
	 * 
	 * @param user
	 */
	int updateUser(long userid, String name, String tel, String id);

	/**
	 * 
	 * @param user
	 */
	int addSchoolUser(long userid, String name, String email, String tel,
			byte type, String id, long schoolId, long classid, String activeCode)
			throws ApplicationException;

	/**
	 * 获取用户在班级的信息
	 * 
	 * @param groupId
	 * @param userId
	 * @return
	 */
	Map<String, Object> getClassUser(long groupId, long userId);

	Map<String, Object> getClassName(long classId);

	/**
	 * 
	 * @param schoolId
	 * @param userId
	 * @return
	 */
	int lockUser(long schoolId, long userId);

	int unlockUser(long schoolId, long userId);

	int deleteUser(long classId, long userId);

	Page<Map<String, Object>> search(long schoolId, long classId, byte type,
			long page, long size);

	/**
	 * 检查用户邮件是否存在
	 * 
	 * @param email
	 * @param schoolId
	 * @return
	 */
	String checkEmail(String email, long schoolId);

	/**
	 * 设置用户在班里的角色
	 * 
	 * @param classid
	 * @param userid
	 * @param role
	 * @return
	 */
	int setRole(long classid, long userid, byte role);

	/**
	 * 管理学校的地址
	 * 
	 * @param schoolId
	 * @return
	 */
	List<SchoolAddress> findAddress(long schoolId);

	/**
	 * 保存学校地址
	 * 
	 * @param address
	 * @return
	 */
	long saveAddress(SchoolAddress address);

	/**
	 * 删除学校地址
	 * 
	 * @param id
	 * @return
	 */
	int deleteAddress(long id);

	/**
	 * 获取学校地址信息
	 * 
	 * @param id
	 * @return
	 */
	SchoolAddress getAddress(long id);

	/**
	 * 获取学校所有完整的地址文字信息
	 * 
	 * @param id
	 * @return
	 */
	String getAddressFullText(long id);

	/**
	 * 获取学校短地址信息，只包括学校的名称及分校名
	 * 
	 * @param id
	 * @return
	 */
	String getAddressShortText(long id);

	/**
	 * 冻结学校
	 * 
	 * @param schoolId
	 * @return
	 */
	int frozen(String domain);

	/**
	 * 解冻学校
	 * 
	 * @param schoolId
	 * @return
	 */
	int unFrozen(String domain);

	/**
	 * 审核学校通过
	 * 
	 * @param schoolId
	 * @return
	 */
	int verifySchoolPass(long schoolId, String memo);

	/**
	 * 审核学校不通过
	 * 
	 * @param schoolId
	 * @return
	 */
	int verifySchoolNotPass(long schoolId, String memo);

	String timetable(long schoolId);

	int saveTimetable(long schoolId,boolean isNew, String timetable);

}
