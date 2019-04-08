package com.wsss.frame.netty.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wsss.frame.netty.utils.bean.SocketToNioFollower;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Description: netty异步发送消息工具类
 * @see: NioSocketUtils 此处填写需要参考的类
 * @version 2017年9月14日 下午2:47:14
 * @author zhongxuan.fan
 */
public class NioSocketUtils {
	private static final Logger logger = LoggerFactory.getLogger(NioSocketUtils.class);
	private static EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);
	private static Bootstrap bootstrap = null;
	// channel的id和解析类
	private static Map<String, SocketToNioFollower> channelIdAndFollower = new ConcurrentHashMap<String, SocketToNioFollower>(128);

	static {
		try {
			bootstrap = new Bootstrap();
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.option(ChannelOption.TCP_NODELAY, true);
			bootstrap.group(eventLoopGroup);
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					socketChannel.pipeline().addLast(new ClientHandler());
				}
			});
		} catch (Exception e) {
			logger.error("{}", e);
		}
	}

	public static void send(byte[] bytes, String host, int port, SocketToNioFollower follower) {
		try {
			ChannelFuture future = bootstrap.connect(host, port).sync();
			if (future.isSuccess()) {
				SocketChannel socketChannel = (SocketChannel) future.channel();
				channelIdAndFollower.put(socketChannel.id().toString(), follower);
				socketChannel.writeAndFlush(Unpooled.copiedBuffer(bytes));
				socketChannel.shutdownOutput();
			}
		} catch (Exception e) {
			logger.error("发送失败:{}", e);
		}
	}

	// 静态内部类，主要用于不同连接的返回处理
	static class ClientHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			String id = ctx.channel().id().toString();
			try {
				ByteBuf buf = (ByteBuf) msg;
				byte[] req = new byte[buf.readableBytes()];
				buf.readBytes(req);
				SocketToNioFollower follower = channelIdAndFollower.get(id);
				follower.parseBytes(req);
			} finally {
				channelIdAndFollower.remove(id);
			}
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			ctx.channel().close();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			// TODO Auto-generated method stub
			super.exceptionCaught(ctx, cause);
			logger.error("{}", cause);
			channelIdAndFollower.remove(ctx.channel().id().toString());
			ctx.close();
		}

	}
}
