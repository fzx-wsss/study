package com.wsss.basic.nio.timeServer;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;


public class MultiplexerTimeServer implements Runnable {
	private Selector selector;
	private ServerSocketChannel serverSocketChannel;
	private volatile boolean stop;
	
	public MultiplexerTimeServer(int port) {
		try {
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("server start！");
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void stop() {
		this.stop = true;
	}
	@Override
	public void run() {
		while (!stop) {
			try {
				selector.select(1000);
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectedKeys.iterator();
				while(it.hasNext()) {
					SelectionKey key = it.next();
					it.remove();
					try {
						handlerInput(key);
					}catch(Exception e) {
						if(key!=null) {
							key.cancel();
							if(key.channel()!= null)
								key.channel().close();
						}
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		//多路复用器关闭后，所有注册在上面的channel和pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
		if(selector != null) {
			try{
				selector.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void handlerInput(SelectionKey key) throws IOException {
		if(key.isValid()) {
			if(key.isAcceptable()) {
				//accepy the new connection
				ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
				SocketChannel sc = ssc.accept();
				sc.configureBlocking(false);
				//add the new connection to the selector
				sc.register(selector, SelectionKey.OP_READ);
			}
			
			if(key.isReadable()) {
				//read the data
				SocketChannel sc = (SocketChannel) key.channel();
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if(readBytes > 0) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes,"UTF-8");
					System.out.println("The time Server receive order : " + body);
					
					String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? 
							new Date(System.currentTimeMillis()).toString() : "bad order";
					doWrite(sc,currentTime);
				} else if (readBytes < 0) {
					key.cancel();sc.close();
				}
			}
		}
	}
	
	private void doWrite(SocketChannel channel,String response) throws IOException {
		if(response != null && response.trim().length() > 0) {
			byte[] bytes = response.getBytes();
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			channel.write(writeBuffer);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
