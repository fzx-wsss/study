package com.wsss.frame.aviator;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.wsss.basic.util.json.JsonUtils;
import com.wsss.frame.aviator.function.NumberConverFunction;


public class AviatorTest {

	public static void main(String[] args) {
		AviatorEvaluator.addFunction(new NumberConverFunction());
		// TODO Auto-generated method stub
		String yourname = "aviator";
		Long x=100L;
		 Map<String, Object> env = new HashMap<String, Object>();
//		 env.put("yourname", yourname);
		// env.put("dockingOrganization", null);
		 env.put("settleAmount", "999");
		 env.put("map", env);
		// env = JsonUtils.toJsonToObject("{transAmount=5.55, INSTITUTION_BRAND_CODE=YHK, agentNo=9614232645, transType=null, requestType=PURCHASE, settleAmount=5.5, custOrganNo=29000000, optionalCustomer=false, mcc=5732, cust=Customer [customerNo=8619183510, agentNo=9614232645, shortName=艳霞丽人, fullName=北京艳霞丽人服装店, status=TRUE, organizationCode=1101, phoneNo=13552988329, receiveAddress=北京市东城区王府井大街277号, linkman=王经理, customerType=ISHUA, mcc=5732, bankMcc=5732, openTime=Mon Dec 11 17:41:03 CST 2017, processInstanceId=10980, realNameAuth=UNKNOWN, custOrganNo=29000000, ], productNo=null, order=OrderBean [amount=5.55, accountAmount=null, status=SUCCESS, institutionBrandCode=YHK, forwardInstitution=00000000, posCati=60995581, customerNo=8619183510, optimistic=1, externalId=671633997913, customerOrderCode=20180515120913104211, resultStatus=null, resultMsg=null, settleTn=null, useExcellentQuality=false, dockingOrganization=29000000, expansionInstitution=null, orderNo=BBV5SVESUBD5M0NNFEUG, businessType=null, pan=6217730708418551, completeTime=Tue May 15 12:09:27 CST 2018, customerFee=0.05, accountSubject=PAY, transType=PURCHASE]}", HashMap.class);

//		 String result = (String) AviatorEvaluator.execute(" 'hello ' + yourname ", env);
		// boolean result = (boolean) AviatorEvaluator.execute("!dddc",env);
		 Expression compiledExp = AviatorEvaluator.compile("num(settleAmount) >= 0");
		 //boolean result = (boolean)compiledExp.execute(env);
		 //boolean result = (boolean) AviatorEvaluator.execute("(1+x*x)>20 ", env);
//		    System.out.println(AviatorEvaluator.execute("add(1, 2)"));
		 System.out.println(compiledExp.execute(env));
	}

}
