package com.wsss.frame.netty.myTest.server;

import java.util.Date;

import org.aspectj.lang.reflect.UnlockSignature;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class TimeServerHandler extends ChannelHandlerAdapter {

	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.close();
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String) msg;
		System.out.println("receive: " + body);
		
		String currentTime = new Date(System.currentTimeMillis()).toString() + System.lineSeparator();
		ByteBuf writeBuf = Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.write(writeBuf);
	}


	
}
