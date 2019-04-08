package com.wsss.basic.common;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
//import java.util.Base64;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;


/**
 * 使用keystore中的jks私钥加密，验签
 * @author hasee
 *
 */
public final class SignatureUtils {


	// share by sender and receiver
	public static Signature sign = null;
	// belong to sender,it visible to sender and receiver
	public static PublicKey publicKey = null;
	// belong to sender,it is only visible to sender
	public static RSAPrivateKey privateKey = null;
	
	public static String publicKeyCer = "C:\\Users\\hasee\\Desktop\\通道\\昆明民生\\生产\\lefu8.cer";
	public static String keyStore = "C:\\Users\\hasee\\Desktop\\通道\\昆明民生\\生产\\lefu8.keystore";
	public static String alias = "lefu8";
	public static String pwd = "lefu81234";

	public static void initPrivateKey(String priKeyPath, String alias, String pswd) {
		if (privateKey == null) {
			synchronized (SignatureUtils.class) {
				if (privateKey == null) {
					FileInputStream in = null;
					// 获取私钥初始化
					try {
						in = new FileInputStream(priKeyPath);
						KeyStore ks = KeyStore.getInstance("JKS");
						ks.load(in, pswd.toCharArray());
						privateKey = (RSAPrivateKey) ks.getKey(alias, pswd.toCharArray());
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							if (in != null) in.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

	}

	public static void initPublicKey(String cerPath) {

		if (publicKey == null) {
			synchronized (SignatureUtils.class) {
				if (publicKey == null) {
					// 获取公钥初始化
					try {
						CertificateFactory certFactoryPub = CertificateFactory.getInstance("X.509");
						X509Certificate merchantCertPub = (X509Certificate) certFactoryPub.generateCertificate(new FileInputStream(cerPath));
						publicKey = merchantCertPub.getPublicKey();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	/**
	 * 签名
	 * @param
	 */
	public static String createSignature(String dataString, Properties prop) {
		byte[] dataSha = DigestUtils.sha(dataString.getBytes());
		String signString = null;
		try {
			initPrivateKey(keyStore, alias, pwd);
			signString = signByPrivateKey(privateKey, dataSha);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signString;
	}

	public static String signByPrivateKey(RSAPrivateKey privateKey, byte data[]) throws Exception {
		Signature st = Signature.getInstance("SHA1withRSA");
		st.initSign(privateKey);
		st.update(data);
		// return new String(Base64.getEncoder().encode(st.sign()));
		return new String(Base64.encodeBase64(st.sign()));
	}

	/**
	 * 验签
	 * @param
	 */

	public static boolean validateSign(String checkSinStr, String sinStr, Properties prop) {
		try {
			Signature sig = Signature.getInstance("SHA1WithRSA");
			initPublicKey(publicKeyCer);
			sig.initVerify(publicKey);
			sig.update(DigestUtils.sha(checkSinStr.getBytes("UTF-8")));
			// return sig.verify(Base64.getDecoder().decode(sinStr.getBytes("UTF-8")));
			return sig.verify(Base64.decodeBase64(sinStr.getBytes("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) {
		
		String s = "merNum=305501559339241&orderId=B160824013714&UBSR5VV2F9jPBGjdHqVMnv/mvIo75YZmY/KMtDMV4W1M8D88c8tsN5wuBnDr7+qdvmfOWNJK5yGq2K6PcKVaaID/DGtY1dSeAI5KkNQDLPayn+GoerqGSxtQbTZ3fk2k+g/JqX9rx6i1klSZmxvO1XcVEa7qlPcT5t7HkkSKojxfwfj6bB+N+EiSbKOMBtu2ng2+KAODY1YCdRaIG/MsLknP7MX9APOG1g8VPTL5Y7iRN8yQ8Wkk67auOaGNug4IwZcTzurOX2qT/UF14CLnPSnLBk4zakwq5EoAwo4mZa1YwrmpRLxcJp7tVsFDcnVqT9iVF6fAOkV5c25zz4X75A==";
		String sign = createSignature(s, null);
		System.out.println(sign);
		
		System.out.println(validateSign(s, sign, null));
		
	}

}
