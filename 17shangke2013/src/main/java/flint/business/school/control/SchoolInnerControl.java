package flint.business.school.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import kiss.storage.client.SpringSmarty;
import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import entity.Group;
import entity.School;
import flint.business.constant.GroupConst;
import flint.business.group.service.GroupService;
import flint.business.mail.MailManager;
import flint.business.school.service.SchoolService;
import flint.business.util.SchoolHelper;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.util.RandomGenerator;
import flint.util.WebHelper;
import flint.web.WebControl;

/**
 * 学校内部后台
 * 
 * @author Dove Wang
 * 
 */
@Controller
public class SchoolInnerControl extends WebControl {
	@Autowired
	private MailManager mailManager;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private SpringSmarty springSmarty;

	/**
	 * 检查用户邮件是否存在： {global:true,school:false}
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/user/email.html")
	public ModelAndView checkEmail(HttpServletRequest request) {
		return render(schoolService.checkEmail(request.getParameter("email"),
				SchoolHelper.getSchool(getDomain(request))));
	}

	/**
	 * 后台管理首页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/index.html")
	public ModelAndView admin(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/admin/index");
		return mav;
	}

	/**
	 * 用户管理
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/user.html")
	public ModelAndView userManager(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/admin/user");
		mav.addObject(RESULT, groupService.findGroupList(
				SchoolHelper.getSchool(getDomain(request)), GroupConst.CLASS));
		return mav;
	}

	@RequestMapping("/admin/course.html")
	public void course(HttpServletRequest request) {

	}

	/**
	 * 学校 基本信息页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/school/index.html")
	public ModelAndView systemeManager(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/admin/school/index");
		mav.addObject("school",
				schoolService.get(SchoolHelper.getSchool(getDomain(request))));
		return mav;
	}

	/**
	 * 学校基本信息保存
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/school.save.html", method = RequestMethod.POST)
	public ModelAndView saveSchool(School school,
			MultipartHttpServletRequest request) throws IOException {
		// 获取表单中的文件资源
		MultipartFile logoFile = request.getFile("logoFile");
		if (logoFile.getSize() > 0) {
			UUID uuid = UUID.randomUUID();
			String logoName = logoFile.getOriginalFilename();
			logoName = uuid
					+ logoName.substring(logoName.lastIndexOf("."),
							logoName.length());
			String logoPath = springSmarty.upload(logoFile,
					"files/logo/" + logoName, "\\.(gif|jpg|jpeg|bmp|png|tif)$",
					1024 * 5 * 1024).getHttp();
			school.setLogo(logoPath);
		}
		if (schoolService.update(school) > 0) {
			return success("更新学校信息成功！");
		} else {
			return failure("更新学校信息失败！");
		}
	}

	/* ==========用户管理==================== */
	@RequestMapping("/admin/user/index.html")
	public ModelAndView search(HttpServletRequest request)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView("/admin/user/index");
		mav.addObject("years", groupService.findGroupYears(
				getSchoolId(request), GroupConst.CLASS));
		return mav;
	}

	@RequestMapping("/admin/user/list.html")
	public ModelAndView list(HttpServletRequest request)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView("/admin/user/list");
		long classid = Q.toLong(request.getParameter("classid"));
		byte type = Q.toByte(request.getParameter("type"));
		long schoolId = this.getSchoolId(request);
		long page = Q.toLong(request.getParameter("p"), 1);
		Page<Map<String, Object>> list = schoolService.search(schoolId,
				classid, type, page, 15);
		list.setRequest(request);
		mav.addObject(RESULT, list);
		return mav;
	}

	/**
	 * 用户信息编辑
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/user/form.html")
	public ModelAndView editUser(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/admin/user/form");
		String is = request.getParameter("is");
		if (!Q.isEmpty(is)) {// 新增
			mav.addObject(
					RESULT,
					schoolService.getClassName(Q.toLong(
							request.getParameter("cid"), 0)));
		} else {
			String uid = request.getParameter("uid");
			mav.addObject(
					RESULT,
					schoolService.getClassUser(
							Q.toLong(request.getParameter("cid")),
							Q.toLong(uid, 0)));
		}
		return mav;
	}

	/**
	 * 保存学校内部用户的信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/user/save.html", method = RequestMethod.POST)
	public ModelAndView saveUser(HttpServletRequest request) {
		long schoolid = SchoolHelper.getSchool(getDomain(request));
		String email = request.getParameter("email");
		String id = request.getParameter("id");// 学号/教号
		String name = request.getParameter("username");
		byte type = Q.toByte(request.getParameter("userType"), (byte) 1);
		long userid = Q.toLong(request.getParameter("userid"));
		long classid = Q.toLong(request.getParameter("classid"));
		String tel = request.getParameter("tel");
		if (userid != 0) {// 更新
			if (schoolService.updateUser(userid, name, tel, id) > 0) {
				return success("更新用户信息成功!");
			} else {
				return failure("更新用户信息失败!");
			}
		} else {// 新增
			int result = 0;
			/* 新用户激活码 */
			String activeCode = RandomGenerator.getRandomWord(32);
			try {
				result = schoolService.addSchoolUser(userid, name, email, tel,
						type, id, schoolid, classid, activeCode);
			} catch (ApplicationException e) {
				return failure(e.getMessage());
			}
			if (result == 3 || result == 2) {
				sendEmail(schoolid, result, email, name, activeCode, request);
				return success("添加用户成功,已通知联系人!");
			} else if (result == 1) {
				return success("添加用户成功");
			} else {
				return failure("添加失败!");
			}
		}
	}

	private void sendEmail(long schoolId, int type, String email,
			String username, String activeCode, HttpServletRequest request) {
		Map<String, Object> value = new HashMap<String, Object>();
		value.put("username", username);
		value.put("email", email);
		value.put("activecode", activeCode);
		String maindomain = configHelper.getString("site", "domain");
		String subdomain = WebHelper.getSubDomain(request, maindomain);
		if (type == 3) {// 存在主站不存在该学校的新用户
			mailManager.joinSchool(subdomain, value, email);
		} else if (type == 2) {// 都不存在的新用户
			mailManager.resetPass(subdomain, value, email);
		}
	}

	@RequestMapping(value = "/admin/user/lock.html", method = RequestMethod.POST)
	public ModelAndView lockUser(HttpServletRequest request) {
		long schoolId = SchoolHelper.getSchool(getDomain(request));
		long userId = Long.parseLong(request.getParameter("id"));
		if (schoolService.lockUser(schoolId, userId) > 0) {
			return success("锁定用户账号成功!");
		} else {
			return failure("锁定用户账号失败!");
		}

	}

	@RequestMapping(value = "/admin/user/unlock.html", method = RequestMethod.POST)
	public ModelAndView unlockUser(HttpServletRequest request) {
		long schoolId = SchoolHelper.getSchool(getDomain(request));
		long userId = Long.parseLong(request.getParameter("id"));
		if (schoolService.unlockUser(schoolId, userId) > 0) {
			return success("解锁用户账号成功!");
		} else {
			return failure("解锁用户账号失败!");
		}

	}

	/**
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/admin/user/setrole.html", method = RequestMethod.POST)
	public ModelAndView setrole(HttpServletRequest request, long userid,
			long classid, byte role) {
		if (schoolService.setRole(classid, userid, role) > 0) {
			return success("设置成功!");
		} else {
			return failure("设置失败!");
		}
	}

	@RequestMapping(value = "/admin/user/delete.html", method = RequestMethod.POST)
	public ModelAndView deleteUser(HttpServletRequest request) {
		long classId = Long.parseLong(request.getParameter("classid"));
		long userId = Long.parseLong(request.getParameter("id"));
		if (schoolService.deleteUser(classId, userId) > 0) {
			return success("删除用户账号成功!");
		} else {
			return failure("删除用户账号失败!");
		}
	}

	/* ====================== 学校部分班级管理===================================== */
	private ModelAndView permissionView(boolean isJson) {
		if (isJson) {
			return failure("您没有权限访问该班级或者账号被锁定，如有疑问请与管理员联系！");
		}
		return failureView("您没有权限访问该班级或者账号被锁定，如有疑问请与管理员联系！");
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/class/index.html")
	public ModelAndView studentIndex(HttpServletRequest request)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView("/class/item");
		List<Group> groups = groupService.my(this.getUserId(request),
				GroupConst.CLASS);
		if (groups.size() > 0) {
			mav.addAllObjects(groupService.get(groups.get(0).getGroupId()));
		} else {
			mav.setView(this.redirectView("/index.html"));
		}

		return mav;
	}

	/**
	 * 班级首页
	 * 
	 * @param groupId
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/class/{groupId}.html")
	public ModelAndView clazz(@PathVariable("groupId") int groupId,
			HttpServletRequest request) throws ApplicationException {
		/* 分组的权限 */
		if (groupService.authenticate(groupId, this.getUser(request))) {
			ModelAndView mav = new ModelAndView("/class/item");
			mav.addAllObjects(groupService.get(groupId));
			return mav;
		} else {
			return permissionView(false);
		}
	}

	/* =================班级后台管理============================= */

	/**
	 * 班级管理首页，默认只显示在校的正常班级
	 * 
	 * @param request
	 */
	@RequestMapping("/admin/class/index.html")
	public ModelAndView clazz(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		long schoolId = this.getSchoolId(request);
		List<Integer[]> years = groupService.findGroupYears(schoolId,
				GroupConst.CLASS);
		long page = Q.toLong(request.getParameter("p"), 1);
		int year = Q.toInteger(request.getParameter("year"), years.get(0)[0]);
		Page<Group> groups = groupService.findGroup(getUserId(request),
				GroupConst.CLASS, schoolId, year, page, 10);
		mav.addObject("years", years);
		mav.addObject("year", year);
		mav.addObject("groups", groups);
		return mav;
	}

	@RequestMapping("/admin/class/list.html")
	public ModelAndView classList(byte type, HttpServletRequest request) {
		long schoolId = 0;
		long page = Q.toLong(request.getParameter("p"), 1);
		int year = Q.toInteger(request.getParameter("year"));
		String is = request.getParameter("is");
		if (!Q.isBlank(is) || type == 0) {// 如果是学校
			schoolId = this.getSchoolId(request);
		}
		Page<Group> list = groupService.findGroup(getUserId(request), type,
				schoolId, year, page, 10);
		return render(RESULT, list);
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/class/form.html")
	public ModelAndView from(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String id = request.getParameter("id");
		if (!Q.isBlank(id)) {
			mav.addObject(RESULT, groupService.getGroup(Q.String(id).toLong()));
		}
		return mav;
	}

	/**
	 * 删除班级
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/class/delete.html", method = RequestMethod.POST)
	public ModelAndView deleteClass(HttpServletRequest request, long id) {
		if (groupService.deleteGroup(id, true) > 0) {
			return success("删除班级成功!");
		} else {
			return failure("删除班级失败!");
		}
	}

	/**
	 * 更新班级或者圈子
	 * 
	 * @param request
	 * @param group
	 * @return
	 */
	@RequestMapping(value = "/admin/class/save.html", method = RequestMethod.POST)
	public ModelAndView saveClass(HttpServletRequest request, Group group) {
		String is = request.getParameter("is");
		if (!Q.isBlank(is)) {// 如果是学校
			group.setSchoolId(getSchoolId(request));
		}
		if (group.getGroupId() != 0) {// 更新
			if (groupService.updateGroup(group) > 0) {
				return success("更新班级信息成功!");
			} else {
				return failure("更新班级信息失败!");
			}
		} else {
			group.setCreator(getUserId(request));// 创建者
			if (groupService.saveGroup(group) > 0) {
				return success("保存班级信息成功!");
			} else {
				return failure("保存班级信息失败!");
			}
		}
	}

	@RequestMapping(value = "/school/timetable.html")
	public ModelAndView timetable(HttpServletRequest request) {
		return render(schoolService.timetable(this.getSchoolId(request)));
	}

	@RequestMapping(value = "/school/timetable.save.html", method = RequestMethod.POST)
	public ModelAndView saveTimetable(HttpServletRequest request)
			throws UnsupportedEncodingException {
		if (schoolService.saveTimetable(this.getSchoolId(request),
				"true".equals(request.getParameter("isnew")),
				Q.decodeURL(request.getParameter("timetable"))) > 0) {
			return success("保存成功！");
		}
		return failure("保存失败！");
	}

}
