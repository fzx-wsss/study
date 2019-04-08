package com.wsss.basic.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description: 这里用一句话描述这个类的作用
 * @see: MessageCrypto 此处填写需要参考的类
 * @version 2016年4月20日 下午3:13:59
 * @author jingyuan.ma
 */
public class MessageCrypto {
	/**
	 * 数字签名函数入口
	 * @param plainBytes
	 *            待签名明文字节数组
	 * @param privateKey
	 *            签名使用私钥
	 * @param signAlgorithm
	 *            签名算法
	 * @return 签名后的字节数组
	 * @throws Exception
	 */
	public static byte[] digitalSign(byte[] plainBytes, PrivateKey privateKey, String signAlgorithm) throws Exception {
		try {
			Signature signature = Signature.getInstance(signAlgorithm);
			signature.initSign(privateKey);
			signature.update(plainBytes);
			byte[] signBytes = signature.sign();

			return signBytes;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception(String.format("数字签名时没有[%s]此类算法", signAlgorithm));
		} catch (InvalidKeyException e) {
			throw new Exception("数字签名时私钥无效");
		} catch (SignatureException e) {
			throw new Exception("数字签名时出现异常");
		}
	}

	/**
	 * 验证数字签名函数入口
	 * @param plainBytes
	 *            待验签明文字节数组
	 * @param signBytes
	 *            待验签签名后字节数组
	 * @param publicKey
	 *            验签使用公钥
	 * @param signAlgorithm
	 *            签名算法
	 * @return 验签是否通过
	 * @throws Exception
	 */
	public static boolean verifyDigitalSign(byte[] plainBytes, byte[] signBytes, PublicKey publicKey, String signAlgorithm) throws Exception {
		boolean isValid = false;
		try {
			Signature signature = Signature.getInstance(signAlgorithm);
			signature.initVerify(publicKey);
			signature.update(plainBytes);
			isValid = signature.verify(signBytes);
			return isValid;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception(String.format("验证数字签名时没有[%s]此类算法", signAlgorithm));
		} catch (InvalidKeyException e) {
			throw new Exception("验证数字签名时公钥无效");
		} catch (SignatureException e) {
			throw new Exception("验证数字签名时出现异常");
		}
	}

	/**
	 * 加密
	 * @param plainBytes
	 *            明文字节数组
	 * @param publicKey
	 *            公钥
	 * @param keyLength
	 *            密钥bit长度
	 * @param reserveSize
	 *            padding填充字节数，预留11字节
	 * @param cipherAlgorithm
	 *            加解密算法，一般为RSA/ECB/PKCS1Padding
	 * @return 加密后字节数组，不经base64编码
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] plainBytes, PublicKey publicKey, int keyLength, int reserveSize, String cipherAlgorithm) throws Exception {
		int keyByteSize = keyLength / 8; // 密钥字节数
		int encryptBlockSize = keyByteSize - reserveSize; // 加密块大小=密钥字节数-padding填充字节数
		int nBlock = plainBytes.length / encryptBlockSize;// 计算分段加密的block数，向上取整
		if ((plainBytes.length % encryptBlockSize) != 0) { // 余数非0，block数再加1
			nBlock += 1;
		}

		try {
			Cipher cipher = Cipher.getInstance(cipherAlgorithm);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			// 输出buffer，大小为nBlock个keyByteSize
			ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * keyByteSize);
			// 分段加密
			for (int offset = 0; offset < plainBytes.length; offset += encryptBlockSize) {
				int inputLen = plainBytes.length - offset;
				if (inputLen > encryptBlockSize) {
					inputLen = encryptBlockSize;
				}

				// 得到分段加密结果
				byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
				// 追加结果到输出buffer中
				outbuf.write(encryptedBlock);
			}

			outbuf.flush();
			outbuf.close();
			return outbuf.toByteArray();
		} catch (NoSuchAlgorithmException e) {
			throw new Exception(String.format("没有[%s]此类加密算法", cipherAlgorithm));
		} catch (NoSuchPaddingException e) {
			throw new Exception(String.format("没有[%s]此类填充模式", cipherAlgorithm));
		} catch (InvalidKeyException e) {
			throw new Exception("无效密钥");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("加密块大小不合法");
		} catch (BadPaddingException e) {
			throw new Exception("错误填充模式");
		} catch (IOException e) {
			throw new Exception("字节输出流异常");
		}
	}

	/**
	 * RSA解密
	 * @param encryptedBytes
	 *            加密后字节数组
	 * @param privateKey
	 *            私钥
	 * @param keyLength
	 *            密钥bit长度
	 * @param reserveSize
	 *            padding填充字节数，预留11字节
	 * @param cipherAlgorithm
	 *            加解密算法，一般为RSA/ECB/PKCS1Padding
	 * @return 解密后字节数组，不经base64编码
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] encryptedBytes, PrivateKey privateKey, int keyLength, int reserveSize, String cipherAlgorithm) throws Exception {
		int keyByteSize = keyLength / 8; // 密钥字节数
		int decryptBlockSize = keyByteSize - reserveSize; // 解密块大小=密钥字节数-padding填充字节数
		int nBlock = encryptedBytes.length / keyByteSize;// 计算分段解密的block数，理论上能整除

		try {
			Cipher cipher = Cipher.getInstance(cipherAlgorithm);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			// 输出buffer，大小为nBlock个decryptBlockSize
			ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * decryptBlockSize);
			// 分段解密
			for (int offset = 0; offset < encryptedBytes.length; offset += keyByteSize) {
				// block大小: decryptBlock 或 剩余字节数
				int inputLen = encryptedBytes.length - offset;
				if (inputLen > keyByteSize) {
					inputLen = keyByteSize;
				}

				// 得到分段解密结果
				byte[] decryptedBlock = cipher.doFinal(encryptedBytes, offset, inputLen);
				// 追加结果到输出buffer中
				outbuf.write(decryptedBlock);
			}

			outbuf.flush();
			outbuf.close();
			return outbuf.toByteArray();
		} catch (NoSuchAlgorithmException e) {
			throw new Exception(String.format("没有[%s]此类解密算法", cipherAlgorithm));
		} catch (NoSuchPaddingException e) {
			throw new Exception(String.format("没有[%s]此类填充模式", cipherAlgorithm));
		} catch (InvalidKeyException e) {
			throw new Exception("无效密钥");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("解密块大小不合法");
		} catch (BadPaddingException e) {
			throw new Exception("错误填充模式");
		} catch (IOException e) {
			throw new Exception("字节输出流异常");
		}
	}

	/**
	 * 字符数组转16进制字符串
	 * @param bytes
	 * @return
	 */
	public static String bytes2string(byte[] bytes, int radix) {
		// 2个16进制字符占用1个字节，8个二进制字符占用1个字节
		int size = 2;
		if (radix == 2) {
			size = 8;
		}
		StringBuilder sb = new StringBuilder(bytes.length * size);
		for (int i = 0; i < bytes.length; i++) {
			int integer = bytes[i];
			while (integer < 0) {
				integer = integer + 256;
			}
			String str = Integer.toString(integer, radix);
			sb.append(StringUtils.leftPad(str.toUpperCase(), size, "0"));
		}
		return sb.toString();
	}
}
