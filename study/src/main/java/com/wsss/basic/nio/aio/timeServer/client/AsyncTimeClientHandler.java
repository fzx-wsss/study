package com.wsss.basic.nio.aio.timeServer.client;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeClientHandler implements CompletionHandler<Void, AsyncTimeClientHandler>,Runnable {
	
	private AsynchronousSocketChannel client;
	private String host;
	private int port;
	private CountDownLatch latch;
	
	public AsyncTimeClientHandler(String host,int port) {
		this.host = host;
		this.port = port;
		try {
			client = AsynchronousSocketChannel.open();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		latch = new CountDownLatch(1);
		client.connect(new InetSocketAddress(host,port),this,this);
		try {
			latch.await();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			client.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void completed(Void result, AsyncTimeClientHandler attachment) {
		// TODO Auto-generated method stub
		byte[] req = "QUERY TIME ORDER".getBytes();
		ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
		writeBuffer.put(req);
		writeBuffer.flip();
		client.write(writeBuffer,writeBuffer,new CompletionHandler<Integer, ByteBuffer>() {

			@Override
			public void completed(Integer result, ByteBuffer buffer) {
				if(buffer.hasRemaining()) {
					client.write(buffer,buffer,this);
				}else {
					ByteBuffer readBuffer = ByteBuffer.allocate(1024);
					client.read(readBuffer,readBuffer,new CompletionHandler<Integer, ByteBuffer>() {

						@Override
						public void completed(Integer result, ByteBuffer buffer) {
							// TODO Auto-generated method stub
							buffer.flip();
							byte[] bytes = new byte[buffer.remaining()];
							buffer.get(bytes);
							String body = null;
							try {
								body = new String(bytes,"utf-8");
								System.out.println("now is : " + body);
								latch.countDown();
							}catch(Exception e) {
								e.printStackTrace();
							}
						}

						@Override
						public void failed(Throwable exc, ByteBuffer attachment) {
							try {
								client.close();
							}catch(Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				try {
					client.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
		// TODO Auto-generated method stub
		
		exc.printStackTrace();
		try {
			client.close();
			latch.countDown();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	

}
