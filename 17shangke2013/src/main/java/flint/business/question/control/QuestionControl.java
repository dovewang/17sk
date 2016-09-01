package flint.business.question.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kiss.security.Identity;
import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import entity.Question;
import entity.QuestionAnswer;
import flint.business.constant.AccountConst;
import flint.business.constant.QuestionConst;
import flint.business.constant.UserConst;
import flint.business.question.service.QuestionService;
import flint.business.question.util.QuestionSearcher;
import flint.business.util.SchoolHelper;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.util.NumberHelper;
import flint.web.WebControl;
import flint.web.util.CookieHelper;

@Controller
public class QuestionControl extends WebControl {

	@Autowired
	private QuestionService questionService;

	/**
	 * 提问页面
	 * 
	 * @return
	 */
	@RequestMapping("/question/ask.html")
	public void ask() {

	}

	/**
	 * 问题查看详细页面
	 * 
	 * @param id
	 *            问题id
	 * @return ModelAndView @
	 * @throws ApplicationException
	 */
	@RequestMapping("/qitem-{questionId}.html")
	public ModelAndView view(@PathVariable("questionId") long questionId,
			HttpServletRequest request) throws ApplicationException {
		ModelAndView view = new ModelAndView("/question/item");
		Identity user = getUser(request);
		Question question = questionService.findById(questionId,
				Long.parseLong(user.getId()));
		long schoolId = this.getSchoolId(request);
		/* 删除的问题允许管理员访问 */
		if (question == null
				|| (question.getStatus() == QuestionConst.QUESTION_STATUS_DELETE)
				&& (user.is(UserConst.ADMIN))
				|| question.getSchoolId() != schoolId) {
			return new ModelAndView("/question/item.notfound");
		}
		view.addObject("question", question);
		view.addObject("watchers",
				questionService.getWatchers(questionId, 0, false));
		view.addObject("relates", questionService.related(question));
		view.addObject(
				"answer",
				questionService.findAnswers(questionId, getUserId(request),
						Q.toInteger(request.getParameter("order"))));
		questionService.addview(questionId);
		return view;

	}

	/**
	 * 快速解答
	 * 
	 * @param questionId
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/question/fastAnswer.html", method = RequestMethod.POST)
	public ModelAndView fastAnswer(long questionId, String title,
			String content, HttpServletRequest request)
			throws ApplicationException {
		QuestionAnswer answer = new QuestionAnswer();
		answer.setAnswer(getUserId(request));
		answer.setContent(content);
		answer.setPrice(Q.toInteger(request.getParameter("price")));
		answer.setQuestionId(questionId);
		answer.setType(QuestionConst.ANSWER_TEXT);
		answer.setMemo(title);
		if (questionService.answer(answer, this.getUser(request)) > 0) {
			return success("解答问题成功！");
		} else {
			return failure("解答问题失败！");
		}
	}

	/**
	 * @param qid
	 *            问题ID
	 * @param title
	 *            问题标题
	 * @param aid
	 *            答案ID
	 * @param type
	 *            问题类型 0：免费 、1：收费
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/question/selectAnswer.html", method = RequestMethod.POST)
	public ModelAndView selectAnswer(long qid, String title, long aid,
			byte type, HttpServletRequest request) throws ApplicationException {
		if (questionService.selectAnswer(
				SchoolHelper.getSchool(getDomain(request)), qid, title, aid,
				getUserId(request), type, request.getRemoteAddr(),
				this.getUser(request)) > 0) {
			return success("选择答案成功！");
		} else {
			return failure("选择答案失败！");
		}
	}

	/**
	 * 查看获取答案内容
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/question/answer.html")
	public ModelAndView answer(long answerId, String title,
			HttpServletRequest request) throws ApplicationException {
		ModelAndView mav = new ModelAndView("/question/answer.item");
		/* 获取授权码 */
		Identity user = this.getUser(request);
		String key = request.getParameter("key");
		String vkey = user.getAttribute(AccountConst.AUTHORIZE_TOKEN);
		if (vkey == null || !vkey.equals(key)) {
			return mav.addObject("content", "授权码不存在，验证失败，请您重新授权！");
		}
		return mav.addObject("answer", questionService.viewAnswer(answerId,
				getUserId(request), title, request.getRemoteAddr(), 0));
	}

	/**
	 * 发布问题
	 * 
	 * @param question
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/question/post.html", method = RequestMethod.POST)
	public ModelAndView post(Question question, HttpServletRequest request)
			throws ApplicationException {
		question.setAsker(getUserId(request));
		question.setSchoolId(this.getSchoolId(request));// 设定问题发布的学校
		if (questionService.post(question, request.getParameter("helpers")) > 0) {
			return success("发布问题成功！");
		} else {
			return failure("发布问题失败！");
		}
	}

	/**
	 * 获取本用户最新发布的问题
	 * 
	 * @param question
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/question/getNewstQuestion.html")
	public ModelAndView newest(HttpServletRequest request)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView("/question/insert.result");
		mav.addObject("question", questionService.newest(getSchoolId(request),
				getUserId(request)));
		return mav;
	}

	/**
	 * 编辑问题
	 * 
	 * @param questionId
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/question/edit.html")
	public ModelAndView edit(long questionId) throws ApplicationException {
		ModelAndView view = new ModelAndView();
		view.addObject("question", questionService.findById(questionId));
		return view;
	}

	/**
	 * 保存修改
	 * 
	 * @param question
	 *            问题
	 * @return String 操作结果 @
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/question/update.html", method = RequestMethod.POST)
	public ModelAndView update(Question question, HttpServletRequest request)
			throws ApplicationException {
		question.setAsker(getUserId(request));
		question.setSchoolId(getSchoolId(request));
		if (questionService.update(question) > 0) {
			return success("更新问题成功！");
		} else {
			return failure("更新问题失败！");
		}
	}

	/**
	 * 关闭问题
	 * 
	 * @param id
	 *            问题id
	 * @param HttpServletRequest
	 * @return String 操作结果 @
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/question/close.html", method = RequestMethod.POST)
	public ModelAndView close(int questionId, HttpServletRequest request)
			throws ApplicationException {
		if (questionService.close(questionId, getUserId(request)) > 0) {
			return success("关闭问题成功！");
		} else {
			return failure("关闭问题失败！");
		}
	}

	/**
	 * 删除问题
	 * 
	 * @param questionId
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/question/del.html", method = RequestMethod.POST)
	public ModelAndView delete(int questionId, HttpServletRequest request)
			throws ApplicationException {
		if (questionService.delete(questionId, getUserId(request)) > 0) {
			return success("删除问题成功！");
		} else {
			return failure("删除问题失败！");
		}
	}

	/* ==================问题检索模块====================== */

	public ModelAndView search(String viewName, HttpServletRequest request,
			int type, long userId, String keyword) throws ApplicationException {
		long size = Q.toLong(Q.isEmpty(request.getParameter("s"),
				configHelper.getString("question", "mypagesize", "20")));
		int page = Q.toInteger(request.getParameter("p"), 1);
		ModelAndView view = new ModelAndView(viewName);
		QuestionSearcher qs = new QuestionSearcher(this.getSchoolId(request),
				request.getParameter("c"), keyword, type, userId);
		Page<Question> list = questionService.search(page, size, qs);
		list.setRequest(request);
		view.addObject(RESULT, list);
		view.addObject("TYPE", type);
		view.addObject("condition", qs.getReponseParams());
		return view;
	}

	@RequestMapping("/search/question.html")
	public ModelAndView searchQuestion(HttpServletRequest request)
			throws ApplicationException {
		ModelAndView view = search("/search/question", request,
				QuestionSearcher.TYPE_ALL, 0, request.getParameter("q"));
		return view;
	}

	@RequestMapping("/question.html")
	public ModelAndView search(HttpServletRequest request)
			throws ApplicationException {
		String is = request.getParameter("is");
		String keyword = request.getParameter("q");
		long userId = getUserId(request);
		int type = NumberHelper.toInt(is, QuestionSearcher.TYPE_ALL);
		if (type > QuestionSearcher.TYPE_FAV
				|| type < QuestionSearcher.TYPE_ALL) {
			type = QuestionSearcher.TYPE_ALL;
			userId = getUserId(request);
		}
		return search("/question", request, type, userId, keyword);// 用户ID指定为0，不检索用户
	}

	/**
	 * 所有问题列表
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/question-all.html")
	public ModelAndView all(HttpServletRequest request)
			throws ApplicationException {
		return search("/question/result.list", request,
				QuestionSearcher.TYPE_ALL, 0, null);// 用户ID指定为0，不检索用户
	}

	/**
	 * 使用ajax方式在所有问题中检索，和问题检索首页不同 add by Dove 2012-3-30
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/question/list.html")
	public ModelAndView list(HttpServletRequest request)
			throws ApplicationException {
		return search("/question/list.ajax", request,
				QuestionSearcher.TYPE_ALL, 0, null);// 用户ID指定为0，不检索用户
	}

	/**
	 * 我解答过的问题
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/question-mya.html")
	public ModelAndView mya(HttpServletRequest request)
			throws ApplicationException {
		long u = NumberHelper.toLong(request.getParameter("u"),
				getUserId(request));
		return search("/question/result.list", request,
				QuestionSearcher.TYPE_MY_ANSWER, u, null);
	}

	/**
	 * 我提出的问题
	 * 
	 * @param page
	 *            当前页
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/question-myq.html")
	public ModelAndView myq(HttpServletRequest request)
			throws ApplicationException {
		long u = NumberHelper.toLong(request.getParameter("u"),
				getUserId(request));
		return search("/question/result.list", request,
				QuestionSearcher.TYPE_MY_QUESTION, u, null);
	}

	/**
	 * 我收藏的问题，只有本人能看的
	 * 
	 * @param page
	 *            当前页
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/question/fav.html")
	public ModelAndView fav(HttpServletRequest request)
			throws ApplicationException {
		return search("/question/result.list", request,
				QuestionSearcher.TYPE_FAV, getUserId(request), null);
	}

	/**
	 * 相关问题
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/question-related.html")
	public ModelAndView related(HttpServletRequest request)
			throws ApplicationException {
		return search("/question/question.related", request,
				QuestionSearcher.TYPE_RELATED, getUserId(request),
				request.getParameter("q"));
	}

	/**
	 * 自动检索相关问题答案
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/question.autosearch.html")
	public ModelAndView autoSearch(HttpServletRequest request)
			throws ApplicationException {
		return search("/question/question.autosearch", request,
				QuestionSearcher.TYPE_RELATED, getUserId(request),
				request.getParameter("q"));
	}

	/**
	 * 用户删除问题答案
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/question/answer.delete.html", method = RequestMethod.POST)
	public ModelAndView deleteAnswer(long answer, long qid, long id,
			HttpServletRequest request) {
		if (questionService.deleteAnswer(answer, qid, id) > 0) {
			return success("删除成功！");
		} else {
			return failure("删除失败！");
		}
	}

	/**
	 * 编辑答案
	 * 
	 * @param answerId
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/question/editAnswer.html")
	public ModelAndView editAnswerView(long answerId)
			throws ApplicationException {
		ModelAndView view = new ModelAndView("/question/answer_edit");
		view.addObject("answer", questionService.findAnswerById(answerId));
		return view;
	}

	/**
	 * 保存修改
	 * 
	 * @param question
	 *            问题
	 * @return String 操作结果 @
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/question/updateAnswer.html", method = RequestMethod.POST)
	public ModelAndView updateAnswer(long aid, String content,
			HttpServletRequest request) throws ApplicationException {
		if (questionService.updateAnswer(aid, content) > 0) {
			return success("更新答案成功！");
		} else {
			return failure("更新答案失败！");
		}
	}

	/**
	 * 获取At到我的问题
	 * 
	 * @param page
	 *            当前页
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/question/at.html")
	public ModelAndView at(HttpServletRequest request,
			HttpServletResponse response) throws ApplicationException {
		long size = Q.toLong(Q.isEmpty(request.getParameter("s"),
				configHelper.getString("question", "mypagesize", "20")));
		int page = Q.toInteger(request.getParameter("p"), 1);
		ModelAndView view = new ModelAndView("/question/result.list");
		Page<Question> list = questionService.searchAtQuestion(
				getUserId(request), page, size);
		list.setRequest(request);
		view.addObject("TYPE", 0);
		view.addObject(RESULT, list);
		/* 清空缓存的At记录的Cookie信息 */
		CookieHelper.removeCookie(request, response, "message.count");
		return view;
	}

	/**
	 * 邀请我解答的问题
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("question/invited.html")
	public ModelAndView invited(HttpServletRequest request,
			HttpServletResponse response) throws ApplicationException {
		long size = Q.toLong(Q.isEmpty(request.getParameter("s"),
				configHelper.getString("question", "mypagesize", "20")));
		int page = Q.toInteger(request.getParameter("p"), 1);
		ModelAndView view = new ModelAndView("/question/invited");
		Page<Question> list = questionService.searchAtQuestion(
				getUserId(request), page, size);
		list.setRequest(request);
		view.addObject("TYPE", 0);
		view.addObject(RESULT, list);
		/* 清空缓存的At记录的Cookie信息 */
		CookieHelper.removeCookie(request, response, "message.count");
		return view;
	}

	/**
	 * 用户关注的问题
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/question/watching.html")
	public ModelAndView watching(HttpServletRequest request)
			throws ApplicationException {
		return null;
	}

	/**
	 * 关注問題
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/question/watch.html", method = RequestMethod.POST)
	public ModelAndView watch(HttpServletRequest request)
			throws ApplicationException {
		return null;
	}

	/**
	 * 取消关注問題
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/question/unwatch.html", method = RequestMethod.POST)
	public ModelAndView unwatch(HttpServletRequest request)
			throws ApplicationException {
		return null;
	}
}
