package flint.business.abc.room;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kiss.security.Identity;
import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Message;
import entity.Question;
import entity.QuestionAnswer;
import entity.Room;
import flint.business.abc.room.context.RoomConfig;
import flint.business.abc.room.context.RoomContext;
import flint.business.abc.room.context.RoomSession;
import flint.business.abc.room.support.RoomException;
import flint.business.constant.MessageConst;
import flint.business.constant.QuestionConst;
import flint.business.constant.TradeConst;
import flint.business.question.service.QuestionService;
import flint.business.trade.service.TradeStatus;
import flint.business.user.service.MessageService;
import flint.business.user.service.UserService;
import flint.exception.ApplicationException;
import flint.util.NumberHelper;

@Service
public class QuestionRoom extends OrderRoom {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService userService;

	// @Autowired
	// private WiziqService wiziqService;

	@Override
	public RoomConfig getRoomConfig() {
		return null;
	}

	@Override
	public Room create(Room room, Identity user) throws ApplicationException {
		Question question = questionService.findById(room.getSubjectId());
		/* 检查问题状态 */
		if (question == null || question.getStatus() == 0
				|| question.getStatus() == QuestionConst.ANSWER_DELETE) {
			throw new ApplicationException("QUESTION_NOT_FIND");
		}

		if (question.getAsker() == room.getUserId()) {
			throw new ApplicationException("QUESTION_ANSWER_BY_ASKER_ILLEGAL");
		}

		/* 记录解答者信息 */
		room.setSubject(question.getTitle());
		room.setHost(room.getUserId());

		// /* 使用wiziq对接 */
		// kiss.collaboration.node.Room wroom = new
		// kiss.collaboration.node.Room(
		// String.valueOf(room.getRoomId()), String.valueOf(room
		// .getRoomId()));
		// wroom.setMaxAttendee(2);
		// //wroom.setDuration(room.getTime());
		// wroom.attr(
		// "return_url",
		// "http://www.17shangke.com/abc/end.html?roomId="+ room.getRoomId());
		// wiziqService.schedule(wroom);
		// /* 先保存主将人的信息 */
		// JSONObject o = new JSONObject();
		// o.put("classid", wroom.attr("classid"));
		// o.put("presenter_url", wroom.attr("presenter_url"));
		// o.put("recording_url", wroom.attr("recording_url"));
		// JSONObject p = new JSONObject();
		// p.put("user" + user.getId(), wroom.attr("presenter_url"));
		// o.put("attendees", p);
		// room.setMemo(o.toJSONString());

		/* 提问人要进入后才可见 */

		/* 保存消息 */
		int dateline = Q.now();
		Message message = new Message();
		message.setMessage("{\"title\":\"" + question.getTitle()
				+ "\",\"roomid\":\"" + room.getRoomId() + "\"}");
		message.setSendTime(dateline);
		message.setSender(Long.valueOf(user.getId()));
		message.setType(MessageConst.QUESRION_ANSWER_INSTANCE);

		/* 发送信息的解答消息 */
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("type", MessageConst.QUESRION_ANSWER_INSTANCE);
		data.put("message", message.getMessage());
		data.put("name", user.getName());
		data.put("face", user.getAttribute("face"));
		data.put("sender", user.getId());
		data.put("shower", user.getId());
		data.put("dateline", message.getSendTime());

		/* 通知所有人关注该问题的人,这里只通知在线用户 */
		List<Long> focus = questionService.getWatchers(
				question.getQuestionId(), NumberHelper.toLong(user.getId()),
				true);
		for (long u : focus) {// 通知提出问题的人
			message.setReceiver(u);
			long id = messageService.im(message);
			data.put("receiver", u);
			data.put("id", id);
			cometdService.privateBroadcast(u, data);
		}

		/* 问题这里直接生成答案记录，用户主要是购买答案，生成交易订单的时候直接与问题答案对应 */
		QuestionAnswer qanswer = new QuestionAnswer();
		qanswer.setAnswer(room.getHost());
		qanswer.setQuestionId(room.getSubjectId());
		qanswer.setType(QuestionConst.ANSWER_VIDEO);
		qanswer.setRoomId(room.getRoomId());
		qanswer.setPrice(room.getPrice());
		qanswer.setAnswerTime(Q.now());
		long answerId = dao.insert(qanswer);
		/* 增加答案数量 */
		questionService.newAnswer(room.getSubjectId());

		room.setSubId(answerId);// 这里记录答案的编号
		/* 保存并创建 */
		room = super.create(room, user);
		return room;
	}

	@Override
	public boolean authenticate(RoomContext context, Identity user,
			String netAddress) throws RoomException, ApplicationException {
		Room room = context.getRoom();
		/* 生成订单 */
		long answerId = room.getSubId();
		questionService.viewAnswer(answerId, Long.parseLong(user.getId()),
				room.getSubject(), netAddress, room.getRoomId());
		// JSONObject m = Q.toJSON(room.getMemo());
		// String classId = m.getString("classid");
		// /* 添加接口 */
		// List<Attendee> list = wiziqService.addAttendee(
		// String.valueOf(room.getRoomId()), user.getId(), user.getName());
		// JSONObject attendees = m.getJSONObject("attendees");
		// for (Attendee a : list) {
		// attendees.put("user" + a.getId(), a.attr("url"));
		// }
		// room.setMemo(m.toJSONString());
		return true;
	}

	@Override
	public void close(Room room, RoomSession session) {
		super.close(room, session);
		// /*wiziq退出教室*/
		// wiziqService.checkOut(String.valueOf(room.getRoomId()));
		/* 更新问题答案状态 */
		if ("false".equals(configHelper.getString("question", "question.check",
				"false"))) {
			QuestionAnswer answer = new QuestionAnswer();
			answer.setAnswerId(room.getSubId());
			answer.setQuestionId(room.getSubjectId());
			answer.setMemo(room.getSubject());
			answer.setAnswer(session.getUserId());
			questionService.checkAnswer(answer, QuestionConst.OK,
					session.getName(), session.getFace());
			/* 关闭问题教室时，通知通话有人解答了他的问题 */
		}
	}

	@Override
	public boolean playback(Room room, long userId, boolean allowPay,
			String netAddress) throws ApplicationException {
		/* 本人可以直接看 */
		if (room.getHost() == userId) {
			return true;
		}

		TradeStatus status = tradeService.getTradeStatus(room.getSubjectId(),
				TradeConst.TYPE_QUESTION, userId);
		if (status.isPrepayed()) {
			return true;
		}
		/* 自动支付答案，这里主要是防护用户直接使用视频地址时要求用户对答案付款 */
		if (allowPay) {
			questionService.viewAnswer(room.getSubId(), userId,
					room.getSubject(), netAddress, room.getRoomId());
		}
		return false;
	}

}
