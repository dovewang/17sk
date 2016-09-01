package flint.business.course.dao;

import java.util.List;
import java.util.Map;

import entity.Course;
import entity.CourseComments;
import flint.base.BaseDAO;
import flint.common.Page;
import flint.exception.DataAccessException;

/**
 * 
 * 功能描述：系统自动生成，请修改<br>
 * 
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
public interface CourseDAO extends BaseDAO<Course, Long> {

	/**
	 * 更新课程信息
	 * 
	 * @param course
	 * @return
	 */
	int update(Course course);

	/**
	 * 查询课程信息
	 * 
	 * @param where
	 * @param orderString
	 * @param page
	 * @param size
	 * @param parameters
	 * @return
	 * @throws DataAccessException
	 */
	Page<Course> query(String where, String orderString, long page, long size,
			Object... parameters) throws DataAccessException;

	/**
	 * 根据全文检索查询出的ID从数据库读取数据
	 * 
	 * @param ids
	 *            全文检索的id值
	 * @return
	 */
	List<Course> findByIds(List<String> ids) throws DataAccessException;

	/**
	 * 发布课程
	 * 
	 * @param courseId
	 * @return
	 */
	int publish(long courseId, byte status) throws DataAccessException;

	int postComments(CourseComments commet);

	Page<CourseComments> findComments(long id, long page, long size, int score);

	String tip(long userId, long schoolId);

	int cancle(long courseId, long userId, String reason);

	int close(long courseId, long userId, String reason);

	List<Course> guess(String focus, long page, long size);

	int comment(long courseId, long seller, int score, int mark, String comment);

	Page<Course> search(long page, int size, Map<String, String[]> params);

	void saveCourseScope(long courseId, String scope);

	void add(long courseId, String field);

	int end(long roomid, String dir, long courseId, int startTime, int endTime);

	boolean isOwer(long courseId, long userId);

	boolean checkCourse(boolean agree, long id, byte type, int newprice,
			String reason);

	boolean isAllowComment(long id, long userId);

	/**
	 * 获取该用户发布的相关课程，只获取正常的课程
	 * 
	 * @param userId
	 *            用户编号
	 * @param size
	 *            课程数
	 * @return
	 */
	List<Course> get(long userId, int size);

	/**
	 * 查找出最新的小学、初中、高中的课程、线下课程（家长首页）
	 * 
	 * @param nums
	 * @return
	 */
	List<Course> hot(long schoolId, int nums);

	void clear();
}
