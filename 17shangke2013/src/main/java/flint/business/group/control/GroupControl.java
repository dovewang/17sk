package flint.business.group.control;

import javax.servlet.http.HttpServletRequest;

import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import entity.Group;
import flint.business.group.service.GroupService;
import flint.business.util.SchoolHelper;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.web.WebControl;

/**
 * 学习圈
 * 
 * @author Dove Wang
 * 
 */
@Controller
public class GroupControl extends WebControl {

	@Autowired
	private GroupService groupService;

	@RequestMapping("/user/group{type}.html")
	public ModelAndView my(@PathVariable("type") byte type,
			HttpServletRequest request) {
		return render("groups", groupService.my(this.getUserId(request), type));
	}

	/**
	 * 学习圈首页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/group/index.html")
	public ModelAndView index(HttpServletRequest request) {
		return null;
	}

	/**
	 * 学习圈申请页面，申请后需要学校管理员批准才能使用
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/group/apply.html")
	public ModelAndView apply(HttpServletRequest request) {
		return null;
	}

	/**
	 * 检查圈子的名字是否存在
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("/group/name.check.html")
	@ResponseBody
	public String checkName(String name, HttpServletRequest request) {
		return String.valueOf(groupService.checkGroupName(name,
				getSchoolId(request)));
	}

	/**
	 * 某个圈子的主页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/group/{groupId}/index.html")
	public ModelAndView group(@PathVariable("groupId") int groupId,
			HttpServletRequest request) {
		return null;
	}

	/**
	 * 圈子的邀请
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/group/invite.html")
	public ModelAndView invite(HttpServletRequest request) {
		return null;
	}

	/**
	 * 申请加入圈子
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/group/join.apply.html")
	public ModelAndView joinApply(HttpServletRequest request) {
		return null;
	}

	/**
	 * 加入圈子审核
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/group/join.check.html")
	public ModelAndView joinCheck(HttpServletRequest request) {
		return null;
	}

	/**
	 * 圈子成员管理
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/group/{groupId}/member.html")
	public ModelAndView member(@PathVariable("groupId") int groupId, int page,
			int size, HttpServletRequest request) {
		return this.render("members",
				groupService.findMembers(groupId, page, size));
	}

	/**
	 * 圈子设置
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/group/{groupId}/profile.html")
	public ModelAndView profile(@PathVariable("groupId") int groupId,
			HttpServletRequest request) {
		return null;
	}

	/**
	 * 圈子公告修改
	 * 
	 * @param groupId
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/group/{groupId}/announce.html", method = RequestMethod.POST)
	public ModelAndView announce(@PathVariable("groupId") int groupId,
			String announce, HttpServletRequest request)
			throws ApplicationException {
		if (groupService.authenticate(groupId, getUser(request))) {
			if (groupService.announce(groupId, announce) > 0) {
				return this.success("更新公告成功！");
			}
			return this.failure("更新公告失败！");
		} else {
			return this.permissionView(true);
		}
	}

	/**
	 * 圈内话题
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/group/{groupId}/topic.html")
	public ModelAndView topic(HttpServletRequest request) {
		return null;
	}

	/**
	 * 圈子选择器
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/group/select.html")
	public ModelAndView select(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("global:/common/group/select");
		long schoolId = SchoolHelper.getSchool(getDomain(request));
		long page = Q.String(request.getParameter("p")).toLong(1);
		byte type = Q.String(request.getParameter("type")).toByte();
		Page<Group> list = groupService.findGroup(getUserId(request), type,
				schoolId, 0, page, 10);
		list.setRequest(request);
		mav.addObject(RESULT, list);
		return mav;
	}

	@RequestMapping("/group/list.json.html")
	public ModelAndView list(HttpServletRequest request) {
		return null;
	}

	private ModelAndView permissionView(boolean isJson) {
		if (isJson) {
			return failure("您没有权限访问该班级或者账号被锁定，如有疑问请与管理员联系！");
		}
		return failureView("您没有权限访问该班级或者账号被锁定，如有疑问请与管理员联系！");
	}
}
