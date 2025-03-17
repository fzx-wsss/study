
import java.io.IOException;
import java.util.Arrays;

import com.superatomfin.hubble.api.FollowOrderQueryService;
import com.superatomfin.hubble.api.model.FollowOrderQueryByPageRequest;
import com.superatomfin.hubble.api.model.FollowOrderQueryRequest;
import com.wsss.frame.dubbo.service.DemoService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

public class Consumer {

	public static void main(String[] args) throws IOException {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName("accountInTest");
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress("zookeeper://10.48.1.131:2181");

		ReferenceConfig<FollowOrderQueryService> reference = new ReferenceConfig<>();
		reference.setApplication(applicationConfig);
		reference.setInterface(FollowOrderQueryService.class);
		reference.setVersion("1.0.0");
//		reference.setFilter("clientFilter");
		reference.setRegistry(registry);
		FollowOrderQueryService service = reference.get();
		String s = "{\"userId\":12345,\"chain\":\"sol\",\"followOrderId\":15,\"status\":[\"FOLLOWING\",\"PAUSED\"],\"profit\":true}";
		FollowOrderQueryByPageRequest request = new FollowOrderQueryByPageRequest();
		request.setUserId(12345L);
		request.setChain("sol");
		request.setStatus(Arrays.asList("FOLLOWING","PAUSED"));
		request.setProfit(true);
		request.setChain("sol");
		while (true) {
			System.in.read();
			System.out.println(service.queryFollowOrdersByPage(request));
		}

	}
}
