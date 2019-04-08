import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;


public class TestJson extends Test {
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		// { "aaa":"111", "bbb":"222", "ccc":"333" }
	    String jsonInput = "{ \"aaa\":\"111\",\"bbb\":\"222\",\"ccc\":\"333\" }";

	    ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD,
	                         Visibility.ANY);
	    mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
	                     false);

	    Test test = mapper.readValue(jsonInput, Test.class);
	    System.out.println(test);
	}
	
	
}
