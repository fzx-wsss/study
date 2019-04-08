package com.wsss.frame.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.Constant;

import java.io.UnsupportedEncodingException;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

public class NettyServerHandler extends ChannelHandlerAdapter {

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//TimeUnit.SECONDS.sleep(10);
		System.out.println("channelActive");
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		
		ByteBuf buf = (ByteBuf) msg;
		
		String recieved = getMessage(buf);
		System.out.println("服务器接收到消息：" + recieved);
		
		try {
			ctx.writeAndFlush(getSendByteBuf("APPLE\n"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}


	/*
	 * 从ByteBuf中获取信息 使用UTF-8编码返回
	 */
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
	
	private ByteBuf getSendByteBuf(String message)
			throws UnsupportedEncodingException {

		byte[] req = message.getBytes("UTF-8");
		ByteBuf pingMessage = Unpooled.buffer();
		pingMessage.writeBytes(req);

		return pingMessage;
	}
}