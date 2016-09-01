package flint.business.question.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kiss.search.SearchEngine;
import kiss.security.Identity;
import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entity.AnswerView;
import entity.Mblog;
import entity.Message;
import entity.OrderItem;
import entity.Question;
import entity.QuestionAnswer;
import flint.base.BaseServiceImpl;
import flint.business.constant.AtConst;
import flint.business.constant.ClassroomConst;
import flint.business.constant.MblogConst;
import flint.business.constant.MessageConst;
import flint.business.constant.QuestionConst;
import flint.business.constant.TradeConst;
import flint.business.message.CometdService;
import flint.business.question.dao.AnswerDAO;
import flint.business.question.dao.QuestionDAO;
import flint.business.question.service.QuestionService;
import flint.business.question.util.QuestionSearcher;
import flint.business.search.QuestionSearch;
import flint.business.social.service.SocialService;
import flint.business.trade.service.TradeService;
import flint.business.user.dao.UserDAO;
import flint.business.user.service.MessageService;
import flint.business.user.service.UserService;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.util.NumberHelper;

@Service
public class QuestionServiceImpl extends BaseServiceImpl implements
		QuestionService {

	@Autowired
	private QuestionSearch questionSearch;

	@Autowired
	private UserService userService;

	@Autowired
	private SocialService socialService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private TradeService tradeService;

	@Autowired
	private CometdService cometdService;

	@Autowired
	private QuestionDAO dao;

	@Autowired
	private AnswerDAO answerdao;

	@Autowired
	private UserDAO userDao;
	
	private SearchEngine search;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Page<Question> search(long page, long size, QuestionSearcher qs)
			throws ApplicationException {
		if (qs.isFullText()) {
			Page pageSearch = questionSearch.queryPage(qs.getQueryBuilder(),
					(int) page, (int) size);
			List<String> searchResult = (List<String>) pageSearch.getResult();
			if (pageSearch.getTotalCount() > 0) {
				pageSearch.setResult(dao.findQuestionByIds(searchResult));
			} else {
				pageSearch.setResult(new ArrayList());
			}
			return pageSearch;
		} else {
			return dao.search(page, size, qs.getWhere(), qs.getParamters());
		}
	}

	@Override
	public QuestionAnswer getAnswer(long answerId) {
		return dao.getAnswer(answerId);
	}

	@Override
	@Transactional
	public QuestionAnswer viewAnswer(long answerId, long viewer, String title,
			String netAddress, long roomId) throws ApplicationException {
		QuestionAnswer answer = getAnswer(answerId);
		/* 防止重复查看时会出现问题 */
		if (dao.isAnswerView(answerId, viewer)) {
			return answer;
		}
		/* 创建订单，直接付款，更新用户查看状态 */
		AnswerView v = new AnswerView();
		v.setAnswerId(answerId);
		v.setUserId(viewer);
		v.setNetAddress(netAddress);
		v.setDateline(Q.now());
		v.setStatus(QuestionConst.OK);
		dao.save(v);
		long orderId = Long.parseLong(dao.getDateSerialNumber("question"));
		List<OrderItem> items = new ArrayList<OrderItem>();
		OrderItem item = new OrderItem();
		item.setOrderId(orderId);
		item.setQuantity(1);
		item.setDeliveries(1);
		item.setTotal(1);
		item.setProductType(TradeConst.TYPE_QUESTION);
		item.setOldPrice(answer.getPrice());
		item.setPrice(answer.getPrice());
		item.setProductId(answer.getQuestionId());
		item.setSubId(answerId);
		item.setProductName(title);
		item.setBuyerAddressId(viewer);// 默认为用户编号
		/* 这里只有实时解答的时候会产生房间编号 */
		item.setRoomId(roomId);
		String memo = "查看答案";
		if (roomId != 0) {
			item.setStatus(ClassroomConst.USER_OK);
			memo = "实时解答";
		}
		items.add(item);
		/* 快速订单，直接支付完成 */
		tradeService.fastOrder(orderId, TradeConst.TYPE_QUESTION, viewer,
				answer.getAnswer(), answer.getPrice(), items, memo);
		return answer;
	}

	@Override
	@Transactional
	public long post(Question question, String helpers)
			throws ApplicationException {
		try {
			question.setIntro(Q.isEmpty(question.getIntro(), " "));
			question.setCreateTime(Q.now());
			question.setLastUpdateTime(question.getCreateTime());
			if ("false".equals(configHelper.getString("question",
					"question.check", "false"))) {
				question.setStatus(QuestionConst.QUESTION_STATUS_UNSOLVED);// 新建状态
			}
			long questionId = dao.insert(question);
			if (questionId > 0) {
				userService.addScore(question.getAsker(), "question.create");
				/* 创建问题索引 */
				questionSearch.createIndex(questionId);
				
				//search.index("", "quesiton", String.valueOf(questionId),"");
				
				/* 将信息同步到微博信息 */
				Mblog mblog = new Mblog();
				mblog.setDateline(question.getCreateTime());
				mblog.setType(MblogConst.TYPE_QUESTION);
				mblog.setSchoolId(question.getSchoolId());
				mblog.setContent("发布了问题<a>#" + question.getTitle() + "#</a>");
				mblog.setUserId(question.getAsker());
				mblog.setMedia(String.valueOf(questionId));
				socialService.sync(mblog);
				/* 求助用户 */
				if (!Q.isBlank(helpers)) {
					userService.at(questionId, AtConst.QUESTION, helpers);
				}
			}
			return questionId;
		} catch (Exception e) {
			throw new ApplicationException("UNKNOWN");
		}
	}

	@Override
	public int update(Question question) throws ApplicationException {
		question.setLastUpdateTime(Q.now());
		int result = dao.update(question);
		if (result > 0) {
			Mblog mblog = new Mblog();
			mblog.setDateline(question.getLastUpdateTime());
			mblog.setType(MblogConst.TYPE_QUESTION);
			mblog.setSchoolId(question.getSchoolId());
			mblog.setContent("编辑了<a>#" + question.getTitle() + "#</a>这个问题");
			mblog.setUserId(question.getAsker());
			mblog.setMedia(String.valueOf(question.getQuestionId()));
			socialService.sync(mblog);
			questionSearch.createIndex(question.getQuestionId());
		}
		return result;
	}

	private void cancle(long questionId, String memo)
			throws ApplicationException {
		/* 取消之前冻结的交易，返还资金 ，由于问题的订单唯一，即一个问题只能建立一个订单 */
		List<OrderItem> items = dao.findByWhere(OrderItem.class,
				"   where product_Id=? and product_type=?", questionId,
				TradeConst.TYPE_QUESTION);
		/* 取消交易 */
		if (items.size() > 0) {
			tradeService.cancle(items.get(0).getOrderId(), memo);
		}
	}

	@Override
	public int close(long questionId, long asker) throws ApplicationException {
		return dao.close(questionId, asker);
	}

	@Override
	public int delete(long questionId, long asker) throws ApplicationException {
		int result = dao.delete(questionId);
		if (result > 0) {
			/* 删除全文检索 */
			questionSearch.delete(questionId);
		}
		return result;
	}

	@Override
	public Question findById(long questionId) throws ApplicationException {
		return dao.findById(questionId);
	}

	@Override
	public Question findById(long id, long userid) {
		Question question = dao.findById(id);
		return question;
	}

	@Override
	public List<QuestionAnswer> findAnswers(long questionId, long userId,
			int orderBy) throws ApplicationException {
		return dao.findAnswers(questionId, userId, orderBy);
	}

	@Override
	@Transactional
	public int answer(QuestionAnswer answer, Identity user)
			throws ApplicationException {
		return answer(answer, user, false);
	}

	@Override
	@Transactional
	public int answer(QuestionAnswer answer, Identity user, boolean update)
			throws ApplicationException {
		userService.addAnswers(answer.getAnswer(), 1);
		userService.addScore(answer.getAnswer(), "question.answer");
		String title = answer.getMemo();
		answer.setMemo("");
		/* 判断系统流程设定问题是否需要审核，默认为不审核 */
		if ("false".equals(configHelper.getString("question", "answer.check",
				"false"))) {
			answer.setStatus(QuestionConst.OK);
		}
		if (update) {
			dao.update(answer);
		} else {
			answer.setAnswerTime(Q.now());
			answer.setAnswerId(dao.insert(answer));
			/* 更新问题 */
			dao.newAnswer(answer.getQuestionId());
			answer.setMemo(title);
			answerNotify(answer, user.getName(),
					(String) user.getAttribute("face"));
		}
		return 1;
	}

	private void answerNotify(QuestionAnswer answer, String userName,
			String face) {
		/* 保存消息 */
		int dateline = Q.now();
		Message message = new Message();
		message.setMessage("{\"title\":\"" + answer.getMemo().trim()
				+ "\",\"answerid\":\"" + answer.getAnswerId()
				+ "\",\"questionid\":\"" + answer.getQuestionId() + "\"}");
		message.setSendTime(dateline);
		message.setSender(answer.getAnswer());
		message.setType(MessageConst.QUESRION_ANSWER);

		/* 发送信息的解答消息 */
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("type", MessageConst.QUESRION_ANSWER);
		data.put("message", message.getMessage());
		data.put("answer", answer.getAnswer());
		data.put("name", userName);
		data.put("face", face);
		data.put("sender", answer.getAnswer());
		data.put("shower", answer.getAnswer());
		data.put("dateline", message.getSendTime());
		List<Long> focus = this.getWatchers(answer.getQuestionId(),
				answer.getAnswer(), false);
		for (long u : focus) {// 通知提出问题的人
			message.setReceiver(u);
			long id = messageService.im(message);
			data.put("receiver", u);
			data.put("id", id);
			cometdService.privateBroadcast(u, data);
		}
	}

	@Override
	@Transactional
	public int selectAnswer(long schoolId, long qid, String title, long aid,
			long userId, byte type, String netAddress, Identity user)
			throws ApplicationException {
		/* 如果用采纳的是免费的问题 生成查看日志记录 */
		if (type == 0 && answerdao.selectScore(aid, userId) == null) {
			AnswerView v = new AnswerView();
			v.setAnswerId(aid);
			v.setUserId(userId);
			v.setNetAddress(netAddress);
			v.setDateline(Q.now());
			v.setStatus(QuestionConst.OK);
			v.setAdopt(true);
			dao.save(v);
		} else {
			type = 1;
		}
		int answer = dao.selectAnswer(qid, aid, userId, type);
		/* 选择答案后，问题已解决，重建索引 */
		if (answer > 0) {
			/* 发送用户动态消息 */
			Mblog mblog = new Mblog();
			mblog.setDateline(Q.now());
			mblog.setType(MblogConst.TYPE_QUESTION);
			mblog.setSchoolId(schoolId);
			mblog.setContent("选择了<a>#" + title + "#</a>问题的答案");
			mblog.setUserId(userId);
			mblog.setMedia(String.valueOf(qid));
			socialService.sync(mblog);

			QuestionAnswer a = dao.getAnswer(aid);
			userService.addAdoptAnswers(a.getAnswer(), 1);
			userService.addScore(a.getAnswer(), "question.answer.adopt");
			/* 这里判断用户是否付款，如果已付款这不在提示 */

			/* 通知解答人 */

			/* 保存消息 */
			int dateline = Q.now();
			Message message = new Message();
			message.setMessage(title);
			message.setSendTime(dateline);
			message.setSender(userId);
			message.setReceiver(a.getAnswer());
			message.setType(MessageConst.QUESRION_ANSWER_SELECT);
			long id = messageService.im(message);

			/* 发送信息的解答消息 */
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("type", MessageConst.QUESRION_ANSWER_SELECT);
			data.put("message", message.getMessage());
			data.put("answer", userId);
			data.put("name", user.getName());
			data.put("face", user.getAttribute("face"));
			data.put("sender", userId);
			data.put("shower", userId);
			data.put("dateline", message.getSendTime());
			data.put("receiver", a.getAnswer());
			data.put("id", id);
			cometdService.privateBroadcast(a.getAnswer(), data);

			/** 建立老师和学生的关系（回答人是老师、查看人是学生） **/
			userDao.addStudent(a.getAnswer(), userId);
		}
		questionSearch.createIndex(qid);
		return answer;
	}

	@Override
	public void checkAnswer(QuestionAnswer answer, byte status,
			String userName, String face) {
		this.answerNotify(answer, userName, face);
		dao.checkAnswer(answer.getAnswerId(), status);
	}

	@Override
	public int comment(long answerId, long buyer, int score, int mark,
			String comment) {
		return dao.comment(answerId, buyer, score, mark, comment);
	}

	@Override
	public int deleteAnswer(long answer, long questionId, long answerId) {
		/* 用户答案数减少，问题答案数减少 */
		// userService.addAnswers(answerId, -1);
		return dao.deleteAnswer(answerId);
	}

	@Override
	public QuestionAnswer findAnswerById(long id) {
		return dao.findAnswerById(id);
	}

	@Override
	public int updateAnswer(long aid, String content)
			throws ApplicationException {
		return dao.updateAnswer(aid, content);
	}

	@Override
	public Page<Question> questionSearch(long page, int size,
			Map<String, String[]> params) throws ParseException {
		return dao.search(page, size, params);
	}

	@Override
	public int verify(long qid, String memo, int status, Identity user)
			throws ApplicationException {
		int result = 0;
		if (status == -1) {// 审核不通过
			result = dao.verifyIsNo(qid, memo);
		} else {
			result = dao.verifyIsYes(qid);
		}
		int dateline = Q.now();
		Question question = dao.findById(qid);
		Message message = new Message();
		message.setMessage("您的提问#" + question.getTitle() + "#审核"
				+ (status == -1 ? "不通过，原因是" + memo : "已通过!"));
		message.setSendTime(dateline);
		message.setType(MessageConst.SYSTEM);
		message.setReceiver(question.getAsker());
		messageService.send(message, user);
		return result;
	}

	@Override
	public Page<Question> searchGoodat(long page, int size, String expert) {
		return dao.searchGoodat(page, size, expert);
	}

	@Override
	public int createIndexs() {
		questionSearch.rebulidIndex();
		return 1;
	}

	@Override
	public int addview(long id) {
		return dao.addview(id);
	}

	@Override
	public Page<Question> searchAtQuestion(long userId, long page, long size) {
		Page<Question> pageList = dao.searchAtQuestion(userId, page, size);
		List<String> questionIds = new ArrayList<String>();
		Iterator<Question> it = pageList.getResult().iterator();
		while (it.hasNext()) {
			Question q = it.next();
			if (NumberHelper.toByte(q.getMemo()) == AtConst.NO_READ) {
				String qid = String.valueOf(q.getQuestionId());
				questionIds.add(qid);
			}
		}
		// At信息置为已读
		if (questionIds.size() > 0) {
			userService.readAtStatus(questionIds, userId, AtConst.QUESTION);
		}
		return pageList;
	}

	@Override
	public int newAnswer(long questionId) {
		return dao.newAnswer(questionId);
	}

	@Override
	public Question newest(long schoolId, long userId) {
		return dao.newest(schoolId, userId);
	}

	@Override
	public List<Question> related(Question question) {
		return dao.related(question);
	}

	@Override
	public int watch(long questionId, long userId) {
		return 0;
	}

	@Override
	public int unwatch(long questionId, long userId) {
		return 0;
	}

	@Override
	public int invite(long questionId, String inviters) {
		return 0;
	}

	@Override
	public List<Long> getWatchers(long questionId, long userId, boolean online) {
		return dao.getWatchers(questionId, userId, online);
	}
}