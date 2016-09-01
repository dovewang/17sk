package flint.business.user.control;

import static kiss.util.Helper.$;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kiss.security.Identity;
import kiss.security.Passport;
import kiss.storage.client.SpringSmarty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import entity.Collect;
import entity.User;
import entity.UserAddress;
import entity.UserExtends;
import flint.business.constant.UserConst;
import flint.business.security.Person;
import flint.business.trade.service.TradeService;
import flint.business.user.service.UserService;
import flint.business.user.util.UserSearcher;
import flint.business.util.ImageHelperFactory;
import flint.business.util.SchoolHelper;
import flint.common.Page;
import flint.core.Configuration;
import flint.exception.ApplicationException;
import flint.util.DateHelper;
import flint.util.NumberHelper;
import flint.util.StringHelper;
import flint.web.WebControl;

@Controller
public class UserControl extends WebControl {
	@Autowired
	private UserService userService;

	@Autowired
	private TradeService tradeService;

	@Autowired
	private SpringSmarty springSmarty;

	/**
	 * 个人中心主页面
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/user/index.html")
	public ModelAndView index(HttpServletRequest request)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView("/user/index");
		Identity user = getUser(request);
		long userId = Long.parseLong(user.getId());
		/* 用户相关信息 */
		mav.addAllObjects(userService.get(userId, user));
		/* 近期交易情况统计 */
		mav.addObject("tradeStat", tradeService.stat(userId));
		return mav;
	}

	/*
	 * ============================================================
	 * 
	 * 用户基本信息修改模块： 1、基本信息 2、详细信息 3、头像上传 4、密码修改
	 * 
	 * ===========================================================
	 */

	/**
	 * 用户信息查看页面
	 * 
	 * @param name
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/user/profile.html", method = RequestMethod.GET)
	public ModelAndView profile(HttpServletRequest request)
			throws ApplicationException {
		ModelAndView view = new ModelAndView("/user/user");
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap = userService.viewUser(getSchoolId(request), getUserId(request));
		User user = (User) userMap.get("user");
		view.addObject("user", user);
		UserExtends userx = (UserExtends) userMap.get("userExtends");
		if (userx.getBirthday() != 0) {
			view.addObject("birthday",
					DateHelper.getNow(userx.getBirthday(), DateHelper.DATE));
		} else {
			view.addObject("birthday", "");
		}
		view.addObject("userx", userx);
		view.addObject("course", userMap.get("course"));
		return view;
	}

	/**
	 * 修改用户资料
	 * 
	 * @param HttpServletRequest
	 * @return ModelAndView
	 * @throws ApplicationException
	 */
	@RequestMapping("/user/updateUser.html")
	public ModelAndView saveUser(HttpServletRequest request, User user)
			throws ApplicationException {
		user.setUserId(getUserId(request));
		if (userService.updateUser(this.getSchoolId(request), user) > 0) {
			/* 更新缓存*/
			Person person = (Person)this.getUser(request);
			person.setName(user.getRealName());
			person.getPassport().setAttribute("expert", user.getExpert());
			person.getPassport().setAttribute("focus", user.getFocus());
			return success("保存用户信息成功！");
		} else {
			return failure("保存用户信息失败！");
		}

	}

	/**
	 * 修改用户扩展资料
	 * 
	 * @param HttpServletRequest
	 * @return ModelAndView
	 * @throws ApplicationException
	 * @throws ParseException
	 */
	@RequestMapping("/user/updateExtends.html")
	public ModelAndView updateExtends(HttpServletRequest request,
			UserExtends extend) throws ApplicationException {
		extend.setUserId(getUserId(request));
		if (userService.updateUserExtends(extend) > 0) {
			return success("保存用户详细信息成功！");
		} else {
			return failure("保存用户详细信息失败！");
		}

	}

	/**
	 * 上传用户头像，缩放为500*500图像
	 * 
	 * @param file
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/user/uploadAvatar.html")
	@ResponseBody
	public String uploadAvatar(@RequestParam("avatar") MultipartFile file,
			HttpServletRequest request) throws ApplicationException {
		/* 压缩图片 */
		String path = "/files/avatar/" + getUserId(request);
		try {
			ImageHelperFactory.resize(file.getInputStream(), 300, 300,
					Configuration.getWebRoot() + path + ".o.jpg");
		} catch (IOException e) {
			throw new ApplicationException(e);
		}
		return "{\"path\":\"" + path + "\"}";
	}

	/**
	 * 编辑切割生成缩略图，将图片生成3中格式 180*180,75*75,50*50
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/user/saveAvatar.html")
	public ModelAndView saveAvatar(HttpServletRequest request)
			throws ApplicationException {
		Passport user = getPassport(request);
		int x = NumberHelper.toInt(request.getParameter("x"));
		int y = NumberHelper.toInt(request.getParameter("y"));
		int h = NumberHelper.toInt(request.getParameter("h"));
		int w = NumberHelper.toInt(request.getParameter("w"));
		String path = "files/avatar/" + user.getId();
		try {
			ImageHelperFactory.face(x, y, h, w, Configuration.getWebRoot()
					+ "/" + path);
		} catch (IOException e) {
			throw new ApplicationException(e);
		}
		path = springSmarty.getConfig().getProperty("storage.http.image",
				"/image.server/")
				+ path + ".jpg";
		user.setAttribute("face", path);
		if (userService.saveAvatar(path, Long.parseLong(user.getId())) > 0) {
			return success("更新头像成功!", "path", path);
		} else {
			return failure("更新头像失败!");
		}
	}

	/* ============================ */
	/**
	 * 学习记录
	 * 
	 * @return
	 */
	@RequestMapping("/user/footprint.html")
	public ModelAndView footprint() {
		return new ModelAndView();
	}

	/**
	 * 认证管理
	 * 
	 * @return
	 */
	@RequestMapping("/user/cert.html")
	public ModelAndView cert() {
		return new ModelAndView();
	}

	/**
	 * 批量获取页面上所有用户的状态
	 * 
	 * @param u
	 *            一批或者单个用户编号
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/ping.user.html", method = RequestMethod.POST)
	public ModelAndView pingUser(String u, HttpServletRequest request)
			throws ApplicationException {
		boolean full = "true".equals(request.getParameter("full"));
		if (full) {
			return render(
					RESULT,
					userService.getUsercard($(u).toLong(), getUser(request),
							$(request.getParameter("gid")).toLong()));
		}
		return render(
				RESULT,
				userService.ping(u, getUser(request),
						$(request.getParameter("gid")).toLong()));
	}

	/* ===================用户收藏==================== */
	@RequestMapping(value = "/user/fav.html")
	public void fav() throws ApplicationException {

	}

	/**
	 * 添加到收藏
	 * 
	 * @param request
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/user/toFav.html")
	public ModelAndView toFav(HttpServletRequest request, String objectId,
			byte objectType) throws ApplicationException {
		int result = userService.collect(getUserId(request), objectId,
				objectType);
		if (result == 0) {
			return warn("您已经收藏过了");
		} else if (result > 0) {
			return success("收藏成功");
		} else {
			return failure("网络繁忙,请稍后再试!");
		}
	}

	/**
	 * 删除收藏
	 * 
	 * @param request
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/user/delFav.html")
	public ModelAndView deleteFav(HttpServletRequest request, String objectId,
			byte objectType) throws ApplicationException {
		if (userService.deleteCollect(getUserId(request), objectId, objectType) > 0) {
			return success("取消收藏成功");
		} else {
			return failure("取消收藏失败");
		}
	}

	/**
	 * 邀请其他用户来关注你
	 * 
	 * @param userId
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/user/invite.html", method = RequestMethod.GET)
	public void invite(HttpServletRequest request) throws ApplicationException {

	}

	/* ================教师检索及其相关模块=============== */

	/**
	 * 公共检索
	 * 
	 * @param viewName
	 * @param request
	 * @param type
	 * @return
	 * @throws ApplicationException
	 */
	private ModelAndView search(String viewName, HttpServletRequest request,
			int type, byte seltype) throws ApplicationException {
		long schoolId = SchoolHelper.getSchool(getDomain(request));
		long size = NumberHelper.toLong(configHelper.getString("user",
				"mypagesize", "20"));
		long page = NumberHelper.toLong(request.getParameter("p"), 1);
		ModelAndView view = new ModelAndView(viewName);
		UserSearcher qs = new UserSearcher(schoolId, request.getParameter("c"),
				request.getParameter("q"), type);
		Page<User> list = userService.search(this.getUserId(request), page,
				size, schoolId, qs, seltype);
		list.setRequest(request);
		view.addObject(RESULT, list);
		view.addObject("condition", qs.getReponseParams());
		return view;
	}

	/**
	 * 顶部教师检索
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/topsearch/teacher.html")
	public ModelAndView topsearchTeacher(HttpServletRequest request)
			throws ApplicationException {
		long schoolId = SchoolHelper.getSchool(getDomain(request));
		UserSearcher qs = new UserSearcher(schoolId, request.getParameter("c"),
				request.getParameter("q"), UserConst.TEACHER);
		Page<User> list = userService.search(this.getUserId(request), 1, 3,
				schoolId, qs, (byte) 0);
		return render(RESULT, list);
	}

	/**
	 * 教师检索页面
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/search/teacher.html")
	public ModelAndView searchTeacher(HttpServletRequest request)
			throws ApplicationException {
		return search("/search/teacher", request, UserConst.TEACHER, (byte) 2);
	}

	/**
	 * 学生检索页面
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/search/student.html")
	public ModelAndView searchStudent(HttpServletRequest request)
			throws ApplicationException {
		return search("/search/student", request, UserConst.STUDENT, (byte) 1);
	}

	/**
	 * 获取推荐的老师或者学生
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/getUsers.html")
	public ModelAndView getSuggestUsers(HttpServletRequest request)
			throws ApplicationException {
		int nums = NumberHelper.toInt(request.getParameter("n"), 9);// 取得的用户个数
		String type = StringHelper.defaultIfEmpty(request.getParameter("t"),
				"TEACHER");
		return render(RESULT, userService.getSomeUsers(
				SchoolHelper.getSchool(getDomain(request)), getUserId(request),
				nums, type));
	}

	/**
	 * 用户展示信息页面
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/u/{userId}.html", method = RequestMethod.GET)
	public ModelAndView space(@PathVariable("userId") long userId,
			HttpServletRequest request) throws ApplicationException {
		ModelAndView mav = new ModelAndView("/user");
		mav.addAllObjects(userService.get(userId, getUser(request)));
		return mav;
	}

	/**
	 * 用户选择界面，例如邀请用户加入教室
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/user/select.html")
	public void select(HttpServletRequest request) throws ApplicationException {
	}

	/**
	 * 查找出擅长该知识点的用户
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/user/getExpertUser.html")
	public ModelAndView getExpertUser(long grade, long kind,
			HttpServletRequest request) throws ApplicationException {
		return render(RESULT, userService.getExpertUser(grade, kind,
				getSchoolId(request), getUserId(request)));
	}

	/* =======用户地址管理，主要供线下课程使用============= */

	@RequestMapping(value = "/user/address.html")
	public ModelAndView address(HttpServletRequest request)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView();
		mav.addObject("address",
				userService.findAddress(this.getUserId(request)));
		return mav;
	}

	@RequestMapping(value = "/user/address.save.html", method = RequestMethod.POST)
	public ModelAndView saveAddress(HttpServletRequest request,
			UserAddress address) throws ApplicationException {
		address.setUserId(this.getUserId(request));
		address.setStatus((byte) 1);
		long id = userService.saveAddress(address);
		if (id > 0) {
			return success("保存成功！", "id", String.valueOf(id));
		} else {
			return failure("保存失败！");
		}
	}

	@RequestMapping(value = "/user/address.delete.html")
	public ModelAndView deleteAddress(HttpServletRequest request, long id)
			throws ApplicationException {
		if (userService.deleteAddress(id) > 0) {
			return success("删除成功！");
		} else {
			return failure("删除失败！");
		}
	}

	/**
	 * 返回已经收藏的ID
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getCollects.html")
	public ModelAndView getmanageruser(String objectids, byte objectType,
			HttpServletRequest request) {
		List<Collect> list = userService.getcollect(getUserId(request),
				objectids, objectType);
		return render(RESULT, list);
	}

}
