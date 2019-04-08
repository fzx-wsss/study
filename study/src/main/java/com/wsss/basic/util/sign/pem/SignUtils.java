package com.wsss.basic.util.sign.pem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 签名工具类
 * @author JinLei
 */
public class SignUtils {
	private static Logger logger = LoggerFactory.getLogger(SignUtils.class);
	public static final String SIGN_SHA1_WITH_RSA = "SHA1WithRSA";
	private static volatile String privateKeyStr = null;
	private static volatile String publicKeyStr = null;

	private static synchronized void initPrivateKey(String path) throws Exception {
		if (privateKeyStr != null) return;
		BufferedReader bis = null;
		try {
			bis = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
			privateKeyStr = bis.readLine();
			logger.info("private key:{}", privateKeyStr);
		} finally {
			try {
				if (bis != null) bis.close();
			} catch (Exception e) {
				logger.error("{}", e);;
			}
		}
	}

	private static synchronized void initPublicKey(String path) throws Exception {
		if (publicKeyStr != null) return;
		BufferedReader bis = null;
		try {
			bis = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
			publicKeyStr = bis.readLine();
			logger.info("public key:{}", publicKeyStr);
		} finally {
			try {
				if (bis != null) bis.close();
			} catch (Exception e) {
				logger.error("{}", e);;
			}
		}
	}

	public static String signBySoft(String privateKey, String signStr) throws Exception {
		return signBySoft(privateKey, signStr, 1024);
	}

	public static String signBySoft(String privateKey, String signStr, int modulus) throws Exception {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
			byte[] sha1 = messageDigest.digest(signStr.getBytes("utf-8"));

			byte[] encryptxtArr = RsaUtils.encryptByPrivateKey(Base64.getDecoder().decode(privateKey.getBytes("utf-8")), sha1, modulus);

			encryptxtArr = Base64.getEncoder().encode(encryptxtArr);
			return new String(encryptxtArr, "utf-8");
		} catch (Exception e) {
			logger.error("签名异常：{}", e);
			throw new Exception("软签名异常：" + e.getMessage());
		}
	}

	public static boolean verifyingSign(String publicKey, String signCipher, String signStr, int modulus) throws Exception {
		try {
			byte[] signCipherArr = RsaUtils.decryptByPublicKey(Base64.getDecoder().decode(publicKey.getBytes("utf-8")),
					Base64.getDecoder().decode(signCipher.getBytes("utf-8")), modulus);

			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
			byte[] sha1Bytes = messageDigest.digest(signStr.getBytes("utf-8"));

			return Arrays.equals(sha1Bytes, signCipherArr);
		} catch (Exception e) {
			logger.error("验签异常：{}", e);
			throw new Exception("验签异常：" + e.getMessage());
		}
	}

	public static boolean verifyingSign(String publicKey, String signCipher, String signStr) throws Exception {
		return verifyingSign(publicKey, signCipher, signStr, 1024);
	}

	/**
	 * RSA签名
	 * @param content 待签名数据
	 * @param privateKey 商户私钥
	 * @return 签名值
	 */
	public static String signBySoftSha1WithRSA(String content, String privateKey) throws Exception {
		if (privateKeyStr == null) {
			initPrivateKey(privateKey);
		}

		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyStr.getBytes("utf-8")));
		KeyFactory keyf = KeyFactory.getInstance("RSA");
		PrivateKey priKey = keyf.generatePrivate(priPKCS8);

		Signature signature = Signature.getInstance(SIGN_SHA1_WITH_RSA);

		signature.initSign(priKey);
		signature.update(content.getBytes("utf-8"));

		byte[] signed = signature.sign();

		return new String(Base64.getEncoder().encode(signed), "utf-8");

	}

	/**
	 * 验签
	 * @param publicKey 公钥
	 * @param signCipher 签名
	 * @param signStr 待验签数据
	 * @return 验签是否通过
	 * @throws Exception
	 */
	public static boolean verifyingSignSha1WithRSA(String publicKey, String signCipher, String signStr) throws Exception {
		if (publicKeyStr == null) {
			initPublicKey(publicKey);
		}
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr.getBytes())));

		Signature signature = Signature.getInstance(SIGN_SHA1_WITH_RSA);
		signature.initVerify(pubKey);
		signature.update(signStr.getBytes("utf-8"));
		return signature.verify(Base64.getDecoder().decode(signCipher.getBytes("utf-8")));
	}

	/**
	 * 获得特定的待签名数据
	 * @param jsonObject
	 * @return
	 */
	public static String getSignData(Map<String, String> map) {
		map.remove("sign");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (isNotEmpty(entry.getValue())) {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
				resultMap.put(entry.getKey(), entry.getValue());
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		// 排序
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		return sb.toString().substring(0, sb.length() - 1);
	}

	public static boolean isNotEmpty(String str) {
		if (str != null && !"".equals(str) && !"null".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		try {
			String signData = "custOrderId=2220120414000000002&retCode=SIGN_EXCEPTION&retDesc=验签不合格或者无权限&";
			String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAOg+PNOota7ppeHwDBB7zttL/OpUmaOCbei/J2t/FLNjumsMjJVGLfdxKlrqCQXVAzAZDIakQcbfRvvlhZJCEBAnL2tru8Qp6hs9dokI2zOXjf9wyk4hPQT5/noXEnlnXO1lr4MZeXhgLKCwZOQfTeE9WSWuTmdA5Dzgu0d/TvuJAgMBAAECgYBBEqdCeyQlFWyYaQVIXRhx09HS6s99xB79twnZker/9LKYKhT+AoMAsSG4BZlvm+bfxDUBSObxTUB7di099OrAw0J1F0QpCXL5Jrxc2NdW8/j1hXb77UbdgsUZg4hM5JkJ2QRxiwT0JyWUAIikSx0W+jUzTFkz1UFaMiZOwEX7rQJBAPcd1+dvclPiR77McvxEpje04dddIiGIQxCw7oZmARMOK33Jrd3+6nTv8xlhFSWM9/xgJzQq8n+aZ4X9+ZXEp9sCQQDwl4RolGsvtp/8jyFBRNGOTQ6CWM/77lK47swzeu50GCFNyf+tLNu8kOhyk+8LIftKFm44m8PZrsZDYZLNDGlrAkA6n0bHrWWGxshUV/XzKGnyDyQATiS5pbSbMg3zriEVHyhsF7r6Te3avc2CuMgmd1Gg+kJymrmaUcu7OqvJvrQ/AkBFQylwPgIZi1bFi6MEOj6l29MofU7q9TJFYSHSVDqfm27DCTsc7MQZphH1Ild3+gFw08JJc7ZPTbxwG3/6ne8fAkBDiUWE/r5GAVrLDFXIqglkG+25B0LPGT2ttTNL2Id/4QAbUXLxGuwmBY52B8m2Y2U8agJDL6YdsE+gvykcH9oB";
			// 签名过的字符串
			String sign = SignUtils.signBySoft(privateKey, signData);
			String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDoPjzTqLWu6aXh8AwQe87bS/zqVJmjgm3ovydrfxSzY7prDIyVRi33cSpa6gkF1QMwGQyGpEHG30b75YWSQhAQJy9ra7vEKeobPXaJCNszl43/cMpOIT0E+f56FxJ5Z1ztZa+DGXl4YCygsGTkH03hPVklrk5nQOQ84LtHf077iQIDAQAB";

			// 字符未改变验签。
			boolean signResult = SignUtils.verifyingSign(publicKey, sign, signData);
			System.out.println("正面预期(true)：" + signResult);

			// 字符稍微改变验签。
			signData += "d";
			signResult = SignUtils.verifyingSign(publicKey, sign, signData);
			System.out.println("反面预期(false)：" + signResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}