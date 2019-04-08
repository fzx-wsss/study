package com.wsss.frame.netty.timeServerSuccess;

import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHadnler extends ChannelHandlerAdapter {
	private ByteBuf buf;
	
	public TimeClientHadnler() {
		byte[] bytes = ("QUERY TIME ORDER"+System.lineSeparator()).getBytes();
		buf = Unpooled.copiedBuffer(bytes);
		//buf.writeBytes(bytes);
	}

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for(int i=0;i<100;i++) {
			TimeUnit.SECONDS.sleep(2);
			ctx.writeAndFlush(Unpooled.copiedBuffer(buf));
		}
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String) msg;
		System.out.println("body : " + body);
		
	}
	
	
}
