package com.wsss.frame.netty.myTest.client;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import com.wsss.frame.netty.simple.NettyClientHandler;

public class TimeClient {
	
	public void connect(String host,int port) throws InterruptedException {
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

		try {

			Bootstrap bootstrap = new Bootstrap();
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.option(ChannelOption.TCP_NODELAY, true);
			bootstrap.group(eventLoopGroup);
			bootstrap.remoteAddress(host, port);
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
					socketChannel.pipeline().addLast(new StringDecoder());
					socketChannel.pipeline().addLast(new StringEncoder());
					socketChannel.pipeline().addLast(new TimeClientHandler());
				}
			});
			ChannelFuture future = bootstrap.connect(host, port).sync();
			if (future.isSuccess()) {
				SocketChannel socketChannel = (SocketChannel) future.channel();
				System.out.println("----------------connect server success----------------");
			}
			future.channel().closeFuture().sync();
			//TimeUnit.SECONDS.sleep(100);
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new TimeClient().connect("127.0.0.1", 9090);
	}
}
