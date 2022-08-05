package com.wsss.frame.netty.http.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            ChannelFuture channelFuture = bootstrap.group(bossGroup, workGroup)
                    //ChannelOption 控制连接的次数
//                    .option(ChannelOption.SO_BACKLOG,5)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MyChannelInitializer()).bind(12345).sync();
            System.out.println(" chunkedServer bind ");
//            channelFuture.channel().closeFuture().addListener((ChannelFutureListener) channelFuture -> logger.info(f.channel().toString() + "链路关闭")).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            System.out.println("klineServer stoped....");
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
