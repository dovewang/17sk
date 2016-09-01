package flint.business.exercise.control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import entity.Exercise;
import entity.QuestionBank;
import flint.business.exercise.service.ExerciseService;
import flint.business.util.SchoolHelper;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.util.DateHelper;
import flint.util.NumberHelper;
import flint.util.StringHelper;
import flint.web.WebControl;

@Controller
public class ExerciseControl extends WebControl {

	@Autowired
	private ExerciseService exerciseService;

	/*
	 * ==========================================
	 * 
	 * 练习生成模块
	 * 
	 * ==========================================
	 */

	/**
	 * 练习题模板页面：单选，多选，判断，填空等
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/exercise/template.html", method = RequestMethod.GET)
	public ModelAndView getTemplate(HttpServletRequest request) {
		String qid = StringHelper.defaultIfEmpty(request.getParameter("id"), "");
		if (StringHelper.isEmpty(qid)) {
			String type = StringHelper.defaultIfEmpty(request.getParameter("type"), "1");
			/* 如果包含有练习ID，直接读取数据填充模板 */
			return new ModelAndView("global:/app/exercise/template/" + type);
		} else {
			long questionId = NumberHelper.toLong(qid);
			QuestionBank question = exerciseService.getQuestion(questionId);
			ModelAndView mav = new ModelAndView("global:/app/exercise/template/" + question.getType());
			mav.addObject(RESULT, question);
			return mav;
		}
	}

	/**
	 * 保存练习
	 * 
	 * @param question
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/q/save.html", method = RequestMethod.POST)
	public ModelAndView saveQuestion(QuestionBank question, HttpServletRequest request) throws ApplicationException {
		String exerciseId = request.getParameter("exerciseId");
		question.setSchoolId(SchoolHelper.getSchool(getDomain(request)));
		question.setStatus((byte) 1);
		question.setUserId(getUserId(request));
		question.setCreateTime(DateHelper.getNowTime());
		/* 带有关联的练习添加 */
		long questionId = exerciseService.saveQuestion(question);
		if (!StringHelper.isEmpty(exerciseId) && !"0".equals(exerciseId)) {
			exerciseService.add(NumberHelper.toLong(exerciseId), questionId, 1, 5);
		}
		if (questionId > 0) {
			return success("保存习题成功!");
		} else {
			return failure("保存习题失败!");
		}
	}

	/**
	 * 更新试题
	 * 
	 * @return
	 */
	@RequestMapping(value = "/q/update.html", method = RequestMethod.POST)
	public ModelAndView editQuestion(QuestionBank question, HttpServletRequest request) {
		if (exerciseService.updateQuestion(question) > 0) {
			return success("更新习题成功!");
		} else {
			return failure("更新习题失败!");
		}
	}

	/**
	 * 删除问题库的一道试题
	 * 
	 * @param questionId
	 * @return
	 */
	@RequestMapping(value = "/q/delete.html", method = RequestMethod.POST)
	public ModelAndView deleteQuestion(long questionId) {
		if (exerciseService.deleteQuestion(questionId) > 0) {
			return success("删除习题成功!");
		} else {
			return failure("删除习题失败!");
		}
	}

	/**
	 * 练习题库
	 * 
	 * <pre>
	 *    分为我的题库、校内题库和公共题库
	 * </pre>
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/q/bank.html", method = RequestMethod.GET)
	public ModelAndView questionBank(HttpServletRequest request) throws ApplicationException {
		long schoolId = SchoolHelper.getSchool(getDomain(request));
		int type = NumberHelper.toInt(request.getParameter("t"));
		String condition = StringHelper.defaultIfEmpty(request.getParameter("c"), "0,0,0,0,0,0,0");
		ModelAndView mav = new ModelAndView("global:/app/q/bank");
		long page = NumberHelper.toLong(request.getParameter("p"), 1);
		mav.addObject(RESULT, exerciseService.searchQ(page, 15, schoolId, getUserId(request), type, condition));
		mav.addObject("TYPE", type);
		mav.addObject("condition", condition.split(","));
		return mav;
	}

	@RequestMapping("/exercise/save.html")
	public ModelAndView save(Exercise exercise, HttpServletRequest request) throws ApplicationException {
		exercise.setUserId(getUserId(request));
		exercise.setSchoolId(SchoolHelper.getSchool(getDomain(request)));
		if (exerciseService.saveOrUpdate(exercise) > 0) {
			return success("保存练习成功!");
		} else {
			return failure("保存练习失败!");
		}
	}

	/**
	 * 向练习中添加一道习题，这里只直接从练习库查找的试题进行添加，添加问题与练习的对应关系
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/exercise/add.html")
	public ModelAndView add(long exerciseId, long questionId, int displayOrder, int score, HttpServletRequest request) throws ApplicationException {
		if (exerciseService.add(exerciseId, questionId, displayOrder, score) > 0) {
			return success("添加习题成功!");
		} else {
			return failure("添加习题失败!");
		}
	}

	/**
	 * 删除练习中的一道习题
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/exercise/remove.html")
	public ModelAndView remove(long exerciseId, long questionId, HttpServletRequest request) throws ApplicationException {
		if (exerciseService.remove(getUserId(request), exerciseId, questionId) > 0) {
			return success("删除习题成功!");
		} else {
			return failure("删除习题失败!");
		}
	}

	@RequestMapping("/exercise/order.html")
	public ModelAndView order(long exerciseId, long questionId, int order, HttpServletRequest request) throws ApplicationException {
		if (exerciseService.order(getUserId(request), exerciseId, questionId, order) > 0) {
			return success("");
		} else {
			return failure("排序失败!");
		}
	}

	/**
	 * 删除整个练习，需要删除练习表，练习与问题对应关系表，但是不删除练习
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/exercise/delete.html")
	public ModelAndView delete(long exerciseId, HttpServletRequest request) throws ApplicationException {
		ModelAndView mav = new ModelAndView();
		exerciseService.delete(getUserId(request), exerciseId);
		return mav;
	}

	/**
	 * 保存练习，并生成静态试题页
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/exercise/done.html", method = RequestMethod.POST)
	public ModelAndView done(long exerciseId, HttpServletRequest request) throws ApplicationException {
		ModelAndView mav = new ModelAndView();
		exerciseService.done(getUserId(request), exerciseId);
		return mav;
	}

	/*
	 * ==========================================
	 * 
	 * 练习测试做题模块
	 * 
	 * ==========================================
	 */

	/**
	 * 随机试题
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/exercise/random.html")
	public ModelAndView random(HttpServletRequest request) throws ApplicationException {
		ModelAndView mav = new ModelAndView();
		exerciseService.random(getUserId(request));
		return mav;
	}

	/**
	 * 获取指定试题
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/exercise/get.html")
	public ModelAndView get(long exerciseId, HttpServletRequest request) throws ApplicationException {
		ModelAndView mav = new ModelAndView("global:/app/exercise/list");
		mav.addObject(RESULT, exerciseService.get(exerciseId));
		return mav;
	}

	/**
	 * 课后练习及课堂练习
	 * 
	 * @param sid
	 * @param st
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/exercise/subjectExercise.html")
	public ModelAndView getSubjectExercise(long sid, byte st, HttpServletRequest request) throws ApplicationException {
		ModelAndView mav = new ModelAndView("global:/app/exercise/subjectExercise");
		mav.addObject(RESULT, exerciseService.getSubjectExercise(SchoolHelper.getSchool(getDomain(request)), getUserId(request), sid, st));
		return mav;
	}

	/**
	 * 用户收到练习确认
	 * 
	 * @param exerciseId
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/exercise/receive.html")
	public ModelAndView receive(long exerciseId, HttpServletRequest request) throws ApplicationException {
		long roomId = NumberHelper.toLong(request.getParameter("roomid"));
		exerciseService.receive(getUserId(request), roomId, exerciseId);
		return success("交卷成功，请等待老师批阅");
	}

	/**
	 * 用户完成练习，提交答案，当不需要手工阅卷的时候，自动阅卷
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/exercise/submit.html")
	public ModelAndView submit(long exerciseId, HttpServletRequest request) throws ApplicationException {
		long roomId = NumberHelper.toLong(request.getParameter("roomid"));
		exerciseService.submit(getUserId(request), roomId, exerciseId, request.getParameterMap());
		return success("交卷成功，请等待老师批阅");
	}

	/**
	 * 阅卷，部分由机器批阅，部分手工阅卷，并自动将错题记录到用户的错题库
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/exercise/mark.html")
	public ModelAndView mark(long exerciseId, long id, HttpServletRequest request) throws ApplicationException {
		exerciseService.mark(getUserId(request), exerciseId, id);
		return success("阅卷成功");
	}

	/**
	 * 用户练习成绩查询，包括信息：总耗时，错题数，对题数，正确率，得分，名次，知识点弱项
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/exercise/result.html")
	public ModelAndView result(long exerciseId, HttpServletRequest request) throws ApplicationException {
		long roomId = NumberHelper.toLong(request.getParameter("roomid"));
		ModelAndView mav = new ModelAndView("global:/app/exercise/result");
		mav.addObject(RESULT, exerciseService.result(roomId, exerciseId));
		return mav;
	}

	/**
	 * 练习管理界面
	 * 
	 * @param request
	 * @throws ApplicationException
	 */
	@RequestMapping("/exercise/manager.html")
	public String manager(HttpServletRequest request) throws ApplicationException {
           return "global:/app/exercise/manager";
	}

	/**
	 * 练习列表
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/exercise/elist.html")
	public ModelAndView elist(HttpServletRequest request) throws ApplicationException {
		ModelAndView mav = new ModelAndView("global:/app/exercise/elist");
		int type = NumberHelper.toInt(request.getParameter("t"));
		long page = NumberHelper.toLong(request.getParameter("p"));
		String keyword = StringHelper.defaultIfEmpty(request.getParameter("q"), "");
		String order = StringHelper.defaultIfEmpty(request.getParameter("o"), "");
		Page<Exercise> list = exerciseService.list(getUserId(request), type, keyword, page, 10, order);
		list.setUrl(request.getRequestURI());
		list.setParam(StringHelper.queryStringReplace(request.getQueryString(), "p", "@"));
		mav.addObject(RESULT, list);
		return mav;
	}

	/**
	 * 教室练习题
	 * 
	 * @param exerciseId
	 * @param userId
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/exercise/paper.html")
	public ModelAndView paper(long exerciseId, HttpServletRequest request) throws ApplicationException {
		ModelAndView mav = new ModelAndView("global:/app/exercise/paper");
		mav.addObject(RESULT, exerciseService.get(exerciseId));
		return mav;
	}

}
