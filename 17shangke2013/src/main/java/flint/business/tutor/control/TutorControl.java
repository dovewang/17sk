package flint.business.tutor.control;

import javax.servlet.http.HttpServletRequest;

import kiss.security.Identity;
import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import entity.Tutor;
import entity.TutorDemand;
import entity.User;
import flint.business.constant.TutorConst;
import flint.business.constant.UserConst;
import flint.business.tutor.service.TutorService;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.util.NumberHelper;
import flint.util.StringHelper;
import flint.web.WebControl;
import flint.web.util.CookieHelper;

/**
 * 
 * @author Dove Wang
 * 
 */
@Controller
public class TutorControl extends WebControl {

	@Autowired
	private TutorService tutorService;

	/**
	 * 辅导首页
	 * 
	 * @param request
	 */
	@RequestMapping("/tutor/index.html")
	public ModelAndView index(HttpServletRequest request) {
		long size = NumberHelper.toLong(configHelper.getString("tutor", "pagesize", "15"));
		int page = NumberHelper.toInt(request.getParameter("p"), 1);
		String tab = StringHelper.defaultIfEmpty(request.getParameter("tab"), "all");
		String condition = StringHelper.defaultIfEmpty(request.getParameter("c"), "0,0,0,0,0,0,0,0");
		byte type = 0;
		if ("school".equals(tab)) {
			type = TutorConst.SCHOOL;
		} else if ("teacher".equals(tab)) {
			type = TutorConst.TEACHER;
		}
		String keyword = request.getParameter("q");
		ModelAndView view = new ModelAndView();
		Page<Tutor> list = tutorService.search(type, condition, keyword, page, size);
		list.setUrl(request.getRequestURI());
		list.setParam(StringHelper.queryStringReplace(request.getQueryString(), "p", "@"));
		view.addObject(RESULT, list);
		view.addObject("condition", condition.split(","));
		return view;
	}
	
	/**
	 * 个人发布辅导需求信息
	 * 
	 * @param request
	 */
	private ModelAndView tutordemand(HttpServletRequest request) {
		long size = NumberHelper.toLong(configHelper.getString("tutor", "pagesize", "15"));
		int page = NumberHelper.toInt(request.getParameter("p"), 1);
		ModelAndView view = new ModelAndView();
		Page<TutorDemand> list = tutorService.searchDemand(getUserId(request), page, size);
		list.setUrl(request.getRequestURI());
		list.setParam(StringHelper.queryStringReplace(request.getQueryString(), "p", "@"));
		view.addObject(RESULT, list);
		view.setViewName("tutor/tutordemand");
		return view;
	}
	
	/**
	 * 个人发布辅导服务信息
	 * 
	 * @param request
	 */
	private ModelAndView searchTutor(HttpServletRequest request) {
		long size = NumberHelper.toLong(configHelper.getString("tutor", "pagesize", "15"));
		int page = NumberHelper.toInt(request.getParameter("p"), 1);
		ModelAndView view = new ModelAndView();
		Page<Tutor> list = tutorService.searchTutor(getUserId(request), page, size);
		list.setUrl(request.getRequestURI());
		list.setParam(StringHelper.queryStringReplace(request.getQueryString(), "p", "@"));
		view.addObject(RESULT, list);
		view.setViewName("tutor/searchTutor");
		return view;
	}
	
	/**
	 * 个人发布辅导信息
	 * 
	 * @param request
	 */
	@RequestMapping("/tutor/post.html")
	public ModelAndView tutor(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("passport/login");
		 Identity user = getUser(request);
		if(user.is(UserConst.STUDENT)||user.is(UserConst.CURATOR)){//学生或者家长
			view = tutordemand(request);
		}else if(user.is(UserConst.TEACHER )|| user.is(UserConst.ENTERPRISE)){
			view = searchTutor(request);
		}
		return view;
	}
	
	@RequestMapping("/tutor/demand.form.html")
	public void demandForm(String demandId, String kind, String price, String intro, HttpServletRequest request){
	}
	
	/**
	 * 个人发布辅导服务编辑
	 * 
	 * @param request
	 */
	@RequestMapping("/tutor/service.form.html")
	public void serviceForm(String tutorId, String kind, String price, String intro, HttpServletRequest request) {
	}
	
	/**
	 * 删除个人辅导服务信息
	 * 
	 * @param tutorId
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/tutor/delTutor.html", method = RequestMethod.POST)
	public ModelAndView deleteTutor(long id, HttpServletRequest request)
			throws ApplicationException {
		if (tutorService.deleteTutor(id) > 0) {
			return success("删除成功！");
		} else {
			return failure("删除失败！");
		}
	}
	
	/**
	 * 删除个人辅导需求信息
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/tutor/delTutorDemand.html", method = RequestMethod.POST)
	public ModelAndView deleteTutorDemand(long id, HttpServletRequest request)
			throws ApplicationException {
		if (tutorService.deleteTutorDemand(id) > 0) {
			return success("删除成功！");
		} else {
			return failure("删除失败！");
		}
	}


	/**
	 * 发布求辅导的信息
	 * 
	 * @param request
	 */
	@RequestMapping("/tutor/demand.post.html")
	public ModelAndView postDemand(TutorDemand demand, String kind, HttpServletRequest request) {
		demand.setUserId(getUserId(request));
		demand.setStatus(TutorConst.TUTOR_AVAILABLE);
		if(!StringHelper.isEmpty(kind)){
			String k[] = kind.split(",");
			for (int i = 1; i < k.length + 1; i++) {
				if(i==1)demand.setKind1(NumberHelper.toLong(k[i-1]));
				if(i==2)demand.setKind2(NumberHelper.toLong(k[i-1]));
				if(i==3)demand.setKind3(NumberHelper.toLong(k[i-1]));
			}
		}
		int result = 0;
		if(demand.getDemandId() > 0){//更新
			result = tutorService.updateTutorDemand(demand.getDemandId(), demand.getKind1(), demand.getKind2(), demand.getKind3(), demand.getPrice(), demand.getIntro(), getUserId(request));
		}else{//新增
			result = tutorService.postDemand(demand);
		}
		if (result > 0) {
			return success("发布成功！");
		} else {
			return failure("网络繁忙，请稍后再试！");
		}
	}


	/**
	 * 提供辅导信息
	 * 
	 * @param request
	 */
	@RequestMapping("/tutor/service.post.html")
	public ModelAndView postService(Tutor tutor, String kind, HttpServletRequest request) {
		Identity user = this.getUser(request);
		tutor.setTutorType(user.getType());
		tutor.setProvider(Long.parseLong(user.getId()));
		tutor.setStatus(TutorConst.TUTOR_AVAILABLE);
		if(!Q.isEmpty(kind)){
			String k[] = kind.split(",");
			for (int i = 1; i < k.length + 1; i++) {
				if(i==1)tutor.setKind1(NumberHelper.toLong(k[i-1]));
				if(i==2)tutor.setKind2(NumberHelper.toLong(k[i-1]));
				if(i==3)tutor.setKind3(NumberHelper.toLong(k[i-1]));
			}
		}
		int result = 0;
		if(tutor.getTutorId() > 0){//更新
			result = tutorService.updateTutor(tutor.getTutorId(), tutor.getKind1(), tutor.getKind2(), tutor.getKind3(), tutor.getPrice(), tutor.getIntro(), getUserId(request));
		}else{//新增
			result = tutorService.postService(tutor);
		}
		if (result > 0) {
			return success("发布成功！");
		} else {
			return failure("网络繁忙，请稍后再试！");
		}
	}

	/**
	 * 辅导管理
	 * 
	 * @param request
	 */
	@RequestMapping("/tutor/manager.html")
	public void manager(HttpServletRequest request) {

	}

	/**
	 * 候选人列表
	 * 
	 * @param request
	 */
	@RequestMapping("/tutor/candidate.html")
	public void candidate(HttpServletRequest request) {

	}

	/**
	 * 加入候选人列表
	 * 
	 * @param request
	 */
	@RequestMapping("/tutor/candidate.add.html")
	public ModelAndView addCandidate(long candidate, HttpServletRequest request) {
		long id = tutorService.addCandidate(getUserId(request), candidate);
		if (id > 0) {
			return success("添加候选人成功！", "id", String.valueOf(id));
		} else if (id == 0) {
			return failure("对不起，您已经添加了该候选人！");
		} else {
			return failure("网络繁忙，添加候选人失败，请稍后再试！");
		}
	}

	/**
	 * 删除候选人
	 * 
	 * @param request
	 */
	@RequestMapping("/tutor/candidate.delete.html")
	public void deleteCandidate(HttpServletRequest request) {

	}

	/**
	 * 候选人比较
	 * 
	 * @param request
	 */
	@RequestMapping("/tutor/candidate.compare.html")
	public void compareCandidate(HttpServletRequest request) {

	}

	/**
	 * 确定选择某个候选人，确定选择候选人后就需要确认订单支付一定的费用
	 * 
	 * @param request
	 */
	@RequestMapping("/tutor/candidate.select.html")
	public ModelAndView selectCandidate(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/user/select.list");
		long size = NumberHelper.toLong(configHelper.getString("tutor", "pagesize", "15"));
		int page = NumberHelper.toInt(request.getParameter("p"), 1);
		Page<User> list = tutorService.findCandidate(getUserId(request), page, size);
		mav.addObject(RESULT, list);
		return mav;
	}

	/**
	 * 参加用户发布的某个求辅导信息
	 * 
	 * @param request
	 */
	@RequestMapping("/tutor/join.html")
	public void join(HttpServletRequest request) {

	}
	
	/**
	 * 教师首页求辅导信息
	 * 
	 * @param userId
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/tutor/guessDemand.html", method = RequestMethod.GET)
	public ModelAndView guessDemand(HttpServletRequest request)
			throws ApplicationException {
		String focus = StringHelper.defaultIfEmpty(
				CookieHelper.getCookieValue(request, "user.focus"), "");
		long userId = getUserId(request);
		long page = NumberHelper.toLong(request.getParameter("p"), 1);
		long size = NumberHelper.toLong(request.getParameter("n"), 1);
		return render(RESULT, tutorService.guessDemand(focus, userId, page, size));
	}
	
	/**
	 * 教师辅导首页求辅导详细信息
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/tutor/getGuessTutorDemand.html", method = RequestMethod.GET)
	public ModelAndView getGuessTutorDemand(String id, HttpServletRequest request)
			throws ApplicationException {
		ModelAndView view = new ModelAndView("/tutor/guess-demand");
		view.addObject("demand", tutorService.selectTutorDemand(NumberHelper.toLong(id)));
		return view;
	}

}
