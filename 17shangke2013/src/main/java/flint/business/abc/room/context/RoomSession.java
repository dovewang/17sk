package flint.business.abc.room.context;

public interface RoomSession {

	/* sessionId */
	String getId();

	long getUserId();

	void setUserId(long userId);

	byte getStatus();

	void setStatus(byte status);
	
	 String getName();

	 void setName(String name) ;

	 boolean isHost();

	 void setHost(boolean host);

	 String getFace();

	 void setFace(String face) ;

	long getCreationTime();

	long getLastAccessedTime();

	RoomContext getRoomContext();

	boolean isNew();

	void invalidate();

	<T> T getAttribute(String attribute);

	void setAttribute(String attribute, Object value);

	<T> T removeAttribute(String attribute);

	void setLastAccessedTime(long lastAccessedTime);

	void setNew(boolean isNew);

	boolean isAuthenticated();

	void setAuthenticated(boolean authenticated);

	
}
