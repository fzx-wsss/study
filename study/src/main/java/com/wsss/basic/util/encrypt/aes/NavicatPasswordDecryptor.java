package com.wsss.basic.util.encrypt.aes;

import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class NavicatPasswordDecryptor {

    private static final String AES_KEY = "libcckeylibcckey"; // AES-128密钥（16字节）
    private static final String AES_IV = "libcciv libcciv ";  // 初始向量（16字节）

    public static String decrypt(String encryptedHex) throws Exception {
        // 将16进制字符串转为字节数组
        byte[] encryptedBytes = Hex.decode(encryptedHex);

        // 初始化AES解密器
        SecretKeySpec keySpec = new SecretKeySpec(AES_KEY.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(AES_IV.getBytes());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        // 执行解密
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, "UTF-8");
    }

    public static void main(String[] args) throws Exception {
        String encryptedPassword = "B536E8BC09B27BD7CF9EB31559A9D5B061FB5ECE6D52E61324A19F399BE1AE3A"; // 替换为实际密文
        System.out.println("解密结果: " + decrypt(encryptedPassword));
    }
}
