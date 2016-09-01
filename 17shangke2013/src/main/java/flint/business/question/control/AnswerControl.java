package flint.business.question.control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import entity.AnswerView;
import flint.business.question.service.AnswerService;
import flint.util.NumberHelper;
import flint.web.WebControl;

@Controller
public class AnswerControl extends WebControl {

	@Autowired
	private AnswerService answerService;
	
	/**
	 * 获取相关的问题解答的评论
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/answer/loadAnswerComment.html")
	public ModelAndView loadAnswerComment(long id, long answer, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/question/answer");
		long page = NumberHelper.toLong(request.getParameter("page"), 1);
		mav.addObject(RESULT, answerService.listAnswerComment(id, page, NumberHelper.toLong(configHelper.getString("sns", "mblog.comment.page.size", "10"))));
		AnswerView answerView = answerService.selectScore(id, getUserId(request));
		if(answerView!=null&&answerView.getComment()!=null){
			mav.addObject("flay",2);//已经评论过
		}else{
			mav.addObject("flay",1);
		}
		if(answer==getUserId(request)){
			mav.addObject("flay",2);//是自己的评论
		}
		return mav;
	}
	
	/**
	 * 插入对问题答案的评论
	 * 
	 * @param aid
	 * @param score
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/answer/answerComment.html", method = RequestMethod.POST)
	public ModelAndView answerComment(long aid, byte score, HttpServletRequest request){
		int result = 0;
		AnswerView answerView = answerService.selectScore(aid, getUserId(request));
	    if(answerView!=null&&answerView.getComment()==null){
	    	result = answerService.updateScore(aid, getUserId(request), request.getParameter("text"), score, request.getRemoteAddr());
	    }else{
	    	result = answerService.insertScore(aid, getUserId(request), request.getParameter("text"), score, request.getRemoteAddr());
	    }
	    if (result > 0) {
			return success("评论成功！");
		} else {
			return failure("评论失败！");
		}
	}
}
