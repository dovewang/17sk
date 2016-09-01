package flint.business.user.dao;

import java.util.List;
import java.util.Map;

import entity.Message;
import flint.base.BaseDAO;
import flint.common.Page;

/**
 * 
 * @author Dove Wang
 * 
 */
public interface MessageDAO extends BaseDAO<Message, Long> {

	/**
	 * 统计未读消息
	 * 
	 * @param userId
	 * @return
	 */
	long statUnread(long userId);

	/**
	 * 统计未发送的消息
	 * 
	 * @param userId
	 * @return
	 */
	long statUnsend(long userId);

	int deleteSendstatus(long id);
	
	int deleteReceivestatus(long messageId, long userid);
	
	/**
	 * 批量删除他人发送给我的信息
	 * 
	 * @param userId
	 * @return
	 */
	long deleteMessageToMy(long userId, long other);
	
	/**
	 * 批量删除我发送给他人的信息
	 * 
	 * @param userId
	 * @return
	 */
	long deleteMessageToOther(long userId, long other);

	int read(String ids, int type, long userid);

	/**
	 * 标记收件箱邮件状态
	 * 
	 * @param messageId
	 * @param userId
	 * @param status
	 * @return
	 */
	int flag(long messageId, long userId, byte status);
	
	/**
	 * 根据发送者和接收者互通消息标记消息的状态为已读 
	 * 
	 * @param messageId
	 * @param userId
	 * @param status
	 * @return
	 */
	int readAllbySenderAndReceiver(long sender,long receiver);

	/**
	 * 收件箱
	 * 
	 * @param userId
	 * @return
	 */
	Page<Message> inbox(long userId, byte type, long page, long size);
	
	/**
	 * 获取联系发件与收件的最新信息
	 * 
	 * @param userId
	 * @return
	 */
	Page<Map<String,Object>> allbox(long userId, long page, long size);
	
	/**
	 * 获取与他人的聊天信息(基于分页)
	 * 
	 * @param userId
	 * @param anotherId
	 * @return
	 */
	Page<Map<String,Object>> getMes(long userId, long anotherId,byte type, long page, long size);

	/**
	 * 系统消息，其他是学校消息(个人获取系统、私人、学校消息)
	 * 
	 * @param schoolId
	 * @param type
	 * @param page
	 * @param size
	 * @return
	 */
	Page<Map<String, Object>> announce(long userId, String schoolIds, byte type, long page, long size);

	/**
	 * 学校后台获取公告消息
	 * 
	 * @param schoolId
	 * @param page
	 * @param size
	 * @return
	 */
	Page<Message> schoolAnnounce(long schoolId, long page, long size);
	
	/**
	 * 取得某个学校的最新公告
	 * 
	 * @param schoolId
	 * @param nums
	 * @return
	 */
	List<Message> announce(long schoolId, int nums);

	/**
	 * 发件箱
	 * 
	 * @param userId
	 * @return
	 */
	Page<Message> outbox(long userId, long page, long size);

	/**
	 * 草稿箱
	 * 
	 * @param userId
	 * @return
	 */
	Page<Message> drafts(long userId, long page, long size);

	int deleteAnnounce(long schoolId, long messageId, long userId);

	int deleteAnnounce(long schoolId, long messageId);

	List<Message> now(long userId, long friend);

	List<Map<String, Object>> recentUsers(long userId);
	
	/**
	 * 获取用户未读的系统消息数量和学校消息的数量
	 * 
	 * @param userId
	 * @param schoolId
	 * @return
	 */
	List<Map<String, Object>> getSystemMessageCount(long userId, long schoolId);
}
