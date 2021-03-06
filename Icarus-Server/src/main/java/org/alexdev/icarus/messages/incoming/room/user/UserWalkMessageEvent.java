package org.alexdev.icarus.messages.incoming.room.user;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class UserWalkMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {

        int X = request.readInt();
        int Y = request.readInt();

        player.getRoomUser().walkTo(X, Y);
    }
}
