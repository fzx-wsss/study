package com.wsss.frame.jmeter.first;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.wsss.basic.util.http.HttpUtils;
import com.wsss.basic.util.json.JsonUtils;

public class YjSendTest extends AbstractJavaSamplerClient {
	public static int seq = 1;
	public static int ra = new Random().nextInt(1000000);
	
	@Override
	public Arguments getDefaultParameters() {
		Arguments arguments = new Arguments();
        arguments.addArgument("publicKey", "公钥地址");
        arguments.addArgument("privateKey", "私钥地址");
        arguments.addArgument("url", "http://10.10.128.82:8083/proxy-infront/unionpay/dpay/remit");
        arguments.addArgument("password", "密码");
        
        arguments.addArgument("batchId", "order674456");
		arguments.addArgument("customerId", "unionpayCustomerCode1");
		arguments.addArgument("payee", "zhangsan");
		arguments.addArgument("bankCode", "ICBC");
		arguments.addArgument("bankName", "zhongguogongsahng");
		arguments.addArgument("bankNo", "6214830106600132");
		arguments.addArgument("accAttr", "0");
		arguments.addArgument("tradeType", "T0");
		arguments.addArgument("tradeAmount", "100000.23");
		arguments.addArgument("productCode", "fundproductcode1");
		arguments.addArgument("productName", "yuebao");
		arguments.addArgument("tradeTime", "2018-08-19");
		arguments.addArgument("createTime", "2018-08-21");
        return arguments;
	}

	@Override
	public SampleResult runTest(JavaSamplerContext context) {
		SampleResult res = new SampleResult();
		PublicKey pubk;
		try {
			context.getParameter("PublicKey");
			pubk = RSAUtils.getPubKeyFromCRT(context.getParameter("publicKey"));
			PrivateKey prik = RSAUtils.getPvkformPfx(context.getParameter("privateKey"), context.getParameter("password"));
			String reqStr = "加密证书和RSA加密解密";
			// 加密
			//Map<String, String> map = Decipher.encryptData(reqStr);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("orderId", "order"+ ra + seq++);
			map.put("batchId", "order674456");
			map.put("customerId", context.getParameter("customerId"));
			map.put("payee", context.getParameter("payee"));
			map.put("bankCode", context.getParameter("bankCode"));
			map.put("bankName", context.getParameter("bankName"));
			map.put("bankNo", context.getParameter("bankNo"));
			map.put("accAttr", context.getParameter("accAttr"));
			map.put("tradeType", context.getParameter("tradeType"));
			map.put("tradeAmount", context.getParameter("tradeAmount"));
			map.put("productCode", context.getParameter("productCode"));
			map.put("productName", context.getParameter("productName"));
			map.put("tradeTime", context.getParameter("tradeTime"));
			map.put("createTime", context.getParameter("createTime"));
			String json = JsonUtils.toJsonString(map);
			String mw = RSAUtils.encrypt(json,pubk);
			res.sampleStart();
			String s = HttpUtils.doPost(context.getParameter("createTime"), mw);
			res.sampleEnd();
			String plaintext1 = RSAUtils.decryptByPublicKey(s, pubk);
			System.out.println("公钥解密明文===="+plaintext1);
			if(plaintext1.contains("SUCCESS")) {
				res.setSuccessful(true);
			}else {
				res.setSuccessful(false);
			}
			// 解密
			//String respStr = Decipher.decryptData(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	

}
