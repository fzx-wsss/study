package com.wsss.frame.netty.debug;

import com.wsss.frame.netty.myTest.server.ClientManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import com.wsss.frame.netty.simple.NettyServerHandler;

public class NettyServerBootstrap {

    public static void main(String[] args) {
        new NettyServerBootstrap().bind(9090);
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new NettyInHandler());
            socketChannel.pipeline().addLast(new NettyOutHandler());
        }
    }

    public void bind(int port) {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ClientManager.init();
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(boss, worker);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024); //连接数
            //bootstrap.option(ChannelOption.TCP_NODELAY, true);  //不延迟，消息立即发送
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true); //长连接
            bootstrap.childHandler(new ChildChannelHandler());
            ChannelFuture f = bootstrap.bind(port).sync();
            if (f.isSuccess()) {
                System.out.println(("启动Netty服务成功，端口号：" + port));
            }
            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
