package com.wsss.frame.netty.syn;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;

/**
 * 消息解码器
 * 
 */
public class MessageDecoder extends LengthFieldBasedFrameDecoder {

	public MessageDecoder(int maxFrameLength, int lengthFieldOffset,
			int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {

		super(maxFrameLength, lengthFieldOffset, lengthFieldLength,
				lengthAdjustment, initialBytesToStrip);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {

		ChannelBuffer frame = (ChannelBuffer) super.decode(ctx, channel, buffer);
		if (frame == null) {
			return null;
		}
		
		byte[] bytes = new byte[frame.readInt()];
		frame.readBytes(bytes);
		
		String content = new String(bytes);
		return content;
	}

	@Override
	protected Object decodeLast(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {

		return this.decode(ctx, channel, buffer);
	}

}