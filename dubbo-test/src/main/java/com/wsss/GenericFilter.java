package com.wsss;

import org.apache.dubbo.rpc.*;

import static org.apache.dubbo.rpc.Constants.GENERIC_KEY;

public class GenericFilter implements Filter, Filter.Listener {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        invocation.setAttachment(
                GENERIC_KEY, "gson");
        return invoker.invoke(invocation);
    }

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
        String generic = invoker.getUrl().getParameter(GENERIC_KEY);
        String methodName = invocation.getMethodName();
        Class<?>[] parameterTypes = invocation.getParameterTypes();
        Object value = appResponse.getValue();
        System.out.println(value);
    }

    @Override
    public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {
        System.out.println(t);
    }
}
