package org.alexdev.icarus.messages.incoming.handshake;

import org.alexdev.icarus.dao.mysql.PlayerDao;
import org.alexdev.icarus.dao.mysql.RoomDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.handshake.AuthenticationOKMessageComposer;
import org.alexdev.icarus.messages.outgoing.handshake.AvailabilityMessageComposer;
import org.alexdev.icarus.messages.outgoing.handshake.UniqueMachineIDMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.HomeRoomMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.LandingWidgetMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class AuthenticateMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, ClientMessage request) {

		String sso = request.readString();
		
		boolean loginSuccess = PlayerDao.login(player, sso);
		
		if (!loginSuccess) {
			player.getNetwork().close();
			return;
		}
		
		if (player.getMachineId() == null) {
			player.getNetwork().close();
			return;
		}
		
		if (PlayerManager.checkForDuplicates(player)) {
			player.getNetwork().close();
			return;
		}
		
		Log.println("SSO ticket: " + sso);
        
        
		
		player.send(new UniqueMachineIDMessageComposer(player.getMachineId()));
		player.send(new AuthenticationOKMessageComposer());
		player.send(new HomeRoomMessageComposer(2, false));
		player.send(new LandingWidgetMessageComposer());
		player.send(new AvailabilityMessageComposer());
		
		RoomDao.getPlayerRooms(player.getDetails(), true); // load de rooms!
		
		player.getInventory().init(); // load de items!
	}
}