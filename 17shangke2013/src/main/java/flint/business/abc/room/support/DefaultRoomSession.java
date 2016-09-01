package flint.business.abc.room.support;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import kiss.util.Q;
import flint.business.abc.room.context.RoomContext;
import flint.business.abc.room.context.RoomSession;

public class DefaultRoomSession implements RoomSession {

	private RoomContext context;

	private String id;

	private long lastAccessedTime;

	private boolean isNew;

	private boolean authenticated;

	private long userId;

	private byte status;

	private String name;// 用户名称
	private boolean host;// 是否是主持人
	private String face;// 用户头像

	private Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();

	public DefaultRoomSession(RoomContext roomContext) {
		context = roomContext;
		id = Q.uuid();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public long getCreationTime() {
		return new Date().getTime();
	}

	@Override
	public long getLastAccessedTime() {
		return lastAccessedTime;
	}

	@Override
	public void setLastAccessedTime(long lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}

	@Override
	public RoomContext getRoomContext() {
		return context;
	}

	@Override
	public void setNew(boolean isNew) {
		this.isNew = isNew;

	}

	@Override
	public boolean isNew() {
		return isNew;
	}

	@Override
	public void invalidate() {
		context.removeSession(userId);
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

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isHost() {
		return host;
	}

	public void setHost(boolean host) {
		this.host = host;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

}
