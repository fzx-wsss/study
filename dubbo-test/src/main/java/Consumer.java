

import java.util.Arrays;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.wsss.frame.dubbo.service.DemoService;

public class Consumer {
	
	private static ApplicationConfig applicationConfig;
	private static RegistryConfig registry;
	
	public static void main(String[] args) {
		applicationConfig=new ApplicationConfig();
        applicationConfig.setName("accountInTest");
        registry=new RegistryConfig();
        
        registry.setAddress("zookeeper://127.0.0.1:2181");
        
        Class<?> interfaceClass=DemoService.class;
        //  ApplicationConfig applicationConfig=new ApplicationConfig();
        //  applicationConfig.setName("accountInTest");
        ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
        //   RegistryConfig registry = new RegistryConfig();

        reference.setApplication(applicationConfig);
        reference.setInterface(interfaceClass);
        reference.setTimeout(30000);
        reference.setRegistry(registry);
        reference.setRetries(0);
        ProtocolConfig dubbo = new ProtocolConfig("dubbo");
        dubbo.setPort(20881);
        ProtocolConfig http = new ProtocolConfig("http");
        //http.setPort(20881);
        reference.setProtocol("http");
        DemoService service = reference.get();
        System.out.println(service.sayHello("123"));
	}
}
