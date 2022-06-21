package com.wsss.frame.netty.webSocket.server;

import com.wsss.frame.netty.webSocket.handler.AllMsgHandler;
import com.wsss.frame.netty.webSocket.handler.TextWebSocketFrameHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutorGroup;

public class WSServer {
    private static int port = 12345;

    public static void main(String[] args) {
        EventLoopGroup bossGroup;
        EventLoopGroup workerGroup;
        EventExecutorGroup executors = new DefaultEventExecutorGroup(1);
        bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("boss"));
        workerGroup = new NioEventLoopGroup(10, new DefaultThreadFactory("work"));

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(
                            NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            pipeline.addLast(new HttpServerCodec());

                            pipeline.addLast("http-aggregator", new HttpObjectAggregator(131072, true));

                            pipeline.addLast(new ChunkedWriteHandler());

                            pipeline.addLast("WebSocket-protocol", new WebSocketServerProtocolHandler("/test/api"));

                            pipeline.addLast(executors,new TextWebSocketFrameHandler());
                            pipeline.addLast(executors,new AllMsgHandler());
                        }
                    })
                    .option(NioChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.SO_KEEPALIVE, false)
                    .childOption(NioChannelOption.TCP_NODELAY, true);
            System.out.println("klineServer started prot:{}"+ port);
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().addListener((ChannelFutureListener) channelFuture -> System.out.println(f.channel().toString() + "链路关闭")).sync();
        } catch (InterruptedException e) {
            System.out.println("klineServer stoped....");
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("klineServer stoped....");
        }
    }
}
