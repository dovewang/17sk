package flint.business.zone.control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import flint.business.mblog.service.MblogService;
import flint.business.user.service.UserService;
import flint.web.WebControl;

@Controller
public class ZoneControl extends WebControl {

	@Autowired
	private UserService userService;

	@Autowired
	private MblogService mblogService;

	/**
	 * 个人主页
	 * 
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping("/zone/{userId}/index.html")
	public ModelAndView index(@PathVariable("userId") long userId,
			HttpServletRequest request) {
		/* 需要判断用户的类型，不同类型的用户首页可能不相同 */
		return null;
	}

	/**
	 * 关于
	 * 
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping("/zone/{userId}/about.html")
	public ModelAndView about(@PathVariable("userId") long userId,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/zone/about");
		mav.addObject("user", userService.getUser(userId));
		return mav;
	}

}
