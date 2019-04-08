package com.wsss.basic.nio.aio.timeServer.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

import com.alibaba.dubbo.remoting.Channel;

public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
	
	private AsynchronousSocketChannel socketChannel;
	
	public ReadCompletionHandler(AsynchronousSocketChannel result) {
		if(socketChannel == null) {
			this.socketChannel = result;
		}
	}
	
	@Override
	public void completed(Integer result, ByteBuffer attachment) {
		attachment.flip();
		byte[] body = new byte[attachment.remaining()];
		attachment.get(body);
		try {
			String req = new String(body,"utf-8");
			System.out.println("Time server receive order : " + req);
			String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req) ? new Date(System.currentTimeMillis()).toString() : "Bad order";
			
			doWrite(currentTime);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void doWrite(String currentTime) {
		if(currentTime != null && currentTime.trim().length() > 0) {
			byte[] bytes = (currentTime).getBytes();
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			
			socketChannel.write(writeBuffer,writeBuffer,new CompletionHandler<Integer, ByteBuffer>() {

				@Override
				public void completed(Integer result, ByteBuffer buffer) {
					// 如果没有发送完成，继续发送
					if(buffer.hasRemaining())
						socketChannel.write(buffer,buffer,this);
				}

				@Override
				public void failed(Throwable exc, ByteBuffer attachment) {
					// TODO Auto-generated method stub
					try {
						socketChannel.close();
					}catch(Exception e) {
						
					}
				}
			});
			
		}
	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		// TODO Auto-generated method stub
		try {
			socketChannel.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
