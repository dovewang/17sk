package flint.business.question.service;

import entity.AnswerView;
import flint.base.BaseService;
import flint.common.Page;

/**
 * 
 * 功能描述：用户问题答案评论相关功能<br>
 * 
 * 日 期：2011-7-20 18:14:17<br>
 * 
 * 作 者：LJF<br>
 * 
 * 版权所有：©mosai<br>
 * 
 * 版 本 ：v0.01<br>
 * 
 * 联系方式：<a href="mailto:hs0910@163.com">hs0910@163.com</a><br>
 * 
 * 修改备注：{修改人|修改时间}<br>
 * 
 * 
 **/
public interface AnswerService extends BaseService {

	/**
	 * 查询问题答案的评论
	 * 
	 * @param aid 答案ID
	 * @return
	 */
	Page<AnswerView> listAnswerComment(long aid, long page, long size); 
	
	/**
	 * 查询用户对答案是否存在评论
	 * 
	 * @param aid 答案ID
	 * @return
	 */
	AnswerView selectScore(long aid, long userId); 
	
	/**
	 * 插入对问题答案的评论
	 * 
	 * @param aid 答案ID
	 * @return
	 */
	int insertScore(long aid, long userId,String comment, byte answerScore, String netAddress); 
	
	/**
	 * 插入对问题答案的评论
	 * 
	 * @param aid 答案ID
	 * @return
	 */
	int updateScore(long aid, long userId,String comment, byte answerScore, String netAddress); 

}
