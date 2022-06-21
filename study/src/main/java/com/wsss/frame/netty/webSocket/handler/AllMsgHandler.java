package com.wsss.frame.netty.webSocket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class AllMsgHandler extends SimpleChannelInboundHandler<Object> {
    /**
     * @param channelHandlerContext
     * @param o
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        System.out.println(o.getClass().getName() + ":" + o.toString());
    }
}
