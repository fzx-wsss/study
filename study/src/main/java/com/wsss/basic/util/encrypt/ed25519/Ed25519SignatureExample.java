package com.wsss.basic.util.encrypt.ed25519;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.Ed25519KeyGenerationParameters;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.crypto.util.PrivateKeyInfoFactory;
import org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory;

import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Ed25519SignatureExample {

    public static void main(String[] args) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        // 使用OpenSSL生成的PEM格式私钥
        String privateKeyPem = "MC4CAQAwBQYDK2VwBCIEIIxle4U3KqCakwQQAA4VScewVblRlqFCjc8Gd6Z5lICI";

        // 使用OpenSSL生成的PEM格式公钥
        String publicKeyPem = "MCowBQYDK2VwAyEA1YeVswkeLddMmADZTfxMf9FEztFYie8ObrzKJmJbE7o=";

        // 加载PEM格式私钥
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyPem);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        // 加载PEM格式公钥
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyPem);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        // 签名数据
        byte[] dataToSign = "Hello, Ed25519!".getBytes();
        // 创建Ed25519签名器
        Ed25519Signer signer = new Ed25519Signer();
        Ed25519PrivateKeyParameters privateKeyParameters = new Ed25519PrivateKeyParameters(privateKeySpec.getEncoded(),16);
        signer.init(true, privateKeyParameters);
        System.out.println("privateKey:" + Base64.getEncoder().encodeToString(privateKeyParameters.getEncoded()));
        signer.update(dataToSign, 0, dataToSign.length);
        byte[] signature = signer.generateSignature();
        Ed25519Signer verifier = new Ed25519Signer();
        Ed25519PublicKeyParameters publicKeyParameters = new Ed25519PublicKeyParameters(publicKeySpec.getEncoded(),12);
        verifier.init(false, publicKeyParameters);
        verifier.update(dataToSign, 0, dataToSign.length);
        boolean isSignatureValid = verifier.verifySignature(signature);
        System.out.println("publicKey:" + Base64.getEncoder().encodeToString(publicKeyParameters.getEncoded()));
        System.out.println("Signature: " + Base64.getEncoder().encodeToString(signature));
        System.out.println("Signature is valid: " + isSignatureValid);
        // 验证签名
        test(dataToSign,signature,publicKeySpec);
        long start = System.currentTimeMillis();
        test(dataToSign,signature,publicKeySpec);
        long end = System.currentTimeMillis();
        System.out.println("time:" + (end-start));
        System.out.println("publicKey:" + Base64.getEncoder().encodeToString(publicKeyParameters.getEncoded()));
        System.out.println("Signature: " + Base64.getEncoder().encodeToString(signature));
        System.out.println("Signature is valid: " + isSignatureValid);
    }

    private static void test(byte[] dataToSign,byte[] signature,X509EncodedKeySpec publicKeySpec) {
        for(int i=0;i<1000;i++) {
            Ed25519Signer verifier = new Ed25519Signer();
            Ed25519PublicKeyParameters publicKeyParameters = new Ed25519PublicKeyParameters(publicKeySpec.getEncoded(),12);
            verifier.init(false, publicKeyParameters);
            verifier.update(dataToSign, 0, dataToSign.length);
            boolean isSignatureValid = verifier.verifySignature(signature);
        }
    }
}
