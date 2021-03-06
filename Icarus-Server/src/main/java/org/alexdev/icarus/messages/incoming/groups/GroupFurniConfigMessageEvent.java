package org.alexdev.icarus.messages.incoming.groups;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.catalogue.GroupFurniConfigMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class GroupFurniConfigMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        player.send(new GroupFurniConfigMessageComposer(player.getAdministrativeGroups()));
    }
}
