
import java.io.IOException;

import com.wsss.frame.dubbo.service.DemoService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

public class Consumer {

	public static void main(String[] args) throws IOException {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName("accountInTest");
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress("zookeeper://10.48.1.124:2181");

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
