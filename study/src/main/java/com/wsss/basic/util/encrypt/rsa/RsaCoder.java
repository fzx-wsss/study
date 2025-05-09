package com.wsss.basic.util.encrypt.rsa;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.apache.poi.util.SystemOutLogger;

/**
 * @author qichenchen
 * @date 18-12-14
 */
public class RsaCoder {
	/**
	 * 数字签名，密钥算法
	 */
	public static final String KEY_ALGORITHM = "RSA";
	/**
	 * 数字签名 签名/验证算法
	 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/** 数据集 */
	public static final String CHAR_SET = "UTF-8";

	/**
	 * RSA密钥长度，RSA算法的默认密钥长度是1024 密钥长度必须是64的倍数，在512到65536位之间
	 */
	private static final int KEY_SIZE = 512;

	/**
	 * RSA最大加密 明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = KEY_SIZE / 8 - 11;

	/**
	 * RSA最大解密 密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = KEY_SIZE / 8;
	/**
	 * 公钥
	 */
	private static final String PUBLIC_KEY = "RSAPublicKey";
	/**
	 * 私钥
	 */
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	// private static final Base64.Decoder decoder = Base64.getDecoder();
	//
	// private static final Base64.Encoder encoder = Base64.getEncoder();

	/**
	 * 初始化密钥对
	 *
	 * @return Map 甲方密钥的Map
	 */
	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGenerator.initialize(KEY_SIZE);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * 取得私钥
	 *
	 * @param keyMap
	 *            密钥map
	 * @return String 私钥
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return HexBin.encode(key.getEncoded());
	}

	/**
	 * 取得公钥
	 *
	 * @param keyMap
	 *            密钥map
	 * @return String 公钥
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return HexBin.encode(key.getEncoded());
	}

	/**
	 * 取得私钥
	 *
	 * @param keyMap
	 *            密钥map
	 * @return RSAPrivateKey 私钥
	 */
	public static RSAPrivateKey getRSAPrivateKey(Map<String, Object> keyMap) throws Exception {
		RSAPrivateKey key = (RSAPrivateKey) keyMap.get(PRIVATE_KEY);
		return key;
	}

	/**
	 * 取得公钥
	 *
	 * @param keyMap
	 *            密钥map
	 * @return RSAPublicKey 公钥
	 */
	public static RSAPublicKey getRSAPublicKey(Map<String, Object> keyMap) throws Exception {
		RSAPublicKey key = (RSAPublicKey) keyMap.get(PUBLIC_KEY);
		return key;
	}

	/**
	 * 使用私钥签名
	 *
	 * @param data
	 *            待签名字符串
	 * @param privateKey
	 *            签名使用的私钥
	 * @return
	 * @throws Exception
	 */
	public static String sign(String data, String privateKey) throws Exception {
		return HexBin.encode(sign(data.getBytes(CHAR_SET), HexBin.decode(privateKey)));
	}

	/**
	 * 签名
	 *
	 * @param data
	 *            待签名数据
	 * @param privateKey
	 *            密钥
	 * @return byte[] 数字签名
	 */
	public static byte[] sign(byte[] data, byte[] privateKey) throws Exception {
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);
		return signature.sign();
	}

	/**
	 * 验证签名
	 *
	 * @param data
	 *            待验证数据
	 * @param publicKey
	 *            验证签名使用的公钥
	 * @param sign
	 *            签名字符串
	 * @return
	 */
	public static boolean verify(String data, String publicKey, String sign) throws Exception {
		return verify(data.getBytes(CHAR_SET), HexBin.decode(publicKey), HexBin.decode(sign));
	}

	/**
	 * 校验数字签名
	 *
	 * @param data
	 *            待校验数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * @return boolean 校验成功返回true，失败返回false
	 */
	public static boolean verify(byte[] data, byte[] publicKey, byte[] sign) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);
		return signature.verify(sign);
	}

	/**
	 * <p>
	 * 私钥解密
	 * </p>
	 *
	 * @param encryptedData
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(String encryptedData, String privateKey) throws Exception {
		byte[] bytes = decryptByPrivateKey(HexBin.decode(encryptedData), HexBin.decode(privateKey));
		return new String(bytes, CHAR_SET);
	}

	/**
	 * <P>
	 * 私钥解密
	 * </p>
	 *
	 * @param encryptedData
	 *            已加密数据
	 * @param privateKey
	 *            私钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] encryptedData, byte[] privateKey) throws Exception {
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		byte[] decryptedData = rsaCipherDoFinal(cipher, encryptedData, MAX_DECRYPT_BLOCK);
		return decryptedData;
	}

	/**
	 * 公钥解密
	 * 
	 * @param encryptedData
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPublicKey(String encryptedData, String publicKey) throws Exception {
		return new String(decryptByPublicKey(HexBin.decode(encryptedData), HexBin.decode(publicKey)), CHAR_SET);
	}

	/**
	 * <p>
	 * 公钥解密
	 * </p>
	 *
	 * @param encryptedData
	 *            已加密数据
	 * @param publicKey
	 *            公钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] encryptedData, byte[] publicKey) throws Exception {
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		byte[] decryptedData = rsaCipherDoFinal(cipher, encryptedData, MAX_DECRYPT_BLOCK);
		return decryptedData;
	}

	/**
	 * <p>
	 * 公钥加密
	 * </p>
	 *
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String data, String publicKey) throws Exception {
		return HexBin.encode(encryptByPublicKey(data.getBytes(CHAR_SET), HexBin.decode(publicKey)));
	}

	/**
	 * <p>
	 * 公钥加密
	 * </p>
	 *
	 * @param data
	 *            源数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey) throws Exception {
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		byte[] encryptedData = rsaCipherDoFinal(cipher, data, MAX_ENCRYPT_BLOCK);
		return encryptedData;
	}

	/**
	 * <p>
	 * 私钥加密
	 * </p>
	 * 
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPrivateKey(String data, String privateKey) throws Exception {

		return HexBin.encode(encryptByPrivateKey(data.getBytes(CHAR_SET), HexBin.decode(privateKey)));
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
	public static byte[] encryptByPrivateKey(byte[] data, byte[] privateKey) throws Exception {
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, privateK);
		byte[] encryptedData = rsaCipherDoFinal(cipher, data, MAX_ENCRYPT_BLOCK);
		return encryptedData;
	}

	/**
	 * The RSA algorithm can only encrypt data that has a maximum byte length of
	 * the RSA key length in bits divided with eight minus eleven padding bytes,
	 * i.e. number of maximum bytes = key length in bits / 8 - 11.
	 *
	 * @param cipher
	 *            密码核心
	 * @param data
	 *            待处理数据
	 * @param blockSize
	 *            处理数据的块大小限制
	 * @return
	 */
	private static byte[] rsaCipherDoFinal(Cipher cipher, byte[] data, int blockSize) {
		byte[] encryptedData = null;
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			int inputLen = data.length;
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > blockSize) {
					cache = cipher.doFinal(data, offSet, blockSize);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				offSet = ++i * blockSize;
			}
			encryptedData = out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedData;
	}

	public static void main2(String[] args) throws Exception {

		Map<String, Object> initKey = RsaCoder.initKey();
		// String publicKey = RsaCoder.getPublicKey(initKey);
		String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALglzk0MKlvggvLl150UZCMictCM9JS+ix65lcbVbVBZU0JfKEl/KjUfe1ohScR0eFFDvgKrDp6J0hDS9xGMPFECAwEAAQ==";
		System.out.println("公钥：" + publicKey);
//		String privateKey = RsaCoder.getPrivateKey(initKey);
		String privateKey = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAuCXOTQwqW+CC8uXXnRRkIyJy0Iz0lL6LHrmVxtVtUFlTQl8oSX8qNR97WiFJxHR4UUO+AqsOnonSENL3EYw8UQIDAQABAkAfEMGs3dCHj2iH8Z5eMYJzKuyTojPgpAR0Onq6AGdglmfVNgiXGR0suC6OWou12hLKBOhrty50HXXc11p3Z50BAiEA4cF75xg3e2zYLw1DcsNAa4AHk6bj9JiUFtMDJRs6vG0CIQDQ0VSr9NpK/SH9PLmrpk1ptmHlzkO3lcy/ctrD6ayI9QIgCrkvEn4S/Jjh9/IwVv4xTCnrsjbGMzMgLaleR9/wPU0CIQCslWYKnNrSAXZXCxpVcKQoi7FLsncrQyau0GlYZEfSBQIhAMjkpq7zTV37kZC4/t94tmNzT85/NaFqXCuw5nQs2dKw";
		System.out.println("私钥：" + privateKey);

		String content = "华股份\"},\"600828.SH\":{\"preClose\":3.59,\"high\":3.95,1";
		// RSA公钥加密
		String newContent = HexBin.encode(RsaCoder.encryptByPublicKey(content.getBytes(StandardCharsets.UTF_8)
				, Base64.getDecoder().decode(publicKey)));
		System.out.println(newContent);
		// RSA私钥解密
		// String new2Content = RsaCoder.decryptByPrivateKey(newContent,
		// privateKey);
		// System.out.println(new2Content);

		String content2 = "17606760989";
		// RSA私钥加密
		String newContent2 = new String(RsaCoder.decryptByPrivateKey(HexBin.decode(newContent)
				, Base64.getDecoder().decode(privateKey)));
		System.out.println(newContent2);
		// RSA公钥解密
		// String new2Content2 = RsaCoder.decryptByPublicKey(newContent2,
		// publicKey);
		// System.out.println(new2Content2);
	}

	public static void main(String[] args) {
		String dex = "6f77223a32302e392c226368616e6765526174696f223a31392e39383131303533342c226c6174657374223a32352e342c22616d6f756e74223a322e38343634303645382c22766f6c756d65223a312e313931333245372c226f70656e223a32312e32312c226e616d65223a22e5a4a9e58583e5ae";
		System.out.println(new String(HexBin.decode(dex), Charset.forName("utf-8")));
	}
}
