package flint.business.mblog.control;

import static kiss.util.Helper.$;

import javax.servlet.http.HttpServletRequest;

import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import entity.Mblog;
import entity.MblogComment;
import flint.business.mblog.service.MblogService;
import flint.business.util.SchoolHelper;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.util.DateHelper;
import flint.util.NumberHelper;
import flint.web.WebControl;

@Controller
public class MblogControl extends WebControl {

	@Autowired
	private MblogService mblogService;

	/**
	 * 微博首页
	 * 
	 * @return
	 */
	@RequestMapping("/m/index.html")
	public String index(HttpServletRequest request) {
		return "/m/index";
	}

	/**
	 * 微博列表，这里只提供学校部分的公共交流列表。圈子的交流请查看
	 * 
	 * @see {@link flint.business.group.control.GroupControl#twitter(HttpServletRequest)}
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/m/list{group}-{userId}.html")
	public ModelAndView list(@PathVariable("group") String group,
			@PathVariable("userId") String uid, HttpServletRequest request) {
		long mygroup = $(request.getParameter("g")).toLong();
		long groupId = $(group).toLong();// 这里需要验证用户的权限，目前是完全开放，需要设置浏览权限才有效
		long userId = $(uid).toLong();
		ModelAndView mav = new ModelAndView("global:/common/mblog/list");
		long page = $(request.getParameter("page")).toLong();
		Page<Mblog> list = mblogService.list(getSchoolId(request), groupId,
				userId, mygroup, page,
				$(configHelper.getString("sns", "mblog.page.size", "10"))
						.toInteger());
		list.setRequest(request);
		mav.addObject(RESULT, list);
		mav.addObject("group", mygroup);
		return mav;
	}

	/**
	 * 发送一条微博信息，范围：公共的，学校的，班级的，学习圈的
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/m/post.html", method = RequestMethod.POST)
	@ResponseBody
	public String post(HttpServletRequest request) {
		Mblog m = new Mblog();
		m.setDateline(Q.now());
		/* 设置微博发送的圈子 */
		m.setGroupId($(request.getParameter("gid")).toLong());
		/* 改字段已做调整，直接存在JSON对象，在前台定义模版处理 */
		m.setMedia(request.getParameter("media"));
		m.setSchoolId(SchoolHelper.getSchool(getDomain(request)));
		m.setContent(request.getParameter("message"));
		m.setUserId(getUserId(request));
		return String.valueOf(mblogService.post(m));
	}

	/**
	 * 加载评论信息
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/m/comments.html")
	public ModelAndView comments(long id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("global:/common/mblog/comment");
		long page = $(request.getParameter("page")).toLong(1);
		mav.addObject(RESULT, mblogService.comments(
				id,
				page,
				$(
						configHelper.getString("sns",
								"mblog.comment.page.size", "10")).toLong()));
		return mav;
	}

	/**
	 * 发布一个评论
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/m/comment.html", method = RequestMethod.POST)
	@ResponseBody
	public String comment(long id, HttpServletRequest request) {
		MblogComment comment = new MblogComment();
		comment.setMid(id);
		comment.setCreateTime(Q.now());
		comment.setContent(request.getParameter("text"));
		comment.setUserId(getUserId(request));
		return String.valueOf(mblogService.comment(comment));
	}

	/**
	 * 获取某一用户刚发布的单条微博信息
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/m/new/item-{id}.html")
	public ModelAndView item(@PathVariable("id") long id,
			HttpServletRequest request) throws ApplicationException {
		ModelAndView mav = new ModelAndView("global:/common/mblog/item");
		Mblog m = mblogService.item(id, this.getUserId(request));
		if (m == null) {
			throw new ApplicationException("您访问的数据不存在或者您没有权限访问!");
		}
		mav.addObject("m", m);
		return mav;
	}

	/**
	 * 转发微博
	 * 
	 * @param id
	 *            微博编号
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/m/forward.html", method = RequestMethod.POST)
	public ModelAndView forward(long id, HttpServletRequest request) {
		if (mblogService.forward(id, getUserId(request)) > 0) {
			return success("转发成功！");
		} else {
			return failure("转发失败！");
		}
	}

	/**
	 * 用户删除微博
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/m/delete.html", method = RequestMethod.POST)
	public ModelAndView delete(long id, HttpServletRequest request) {
		if (mblogService.delete(id, this.getUserId(request),
				request.getParameter("reason")) > 0) {
			return success("删除成功！");
		} else {
			return failure("删除失败！");
		}
	}

	/**
	 * 首页随便说说Box
	 * 
	 * @param userId
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/m/guessMblog.html", method = RequestMethod.GET)
	public ModelAndView guessDemand(HttpServletRequest request)
			throws ApplicationException {
		return render(RESULT, mblogService.getTop(0, 12));
	}

	/**
	 * 删除微博评论
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/m/delComment.html", method = RequestMethod.POST)
	public ModelAndView deleteComment(int id, long mid,
			HttpServletRequest request) throws ApplicationException {
		if (mblogService.deleteComment(id, mid) > 0) {
			return success("删除评论成功！");
		} else {
			return failure("删除评论失败！");
		}
	}
}
