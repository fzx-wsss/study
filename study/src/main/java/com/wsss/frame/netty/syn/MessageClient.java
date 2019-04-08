package com.wsss.frame.netty.syn;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

/**
 * 
 * 客户端代码
 */

public class MessageClient {

	public static List<Channel> channelList = new ArrayList<Channel>();
	
	public static ChannelFactory factory = null;
	
	static  {
		
		factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool(), 8);
		ClientBootstrap bootstrap = new ClientBootstrap(factory);

		MessageFactory fac = new MessageFactory();

		bootstrap.setPipelineFactory(fac);

		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);
		
		int clientCount = 2;
		
		for (int i=0; i<clientCount; i++) {
			ChannelFuture cf = bootstrap.connect(new InetSocketAddress("localhost", 9999));
			channelList.add(cf.getChannel());
		}
	}
		
	public static void main(String [] args) {
		
		for (int i=0; i<1000; i++) {
			channelList.get(0).write("client1 "+i);
			channelList.get(1).write("client2 "+i);
		}
		
	}
}