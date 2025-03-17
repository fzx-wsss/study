package com.wsss.frame.netty.webSocket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

import java.util.List;

public class CustomWebSocketServerProtocolHandler extends WebSocketServerProtocolHandler {
    public CustomWebSocketServerProtocolHandler(String websocketPath,boolean allow) {
        super(websocketPath,allow);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> out) throws Exception {
        if(frame instanceof PingWebSocketFrame || frame instanceof PongWebSocketFrame) {
            ctx.fireUserEventTriggered("123");
        }
        super.decode(ctx, frame, out);
    }
}
