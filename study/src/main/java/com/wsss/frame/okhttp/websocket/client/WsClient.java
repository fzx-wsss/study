package com.wsss.frame.okhttp.websocket.client;

import com.wsss.frame.netty.webSocket.test.GzipUtils;
import okhttp3.*;
import okio.ByteString;

import java.util.concurrent.TimeUnit;

public class WsClient {
    public static void main(String[] args) throws Exception {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .sslSocketFactory(RxUtils.createSSLSocketFactory(),new RxUtils.TrustAllManager())
                .hostnameVerifier(new RxUtils.TrustAllHostnameVerifier())
                .readTimeout(3, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(3, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(3, TimeUnit.SECONDS)//设置连接超时时间
                .build();

        //连接地址
//        String url = "wss://ws.bitrue.com/etf/ws";
        String url = "ws://3.0.135.116:12345/etf/ws";
        //构建一个连接请求对象

        Request request = new Request.Builder().get().url(url).build();
        //开始连接
        WebSocket websocket = mClient.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                //连接成功...
                System.out.println(response.headers());
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                //收到消息...（一般是这里处理json）
                System.out.println(text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
                //收到消息...（一般很少这种消息）
                System.out.println(new String(GzipUtils.decompress(bytes.toByteArray())));
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                //连接关闭...
                System.out.println("onclosed");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable throwable, Response response) {
                super.onFailure(webSocket, throwable, response);
                //连接失败...
                throwable.printStackTrace();
            }
        });

        String putMessage = "{\"event\":\"sub\",\"params\":{\"cb_id\":\"xrp3lusdt\",\"channel\":\"market_xrp3lusdt_net_value\",\"top\":20}}";
        websocket.send(putMessage);
        TimeUnit.SECONDS.sleep(100);
    }
}
