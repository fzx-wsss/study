package com.wsss.basic.util.encrypt.ed25519;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.Ed25519KeyGenerationParameters;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.crypto.util.PrivateKeyInfoFactory;
import org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory;

import java.io.IOException;
import java.security.Security;
import java.util.Base64;

public class Ed25519Example {

    public static void main(String[] args) throws CryptoException, InvalidCipherTextException, IOException {
        // 添加Bouncy Castle作为Provider
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        // 生成Ed25519密钥对
        AsymmetricCipherKeyPair keyPair = generateEd25519KeyPair();

        // 获取私钥和公钥
        AsymmetricKeyParameter privateKey = (AsymmetricKeyParameter) keyPair.getPrivate();
        AsymmetricKeyParameter publicKey = (AsymmetricKeyParameter) keyPair.getPublic();
        System.out.println("privateKey:" + Base64.getEncoder().encodeToString(((Ed25519PrivateKeyParameters) privateKey).getEncoded()));
        System.out.println("publicKey:" + Base64.getEncoder().encodeToString(((Ed25519PublicKeyParameters) publicKey).getEncoded()));
        // 将私钥导出为DER格式
        byte[] privateKeyDer = PrivateKeyInfoFactory.createPrivateKeyInfo(privateKey).getEncoded();

        // 将公钥导出为DER格式
        byte[] publicKeyDer = SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(publicKey).getEncoded();
        System.out.println("privateKey:" + Base64.getEncoder().encodeToString(privateKeyDer));
        System.out.println("publicKey:" + Base64.getEncoder().encodeToString(publicKeyDer));

        // 示例消息
        byte[] message = "Hello, Ed25519!".getBytes();

        // 使用私钥进行签名
        byte[] signature = sign(message, privateKey);

        // 使用公钥进行验证
        boolean isValid = verify(message, signature, publicKey);
        System.out.println("signature:" + Base64.getEncoder().encodeToString(signature));
        System.out.println("Signature is valid: " + isValid);
    }

    private static AsymmetricCipherKeyPair generateEd25519KeyPair() {
        Ed25519KeyPairGenerator keyPairGenerator = new Ed25519KeyPairGenerator();
        keyPairGenerator.init(new Ed25519KeyGenerationParameters(null));
        return keyPairGenerator.generateKeyPair();
    }

    private static byte[] sign(byte[] message, AsymmetricKeyParameter privateKey) throws CryptoException {
        Ed25519Signer signer = new Ed25519Signer();
        signer.init(true, privateKey);
        signer.update(message, 0, message.length);
        return signer.generateSignature();
    }

    private static boolean verify(byte[] message, byte[] signature, AsymmetricKeyParameter publicKey) throws InvalidCipherTextException {
        Ed25519Signer signer = new Ed25519Signer();
        signer.init(false, publicKey);
        signer.update(message, 0, message.length);
        return signer.verifySignature(signature);
    }
}
