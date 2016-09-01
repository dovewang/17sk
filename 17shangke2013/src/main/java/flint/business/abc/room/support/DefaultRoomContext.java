package flint.business.abc.room.support;

import static kiss.util.Helper.$;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import kiss.security.Identity;
import kiss.util.Q;
import entity.Room;
import flint.business.abc.room.RoomService;
import flint.business.abc.room.context.RoomContext;
import flint.business.abc.room.context.RoomSession;
import flint.business.constant.ClassroomConst;
import flint.exception.ApplicationException;

/**
 * 建立某个教室的环境
 * 
 * @author Dove Wang
 * 
 */
public class DefaultRoomContext implements RoomContext {
	private Room room;

	private RoomService service;

	private Map<Long, RoomSession> sessions = new ConcurrentHashMap<Long, RoomSession>();

	private Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();

	private long createTime;

	private long recordStartTime;// 录制开始时间

	public DefaultRoomContext(Room room, RoomService service) {
		this.room = room;
		this.service = service;
		createTime = new Date().getTime();
	}

	@Override
	public long getCreationTime() {
		return createTime;
	}

	@Override
	public long getDestroyTime() {
		return 0;
	}

	public long getRecordStartTime() {
		return recordStartTime;
	}

	public void setRecordStartTime(long recordStartTime) {
		this.recordStartTime = recordStartTime;
	}

	@Override
	public int getOnlines() {
		return sessions.size();
	}

	@Override
	public Room getRoom() {
		return room;
	}

	@Override
	public RoomSession getSession(long userId) {
		RoomSession session = sessions.get(userId);
		if (session == null) {
			session = new DefaultRoomSession(this);
			session.setNew(true);
			sessions.put(userId, session);
		} else {
			session.setNew(false);
			session.setUserId(userId);
			session.setLastAccessedTime(new Date().getTime());// 最后访问时间
		}
		return session;
	}

	@Override
	public void removeSession(long userId) {
		RoomSession session = sessions.remove(userId);
		session.setLastAccessedTime(new Date().getTime());
		service.logout(session);
	}

	@Override
	public RoomSession authenticate(String netAddress, Identity user,
			String password) throws RoomException, ApplicationException {
		long userId = Long.parseLong(user.getId());
		if (room.getCapacity() == getOnlines()) {
			throw new RoomException("很抱歉，该教室的容量已满", ClassroomConst.CAPACITY_MAX);
		}
		/* 先检查密码是否正确 */
		if ($(room.getPassword()).isEmpty()
				|| room.getPassword().equals(password)) {
			RoomSession session = getSession(userId);
			if (!session.isNew() && session.isAuthenticated()) {
				throw new RoomException("很抱歉，该用户已在另一位置登录",
						ClassroomConst.USER_HAS_LOGIN);
			}
			/* 主持人直接授权通过,这里可能有临时进入课室的用户 */
			if (userId == room.getHost()
					|| service.authenticate(this, user, netAddress)) {
				session.setStatus(ClassroomConst.USER_OK);
				session.setName(user.getName());
				session.setFace((String) user.getAttribute("face"));
				if (userId == room.getHost())
					session.setHost(true);
				session.setAuthenticated(true);
			} else {
				sessions.remove(userId);
				throw new RoomException("很抱歉，您没有安排该课程，所有您不能进入教室哦",
						ClassroomConst.USER_NOT_EXISTS);
			}
			return session;
		} else {
			/* 返回空值代表登录需要密码 */
			if ($(password).isEmpty())
				throw new RoomException("需要输入登录密码",
						ClassroomConst.USER_PASSWORD_NEED);
			else
				throw new RoomException("教室登录密码错误",
						ClassroomConst.USER_PASSWORD_ERROR);
		}

	}

	@Override
	public List<RoomSession> getSessions() {
		List<RoomSession> list = new ArrayList<RoomSession>();
		list.addAll(sessions.values());
		Collections.sort(list, new Comparator<RoomSession>() {
			@Override
			public int compare(RoomSession o1, RoomSession o2) {
				return (int) (o1.getCreationTime() - o2.getCreationTime());
			}

		});
		return list;
	}

	@Override
	public void destory() {
		RoomSession host = null;
		for (RoomSession session : sessions.values()) {
			if (session.isHost()) {
				host = session;
			}
			session.invalidate();// 销毁所有用户的连接
		}
		room.setStartTime((int) (getRecordStartTime() / 1000));
		room.setEndTime(Q.now());
		service.close(room, host);
		sessions = null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAttribute(String attribute) {
		return (T) attributes.get(attribute);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T removeAttribute(String attribute) {
		return (T) attributes.remove(attribute);
	}

	@Override
	public void setAttribute(String attribute, Object value) {
		attributes.put(attribute, value);
	}

}
