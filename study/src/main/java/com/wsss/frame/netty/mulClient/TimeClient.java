package com.wsss.frame.netty.mulClient;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import com.wsss.frame.netty.simple.NettyClientHandler;

public class TimeClient {
	EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);
	public void connect(String host,int port) throws InterruptedException {
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
					socketChannel.pipeline().addLast(new TimeClientHandler());
				}
			});
			for(int i=0;i<10;i++) {
				ChannelFuture future = bootstrap.connect(host, port).sync();
				if (future.isSuccess()) {
					SocketChannel socketChannel = (SocketChannel) future.channel();
					byte[] bytes = ("QUERY TIME ORDER"+System.lineSeparator()).getBytes();
					System.out.println("id:" + socketChannel.id());
					System.out.println("----------------connect server success----------------" + i);
					socketChannel.writeAndFlush(Unpooled.copiedBuffer(bytes));
				}
			}
			//future.channel().closeFuture().sync();
		} finally {
			//eventLoopGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		
			new TimeClient().connect("127.0.0.1", 9090);
		
		System.out.println("完成");
		TimeUnit.MINUTES.sleep(10);
	}
}
