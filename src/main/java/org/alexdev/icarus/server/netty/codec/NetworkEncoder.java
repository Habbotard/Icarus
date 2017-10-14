/*
 * Copyright (c) 2012 Quackster <alex.daniel.97@gmail>. 
 * 
 * This file is part of Sierra.
 * 
 * Sierra is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Sierra is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Sierra.  If not, see <http ://www.gnu.org/licenses/>.
 */

package org.alexdev.icarus.server.netty.codec;

import java.nio.charset.Charset;

import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.util.Util;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkEncoder extends SimpleChannelHandler {

	final private static Logger log = LoggerFactory.getLogger(NetworkEncoder.class);
	
    @Override
    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) {

        try {
            
            if (!ctx.getChannel().isOpen()) {
                return;
            }

            if (e.getMessage() instanceof String) {
                Channels.write(ctx, e.getFuture(), ChannelBuffers.copiedBuffer((String) e.getMessage(), Charset.forName("ISO-8859-1")));
                return;
            }

            if (e.getMessage() instanceof MessageComposer) {

                MessageComposer msg = (MessageComposer) e.getMessage();
                if (!msg.getResponse().isFinalised()) {
                    msg.write();
                }

                if (Util.getServerConfig().get("Logging", "log.sent.packets", Boolean.class)) {
                    log.info("SENT: {} / {}", msg.getResponse().getHeader(), msg.getResponse().getBodyString());
                }

                Channels.write(ctx, e.getFuture(), ChannelBuffers.copiedBuffer((ChannelBuffer)msg.getResponse().get()));
                return;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
