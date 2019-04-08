import java.util.HashMap;
import java.util.Map;

import com.googlecode.aviator.AviatorEvaluator;


public class AviatorTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String yourname = "aviator";
		Long x=100L;
		 Map<String, Object> env = new HashMap<String, Object>();
//		 env.put("yourname", yourname);
		 env.put("x", false);

//		 String result = (String) AviatorEvaluator.execute(" 'hello ' + yourname ", env);
		 boolean result = (boolean) AviatorEvaluator.execute("x || false ",env);
		 //boolean result = (boolean) AviatorEvaluator.execute("(1+x@x)>2 ", env);
//		    System.out.println(AviatorEvaluator.execute("add(1, 2)"));
		 System.out.println(result);
	}

}
