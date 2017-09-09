package org.alexdev.icarus.server.netty.connections;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.server.netty.NettyPlayerNetwork;
import org.jboss.netty.channel.Channel;

public class SessionManager {
    
    private ConcurrentMap<Integer, Player> sessions;

    public SessionManager() {
        sessions = new ConcurrentHashMap<Integer, Player>();
    }
    
    public boolean addSession(Channel channel) {
        
        Player player = new Player(new NettyPlayerNetwork(channel, channel.getId()));
        channel.setAttachment(player);

        return sessions.putIfAbsent(channel.getId(), player) == null;
    }

    public void removeSession(Channel channel) { 
       
        try {
            sessions.remove(channel.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasSession(Channel channel) {
        return sessions.containsKey(channel.getId());
    }

    public ConcurrentMap<Integer, Player> getSessions() {
        return sessions;
    }
}
