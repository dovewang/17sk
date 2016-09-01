package flint.business.abc.room;

import kiss.security.Identity;
import entity.Room;
import flint.business.abc.room.context.RoomSession;
import flint.exception.ApplicationException;

public interface Lifecycle {
	/**
	 * 创建1个课室，需要根据用户的权限判断用户是否具有创建教室的权限
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	Room create(Room room, Identity user) throws ApplicationException;

	/**
	 * 关闭教室
	 */
	void close(Room room,RoomSession session);

	/**
	 * 收费课程，默认不允许直接播放
	 * @throws ApplicationException 
	 */
	boolean playback(Room room, long userId, boolean allowPay, String netAddress) throws ApplicationException;
}
