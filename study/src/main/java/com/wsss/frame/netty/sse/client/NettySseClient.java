package com.wsss.frame.netty.sse.client;

import com.wsss.frame.netty.sse.handler.SseClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.HttpHeaders.Values;
import java.net.URI;

public class NettySseClient {
    private final String host;
    private final int port;
    private final String path;
    private EventLoopGroup group;

    public NettySseClient(String url) throws Exception {
        URI uri = new URI(url);
        this.host = uri.getHost();
        this.port = uri.getPort() == -1 ? 80 : uri.getPort();
        this.path = uri.getRawPath() + (uri.getRawQuery() == null ? "" : "?" + uri.getRawQuery());
    }

    public void connect() throws InterruptedException {
        group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) {
                     ch.pipeline()
                       .addLast(new HttpClientCodec())
                       .addLast(new HttpObjectAggregator(65536))
                       .addLast(new SseClientHandler());
                 }
             });

            Channel ch = b.connect(host, port).sync().channel();
            FullHttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.GET, path);
            request.headers().set(HttpHeaderNames.HOST, host);
            request.headers().set(HttpHeaderNames.HOST, host);
            request.headers().set(HttpHeaderNames.ACCEPT, "text/event-stream");
            request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);

            ch.writeAndFlush(request);
            ch.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new NettySseClient("http://localhost:18080/sse").connect();
    }
}
