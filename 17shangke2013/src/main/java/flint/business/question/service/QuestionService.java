package flint.business.question.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import kiss.security.Identity;
import entity.Question;
import entity.QuestionAnswer;
import flint.base.BaseService;
import flint.business.question.util.QuestionSearcher;
import flint.common.Page;
import flint.exception.ApplicationException;

/**
 * 
 * 功能描述：用户发布问题模块，问题相关的操作都同步发布到用户指定的SNS网站，由用户自行配置消息接收方式，默认是向所有绑定账号发布消息（包括邮箱，微博，分享
 * ，邮箱，手机）<br>
 * 
 * 日 期：2011-7-20 18:14:17<br>
 * 
 * 作 者：HuangShuai<br>
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
public interface QuestionService extends BaseService {

	/**
	 * 用户发布一个问题，同时将数据发布到用户绑定的微博及其他SNS网站
	 * 
	 * @param question
	 * @param helpers
	 *            求助的人或者组织，多个已逗号分隔
	 * @return
	 * @throws ApplicationException
	 */
	long post(Question question, String inviters) throws ApplicationException;

	/**
	 * 关闭一个问题，同时发布消息到相关的SNS，通常指未解决的问题，已解决的不允许关闭
	 * 
	 * @param questionId
	 * @return
	 * @throws ApplicationException
	 */
	int close(long questionId, long asker) throws ApplicationException;

	/**
	 * 用户更新问题,同时发布消息到相关的SNS
	 * 
	 * @param question
	 * @return
	 * @throws ApplicationException
	 */
	int update(Question question) throws ApplicationException;

	/**
	 * 删除一个问题
	 * 
	 * @param questionId
	 * @return
	 * @throws ApplicationException
	 */
	int delete(long questionId, long asker) throws ApplicationException;

	/**
	 * 获取单个问题的详细信息
	 * 
	 * @param questionId
	 * @return
	 */
	Question findById(long questionId) throws ApplicationException;

	/**
	 * 查看单个问题的详细信息
	 * 
	 * @param questionId
	 * @return
	 */
	Question findById(long questionId, long userid) throws ApplicationException;

	/**
	 * 获取某个问题的相关问题，目前只按分类进行获取
	 * 
	 * @param question
	 * @return
	 */
	List<Question> related(Question question);

	/**
	 * 根据ID获取问题的答案
	 * 
	 * @param questionId
	 * @return
	 * @throws ApplicationException
	 */
	List<QuestionAnswer> findAnswers(long questionId, long userId, int orderBy)
			throws ApplicationException;

	/**
	 * 
	 * @param page
	 * @param size
	 * @param qs
	 * @return
	 * @throws ApplicationException
	 */
	Page<Question> search(long page, long size, QuestionSearcher qs)
			throws ApplicationException;

	/**
	 * 解答问题
	 * 
	 * @param answer
	 * @return
	 * @throws ApplicationException
	 */
	int answer(QuestionAnswer answer, Identity user)
			throws ApplicationException;

	int answer(QuestionAnswer answer, Identity user, boolean update)
			throws ApplicationException;

	/**
	 * 选择问题答案
	 * 
	 * @param schoolId
	 * @param qid
	 * @param aid
	 * @param asker
	 * @return
	 * @throws ApplicationException
	 */
	int selectAnswer(long schoolId, long qid, String title, long aid,
			long asker, byte type, String netAddress, Identity user)
			throws ApplicationException;

	/**
	 * 查看答案
	 * 
	 * @param answerId
	 * @param viewer
	 * @param netAddress
	 * @return
	 * @throws ApplicationException
	 */
	QuestionAnswer viewAnswer(long answerId, long viewer, String title,
			String netAddress, long roomId) throws ApplicationException;

	/**
	 * 关注某个问题
	 * 
	 * @param questionId
	 * @param userId
	 * @return
	 */
	int watch(long questionId, long userId);

	/**
	 * 取消对某个问题的关注
	 * 
	 * @param questionId
	 * @param userId
	 * @return
	 */
	int unwatch(long questionId, long userId);

	/**
	 * 邀请某人解答
	 * 
	 * @param questionId
	 * @param inviters
	 * @return
	 */
	int invite(long questionId, String inviters);

	/**
	 * 关注该问题的用户
	 * 
	 * @param questionId
	 * @param online
	 *            是否在线
	 * @return
	 */
	List<Long> getWatchers(long questionId, long userId, boolean online);

	/**
	 * 根据answerId获取answer内容
	 * 
	 * @param answerId
	 * @return
	 */
	QuestionAnswer getAnswer(long answerId);

	/**
	 * 审核问题答案
	 * 
	 * @param answerId
	 * @param status
	 */
	void checkAnswer(QuestionAnswer answer, byte status, String userName,
			String face);

	int comment(long answerId, long buyer, int score, int mark, String comment);

	/**
	 * 删除答案
	 * 
	 * @param answer
	 * @param questionId
	 * @param answerId
	 * @return
	 */
	int deleteAnswer(long answer, long questionId, long answerId);

	QuestionAnswer findAnswerById(long id);

	/**
	 * 用户更新答案
	 * 
	 * @param question
	 * @return
	 * @throws ApplicationException
	 */
	int updateAnswer(long aid, String content) throws ApplicationException;

	/**
	 * 审核问题
	 * 
	 * @param qid
	 * @return
	 * @throws ApplicationException
	 */
	int verify(long qid, String memo, int status, Identity user)
			throws ApplicationException;

	Page<Question> questionSearch(long page, int size,
			Map<String, String[]> params) throws ParseException;

	/**
	 * 查找用户擅长的科目
	 * 
	 * @param page
	 * @param size
	 * @param expert
	 * @return
	 */
	public Page<Question> searchGoodat(long page, int size, String expert);

	/**
	 * 问题索引重建
	 * 
	 * @return
	 */
	public int createIndexs();

	/**
	 * 增加浏览人数
	 * 
	 * @param id
	 * @return
	 */
	int addview(long id);

	/**
	 * 检索At有关问题
	 * 
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	Page<Question> searchAtQuestion(long userId, long page, long size);

	/**
	 * 问题答案 +1
	 * 
	 * @param questionId
	 * @return
	 */
	int newAnswer(long questionId);

	/**
	 * 查找用户最新发布的问题
	 */
	Question newest(long schoolId, long userId);
}
