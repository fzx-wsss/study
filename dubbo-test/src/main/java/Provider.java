
import java.io.IOException;

import com.wsss.frame.dubbo.service.DemoService;
import com.wsss.frame.dubbo.service.impl.DemoServiceImpl;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

public class Provider {

	public static void main(String[] args) throws IOException {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName("accountInTest");
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress("zookeeper://127.0.0.1:2181");
		ServiceConfig<DemoService> reference = new ServiceConfig<>();

		reference.setApplication(applicationConfig);
		reference.setInterface(DemoService.class);
		reference.setRef(new DemoServiceImpl());
		reference.setTimeout(30000);
		reference.setRegistry(registry);
//		reference.setFilter("masterFilter");
		reference.export();
		System.out.println("start");
		System.in.read();
	}
}
