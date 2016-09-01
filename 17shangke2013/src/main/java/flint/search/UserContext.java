package flint.search;

public interface UserContext {
	/**
	 * 用户是否在线
	 * 
	 * @param uid
	 * @return
	 */
	boolean isOnline(String uid);
}
