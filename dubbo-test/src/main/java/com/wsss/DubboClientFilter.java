package com.wsss;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

//@Activate(group = {"consumer"})
public class DubboClientFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = invoker.invoke(invocation);
        System.out.println("params:"+RpcContext.getContext().getAttachment("123"));
        System.out.println("params:"+RpcContext.getServerContext().getAttachment("123"));
        System.out.println("params:"+invocation.getAttachment("123"));
        System.out.println("params:"+result.getAttachment("123"));
        return result;
    }

}