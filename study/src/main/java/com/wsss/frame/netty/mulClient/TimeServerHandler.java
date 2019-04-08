package com.wsss.frame.netty.mulClient;

import java.util.Date;

import org.aspectj.lang.reflect.UnlockSignature;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter {

	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.close();
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String) msg;
		System.out.println("The time Server receive order : " + body);
		
		String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? 
				new Date(System.currentTimeMillis()).toString() : "bad order";
		currentTime += System.lineSeparator();
		ByteBuf writeBuf = Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.write(writeBuf);
	}

	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
		cause.printStackTrace();
	}
	
}
