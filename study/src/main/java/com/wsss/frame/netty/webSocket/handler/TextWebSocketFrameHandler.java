package com.wsss.frame.netty.webSocket.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    /**
     * @param channelHandlerContext
     * @param webSocketFrame
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        Channel incoming = ctx.channel();

        //处理消息
        if (msg instanceof CloseWebSocketFrame) {
            System.out.println("Client:" + incoming.remoteAddress() + " close ->");
            incoming.close();
        }

        if (msg instanceof PongWebSocketFrame) {
            String message = msg.copy().toString();
            System.out.println("Client:" + incoming.remoteAddress() + " message ->" + message);
        }

        if (msg instanceof TextWebSocketFrame) {
            String message = ((TextWebSocketFrame) msg).text();
            System.out.println("Client:" + incoming.remoteAddress() + " message ->" + message);
        }
    }
}
