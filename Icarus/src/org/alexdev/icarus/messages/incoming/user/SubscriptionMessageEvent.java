package org.alexdev.icarus.messages.incoming.user;

import org.alexdev.icarus.dao.mysql.ClubDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class SubscriptionMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        long[] subscriptionData = ClubDao.getSubscription(player.getDetails().getId());
        
        if (subscriptionData != null) {
            player.getSubscription().update(player.getDetails().getId(), subscriptionData[0], subscriptionData[1]);
            
        }
        
        player.getSubscription().sendSubscriptionStatus();
        
    }

}