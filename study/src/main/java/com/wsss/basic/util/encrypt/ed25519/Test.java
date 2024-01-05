package com.wsss.basic.util.encrypt.ed25519;

import com.cmcm.finance.sign.Signer;
import org.spongycastle.util.encoders.Hex;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String[] args) {
        signTest();
    }
    public static void signTest(){
        String pubK = "a1786ff4e3de5559357c61732423f387084db268fe9c5a85c5f8eb1a0ffb434c";
        String priK = "c1326888dd2cefd48ea9476c16fc6876ecb07ab21c4369136a7344a6f8f13025";
        try {
            String txt = "bake3susdt10000000";
            // 私钥签名
            System.out.println(txt);
            byte[] data1 = Signer.signWithHexKey(txt.getBytes(),priK);
            String sign = Hex.toHexString(data1);
            System.out.println("签名："+ sign);
            // 公钥验签
            boolean succ1 = Signer.verifyWithHexKey(txt.getBytes(),pubK, Hex.decode(sign));
            System.out.println("验签结果："+succ1);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test() throws Exception {
        String privateKey = "jGV7hTcqoJqTBBAADhVJx7BVuVGWoUKNzwZ3pnmUgIg=";
        String publicKey = "1YeVswkeLddMmADZTfxMf9FEztFYie8ObrzKJmJbE7o=";
//        String privateKey = "c1326888dd2cefd48ea9476c16fc6876ecb07ab21c4369136a7344a6f8f13025";
//        String publicKey = "a1786ff4e3de5559357c61732423f387084db268fe9c5a85c5f8eb1a0ffb434c";
        System.out.println("privateKey:" + privateKey);
        System.out.println("publicKey:" + publicKey);

        String data = "symbol=LTCBTC&side=BUY&type=LIMIT&timeInForce=GTC&quantity=1&price=0.1&recvWindow=5000&timestamp=1499827319559";
        String signData = Base64.getEncoder().encodeToString(Signer.signWithBase64Key(data.getBytes(),privateKey));

        System.out.println("signData:" + signData);

        Boolean flag = Signer.verifyWithBase64Key(data.getBytes(), publicKey, Base64.getDecoder().decode(signData));
        System.out.println(flag);

        for (int i = 0; i < 1000; i++) {
            signData = Base64.getEncoder().encodeToString(Signer.signWithBase64Key(data.getBytes(),privateKey));
            boolean res = Signer.verifyWithBase64Key(data.getBytes(), publicKey, Base64.getDecoder().decode(signData));
        }

        long start = System.currentTimeMillis();
        signData = Base64.getEncoder().encodeToString(Signer.signWithBase64Key(data.getBytes(),privateKey));
        for (int i = 0; i < 1000; i++) {
            boolean res = Signer.verifyWithBase64Key(data.getBytes(), publicKey, Base64.getDecoder().decode(signData));
        }
        long end = System.currentTimeMillis();
        System.out.println("time:" + (end - start));
    }
}
