package flint.business.active.control;

import static kiss.util.Helper.$;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kiss.security.Identity;
import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import entity.Show;
import entity.ShowComment;
import entity.ShowVote;
import entity.Tag;
import flint.business.active.service.ShowService;
import flint.business.active.util.ShowSearcher;
import flint.business.constant.CourseConst;
import flint.business.constant.ShowConst;
import flint.business.constant.TagConst;
import flint.business.constant.UserConst;
import flint.business.tag.service.TagService;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.util.DateHelper;
import flint.util.NumberHelper;
import flint.util.StringHelper;
import flint.web.WebControl;

@Controller
public class ShowControl extends WebControl {

	@Autowired
	private ShowService showService;

	@Autowired
	private TagService tagService;

	@RequestMapping("/search/show.html")
	public ModelAndView index(HttpServletRequest request) {
		String tab = StringHelper.defaultIfEmpty(request.getParameter("tab"),
				"all");
		byte type = (Byte) Q.when(tab, "video",
				CourseConst.COURSE_TYPE_FORWARD, "online",
				CourseConst.COURSE_TYPE_ONLINE, (byte) 0);
		String keyword = request.getParameter("q");
		ModelAndView mav = this.search("/search/show", keyword, 0,
				ShowSearcher.TYPE_ALL, request, type);
		/* 秀秀搜索条件 */
		List<Tag> taglist = tagService.getTop(TagConst.SHOW, 15);
		mav.addObject("tagSearch", taglist);
		return mav;
	}

	@RequestMapping("/show/list.html")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = search("/show/list", null, 0, (byte) 0, request,
				(byte) 0);
		return mav;
	}

	@RequestMapping("/show/fav.html")
	public ModelAndView showfav(HttpServletRequest request) {
		ModelAndView mav = search("/show/list", null, getUserId(request),
				ShowSearcher.TYPE_FAV, request, (byte) 0);
		return mav;
	}

	@RequestMapping("/show/commentlist.html")
	public ModelAndView comment(HttpServletRequest request) {
		ModelAndView mav = search("/show/list", null, getUserId(request),
				ShowSearcher.TYPE_COMMENT, request, (byte) 0);
		return mav;
	}

	@RequestMapping("/show/u{id}-list.html")
	public ModelAndView listByUser(@PathVariable("id") long id,
			HttpServletRequest request) {
		ModelAndView mav = search("/show/list", null, id,
				ShowSearcher.TYPE_USER, request, (byte) 0);
		return mav;
	}

	private ModelAndView search(String view, String keyword, long userId,
			int type, HttpServletRequest request, byte courseType) {
		ModelAndView mav = new ModelAndView(view);
		Page<Show> list;
		try {
			ShowSearcher showSea = new ShowSearcher(keyword,
					request.getParameter("c"), userId, type, courseType);
			list = showService.search(showSea, $(request.getParameter("p"))
					.toLong(1), 9);
			list.setUrl(request.getRequestURI());
			list.setParam(StringHelper.queryStringReplace(
					request.getQueryString(), "p", "@"));
			mav.addObject(RESULT, list);
			mav.addObject("condition", showSea.getReponseParams());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		return mav;
	}

	/**
	 * 主题秀秀
	 * 
	 * @param request
	 */
	@RequestMapping("/show/topic.html")
	public void topic(HttpServletRequest request) {

	}

	/**
	 * 我来秀秀表单信息
	 * 
	 * @param request
	 */
	@RequestMapping("/show/form.html")
	public void form(HttpServletRequest request) {

	}

	/**
	 * 查看我来秀秀基本信息（编辑）
	 * 
	 * @param HttpServletRequest
	 * @return ModelAndView
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/show/view.html", method = RequestMethod.GET)
	public ModelAndView view(long sid, HttpServletRequest request)
			throws ApplicationException {
		Show show = showService.findById(sid);
		if (getUserId(request) != show.getUserId()) {
			return failureView("这个秀秀不属于您!");
		}
		ModelAndView view = new ModelAndView("/show/form");
		/* 如果已有人报名，课程发生修改后需要邮件方式通知用户 */
		view.addObject("show", show);
		return view;
	}

	/**
	 * 删除秀秀
	 * 
	 * @param questionId
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/show/del.html", method = RequestMethod.POST)
	public ModelAndView del(int sid, HttpServletRequest request)
			throws ApplicationException {
		if (showService.delete(sid) > 0) {
			return success("删除秀秀成功！");
		} else {
			return failure("删除秀秀失败！");
		}
	}

	/**
	 * 发布我来秀秀
	 * 
	 * @return
	 */
	@RequestMapping(value = "/show/publish.html", method = RequestMethod.POST)
	public ModelAndView publish(Show show, HttpServletRequest request) {
		show.setUserId(getUserId(request));
		int result = 0;
		if (show.getShowId() != 0) {// 更新
			result = showService.update(show);
		} else {// 新增
			result = showService.publish(show);
		}
		if (result > 0) {
			return success("发布成功！");
		} else {
			return failure("网络繁忙，请稍后再试！");
		}
	}

	/**
	 * 更新我来秀秀
	 * 
	 * @return
	 */
	@RequestMapping(value = "/show/update.html", method = RequestMethod.POST)
	public ModelAndView update(Show show) {
		if (showService.update(show) > 0) {
			return success("更新成功！");
		} else {
			return failure("网络繁忙，请稍后再试！");
		}
	}

	/**
	 * 删除我来秀秀
	 * 
	 * @return
	 */
	@RequestMapping(value = "/show/delete.html", method = RequestMethod.POST)
	public ModelAndView delete(long id) {
		if (showService.delete(id) > 0) {
			return success("删除成功！");
		} else {
			return failure("网络繁忙，请稍后再试！");
		}
	}

	/**
	 * 查看单个我来秀秀的信息
	 * 
	 * @param request
	 */
	@RequestMapping("/show/item-{id}.html")
	public ModelAndView item(@PathVariable("id") long id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/show/item");
		Show show = showService.findById(id);
		if(show == null || (show.getStatus() == ShowConst.DELETE) && getUser(request).is(UserConst.ADMIN)){
			return new ModelAndView("/show/item.notfound");
		}
		showService.addview(id);//增加浏览人数
		return mav.addObject("show", show);
	}

	/**
	 * 热点秀秀
	 * 
	 * @return
	 */
	@RequestMapping("/show/hot.html")
	public ModelAndView hot() {
		ModelAndView mav = new ModelAndView();
		mav.addObject(RESULT, showService.hot(6));
		return mav;
	}

	/**
	 * 评论
	 * 
	 * @param request
	 */
	@RequestMapping("/show/comment.html")
	public ModelAndView comment(long id, String content,
			HttpServletRequest request) {
		long userId = getUserId(request);
		ShowComment comment = new ShowComment();
		comment.setShowId(id);
		comment.setNetAddress(request.getRemoteAddr());
		comment.setContent(content);
		comment.setStatus((byte) 1);
		comment.setUserId(userId);
		comment.setDateline(DateHelper.getNowTime());
		comment.setParentId(NumberHelper.toLong(request.getParameter("pid")));
		if (showService.comment(comment, request.getParameter("title")) > 0) {
			return success("谢谢您的支持！");
		} else {
			return failure("网络繁忙，请稍后再试！");
		}
	}

	@RequestMapping(value = "/show/vote.html", method = RequestMethod.POST)
	public ModelAndView vote(long id, int support, HttpServletRequest request) {
		ShowVote vote = new ShowVote();
		vote.setShowId(id);
		vote.setSupport(support > 0);
		vote.setNetAddress(request.getRemoteAddr());
		vote.setUserId(getUserId(request));
		vote.setDateline(Q.now());
		if (showService.vote(vote) > 0) {
			return success("谢谢您的支持！");
		} else {
			return failure("网络繁忙，请稍后再试！");
		}
	}

	/**
	 * 限管理员使用
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/manager/show/comment.delete.html", method = RequestMethod.POST)
	public ModelAndView deleteComment(long id, String memo,
			HttpServletRequest request) {
		Identity user = this.getUser(request);
		memo = user.getName() + "(" + user.getId() + "):"
				+ request.getRemoteAddr() + "于" + Q.now("yyyy-MM-dd HH:mm:ss")
				+ "删除了秀秀评论，原因：" + memo;
		if (showService.deleteComment(id, memo) > 0) {
			return success("删除评论成功！");
		} else {
			return failure("网络繁忙，请稍后再试！");
		}
	}

	/**
	 * 评论列表
	 * 
	 * @param id
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/show/comments.{id}.{page}.html")
	public ModelAndView commentList(@PathVariable("id") long id,
			@PathVariable("page") long page, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/show/comments");
		Page<ShowComment> list = showService.listComments(id, page, 15);
		mav.addObject(RESULT, list);
		return mav;
	}

	/**
	 * 秀秀重建索引接口
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/show/createindexs.html")
	public ModelAndView createindexs(HttpServletRequest request)
			throws ApplicationException {
		if (getUser(request).is(UserConst.ADMIN))
			return failure("对不起您没有该权限!");
		if (showService.createIndexs() > 0) {
			return success("重建索引成功!");
		} else {
			return failure("失败，请稍后再试!");
		}
	}

}
