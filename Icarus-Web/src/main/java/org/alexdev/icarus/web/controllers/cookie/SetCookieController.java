package org.alexdev.icarus.web.controllers.cookie;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.alexdev.icarus.web.routes.manager.Route;
import org.alexdev.icarus.web.server.response.WebResponse;

public class SetCookieController implements Route {
    @Override
    public FullHttpResponse handleRoute(FullHttpRequest request, Channel channel) {
        return null;
    }
}