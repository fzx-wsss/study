

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CipherUtil {
	private static Logger logger = LoggerFactory.getLogger(CipherUtil.class);

	/**
	 * encrypt base on DES or 3DES or AES.
	 * @param target
	 *            The target (which is used to encrypt)
	 * @param algorithm
	 *            The algorithm can be DES or DESede or AES
	 * @param key
	 *            The key (which is key to handle this encrypt)
	 * @return
	 */
	public static byte[] encrypt(byte[] targetToByte, String algorithm,String mode, String key) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, getKey(key, mode));
		byte[] result = cipher.doFinal(targetToByte);
		// System.out.println("base64:" +encryptByBase64(result));
		return result;
	}

	/**
	 * decrypt base on DES or 3DES or AES.
	 * @param target
	 *            The target (which is used to decrypt)
	 * @param algorithm
	 *            The algorithm can be DES or DESede or AES
	 * @param key
	 *            The key (which is key to handle this decrypt)
	 * @return
	 */
	public static byte[] decrypt(byte[] targetToByte, String algorithm,String mode, String key) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, getKey(key, mode));
		byte[] result = cipher.doFinal(targetToByte);
		// System.out.println("base:" + new String(result,"UTF-8"));
		return result;
	}

	public static Key getKey(String key, String algorithm) {
		try {
			KeyGenerator generator = KeyGenerator.getInstance(algorithm);
			generator.init(new SecureRandom(key.getBytes()));
			return generator.generateKey();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	// 从十六进制字符串到字节数组转换
	public static byte[] HexString2Bytes(String hexstr) {
		byte[] b = new byte[hexstr.length() / 2];
		int j = 0;
		for (int i = 0; i < b.length; i++) {
			char c0 = hexstr.charAt(j++);
			char c1 = hexstr.charAt(j++);
			b[i] = (byte) ((parse(c0) << 4) | parse(c1));
		}
		return b;
	}

	private static int parse(char c) {
		if (c >= 'a') return (c - 'a' + 10) & 0x0f;
		if (c >= 'A') return (c - 'A' + 10) & 0x0f;
		return (c - '0') & 0x0f;
	}

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	
	/** 
     * ECB加密,不要IV 
     * @param key 密钥 
     * @param data 明文 
     * @return Base64编码的密文 
     * @throws Exception 
     */  
    public static byte[] des3EncodeECB(byte[] key, byte[] data)  
            throws Exception {  
        Key deskey = null;  
        SecretKeySpec spec = new SecretKeySpec(key,"desede");  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/NoPadding");  
        cipher.init(Cipher.ENCRYPT_MODE, deskey);  
        byte[] bOut = cipher.doFinal(data);  
        return bOut;  
    }  
    /** 
     * ECB解密,不要IV 
     * @param key 密钥 
     * @param data Base64编码的密文 
     * @return 明文 
     * @throws Exception 
     */  
    public static byte[] des3DecodeECB(byte[] key, byte[] data)  
            throws Exception {  
        Key deskey = null;  
        SecretKeySpec spec = new SecretKeySpec(key,"desede");   
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance("DESede" + "/ECB/NoPadding");  
        cipher.init(Cipher.DECRYPT_MODE, deskey);  
        byte[] bOut = cipher.doFinal(data);  
        return bOut;  
    } 

	public static void main(String[] args) throws Exception {
		String target = "8FC3109372A1FDD5";
		// String target = "test";
		// DES
		// String encrypt = encrypt(target, "DES", "im a key");
		// System.out.println("加密后：" + encrypt);
		// String decrypt = decrypt(encrypt, "DES", "im a key");
		// System.out.println("解密后：" + decrypt);
		// 3DES
		byte[] encrypt = encrypt(target.getBytes(), "DESede/ECB/NoPadding","DESede", "08000800094440004142410641404304");
		System.out.println("加密后：" + new String(Base64.getEncoder().encode(encrypt)));
		byte[] decrypt2 = decrypt("8FC3109372A1FDD5".getBytes(), "DESede/ECB/NoPadding","DESede", "08000800094440004142410641404304");
		System.out.println("解密后：" + new String(decrypt2));

		// AES
		/*
		 * String encrypt = encrypt(target, "AES", "im a key");
		 * System.out.println("加密后：" + encrypt); String decrypt =
		 * decrypt(encrypt, "AES", "im a key"); System.out.println("解密后：" +
		 * decrypt);
		 */
		 
		 byte[] aa = des3EncodeECB(HexString2Bytes("080008000944400041424106414043040800080009444000"), HexString2Bytes("4434413946333539"));
		 System.out.println("加密后：" + bytesToHexString(aa));
		 byte[] bb = des3DecodeECB(HexString2Bytes("080008000944400041424106414043040800080009444000"), HexString2Bytes("8FC3109372A1FDD5"));
		 System.out.println("解密后：" + bytesToHexString(bb));
	}
	
	
	public static String bytesToAscii(byte[] bytes, int offset, int dateLen) {  
        if ((bytes == null) || (bytes.length == 0) || (offset < 0) || (dateLen <= 0)) {  
            return null;  
        }  
        if ((offset >= bytes.length) || (bytes.length - offset < dateLen)) {  
            return null;  
        }  
  
        String asciiStr = null;  
        byte[] data = new byte[dateLen];  
        System.arraycopy(bytes, offset, data, 0, dateLen);  
        try {  
            asciiStr = new String(data, "ISO8859-1");  
        } catch (UnsupportedEncodingException e) {  
        	e.printStackTrace();
        }  
        return asciiStr;  
    }  
  
    public static String bytesToAscii(byte[] bytes, int dateLen) {  
        return bytesToAscii(bytes, 0, dateLen);  
    }  
  
    public static String bytesToAscii(byte[] bytes) {  
    	StringBuffer  tStringBuf=new StringBuffer ();

        char[] tChars=new char[bytes.length];
        
        for(int i=0;i<tChars.length;i++)
         tChars[i]=(char)bytes[i];
        
        tStringBuf.append(tChars);      
        return tStringBuf.toString(); 
    } 
}