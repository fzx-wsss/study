
import java.io.IOException;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.wsss.frame.dubbo.service.DemoService;

public class Consumer {

	public static void main(String[] args) throws IOException {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName("accountInTest");
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress("zookeeper://127.0.0.1:2181");

		ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
		reference.setApplication(applicationConfig);
		reference.setInterface(DemoService.class);
		reference.setRegistry(registry);
		DemoService service = reference.get();
		while (true) {
			System.in.read();
			System.out.println(service.sayHello("123"));
		}

	}
}
