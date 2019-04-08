package com.wsss.frame.netty.mulClient;

import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter {
	private ByteBuf buf;
	
	public TimeClientHandler() {
		byte[] bytes = ("QUERY TIME ORDER"+System.lineSeparator()).getBytes();
		buf = Unpooled.copiedBuffer(bytes);
		//buf.writeBytes(bytes);
		System.out.println("handler:"+this.toString());
	}

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		/*for(int i=0;i<100;i++) {
			TimeUnit.SECONDS.sleep(2);
			ctx.writeAndFlush(Unpooled.copiedBuffer(buf));
		}*/
		System.out.println("ctx:"+ctx.toString());
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String) msg;
		System.out.println("body : " + body);
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
		cause.printStackTrace();
	}

	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.channel().close();
	}
	
	
}
