package flint.business.abc.room;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dove Wang
 * 
 */
public class RoomBuilder {
	private long id;
	private String name = "";
	private byte type;
	private int capacity;
	private int time = 1200;// 单位是秒，默认20分钟

	private StringBuilder sb = new StringBuilder();
	private StringBuilder u = new StringBuilder();
	private List<StringBuilder> ulist = new ArrayList<StringBuilder>();

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setPlayer(long id, String name, String token, String role,
			String face) {
		u = new StringBuilder();
		u.append("<player>");
		u.append("<id>" + id + "</id>");
		u.append("<name>" + name + "</name>");
		u.append("<pic>" + face + "</pic>");
		u.append("<cs>" + token + "</cs>");
		u.append("</player> ");
		u.append("<degree>" + role + "</degree>");
	}

	public void addUser(long id, String name, String role, byte status,
			boolean host, String face) {
		StringBuilder sb = new StringBuilder();
		sb.append("<user id=\"" + id + "\"");
		sb.append("  name=\"" + name + "\" ");
		sb.append("  role=\"" + role + "\"    ");
		sb.append("  status=\"" + status + "\" ");
		sb.append("  host=\"" + (host ? "1" : "0") + "\" ");
		sb.append("  face=\"" + face + "\" />  ");
		ulist.add(sb);
	}

	public String build(String fms) {
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
		sb.append("<data>");
		sb.append("<room>");
		sb.append("<id>" + id + "</id>");
		sb.append("<name>" + name + "</name>");
		sb.append("<type>0</type>");
		sb.append("<capacity>" + capacity + "</capacity>");
		sb.append("<time>" + time + "</time>");
		sb.append("</room>");
		sb.append(u);
		sb.append("<fms>" + fms + id + "/</fms>");
		sb.append("<recordstudent>true</recordstudent>");
		sb.append("<beurl>/room/</beurl>");
		sb.append("<picupurl>/abc/camera.html</picupurl>");
		sb.append("<recordbegin>/abc/record-start.html</recordbegin>");
		sb.append("<recordend>/abc/close.html</recordend>");
		sb.append("<recorditem>/abc/record.html</recorditem>");

		if (ulist.size() > 0) {
			sb.append("<list>");
			for (StringBuilder s : ulist) {
				sb.append(s);
			}
			sb.append("</list>");
		}
		sb.append("</data>");
		return sb.toString();
	}
}
