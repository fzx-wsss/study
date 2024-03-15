package com.wsss;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

@Activate(group = {"provider"})
public class DubboMasterFilter implements Filter, Filter.Listener {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = AsyncRpcResult.newDefaultAsyncResult(null, new RuntimeException("异常"), invocation);
        RpcContext.getContext().setAttachment("123","123");
        RpcContext.getServerContext().setAttachment("123","123");
        invocation.setAttachment("123","123");
        result.setAttachment("123","123");
        return result;
    }

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {

    }

    @Override
    public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {
        System.out.println(t);
    }
}