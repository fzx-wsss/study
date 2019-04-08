package com.wsss.basic.util.sign.pfx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wsss.basic.util.json.JsonUtils;


/**
 * @Description: 这里用一句话描述这个类的作用
 * @see: Signature 此处填写需要参考的类
 * @version 2016年11月18日 下午1:37:51
 * @author zhongxuan.fan
 */
public class SignatureUtils {
	private static Logger logger = LoggerFactory.getLogger(SignatureUtils.class);
	private static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	private static PrivateKey privKey;
	private static PublicKey pubKey;
	
	public static void main(String[] args) throws Exception {
		String s = "{\"BankName\":\"光大银行\",\"backUrl\":\"144.112.129.213:9099\",\"enterpriseNo\":\"47001\",\"orderId\":\"470012017072000000003\",\"accNo\":\"6226660204026278\",\"txnType\":\"12\",\"ppType\":\"0\",\"version\":\"1.0\",\"settType\":\"1\",\"txnTime\":\"20170720202132\",\"merId\":\"317113148160035\",\"payName\":\"王瑞\",\"signMethod\":\"01\",\"txnAmt\":\"1\"}";
		Properties prop = new Properties();
		prop.setProperty("mrch_cert", "C:\\Users\\hasee\\Desktop\\通道\\贵州银联\\新密钥\\server.pfx");
		prop.setProperty("mrch_cert_pwd", "1234qwer");
		prop.setProperty("epay_cert", "C:\\Users\\hasee\\Desktop\\通道\\贵州银联\\新密钥\\server_public.cer");
		
		Map<String,String> map = JsonUtils.toObject(s, HashMap.class);
		String signature = map.remove("signature");
		/*Map<String,String> map = new HashMap<String, String>();
		map.put("123", "123");*/
		
		String sign = generateMAC(prop, map);
		System.out.println(sign);
		map.put("mac", sign);
		
		System.out.println(verifyMAC(prop, map));
	}
	/**
	 * 生成签名MAC字符串
	 * @param 参数列表（包含mac参数）
	 * @return MAC字符串
	 */
	public static String generateMAC(Properties prop, Map<String, String> params) throws Exception {
		if (privKey == null) synchronized (SignatureUtils.class) {
			if (privKey == null) privKey = readPrivateKey(prop.getProperty("mrch_cert"), prop.getProperty("mrch_cert_pwd"));
		}
		Signature signature = Signature.getInstance("SHA1WithRSA");

		signature.initSign(privKey);
		signature.update(generateParamStr(params).getBytes("utf-8"));
		byte[] signed = signature.sign();

		// 计算base64encode(signed)，无换行。如无法使用，请自行替换为其它BASE64类库。
		@SuppressWarnings("restriction")
		String mac = Base64.encodeBase64String(signed).replaceAll(System.getProperty("line.separator"), "");

		return mac;
	}

	/**
	 * 验证服务器返回的信息中签名的正确性
	 * @param params 参数列表（包含mac参数）
	 * @return true-验签通过，false-验签失败
	 */
	public static boolean verifyMAC(Properties prop, Map<String, String> params) {

		if (!params.containsKey("mac")) return false;
		String mac = params.get("mac");
		if (mac == null) return false;

		try {
			if (pubKey == null) synchronized (SignatureUtils.class) {
				if (pubKey == null) pubKey = readPublicKey(prop.getProperty("epay_cert"));
			}
			java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");

			signature.initVerify(pubKey);
			signature.update(generateParamStr(params).getBytes());

			// 计算base64decode(mac)。如无法使用，请自行替换为其它BASE64类库。
			@SuppressWarnings("restriction")
			byte[] bmac = Base64.decodeBase64(mac);

			return signature.verify(bmac);
		} catch (Exception e) {
			logger.error("验证签名出错：{}", e);
			return false;
		}
	}

	/**
	 * 生成用于MAC计算的参数字符串。<br>
	 * @return 模式为key=value&key=value
	 */
	private static String generateParamStr(Map<String, String> params) {
		// 取所有非空字段内容（除mac以外），塞入列表
		List<String> paramList = new ArrayList<String>();
		for (String key : params.keySet()) {
			if ("mac".equals(key)) {
				continue;
			}
			String val = params.get(key);
			paramList.add(key + "=" + val);
		}
		// 防护
		if (paramList.size() == 0) {
			return null;
		}
		// 对列表进行排序
		Collections.sort(paramList);
		// 以&符分割拼装成字符串
		StringBuilder sb = new StringBuilder();
		sb.append(paramList.get(0));
		for (int i = 1; i < paramList.size(); i++) {
			sb.append(",").append(paramList.get(i));
		}
		return sb.toString();
	}

	/**
	 * 将byte数组转换为16进制格式的字符串
	 * @param bytes 待转换数组
	 * @return 16进制格式的字符串
	 */
	private static String bytesToHexStr(byte[] bytes) {
		StringBuffer sb = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexChar[(bytes[i] & 0xf0) >>> 4]);
			sb.append(hexChar[bytes[i] & 0x0f]);
		}
		return sb.toString();
	}

	/**
	 * SHA摘要算法，输入内容将被UTF-8编码
	 * @param content 输入明文
	 * @return 内容摘要，40位16进制字符串
	 */
	private static String encryptBySHA(String content) {
		if (content == null) return null;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] output = md.digest(content.getBytes("UTF-8"));
			return bytesToHexStr(output);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取PFX文件中的商户私钥，实际使用中可缓存该函数返回值
	 * @param certPath 证书文件路径
	 * @param password 密码
	 * @return 私钥
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 */
	private static PrivateKey readPrivateKey(String certPath, String password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
			IOException, UnrecoverableKeyException {

		KeyStore keyStore = KeyStore.getInstance("pkcs12");
		InputStream is = null;

		File file = new File(certPath);
		if (file.exists()) {
			is = new FileInputStream(certPath);
		} else {
			Class<? extends Signature> clz = Signature.class;
			ClassLoader cl = clz.getClassLoader();

			URL url = cl.getResource(certPath);
			if (url == null) throw new FileNotFoundException();

			is = cl.getResourceAsStream(certPath);
		}
		keyStore.load(is, password == null ? null : password.toCharArray());
		if (is != null) is.close();

		return (PrivateKey) keyStore.getKey(keyStore.aliases().nextElement(), password == null ? null : password.toCharArray());
	}

	/**
	 * 读取CRT文件中的银行公钥，实际使用中可缓存该函数返回值
	 * @param certPath 证书文件路径
	 * @return 公钥
	 * @throws CertificateException
	 * @throws IOException
	 */
	private static PublicKey readPublicKey(String certPath) throws CertificateException, IOException {

		File file = new File(certPath);
		InputStream is = null;
		if (file.exists()) {
			is = new FileInputStream(certPath);
		} else {
			Class<? extends Signature> clz = Signature.class;
			ClassLoader cl = clz.getClassLoader();

			URL url = cl.getResource(certPath);
			if (url == null) throw new FileNotFoundException();

			is = cl.getResourceAsStream(certPath);
		}
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(is);
		if (is != null) is.close();
		return cert.getPublicKey();
	}

	public static Map<String, String> jsonToMap(String jsonStr) {
		Map<String, String> map = JsonUtils.toObject(jsonStr, HashMap.class);
		return map;
	}
}
