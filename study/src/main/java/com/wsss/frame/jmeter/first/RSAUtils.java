package com.wsss.frame.jmeter.first;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.wsss.basic.util.json.JsonUtils;



public class RSAUtils {
	/** 指定key的大小 */
	public static final String CHAR_ENCODING = "UTF-8";
	public static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";
	/** RSA最大加密明文大小*/
	private static final int MAX_ENCRYPT_BLOCK = 116;
	/** RSA最大解密密文大小*/
	private static final int MAX_DECRYPT_BLOCK = 128;



	public static String encrypt(String source, PublicKey pubKeyFromCrt) throws Exception {
		Key key = pubKeyFromCrt;
		/** 得到Cipher对象来实现对源数据的RSA加密 */
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] b = source.getBytes();
		/** 执行加密操作 */
		//byte[] b1 = cipher.doFinal(b);
		int inputLen = b.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(b, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(b, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return new String(Base64.encodeBase64(encryptedData), CHAR_ENCODING);
	}

	/**
	 * 私钥证书解密算法 cryptograph:密文
	 */
	public static String decrypt(String cryptograph, PrivateKey privateKey) throws Exception {
		Key key = privateKey;
		/** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] b1 = Base64.decodeBase64(cryptograph.getBytes());
		/** 分段解密操作 */
		//byte[] b = cipher.doFinal(b1);
		int inputLen = b1.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(b1, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(b1, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return new String(decryptedData);
	}

	/**
	 * <p>
	 * 私钥加密
	 * </p>
	 *
	 * @param data
	 *            源数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPrivateKey(String source, PrivateKey privateKey) throws Exception {

		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		byte[] b = source.getBytes();
		int inputLen = b.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(b, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(b, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return new String(Base64.encodeBase64(encryptedData), CHAR_ENCODING);
	}
	/**
	 * <p>
	 * 公钥解密
	 * </p>
	 *
	 * @param encryptedData
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPublicKey(String encryptedData, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] b = Base64.decodeBase64(encryptedData.getBytes());
		int inputLen = b.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(b, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(b, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return new String(decryptedData);
	}

	/**
	 * 通过PFX文件获得私钥
	 *
	 * @param //文件路径
	 * @param //PFX密码
	 * @return PrivateKey
	 */
	public static PrivateKey getPvkformPfx(String strPfx, String strPassword) throws Exception {
		PrivateKey prikey = null;
		char[] nPassword = null;
		if ((strPassword == null) || strPassword.trim().equals("")) {
			nPassword = null;
		} else {
			nPassword = strPassword.toCharArray();
		}
		KeyStore ks = getKsformPfx(strPfx, strPassword);
		String keyAlias = getAlsformPfx(strPfx, strPassword);
		prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
		return prikey;
	}

	/**
	 * 通过PFX文件获得KEYSTORE
	 *
	 * @param //文件路径
	 * @param //PFX密码
	 * @return KeyStore
	 */
	public static KeyStore getKsformPfx(String strPfx, String strPassword) throws Exception {
		FileInputStream fis = null;
		Security.addProvider(new BouncyCastleProvider());

		KeyStore ks = KeyStore.getInstance("PKCS12", "BC");
		fis = new FileInputStream(strPfx);
		// If the keystore password is empty(""), then we have to set
		// to null, otherwise it won't work!!!
		char[] nPassword = null;
		if ((strPassword == null) || strPassword.trim().equals("")) {
			nPassword = null;
		} else {
			nPassword = strPassword.toCharArray();
		}
		ks.load(fis, nPassword);
		if (null != fis) {

			fis.close();

		}
		return ks;

	}

	/**
	 * 通过PFX文件获得别名
	 *
	 * @param //文件路径
	 * @param //PFX密码
	 * @return 别名
	 */
	public static String getAlsformPfx(String strPfx, String strPassword) throws Exception {
		String keyAlias = null;
		KeyStore ks = getKsformPfx(strPfx, strPassword);
		Enumeration<String> enumas = ks.aliases();
		keyAlias = null;
		// we are readin just one certificate.
		if (enumas.hasMoreElements()) {
			keyAlias = (String) enumas.nextElement();
		}
		return keyAlias;
	}

	/**
	 *
	 * @Description:获取公钥 @param crtFileName，公钥文件路径 @return @throws Exception
	 * PublicKey @throws
	 */
	public static PublicKey getPubKeyFromCRT(String crtFileName) throws Exception {
		InputStream is = new FileInputStream(crtFileName);
		CertificateFactory cf = CertificateFactory.getInstance("x509");
		Certificate cerCert = cf.generateCertificate(is);
		return cerCert.getPublicKey();
	}

	public static void main(String[] args) {
		try {
			PublicKey pubk = getPubKeyFromCRT("H:\\key\\public-rsa.cer");
			PrivateKey prik = getPvkformPfx("H:\\key\\private-rsa.pfx", "es2018");
			String reqStr = "加密证书和RSA加密解密";
			// 加密
			//Map<String, String> map = Decipher.encryptData(reqStr);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("requestId", "request1234567890");
			map.put("customerId", "unionpayCustomerCode1");
			map.put("applyAmount", "1000000");
			map.put("transType", "T0");
			map.put("requestDate", "2018-08-19");
			String json =JsonUtils.toJsonString(map);
			String mw = encrypt(json,pubk);
			System.out.println("密文===="+mw);
			String plaintext = decrypt(mw,prik);
			System.out.println("明文===="+plaintext);
			// 解密chByopmsUYpZ1peP2QrfoBXJrEXM4LjoQgywka8xSxG8eLD15gMyJ/WEYdNvu2aBJrVvFaJRtxsEq4Qv0wDBExaPqw7CeiYNs5CDPjMmwB4ooqOc9T7H0XOwkMaeskAKegrQ8YHvkakRJkVfzK8r+r6B1h2yh0om9j3umXYW5R0NOdr/JzlyGaVkto1CD0RqfrInyjyu58JldLqDqc5tWIkZEp4hWbVjrzPalG8CCVFOIwkq+YSJt3piBxMZ4/ho1vQN0/cWOpNoyZ3lJoNCKfQC9uoP6KNwBS3PxMhbojFAjh2wUYx49nfh3Skwiq5TCZdQq/9LrL1gmlUPc2tQoA==
			//String respStr = Decipher.decryptData(map);
			//System.out.println("解密后的数据为=============>" + respStr);
			System.out.println("公钥字符串="+Base64.encodeBase64String(pubk.getEncoded()));
			System.out.println("私钥字符串="+Base64.encodeBase64String(prik.getEncoded()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
