package com.wsss.frame.netty.webSocket.client;

import com.wsss.frame.netty.webSocket.handler.TextWebSocketFrameHandler;
import com.wsss.frame.netty.webSocket.handler.WebSocketClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.URI;
import java.util.concurrent.TimeUnit;

public class WSClient {
    public static void main(String[] args) throws Exception {
        try {
            //websocke连接的地址，/hello是因为在服务端的websockethandler设置的
            URI websocketURI = new URI("wss://127.0.0.1:12345/test/api");
            //netty基本操作，线程组
            EventLoopGroup group = new NioEventLoopGroup();
            //netty基本操作，启动类
            Bootstrap boot = new Bootstrap();
            boot.option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.TCP_NODELAY, true).group(group)
                    .handler(new LoggingHandler(LogLevel.DEBUG)).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                             pipeline.addLast("http-codec",new HttpClientCodec());
                            pipeline.addLast("aggregator", new HttpObjectAggregator(1024 * 1024 * 10));
                            pipeline.addLast(new ChunkedWriteHandler());
                            pipeline.addLast("hookedHandler", new WebSocketClientHandler());
                            pipeline.addLast("textHandler", new TextWebSocketFrameHandler());
                        }
                    });

            //进行握手
            //客户端与服务端连接的通道，final修饰表示只会有一个
            final Channel channel = boot.connect(websocketURI.getHost(), websocketURI.getPort()).sync().channel();
            //阻塞等待是否握手成功
            System.out.println("连接成功");
            WebSocketClientHandler.handshakeFuture.sync();
            System.out.println("握手成功");
            //给服务端发送的内容，如果客户端与服务端连接成功后，可以多次掉用这个方法发送消息
            for(int i =0 ;i< 100;i++) {
                sengMessage(channel);
                TimeUnit.SECONDS.sleep(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sengMessage(Channel channel) {
        //发送的内容，是一个文本格式的内容
        String putMessage = "{\"event\":\"req\",\"params\":{\"cb_id\":\"adausdt\",\"channel\":\"market_adausdt_trade_ticker\",\"top\":20}}";
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
}
