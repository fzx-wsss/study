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

public class TextWebSocketFrameHandler2 extends SimpleChannelInboundHandler<WebSocketFrame> {
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

        if (msg instanceof PongWebSocketFrame) {
            String message = msg.copy().toString();
            System.out.println("Client:" + incoming.remoteAddress() + " message ->" + message);
        }

        if (msg instanceof TextWebSocketFrame) {
            String message = ((TextWebSocketFrame) msg).text();
            System.out.println(getTime() + "Client:" + incoming.remoteAddress() + " message ->" + message);
        }

        if (msg instanceof BinaryWebSocketFrame) {
            byte[] message = ByteBufUtil.getBytes(((BinaryWebSocketFrame) msg).content());
            System.out.println(getTime() + " message ->" + new String(GzipUtils.decompress(message)));
        }
        TimeUnit.SECONDS.sleep(30);
    }

    private String getTime() {
        return sbf.format(new Date());
    }
}
