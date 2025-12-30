package com.wsss.frame.netty.webSocket.test;

import com.wsss.frame.netty.webSocket.handler.AllMsgHandler;
import com.wsss.frame.netty.webSocket.handler.TextWebSocketFrameHandler;
import com.wsss.frame.netty.webSocket.handler.WebSocketClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.URI;
import java.util.concurrent.TimeUnit;

public class WSClient {

    //public static final String[] symbols = new String[] {"grt3lusdt","iost3lusdt","alpha3lusdt","kava3lusdt","rvn3lusdt","snx3lusdt","bat3lusdt","band3lusdt","zil3lusdt","hnt3lusdt","chr3lusdt","bal3lusdt","ray3lusdt","mkr3lusdt","iotx3lusdt","blz3lusdt","near3lusdt","rune3lusdt","comp3lusdt","arpa3lusdt","reef3lusdt","one3lusdt","celr3lusdt","sfp3lusdt","bel3lusdt","coti3lusdt","zec3lusdt","omg3lusdt","egld3lusdt","nkn3lusdt","trb3lusdt","alice3lusdt","c983lusdt","icx3lusdt","dent3lusdt","ar3lusdt","bake3lusdt","zen3lusdt","ocean3lusdt","sxp3lusdt","srm3lusdt","lina3lusdt","tlm3lusdt","unfi3lusdt","dash3lusdt","tomo3lusdt","rsr3lusdt","ctsi3lusdt","zrx3lusdt","ctk3lusdt","flow3lusdt","waves3lusdt","knc3lusdt"};
    public static final String[] symbols = new String[] {"btc3lusdt"};
    public static void main(String[] args) throws Exception {
        try {
            //websocke连接的地址，/hello是因为在服务端的websockethandler设置的
//            URI websocketURI = new URI("wss://ws.bitrue.com/etf/ws");
            URI websocketURI = new URI("wss://ws.bitrue.com/kline-api/ws");
//            URI websocketURI = new URI("wss://stream.binance.com:443/stream");
//            URI websocketURI = new URI("ws://10.48.1.18:12345/etf/ws");
//            URI websocketURI = new URI("ws://10.231.8.228:12345/kline-api/ws");
//            URI websocketURI = new URI("wss://ws.byqian.com/kline-api/ws");
//            URI websocketURI = new URI("ws://10.48.1.80:12345/kline-api/ws");
//            URI websocketURI = new URI("ws://a682264b60c1146e48df0d5015cb6cc2-1099915016.ap-southeast-1.elb.amazonaws.com:12345/kline-api/ws");
//            URI websocketURI = new URI("wss://ws.byqian.cc/kline-api/ws");
//            URI websocketURI = new URI("ws://3.0.135.116:8888/stream?listenKey=598e55b97a78de9b7f962d1587d72bae57bb734d0df77c41ab45cf98a0227e0e");
            //netty基本操作，线程组
            EventLoopGroup group = new NioEventLoopGroup(10);
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
                                pipeline.addLast(sslCtx.newHandler(socketChannel.alloc(),websocketURI.getHost(),websocketURI.getPort()));
                            }

                            pipeline.addLast("hps ttp-codec",new HttpClientCodec());
                            pipeline.addLast("aggregator", new HttpObjectAggregator(1024 * 1024 * 10));
                            pipeline.addLast(new ChunkedWriteHandler());
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
            Channel channel1 = WebSocketClientHandler.handshakeFuture.sync().channel();

            System.out.println("握手成功" + channel1);
//            TimeUnit.SECONDS.sleep(10);
//            channel.writeAndFlush(new TextWebSocketFrame("234.testmethodParamDesc"));
            for(String symbol : symbols) {
                sengSub(channel,symbol);
            }
            TimeUnit.SECONDS.sleep(1);
            for(String symbol : symbols) {
//                sengUnsub(channel,symbol);
            }
            //给服务端发送的内容，如果客户端与服务端连接成功后，可以多次掉用这个方法发送消息
            for(int i =0 ;i< 100000;i++) {
                TimeUnit.MILLISECONDS.sleep(20000);
//                sengPong(channel);
            }

        } catch (Exception e) {
            System.out.println("eee");
            e.printStackTrace();
        }
    }

    public static void sengSub(Channel channel,String symbol) {
        //发送的内容，是一个文本格式的内容market_all_tickers
//        String putMessage = "{\"event\":\"sub\",\"params\":{\"cb_id\":\""+symbol+"\",\"channel\":\"market_all_tickers\",\"pushTime\":\"100\"}}";
//        String putMessage = "{\"event\":\"sub\",\"params\":{\"cb_id\":\""+symbol+"\",\"channel\":\"market_"+symbol+"_depth_step0\",\"pushTime\":\"100\"}}";
//        String putMessage = "{\"event\":\"sub\",\"params\":{\"cb_id\":\""+symbol+"\",\"channel\":\"market_"+symbol+"_simple_depth_step0\",\"pushTime\":\"100\"}}";
//        String putMessage = "{\"event\":\"sub\",\"params\":{\"cb_id\":\""+symbol+"\",\"channel\":\"market_"+symbol+"_ticker\",\"pushTime\":\"100\"}}";
//        String putMessage = "{\"event\":\"sub\",\"params\":{\"cb_id\":\""+symbol+"\",\"channel\":\"market_"+symbol+"_trades\",\"pushTime\":\"100\"}}";
//        String putMessage = "{\"event\":\"sub\",\"params\":{\"cb_id\":\""+symbol+"\",\"channel\":\"market_"+symbol+"_segment_depth\",\"pushTime\":\"1000\"}}";
//        String putMessage = "{\"event\":\"sub\",\"params\":{\"cb_id\":\""+symbol+"\",\"channel\":\"market_"+symbol+"_top_depth_5\",\"pushTime\":\"100\"}}";
//        String putMessage = "{\"event\":\"sub\",\"params\":{\"cb_id\":\""+symbol+"\",\"channel\":\"market_"+symbol+"_book_ticker\",\"pushTime\":\"100\"}}";
//        String putMessage = "{\"event\":\"sub\",\"params\":{\"cb_id\":\""+symbol+"\",\"channel\":\"market_"+symbol+"_kline_4h\"}}";
//        String putMessage = "{\"method\":\"SUBSCRIBE\",\"params\":[\""+symbol+"@kline_1m\"],\"id\":1}";
        String putMessage = "{\"event\":\"sub\",\"paramsArr\":[{\"cb_id\":\""+symbol+"\",\"channel\":\"market_"+symbol+"_net_value\",\"gzip\":\"true\",\"pushTime\":\"1000\"}]}";
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

    public static void sengPing(Channel channel) {
        PingWebSocketFrame msg = new PingWebSocketFrame();
        channel.writeAndFlush(msg).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("消息发送成功，发送的消息是ping");
                } else {
                    System.out.println("消息发送失败 " + channelFuture.cause().getMessage());
                }
            }
        });

    }

    public static void sengUnsub(Channel channel,String symbol) {
        //发送的内容，是一个文本格式的内容
        String putMessage = "{\"event\":\"unsub\",\"paramsArr\":[{\"cb_id\":\""+symbol+"\",\"channel\":\"market_"+symbol+"_segment_depth\"}]}";
//        String putMessage = "{\"event\":\"unsub\",\"params\":{\"channel\":\"user_order_update\"}}";
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
        String putMessage = "{\"pong\":\"111222333\"}";
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
