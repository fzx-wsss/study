package com.wsss.frame.hystrix.hello.world;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.HystrixCommand.Setter;

public class HelloWorldHystrixCommand extends HystrixCommand { 
	private String name; 
	private static volatile int i = 0;
	private static volatile Class<?> clazz = Object.class;
	private static volatile AtomicBoolean circuitOpen;
	private static volatile AtomicLong circuitOpenedOrLastTestedTime;
	private static volatile HystrixCommandProperties properties;
	
	
	private static HystrixCommandGroupKey key = HystrixCommandGroupKey.Factory.asKey("ThreadPoolTestGroup");
	private static HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey("testCommandKey");
	private static com.netflix.hystrix.HystrixCommandProperties.Setter prop =  HystrixCommandProperties.Setter()
    .withCircuitBreakerEnabled(true)//默认是true，本例中为了展现该参数
    .withCircuitBreakerForceOpen(false)//默认是false，本例中为了展现该参数
    .withCircuitBreakerForceClosed(false)//默认是false，本例中为了展现该参数
    .withCircuitBreakerErrorThresholdPercentage(20)//(1)错误百分比超过5%
    .withCircuitBreakerRequestVolumeThreshold(10)//(2)10s以内调用次数10次，同时满足(1)(2)熔断器打开
    .withCircuitBreakerSleepWindowInMilliseconds(6500);//隔5s之后，熔断器会尝试半开(关闭)，重新放进来请求
	
	public HelloWorldHystrixCommand(String name) { 
		super(Setter.withGroupKey(key)
                .andCommandKey(commandKey)
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(name))
                .andCommandPropertiesDefaults(
                		prop
//                                .withExecutionTimeoutInMilliseconds(1000)
                )
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                                .withMaxQueueSize(10)   //配置队列大小
                                .withCoreSize(2)    // 配置线程池里的线程数
                )
        );
	}
	
	public static void main(String[] args) throws Exception  {
		for(;i<1000;i++){
            Thread.sleep(500); 
            System.out.println(allowTest());
            HystrixCommand<String> command = new HelloWorldHystrixCommand("testCircuitBreaker");
            String result = command.execute();
            //本例子中从第11次，熔断器开始打开
           
            System.out.println("times:"+(i+1)+" "+result +" isCircuitBreakerOpen: "+command.isCircuitBreakerOpen() + " " + command.getMetrics().getHealthCounts().getTotalRequests());
            System.out.println();
            //本例子中5s以后，熔断器尝试关闭，放开新的请求进来
        }
	}
	
	private static boolean allowTest() throws Exception {
		if(clazz != Object.class) {
			 return allow();
		}
		
		HystrixCircuitBreaker breaker = HystrixCircuitBreaker.Factory.getInstance(commandKey);
		if(null == breaker) return true;
		
		synchronized (clazz) {
			if(clazz != Object.class) return allow();
			clazz = breaker.getClass();
			Field circuitOpenedOrLastTestedTimeField = clazz.getDeclaredField("circuitOpenedOrLastTestedTime");
			Field circuitOpenField = clazz.getDeclaredField("circuitOpen");
			Field propertiesField = clazz.getDeclaredField("properties");
			
			circuitOpenedOrLastTestedTimeField.setAccessible(true);
			circuitOpenField.setAccessible(true);
			propertiesField.setAccessible(true);
			circuitOpen = (AtomicBoolean)circuitOpenField.get(breaker);
			circuitOpenedOrLastTestedTime = (AtomicLong)circuitOpenedOrLastTestedTimeField.get(breaker);
			properties = (HystrixCommandProperties)propertiesField.get(breaker);
		}
		return allow();
	}
	
	private static boolean allow() {
		long timeCircuitOpenedOrWasLastTested = circuitOpenedOrLastTestedTime.get();
        // 1) if the circuit is open
        // 2) and it's been longer than 'sleepWindow' since we opened the circuit
        if (!circuitOpen.get() || System.currentTimeMillis() > timeCircuitOpenedOrWasLastTested + properties.circuitBreakerSleepWindowInMilliseconds().get()) {
       	 return true;
        }
        return false;
	}
	
	protected String run() {  
		System.out.println("run");
		try {
			//Thread.sleep(10000);
		}catch(Exception e) {
			
		}
		if(i<24 && i > 12) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>" + i);
			throw new RuntimeException();
		}
		return "success";
    }
	
	 @Override
	    protected String getFallback() {
	        System.out.println("FAILBACK");
	        return "fallback: ";
	    }

	
}


