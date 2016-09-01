package flint.business.question.service.impl;

import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.AnswerView;
import flint.base.BaseServiceImpl;
import flint.business.constant.QuestionConst;
import flint.business.question.dao.AnswerDAO;
import flint.business.question.dao.QuestionDAO;
import flint.business.question.service.AnswerService;
import flint.common.Page;

@Service
public class AnswerServiceImpl extends BaseServiceImpl implements AnswerService {

	@Autowired
	private AnswerDAO answerdao;
	@Autowired
	private QuestionDAO questionDao;

	@Override
	public Page<AnswerView> listAnswerComment(long id, long page, long size) {
		return answerdao.listAnswerComment(id, page, size);
	}

	@Override
	public int insertScore(long aid, long userId, String comment, byte answerScore, String netAddress){
		/*更新答案的评论人数、插入评论*/
		questionDao.updateMark(aid, answerScore);
		
		AnswerView answerView = new AnswerView();
		answerView.setUserId(userId);
		answerView.setAnswerId(aid);
		answerView.setComment(comment);
		answerView.setAnswerScore(answerScore);
		answerView.setNetAddress(netAddress);
		answerView.setDateline(Q.now());
		answerView.setStatus(QuestionConst.OK);
		answerView.setAdopt(false);
		return answerdao.save(answerView);
	}
	
	@Override
	public int updateScore(long aid, long userId, String comment, byte answerScore, String netAddress){
		/*更新答案的评论人数、插入评论*/
		questionDao.updateMark(aid, answerScore);
		return answerdao.updateScore(aid, userId, comment, answerScore);
	}

	@Override
	public AnswerView selectScore(long aid, long userId) {
		return answerdao.selectScore(aid, userId);
	}

}