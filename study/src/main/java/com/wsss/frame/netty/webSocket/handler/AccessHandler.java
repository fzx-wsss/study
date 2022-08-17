package com.wsss.frame.netty.webSocket.handler;

import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AccessHandler extends ChannelInboundHandlerAdapter {
    private static ByteBuf msg = Unpooled.wrappedBuffer("The number of connections reached the upper limit.".getBytes(StandardCharsets.UTF_8));

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            HttpHeaders headers = ((FullHttpRequest) msg).headers();
            String ip = headers.get("X-Real-IP");
            System.out.println("Realip:" + ip);
            msgAndClose(ctx.channel());
            return;
        }
        System.out.println("msg:" + (msg instanceof FullHttpRequest));
        super.channelRead(ctx, msg);
    }
    public void msgAndClose(Channel channel) {
        HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN,msg.retainedDuplicate());

        ChannelFuture future = channel.writeAndFlush(response);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                future.channel().close();
            }
        });
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}
