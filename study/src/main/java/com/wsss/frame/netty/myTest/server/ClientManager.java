package com.wsss.frame.netty.myTest.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientManager extends Thread {
	private static Map<ChannelHandlerContext,Long> ctxs_beats = new HashMap<>();
	
	private static ScheduledExecutorService service = Executors.newScheduledThreadPool(1);  
    

	public static void init() {
		//发送心跳
		service.scheduleAtFixedRate(new Runnable() {  
	          
	        @Override  
	        public void run() {  
	        	for(Entry<ChannelHandlerContext,Long> entry : ctxs_beats.entrySet()) {
	        		if(System.currentTimeMillis() - entry.getValue() > 9) {
	        			try {
	        				System.out.println("send beats");
	        				entry.getKey().writeAndFlush(Unpooled.copiedBuffer(("你好"+System.lineSeparator()).getBytes()));
	        			}catch(Exception e) {
	        				e.printStackTrace();
	        			}
	        		}
	        	}
	              
	        }  
	    }, 10, 10,TimeUnit.SECONDS); 
		//清除长时间没有心跳的连接
		service.scheduleAtFixedRate(new Runnable() {  
	         
	        @Override  
	        public void run() {  
	        	for(Entry<ChannelHandlerContext,Long> entry : ctxs_beats.entrySet()) {
	        		if(System.currentTimeMillis() - entry.getValue() > 19) {
	        			try {
	        				System.out.println("closed ctx");
	        				entry.getKey().close();
	        				ctxs_beats.remove(entry.getKey());
	        			}catch(Exception e) {
	        				e.printStackTrace();
	        			}
	        		}
	        	}
	              
	        }  
	    }, 10, 10,TimeUnit.SECONDS); 
		
		new ClientManager().start();
	}
	
	public static void putCtx(ChannelHandlerContext ctx) {
		ctxs_beats.put(ctx, System.currentTimeMillis());
	}
	
	public static void deleteCtx(ChannelHandlerContext ctx) {
		ctxs_beats.remove(ctx);
	}

	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String s = null;
			while((s = br.readLine()) != null) {
				System.out.println("in:" + s);
				for(Entry<ChannelHandlerContext,Long> entry : ctxs_beats.entrySet()) {
					entry.getKey().writeAndFlush(Unpooled.copiedBuffer((s+System.lineSeparator()).getBytes()));
		    	}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
