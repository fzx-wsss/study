package com.wsss.frame.jetty.bitrue;

import com.xxl.job.core.biz.model.TriggerParam;
import com.xxl.rpc.remoting.net.params.XxlRpcFutureResponseFactory;
import com.xxl.rpc.remoting.net.params.XxlRpcRequest;
import com.xxl.rpc.remoting.net.params.XxlRpcResponse;
import com.xxl.rpc.serialize.impl.HessianSerializer;
import com.xxl.rpc.util.ThrowableUtil;
import com.xxl.rpc.util.XxlRpcException;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Result;
import org.eclipse.jetty.client.util.BufferingResponseListener;
import org.eclipse.jetty.client.util.BytesContentProvider;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpStatus;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class JettyClient {
    private static HessianSerializer serializer = new HessianSerializer();
    public static void main(String[] args) throws Exception {
        HttpClient httpClient = new HttpClient();
        httpClient.start();

        for (int i =0;i<10000;i++) {
            TimeUnit.MILLISECONDS.sleep(10);
            XxlRpcRequest xxlRpcRequest = buildRequest();
            Request request = httpClient.newRequest("http://10.48.2.64:9977");
            request.method(HttpMethod.POST);
            request.timeout(10, TimeUnit.SECONDS);		// async, not need timeout
            request.content(new BytesContentProvider(serializer.serialize(xxlRpcRequest)));
            request.send(new BufferingResponseListener() {
                @Override
                public void onComplete(Result result) {
                    try {
                        if (result.isFailed()) {
                            throw new XxlRpcException(result.getResponseFailure());
                        }
                        if (result.getResponse().getStatus() != HttpStatus.OK_200) {
                            throw new XxlRpcException("xxl-rpc remoting request fail, http HttpStatus["+ result.getResponse().getStatus() +"] invalid.");
                        }
                        byte[] responseBytes = getContent();
                        if (responseBytes == null || responseBytes.length==0) {
                            throw new XxlRpcException("xxl-rpc remoting request fail, response bytes is empty.");
                        }

                        // deserialize response
                        XxlRpcResponse xxlRpcResponse = (XxlRpcResponse) serializer.deserialize(responseBytes, XxlRpcResponse.class);
                        System.out.println(xxlRpcResponse);
                    } catch (Exception e){
                        throw e;
                    }
                }
            });
        }


        TimeUnit.SECONDS.sleep(10);
        httpClient.stop();
    }

    private static XxlRpcRequest buildRequest() {
        XxlRpcRequest xxlRpcRequest = new XxlRpcRequest();
        xxlRpcRequest.setRequestId(UUID.randomUUID().toString());
        xxlRpcRequest.setCreateMillisTime(System.currentTimeMillis());
        xxlRpcRequest.setAccessToken("accessToken");
        xxlRpcRequest.setClassName("com.xxl.job.core.biz.ExecutorBiz");
        xxlRpcRequest.setMethodName("run");
        xxlRpcRequest.setParameterTypes(new Class[]{com.xxl.job.core.biz.model.TriggerParam.class});
        TriggerParam param = new TriggerParam();
        param.setJobId(391);
        param.setExecutorHandler("voteCurrencyStateFlowJobHandler");
        param.setExecutorParams("");
        param.setExecutorBlockStrategy("SERIAL_EXECUTION");
        param.setLogId(ThreadLocalRandom.current().nextInt(10000)+5000000);
        param.setLogDateTim(System.currentTimeMillis());
        param.setGlueType("BEAN");
        param.setGlueSource("");
        param.setGlueUpdatetime(System.currentTimeMillis());
        param.setBroadcastIndex(0);
        param.setBroadcastTotal(1);
        param.setExecutorTimeout(0);
        xxlRpcRequest.setParameters(new Object[]{param});
        return xxlRpcRequest;
    }
}