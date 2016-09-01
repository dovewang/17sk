package flint.business.mblog.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import entity.User;
import flint.business.mblog.service.FollowService;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.util.NumberHelper;
import flint.util.StringHelper;
import flint.web.WebControl;
import flint.web.util.CookieHelper;

/**
 * 微博用户关注功能
 * 
 * @author Dove Wang
 * 
 */
@Controller
public class FollowControl extends WebControl {

	@Autowired
	private FollowService followService;

	@RequestMapping(value = "/follow/index.html", method = RequestMethod.GET)
	public void follow(HttpServletRequest request) throws ApplicationException {

	}

	/**
	 * 关注某个用户
	 * 
	 * @param userId
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/follow/focus.html", method = RequestMethod.POST)
	public ModelAndView focus(long userId, HttpServletRequest request)
			throws ApplicationException {
		if (followService.focus(userId, getUserId(request)) > 0) {
			return success("关注成功！");
		} else {
			return failure("关注失败！");
		}
	}

	/**
	 * 取消关注用户
	 * 
	 * @param userId
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/follow/blur.html", method = RequestMethod.POST)
	public ModelAndView blur(long userId, HttpServletRequest request)
			throws ApplicationException {
		if (followService.blur(userId, getUserId(request)) > 0) {
			return success("取消关注成功！");
		} else {
			return failure("取消关注失败！");
		}
	}

	/**
	 * 设置关注用户分组
	 * 
	 * @param userId
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/follow/group.html", method = RequestMethod.POST)
	public ModelAndView group(long userId, long groupId,
			HttpServletRequest request) throws ApplicationException {
		if (followService.group(userId, getUserId(request), groupId) > 0) {
			return success("设置分组成功！");
		} else {
			return failure("设置分组失败！");
		}
	}

	/**
	 * 查找用户粉丝(同时更新At消息状态)
	 * 
	 * @param userId
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/follow/fans.html", method = RequestMethod.GET)
	public ModelAndView fans(long id, HttpServletRequest request, HttpServletResponse response)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView("/follow/list");
		long page = NumberHelper.toLong(request.getParameter("p"), 1);
		long size = NumberHelper.toLong(request.getParameter("s"), 10);
		Page<User> list = followService.getFansAndAtStatus(id, page, size);
		list.setRequest(request);
		mav.addObject(RESULT, list);
		/* 清空缓存的At记录的Cookie信息*/
		CookieHelper.removeCookie(request, response, "message.count");
		return mav;
	}

	/**
	 * 用户关注的人
	 * 
	 * @param userId
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/follow/followers.html", method = RequestMethod.GET)
	public ModelAndView followers(long id, HttpServletRequest request)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView("/follow/list");
		long page = NumberHelper.toLong(request.getParameter("p"), 1);
		long size = NumberHelper.toLong(request.getParameter("s"), 10);
		Page<User> list = followService.followers(id, page, size);
		list.setRequest(request);
		mav.addObject(RESULT, list);
		return mav;
	}

	/**
	 * 猜用户喜欢的，重cookie中读取数据
	 * 
	 * @param userId
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/follow/guess.html", method = RequestMethod.GET)
	public ModelAndView guess(HttpServletRequest request)
			throws ApplicationException {
		String focus = StringHelper.defaultIfEmpty(
				CookieHelper.getCookieValue(request, "user.focus"), "");
		long userId = getUserId(request);
		long page = NumberHelper.toLong(request.getParameter("p"), 1);
		long size = NumberHelper.toLong(request.getParameter("n"), 1);
		return render(RESULT, followService.guess(focus, userId, page, size));
	}

	/**
	 * 推荐用户关注的
	 * 
	 * @param userId
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/follow/recommend.html", method = RequestMethod.GET)
	public ModelAndView recommend(long userId, HttpServletRequest request)
			throws ApplicationException {
		followService.recommend(userId);
		return null;
	}


	@RequestMapping(value = "/follow/select.html", method = RequestMethod.GET)
	public ModelAndView select(long userId, HttpServletRequest request)
			throws ApplicationException {
		return null;
	}
}
