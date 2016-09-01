package flint.business.abc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kiss.util.Q;

import org.springframework.stereotype.Repository;

import entity.Room;
import flint.base.BaseDAOImpl;
import flint.business.abc.dao.AbcDAO;
import flint.business.constant.ClassroomConst;
import flint.jdbc.RowSetHandler;

@Repository
public class AbcDAOImpl extends BaseDAOImpl<Room, Long> implements AbcDAO {

	@Override
	public void close(Room room) {
		/* 更新教室状态 */
		update("update TB_ROOM set start_time=?,end_time=?,status=? where room_id=?",
				room.getStartTime(), room.getEndTime(),
				ClassroomConst.CLASS_OVER, room.getRoomId());
		/* 更新用户的订单细项 */

		/* 状态为报名状态的更新为未报到 */
		update("update TB_ORDER_ITEM  set  status=? where  room_id=? and `STATUS`=? ",
				ClassroomConst.USER_NOT_COME, room.getRoomId(),
				ClassroomConst.JOIN_OK);

	}

	@Override
	public void updateUser(Room room) {
		update("update TB_ORDER_ITEM  set   room_id=?,buyer_address_id=? where  PRODUCT_ID=? and PRODUCT_TYPE=? and SUB_ID=?  and `STATUS`=? ",
				room.getRoomId(), room.getHost(), room.getSubjectId(),
				room.getSubjectType(), room.getSubId(), ClassroomConst.JOIN_OK);
	}

	@Override
	public void logout(int enterTime, int leaveTime, long roomId, long userId,
			byte status) {
		status = (Byte) Q.when(status, ClassroomConst.USER_OK,
				ClassroomConst.USER_OUT, status);
		update("update TB_ORDER_ITEM set f1=?, f2=?,status=? where  room_id=?  and buyer_address_id=?",
				enterTime, leaveTime, status, roomId, userId);
	}

	@Override
	public long getRecordTime(long id) {
		return getLong(
				"select  (end_time-start_time) as time from TB_ROOM where room_id=?",
				id);
	}

	@Override
	public void record(long roomId, String data, String remoteAddr, long userId) {
		update("insert into TB_ROOM_RECORD(ROOM_ID,RECORD_TIME,DATA,NET_ADDRESS,USER_ID) values(?,?,?,?,?)",
				roomId, Q.now(), data, remoteAddr, userId);
	}

	@Override
	public String getRecord(long roomId) {
		final long startTime = this.getLong(
				"select  start_time from TB_ROOM where room_id=?", roomId);
		return query(
				"select DATA,RECORD_TIME from TB_ROOM_RECORD where room_id=? order by RECORD_TIME ASC ",
				new RowSetHandler<String>() {
					@Override
					public String populate(ResultSet rs) throws SQLException {
						StringBuilder sb = new StringBuilder(
								"<?xml version=\"1.0\" encoding=\"utf-8\" ?><data>");
						StringBuilder prev = new StringBuilder("<pre>");
						StringBuilder items = new StringBuilder("<items>");
						while (rs.next()) {
							int recordTime = rs.getInt("RECORD_TIME");
							if (recordTime <= startTime) {
								prev.append("<item>").append(rs.getString(1))
										.append("</item>");
							} else {
								items.append("<item>").append(rs.getString(1))
										.append("</item>");
							}
						}
						prev.append("</pre>");
						items.append("</items>");
						sb.append(prev).append(items).append("</data>");
						return sb.toString();
					}
				}, roomId);

	}

}
