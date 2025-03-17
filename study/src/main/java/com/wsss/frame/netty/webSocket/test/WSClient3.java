package com.wsss.frame.netty.webSocket.test;

import com.wsss.frame.netty.webSocket.handler.AllMsgHandler;
import com.wsss.frame.netty.webSocket.handler.TextWebSocketFrameHandler;
import com.wsss.frame.netty.webSocket.handler.WebSocketClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.URI;
import java.util.concurrent.TimeUnit;

public class WSClient3 {

    //public static final String[] symbols = new String[] {"grt3lusdt","iost3lusdt","alpha3lusdt","kava3lusdt","rvn3lusdt","snx3lusdt","bat3lusdt","band3lusdt","zil3lusdt","hnt3lusdt","chr3lusdt","bal3lusdt","ray3lusdt","mkr3lusdt","iotx3lusdt","blz3lusdt","near3lusdt","rune3lusdt","comp3lusdt","arpa3lusdt","reef3lusdt","one3lusdt","celr3lusdt","sfp3lusdt","bel3lusdt","coti3lusdt","zec3lusdt","omg3lusdt","egld3lusdt","nkn3lusdt","trb3lusdt","alice3lusdt","c983lusdt","icx3lusdt","dent3lusdt","ar3lusdt","bake3lusdt","zen3lusdt","ocean3lusdt","sxp3lusdt","srm3lusdt","lina3lusdt","tlm3lusdt","unfi3lusdt","dash3lusdt","tomo3lusdt","rsr3lusdt","ctsi3lusdt","zrx3lusdt","ctk3lusdt","flow3lusdt","waves3lusdt","knc3lusdt"};
    public static final String[] symbols = new String[] {"btcusdt"};
    public static void main(String[] args) throws Exception {
        try {
            //websocke连接的地址，/hello是因为在服务端的websockethandler设置的
//            URI websocketURI = new URI("wss://ws.bitrue.com/etf/ws");
//            URI websocketURI = new URI("ws://10.48.1.113:12345/stream/ws");
//            URI websocketURI = new URI("ws://127.0.0.1:12345/stream/ws");

            URI websocketURI = new URI("wss://ws.alphtest.cc/stream/ws");
            //netty基本操作，线程组
            EventLoopGroup group = new NioEventLoopGroup();
            //netty基本操作，启动类
            Bootstrap boot = new Bootstrap();
            boot.option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.TCP_NODELAY, true).group(group)
                    .handler(new LoggingHandler(LogLevel.DEBUG)).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            if("wss".equals(websocketURI.getScheme())) {
                                SslContext sslCtx = SslContextBuilder.forClient()
                                        .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
                                pipeline.addLast(sslCtx.newHandler(socketChannel.alloc()));
                            }

                            pipeline.addLast("http-codec",new HttpClientCodec());
                            pipeline.addLast("aggregator", new HttpObjectAggregator(1024 * 1024 * 10));
                            pipeline.addLast(new ChunkedWriteHandler());
                            pipeline.addLast("compressionHandler", WebSocketClientCompressionHandler.INSTANCE);
                            pipeline.addLast("hookedHandler", new WebSocketClientHandler(websocketURI));
                            pipeline.addLast("textHandler", new TextWebSocketFrameHandler());
                            pipeline.addLast("allHandler",new AllMsgHandler());
                        }
                    });

            //进行握手
            //客户端与服务端连接的通道，final修饰表示只会有一个
            final Channel channel = boot.connect(websocketURI.getHost(), 443).sync().channel();
            //阻塞等待是否握手成功
            System.out.println("连接成功");
            WebSocketClientHandler.handshakeFuture.sync();
            System.out.println("握手成功");
            for(String symbol : symbols) {
                sengSub(channel,symbol);
            }

            //给服务端发送的内容，如果客户端与服务端连接成功后，可以多次掉用这个方法发送消息
            for(int i =0 ;i< 100000;i++) {
//                sengPong(channel);
                TimeUnit.MILLISECONDS.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sengSub(Channel channel,String symbol) {
        //发送的内容，是一个文本格式的内容
        String putMessage = "{\"event\":\"SUBSCRIBE\",\"id\":\"123\",\"params\":[{\"token\":\"AvRiG4buyV8bRsmwSK5hxfuEc62Pxj1nJAHxxvUwpump\",\"type\":\"signal\",\"scale\":\"1w\"}]}";
//        String putMessage = "{\"event\":\"sub\",\"params\":{\"channel\":\"user_order_update\"}}";
        TextWebSocketFrame msg = new TextWebSocketFrame(putMessage);
        channel.writeAndFlush(msg).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("消息发送成功，发送的消息是：" + putMessage);
                } else {
                    System.out.println("消息发送失败 " + channelFuture.cause().getMessage());
                }
            }
        });

    }

    public static void sengPong(Channel channel) {
        //发送的内容，是一个文本格式的内容
        String time = String.valueOf(System.currentTimeMillis());
        channel.writeAndFlush(new PingWebSocketFrame());
        channel.writeAndFlush(new PongWebSocketFrame(Unpooled.copiedBuffer(time.getBytes())));
    }
}
