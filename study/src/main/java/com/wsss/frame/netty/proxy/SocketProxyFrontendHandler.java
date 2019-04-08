package com.wsss.frame.netty.proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOption;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketProxyFrontendHandler extends ChannelInboundHandlerAdapter {
	private static Logger logger = LoggerFactory.getLogger(SocketProxyFrontendHandler.class);
	private volatile Channel outboundChannel;

	public void channelActive(ChannelHandlerContext ctx) throws InterruptedException {
		/*Channel inboundChannel = ctx.channel();

		InetSocketAddress frontendAddress = (InetSocketAddress) inboundChannel.remoteAddress();
		InetSocketAddress backendAddress = SocketRouteConfig.getProxyAddress(frontendAddress.getHostName());
		if (backendAddress == null) {
			logger.error("No route config from {}", frontendAddress.getHostName());
			inboundChannel.close();
		}
		String backendHost = backendAddress.getHostName();
		int backendPort = backendAddress.getPort();
		logger.info("Proxying {}:{} to {}:{}",new Object[] { frontendAddress.getHostName(), Integer.valueOf(frontendAddress.getPort()), backendHost,
						Integer.valueOf(backendPort) });

		Bootstrap b = new Bootstrap();
		((Bootstrap) ((Bootstrap) ((Bootstrap) b.group(inboundChannel.eventLoop())).channel(ctx.channel().getClass()))
				.handler(new SocketProxyBackendHandler(inboundChannel))).option(ChannelOption.AUTO_READ,
				Boolean.valueOf(false));

		ChannelFuture f = b.connect(backendHost, backendPort);
		this.outboundChannel = f.channel();
		logger.info("{}",this.outboundChannel.isActive());
		
		f.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				// TODO Auto-generated method stub
				if (future.isSuccess()) {
					SocketProxyFrontendHandler.logger.info("{} start to read data,time:{}", future.channel().toString(),System.currentTimeMillis());
					future.channel().read();
				} else {
					SocketProxyFrontendHandler.logger.warn("{} attempt has failed", future.channel().toString());
					future.channel().close();
				}
			}
		});*/
		/*
		 * f.addListener(new ChannelFutureListener(inboundChannel) { public void
		 * operationComplete(ChannelFuture future) { 44 if (future.isSuccess())
		 * { 45 SocketProxyFrontendHandler.logger.info("{} start to read data",
		 * future.channel().toString()); 46 this.val$inboundChannel.read(); }
		 * else { 48
		 * SocketProxyFrontendHandler.logger.warn("{} attempt has failed",
		 * future.channel().toString()); 49 this.val$inboundChannel.close(); } }
		 * });
		 */
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		//logger.info("channelRead time:{}",System.currentTimeMillis());
		/*if (this.outboundChannel.isActive()) this.outboundChannel.writeAndFlush(msg).addListener(
				new ChannelFutureListener() {

					@Override
					public void operationComplete(ChannelFuture future) throws Exception {
						// TODO Auto-generated method stub
						if (future.isSuccess()) {
							SocketProxyFrontendHandler.logger.info("{} start to readed data", future.channel().toString());
							future.channel().read();
						} else {
							SocketProxyFrontendHandler.logger.warn("{} attempt has failed", future.channel().toString());
							future.channel().close();
						}
					}
				});*/
		ByteBuf buf = (ByteBuf) msg;
		
		String recieved = getMessage(buf);
		System.out.println("服务器接收到消息：" + recieved);
		ctx.write("111111");
		ctx.write(Unpooled.EMPTY_BUFFER);
		ctx.flush();
		System.out.println(msg);
	}

	public void channelInactive(ChannelHandlerContext ctx) {
		if (this.outboundChannel != null) closeOnFlush(this.outboundChannel);
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		logger.error(ctx.channel().toString() + " somthing happend", cause);
		closeOnFlush(ctx.channel());
	}

	static void closeOnFlush(Channel ch) {
		if (ch.isActive()) ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}
	private String getMessage(ByteBuf buf) {

		byte[] con = new byte[buf.readableBytes()];
		buf.readBytes(con);
		try {
			return new String(con, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private ByteBuf getSendByteBuf(String message) {
		try {
			byte[] req = message.getBytes("UTF-8");
			ByteBuf pingMessage = Unpooled.buffer();
			pingMessage.writeBytes(req);
	
			return pingMessage;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

/*
 * Location: C:\Users\hasee\Desktop\文档\proxy\lib\proxy-0.0.1-SNAPSHOT\ Qualified
 * Name: com.lefu.proxy.socket.SocketProxyFrontendHandler JD-Core Version: 0.6.0
 */