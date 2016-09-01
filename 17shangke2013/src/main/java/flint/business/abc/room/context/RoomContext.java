package flint.business.abc.room.context;

import java.util.List;

import kiss.security.Identity;
import entity.Room;
import flint.business.abc.room.support.RoomException;
import flint.exception.ApplicationException;

/**
 * 单个房间的服务信息
 * 
 * @author Dove Wang
 * 
 */
public interface RoomContext {

	/**
	 * 房间创建的时间
	 * 
	 * @return
	 */
	long getCreationTime();

	/**
	 * 房间销毁时间
	 * 
	 * @return
	 */
	long getDestroyTime();

	/**
	 * 录制开始时间
	 * 
	 * @param time
	 */
	void setRecordStartTime(long time);

	long getRecordStartTime();

	/**
	 * 房间在线人数
	 * 
	 * @return
	 */
	int getOnlines();

	/**
	 * 房间的基本信息
	 * 
	 * @return
	 */
	Room getRoom();

	/**
	 * 房间授权
	 * 
	 * @param userId
	 * @param password
	 * @return
	 * @throws RoomException
	 * @throws ApplicationException
	 */
	RoomSession authenticate(String netAddress, Identity user, String password)
			throws RoomException, ApplicationException;

	/**
	 * 所有房间在线用户的信息，按登入的时间进行排序
	 * 
	 * @return
	 */
	List<RoomSession> getSessions();

	void removeSession(long userId);

	RoomSession getSession(long userId);

	void destory();

	<T> T getAttribute(String attribute);

	void setAttribute(String attribute, Object value);

	<T> T removeAttribute(String attribute);

}
