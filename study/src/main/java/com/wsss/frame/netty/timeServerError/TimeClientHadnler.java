package com.wsss.frame.netty.timeServerError;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHadnler extends ChannelHandlerAdapter {
	private ByteBuf buf;
	
	public TimeClientHadnler() {
		byte[] bytes = ("QUERY TIME ORDER").getBytes();
		buf = Unpooled.copiedBuffer(bytes);
		//buf.writeBytes(bytes);
	}

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for(int i=0;i<100;i++) {
			ctx.writeAndFlush(Unpooled.copiedBuffer(buf));
		}
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf read = (ByteBuf) msg;
		byte[] bytes = new byte[read.readableBytes()];
		read.readBytes(bytes);
		String body = new String(bytes,"utf-8");
		System.out.println("body : " + body);
		
	}
	
	
}
