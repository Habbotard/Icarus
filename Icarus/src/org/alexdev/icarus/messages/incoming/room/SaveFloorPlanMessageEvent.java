package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class SaveFloorPlanMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, ClientMessage reader) {
		
		String heightmap = reader.getMessageBody().trim();
		
		System.out.println("heightmap: " + heightmap);
	}

}
