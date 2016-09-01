package flint.business.abc.room;

import kiss.security.Identity;
import flint.business.abc.room.context.RoomConfig;
import flint.business.abc.room.context.RoomContext;
import flint.business.abc.room.support.RoomException;
import flint.exception.ApplicationException;

public class OrderRoom extends RoomService {

	@Override
	public RoomConfig getRoomConfig() {
		return null;
	}

	@Override
	public boolean authenticate(RoomContext context, Identity user,
			String netAddres) throws RoomException, ApplicationException {
		return false;
	}
}
