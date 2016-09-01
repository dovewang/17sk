package flint.business.abc.room;

import kiss.security.Identity;

import org.springframework.stereotype.Service;

import flint.business.abc.room.context.RoomContext;

@Service
public class ShowRoom extends TempRoom {


	/* 单人秀，不允许其他人进入 */
	@Override
	public boolean authenticate(RoomContext context,  Identity user, String netAddres) {
		return false;
	}
}
