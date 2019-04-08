package com.wsss.frame.netty.proxy;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public final class SocketProxyServer {
	public static void main(String[] args) throws Exception {
		
		init(8443);
	}
	public static void init(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup(1);
		try {

			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				//.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new SocketProxyInitializer())
				//.childOption(ChannelOption.AUTO_READ, Boolean.valueOf(false))
				.bind(port).sync().channel()
				.closeFuture().sync();
			
			
			/*ServerBootstrap bootstrap = new ServerBootstrap();

			bootstrap.group(bossGroup, workerGroup);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.option(ChannelOption.SO_BACKLOG, 1024); //连接数
			bootstrap.option(ChannelOption.TCP_NODELAY, true);  //不延迟，消息立即发送
			bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true); //长连接
			bootstrap.childHandler(new SocketProxyInitializer());
			ChannelFuture f = bootstrap.bind(port).sync();
			// 关闭连接
			f.channel().closeFuture().sync();*/
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}

/*
 * Location: C:\Users\hasee\Desktop\文档\proxy\lib\proxy-0.0.1-SNAPSHOT\ Qualified
 * Name: com.lefu.proxy.socket.SocketProxyServer JD-Core Version: 0.6.0
 */