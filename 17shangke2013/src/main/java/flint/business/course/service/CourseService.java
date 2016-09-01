package flint.business.course.service;

import java.util.List;
import java.util.Map;

import kiss.security.Identity;
import entity.Course;
import entity.CourseComments;
import entity.UserAddress;
import flint.base.BaseService;
import flint.business.course.util.CourseSearcher;
import flint.common.Page;
import flint.exception.ApplicationException;

/**
 * 
 * 功能描述： 课程模块
 * 
 * <pre>
 *     1、课程保存
 *     2、课程更新
 *     3、课程发布
 *     4、课程取消
 *     5、课程关闭
 *     6、课程检索
 *     7、课程报名
 * 
 * </pre>
 * 
 * 日 期：2011-5-18 11:12:45<br>
 * 
 * 作 者：Spirit<br>
 * 
 * 版权所有：©mosai<br>
 * 
 * 版 本 ：v0.01<br>
 * 
 * 联系方式：<a href="mailto:dream-all@163.com">dream-all@163.com</a><br>
 * 
 * 修改备注：{修改人|修改时间}<br>
 * 
 * 
 **/
public interface CourseService extends BaseService {

	/**
	 * 课程检索
	 * 
	 * @param page
	 * @param size
	 * @param qs
	 * @return
	 */
	Page<Course> search(long page, long size, CourseSearcher cs);

	/**
	 * 保存课程信息
	 * 
	 * @param course
	 * @throws ApplicationException
	 */
	long save(Course course) throws ApplicationException;

	/**
	 * 更新课程信息
	 * 
	 * @param request
	 * @param course
	 * @return
	 * @throws ApplicationException
	 */
	int update(Identity user, Course course) throws ApplicationException;

	/**
	 * 发布课程
	 * 
	 * @param courseId
	 *            课程编号
	 * 
	 * @throws ApplicationException
	 */
	int publish(long courseId, boolean checkPublish)
			throws ApplicationException;

	/**
	 * 课程取消，特指在线课程，课程未开始之前可以对课程进行取消，取消后，对报名的课程将自动退费
	 * 
	 * @param courseId
	 * @param user
	 * @param reason
	 * @return
	 * @throws ApplicationException
	 */
	int cancle(long courseId, Identity user, String reason)
			throws ApplicationException;

	/**
	 * 课程关闭,对于所有的课程都可以执行该操作，如果课程是在线课程或者线下课程则需要通知报名的用户
	 * 
	 * @param courseId
	 * @param user
	 * @param reason
	 * @return
	 * @throws ApplicationException
	 */
	int close(long courseId, Identity user, String reason)
			throws ApplicationException;

	/**
	 * 
	 * @param courseId
	 * @return
	 */
	Course findById(long courseId);

	
	long apply();
	
	/**
	 * 线下用户报名参加课程
	 * 
	 * @param courseId
	 *            课程编号
	 * @param userId
	 *            用户编号
	 * @param userAddress
	 *            报名用户地址，如果地址包含电子邮件，将向该地址发送邮件
	 * @param schoolUser
	 *            老师或者学校
	 * @return
	 * @throws ApplicationException
	 */
	long join(Course course, long userId, UserAddress userAddress,
			long schoolUser) throws ApplicationException;

	/**
	 * 课程结束
	 * 
	 * @param roomid
	 * @param dir
	 * @param courseId
	 * @param startTime
	 * @param endTime
	 */
	void end(long roomid, String dir, long courseId, int startTime, int endTime);

	/**
	 * 获得在线课程的用户
	 * 
	 * @param courseId
	 * @return
	 */
	List<Long> getJoinMembers(long courseId, byte status);

	/**
	 * 发布评论
	 * 
	 * @param commet
	 * @return
	 */
	int postComments(CourseComments commet);

	/**
	 * 评论列表
	 * 
	 * @param id
	 * @param page
	 * @param size
	 * @param score
	 * @return
	 */
	Page<CourseComments> findComments(long id, long page, long size, int score);

	/**
	 * 交易模式评论
	 * 
	 * @param productId
	 * @param seller
	 * @param score
	 * @param mark
	 * @param comment
	 * @return
	 */
	int comment(long productId, long seller, int score, int mark, String comment);

	/**
	 * 检查是否允许用户评论
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	boolean isAllowComment(long id, long userId);

	String tip(long userId, long schoolId);

	/**
	 * 猜某人感兴趣的课程
	 * 
	 * @param focus
	 *            关注的科目
	 * @param page
	 * @param size
	 * @return
	 */
	List<Course> guess(String focus, long page, long size);

	int check(Course course, long userId) throws ApplicationException;

	Page<Course> search(long page, int size, Map<String, String[]> params);

	/**
	 * 浏览课程
	 * 
	 * @param courseId
	 */
	void addViews(long courseId);

	/**
	 * 是否有权限管理该课程
	 * 
	 * @param courseId
	 * @param user
	 * @return
	 */
	boolean isOwer(long courseId, Identity user);

	/**
	 * 管理员课程审核
	 * 
	 * @param user
	 *            审核员
	 * @param agree
	 *            是否审核通过
	 * @param id
	 *            课程编号
	 * @param type
	 *            课程类型
	 * @param newprice
	 *            新价格
	 * @param reason
	 *            驳回原因
	 * @return
	 * @throws ApplicationException
	 */
	boolean checkCourse(Identity user, boolean agree, long id, byte type,
			int newprice, String reason) throws ApplicationException;

	/**
	 * 获取用户的创建的部分课程
	 * 
	 * @param userId
	 * @param size
	 * @return
	 */
	List<Course> get(long userId, int size);

	/**
	 * 查找出最新的小学、初中、高中的课程（家长首页）
	 * 
	 * @param nums
	 * @return
	 */
	Map<String, Object> hot(long schoolId, int nums);

	/**
	 * 重建索引
	 */
	void reindex();

	/**
	 * 清理没有上的课程
	 */
	void clear();

	/**
	 * 观看课程
	 * 
	 * @param user
	 * @param courseId
	 */
	void view(Identity user, long courseId);

	/**
	 * 分享课程
	 * 
	 * @param user
	 * @param courseId
	 */
	void share(Identity user, long courseId);
}
