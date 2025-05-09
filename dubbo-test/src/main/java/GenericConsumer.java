import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;

import static org.apache.dubbo.common.constants.CommonConstants.GENERIC_SERIALIZATION_GSON;
import static org.apache.dubbo.rpc.Constants.GENERIC_KEY;

public class GenericConsumer {
	private static ApplicationConfig applicationConfig;
	private static RegistryConfig registry;
	
	public static void main(String[] args) throws IOException {
		applicationConfig=new ApplicationConfig();
        applicationConfig.setName("accountInTest");
        registry=new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");
        registry.setPort(25012);
        Map map = new HashMap<>();
        map.put("customerId", "20170105");
        map.put("tradeAmount", new BigDecimal(4000));
        map.put("tradeType", "T0");
        map.put("serialId", UUID.randomUUID().toString().replace("-", ""));
        map.put("capitalType", "R1");
        map.put("tradeDate", new Date());
        map.put("openBank", "11111111");
        map.put("openBankName", "22222222");
		
        List<Map<String, Object>> paramInfos= new ArrayList<>();
        paramInfos.add(map);
        
        
        Object obj = genericInvoke("com.wsss.frame.dubbo.service.DemoService", "create", new String[] {"com.wsss.frame.dubbo.model.User"} ,new Object[] {"{\"name\":\"2\",\"age\":2,\"sex\":\"woman\"}"});
        //Object obj = genericInvoke("com.unionpay.financial.api.interfaces.RepayDubboService", "repay", new String[] {"com.unionpay.financial.api.bean.RepayReqBean"} ,new Object[] {map});
        System.out.println(obj);
        System.in.read();
	}
	
	public static Object genericInvoke(String interfaceClass, String methodName, String[] type,Object[] params){

        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(applicationConfig); 
        reference.setRegistry(registry); 
        reference.setInterface(interfaceClass); // 接口名 
        reference.setGeneric(GENERIC_SERIALIZATION_GSON); // 声明为泛化接口
        reference.setTimeout(30000);
        reference.setFilter("myfilter");
        
        //ReferenceConfig实例很重，封装了与注册中心的连接以及与提供者的连接，
        //需要缓存，否则重复生成ReferenceConfig可能造成性能问题并且会有内存和连接泄漏。
        //API方式编程时，容易忽略此问题。
        //这里使用dubbo内置的简单缓存工具类进行缓存
        
        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);//;
        // 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用 
            RpcContext.getContext().set(GENERIC_KEY,GENERIC_SERIALIZATION_GSON);
//        String[] invokeParamTyeps = new String[parameters.length];
//        for(int i = 0; i < parameters.entrySet(); i++){
//            invokeParamTyeps[i] = parameters[i].getClass().getTypeName();
//        }
        return genericService.$invoke(methodName, type, params);
    }
}
