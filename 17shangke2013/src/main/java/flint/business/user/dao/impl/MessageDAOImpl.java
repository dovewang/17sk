package flint.business.user.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import entity.Message;
import flint.base.BaseDAOImpl;
import flint.business.constant.AtConst;
import flint.business.constant.MessageConst;
import flint.business.user.dao.MessageDAO;
import flint.common.Page;
import flint.util.DateHelper;

/**
 * 消息
 * 
 * @author Dove Wang
 * 
 */
@Repository
public class MessageDAOImpl extends BaseDAOImpl<Message, Long> implements
		MessageDAO {
	
	@Override
	public long statUnread(long userId) {
		return count(
				"SELECT count(message_id) FROM TB_MESSAGE  where  receiver=? and receive_status=? ",
				userId, MessageConst.MESSAGE_NO_READ);
	}

	@Override
	public long statUnsend(long userId) {
		return count(
				"SELECT count(message_id) FROM TB_MESSAGE  where sender=? and send_status=? ",
				userId, MessageConst.MESSAGE_NO_SEND);
	}

	@Override
	public int deleteSendstatus(long id) {
		return update(
					"UPDATE TB_MESSAGE SET  SEND_STATUS = ? WHERE MESSAGE_ID = ?", MessageConst.MESSAGE_SEND_DELETE, id);
	}
	
	@Override
	public int deleteReceivestatus(long messageId, long userid) {
		//删除系统或学校信息
		int result = update(
						"UPDATE TB_MESSAGE_LOG SET STATUS = ? WHERE USER_ID = ? and MESSAGE_ID = ?",
						MessageConst.MESSAGE_RECEIVE_DELETE, 
						userid, 
						messageId
						);
		//删除私人消息
		if(result != 1){
			result =  update(
						"UPDATE  TB_MESSAGE SET  RECEIVE_STATUS = ? WHERE MESSAGE_ID = ?",
						MessageConst.MESSAGE_RECEIVE_DELETE,
						messageId
						);
		}
		return result;
	}

	@Override
	public int read(String ids, int type, long userid) {
		if(type == MessageConst.SYSTEM || type == MessageConst.SCHOOL){
			List<Object[]> parameters = new ArrayList<Object[]>();
			for (String id : ids.split(",")) {
				parameters.add(new Object[] { Long.parseLong(id) });
			}
			if (parameters.size() > 0)
			this.batchUpdate(
						"insert into TB_MESSAGE_LOG(MESSAGE_ID,STATUS,USER_ID) values(?,"+MessageConst.MESSAGE_READ+","+userid+")", parameters);
			return 1;
		}else{
			return update(
				"UPDATE  TB_MESSAGE SET read_time=?,receive_status=? WHERE message_id in("
						+ ids + ") ", DateHelper.getNowTime(),
				MessageConst.MESSAGE_READ);
		}
	}

	@Override
	public int flag(long messageId, long userId, byte status) {
		return 0;
	}

	@Override
	public Page<Message> inbox(long userId, byte type, long page, long size) {
		return findPage(
				"select * from TB_MESSAGE where receiver=? and type=? and receive_status!=?  ORDER BY RECEIVE_STATUS ASC,SEND_TIME DESC ",
				page, size, userId, type, MessageConst.MESSAGE_RECEIVE_DELETE);
	}

	@Override
	public Page<Message> outbox(long userId, long page, long size) {
		return findPage(
				"select * from TB_MESSAGE where sender=? and type=? and send_status!=?  ORDER BY RECEIVE_STATUS ASC,SEND_TIME DESC ",
				page, size, userId, MessageConst.PRIVATE,
				MessageConst.MESSAGE_SEND_DELETE);
	}

	@Override
	public Page<Map<String, Object>> allbox(long userId, long page, long size) {
		String sql = " SELECT MESSAGE_ID,SEND_TIME,SENDER,RECEIVER,MESSAGE,RECEIVE_STATUS, a.COUNT_MESSAGE FROM TB_MESSAGE,";
		sql += " (SELECT count(MESSAGE_ID) COUNT_MESSAGE,MAX(SEND_TIME) AS tt,MAX(MESSAGE_ID)  as mid FROM";
		sql += " (SELECT SEND_TIME,MESSAGE_ID,CONCAT(SENDER,RECEIVER) AS c FROM TB_MESSAGE WHERE sender=? AND type=? and send_status != ?";
		sql += " UNION ALL";
		sql += " SELECT SEND_TIME,MESSAGE_ID,CONCAT(RECEIVER,SENDER) AS c  FROM TB_MESSAGE WHERE receiver=? AND type=? and receive_status != ?) t GROUP BY c) a";
		sql += " where a.mid= TB_MESSAGE.MESSAGE_ID ORDER BY  SEND_TIME DESC";
		return findPageForMap(sql, page, size, userId, MessageConst.PRIVATE, MessageConst.MESSAGE_SEND_DELETE,
				userId, MessageConst.PRIVATE, MessageConst.MESSAGE_RECEIVE_DELETE);
	}

	@Override
	public int readAllbySenderAndReceiver(long sender, long receiver) {
		return update(
				"UPDATE  TB_MESSAGE SET  receive_status=? WHERE sender=? and receiver=?",
				MessageConst.MESSAGE_READ, sender, receiver);
	}

	@Override
	public Page<Message> drafts(long userId, long page, long size) {
		return findPage(
				"select * from TB_MESSAGE where sender=? and type=? and receive_status!=?  ORDER BY RECEIVE_STATUS ASC,SEND_TIME DESC ",
				page, size, userId, MessageConst.PRIVATE,
				MessageConst.MESSAGE_NO_SEND);
	}

	@Override
	public Page<Map<String, Object>> announce(long userId, String schoolIds, byte type,
			long page, long size) {
		int dayoffset = DateHelper.getTime(new Date(), 0, -3, 0, 0, 0, 0);
		String sql = "SELECT * FROM(SELECT  MESSAGE_ID,`SUBJECT`, TYPE, MESSAGE, SENDER,RECEIVER,SEND_STATUS,SCHOOL_ID,"
				+ "IFNULL((select status from TB_MESSAGE_LOG where TB_MESSAGE_LOG.USER_ID=? and TB_MESSAGE_LOG.MESSAGE_ID=TB_MESSAGE.MESSAGE_ID),RECEIVE_STATUS) as  RECEIVE_STATUS ,"
				+ "SEND_TIME,READ_TIME, MEMO FROM TB_MESSAGE) a where 1=1";
		if(type == MessageConst.SYSTEM){//系统消息 取最近3个月
			sql += " and type = ? and (RECEIVER = 0 or RECEIVER = "+userId+") and SEND_TIME >= " + dayoffset;
		}else{//学校
			sql += " and type = ?";
		}
		sql += " and school_id in ("+schoolIds+") and RECEIVE_STATUS!=? ORDER BY RECEIVE_STATUS ASC,SEND_TIME DESC";
		return findPageForMap(sql, page, size, userId, type,
				MessageConst.MESSAGE_RECEIVE_DELETE);
	}
	
	@Override
	public Page<Message> schoolAnnounce(long schoolId, long page, long size) {
		return findPage(
				"select * from TB_MESSAGE where SCHOOL_ID=? and type=? and SEND_STATUS !=? ORDER BY SEND_TIME DESC",
				page, size, schoolId, MessageConst.SCHOOL, MessageConst.MESSAGE_SEND_DELETE);
	}
	
	@Override
	public List<Map<String, Object>> getSystemMessageCount(long userId, long schoolId) {
	    String sql = "SELECT count(1) as COUNT, TYPE from(";
	    	   sql += " SELECT CONCAT('M',TYPE) TYPE from TB_MESSAGE  where  TB_MESSAGE.RECEIVE_STATUS=?";
	    	   sql += " and not EXISTS(select 1 from TB_MESSAGE_LOG where TB_MESSAGE_LOG.USER_ID=? and TB_MESSAGE_LOG.MESSAGE_ID=TB_MESSAGE.MESSAGE_ID) "; 
	    	   sql += " and (RECEIVER = 0 or RECEIVER = ?)"; 
	    	   //sql += " and TB_MESSAGE.SCHOOL_ID=0"; 提示未读学校消息暂时不做
	    	   sql += " UNION ALL SELECT CONCAT('@',TYPE) TYPE FROM TB_AT  WHERE USER_ID = ? AND STATUS = ?"; 
	    	   sql += " ) a GROUP BY TYPE";
		return findForMap(sql,MessageConst.MESSAGE_NO_READ, userId, userId, userId, AtConst.NO_READ);
	}

	@Override
	public List<Message> announce(long schoolId, int nums) {
		return find(
				"select * from TB_MESSAGE where type!=? and school_id=?  ORDER BY  send_time DESC limit 0,?",
				MessageConst.PRIVATE, schoolId, nums);
	}

	@Override
	public int deleteAnnounce(long schoolId, long messageId, long userId) {
		return update(
				"update TB_MESSAGE set send_status=?   where message_id=? and school_id=? ",
				MessageConst.MESSAGE_SEND_DELETE, messageId, schoolId);
	}

	@Override
	public int deleteAnnounce(long schoolId, long messageId) {
		return update(
				"update TB_MESSAGE set send_status=?   where message_id=? and school_id=? ",
				MessageConst.MESSAGE_SEND_DELETE, messageId, schoolId);
	}

	@Override
	public List<Message> now(long userId, long friend){
		String invalue1 = MessageConst.PRIVATE + "";
		String invalue2 = MessageConst.PRIVATE + "," + MessageConst.ROOM_INVITE + "," + MessageConst.ROOM_REPLY + "," + MessageConst.QUESRION_ANSWER + "," + MessageConst.QUESRION_ANSWER_INSTANCE + "," + MessageConst.QUESRION_ANSWER_SELECT;
		return this
				.find("select * from TB_MESSAGE  where ( (sender=?  and receiver=?  and SEND_STATUS != ? and type in(" + invalue1 + ")) or  (sender=?  and receiver=?  and receive_status != ? and type in(" + invalue2 + "))) and (SEND_TIME>? or receive_status=? )  order by SEND_TIME  ",
						userId, friend, MessageConst.MESSAGE_SEND_DELETE, friend, userId, MessageConst.MESSAGE_RECEIVE_DELETE,
						DateHelper.getTime(new Date(), 0, 0, 0, -24, 0, 0),
						MessageConst.MESSAGE_NO_READ
						);
	}

	@Override
	public List<Map<String, Object>> recentUsers(long userId) {
		String invalue1 = MessageConst.PRIVATE + "";
		String invalue2 = MessageConst.PRIVATE + "," + MessageConst.ROOM_INVITE + "," + MessageConst.ROOM_REPLY + "," + MessageConst.QUESRION_ANSWER + "," + MessageConst.QUESRION_ANSWER_INSTANCE + "," + MessageConst.QUESRION_ANSWER_SELECT;
		int dayoffset = DateHelper.getTime(new Date(), 0, 0, -7, 0, 0, 0);
		StringBuffer sql = new StringBuffer();
		sql.append("select b.USER_ID,IFNULL(b.REAL_NAME,b.`NAME`) name,b.FACE,b.ONLINE,");
		sql.append("(select count(*) from TB_MESSAGE t where t.RECEIVE_STATUS = ? and t.SENDER = a.USER_ID and t.RECEIVER = ? and t.type in (" + invalue2 + ")) NOTREAD_COUNT ");
		sql.append("FROM(select DISTINCT(uid) USER_ID,MAX(t.SEND_TIME) mtime from(select RECEIVER uid,SEND_TIME from TB_MESSAGE  where sender=? and SEND_TIME>?  and type in (" + invalue1 + ") UNION ALL ");
		sql.append("select sender uid,SEND_TIME from TB_MESSAGE  where receiver=? and SEND_TIME>? and receive_status != ? and type in (" + invalue2 + ") UNION ALL select sender uid,SEND_TIME from TB_MESSAGE  where RECEIVE_STATUS = ? and RECEIVER = ? and type in(" + invalue2 + ")) t GROUP BY uid ORDER BY  mtime desc )  a,TB_USER b where a.USER_ID=b.USER_ID");
		return this.findForMap(sql.toString(), MessageConst.MESSAGE_NO_READ,
				userId, userId, dayoffset, userId, dayoffset, MessageConst.MESSAGE_RECEIVE_DELETE, MessageConst.MESSAGE_NO_READ,
				userId);
	}
	
	@Override
	public Page<Map<String, Object>> getMes(long userId, long anotherId, byte type,
			long page, long size) {
		String sql = "select t.MESSAGE_ID,t.MESSAGE,t.SENDER,t.RECEIVER,t.SEND_TIME,t.RECEIVE_STATUS";
		sql += " from TB_MESSAGE t";
		sql += " where t.TYPE = ? and (t.SENDER = ? and t.RECEIVER = ? and t.SEND_STATUS != ?) or (t.SENDER = ? and t.RECEIVER = ? and t.RECEIVE_STATUS != ?) and t.type = ? order by t.SEND_TIME desc";
		return findPageForMap(sql, page, size, MessageConst.PRIVATE, userId,
				anotherId, MessageConst.MESSAGE_SEND_DELETE, anotherId, userId, MessageConst.MESSAGE_RECEIVE_DELETE, MessageConst.PRIVATE);
	}

	@Override
	public long deleteMessageToMy(long userId, long other){
		return update(
				"UPDATE  TB_MESSAGE SET  receive_status = ? WHERE sender = ? and receiver = ?", MessageConst.MESSAGE_RECEIVE_DELETE, other, userId);
	}

	@Override
	public long deleteMessageToOther(long userId, long other) {
		return update(
				"UPDATE  TB_MESSAGE SET  send_status = ? WHERE sender = ? and receiver = ?", MessageConst.MESSAGE_RECEIVE_DELETE, userId, other);
	}

}
