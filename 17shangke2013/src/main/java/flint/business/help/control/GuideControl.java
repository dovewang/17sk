package flint.business.help.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kiss.security.Identity;
import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import flint.business.constant.UserConst;
import flint.business.user.service.UserService;
import flint.exception.ApplicationException;
import flint.util.StringHelper;
import flint.web.WebControl;
import flint.web.util.CookieHelper;

/**
 * 用户向导相关
 * 
 * @author Dove Wang
 * 
 */
@Controller
public class GuideControl extends WebControl {

	@Autowired
	private UserService userService;

	/**
	 * 用户初次登陆，检查用户的基本信息，要求用户修改或调整必要的信息
	 * 
	 * @param request
	 * @throws ApplicationException
	 */
	@RequestMapping("/guide/first.html")
	public void first(HttpServletRequest request) throws ApplicationException {

	}

	/**
	 * 用户首次提交
	 * 
	 * @param request
	 * @throws ApplicationException
	 */
	@RequestMapping("/guide/interest.html")
	public ModelAndView interest(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		String focus = request.getParameter("focus");
		String expert = request.getParameter("expert");
		String grade = request.getParameter("grade");
		String area = request.getParameter("area");
		if (!StringHelper.isEmpty(focus)) {
			CookieHelper.addCookie(response, "user.focus", focus, 3600 * 24 * 60, "/", false);
		} else {
			focus = "";
		}
		if (!StringHelper.isEmpty(expert)) {
			CookieHelper.addCookie(response, "user.expert", expert, 3600 * 24 * 60, "/", false);
		} else {
			expert = "";
		}
		if (!StringHelper.isEmpty(grade)) {
			CookieHelper.addCookie(response, "user.grade", grade, 3600 * 24 * 60, "/", false);
		} else {
			grade = "0";
		}

		/* 判断用户是否登录，且不是匿名用户，代表用户第一次登录 */
		Identity user = this.getUser(request);
		if (user.is(UserConst.ANONYMOUS) && !Q.isEmpty(expert)) {
			userService.expert(Long.parseLong(user.getId()), expert);
		}
		return success("恭喜您，设置成功！");
	}
}
