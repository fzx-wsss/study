/*

 * Copyright (c) 2022 superatomfin.com. All Rights Reserved.

 */
package com.wsss.frame.netty.http.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author shaoxinye  Date: 2022/7/18 Time: 1:14 下午
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {


    public MyChannelInitializer() {
    }
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline()
                    .addLast(new HttpServerCodec())//http decode encode  只能解析http请求头 不能解析body
                    .addLast(new HttpObjectAggregator(600))//解析http body体 双向的 都是http的处理
                    .addLast(new ChunkedWriteHandler())//处理器写顺序 MyHandler--》ChunkedWriteHandler--》HttpObjectAggregator--
                    .addLast(new MyHandler());
        }

}