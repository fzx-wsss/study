package com.wsss.frame.netty.http.server;

import com.alibaba.dubbo.common.utils.ConcurrentHashSet;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedStream;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.openxmlformats.schemas.drawingml.x2006.main.CTEffectList;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static Set<Channel> group = new ConcurrentHashSet<>();

    public MyHandler() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            private int i = 0;
            @Override
            public void run() {
                try {
                    System.out.println(group.size());
                    if(!group.isEmpty()) {
                        String s = i + "\n";
                        InputStream in = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
                        int length = in.available();
                        for(Channel channel : group) {
                            channel.writeAndFlush(new ChunkedStream(in,length));
                        }
                        in.close();
                        i++;
                    }
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }, 300, 300);

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        //1 remoteAddress
        String remoteIp = remoteAddress(ctx);
        //3 add channel
        addChannel(ctx);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buildHttpResponse());
        super.channelReadComplete(ctx);
        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        group.remove(ctx.channel());
        super.channelInactive(ctx);
    }

    private void addChannel(ChannelHandlerContext ctx) {
        //add channel
        group.add(ctx.channel());
    }

    private String remoteAddress(ChannelHandlerContext ctx) {
        String clientIp = "";
        try {
            clientIp = (String) ctx.channel().attr(AttributeKey.valueOf("HIP")).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientIp;
    }

    private HttpResponse buildHttpResponse() throws IOException {
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        Calendar time = new GregorianCalendar();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
//        response.headers().set(HttpHeaderNames.DATE, dateFormatter.format(time.getTime()));
//        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, length);
        response.headers().set(HttpHeaderNames.ACCEPT_ENCODING, "gzip, deflate, br");
        response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        //text/plain
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
        response.headers().set("Transfer-Encoding", "chunked");
        return response;
    }


}
