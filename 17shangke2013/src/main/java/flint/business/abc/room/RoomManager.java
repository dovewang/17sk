package flint.business.abc.room;

import static kiss.util.Helper.$;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import kiss.security.Identity;
import kiss.util.Q;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Room;
import flint.business.abc.dao.AbcDAO;
import flint.business.abc.room.context.RoomContext;
import flint.business.abc.room.context.RoomSession;
import flint.business.abc.room.support.DefaultRoomContext;
import flint.business.abc.room.support.RoomException;
import flint.business.constant.ClassroomConst;
import flint.context.ConfigHelper;
import flint.exception.ApplicationException;

@Service
public class RoomManager implements InitializingBean {

	private final static Logger logger = LoggerFactory
			.getLogger(RoomManager.class);

	private Map<String, RoomService> roomType;

	@Autowired
	public AbcDAO dao;

	@Autowired
	private TempRoom tempRoom;

	@Autowired
	private QuestionRoom questionRoom;

	@Autowired
	private CourseRoom courseRoom;

	@Autowired
	private ShowRoom showRoom;

	@Autowired
	private TutorRoom tutorRoom;

	@Autowired
	private ConfigHelper configHelper;

	@Override
	public void afterPropertiesSet() throws Exception {
		roomType = new HashMap<String, RoomService>();
		roomType.put("t0", tempRoom);
		roomType.put("t1", questionRoom);
		roomType.put("t2", courseRoom);
		roomType.put("t3", showRoom);
		roomType.put("t4", tutorRoom);
	}

	/**
	 * 存储当前服务器的登录信息
	 */
	Map<Long, RoomContext> contexts = new ConcurrentHashMap<Long, RoomContext>();

	/**
	 * 返回值，可能会为空，代表课程已取消、结束，或课程状态不正常
	 * 
	 * @param roomId
	 * @return
	 * @throws RoomException
	 */
	public RoomContext get(long roomId) throws RoomException {
		RoomContext context = contexts.get(roomId);
		if (context == null) {
			Room room = dao.findById(roomId);
			if (room != null) {
				context = new DefaultRoomContext(room, roomType.get("t"
						+ room.getSubjectType()));
				/* 正在上课的课程才放入内存，非正常状态的直接返回context值 */
				if (room.getStatus() == ClassroomConst.CLASS_OK)
					contexts.put(roomId, context);
			}
		}

		if (context == null) {
			throw new RoomException("您所访问的教室不存在!",
					ClassroomConst.CLASS_NOT_EXISTS);
		}
		return context;
	}

	public Room getRoom(long roomId) throws RoomException {
		RoomContext c = this.get(roomId);
		return c.getRoom();
	}

	public Room create(Identity user, byte orgType, long subjectId,
			String subject, byte subjectType, int price, String password,
			String netAddress) throws ApplicationException {
		/* 先检查课程的教室是否已经创建，如果已经创建则不再生成新的教室 */
		Room checkRoom = checkRoom(subjectId, subjectType,
				Long.parseLong(user.getId()));
		if (checkRoom != null) {
			logger.debug("room was created!now go !");
			return checkRoom;
		}
		logger.debug("create a room,waiting please.....");
		RoomService service = roomType.get("t" + subjectType);
		logger.debug("user room service:" + service.getClass());
		Room room = new Room();
		room.setRoomId(Long.parseLong(dao.getDateSerialNumber("room")));
		room.setOrgId(String.valueOf(user.getDomainId()));
		room.setUserId($(user.getId()).toLong());
		room.setCreateTime(Q.now());
		room.setSubjectType(subjectType);
		room.setSubjectId(subjectId);
		room.setSubject(subject);
		room.setNetAddress(netAddress);
		room.setPrice(price);
		room.setStatus(ClassroomConst.CLASS_OK);
		room = service.create(room, user);
		logger.debug("create a room success!");
		return room;
	}

	private Room checkRoom(long subjectId, byte subjectType, long userId) {
		for (Map.Entry<Long, RoomContext> mrc : contexts.entrySet()) {
			RoomContext rc = mrc.getValue();
			Room room = rc.getRoom();
			if (room.getSubjectId() == subjectId
					&& room.getSubjectType() == subjectType) {
				if (subjectType == ClassroomConst.ROOM_COURSE) {
					return room;
				} else {
					/* 适用于临时教室，如问题等，只需判断 */
					/* 允许多次解答 */
					// if (userId == room.getHost()) {
					// return room;
					// }
				}
			}
		}
		return null;
	}

	public RoomSession login(String netAddress, Identity user, long roomId,
			String password) throws RoomException, ApplicationException {
		RoomContext c = this.get(roomId);
		return c.authenticate(netAddress, user, password);
	}

	public RoomSession getSession(long roomId, long userId, String token)
			throws RoomException {
		RoomContext c = this.get(roomId);
		RoomSession session = c.getSession(userId);
		if (c.getRoom().getStatus() == ClassroomConst.CLASS_OK
				&& !session.getId().equals(token)) {
			throw new RoomException("用户身份不符!", ClassroomConst.USER_TOKEN_ERROR);
		}
		return session;
	}

	/**
	 * 关闭某个房间
	 * 
	 * @param roomId
	 * @throws RoomException
	 */
	public void close(long roomId) throws RoomException {
		RoomContext c = get(roomId);
		if (c != null) {
			c.destory();
			contexts.remove(roomId);
		}

	}

	/**
	 * 回放权限检查
	 * 
	 * @param roomId
	 * @param userId
	 * @param allowPlay
	 *            是否允许自动播放
	 * @param allowPay
	 *            是否可以付费后播放
	 * @param netAddress
	 *            用戶ip地址
	 * @return
	 * @throws ApplicationException
	 */
	public Room playback(long roomId, long userId, boolean allowPlay,
			boolean allowPay, String netAddress) throws ApplicationException {
		Room room = dao.findById(roomId);
		RoomService service = roomType.get("t" + room.getSubjectType());
		/* 免费或者获得授权的视频，允许用户播放 */
		if (allowPlay || room.getPrice() == 0
				|| service.playback(room, userId, allowPay, netAddress)) {
			return room;
		}
		return null;
	}

	public void recordStart(long roomId, String token) throws RoomException {
		RoomContext c = this.get(roomId);
		c.setRecordStartTime(new Date().getTime());
	}

	public void record(long roomid, String data, String remoteAddr, long userId) {
		dao.record(roomid, data, remoteAddr, userId);
	}

	public String getConfig(long id, Identity user) throws RoomException {
		long userId = Long.parseLong(user.getId());
		RoomBuilder builder = new RoomBuilder();
		RoomContext c = this.get(id);
		RoomSession session = c.getSession(userId);
		Room room = c.getRoom();
		String token = session.getId();// 在flash中使用该ID对用户的session进行验证
		builder.setId(room.getRoomId());
		builder.setName(room.getSubject());
		builder.setType(room.getSubjectType());
		builder.setCapacity(room.getCapacity());
		builder.setPlayer(userId, user.getName(), token,
				(String) Q.when(room.getHost(), userId, "teacher", "student"),
				(String) user.getAttribute("face"));

		List<RoomSession> ls = c.getSessions();
		for (RoomSession s : ls) {
			builder.addUser(s.getUserId(), s.getName(), null, s.getStatus(),
					s.isHost(), s.getFace());
		}
		return builder.build(configHelper.getString("fms", "server",
				"rtmp://113.108.203.135/saimo/"));
	}

}
