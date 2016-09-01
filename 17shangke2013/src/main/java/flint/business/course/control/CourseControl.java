package flint.business.course.control;

import static kiss.util.Helper.$;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kiss.security.Identity;
import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import entity.Course;
import entity.CourseComments;
import flint.business.constant.ClassroomConst;
import flint.business.constant.CourseConst;
import flint.business.constant.UserConst;
import flint.business.course.service.CourseService;
import flint.business.course.util.CourseSearcher;
import flint.business.user.service.UserService;
import flint.business.util.SchoolHelper;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.util.DateHelper;
import flint.util.NumberHelper;
import flint.util.StringHelper;
import flint.web.WebControl;
import flint.web.util.CookieHelper;

@Controller
public class CourseControl extends WebControl {

	@Autowired
	private CourseService courseService;

	@Autowired
	private UserService userService;

	/**
	 * 创建课程向导页面，这里需要检查用户创建课程的资格权限
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/course/manager/guide.html")
	public ModelAndView guide(HttpServletRequest request)
			throws ApplicationException {
		return new ModelAndView("/course/manager/guide");
	}

	/**
	 * 进入创建课程
	 * 
	 * @param HttpServletRequest
	 * @return ModelAndView
	 * @throws ApplicationException
	 */
	@RequestMapping("/course/manager/{type}/create.html")
	public ModelAndView create(@PathVariable("type") int type,
			HttpServletRequest request) throws ApplicationException {
		if (!$(1, 2, 3, 4).contains(type)) {
			return this.failure("您所创建的课程类型不存在!");
		}
		return new ModelAndView("/course/manager/create", "type", type);
	}

	/**
	 * 查看课程基本信息
	 * 
	 * @param HttpServletRequest
	 * @return ModelAndView
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/course/manager/{courseId}/edit.html", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("courseId") long courseId,
			HttpServletRequest request) throws ApplicationException {
		boolean isOwer = courseService.isOwer(courseId, this.getUser(request));
		if (!isOwer) {
			return failureView("这个课程不属于您，或者您没有访问的权限");
		}
		ModelAndView view = new ModelAndView("/course/manager/edit");
		/* 如果已有人报名，课程发生修改后需要邮件方式通知用户 */
		view.addObject("edit", true);
		view.addObject("course", courseService.findById(courseId));
		return view;
	}

	/**
	 * 保存课程信息，课程的创建和用户有关，如果用户是企业账户，创建的课程都将代表该学校
	 * 
	 * @param request
	 * @param course
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/course/manager/save.html", method = RequestMethod.POST)
	public ModelAndView save(HttpServletRequest request, Course course)
			throws ApplicationException {
		course.setUserId(getUserId(request));
		if ($(course.getTeacher()).isEmpty())
			course.setTeacher(String.valueOf(course.getUserId()));
		long courseId = courseService.save(course);
		return success("课程基本信息保存成功，请您继续填写!", "courseId",
				String.valueOf(courseId));
	}

	/**
	 * 更新课程信息
	 * 
	 * @param request
	 * @param course
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/course/manager/update.html", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request, Course course)
			throws ApplicationException {
		Identity user = this.getUser(request);
		courseService.update(user, course);
		return success("课程更新成功，已通知相关人员！");
	}

	/**
	 * 取消某个课程，只有本人能夠取消，取消课程需要录入课程原因
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/course/manager/cancle.html", method = RequestMethod.POST)
	public ModelAndView cancle(long id, HttpServletRequest request)
			throws ApplicationException {
		String reason = request.getParameter("reason");
		if (courseService.cancle(id, getUser(request), reason) > 0) {
			return success("课程取消成功，已通知报名参加的学生！");
		} else {
			return failure("课程取消失败，请稍后再试!");
		}
	}

	/**
	 * 关闭课程，本人、学校管理员，公共网站管理员可以关闭课程，关闭课程需要录入课程原因
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/course/manager/close.html", method = RequestMethod.POST)
	public ModelAndView close(long id, HttpServletRequest request)
			throws ApplicationException {
		String reason = request.getParameter("reason");
		if (courseService.close(id, getUser(request), reason) > 0) {
			return success("课程关闭成功，已通知相关人员！");
		} else {
			return failure("课程关闭失败，请稍后再试!");
		}
	}

	/**
	 * 课件浏览页面
	 * 
	 * @param HttpServletRequest
	 * @return ModelAndView
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/course/manager/{courseId}/courseware.html")
	public ModelAndView courseware(@PathVariable("courseId") long courseId,
			HttpServletRequest request) throws ApplicationException {
		boolean isOwer = courseService.isOwer(courseId, this.getUser(request));
		if (!isOwer) {
			return failureView("您访问的课程不存在，或者没有访问权限！");
		}
		ModelAndView mav = new ModelAndView("/course/manager/courseware");
		mav.addObject("course", courseService.findById(courseId));
		return mav;
	}

	/**
	 * 编写练习页面
	 * 
	 * @param HttpServletRequest
	 * @return ModelAndView
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/course/manager/{courseId}/exercise.html", method = RequestMethod.GET)
	public ModelAndView exercise(@PathVariable("courseId") long courseId,
			HttpServletRequest request) throws ApplicationException {
		boolean isOwer = courseService.isOwer(courseId, this.getUser(request));
		if (!isOwer) {
			return failureView("这个课程不属于您，或者您没有访问的权限");
		}
		ModelAndView mav = new ModelAndView("/course/manager/exercise");
		mav.addObject("course", courseService.findById(courseId));
		return mav;

	}

	/**
	 * 发布课程
	 * 
	 * @param courseId
	 *            课程id
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/course/manager/{courseId}/publish.html")
	public ModelAndView publish(@PathVariable("courseId") long courseId,
			HttpServletRequest request) throws ApplicationException {
		boolean isOwer = courseService.isOwer(courseId, this.getUser(request));
		if (!isOwer) {
			return failure("这个课程不属于您，或者您没有访问的权限");
		}
		int m = courseService.publish(courseId, true);
		if (m == 0) {
			return success("您的课程信息已提交，请等待管理员审核！");
		} else if (m == 1) {
			return success("恭喜您，课程已发布成功！");
		}
		return failure("发布失败，系统异常，请稍后再试！");
	}

	/**
	 * 查看课程详细页面，课程被关闭或删除不允许普通用户查看 这里的thread开启目的是为了系统更新后阻止系统缓存，同时可以提高SEO的效果
	 * 
	 * @param courseId
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/course/{courseId}-{thread}.html", method = RequestMethod.GET)
	public ModelAndView item(@PathVariable("courseId") long courseId,
			HttpServletRequest request) throws ApplicationException {
		long schoolId = this.getSchoolId(request);
		Identity user = this.getUser(request);
		Course course = courseService.findById(courseId);
		/* 课程不存在 */
		if (course == null) {
			return new ModelAndView("/course/item.notfound");
		}
		/*
		 * 检查访问权限： 不同学校只能访问自己的课程 1、公共学校可以访问公共的课程
		 */
		if (user.is(UserConst.ADMIN)) {
			if ((course.getStatus() == CourseConst.COURSE_CLOSE)
					|| (course.getSchoolId() != schoolId && (course
							.getSchoolId() != 0 && course.getShareModel() != 0))) {
				return new ModelAndView("/course/item.notfound");
			}
		}

		boolean check = (course != null);
		/* 允许管理员访问 */
		if (user.is(UserConst.ADMIN)) {
			check = check && CourseConst.isViewable(course.getStatus());
		}
		if (check) {
			ModelAndView mav = new ModelAndView("/course/item"
					+ course.getType());
			mav.addObject("course", course);// 课程信息
			/* 查找课程的促销策略 */
			if (course.isFavor()) {

			}
			mav.addAllObjects(userService.get(course.getUserId(), user));// 发布人信息
			/* 线下课程和在线课程显示报名人数及相关信息 */
			if (course.getType() == CourseConst.COURSE_TYPE_LOACL
					|| course.getType() == CourseConst.COURSE_TYPE_ONLINE) {
				mav.addObject("joins", courseService.getJoinMembers(courseId,
						ClassroomConst.JOIN_OK));// 报名人信息
			}
			/* 记录浏览人数 */
			courseService.addViews(course.getCourseId());
			mav.addObject(
					"allowComment",
					courseService.isAllowComment(course.getCourseId(),
							this.getUserId(request)));
			return mav;
		}
		return new ModelAndView("/course/item.notfound");
	}

	/**
	 * 课程检索
	 * 
	 * @param viewName
	 * @param request
	 * @param type
	 * @param userId
	 * @param keyword
	 * @return
	 * @throws ApplicationException
	 */
	private ModelAndView search(String viewName, HttpServletRequest request,
			int type, long userId, String keyword) throws ApplicationException {
		long size = Q.toLong(configHelper
				.getString("course", "page.size", "12"));// 由于界面列表切换，需要定义为3的倍数
		int page = $(request.getParameter("p")).toInteger(1);
		ModelAndView view = new ModelAndView(viewName);
		/* 添加对课程中分类的检索，主要分为线下，在线，和视频 */
		String tab = request.getParameter("tab");
		int courseType = 0;
		if ("online".equals(tab)) {
			courseType = CourseConst.COURSE_TYPE_ONLINE;
		} else if ("local".equals(tab)) {
			courseType = CourseConst.COURSE_TYPE_LOACL;
		} else if ("video".equals(tab)) {
			courseType = CourseConst.COURSE_TYPE_VIDEO;
		}
		CourseSearcher qs = new CourseSearcher(this.getSchoolId(request),
				request.getParameter("c"), keyword, type, userId, courseType);
		Page<Course> list = courseService.search(page, size, qs);
		list.setRequest(request);
		view.addObject(RESULT, list);
		view.addObject("TYPE", type);
		view.addObject("condition", qs.getReponseParams());
		return view;
	}

	/**
	 * 顶部课程检索提示项
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/topsearch/course.html")
	public ModelAndView topSearchCourse(HttpServletRequest request)
			throws ApplicationException {
		CourseSearcher qs = new CourseSearcher(
				SchoolHelper.getSchool(getDomain(request)),
				request.getParameter("c"), request.getParameter("q"),
				CourseSearcher.TYPE_ALL, 0, 0);
		Page<Course> list = courseService.search(1, 3, qs);
		return render(RESULT, list);
	}

	/**
	 * 课程检索
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/search/course.html")
	public ModelAndView search(HttpServletRequest request)
			throws ApplicationException {
		ModelAndView view = search("/search/course", request,
				CourseSearcher.TYPE_ALL, 0, request.getParameter("q"));
		view.addObject("searchType", "course");
		return view;
	}

	/**
	 * 学校内部课程列表页面
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/course.html")
	public ModelAndView searchSchoolCourse(HttpServletRequest request)
			throws ApplicationException {
		String is = request.getParameter("is");
		String keyword = request.getParameter("q");
		long userId = getUserId(request);
		int type = NumberHelper.toInt(is, CourseSearcher.TYPE_ALL);
		if (type > CourseSearcher.TYPE_FAV || type < CourseSearcher.TYPE_ALL) {
			type = CourseSearcher.TYPE_ALL;
		}
		return search("/course", request, type, userId, keyword);// 用户ID指定为0，不检索用户
	}

	/**
	 * 所有课程列表,允许主站外部访问
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/course-all.html")
	public ModelAndView all(HttpServletRequest request)
			throws ApplicationException {
		return search("/course/list", request, CourseSearcher.TYPE_ALL, 0, null);// 用户ID指定为0，不检索用户
	}

	/**
	 * 我报名参加的课程
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/course-mys.html")
	public ModelAndView mys(HttpServletRequest request)
			throws ApplicationException {
		return search("/course/list", request, CourseSearcher.TYPE_MY_STUDY,
				getUserId(request), null);
	}

	/**
	 * 我创建的课程,允许主站外部访问
	 * 
	 * @param page
	 *            当前页
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/course-myc.html")
	public ModelAndView myc(HttpServletRequest request)
			throws ApplicationException {
		long u = NumberHelper.toLong(request.getParameter("u"),
				getUserId(request));
		return search("/course/list", request, CourseSearcher.TYPE_MY_CREATE,
				u, null);
	}

	/**
	 * 我收藏的课程
	 * 
	 * @param page
	 *            当前页
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/course/fav.html")
	public ModelAndView fav(HttpServletRequest request)
			throws ApplicationException {
		return search("/course/list", request, CourseSearcher.TYPE_FAV,
				getUserId(request), null);
	}

	/**
	 * 相关课程页面
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/course-related.html")
	public ModelAndView related(HttpServletRequest request)
			throws ApplicationException {
		return search("/course/list", request, CourseSearcher.TYPE_RELATED,
				getUserId(request), null);
	}

	/**
	 * 课程评论列表
	 * 
	 * @param i
	 * @param request
	 * @return
	 */
	@RequestMapping("/course/comments-{courseId},{score}.html")
	public ModelAndView comment(@PathVariable("courseId") long id,
			@PathVariable("score") int score, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("global:/comment/list");
		long page = Q.toLong(request.getParameter("p"), 1);
		Page<CourseComments> list = courseService.findComments(id, page, 20,
				score);
		list.setRequest(request);
		mav.addObject(RESULT, list);
		return mav;
	}

	/**
	 * 发布课程评论，只有观看和参加过该课程的用户才能进行评价
	 * 
	 * @param i
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/course/comment.html", method = RequestMethod.POST)
	public ModelAndView comment(CourseComments commet,
			HttpServletRequest request) {
		/* 这里需要做业务校验，判断用户是否观看或参加了该课程 */
		commet.setUserId(getUserId(request));
		commet.setCome(request.getRemoteAddr());
		commet.setCreateTime(DateHelper.getNowTime());
		if (courseService.postComments(commet) > 0) {
			return success("发布评论成功！");
		} else {
			return failure("发布评论失败！");
		}
	}

	/**
	 * 显示用户即将开始的课程，主要包括两种课程： 都是指报名已结束的课程，1、用户自己创建的课程，2，用户报名参加的课程
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/coursetip.html")
	public ModelAndView course(HttpServletRequest request,
			HttpServletResponse response) {
		return render(courseService.tip(getUserId(request),
				SchoolHelper.getSchool(getDomain(request))));
	}

	/**
	 * 猜用户敢兴趣的课程，可以是未登录的用户，直接从cookie 中读取
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/course/guess.html")
	public ModelAndView guess(HttpServletRequest request)
			throws ApplicationException {
		String focus = StringHelper.defaultIfEmpty(
				CookieHelper.getCookieValue(request, "user.focus"), "");
		long page = NumberHelper.toLong(request.getParameter("p"), 1);
		long size = NumberHelper.toLong(request.getParameter("n"), 1);
		return render(RESULT, courseService.guess(focus, page, size));
	}
}
