package flint.business.passport.control;

import static kiss.util.Helper.$;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kiss.security.Identity;
import kiss.security.Passport;
import kiss.security.authc.Authenticator;
import kiss.security.authc.UsernamePasswordToken;
import kiss.security.exception.AuthenticationException;
import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import entity.User;
import flint.business.constant.UserConst;
import flint.business.mail.MailManager;
import flint.business.security.Person;
import flint.business.user.service.UserService;
import flint.common.Result;
import flint.exception.ApplicationException;
import flint.security.word.WordFilter;
import flint.security.word.WordFilterResult;
import flint.util.RandomGenerator;
import flint.web.WebControl;
import flint.web.util.CookieHelper;

@Controller
public class PassportControl extends WebControl {

	@Autowired
	private UserService userService;
	@Autowired
	private MailManager mailManager;
	@Autowired
	private Authenticator authenticator;
	@Autowired
	private WordFilter wordFilter;

	/**
	 * 用户登陆界面
	 * 
	 * @param request
	 */
	@RequestMapping("/passport/login.html")
	public ModelAndView login(HttpServletRequest request) {
		String window = request.getParameter("window");
		/* ajax 登录窗口 */
		if (window != null) {
			return new ModelAndView(
					"global:/theme/main/default/passport/slogin");
		} else {
			return new ModelAndView("/passport/login");
		}
	}

	/**
	 * 用户注册页面
	 * 
	 */
	@RequestMapping("/passport/register.html")
	public void register(HttpServletRequest request) {

	}

	/**
	 * 学校注册页面
	 * 
	 * @param request
	 */
	@RequestMapping("/passport/school.register.html")
	public void registeSchool(HttpServletRequest request) {

	}

	/**
	 * 用户注册
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/passport/signup.html", method = RequestMethod.POST)
	public ModelAndView signUp(HttpServletRequest request,
			HttpServletResponse response) throws ApplicationException {
		Result<String> result = new Result<String>();
		// 从request中取值
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String userType = request.getParameter("userType");
		// String r = request.getParameter("r");// 邀请码
		// String needInvite = this.configHelper.getString("register",
		// "invite.must", "false");
		// if (!Q.isBlank(r)) {
		// result.addError(bindId, message)
		// }
		/* 验证数据信息 */
		if (Q.isEmpty(userName)) {
			result.addError("userName", "用户名不能空！");
		} else {
			/* 用户检查从最低级别过滤,0级别是过滤用户名专有的其他是国家不允许的内容 */
			WordFilterResult fresult = wordFilter.doFilter(userName, 0);
			if (fresult.hasDenyWords()) {
				result.addError("userName", "用户名包含敏感或违反规定的词组！");
			}
		}
		if (Q.isEmpty(userName)) {
			result.addError("password", "用户密码不能空");
		}
		if (Q.isEmpty(email)) {
			result.addError("email", "用户邮箱不能空");
		}

		if (Q.isEmpty(userType)) {
			result.addError("userType", "用户类型必选");
		}
		if (result.hasErrors()) {
			result.setStatus(Result.FAILURE);
			return jsonResult(result);
		}

		/* 保存到实体 */
		User entity = new User();
		entity.setName(userName);
		entity.setPassword(password);
		entity.setEmail(email);
		entity.setActive(UserConst.ACTIVE_NO);
		entity.setUserType($(userType).toByte(UserConst.STUDENT));
		entity.setStatus(UserConst.STATUS_NORMAL);// 正常(默认是正常)
		entity.setActiveCode(RandomGenerator.getRandomWord(32));
		entity.setCodeTime(Q.now());// 发送验证码时间
		entity.setRegiditTime(Q.now());// 注册时间
		long uid = userService.signUp(entity, getSchoolId(request));
		if (uid > 0) {
			/* 自动登录系统 */
			autoLogin(request, response, userName, password);
			Identity user = this.getUser(request);
			userService.email(user, entity.getActiveCode(), false);
			result.setResult(String.valueOf(uid));
		}
		return jsonResult(result);
	}

	@RequestMapping(value = "/passport/email.reactive.html")
	public ModelAndView reactiveEmail(HttpServletRequest request)
			throws ApplicationException {
		Identity user = this.getUser(request);
		userService.email(user, RandomGenerator.getRandomWord(32), true);
		return new ModelAndView(redirectView("/passport/success.html?t=sign"));
	}

	/**
	 * 自动登录系统
	 * 
	 * @param request
	 * @param username
	 * @param password
	 * @throws ApplicationException
	 */
	private void autoLogin(HttpServletRequest request,
			HttpServletResponse response, String username, String password)
			throws ApplicationException {
		try {
			authenticator.authenticate(getPassport(request),
					new UsernamePasswordToken(username, password, false));
		} catch (Exception e) {
			throw new ApplicationException(e);
		}

	}

	/**
	 * 用户注册，检查用户名是否存在，如果存在则生成推荐用户名
	 * 
	 * @param HttpServletRequest
	 * @return ModelAndView
	 * @throws ApplicationException
	 */
	@RequestMapping("/passport/name.check.html")
	public ModelAndView checkName(HttpServletRequest request)
			throws ApplicationException {
		String userName = request.getParameter("name");
		List<Object> ns = userService.checkName(userName);
		if (ns == null) {
			return render("check", "true");
		}
		return render("names", userService.checkName(userName));
	}

	/**
	 * 检查email是否已存在，主要功能1:、用户注册时检查邮箱是否重复
	 * 
	 * @param HttpServletRequest
	 * @return String
	 * @throws ApplicationException
	 */
	@RequestMapping("/passport/email.check.html")
	@ResponseBody
	public String checkEmail(HttpServletRequest request)
			throws ApplicationException {
		return String.valueOf(userService.existsEmail(
				request.getParameter("email"), request.getParameter("name")));
	}

	/*
	 * =======用户验证:1、邮箱验证；2、实名认证；3、手机验证、4学位认证
	 */

	/**
	 * 验证邮箱通行证
	 * 
	 * @param request
	 * @throws ApplicationException
	 * @throws NumberFormatException
	 */
	@RequestMapping(value = "/passport/active.html", method = RequestMethod.GET)
	public ModelAndView validateEmail(HttpServletRequest request)
			throws ApplicationException {
		String id = request.getParameter("id");
		String activeCode = request.getParameter("ac");
		if (userService.validateEmail(Long.parseLong(id), activeCode)) {
			return new ModelAndView(
					redirectView("https://passport.17shangke.com/success.html?t=active"));
		} else {
			return new ModelAndView(
					redirectView("https://passport.17shangke.com/failure.html?t=active"));
		}
	}

	/**
	 * 用户恢复密码界面
	 * 
	 * @return
	 * @throws ApplicationException
	 * @throws NumberFormatException
	 */
	@RequestMapping("/passport/recovery.html")
	public ModelAndView recovery(HttpServletRequest request)
			throws ApplicationException {
		String step = Q.isEmpty(request.getParameter("s"), "0");
		/* 第一步：填写邮箱 */
		if ("1".equals(step)) {
			/* 提交数据，发送邮件 */
			if ("true".equals(request.getParameter("g"))) {
				String activeCode = RandomGenerator.getRandomWord(32);
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				if (userService.recovery(name, email, activeCode) > 0) {
					Map<String, Object> value = new HashMap<String, Object>();
					value.put("userName", name);
					value.put("email", email);
					value.put("activeCode", activeCode);
					mailManager.recovery(value, email);
					return success("账户验证成功！");
				} else {
					return failure("很抱歉，用户和邮箱不匹配！");
				}
			}
		}
		/* 第二步：填写密码保护 */
		else if ("2".equals(step)) {

		}
		/* 第三步：短信验证 */
		else if ("3".equals(step)) {

		} else {
			/* 密码激活 */
			String email = request.getParameter("id");
			if (!Q.isEmpty(email)) {
				String activeCode = request.getParameter("ac");
				if (userService.validateRecovery(email, activeCode)) {
					return new ModelAndView("/passport/password");
				} else {
					return new ModelAndView(
							redirectView("https://passport.17shangke.com/failure.html?t=password.recovery"));
				}
			}
		}

		return new ModelAndView("/passport/recovery");
	}

	@RequestMapping(value = "/passport/password.reset.html", method = RequestMethod.POST)
	public ModelAndView resetPassword(HttpServletRequest request)
			throws ApplicationException {
		String newPassword = request.getParameter("newpassword");
		String email = request.getParameter("id");
		String activeCode = request.getParameter("ac");
		if (userService.resetPassword(email, activeCode, newPassword) > 0) {
			return success("恭喜您，密码重置成功！");
		} else {
			return failure("网络繁忙，请稍候再试……");
		}

	}

	/**
	 * 验证用户密码是否正确
	 * 
	 * @param request
	 * @throws ApplicationException
	 * @throws NumberFormatException
	 */
	@RequestMapping(value = "/passport/validatePassword.html", method = RequestMethod.POST)
	@ResponseBody
	public String validatePassword(HttpServletRequest request)
			throws ApplicationException {
		String password = request.getParameter("password");
		return String.valueOf(userService.validatePassword(getUserId(request),
				password));
	}

	/**
	 * 
	 * 修改用户密码
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/passport/change.html", method = RequestMethod.POST)
	public ModelAndView changePassword(HttpServletRequest request)
			throws ApplicationException {
		String oldPassword = request.getParameter("oldpassword");
		String newPassword = request.getParameter("newpassword");
		if (userService.changePassword(getUserId(request), oldPassword,
				newPassword) > 0) {
			return success("更新密码成功！");
		} else {
			return failure("更新密码失败！");
		}

	}

	@RequestMapping(value = "/user/changePassword.html", method = RequestMethod.POST)
	@ResponseBody
	public String changePassword1(HttpServletRequest request)
			throws ApplicationException {
		String oldPassword = request.getParameter("oldpassword");
		String newPassword = request.getParameter("newpassword");
		return String.valueOf(userService.changePassword(getUserId(request),
				oldPassword, newPassword));

	}

	@RequestMapping(value = "/passport/success.html", method = RequestMethod.GET)
	public void success(HttpServletRequest request) {

	}

	@RequestMapping(value = "/passport/failure.html", method = RequestMethod.GET)
	public void failure(HttpServletRequest request) {

	}

	/**
	 * 前台用户登录,后台验证
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 * @throws AuthenticationException
	 */
	@RequestMapping(value = "/passport/dologin.html", method = RequestMethod.POST)
	public ModelAndView doLogin(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			ApplicationException {
		Result<String> result = new Result<String>();
		String username = request.getParameter(Passport.USER);
		String password = request.getParameter(Passport.PASSWORD);
		String captcha = request.getParameter("captcha");
		String remeberme = request.getParameter(Passport.REMEBERME);
		String forwardUrl = request.getParameter("forwardUrl");
		/* 用户名不能空 */
		if (Q.isEmpty(username)) {
			result.addError(Passport.USER, "登录名不能空");
		}
		/* 密码不能空 */
		if (Q.isEmpty(password)) {
			result.addError(Passport.PASSWORD, "登录密码不能空");
		}
		Object captchas = request.getSession().getAttribute("captcha");
		if (captchas == null || !captchas.equals(captcha)) {
			return failure("验证码输入错误");
		}

		if (result.hasErrors()) {
			result.setStatus(Result.FAILURE);
			return jsonResult(result);
		}
		Passport member = getPassport(request);
		member.setAttribute(Passport.USER, username);
		member.setAttribute("login.schoolId", this.getSchoolId(request));
		// member.setNetAddress(request.getRemoteAddr());
		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password, "1".equals(remeberme));
		Passport user = authenticator.authenticate(member, token);

		if (user.isAuthenticated()) {
			try {
				/* 添加用户登录信息 */
				if ("1".equals(remeberme)) {
					CookieHelper.addSecureCookie(response,
							Passport.COOKIE_PASSPORT,
							(String) user.getAttribute("cookie"),
							Passport.COOKIE_MAX_AGE);
				}
			} catch (Exception e) {
				throw new ApplicationException("cookie error:{}", e);
			}
			Identity identity = user.get();
			/* 对于学校情况的处理,学生自动转到该学生的班级首页，下次调整为用户自定义进入首页，学校内部直接转向首页，这里稍后需要调整 */
			if (identity.getDomainId() != 0 && identity.is(UserConst.STUDENT)) {
				forwardUrl = "/class/index.html";
			}

			result.setResult(forwardUrl);
			return jsonResult(result);
		} else {
			result.setStatus(Result.FAILURE);
			result.setCode(String.valueOf(user.removeAttribute("code")));
			return jsonResult(result);
		}

	}

	/**
	 * 用户身份切换 禁用该功能
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/passport/doOtherlogin.html", method = RequestMethod.POST)
	public ModelAndView doOtherlogin(HttpServletRequest request,
			HttpServletResponse response) throws ApplicationException {
		// request.getSession().setAttribute(
		// Passport.PRINCIPAL,
		// authenticator.changeUser(this.getUser(request).getPassport(),Q.toLong(userid),
		// response));
		return success("切换用户成功!");
	}

	/**
	 * 注销某一处的session，并不意味着用户完全退出，用户可能在其他位置登录
	 * 
	 * @param request
	 * @return 请求的view名称
	 */
	@RequestMapping("/passport/logout.html")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) throws ApplicationException {
		HttpSession session = request.getSession();
		session.invalidate();
		CookieHelper.removeCookies(request, response, new String[] {
				Passport.COOKIE_USER_ID, Passport.COOKIE_USER_NAME,
				Passport.COOKIE_PASSPORT });
		return "redirect:"
				+ Q.isEmpty(request.getParameter("url"), "/index.html");

	}

	/**
	 * 主要供QQ等登录账户使用
	 * 
	 * @param request
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/selectUserType.html", method = RequestMethod.POST)
	public ModelAndView selectUserType(HttpServletRequest request, byte type) {
		Person user = (Person) this.getUser(request);
		long userId = Long.parseLong(user.getId());
		if (userId != 0) {
			if (type >= UserConst.ENTERPRISE) {
				return failure("非法用户类型");
			}
			if (userService.selectUserType(userId, type) > 0) {
				user.setType(type);
				return success("选择用户身份成功");
			} else {
				return failure("网络繁忙，请稍后再试！");
			}
		}
		return failure("您还没有登录");
	}
}
