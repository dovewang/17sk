package flint.business.abc.room;

import kiss.security.Identity;

import org.springframework.beans.factory.annotation.Autowired;

import entity.Room;
import flint.business.abc.dao.AbcDAO;
import flint.business.abc.room.context.RoomConfig;
import flint.business.abc.room.context.RoomContext;
import flint.business.abc.room.context.RoomSession;
import flint.business.abc.room.support.RoomException;
import flint.business.message.CometdService;
import flint.business.trade.service.TradeService;
import flint.business.user.util.CertHelper;
import flint.context.ConfigHelper;
import flint.exception.ApplicationException;

public abstract class RoomService implements Lifecycle {

	@Autowired
	public TradeService tradeService;

	@Autowired
	public CometdService cometdService;

	@Autowired
	public ConfigHelper configHelper;

	@Autowired
	public AbcDAO dao;

	public abstract RoomConfig getRoomConfig();

	public abstract boolean authenticate(RoomContext context, Identity user,
			String netAddres) throws RoomException, ApplicationException;

	@Override
	public Room create(Room room, Identity user) throws ApplicationException {
		/* 未实名认证的用户，只允许创建1对1的教室 */
		if (!CertHelper.vRealName(CertHelper.getActive(user.getPassport()))) {
			room.setCapacity(2);
		} else if (room.getCapacity() == 0) {
			/* 未指定，默认只允许10人参与 */
			room.setCapacity(10);
		}
		dao.save(room);
		return room;
	}

	public void logout(RoomSession session) {
		dao.logout((int) (session.getCreationTime() / 1000L),
				(int) (session.getLastAccessedTime() / 1000L), session
						.getRoomContext().getRoom().getRoomId(),
				session.getUserId(), session.getStatus());
	}

	@Override
	public void close(Room room, RoomSession session) {
		dao.close(room);
	}

	@Override
	public boolean playback(Room room, long userId, boolean allowPay,
			String netAddress) throws ApplicationException {
		/* 检查用户的订单记录，凡是购买过该服务，状态正常的订单都可以播放视频 */
		return false;
	}
}
