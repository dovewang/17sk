package flint.business.course.service.impl;

import static kiss.util.Helper.$;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kiss.search.SearchEngine;
import kiss.security.Identity;
import kiss.util.Q;
import kiss.util.wrapper.DateWrapper;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entity.Course;
import entity.CourseComments;
import entity.Mblog;
import entity.Message;
import entity.UserAddress;
import flint.base.BaseServiceImpl;
import flint.business.constant.ClassroomConst;
import flint.business.constant.CourseConst;
import flint.business.constant.MblogConst;
import flint.business.constant.MessageConst;
import flint.business.constant.TradeConst;
import flint.business.constant.UserConst;
import flint.business.course.dao.CourseDAO;
import flint.business.course.service.CourseService;
import flint.business.course.util.CourseSearcher;
import flint.business.mail.MailManager;
import flint.business.message.CometdService;
import flint.business.school.service.SchoolService;
import flint.business.search.CourseSearch;
import flint.business.social.service.SocialService;
import flint.business.trade.service.TradeService;
import flint.business.trade.service.TradeStatus;
import flint.business.user.service.MessageService;
import flint.business.user.service.UserService;
import flint.business.util.ImageHelper;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.util.NumberHelper;

@Service
@Transactional
public class CourseServiceImpl extends BaseServiceImpl implements CourseService {
	@Autowired
	private MailManager mailManager;

	@Autowired
	private CourseSearch courseSearch;

	@Autowired
	private TradeService tradeService;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private SocialService socialService;

	@Autowired
	private CometdService cometdService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService userService;

	@Autowired
	private CourseDAO dao;
	@Autowired
	private ImageHelper imageHelper;

	//@Autowired
	//private SearchEngine searchEngine;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Page<Course> search(long page, long size, CourseSearcher cs) {
		if (cs.isFullText()) {
			/* 从全文检索查询后再查询数据库 */
			Page pageSearch = courseSearch.queryPage(cs.getQueryBuilder(),
					(int) page, (int) size);
			List searchResult = pageSearch.getResult();
			if (searchResult.size() > 0) {
				pageSearch.setResult(dao.findByIds(searchResult));
			} else {
				pageSearch.setResult(new ArrayList());
			}
			return pageSearch;
		} else {
			/* 不在使用数据库查询 */
			return dao.query(cs.getWhere(), cs.getOrder(), page, size,
					cs.getParamters());
		}
	}

	@Override
	public boolean isOwer(long courseId, Identity user) {
		Object v = user.getAttribute("course.v" + courseId);
		boolean result = false;
		if (v == null) {
			if (user.is(UserConst.ADMIN)
					|| dao.isOwer(courseId, Long.parseLong(user.getId()))) {
				result = true;
			}
			user.setAttribute("course.v" + courseId, result);// 设定课程操作权限
		} else {
			result = (Boolean) v;
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws ApplicationException
	 */
	@Override
	@Transactional
	public int publish(long courseId, boolean checkPublish)
			throws ApplicationException {
		Course course = dao.findById(courseId);
		course.setStatus(CourseConst.COURSE_SIGN);
		/* 是否验证课程发布的状态 */
		if (checkPublish) {
			boolean onlineCheck = "true".equals(configHelper.getString(
					"course", "online.check", "false"));// 在线课程发布审核
			boolean localCheck = "true".equals(configHelper.getString("course",
					"local.check", "true"));
			switch (course.getType()) {
			/* 转发和上传的视频默认状态设定为课程结束 */
			case CourseConst.COURSE_TYPE_VIDEO:
			case CourseConst.COURSE_TYPE_FORWARD:
				course.setStatus(CourseConst.COURSE_SUCCESS);
				break;
			case CourseConst.COURSE_TYPE_LOACL:
				if (localCheck) {
					course.setStatus(CourseConst.COURSE_CHECK);
				}
				break;
			case CourseConst.COURSE_TYPE_ONLINE:
				if (onlineCheck) {
					course.setStatus(CourseConst.COURSE_CHECK);
				}
				break;
			}
		}
		/* 发布课程 */
		dao.publish(courseId, course.getStatus());

		/* 通过审核的才发布，发送通知 */
		if (course.getStatus() > CourseConst.COURSE_CHECK) {
			publishNotity(course);
		}
		return 1;
	}

	private void publishNotity(Course course) {
		/* 新增课程 */
		Mblog mblog = new Mblog();
		mblog.setDateline(Q.now());
		mblog.setType(MblogConst.TYPE_COURSE);
		mblog.setSchoolId(course.getSchoolId());
		mblog.setUserId(course.getUserId());

		String cover = imageHelper.resolve(course.getCover(), "s120,90");
		mblog.setMedia(String.valueOf(course.getCourseId()));
		mblog.setContent("<a>#" + course.getName() + "#</a><br/><a><img src=\""
				+ cover + "\" width=\"120\" height=\"90\"></a>");
		if (course.getUpdateTime() == 0) {
			userService.addCourses(course.getUserId(), 1);
			mblog.setContent("发布了新课程" + mblog.getContent());
		} else {
			mblog.setContent("更新了课程" + mblog.getContent());
		}
		socialService.sync(mblog);
		/* 是否是完全公开,如果是公开，信息将也被发布到公开平台 */
		if (course.getShareModel() == 0 && course.getSchoolId() != 0) {
			mblog.setSchoolId(0);
			socialService.sync(mblog);
		}
		/* 创建索引 */
		courseSearch.createIndex(course.getCourseId());
	}

	@Transactional
	@Override
	public long save(Course course) throws ApplicationException {
		course.setOldPrice(course.getPrice());
		course.setStatus(CourseConst.COURSE_NOPUBLISH);
		long courseId = dao.insert(course);
		/* 保存适用范围 */
		dao.saveCourseScope(courseId, course.getScope());
		return courseId;
	}

	@Override
	public int update(Identity user, Course course) throws ApplicationException {
		/* 更新课程，将课程状态设置到未发布状态 */
		course.setStatus(CourseConst.COURSE_NOPUBLISH);
		if (dao.update(course) > 0
				&& (course.getType() == CourseConst.COURSE_TYPE_ONLINE)) {
			/* 目前先在线通知 */
			List<Long> list = this.getJoinMembers(course.getCourseId(),
					ClassroomConst.JOIN_OK);
			/* 发送系统消息给用户 */
			Message message = new Message();
			message.setMessage(Q
					.variable(
							"您参加的课程{}，已于{}更新，<a href='/course/{}-{}.html' target='_blank'>请点这里查看</a>",
							course.getName(),
							Q.now(DateWrapper.YYYYMMDDHH24MMSS),
							course.getCourseId(), Q.now()));
			message.setSubject("课程更新通知");
			message.setSchoolId(user.getDomainId());
			message.setSender(user.getDomainId());
			message.setType(MessageConst.SYSTEM);
			for (long u : list) {
				message.setReceiver(u);
				messageService.send(message, user);
			}

			// String[] to = new String[1];
			// Map<String, Object> value = new HashMap<String, Object>();
			/* 发送邮件给已报名的用户，通知课程发生了修改 */
			// mailManager.modifyCourse(user.getSchoolDomain(), value, to);
		}
		return 1;
	}

	@Override
	public Course findById(long id) {
		Course course = dao.findById(id);
		/* bug fix:空值检查 */
		if (course != null && course.getAddressId() != 0) {
			course.setMemo(schoolService.getAddressShortText(course
					.getAddressId()));
		}
		return course;
	}

	@Override
	public int check(Course course, long userId) throws ApplicationException {
		/* 检查课程状态 是否在报名阶段 */
		if (course.getStatus() != CourseConst.COURSE_SIGN) {
			throw new ApplicationException("COURSE_NOT_IN_SIGN");
		}
		/* 不允许报名参加自己创建的课程 */
		if (course.getUserId() == userId) {
			throw new ApplicationException("COURSE_SIGN_BY_PUBLISHER_ERROR");
		}

		/* 检查报名人数是否已满 */
		if (course.getMaxCapacity() != 0
				&& course.getJoins() == course.getMaxCapacity()) {
			throw new ApplicationException("COURSE_SIGN_FULL");
		}

		/* 在线课程，只允许本账户报名，现在课程则可以替其他人员报名 */
		if (course.getType() == CourseConst.COURSE_TYPE_ONLINE) {
			/* 检查用户是否已报名该课程 */
			TradeStatus status = tradeService
					.getTradeStatus(course.getCourseId(),
							TradeConst.TYPE_COURSE_ONLINE, userId);
			if (status.getOrderStatus() != -1) {
				throw new ApplicationException("COURSE_SIGN_AGAIN");
			}

			/* 检查课程冲突 */
		}

		return 1;
	}

	@Transactional
	@Override
	public long join(Course course, long userId, UserAddress userAddress,
			long schoolUser) throws ApplicationException {
		/* 发送通知给报名人 */
		/* 发送通知上课的人 */
		/* 发送通知到管理员邮箱，同时在网页提醒 */
		/* 发送通知到学校邮箱，同时在网页提醒 */
		Map<String, Object> value = new HashMap<String, Object>();
		value.put("studentId", userId);
		String email = null;
		String master = null;
		if (userAddress != null) {
			email = userAddress.getEmail();
			course.setMemo(schoolService.getAddressFullText(course
					.getAddressId()));// 上课报道地址，线下课程邮件使用
			value.put("address", userAddress);
			master = "webmaster@17shangke.com";// 仅线下课程给管理员发送邮件
		}
		value.put("course", course);
		if (email == null)
			mailManager.joinCourse("www", value,
					userService.getEmail(schoolUser), master,
					userService.getEmail(userId));
		else
			mailManager.joinCourse("www", value,
					userService.getEmail(schoolUser), master,
					userService.getEmail(userId), email);

		/* 报名课程人数增加 */
		this.addJoins(course.getCourseId());
		return 1;
	}

	@Override
	public void end(long roomid, String dir, long courseId, int startTime,
			int endTime) {
		dao.end(roomid, dir, courseId, startTime, endTime);
		/* 重建索引 */
		courseSearch.createIndex(courseId);
	}

	@Override
	public List<Long> getJoinMembers(long courseId, byte status) {
		return tradeService.getCourseMembers(courseId, status);
	}

	@Override
	public int postComments(CourseComments commet) {
		return dao.postComments(commet);
	}

	@Override
	public Page<CourseComments> findComments(long id, long page, long size,
			int score) {
		return dao.findComments(id, page, size, score);
	}

	@Override
	public String tip(long userId, long schoolId) {
		return dao.tip(userId, schoolId);
	}

	@Override
	@Transactional
	public int cancle(long courseId, Identity user, String reason)
			throws ApplicationException {
		long userId = Long.parseLong(user.getId());
		Course course = findById(courseId);
		/* 课程在报名阶段允许取消 */
		if (course.getStatus() == CourseConst.COURSE_SIGN) {
			courseSearch.delete(courseId);
			userService.addCourses(course.getUserId(), -1);
			/* 课程取消自动退费，目前一个订单只有1个课程，课程取消直接获取订单信息进行取消即可 */
			List<Long> list = this.getJoinMembers(courseId,
					ClassroomConst.JOIN_OK);
			// tradeService.getOrders(courseId,TradeConst.TYPE_COURSE_ONLINE);
			// tradeService.cancle(orderIds, "课程取消，自动退费");
			// String[] to = null;
			// Map<String, Object> value = new HashMap<String, Object>();
			// value.put("course", course);
			// value.put("reason", reason);
			// mailManager.cancleCourse(user.getSchoolDomain(), value, to);

			/* 发送系统消息给用户 */
			Message message = new Message();
			message.setMessage("您参加的课程<a href='/course/" + course.getCourseId()
					+ "-" + Q.now() + ".html' target='_blank'>"
					+ course.getName() + "</a>由于<span style='color:red'>"
					+ Q.isEmpty(reason, "") + "</span>已于"
					+ Q.now(DateWrapper.YYYYMMDDHH24MMSS) + "取消，系统将自动退还您所有的费用！");
			message.setSubject("课程取消通知");
			message.setSchoolId(user.getDomainId());
			message.setSender(user.getDomainId());
			message.setType(MessageConst.SYSTEM);
			for (long u : list) {
				message.setReceiver(u);
				messageService.send(message, user);
			}
			return dao.cancle(courseId, userId, reason);
		}
		return -1;
	}

	@Override
	@Transactional
	public int close(long courseId, Identity user, String reason)
			throws ApplicationException {
		long userId = Long.parseLong(user.getId());
		/* 如果课程已经被取消，这里再关闭时不再做处理 */
		Course course = findById(courseId);
		if (course.getStatus() != CourseConst.COURSE_CANCLE) {
			courseSearch.delete(courseId);
			/* 课程关闭自动退费 */
			userService.addCourses(userId, -1);
			List<Long> list = getJoinMembers(courseId, ClassroomConst.JOIN_OK);

			Message message = new Message();
			message.setMessage("您参加的课程<a href='/course/" + course.getCourseId()
					+ "-" + Q.now() + ".html' target='_blank'>"
					+ course.getName() + "</a>由于<span style='color:red'>"
					+ Q.isEmpty(reason, "") + "</span>已于"
					+ Q.now(DateWrapper.YYYYMMDDHH24MMSS)
					+ "被关闭，已报名的用户将会自动退还相关费用。");
			message.setSubject("课程关闭通知");
			message.setSchoolId(user.getDomainId());
			message.setSender(user.getDomainId());
			message.setType(MessageConst.SYSTEM);
			for (long u : list) {
				message.setReceiver(u);
				messageService.send(message, user);
			}

			/* 获取所哟 */
			String[] to = userService.getEmails($(list).join());
			Map<String, Object> value = new HashMap<String, Object>();
			value.put("course", course);
			value.put("reason", reason);
			mailManager.closeCourse(user.getDomain(), value, to);
		}
		return dao.close(courseId, userId, reason);
	}

	@Override
	public List<Course> guess(String focus, long page, long size) {
		return dao.guess(focus, page, size);
	}

	@Override
	public int comment(long courseId, long seller, int score, int mark,
			String comment) {
		return dao.comment(courseId, seller, score, mark, comment);
	}

	@Override
	public Page<Course> search(long page, int size, Map<String, String[]> params) {
		return dao.search(page, size, params);
	}

	@Override
	public void addViews(long courseId) {
		dao.add(courseId, "views");
	}

	public void addJoins(long courseId) {
		dao.add(courseId, "joins");
	}

	@Override
	public boolean checkCourse(Identity user, boolean agree, long id,
			byte type, int newprice, String reason) throws ApplicationException {
		boolean result = dao.checkCourse(agree, id, type, newprice, reason);
		Course course = dao.findById(id);
		Message message = new Message();
		message.setSubject("课程审核通知");
		message.setSchoolId(course.getSchoolId());
		message.setSender(course.getSchoolId());
		message.setReceiver(course.getUserId());
		message.setType(MessageConst.SYSTEM);
		if (agree && result) {
			message.setMessage("您的课程<a href='/course/" + id + "-" + Q.now()
					+ ".html' target='_blank'>" + course.getName()
					+ "</a>审核已通过，系统已自动发布！");
			publishNotity(dao.findById(id));
		} else {
			message.setMessage("您的课程<a href='/course/" + id + "-" + Q.now()
					+ ".html' target='_blank'>" + course.getName()
					+ "</a>审核未通过，原因：" + reason);
		}
		messageService.send(message, user);
		return result;
	}

	@Override
	public boolean isAllowComment(long id, long userId) {
		return dao.isAllowComment(id, userId);
	}

	@Override
	public List<Course> get(long userId, int size) {
		return dao.get(userId, size);
	}

	@Override
	public Map<String, Object> hot(long schoolId, int nums) {

		Map<String, Object> map = new HashMap<String, Object>();

		List<Course> course1 = new ArrayList<Course>();
		List<Course> course2 = new ArrayList<Course>();
		List<Course> course3 = new ArrayList<Course>();
		List<Course> course4 = new ArrayList<Course>();

		List<Course> courseList = dao.hot(schoolId, nums);
		for (Course c : courseList) {
			byte type = NumberHelper.toByte(c.getMemo());
			switch (type) {
			case 1:
				course1.add(c);
				break;
			case 2:
				course2.add(c);
				break;
			case 3:
				course3.add(c);
				break;
			case 4:
				course4.add(c);
				break;
			default:
				break;
			}
		}

		map.put("course1", course1);
		map.put("course2", course2);
		map.put("course3", course3);
		map.put("course4", course4);

		return map;
	}

	@Override
	public void reindex() {
		courseSearch.rebulidIndex();
	}

	@Override
	public void clear() {
		dao.clear();
	}

	@Override
	public long apply() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void view(Identity user, long courseId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void share(Identity user, long courseId) {
		// TODO Auto-generated method stub

	}

	public SearchRequestBuilder getSearchRequestBuilder() {
		return null;
		// return searchEngine.client().prepareSearch("").setFrom(from)
	}

}