package org.alexdev.icarus.server.netty;

import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.IPlayerNetwork;
import org.jboss.netty.channel.Channel;

public class NettyPlayerNetwork extends IPlayerNetwork {

	private Channel channel;

	public NettyPlayerNetwork(Channel channel, int connectionId) {
		super(connectionId);
		this.channel = channel;
	}

	@Override
	public void close() {
		channel.close();
	}

	@Override
	public void send(OutgoingMessageComposer response) {
		channel.write(response);
		
	}
}
