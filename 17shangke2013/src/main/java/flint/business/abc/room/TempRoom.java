package flint.business.abc.room;

import java.util.ArrayList;
import java.util.List;

import kiss.security.Identity;

import org.springframework.stereotype.Service;

import entity.OrderItem;
import entity.Room;
import flint.business.abc.room.context.RoomConfig;
import flint.business.abc.room.context.RoomContext;
import flint.business.abc.room.context.RoomSession;
import flint.business.abc.room.support.RoomException;
import flint.business.constant.ClassroomConst;
import flint.business.constant.TradeConst;
import flint.business.trade.service.TradeStatus;
import flint.exception.ApplicationException;

@Service
public class TempRoom extends RoomService {

	@Override
	public RoomConfig getRoomConfig() {
		return null;
	}

	public Room create(Room room, Identity user) throws ApplicationException {
		room.setHost(room.getUserId());// 临时教室的主持人一定为创建人
		room.setCapacity(10);// 临时教室最多为10人
		room.setSubjectId(room.getRoomId());// 临时教室设定主题编号直接为教室编号
		return super.create(room, user);
	}

	@Override
	public boolean authenticate(RoomContext context, Identity user,
			String netAddres) throws RoomException, ApplicationException {
		Room room = context.getRoom();
		/* 每新进入1个用户，记录1条记录, 临时课程生成订单 */
		this.prepayForRoom(room, Long.parseLong(user.getId()));
		return true;
	}

	@Override
	public void close(Room room, RoomSession session) {
		super.close(room,session);
	}

	private void prepayForRoom(Room room, long userId)
			throws ApplicationException {
		long orderId = Long.parseLong(dao.getDateSerialNumber("question"));
		List<OrderItem> items = new ArrayList<OrderItem>();
		OrderItem item = new OrderItem();
		item.setOrderId(orderId);
		item.setQuantity(1);
		item.setDeliveries(1);
		item.setTotal(1);
		item.setProductType(TradeConst.TYPE_ROOM_TEMP);
		item.setOldPrice(room.getPrice());
		item.setPrice(room.getPrice());
		item.setProductId(room.getSubjectId());
		item.setProductName(room.getSubject());
		item.setStatus(ClassroomConst.USER_OK);// 正常上课
		item.setRoomId(room.getRoomId());// 存储教室编号
		item.setBuyerAddressId(userId);
		items.add(item);
		/* 生成订单 */
		tradeService.fastOrder(orderId, TradeConst.TYPE_ROOM_TEMP, userId,
				room.getUserId(), room.getPrice(), items, "临时教室");
	}

	@Override
	public boolean playback(Room room, long userId, boolean allowPay,
			String netAddress) throws ApplicationException {
		TradeStatus status = tradeService.getTradeStatus(room.getSubjectId(),
				TradeConst.TYPE_ROOM_TEMP, userId);
		if (status.isPrepayed()) {
			return true;
		}
		if (allowPay) {
			this.prepayForRoom(room, userId);
		}
		return false;
	}

}
