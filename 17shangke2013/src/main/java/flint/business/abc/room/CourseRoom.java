package flint.business.abc.room;

import kiss.security.Identity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entity.Course;
import entity.Room;
import flint.business.abc.room.context.RoomContext;
import flint.business.abc.room.context.RoomSession;
import flint.business.abc.room.support.RoomException;
import flint.business.constant.TradeConst;
import flint.business.course.service.CourseService;
import flint.business.trade.service.TradeStatus;
import flint.exception.ApplicationException;
import flint.util.DateHelper;

@Service
public class CourseRoom extends OrderRoom {
	private int beforeTime = 5;
	@Autowired
	private CourseService courseService;

	@Override
	@Transactional
	public Room create(Room room, Identity user) throws ApplicationException {
		Course course = courseService.findById(room.getSubjectId());
		if (course == null) {
			throw new ApplicationException("COURSE_NOT_EXISTS");
		}
		room.setHost(course.getUserId());

		if (course.getStartTime() - DateHelper.getNowTime() > beforeTime * 60) {
			/* 返回信息 */
			throw new ApplicationException("ROOM_WAIT_START",
					String.valueOf(DateHelper.getNow(course.getStartTime(),
							DateHelper.DATE_TIME)), String.valueOf(beforeTime));
		}

		/* 更新用户要进入的教室信息 */
		dao.updateUser(room);
		return super.create(room, user);
	}

	@Override
	public boolean authenticate(RoomContext context, Identity user,
			String netAddres) throws RoomException, ApplicationException {
		Room room = context.getRoom();
		TradeStatus status = tradeService.getTradeStatus(room.getSubjectId(),
				TradeConst.TYPE_COURSE_ONLINE, Long.parseLong(user.getId()));
		if (status.isPrepayed()) {
			return true;
		}
		return false;
	}

	@Override
	public void close(Room room, RoomSession session) {
		super.close(room, session);
		/* 未来报到的用户，自动退费 */
		courseService.end(room.getRoomId(), "", room.getSubjectId(),
				room.getStartTime(), room.getEndTime());
		/* 用户没来上课？取消交易 */
		// tradeService.cancle(orderId, "用户没有来上课，交易取消，自动退费");
	}

	@Override
	public boolean playback(Room room, long userId, boolean allowPay,
			String netAddress) {
		/* 课程必须是课程结束后的才行 */
		TradeStatus status = tradeService.getTradeStatus(room.getSubjectId(),
				TradeConst.TYPE_COURSE_ONLINE, userId);
		/* 至少必须是预付款 ，才能查看答案 */
		if (status.isPrepayed()) {
			return true;
		}
		return false;
	}
}
