package com.wsss.frame.hystrix.hello.world;

import java.util.Random;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.collapser.RequestCollapserFactory.Setter;

public class GetOrderCircuitBreakerCommand extends HystrixCommand<String> {
	
	public GetOrderCircuitBreakerCommand(String name){
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ThreadPoolTestGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("testCommandKey"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(name))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withCircuitBreakerEnabled(true)//默认是true，本例中为了展现该参数
                                .withCircuitBreakerForceOpen(false)//默认是false，本例中为了展现该参数
                                .withCircuitBreakerForceClosed(false)//默认是false，本例中为了展现该参数
                                .withCircuitBreakerErrorThresholdPercentage(5)//(1)错误百分比超过5%
                                .withCircuitBreakerRequestVolumeThreshold(10)//(2)10s以内调用次数10次，同时满足(1)(2)熔断器打开
                                .withCircuitBreakerSleepWindowInMilliseconds(5000)//隔5s之后，熔断器会尝试半开(关闭)，重新放进来请求
//                                .withExecutionTimeoutInMilliseconds(1000)
                )
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                                .withMaxQueueSize(10)   //配置队列大小
                                .withCoreSize(2)    // 配置线程池里的线程数
                )
        );
    }
	public static int i =0;
	public static void main(String[] args) throws Exception {
		for(i=0;i<50;i++){
            Thread.sleep(500);
            HystrixCommand<String> command = new GetOrderCircuitBreakerCommand("testCircuitBreaker");
            String result = command.execute();
            //本例子中从第11次，熔断器开始打开
            System.out.println("call times:"+(i+1)+"   result:"+result +" isCircuitBreakerOpen: "+command.isCircuitBreakerOpen() + " " + command.getMetrics().getHealthCounts().getTotalRequests());
            System.out.println();
            //本例子中5s以后，熔断器尝试关闭，放开新的请求进来
        }
		
	}
    

    @Override
    protected String run() throws Exception {
    	if(i<24 && i > 12) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>" + i);
			throw new RuntimeException();
		}
        return "running:  ";
    }

    @Override
    protected String getFallback() {
//        System.out.println("FAILBACK");
        return "fallback: ";
    }
}