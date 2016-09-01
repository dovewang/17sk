package flint.business.main.control;

import static kiss.util.Helper.$;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kiss.security.Identity;
import kiss.security.Passport;
import kiss.storage.client.SpringSmarty;
import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import entity.Mblog;
import entity.Message;
import flint.business.constant.UserConst;
import flint.business.course.service.CourseService;
import flint.business.main.service.MainService;
import flint.business.mblog.service.MblogService;
import flint.business.question.control.QuestionControl;
import flint.business.question.util.QuestionSearcher;
import flint.business.school.service.SchoolService;
import flint.business.user.service.MessageService;
import flint.business.user.service.UserService;
import flint.business.util.KindHelper;
import flint.business.util.KnowledgeHelper;
import flint.business.util.SchoolHelper;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.io.image.Clipper;
import flint.io.image.ImageHelper;
import flint.io.image.Scaler;
import flint.security.word.BBCodeWordSource;
import flint.web.WebControl;
import flint.web.util.CookieHelper;

/***
 * 主页信息
 * 
 * @author Dove Wang
 * 
 */
@Controller
public class MainControl extends WebControl {

	@Autowired
	private SpringSmarty springSmarty;

	@Autowired
	private MainService mainService;

	/* 微博 */
	@Autowired
	private MblogService mblogService;

	/* 消息及公告信息 */
	@Autowired
	private MessageService messageService;

	@Autowired
	private KnowledgeHelper knowledgeHelper;

	@Autowired
	private BBCodeWordSource bbcodeSource;

	@Autowired
	private QuestionControl questionControl;

	@Autowired
	private KindHelper kindHelper;

	@Autowired
	private UserService userService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private SchoolService schoolService;

	@RequestMapping("/timesynch.html")
	@ResponseBody
	public String timesynch(HttpServletRequest request)
			throws ApplicationException {
		return String.valueOf(Long.parseLong(request.getParameter("time"))
				- new Date().getTime());
	}

	@RequestMapping("/error/400.html")
	public void HTTP400() {
	}

	@RequestMapping("/error/403.html")
	public void HTTP403() {
	}

	@RequestMapping("/error/404.html")
	public void HTTP404() {
	}

	@RequestMapping("/error/500.html")
	public void HTTP500() {

	}

	@RequestMapping("/suggest.html")
	public ModelAndView suggest(String content, HttpServletRequest request) {
		mainService.suggest(content, getUserId(request));
		return success("谢谢您的支持，我们会尽快处理您的建议！");

	}

	/**
	 * 课程分类
	 * 
	 * @param grade
	 * @return
	 */
	@RequestMapping("/kind.html")
	public ModelAndView getkinds(String grade) {
		return render(RESULT, kindHelper.getKind(grade));
	}

	@RequestMapping("/kindView.html")
	public void kindView() {

	}

	/**
	 * 知识点
	 * 
	 * @param grade
	 * @param kind
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/knowledge.html")
	public ModelAndView getKnowledges(int grade, long kind, long parentId) {
		return render("knowledge",
				knowledgeHelper.getKnowledge(grade, kind, parentId));
	}

	@RequestMapping("/home.html")
	public void home() {

	}

	/**
	 * 学校关于信息
	 */
	@RequestMapping("/about.html")
	public ModelAndView about(HttpServletRequest request) {
		return new ModelAndView("/about", "school", schoolService.get(this
				.getSchoolId(request)));
	}

	/**
	 * 招聘
	 */
	@RequestMapping("/hr.html")
	public void hr() {

	}

	/**
	 * 服务信息
	 */
	@RequestMapping("/service.html")
	public void service() {

	}

	/**
	 * 用户协议
	 */
	@RequestMapping("/agreement.html")
	public void agreement() {

	}

	/**
	 * 隐私条款
	 */
	@RequestMapping("/privacy.html")
	public void privacy() {

	}

	/**
	 * 首页，由于本站的首页和学校的首页访问地址相同，但是URL地址不同
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/index{t}.html")
	public ModelAndView index(@PathVariable("t") String t,
			HttpServletRequest request, HttpServletResponse response)
			throws ApplicationException {
		ModelAndView index = new ModelAndView();
		String view = "index1";
		Identity user = this.getUser(request);
		/* 2013-01-10 bug fix:用户第一次登陆身份切换一次 */
		if (!Q.isNull(user.getAttribute("__first"))) {
			t = "0";// 清除用户身份
		}
		user.setAttribute("__first", "true");// 设定为第一次访问
		/* 对非登录用户进行判断，允许其进行角色切换,未登录的用户这里类型为0 */
		byte USER_TYPE = $(t).toByte(user.getType());
		if (USER_TYPE == 0) {
			String ut = CookieHelper.getCookieValue(request,
					Passport.COOKIE_USER_TYPE);
			USER_TYPE = $(ut).toByte(UserConst.STUDENT);
		}
		/* 页面跳转 */
		if (USER_TYPE == 0 || USER_TYPE == 1 || USER_TYPE == 127) {// 学生首页
			index = index1(view, request);
		} else if (USER_TYPE == 2) {// 老师首页
			view = "index2";
			index = index2(view, request);
		} else if (USER_TYPE == 3) {// 家长首页
			view = "index3";
			/* 获取人气教师 */
			index.addObject("hotteacher",
					userService.searchHotTeacher(24, getUserId(request)));
			index.addObject("coursemap",
					courseService.hot(getSchoolId(request), 4));
			index.setViewName(view);
		} else if (USER_TYPE == 4) {// 企业用户
			view = "index2";
			index = index2(view, request);
		}
		CookieHelper.addCookie(response, Passport.COOKIE_USER_TYPE,
				String.valueOf(USER_TYPE), 365 * 24 * 3600, "/", false);
		/* 随便说说数据 */
		index.addObject("mbloglist", mblogService.getTop(user.getDomainId(), 12));
		return index;
	}

	@RequestMapping("/announce.html")
	public ModelAndView announce(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		long schoolId = SchoolHelper.getSchool(getDomain(request));
		int max = Q.String(configHelper.getString("anounce", "max")).toInteger(
				3);
		int nums = Q.String(configHelper.getString("anounce", "school.max"))
				.toInteger(1);
		List<Message> announce = messageService.announce(schoolId, nums);
		List<Mblog> mblogs = mblogService.getTop(schoolId,
				max - announce.size());
		mav.addObject("mblogs", mblogs);
		mav.addObject("announce", announce);
		return mav;
	}

	/**
	 * 课程封面选择，使用于爱讲台和课程发布
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/cover/select.html")
	public void coverSelect(HttpServletRequest request)
			throws ApplicationException {
	}

	/**
	 * 封面列表
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/cover/list.html")
	public ModelAndView coverList(HttpServletRequest request)
			throws ApplicationException {
		Page<Map<String, Object>> list = mainService.findCover(
				$(request.getParameter("c")).toLong(-1),
				$(request.getParameter("p")).toLong(1), 6);
		ModelAndView mav = new ModelAndView();
		list.setRequest(request);
		mav.addObject(RESULT, list);
		return mav;
	}

	/**
	 * 生成300*225课程封面
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/cover/save.html", method = RequestMethod.POST)
	public ModelAndView saveCover(HttpServletRequest request)
			throws ApplicationException {
		int x = Q.String(request.getParameter("x")).toInteger();
		int y = Q.String(request.getParameter("y")).toInteger();
		int h = Q.String(request.getParameter("h")).toInteger();
		int w = Q.String(request.getParameter("w")).toInteger();
		String path = request.getParameter("path");
		Properties config = springSmarty.getConfig();
		String root = config.getProperty("storage.home", "");
		String realPath = root + request.getParameter("path");
		InputStream in = null;
		try {
			in = new FileInputStream(realPath);
			BufferedImage source = Clipper.clip(in, x, y, w, h);
			BufferedImage large = Scaler.resize(source, 225, 300);
			ImageHelper.write(large, new FileOutputStream(realPath));
		} catch (IOException e) {
			throw new ApplicationException(e);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				throw new ApplicationException(e);
			}
		}
		path = config.getProperty("storage.http.image", "/image.server/")
				+ path;
		return success("保存成功!", "path", path);
	}

	/**
	 * 发送微博的表情
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/tools.face.html")
	public ModelAndView face(HttpServletRequest request) {
		return this.render("faces", bbcodeSource.getWords());
	}

	/**
	 * index1
	 * 
	 * @throws ApplicationException
	 * 
	 */
	private ModelAndView index1(String view, HttpServletRequest request)
			throws ApplicationException {
		String tabStr = request.getParameter("tab");// 问题选项卡（全部、擅长）
		if (Q.isBlank(tabStr))
			tabStr = "1,1";
		int type = Q.String(tabStr.split(",")[1]).toInteger();
		/* 问题检索 */
		if (type == 1) {// 全部
			return questionControl.search(view, request,
					QuestionSearcher.TYPE_ALL, 0, request.getParameter("q"));
		} else {// 擅长
			return searchGoodat(view, request);
		}
	}

	/**
	 * index2
	 * 
	 * @throws ApplicationException
	 * 
	 */
	private ModelAndView index2(String view, HttpServletRequest request)
			throws ApplicationException {
		ModelAndView model = new ModelAndView();
		String tabStr = request.getParameter("tab");// 选项卡
		if (Q.isBlank(tabStr))
			tabStr = "1,1";
		int tab = Q.String(tabStr.split(",")[0]).toInteger();
		int type = Q.String(tabStr.split(",")[1]).toInteger();
		/* 问题检索 */
		if (tab == 1) {// 微博
			model = searchMblog(view, type == 1 ? 0 : 1, request);
		} else {// 问题
			switch (type) {
			case 1:
				model = questionControl
						.search(view, request, QuestionSearcher.TYPE_ALL, 0,
								request.getParameter("q"));
				break;
			case 2:
				model = searchGoodat(view, request);
				break;
			}
		}
		return model;
	}

	/**
	 * 搜索问题特长
	 * 
	 */
	private ModelAndView searchGoodat(String view, HttpServletRequest request)
			throws ApplicationException {
		String expert = this.getUser(request).getAttribute("expert");
		String keyword = "";
		if (!Q.isBlank(request.getParameter("q"))) {
			keyword = request.getParameter("q");
		} else {
			keyword = kindHelper.getKindLabels(expert).replaceAll("，", "");
		}
		ModelAndView model = questionControl.search(view, request,
				QuestionSearcher.TYPE_ALL, 0, keyword);
		model.addObject("expert", expert == null ? null : expert.split(","));// 检索条件
		return model;
	}

	/**
	 * 首页微博检索模块
	 * 
	 */
	private ModelAndView searchMblog(String view, int group,
			HttpServletRequest request) throws ApplicationException {
		ModelAndView model = new ModelAndView(view);
		long page = Q.String(request.getParameter("p")).toLong(1);
		long userId = group == 0 ? 0 : getUserId(request);
		Page<Mblog> list = mblogService
				.list(getSchoolId(request),
						0L,
						userId,
						group,
						page,
						Q.String(
								configHelper.getString("sns",
										"mblog.page.size", "10")).toLong());
		list.setRequest(request);
		model.addObject(RESULT, list);
		return model;
	}

}
