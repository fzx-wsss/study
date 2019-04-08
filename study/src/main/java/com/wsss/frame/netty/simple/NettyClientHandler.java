package com.wsss.frame.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.Constant;

import java.io.UnsupportedEncodingException;
import java.net.SocketAddress;

public class NettyClientHandler extends ChannelHandlerAdapter {
	private ByteBuf firstMessage;

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		byte[] data = "服务器，给我一个APPLE".getBytes();
		firstMessage = Unpooled.buffer();
		firstMessage.writeBytes(data);
		ctx.writeAndFlush(firstMessage);
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		String rev = getMessage(buf);
		System.out.println("客户端收到服务器数据:" + rev);
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

}