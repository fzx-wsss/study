package com.wsss.frame.netty.webSocket.handler;

import com.wsss.frame.netty.webSocket.test.GzipUtils;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    public static SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
    /**
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

        if (msg instanceof PongWebSocketFrame || msg instanceof PingWebSocketFrame) {
//            String message = msg.copy().toString();
            System.out.println("Client:" + incoming.remoteAddress() + " message ->" + msg.getClass());
        }

        if (msg instanceof TextWebSocketFrame) {
            String message = ((TextWebSocketFrame) msg).text();
            System.out.println(getTime() + "Client:" + incoming.remoteAddress() + "Text message ->" + message);
        }

        if (msg instanceof BinaryWebSocketFrame) {
            byte[] message = ByteBufUtil.getBytes(((BinaryWebSocketFrame) msg).content());
            String s = new String(GzipUtils.decompress(message));
            System.out.println(getTime() + "Binary message ->" + s);
        }
//        TimeUnit.SECONDS.sleep(1000);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("user event:"+ evt);
        if(evt instanceof WebSocketServerProtocolHandler.HandshakeComplete){
            WebSocketServerProtocolHandler.HandshakeComplete complete = (WebSocketServerProtocolHandler.HandshakeComplete)evt;
            System.out.println(complete.requestUri());
        }
        super.userEventTriggered(ctx, evt);
    }

    private String getTime() {
        return sbf.format(new Date());
    }
}
