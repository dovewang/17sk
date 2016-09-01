package flint.business.abc.dao;

import entity.Room;
import flint.base.BaseDAO;

public interface AbcDAO extends BaseDAO<Room, Long> {

	void close(Room room);

//	/**
//	 * 只检查用户是否在该房间存在，只针对固定的教室
//	 * 
//	 * @param roomId
//	 * @param userId
//	 * @return
//	 */
//	boolean checkUser(long roomId, long userId);


	void logout(int enterTime, int leaveTime, long roomId, long userId, byte status);

	/**
	 * 获取录像的时间长度
	 * @param id
	 * @return
	 */
	long getRecordTime(long id);

	void record(long roomid, String data, String remoteAddr, long userId);

	String getRecord(long roomId);

	void updateUser(Room room);

}
