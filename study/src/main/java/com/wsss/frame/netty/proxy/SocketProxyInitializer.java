package com.wsss.frame.netty.proxy;

import com.wsss.frame.netty.simple.NettyServerHandler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class SocketProxyInitializer extends ChannelInitializer<SocketChannel> {
	public void initChannel(SocketChannel ch) {
		ch.pipeline().addLast(new ChannelHandler[] { new LoggingHandler(LogLevel.INFO), new SocketProxyFrontendHandler() });
	}
}

/*
 * Location: C:\Users\hasee\Desktop\文档\proxy\lib\proxy-0.0.1-SNAPSHOT\ Qualified
 * Name: com.lefu.proxy.socket.SocketProxyInitializer JD-Core Version: 0.6.0
 */