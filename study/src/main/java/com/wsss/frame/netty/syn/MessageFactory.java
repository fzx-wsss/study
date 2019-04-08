package com.wsss.frame.netty.syn;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.logging.LoggingHandler;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.Log4JLoggerFactory;

public class MessageFactory implements ChannelPipelineFactory {

	/**
	 * 消息解码器
	 */
	private ChannelHandler messageDecoder = new MessageDecoder(Integer.MAX_VALUE, 0, 4, 0, 4);
	
	/**
	 * 消息编码器
	 */
	private ChannelHandler messageEncoder = new MessageEncoder(4);
	 
	/**
	 * 消息分发的字节流处理器
	 */
	private MessageHandler messageHandler = new MessageHandler();		

	public ChannelPipeline getPipeline() throws Exception {

		ChannelPipeline cp = Channels.pipeline();
		return addMessageHandlersTo(cp);
	}
	
	/**
	 * 给ChannelPipeline对象增加消息协议的字节流处理器集合
	 * @param cp
	 * @return
	 */
	public ChannelPipeline addMessageHandlersTo(ChannelPipeline cp) {
		
		InternalLoggerFactory.setDefaultFactory(new Log4JLoggerFactory());
		cp.addLast("logging", new LoggingHandler());
		
		cp.addLast("messageDecoder", messageDecoder);
		cp.addLast("messageEncoder", messageEncoder);
		
		cp.addLast("messageHandler", messageHandler);
		
		return cp;
	}
	
}