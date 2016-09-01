package flint.business.user.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import entity.Collect;
import entity.User;
import entity.UserAddress;
import entity.UserExtends;
import flint.base.BaseDAO;
import flint.common.Page;
import flint.exception.DataAccessException;

public interface UserDAO extends BaseDAO<User, Long> {

	/* =================验证激活模块===================== */
	void email(long userId, String activeCode);

	/**
	 * 验证用户邮箱激活链接
	 * 
	 * @param userId
	 *            用户ID
	 * @param activeCode
	 *            系统生成的激活码
	 * @return true=验证成功 false=验证失败
	 * @throws DataAccessException
	 */
	boolean validateEmail(long userId, String activeCode)
			throws DataAccessException;

	/**
	 * 学位认证
	 * 
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	boolean validateDegree(long userId) throws DataAccessException;

	/**
	 * 实名认证
	 * 
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	boolean validateRealName(long userId) throws DataAccessException;

	/**
	 * 验证用户密码是否正确
	 * 
	 * @param userId
	 * @param password
	 * @return
	 * @throws DataAccessException
	 */
	boolean validatePassword(long userId, String password);

	/**
	 * 更新用户状态
	 * 
	 * @param userid
	 *            用户Id
	 * @param status
	 *            用户状态
	 * @see #UserConst
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	int updateStatus(long userid, byte status) throws DataAccessException;

	/**
	 * 检查注册用户名是否存在,主要是在注册时使用,支持多个用户名一起检查
	 * 
	 * @param names
	 *            用户名
	 * @return 返回系统中存在的用户名
	 * @throws DataAccessException
	 */
	List<Object> existsUserName(String... names) throws DataAccessException;

	/**
	 * 检查邮箱是否存在，当name==null时在所有的用户中查找，否则只查询用户名对应的邮箱
	 * 
	 * @param email
	 *            邮箱地址
	 * @param name
	 *            用户名
	 * @return
	 * @throws DataAccessException
	 */
	boolean existsEmail(String email, String name) throws DataAccessException;

	/**
	 * 用户更新密码
	 * 
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 * @throws DataAccessException
	 */
	int changePassword(long userId, String oldPassword, String newPassword)
			throws DataAccessException;

	/**
	 * 生成找回密码验证码
	 * 
	 * @param userName
	 * @param email
	 * @param activeCode
	 * @return
	 * @throws DataAccessException
	 */
	int recovery(String userName, String email, String activeCode);

	/**
	 * 验证找回密码链接
	 * 
	 * @param name
	 * @param activeCode
	 * @return
	 */
	boolean validateRecovery(String email, String activeCode);

	int saveAvatar(String faceUrl, long userId) throws DataAccessException;

	/**
	 * 更新用户类型
	 * 
	 * @param type
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	int updateUserType(byte type, long userId) throws DataAccessException;

	/**
	 * 更新用户基本信息
	 * 
	 * @param user
	 * @return
	 * @throws DataAccessException
	 */
	int updateUser(long schoolId, User user) throws DataAccessException;

	/**
	 * 更新用户扩展信息
	 * 
	 * @param userExtends
	 * @return
	 * @throws DataAccessException
	 */
	int updateUserExtends(UserExtends userExtends) throws DataAccessException;

	/**
	 * 根据ID获取用户列表
	 * 
	 * @param result
	 * @return
	 * @throws DataAccessException
	 */
	List<User> findUserByIds(long schoolId, long nowUser, Collection<String> ids)
			throws DataAccessException;

	/**
	 * 检索教师的信息
	 * 
	 * @param where
	 * @param orderBy
	 * @param page
	 * @param size
	 * @return
	 * @throws DataAccessException
	 */
	Page<User> search(long schoolId, long nowUser, String where,
			String orderBy, long page, long size) throws DataAccessException;

	/**
	 * 查找出擅长该知识点的用户
	 * 
	 * @param expert
	 *            知识点 （用逗号分隔）
	 * @return
	 * @throws DataAccessException
	 */
	List<User> getExpertUser(long grade, long kind, long schoolId, long userId);

	/**
	 * 获取用户状态
	 * 
	 * @param userIds
	 * @param schoolId
	 * @param nowuser
	 * @return
	 * @throws DataAccessException
	 */
	String ping(String[] userIds, long schoolId, long groupId, long nowuser)
			throws DataAccessException;

	/**
	 * 获取用户名片信息
	 * 
	 * @param userId
	 * @param schoolId
	 * @param groupId
	 * @param nowuser
	 * @return
	 * @throws DataAccessException
	 */
	User getUsercard(long userId, long schoolId, long groupId, long nowuser)
			throws DataAccessException;

	/**
	 * 获取用户扩展信息
	 * 
	 * @param userId
	 * @return
	 */
	UserExtends findUserExtends(long userId) throws DataAccessException;

	/**
	 * 获取用户信息
	 * 
	 * @param id
	 * @return
	 */
	User getUser(long userId);

	String getSomeUsers(long school, long nowuser, int nums, int type);

	/* ===============用户收藏================== */

	/**
	 * 收藏
	 * 
	 * @param uid
	 * @param objectId
	 * @param objectType
	 * @return
	 */
	int collect(long uid, String objectId, byte objectType);

	/**
	 * 删除收藏
	 * 
	 * @param uid
	 * @param objectId
	 * @param objectType
	 * @return
	 */
	int deleteCollect(long uid, String objectId, byte objectType);

	/**
	 * 返回的ID代表已收藏的
	 * 
	 * @param uid
	 * @param objectIds
	 *            "," 分开
	 * @param objectType
	 * @return
	 */
	List<Collect> getcollect(long uid, String objectIds, byte objectType);

	void expert(long userId, String expert);

	String getEmail(long userId);

	int resetPassword(String email, String activeCode, String newPassword);

	void addScore(long userId, int score);

	void addCredit(long userId, int credit);

	int addStudent(long teacherId, long studentId);

	void addAnswers(long userId, int answers);

	void addAdoptAnswers(long userId, int adopt);

	void addCourses(long userId, int courses);

	void addGoodCourses(long userId, int courses);

	int deleteAddress(long id);

	List<UserAddress> findAddress(long userId);

	UserAddress getAddress(long addressId);

	Page<User> query(int page, int size, Map<String, String[]> params);

	/**
	 * 获取其他的用户角色身份
	 * 
	 * @param userId
	 * @return
	 */
	List<User> searchOherUser(long userId);

	/**
	 * 获取人气教师
	 * 
	 * @param nums
	 * @return
	 */
	Page<User> searchHotTeacher(int nums, long userid);

	/**
	 * At用户，用户相关提示信息
	 * 
	 * @param objectId
	 * @param objectType
	 * @param users
	 */
	void at(long objectId, byte objectType, long... users);

	int readAtStatus(List<String> objectIds, long uid, byte objectType);

}
